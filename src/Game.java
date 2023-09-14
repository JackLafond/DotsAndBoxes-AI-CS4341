import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;

public class Game {
    private HashMap<String, Line> lineHashMap;

    public static final String moveFile = "move_file";
    public static final String goFile = "groupname.go";
    public static final String passFile = "groupname.pass";
    public static final String endFile = "end_game";

    public Game() {

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


    public void init() {
        this.lineHashMap = new HashMap<>();
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


}


// Create Initialization Function
// Create HashMap of Lines
// Or Make it actually an array of 180, x + y*10
// Look into Minimax algo
// Baseline of dealing with file in and out