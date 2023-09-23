import java.util.List;
import java.util.LinkedList;

//This class handles the AI movements
public class Minimax2 {

	public Minimax2 () {}

	//Move is called from main to initiate AI Move
	public static int[] getBestMove(Board2 b) {

		return search(b, 0, true, Integer.MIN_VALUE, Integer.MAX_VALUE);
	
	}

	// returns an int of size 3 where int[2] is the minimax eval, 
	// int[0] is the row of the line, and int[1] is the column of the line (in our 19x19 array)
	public static int[] search(Board2 b, int depth, boolean isMaxing, int alpha, int beta) {

		LinkedList<int[]> moves = b.getLegalMoves();

		if(moves.isEmpty() || depth == 4) {
			int[] curMove = b.madeMoves.getLast();
			return new int[]{curMove[0], curMove[1], b.evaluate()};
		}

		int[] curMove = b.getLegalMoves().getFirst();
		
		if(isMaxing) {

			int[] bestMove = new int[] {curMove[0], curMove[1], Integer.MIN_VALUE};
			for(int[] move : moves) {

				if(!b.completeMove(move[0], move[1])){
					b.myMove = !b.myMove;
				}
				int[] aMove = search(b, depth + 1, b.myMove, alpha, beta);
				bestMove[2] = Math.max(bestMove[2], aMove[2]);
				alpha = Math.max(bestMove[2], alpha);
				b.undoMove(move[0], move[1]);
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
				int[] aMove = search(b, depth + 1, b.myMove, alpha, beta);
				bestMove[2] = Math.min(bestMove[2], aMove[2]);
				beta = Math.min(bestMove[2], beta);
				b.undoMove(move[0], move[1]);
				if(alpha >= beta) {
					break;
				}
			}
			return bestMove;
		}
	}

	public Node getMove (Node current) {
		Node tempNode = current;

		while (tempNode.parent.parent != null) {
			tempNode = tempNode.parent;
		}
		return tempNode;
	}

    public List<Board> getChildren(Board b) {
        LinkedList<Board> children = new LinkedList<Board>();
        int[][] curState = b.getState();
        for(int row = 0; row < 19; row++) {
			boolean shifted = false;
            for(int col = 0; col < 19; col = col + 2) {
                if(row % 2 == 0 && !shifted) {
                    col = col + 1;
					shifted = true;
                }
                if(curState[row][col] == Board.EMPTY_LINE) {
                    int[][] newState = copyArray(curState, curState.length, curState.length);
                    Board newB = new Board(newState, b.playerscore, b.aiscore, b.myMove);
					boolean completeBox = newB.completeLine(row, col);
					if(!completeBox) {
						newB.myMove = !newB.myMove;
					}
                    children.add(newB);
                }
            }
        }
        return children;

    }

	public int[][] copyArray (int[][] state, int rows, int cols) {
		int[][] temp = new int[rows][cols];
		for (int i = 0; i < rows; i ++) {
			for (int j = 0; j < cols; j++) {
				temp[i][j] = state[i][j];
			}
		}
		return temp;
	}
}