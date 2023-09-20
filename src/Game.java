import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Game {

    public static void main(String[] args) 
    {
            System.out.println("Starting Game...");
            Game game = new Game();
            game.hs[1][1].setComplete(true);
            game.hs[0][0].setComplete(true);
            game.hs[4][6].setComplete(true);
            game.vs[1][1].setComplete(true);
            game.vs[0][0].setComplete(true);
            game.vs[6][6].setComplete(true);
            game.printBoard();
    }

    public Line[][] vs;
    public Line[][] hs;
    public Box[][] boxes;

    
    public Board board;



    public Game() {
        this.vs = new Line[10][9];
        this.hs = new Line[9][10];
        this.boxes = new Box[9][9];
        
        initLines();
        initBoxes();

    }

    private void printBoard(){
        for(int i = 0; i < 9; i++){
            for(int j = 0; j < 9; j++){
                System.out.print(".");
                if(hs[i][j].isComplete()) System.out.print("-------");
                else System.out.print("\t");
            }
            System.out.print("\n");
            if(i < 8){
                for(int h =0; h < 3; h++){
                    for(int horisontal=0; horisontal <9; horisontal++){
                        if(vs[i][horisontal].isComplete()) System.out.print("|\t");
                        else System.out.print(" \t");
                    }
                    System.out.print("\n");
                }
            }
        }
    }

    private void initBoxes() {

        for(int i = 0; i <= 8; i++) {
            for(int j = 0; j <= 8; j++) {
                this.boxes[j][i] = new Box(this.hs[j][i + 1], this.vs[j + 1][i], this.hs[j][i], this.vs[j][i]);
            }
        }
    }

    private void initLines() {

        for(int i = 0; i < 9; i++) {
            for(int j = 0; j < 10; j++) {
                this.vs[j][i] = new Line();
                this.hs[i][j] = new Line();
            }
        }
        
        this.board = new Board();

    }



}


// Create Initialization Function
// Create HashMap of Lines
// Or Make it actually an array of 180, x + y*10
// Look into Minimax algo
// Baseline of dealing with file in and out