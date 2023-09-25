//Each Board will hold information of the current boardState

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class Board2 {

    public static final int DOT = 8;
    public static final int BLANK_SPACE = 0;
    public static final int EMPTY_LINE = 2;
    public static final int COMPLETED_LINE = 4;
    public static final int BOARD_SIZE = 9;

	int[][] boardState;
	LinkedList<int[]> madeMoves;
	int otherscore;
	int myscore;
	int numbMovesLeft;
	boolean myMove;

	public Board2 (int[][] state, int otherscore, int myscore, boolean myMove) {
		this.otherscore = otherscore;
		this.myscore = myscore;
		this.boardState = state;
		this.myMove = myMove;
		this.numbMovesLeft = 180;
		this.madeMoves = new LinkedList<int[]>();
	}

	public void addMadeMove(int[] aMove) {
		this.madeMoves.add(aMove);
	}

	public int[][] getState () {
		return this.boardState;
	}

	// evaluation function: our score - other score
	public int evaluate () {
		return this.myscore - this.otherscore;
	}
	
	// used to print board to console so we can track a game
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
						if(i % 2 == 1){
							cellValue ='|';
						} else
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

	public List<int[]> getSortedLegalMoves() {

		List<int[]> moves = new ArrayList<int[]>();

		for(int row = 0; row < 19; row++) {
			boolean shifted = false;
			for(int col = 0; col < 19; col = col + 2) {
                if(row % 2 == 0 && !shifted) {
                    col = col + 1;
					shifted = true;
                }
				if(boardState[row][col] == Board2.EMPTY_LINE) {
					moves.add(new int[]{row, col, 0});
				}
			}
		}

		return sortMoves(moves);
	}

	// fucntion to get the next possible moves from the current state of the board
	public List<int[]> getLegalMoves() {

		List<int[]> moves = new ArrayList<int[]>();

		for(int row = 0; row < 19; row++) {
			boolean shifted = false;
			for(int col = 0; col < 19; col = col + 2) {

				// in our board even number rows hold dots, need to adjust for that
                if(row % 2 == 0 && !shifted) {
                    col = col + 1;
					shifted = true;
                }

				// if the line we hit is empty then add it to list of possible moves
				if(boardState[row][col] == Board2.EMPTY_LINE) {
					moves.add(new int[]{row, col, 0});
				}
			}
		}

		return moves;
	}

	// huerisitc function to get legal moves with a limit on size
	// preference towards 3 filled and 1 filled boxes first, then 0s, then 2s
	public List<int[]> getLimitedLegalMoves(int limit) {

		List<int[]> moves = new ArrayList<int[]>();
		int[] prefOrder = new int[]{3, 1, 0, 2};
		int prefIx = 0;
		int curPref = prefOrder[prefIx];

		// keep filling while we havent hit limit yet
		while(moves.size() < limit) {
			
			for(int row = 0; row < 19; row++) {

				// check size
				if(moves.size() > limit) {
						break;
				}
				boolean shifted = false;

				for(int col = 0; col < 19; col = col + 2) {

					if(moves.size() > limit) {
						break;
					}

					// shift if even row
					if(row % 2 == 0 && !shifted) {
						col = col + 1;
						shifted = true;
					}

					// if we hit an empty line, check its surrounding boxes for how many lines it has
					// if the box matches our current priority then add it to list
					if(boardState[row][col] == Board2.EMPTY_LINE) {
						if(row % 2 == 0) {
							if(row == 0) {
								if(getLines(row + 1, col) == curPref) {
									moves.add(new int[]{row, col, 0});
								}
							} else if (row == 18) {
								if(getLines(row - 1, col) == curPref) {
									moves.add(new int[]{row, col, 0});
								}
							} else {
								if(getLines(row - 1, col) == curPref || getLines(row + 1, col) == curPref) {
									moves.add(new int[]{row, col, 0});
								}
							}
						} else {
							if(col == 0) {
								if(getLines(row, col + 1) == curPref) {
									moves.add(new int[]{row, col, 0});
								}
							} else if (col == 18) {
								if(getLines(row, col - 1) == curPref) {
									moves.add(new int[]{row, col, 0});
								}
							} else {
								if(getLines(row, col - 1) == curPref || getLines(row, col + 1) == curPref) {
									moves.add(new int[]{row, col, 0});
								}
							}
						}
					}
				}
			}

			// if we havent hit limit yet then iterate our current preference, checking that we havent ran out of preferences
			prefIx++;
			if(prefIx > 3) {
				break;
			}
			curPref = prefOrder[prefIx];
		}

		return moves;
	}

	// method used to make a move on a board where row and col is the location of the line in our 19 x 19 matrix 
	// returns true if a box is completed
	public boolean completeMove(int row, int col) {

		// set the lne to a completed line, reduce number of moves left, and add the last made move to our list of made moves
		boardState[row][col] = Board2.COMPLETED_LINE;
		numbMovesLeft--;
		addMadeMove(new int[]{row, col});
		
		// if row is even, line is horizontal, check boxes above and below (keeping in mind the first and last row of the matrix)
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

		// if row is odd, line is vertical, check boxes left and right of it (keep in mind first and last column)
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

	// method to undo a move, row and col is the location of the line to undo in our 19 x 19 matrix
	// returns true if a box is no longer complete as a result of taking back the move
	public boolean undoMove(int row, int col) {

		// set line to empty, increase the number of moves still available, remove the last move in our list of made moves
		boardState[row][col] = Board2.EMPTY_LINE;
		numbMovesLeft++;
		this.madeMoves.removeLast();

		// if row even, line is horizontal, check boxes above and below (keep in mind first and last row)
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

		// if row is odd, line is vertical, check boxes left and right (keep in mind first and last column)
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

	// function to check if a box is complete where row and column is the location of the center of the box in our 19 x 19 matrix
	// returns true if a box is complete, else false
	public boolean checkBox(int row, int col) {

		// check if box is not completed, if so check its lines (can use sum of values around the box)
		if(boardState[row][col] == Board2.BLANK_SPACE) {
			int sum = boardState[row][col + 1] + boardState[row][col - 1] + boardState[row - 1][col] + boardState[row + 1][col];

			// if the sum is same as 4 complete lines then box is completed, update it
			if(sum == 4 * Board2.COMPLETED_LINE) {

				// if it was our move then add to our score and set the boxes value to 1
				if(myMove) {
					boardState[row][col] = 1;
					myscore = myscore + 1;

				// if other player move then update their score and set box value to -1
				} else {
					boardState[row][col] = -1;
					otherscore = otherscore + 1;
				}
				return true;
			} else {
				return false;
			}
		}
		return true;
	}

	// method to undo a box when a move is undone, where row and col is the location of the center of the box in our 19 x 19 matrix
	// returns true if a box was complete before and is now not compelte, false if it was not complete before
	public boolean undoBox(int row, int col) {

		// if the box is worth 1, it was our box, set it back to not completed and reduce our score
		if(boardState[row][col] == 1) {
			boardState[row][col] = Board2.BLANK_SPACE;
			myscore = myscore - 1;
			return true;

		// if box is worth -1 it was opponent box, set it back to not compelted and reduce opponent score
		} else if(boardState[row][col] == -1) {
			boardState[row][col] = Board2.BLANK_SPACE;
			otherscore = otherscore - 1;
			return true;
		}

		// if it was not worth 1 or -1 it was never complete, return false
		return false;
	}

	public int maxLinesOnBox() {
		int[] last = madeMoves.getLast();
		//check if vertical or horisontal
		if(last[0] % 2 == 0){
			//check if bottom or top
			if(last[0] == 0){
				return getLines(last[0]+1,last[1]);
			}
			else if(last[0] == 18){
				return getLines(last[0]-1,last[1]);
			}
			else{
				return Math.max(getLines(last[0]+1,last[1]), getLines(last[0]-1,last[1]));
			}
		}
		else{
			//check if left or right
			if(last[1] == 0){
				return getLines(last[0],last[1]+1);
			}
			else if(last[1] == 18){
				return getLines(last[0],last[1]-1);
			}
			else{
				return Math.max(getLines(last[0],last[1]+1), getLines(last[0],last[1]-1));
			}
		}
	}

	// function to get the number of completed lines of a box where i, j is the location of the center of the box in the 19 x 19 matrix
	// returns the number of completed lines
	private int getLines(int i, int j) {
		int countCompleted = 0;
		if(boardState[i+1][j] == COMPLETED_LINE) countCompleted++;
		if(boardState[i-1][j] == COMPLETED_LINE) countCompleted++;
		if(boardState[i][j+1] == COMPLETED_LINE) countCompleted++;
		if(boardState[i][j-1] == COMPLETED_LINE) countCompleted++;
		return countCompleted;
	}

	
	public List<int[]> sortMoves(List<int[]> moveList) {
        if (moveList.size() > 40) {
            // Create a custom comparator to sort boards by numLinesOnBox in descending order
			Comparator<int[]> comparator = (board1, board2) -> {
        	int priority1 = getPriority(board1);
        	int priority2 = getPriority(board2);
        	return Integer.compare(priority2, priority1);
    	};
            Collections.sort(moveList, comparator);

            return moveList.subList(0, 40);
        } else {
            return moveList;
        }
    }

	private int getPriority(int[] move) {
		completeMove(move[0], move[1]);
		int maxLinesOnBox = maxLinesOnBox();
		undoMove(move[0], move[1]);

		if (maxLinesOnBox == 3) {
			return 0; // Highest priority for 3's
		} else if (maxLinesOnBox == 1) {
			return 1; // Next priority for 1's
		} else if (maxLinesOnBox == 0) {
			return 2; // Priority for 0's
		} else {
			return 3; // Lowest priority for 2's
		}
		
	}
}