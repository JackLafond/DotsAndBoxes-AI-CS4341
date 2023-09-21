import java.util.ArrayList;

public class Node {

	Board board;
	Node parent;
	int depth;
	ArrayList<Node> children;

    Node (Board board) {
		this.board = board;
	}

	public void setParent (Node parent) {
        this.parent = parent;
    }

	public void addChild(Node current) {
    	this.children.add(current);
    }
}