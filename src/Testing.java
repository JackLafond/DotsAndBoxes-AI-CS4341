public class Testing {

    public static void main(String args[]) {

        int arraySize=(9 * 2) + 1;
        int[][] array1 = new int[arraySize][arraySize];

        for (int i = 0; i < arraySize; i++) {
            for (int j = 0; j < arraySize; j++) {
                if (i % 2 == 0 && j % 2 ==0) {
                    array1[i][j] = 8;
                }
                else if(i % 2 == 0 && j % 2 != 0 || i % 2 != 0 && j % 2 == 0 ){
                    array1[i][j] = 2;
                }
                else array1[i][j] = 0;
            }
        }

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
        Board b1 = new Board(array1, 0, 0, true);

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
        b2.myMove = true;
        b2.completeMove(3, 0);
        b2.completeMove(6, 1);
        b2.completeMove(3, 2);
        b2.completeMove(0, 3);
        b2.completeMove(2, 3);
        b2.completeMove(2, 5);
        b2.completeMove(2, 1);
        b2.completeMove(1, 4);
        b2.completeMove(5, 2);
        b2.completeMove(1,6);
        b2.completeMove(4,1);
        b2.printboard();

        b1.completeLine(0, 1);
        b1.completeLine(1,0);
        b1.completeLine(5, 0);
        b1.completeLine(0, 17);
        b1.completeLine(1, 18);
        b1.completeLine(17, 0);
        b1.completeLine(18, 1);
        b1.completeLine(18, 17);
        b1.completeLine(17, 18);
        b1.completeLine(0, 5);
        b1.completeLine(9, 18);
        b1.completeLine(18, 9);
        b1.completeLine(1, 2);
        b1.myMove = false;
        b1.completeLine(1, 16);
        b1.completeLine(2, 17);
        b1.myMove = true;
        b1.completeLine(3, 0);
        b1.completeLine(6, 1);
        b1.completeLine(3, 2);
        b1.completeLine(0, 3);
        b1.completeLine(2, 3);
        b1.completeLine(2, 5);
        b1.completeLine(2, 1);
        b1.completeLine(1, 4);
        b1.completeLine(5, 2);
        b1.completeLine(1, 6);
        b1.completeLine(4, 1);
        b1.printboard();

        System.out.println("Board 1 eval: " + b1.evaluate());
        Minimax m1 = new Minimax();
        int[] move1 = m1.getBestMove(b1);
        System.out.println("2 Best move is : " + move1[0] + ", " + move1[1] + " with an eval of : " + move1[2]);

        System.out.println("Board 2 eval: " + b2.evaluate());
        Minimax2 m2 = new Minimax2();
        int[] move2 = m2.getBestMove(b2);
        System.out.println("2 Best move is : " + move2[0] + ", " + move2[1] + " with an eval of : " + move2[2]);


    }

}