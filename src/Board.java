public class Board {
    
    public Line[][] vs;
    public Line[][] hs;
    public Box[][] boxes;
    public int[] lastLine;

    public Board() {

        this.vs = new Line[10][9];
        this.hs = new Line[9][10];
        this.boxes = new Box[9][9];
        this.lastLine = null;
        
        initBoxes();
        initLines();
    }

    public Board(Board board){
        this.vs = board.vs;
        this.hs = board.hs;
        this.boxes = board.boxes;
        this.lastLine = board.lastLine;
    }

    public void initBoxes() {

        for(int i = 0; i <= 8; i++) {
            for(int j = 0; j <= 8; j++) {
                this.boxes[j][i] = new Box(this.hs[j][i + 1], this.vs[j + 1][i], this.hs[j][i], this.vs[j][i]);
            }
        }
    }

    public void initLines() {

        for(int i = 0; i < 9; i++) {
            for(int j = 0; j < 10; j++) {
                this.vs[j][i] = new Line();
                this.hs[i][j] = new Line();
            }
        }
    }

    public void setLastLine(int[] lastLine) {
        this.lastLine = lastLine;
    }

    public Board copy() {
        return new Board(this);
    }
}
