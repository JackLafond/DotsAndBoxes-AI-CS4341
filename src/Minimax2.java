import java.util.List;

//This class handles the AI movements
public class Minimax2 {

	public Minimax2 () {}

	//Move is called from main to initiate AI Move
	public static int[] getBestMove(Board2 b) {

		long curTime = System.currentTimeMillis();
		long endTime = curTime + 8000;
		return search(b, 0, b.myMove, Integer.MIN_VALUE, Integer.MAX_VALUE, endTime);
	
	}

	// returns an int of size 3 where int[2] is the minimax eval, 
	// int[0] is the row of the line, and int[1] is the column of the line (in our 19x19 array)
	public static int[] search(Board2 b, int depth, boolean isMaxing, int alpha, int beta, long endTime) {

		List<int[]> moves;

		if(depth < 3) {
			moves = b.getLegalMoves();
		} else {
			//System.out.println("depth reached: " + depth);
			moves = b.getLimitedLegalMoves((int) (b.numbMovesLeft / Math.pow(2, Double.valueOf(depth - 2))));
			//System.out.println("Move Size: " + moves.size());
		}

		if(moves.isEmpty() || System.currentTimeMillis() >= endTime || depth > 5) {
			int[] curMove = b.madeMoves.getLast();
			return new int[]{curMove[0], curMove[1], b.evaluate()};
		}

		int[] curMove = moves.get(0);
		
		if(isMaxing) {

			int[] bestMove = new int[] {curMove[0], curMove[1], Integer.MIN_VALUE};
			for(int[] move : moves) {

				if(!b.completeMove(move[0], move[1])){
					b.myMove = !b.myMove;
				}
				int[] aMove = search(b, depth + 1, b.myMove, alpha, beta, endTime);
				if(aMove[2] >= bestMove[2]) {
					bestMove = aMove;
				}
				alpha = Math.max(bestMove[2], alpha);
				b.undoMove(move[0], move[1]);
				b.myMove = isMaxing;
				if(alpha >= beta) {
					break;
				}
			}
			return bestMove;

		} else {

			int[] bestMove = new int[] {curMove[0], curMove[1], Integer.MAX_VALUE};
			for(int[] move : moves) {

				if(!b.completeMove(move[0], move[1])) {
					b.myMove = !b.myMove;
				}
				int[] aMove = search(b, depth + 1, b.myMove, alpha, beta, endTime);
				if(aMove[2] <= bestMove[2]) {
					bestMove = aMove;
				}
				beta = Math.min(bestMove[2], beta);
				b.undoMove(move[0], move[1]);
				b.myMove = isMaxing;
				if(alpha >= beta) {
					break;
				}
			}
			return bestMove;
		}
	}
}