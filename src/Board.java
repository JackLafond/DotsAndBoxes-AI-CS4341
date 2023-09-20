//Each Board will hold information of the current boardState
public class Board {

    public static final int DOT = 8;
    public static final int BLANK_SPACE = 0;
    public static final int EMPTY_LINE = 2;
    public static final int COMPLETED_LINE = 4;
    public static final int BOARD_SIZE = 9;

	int[][] boardState;
	int maxlines;
	int playerscore;
	int aiscore;
	int totallines;
	boolean aimove;
	int difference;

	Board (int[][] state, int playerscore, int aiscore, boolean aimove, int totallines, int maxlines) {
		this.playerscore = playerscore;
		this.aiscore = aiscore;
		this.aimove = aimove;
		this.boardState = state;
		this.totallines = totallines;
		this.maxlines = maxlines;
	}

	public int[][] getState () {
		return this.boardState;
	}

	//Updates Player Score
	public void updateplayerscore (int score) {
		this.playerscore += score;
	}

	//Updates AI Score
	public void updateaiscore (int score) {
		this.aiscore += score;
	}

	//Checks if current move is a move for AI
	public boolean isAIMove () {
		return this.aimove;
	}

	//Evaluates the current board
	public void evaluate () {
		this.difference = this.aiscore - this.playerscore;
	}

	//Prints the board
	public void printboard() {
		for (int i = 0; i < BOARD_SIZE; i++ ) {
			for (int j = 0; j < BOARD_SIZE; j ++) {
				if (boardState[i][j] == DOT) {
					System.out.print("." + " ");
				}
				else if (boardState[i][j] == BLANK_SPACE || boardState[i][j] == EMPTY_LINE){
					System.out.print(" " + " ");
				}
				else if (boardState[i][j] == COMPLETED_LINE) {
					System.out.print("-" + " ");
				}
			}
			System.out.println();
		}
		System.out.println();
	}

	//Runs through the board after a move has been and checks if score needs to be updated
	public void updatescore (int row, int col, String direction) {

		if (direction.equals("horizontal")) {

			//If horizontal line is placed anywhere at the top of the board, then it just has to check if a box has been made below
			if (row == 0) {
				if (boardState[row+1][col-1] == 11 && boardState[row+1][col+1] == 11 && boardState[row+2][col] == 9) {
					if (aimove) {
						updateaiscore(boardState[row+1][col]);
					}
					else {
						updateplayerscore(boardState[row+1][col]);
					}
				}
			}

			//If horizontal line is placed anywhere at the bottom of the board, then it just has to check if a box has been made above
			else if (row == BOARD_SIZE - 1) {
				if (boardState[row-1][col-1] == 11 && boardState[row-1][col+1] == 11 && boardState[row-2][col] == 9) {
					if (aimove) {
						updateaiscore(boardState[row-1][col]);
					}
					else {
						updateplayerscore(boardState[row-1][col]);
					}
				}
			}

			//If horizontal line is placed anywhere else on the board, then it has to check if a box has been made above or below
			else {
				if (boardState[row+1][col-1] == 11 && boardState[row+1][col+1] == 11 && boardState[row+2][col] == 9) {
					if (aimove) {
						updateaiscore(boardState[row+1][col]);
					}
					else {
						updateplayerscore(boardState[row+1][col]);
					}
				}
				if (boardState[row-1][col-1] == 11 && boardState[row-1][col+1] == 11 && boardState[row-2][col] == 9) {
					if (aimove) {
						updateaiscore(boardState[row-1][col]);
					}
					else {
						updateplayerscore(boardState[row-1][col]);
					}
				}
			}

		}

		//If vertical line is placed anywhere at the very left of the board, then it just has to check if a box has been to the right
		else if (direction.equals("vertical")) {

			if (col == 0) {
				if (boardState[row-1][col+1] == 9 && boardState[row+1][col+1] == 9 && boardState[row][col+2] == 11) {
					if (aimove) {
						updateaiscore(boardState[row][col+1]);
					}
					else {
						updateplayerscore(boardState[row][col+1]);
					}
				}
			}

			//If vertical line is placed anywhere at the very right of the board, then it just has to check if a box has been to the left
			else if (col == BOARD_SIZE - 1) {
				if (boardState[row-1][col-1] == 9 && boardState[row+1][col-1] == 9 && boardState[row][col-2] == 11) {
					if (aimove) {
						updateaiscore(boardState[row][col-1]);
					}
					else {
						updateplayerscore(boardState[row][col-1]);
					}
				}
			}

			//If vertical line is placed anywhere else on the board, then it has to check if a box has been to the right or left
			else {
				if (boardState[row-1][col+1] == 9 && boardState[row+1][col+1] == 9 && boardState[row][col+2] == 11) {
					if (aimove) {
						updateaiscore(boardState[row][col+1]);
					}
					else {
						updateplayerscore(boardState[row][col+1]);
					}
				}

				if (boardState[row-1][col-1] == 9 && boardState[row+1][col-1] == 9 && boardState[row][col-2] == 11) {
					if (aimove) {
						updateaiscore(boardState[row][col-1]);
					}
					else {
						updateplayerscore(boardState[row][col-1]);
					}
				}
			}

		}
	}

}