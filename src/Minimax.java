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
	public int[] getBestMove(Board b) {

		// set a time limit
		long curTime = System.currentTimeMillis();
		long endTime = curTime + 8000;

		// get imediate children, init current best vals, child, and current child index
        List<int[]> children = b.getLegalMoves();
        int curBest = Integer.MIN_VALUE;
        int[] bestChild = new int[]{};
		int childIx = 0;

		// iterate through children
        for(int[] child : children) {

			// reset our move, add next childs current best
			b.myMove = true;
			curBestMaxesOverall.add(Integer.MIN_VALUE);

			// make a move, check if its still our turn, and set best child if it hasnt been set yet
			boolean madeBox = b.completeMove(child[0], child[1]);
			if(bestChild.length == 0) {
				bestChild = new int[] {child[0], child[1], b.evaluate()};
			}
            if(!madeBox) {
                b.myMove = !b.myMove;
            }

			// run search for current child
            int eval = search(b, 1, b.myMove, Integer.MIN_VALUE, Integer.MAX_VALUE, endTime, childIx);

			// if search is better than current best update our current bests
			if(eval > curBest) {
                int[] temp = Arrays.copyOf(child, 2);
                bestChild = new int[] {temp[0], temp[1], eval};
				curBest = eval;
			}
    
			// reset board and iterate child
			b.undoMove(child[0], child[1]);
			childIx++;
        }
		return bestChild;
	
	}

	// minimax algo, returns an int of the optimal value of the search
	public int search(Board b, int depth, boolean isMaxing, int alpha, int beta, long endTime, int childIx) {

		List<int[]> moves;

		// huersitic: limit children we view as we get to deeper depths
		// favor children where we can capture boxes of make boxes of 2
		moves = b.getLimitedLegalMoves((int) (b.numbMovesLeft / Math.pow(2, Double.valueOf(depth - 1))));


		// terminating criteria: leaf, or depth too deep
		if(moves.isEmpty() || depth > 5) {
			return b.evaluate();
		} 

		// terminate if time is over allowed
		if(System.currentTimeMillis() >= endTime) {
			return curBestMaxesOverall.get(childIx);
		}

		if(isMaxing) {

			// init best val
			int bestMaxVal = Integer.MIN_VALUE;
			for(int[] move : moves) {

				// make a move, update whose turn it is, evaluate it, and update current best move as well as alpha
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
			// update current best for the current immediate child
			curBestMaxesOverall.set(childIx, Math.max(bestMaxVal, curBestMaxesOverall.get(childIx)));
			return bestMaxVal;

		} else {

			// init best val
			int bestMinVal = Integer.MAX_VALUE;
			for(int[] move : moves) {

				// make a move, update whose turn it is, evaluate it, and update current best move as well as beta
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