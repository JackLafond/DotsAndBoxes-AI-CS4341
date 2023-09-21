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

	Board (int[][] state, int playerscore, int aiscore, boolean aimove) {
		this.playerscore = playerscore;
		this.aiscore = aiscore;
		this.boardState = state;
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

	public void updateState(int x, int y, int direction, int player){
		this.boardState[x][y] = COMPLETED_LINE;
		if(direction == 0){
			//Horizontal Line, check boxes above and below
			if(y != 0){
				//Check box below
				isBoxComplete(x, y-1, player);
			}
			if(y != 18){
				//check box above
				isBoxComplete(x, y+1, player);
			}
		} else{
			//Vertical line, check boxes left and right
			if(x != 0){
				//Check box left
				isBoxComplete(x-1, y, player);
			}
			if(x != 18){
				//check box right
				isBoxComplete(x+1, y, player);
			}
		}
	}

	/**
	 * Check to see if the box at this coordinate is complete or not. If so, update the value to the player id (1 or -1)
	 * @param x x coordinate of box in state
	 * @param y y coord of box in state
	 * @param player player value (1 or -1)
	 * @return true if the box is complete
	 */
	public boolean isBoxComplete(int x, int y, int player){
		int topLine = this.boardState[x][y+1];
		int bottomLine = this.boardState[x][y-1];
		int rightLine = this.boardState[x+1][y];
		int leftLine = this.boardState[x-1][y];

		if(topLine == COMPLETED_LINE && bottomLine == COMPLETED_LINE && rightLine == COMPLETED_LINE && leftLine == COMPLETED_LINE){
			this.boardState[x][y] = player;
			return true;
		}
		else return false;
	}

}