import java.util.ArrayList;
import java.util.List;
import java.util.LinkedList;

//This class handles the AI movements
public class Minimax {

	Board b;

	Minimax (Board b) {
		this.b = b;
		this.b.aimove = true;
	}

	//Move is called from main to initiate AI Move
	public Board move () {
		//Grabs current board
		Node current = new Node(b);
		//Initiates minimax search with current board and specified depth
		Node minimax = search(current, 0);
		//Traverses back up the tree to find the move to make
		Node move = getMove(minimax);
		//Returns the board after move has been made
		return move.board;
	}

	//Recursive Minimax Search, returns Node at max depth
	public Node search (Node current, int depth) {
		if (depth > depth){
			return current;
		}

		boolean ai = false;

		//At every other depth moves alternate between Player and AI
		if (depth % 2 == 0) {
			ai = false;
		}

		//Gets successors of current node (possible moves)
		List<Node> children = getSuccessors(current, ai);

		Node tempNode = null;

		if (current.board.isAIMove()) {
			Integer value = Integer.MIN_VALUE;

			for (Node child : children) {

				//Runs evaluation function
				child.board.evaluate();

				//Recurses down tree until max depth is reached for a child, then does comparisons
				Node x = search(child, depth + 1);
				if (x.board.evaluate() > value) {
					tempNode = x;
					value = x.board.evaluate();
				}
			}
			return tempNode;
		}

		//If its Player's depth/move, find the min node based on the difference in scores
		else {

			Integer value = Integer.MAX_VALUE;
			for (Node child : children) {
                
				//Runs evaluation function
				child.board.evaluate();

				//Recurses down tree until max depth is reached for a child, then does comparisons
				Node x = search(child, depth + 1);
				if (x.board.evaluate() < value) {
					tempNode = x;
					value = x.board.evaluate();
				}
			}
			return tempNode;
		}

	}

	public Node getMove (Node current) {
		Node tempNode = current;

		while (tempNode.parent.parent != null) {
			tempNode = tempNode.parent;
		}
		return tempNode;
	}

	public List<Node> getSuccessors(Node state, boolean value) {

		List<Node> children = new ArrayList<>();
		Board x = state.board;
		int rows = x.rows;
		int cols = x.cols;

		int[][] board = x.getState();

		for (int i = 0; i < rows; i ++) {
			for (int j = 0; j < cols; j++) {
				if ((i % 2 == 0 && j % 2 != 0) && board[i][j] == 7) {
					int[][] temp = copyArray(board, rows, cols);
					temp[i][j] = 9;
					Board tmp = new Board(temp, x.rows, x.cols, x.playerscore, x.aiscore, value, x.totallines, x.maxlines);
					tmp.updatescore(i, j, "horizontal");
					tmp.totallines++;
					Node child = new Node(tmp);
					child.setParent(state);
					children.add(child);
				}
				else if ((i % 2 != 0 && j % 2 == 0) && board[i][j] == 7) {
					int[][] temp = copyArray(board, rows, cols);
					temp[i][j] = 11;
					Board tmp = new Board(temp, x.rows, x.cols, x.playerscore, x.aiscore, value, x.totallines, x.maxlines);
					tmp.updatescore(i, j, "vertical");
					tmp.totallines++;
					Node child = new Node(tmp);
					child.setParent(state);
					children.add(child);
				}
			}
		}
		return children;
	}

    public List<Node> getChildren(Node cur) {
        LinkedList<Node> children = new LinkedList<Node>();
        int[][] curState = cur.board.getState();
        for(int row = 0; row < 19; row++) {
            for(int col = 0; col < 19; col = col + 2) {
                if(row % 2 == 0) {
                    col = col + 1;
                }
                if(curState[row][col] == Board.EMPTY_LINE) {
                    int[][] newState = copyArray(curState, curState.length, curState.length);
                    newState[row][col] = Board.COMPLETED_LINE;
                    Board newB = new Board(newState, cur.board.playerscore, cur.board.aiscore);
                    Node newN = new Node(newB);
                    newN.setParent(cur);
                    children.add(newN);
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