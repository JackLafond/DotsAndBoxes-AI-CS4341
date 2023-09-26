import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// This class handles the AI movements
public class Minimax {

	ArrayList<Integer> curBestMaxesOverall;

	public Minimax () {
		curBestMaxesOverall = new ArrayList<Integer>();
	}

	// Move is called from main to initiate AI Move
	public int[] getBestMove(Board2 b) {

		// set a time limit
		long curTime = System.currentTimeMillis();
		long endTime = curTime + 8000;


        List<int[]> children = b.getLegalMoves();
        int curBest = Integer.MIN_VALUE;
        int[] bestChild = new int[]{};
		int childIx = 0;

        for(int[] child : children) {
			b.myMove = true;
			curBestMaxesOverall.add(Integer.MIN_VALUE);
			boolean madeBox = b.completeMove(child[0], child[1]);
			if(bestChild.length == 0) {
				bestChild = new int[] {child[0], child[1], b.evaluate()};
			}
            if(!madeBox) {
                b.myMove = !b.myMove;
            }
            int eval = search(b, 1, b.myMove, Integer.MIN_VALUE, Integer.MAX_VALUE, endTime, childIx);
			if(eval > curBest) {
                int[] temp = Arrays.copyOf(child, 2);
                bestChild = new int[] {temp[0], temp[1], eval};
				curBest = eval;
			}
    
			b.undoMove(child[0], child[1]);
			childIx++;
        }
		return bestChild;
	
	}

	// returns an int of size 3 where int[2] is the minimax eval, 
	// int[0] is the row of the line, and int[1] is the column of the line (in our 19x19 array)
	public int search(Board2 b, int depth, boolean isMaxing, int alpha, int beta, long endTime, int childIx) {

		List<int[]> moves;

		// iterative deepening: keep deepening if time limit allows
		// once we get to a certain depth, begin limiting the amount fo children to be viewed using heuristic 
		// limit gets smaller and smaller as we get to deeper depths
		moves = b.getLimitedLegalMoves((int) (b.numbMovesLeft / Math.pow(2, Double.valueOf(depth - 1))));


		// terminating criteria: leaf, or depth too deep
		if(moves.isEmpty() || depth > 5) {
			return b.evaluate();
		} 
		if(System.currentTimeMillis() >= endTime) {
			return curBestMaxesOverall.get(childIx);
		}

		if(isMaxing) {

			int bestMaxVal = Integer.MIN_VALUE;
			for(int[] move : moves) {

				// make a move, evaluate it, and update current best move, keeping track of whose turn it is
				boolean madeBox = b.completeMove(move[0], move[1]);
				if(!madeBox){
					b.myMove = !b.myMove;
				}
				int aVal = search(b, depth + 1, b.myMove, alpha, beta, endTime, childIx);
				bestMaxVal = Math.max(bestMaxVal, aVal);
				alpha = Math.max(bestMaxVal, alpha);

				// undo move and reset whose turn it is
				b.undoMove(move[0], move[1]);
				b.myMove = true;

				// check for pruning
				if(alpha >= beta) {
					break;
				}
			}
			curBestMaxesOverall.set(childIx, Math.max(bestMaxVal, curBestMaxesOverall.get(childIx)));
			return bestMaxVal;

		} else {

			int bestMinVal = Integer.MAX_VALUE;
			for(int[] move : moves) {

				// make a move, evaluate it, and update current best move, keeping track of whose turn it is
				boolean madeBox = b.completeMove(move[0], move[1]);
				if(!madeBox) {
					b.myMove = !b.myMove;
				}
				int aVal = search(b, depth + 1, b.myMove, alpha, beta, endTime, childIx);
				bestMinVal = Math.min(aVal, bestMinVal);
				beta = Math.min(bestMinVal, beta);

				// undo move and reset whose turn it is
				b.undoMove(move[0], move[1]);
				b.myMove = false;

				// check for pruning
				if(alpha >= beta) {
					break;
				}
			}
			return bestMinVal;
		}
	}
}