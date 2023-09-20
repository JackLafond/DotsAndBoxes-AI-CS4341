import java.util.LinkedList;

public class Minimax {
    
    public Minimax() {};

    public int[] getBestMove(Board b) {

        

        return new int[]{0,0,0,0};
    }

    public int search(Board b, int depth, boolean isMaxing, int alpha, int beta) {

        LinkedList<Board> children = getChildren(b);
        if(children.isEmpty()) {
            return evaluateBoard(b);
        }

        if(isMaxing) {
            int curBest = -999999;
            for(Board child : children) {
                int val = search(child, depth + 1, false, alpha, beta);
                curBest = Math.max(curBest, val);
                alpha = Math.max(curBest, alpha);
                if(alpha >= beta) {
                    break;
                }
            }
            return curBest;
        } else {
            int curBest = 999999;
            for(Board child : children) {
                int val = search(child, depth + 1, true, alpha, beta);
                curBest = Math.min(curBest, val);
                beta = Math.min(curBest, beta);
                if(beta <= alpha) {
                    break;
                }
            }
            return curBest;
        }
    }

    public int evaluateBoard(Board b) {

        int sum = 0;
        for(int i = 0; i < 9; i++) {
            for(Box aBox: b.boxes[i]) {
                sum = sum + aBox.completedBy;
                if(aBox.isOneLineAway()) {
                    sum = sum - 1;
                }
            }
        }
        return sum;
    }

    public LinkedList<Line> getMoves(Board b) {
        LinkedList<Line> moves = new LinkedList<Line>();
        for(int i = 0; i <= 9; i++) {
            for(int j = 0; j <= 10; j++) {
                if(!b.hs[j][i].isComplete()) {
                    moves.add(b.hs[j][i]);
                }
                if(!b.vs[i][j].isComplete()) {
                    moves.add(b.vs[i][j]);
                }
            }
        }
        return moves;

    }

    public LinkedList<Board> getChildren(Board b) {
        LinkedList<Board> children = new LinkedList<Board>();
        for(int i = 0; i <= 9; i++) {
            for(int j = 0; j <= 10; j++) {
                if(!b.hs[j][i].isComplete()) {
                    children.add(b);
                    children.getLast().hs[j][i].setComplete(true);
                }
                if(!b.vs[i][j].isComplete()) {
                    children.add(b);
                    children.getLast().vs[i][j].setComplete(true);
                }
            }
        }
        return children;

    }
}
