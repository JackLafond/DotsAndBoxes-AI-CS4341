import java.util.ArrayList;
import java.util.List;

//This class handles the AI movements
public class Minimax {

	Board b;

	Minimax (Board b, int depth) {
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

		//Checks if we are at the max depth/ply, or if board is complete
		if (depth > depth || current.board.totallines == current.board.maxlines) {
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
				if (x.board.difference > value) {
					tempNode = x;
					value = x.board.difference;
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
				if (x.board.difference < value) {
					tempNode = x;
					value = x.board.difference;
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