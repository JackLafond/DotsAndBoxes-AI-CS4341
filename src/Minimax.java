import java.util.LinkedList;

public class Minimax {
    
    public Minimax() {};

    public int[] search(Board b) {

        


        return new int[]{0,0,0,0};
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
}
