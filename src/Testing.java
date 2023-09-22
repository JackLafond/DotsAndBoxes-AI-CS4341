public class Testing {

    public static void main(String args[]) {

        int arraySize=(9 * 2) + 1;
        int[][] array = new int[arraySize][arraySize];

        for (int i = 0; i < arraySize; i++) {
            for (int j = 0; j < arraySize; j++) {
                if (i % 2 == 0 && j % 2 ==0) {
                    array[i][j] = 8;
                }
                else if(i % 2 == 0 && j % 2 != 0 || i % 2 != 0 && j % 2 == 0 ){
                    array[i][j] = 2;
                }
                else array[i][j] = 0;
            }
        }

        //SHOULD IA Move be false here??
        Board b = new Board(array, 0, 0, true);

        b.completeLine(0, 1);
        b.completeLine(1,0);
        b.completeLine(5, 0);
        b.completeLine(0, 17);
        b.completeLine(1, 18);
        b.completeLine(17, 0);
        b.completeLine(18, 1);
        b.completeLine(18, 17);
        b.completeLine(17, 18);
        b.completeLine(0, 5);
        b.completeLine(9, 18);
        b.completeLine(18, 9);
        b.completeLine(1, 2);
        b.myMove = false;
        b.completeLine(1, 16);
        b.completeLine(2, 17);
        b.myMove = true;
        b.completeLine(3, 0);
        b.completeLine(6, 1);
        b.completeLine(3, 2);
        b.completeLine(0, 3);
        b.completeLine(2, 3);
        b.completeLine(2, 5);
        b.completeLine(2, 1);
        b.completeLine(1, 4);
        b.printboard();

        Minimax m = new Minimax();
        int[] move = m.getBestMove(b);
        System.out.println("Best move is : " + move[0] + ", " + move[1] + " with an eval of : " + move[2]);

    }

}