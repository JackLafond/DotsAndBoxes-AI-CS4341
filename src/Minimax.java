import java.util.ArrayList;
import java.util.List;

//This class handles the AI movements
public class Minimax {
	Board b;

	Minimax (Board b) {
		this.b = b;
	}

	public Board move () {
		Node current = new Node(b);
		Node minimax = search(current, 0);
		Node move = getMove(minimax);
		return move.board;
	}

	public Node search (Node current, int depth) {
		if (depth > depth){
			return current;
		}

		boolean ai = false;
		if (depth % 2 == 0) {
			ai = false;
		}

		List<Node> children = getSuccessors(current, ai);
		Node tempNode = null;

		if (current.board.isAIMove()) {
			Integer value = Integer.MIN_VALUE;

			for (Node child : children) {
				Node x = search(child, depth + 1);
				if (x.board.evaluate() > value) {
					tempNode = x;
					value = x.board.evaluate();
				}
			}
			return tempNode;
		}
		else {
			Integer value = Integer.MAX_VALUE;
			for (Node child : children) {
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