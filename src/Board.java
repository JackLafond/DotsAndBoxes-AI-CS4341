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

	public void updateplayerscore (int score) {
		this.playerscore += score;
	}

	public void updateaiscore (int score) {
		this.aiscore += score;
	}

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

	public void updatescore (int row, int col, String direction) {
        
	}
}