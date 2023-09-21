import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.*;

public class Gameplay {

    public static final String moveFile = "move_file";
    public static final String goFile = "dannydevito.go";
    public static final String passFile = "dannydevito.pass";
    public static final String endFile = "end_game";

    public static final Path directoryPath = Path.of("C:\\Users\\Aidan\\Desktop\\Intro to AI\\dots_boxes_referee\\dots_boxes_referee");

    //Main function that is while loop that handles all gameplay
    public static void main(String[] args) throws IOException, InterruptedException {
        boolean gameRunning = true;
        Path dir = Paths.get(System.getProperty("user.dir")); //Can manually change Directory as necessary
        dir = directoryPath;
        //-----------------------------------------
        System.out.println(dir);
        //-----------------------------------------

        WatchService watchService = FileSystems.getDefault().newWatchService();

        dir.register(watchService, StandardWatchEventKinds.ENTRY_CREATE);

        Board gameBoard = new Board();

        while(gameRunning){
            WatchKey key = watchService.take();
            for (WatchEvent<?> event : key.pollEvents()) {
                if (event.kind() == StandardWatchEventKinds.ENTRY_CREATE) {
                    if(isFilePresent(endFile)){
                        //Game is Over
                        String endfileContents = fileContents(endFile);
                        System.out.println("game over");
                        System.out.println(endfileContents);
                        gameRunning = false;
                    } else{
                        Path filePath = (Path) event.context();
                        String fileName = filePath.getFileName().toString();

                        if(fileName.equals(goFile)){
                            //TODO: Make a move here

                            //-----------------------------------------
                            System.out.println("GO File Detected!");
                            //-----------------------------------------

                            //Log opponents Move info here, make into a single func (used 2x)
                            String oppMove = fileContents(moveFile);
                            //oppCoords is currently an int array size 2 that holds the relevant
                            //board array x and y values to be changed
                            int[] oppCoords = getStateCoordinates(oppMove);

                            //TODO: update saveMove for new data structure
//                            saveMove(gameBoard, oppCoords, -1);
                            //TODO: make update state func in Board
                            gameBoard.updateState(oppCoords[0], oppCoords[1], oppCoords[2], player);

                            //TODO: Calculate Move
                            System.out.println("calculating move");
                            int[] moveVals = Minimax.getBestMove(gameBoard);

                            String ourMove = "dannydevito " + moveVals[0] + "," + moveVals[1] + " " + moveVals[2] + "," + moveVals[3];
                            int[] ourCoords = getStateCoordinates(ourMove);

                            //TODO: make update state func in Board
                            gameBoard.updateState(oppCoords[0], oppCoords[1], oppCoords[2], player);
//                            saveMove(gameBoard, moveVals, 1);


                            //Write to moveFile to end turn
                            overwriteFile(moveFile, ourMove);

                            System.out.println("sending move");

                        }
                        else if (fileName.equals(passFile)){

                            //Pass here, make empty move

                            //-----------------------------------------
                            System.out.println("PASS File Detected!\n");
                            //-----------------------------------------

                            String oppMove = fileContents(moveFile);
                            int[] oppCoords = getStateCoordinates(oppMove);
                            gameBoard.updateState(oppCoords[0], oppCoords[1], oppCoords[2], player);
//                            saveMove(gameBoard, oppCoords, -1);

                            //Write Empty move to moveFile
                            String passMove = "dannydevito 0,0 0,0";
                            overwriteFile(moveFile, passMove);

                        }
                    }
                }
            }
            gameBoard.printboard();
            // Reset the key
            boolean valid = key.reset();
            if (!valid) {
                // Key is no longer valid, exit the loop
                break;
            }

        }

    }

    /**
     * Find out if a specific file is present in the project directory
     *
     * @param fileName name of the file we are checking for
     * @return True if the file exists
     */
    public static boolean isFilePresent(String fileName) {
        String dir = System.getProperty("user.dir");
        dir = directoryPath.toString();
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
    public static void overwriteFile(String fileName, String message) throws IOException {
        if(!isFilePresent(fileName)){
            System.out.println("File not Present");
        }
        else{
            try {
                String dir = System.getProperty("user.dir");
                dir = directoryPath.toString();
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
    public static String fileContents(String fileName) throws IOException {
        try{
            return Files.readString(Path.of(fileName));
        } catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }


    /**
     * Gives the coordinates of our game array to update the line of
     * @param move the string given by the move_file
     * @return an int array of size 3 with the x and y value of the board array to update and the direction
     */
    public static int[] getStateCoordinates(String move){
        int[] arrayCoordsToUpdate = new int[3];

        String coords = move.substring(move.length() - 7);
        String coord1 = coords.substring(0,3);
        String coord2 = coords.substring(4);

        String[] parts1 = coord1.split(",");
        String[] parts2 = coord2.split(",");

        int x1 = Integer.parseInt(parts1[0]);
        int y1 = Integer.parseInt(parts1[1]);
        int x2 = Integer.parseInt(parts2[0]);
        int y2 = Integer.parseInt(parts2[1]);

        //Ensure we have bottom left coord
        if(x2-x1 < 0 || y2-y1 < 0){
            arrayCoordsToUpdate[0] = x2;
            arrayCoordsToUpdate[1] = y2;

        } else{
            arrayCoordsToUpdate[0] = x1;
            arrayCoordsToUpdate[1] = y1;
        }
        if(x2-x1 != 0){
            //Horizontal Line, add to x val
            arrayCoordsToUpdate[0]++;
            arrayCoordsToUpdate[2] = 0;
        } else {
            //Vert Line, add to Y
            arrayCoordsToUpdate[1]++;
            arrayCoordsToUpdate[2] = 1;
        }

        return arrayCoordsToUpdate;
    }



}
