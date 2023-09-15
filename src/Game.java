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

    

    public static final String moveFile = "move_file";
    public static final String goFile = "groupname.go";
    public static final String passFile = "groupname.pass";
    public static final String endFile = "end_game";

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
    }

    /**
     * Find out if a specific file is present in the project directory
     *
     * @param fileName name of the file we are checking for
     * @return True if the file exists
     */
    public boolean isFilePresent(String fileName) {
        String dir = System.getProperty("user.dir");

        // Create a File object for the file you want to check
        File target = new File(dir, fileName);

        return target.exists();
    }

    /**
     * Provide the file to overwrite and the message to write as strings
     * @param fileName the name of the file
     * @param message the message to overwrite the file with
     * @throws IOException error if the file does not exist or is a directory
     */
    public void overwriteFile(String fileName, String message) throws IOException {
        if(!isFilePresent(fileName)){
            System.out.println("File not Present");
        }
        else{
            try {
                String dir = System.getProperty("user.dir");
                File target = new File(dir, fileName);
                FileWriter writer = new FileWriter(target, false);
                writer.write(message);
                writer.close();
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    /**
     * Get the contents of a file as a string
     * @param fileName File to read
     * @return String containing the file's message
     * @throws IOException Error if file DNE or is directory
     */
    public String fileContents(String fileName) throws IOException {
        try{
            return Files.readString(Path.of(fileName));
        } catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }


    /**
     * Fill the line hashmap with instances of lines
     * TODO: Fix this function so it properly populates
     */
    public void fillLineMap() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
//                lineHashMap.put("line"+i+j, new Line(i,j));
            }
        }
    }

    public int[] coordSanitization(String move){
        int[] coordVals = new int[4];

        String coords = move.substring(move.length() - 7);
        String coord1 = coords.substring(0,3);
        String coord2 = coords.substring(4);

        String[] parts1 = coord1.split(",");
        String[] parts2 = coord2.split(",");

        int x1 = Integer.parseInt(parts1[0]);
        int y1 = Integer.parseInt(parts1[1]);
        int x2 = Integer.parseInt(parts2[0]);
        int y2 = Integer.parseInt(parts2[1]);

        if(x2-x1 < 0 || y2-y1 < 0){
            coordVals[0] = x2;
            coordVals[1] = y2;
            coordVals[2] = x1;
            coordVals[3] = y1;
        } else{
            coordVals[0] = x1;
            coordVals[1] = y1;
            coordVals[2] = x2;
            coordVals[3] = y2;
        }

        return coordVals;
    }

}


// Create Initialization Function
// Create HashMap of Lines
// Or Make it actually an array of 180, x + y*10
// Look into Minimax algo
// Baseline of dealing with file in and out