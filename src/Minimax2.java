import java.util.List;

// This class handles the AI movements
public class Minimax2 {

	public Minimax2 () {}

	// Move is called from main to initiate AI Move
	public static int[] getBestMove(Board2 b) {

		// set a time limit
		long curTime = System.currentTimeMillis();
		long endTime = curTime + 8000;
		return search(b, 0, b.myMove, Integer.MIN_VALUE, Integer.MAX_VALUE, endTime);
	
	}

	// returns an int of size 3 where int[2] is the minimax eval, 
	// int[0] is the row of the line, and int[1] is the column of the line (in our 19x19 array)
	public static int[] search(Board2 b, int depth, boolean isMaxing, int alpha, int beta, long endTime) {

		List<int[]> moves;

		// iterative deepening: keep deepening if time limit allows
		// once we get to a certain depth, begin limiting the amount fo children to be viewed using heuristic
		if(depth < 3) {
			moves = b.getLegalMoves();
		} else {
			// limit gets smaller and smaller as we get to deeper depths
			moves = b.getLimitedLegalMoves((int) (b.numbMovesLeft / Math.pow(2, Double.valueOf(depth - 2))));
		}

		// terminating criteria: leaf, time is up, or depth too deep
		if(moves.isEmpty() || System.currentTimeMillis() >= endTime || depth > 6) {
			int[] curMove = b.madeMoves.getLast();
			return new int[]{curMove[0], curMove[1], b.evaluate()};
		}

		// initialize first mvoe to start comparisons
		int[] curMove = moves.get(0);
		
		if(isMaxing) {

			int[] bestMove = new int[] {curMove[0], curMove[1], Integer.MIN_VALUE};
			for(int[] move : moves) {

				// make a move, evaluate it, and update current best move, keeping track of whose turn it is
				if(!b.completeMove(move[0], move[1])){
					b.myMove = !b.myMove;
				}
				int[] aMove = search(b, depth + 1, b.myMove, alpha, beta, endTime);
				if(aMove[2] >= bestMove[2]) {
					bestMove = aMove;
				}
				alpha = Math.max(bestMove[2], alpha);

				// undo move and reset whose turn it is
				b.undoMove(move[0], move[1]);
				b.myMove = isMaxing;

				// check for pruning
				if(alpha >= beta) {
					break;
				}
			}
			return bestMove;

		} else {

			int[] bestMove = new int[] {curMove[0], curMove[1], Integer.MAX_VALUE};
			for(int[] move : moves) {

				// make a move, evaluate it, and update current best move, keeping track of whose turn it is
				if(!b.completeMove(move[0], move[1])) {
					b.myMove = !b.myMove;
				}
				int[] aMove = search(b, depth + 1, b.myMove, alpha, beta, endTime);
				if(aMove[2] <= bestMove[2]) {
					bestMove = aMove;
				}
				beta = Math.min(bestMove[2], beta);

				// undo move and reset whose turn it is
				b.undoMove(move[0], move[1]);
				b.myMove = isMaxing;

				// check for pruning
				if(alpha >= beta) {
					break;
				}
			}
			return bestMove;
		}
	}
}