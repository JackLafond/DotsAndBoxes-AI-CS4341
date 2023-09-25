public class Testing {

    public static void main(String args[]) {

        int arraySize=(9 * 2) + 1;

        int[][] array2 = new int[arraySize][arraySize];

        for (int i = 0; i < arraySize; i++) {
            for (int j = 0; j < arraySize; j++) {
                if (i % 2 == 0 && j % 2 ==0) {
                    array2[i][j] = 8;
                }
                else if(i % 2 == 0 && j % 2 != 0 || i % 2 != 0 && j % 2 == 0 ){
                    array2[i][j] = 2;
                }
                else array2[i][j] = 0;
            }
        }

        // testing minimax

        Board2 b2 = new Board2(array2, 0, 0, true);

        b2.completeMove(0, 1);
        b2.completeMove(1,0);
        b2.completeMove(5, 0);
        b2.completeMove(0, 17);
        b2.completeMove(1, 18);
        b2.completeMove(17, 0);
        b2.completeMove(18, 1);
        b2.completeMove(18, 17);
        b2.completeMove(17, 18);
        b2.completeMove(0, 5);
        b2.completeMove(9, 18);
        b2.completeMove(18, 9);
        b2.completeMove(1, 2);
        b2.myMove = false;
        b2.completeMove(1, 16);
        b2.completeMove(2, 17);
        b2.completeMove(3, 0);
        b2.completeMove(6, 1);
        b2.completeMove(3, 2);
        b2.completeMove(0, 3);
        b2.completeMove(2, 3);
        b2.completeMove(2, 5);
        b2.completeMove(2, 1);
        b2.completeMove(1, 4);
        b2.completeMove(5, 2);
        b2.completeMove(4,1);
        b2.completeMove(1, 6);
        //b2.completeMove(16,17);
        b2.completeMove(16,1);
        b2.completeMove(16, 3);
        b2.completeMove(18,3);
        //b2.myMove = false;
        b2.printboard();

        System.out.println("Board 2 eval: " + b2.evaluate());
        long startTime = System.currentTimeMillis();
        int[] move2 = Minimax2.getBestMove(b2);
        long endTime = System.currentTimeMillis();
        long totalTime = endTime-startTime;
        System.out.println("2 Best move is : " + move2[0] + ", " + move2[1] + " with an eval of : " + move2[2] + ", and a total time of: " + totalTime);


    }

}