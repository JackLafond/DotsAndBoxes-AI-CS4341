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


	public void updatescore (int row, int col, String direction) {
        
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

	public List<int[]> getLegalMoves() {

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

		return moves;
	}

	public boolean completeMove(int row, int col) {

		boardState[row][col] = Board2.COMPLETED_LINE;
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

		boardState[row][col] = Board2.EMPTY_LINE;
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
		if(boardState[row][col] == Board2.BLANK_SPACE) {
			int sum = boardState[row][col + 1] + boardState[row][col - 1] + boardState[row - 1][col] + boardState[row + 1][col];
			if(sum == 4 * Board2.COMPLETED_LINE) {
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
		if(boardState[row][col] == 1) {
			boardState[row][col] = Board2.BLANK_SPACE;
			aiscore = aiscore - 1;
			return true;
		} else if(boardState[row][col] == -1) {
			boardState[row][col] = Board2.BLANK_SPACE;
			playerscore = playerscore - 1;
			return true;
		}
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