import java.util.Arrays;
import java.util.List;

// This class handles the AI movements
public class Minimax2 {

	public int[] bestOverall;
	public boolean mySearch;

	public Minimax2 () {
		this.bestOverall = new int[]{};
	}

	// Move is called from main to initiate AI Move
	public int[] getBestMove(Board2 b) {

		// set a time limit
		long curTime = System.currentTimeMillis();
		long endTime = curTime + 8000;
		this.bestOverall = new int[]{};
		mySearch = b.myMove;
		return search(b, 0, b.myMove, Integer.MIN_VALUE, Integer.MAX_VALUE, endTime);
	
	}

	// returns an int of size 3 where int[2] is the minimax eval, 
	// int[0] is the row of the line, and int[1] is the column of the line (in our 19x19 array)
	public int[] search(Board2 b, int depth, boolean isMaxing, int alpha, int beta, long endTime) {

		// return best overall if time is up
		if(System.currentTimeMillis() >= endTime) {
			return this.bestOverall;
		}

		List<int[]> moves;

		// iterative deepening: keep deepening if time limit allows
		// once we get to a certain depth, begin limiting the amount fo children to be viewed using heuristic 
		/* 
		if(depth < 2) {
			moves = b.getLegalMoves();
		} else {
			// limit gets smaller and smaller as we get to deeper depths
			moves = b.getLimitedLegalMoves((int) (b.numbMovesLeft / Math.pow(2, Double.valueOf(depth - 2))));
		}
		*/
		moves = b.getLegalMoves();


		// terminating criteria: leaf, or depth too deep
		if(moves.isEmpty() || depth > 5) {
			int[] curMove = b.madeMoves.getLast();
			return new int[]{curMove[0], curMove[1], b.evaluate()};
		}

		// initialize first move to start comparisons
		int[] curMove = moves.get(0);
		if(bestOverall.length == 0) {
			bestOverall = new int[] {curMove[0], curMove[1], b.myMove? Integer.MIN_VALUE : Integer.MAX_VALUE};
		}
		
		if(isMaxing) {

			int[] bestMaxMove = new int[] {curMove[0], curMove[1], Integer.MIN_VALUE};
			for(int[] move : moves) {

				// make a move, evaluate it, and update current best move, keeping track of whose turn it is
				if(!b.completeMove(move[0], move[1])){
					b.myMove = !b.myMove;
				}
				int[] aMove = search(b, depth + 1, b.myMove, alpha, beta, endTime);
				if(aMove[2] >= bestMaxMove[2]) {
					bestMaxMove = Arrays.copyOf(aMove, 3);
				}
				alpha = Math.max(bestMaxMove[2], alpha);

				// undo move and reset whose turn it is
				b.undoMove(move[0], move[1]);
				b.myMove = isMaxing;

				// check for pruning
				if(alpha > beta) {
					break;
				}
			}
			
			
			if(mySearch && bestMaxMove[2] >= bestOverall[2]) {
				bestOverall = Arrays.copyOf(bestMaxMove, 3);
				if(bestOverall[0] == 0 && bestOverall[1] == 7) {
					b.printboard();
				}
			}
			
			return bestMaxMove;

		} else {

			int[] bestMinMove = new int[] {curMove[0], curMove[1], Integer.MAX_VALUE};
			for(int[] move : moves) {

				// make a move, evaluate it, and update current best move, keeping track of whose turn it is
				if(!b.completeMove(move[0], move[1])) {
					b.myMove = !b.myMove;
				}
				int[] aMove = search(b, depth + 1, b.myMove, alpha, beta, endTime);
				if(aMove[2] <= bestMinMove[2]) {
					bestMinMove = Arrays.copyOf(aMove, 3);
				}
				beta = Math.min(bestMinMove[2], beta);

				// undo move and reset whose turn it is
				b.undoMove(move[0], move[1]);
				b.myMove = isMaxing;

				// check for pruning
				if(alpha > beta) {
					break;
				}
			}

			if(!mySearch && bestMinMove[2] <= bestOverall[2]) {
				bestOverall = Arrays.copyOf(bestMinMove, 3);
			}
			
			return bestMinMove;
		}
	}
}