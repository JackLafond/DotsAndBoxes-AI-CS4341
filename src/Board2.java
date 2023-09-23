//Each Board will hold information of the current boardState

import java.util.LinkedList;

public class Board2 {

    public static final int DOT = 8;
    public static final int BLANK_SPACE = 0;
    public static final int EMPTY_LINE = 2;
    public static final int COMPLETED_LINE = 4;
    public static final int BOARD_SIZE = 9;

	int[][] boardState;
	LinkedList<int[]> madeMoves;
	int playerscore;
	int aiscore;
	boolean myMove;

	public Board2 (int[][] state, int playerscore, int aiscore, boolean myMove) {
		this.playerscore = playerscore;
		this.aiscore = aiscore;
		this.boardState = state;
		this.myMove = myMove;
		this.madeMoves = new LinkedList<int[]>();
	}

	public void addMadeMove(int[] aMove) {
		this.madeMoves.add(aMove);
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
		for (int i = 0; i < BOARD_SIZE * 2 + 1; i++) {
			for (int j = 0; j < BOARD_SIZE * 2 + 1; j++) {
				char cellValue;
				switch (boardState[i][j]) {
					case DOT:
						cellValue = '.';
						break;
					case BLANK_SPACE:
						cellValue = '0';
						break;
					case EMPTY_LINE:
						cellValue = ' ';
						break;
					case COMPLETED_LINE:
						cellValue = '-';
						break;
					case 1:
						cellValue = '1';
						break;
					case -1:
						cellValue = '!';
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
			boolean shifted = false;
			for(int col = 0; col < 19; col = col + 2) {
                if(row % 2 == 0 && !shifted) {
                    col = col + 1;
					shifted = true;
                }
				if(boardState[row][col] == Board.EMPTY_LINE) {
					moves.add(new int[]{row, col, 0});
				}
			}
		}

		return moves;
	}

	public boolean completeMove(int row, int col) {

		boardState[row][col] = Board.COMPLETED_LINE;
		addMadeMove(new int[]{row, col});
		
		if(row % 2 == 0) {
			if(row == 0) {
				return checkBox(row+1, col);
			} else if (row == 18) {
				return checkBox(row-1, col);
			} else {
				boolean a = checkBox(row+1, col);
				boolean b = checkBox(row-1, col);
				return a || b;
			}
		} else {
			if(col == 0) {
				return checkBox(row, col+1);
			} else if(col == 18) {
				return checkBox(row, col-1);
			} else {
				boolean a = checkBox(row, col-1);
				boolean b = checkBox(row, col+1);
				return a || b;
			}
		}
	}

	public boolean undoMove(int row, int col) {

		boardState[row][col] = Board.EMPTY_LINE;
		this.madeMoves.removeLast();

		if(row % 2 == 0) {
			if(row == 0) {
				return undoBox(row+1, col);
			} else if(row == 18) {
				return undoBox(row-1, col);
			} else {
				boolean a = undoBox(row+1, col);
				boolean b = undoBox(row-1, col);
				return a || b;
			}
		} else {
			if(col == 0) {
				return undoBox(row, col+1);
			} else if(col == 18) {
				return undoBox(row, col-1);
			} else {
				boolean a = undoBox(row, col-1);
				boolean b = undoBox(row, col+1);
				return a || b;
			}
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

	public boolean undoBox(int row, int col) {
		if(boardState[row][col] != Board.BLANK_SPACE) {
			boardState[row][col] = Board.BLANK_SPACE;
			if(myMove) {
				aiscore = aiscore - 1;
			} else {
				playerscore = playerscore - 1;
			}
			return true;
		}
		return false;
	}
}