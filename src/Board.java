//Each Board will hold information of the current boardState

import java.util.LinkedList;
import java.util.List;

public class Board {

    public static final int DOT = 8;
    public static final int BLANK_SPACE = 0;
    public static final int EMPTY_LINE = 2;
    public static final int COMPLETED_LINE = 4;
    public static final int BOARD_SIZE = 9;

	int[][] boardState;
	int[] lastLine;
	int playerscore;
	int aiscore;
	boolean myMove;

	Board (int[][] state, int playerscore, int aiscore, boolean myMove) {
		this.playerscore = playerscore;
		this.aiscore = aiscore;
		this.boardState = state;
		this.myMove = myMove;
		this.lastLine = null;
	}

	public void setLastLine(int[] aLine) {
		this.lastLine = aLine;
	}

	public int[][] getState () {
		return this.boardState;
	}

	public void updateplayerscore (int score) {
		this.playerscore += score;
	}

	public void updateaiscore (int score) {
		this.aiscore += score;
	}

	public int evaluate () {
		return this.aiscore - this.playerscore;
	}
	
	public void printboard() {
		for (int i = 0; i < BOARD_SIZE; i++) {
			for (int j = 0; j < BOARD_SIZE; j++) {
				char cellValue;
				switch (boardState[i][j]) {
					case DOT:
						cellValue = '.';
						break;
					case BLANK_SPACE:
					case EMPTY_LINE:
						cellValue = ' ';
						break;
					case COMPLETED_LINE:
						cellValue = '-';
						break;
					default:
						cellValue = ' ';
				}
				System.out.print(cellValue + " ");
			}
			System.out.println();
		}
		System.out.println();
	}


	public void updatescore (int row, int col, String direction) {
        
	}

	public LinkedList<int[]> getLegalMoves() {

		LinkedList<int[]> moves = new LinkedList<int[]>();

		for(int row = 0; row < 19; row++) {
			for(int col = 0; col < 19; col = col + 2) {
                if(row % 2 == 0) {
                    col = col + 1;
                }
				if(boardState[row][col] == Board.EMPTY_LINE) {
					moves.add(new int[]{row, col});
				}
			}
		}

		return moves;
	}

	public boolean completeLine(int row, int col) {

		boardState[row][col] = Board.COMPLETED_LINE;
		setLastLine(new int[]{row, col});
		if(row % 2 == 0) {
			return checkBox(row-1, col) || checkBox(row+1, col);
		} else {
			return checkBox(row, col-1) || checkBox(row, col+1);
		}
	}

	public boolean checkBox(int row, int col) {
		if(boardState[row][col] == Board.BLANK_SPACE) {
			int sum = boardState[row][col + 1] + boardState[row][col - 1] + boardState[row - 1][col] + boardState[row + 1][col];
			if(sum == 4 * Board.COMPLETED_LINE) {
				if(myMove) {
					boardState[row][col] = 1;
					aiscore = aiscore + 1;
				} else {
					boardState[row][col] = -1;
					playerscore = playerscore + 1;
				}
				return true;
			} else {
				return false;
			}
		}
		return true;
	}
}