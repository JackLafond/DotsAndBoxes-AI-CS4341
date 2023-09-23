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

        // testing minimax

        Board2 b = new Board2(array, 0, 0, true);

        b.completeMove(0, 1);
        b.completeMove(1,0);
        b.completeMove(5, 0);
        b.completeMove(0, 17);
        b.completeMove(1, 18);
        b.completeMove(17, 0);
        b.completeMove(18, 1);
        b.completeMove(18, 17);
        b.completeMove(17, 18);
        b.completeMove(0, 5);
        b.completeMove(9, 18);
        b.completeMove(18, 9);
        b.completeMove(1, 2);
        b.myMove = false;
        b.completeMove(1, 16);
        b.completeMove(2, 17);
        b.myMove = true;
        b.completeMove(3, 0);
        b.completeMove(6, 1);
        b.completeMove(3, 2);
        b.completeMove(0, 3);
        b.completeMove(2, 3);
        b.completeMove(2, 5);
        b.completeMove(2, 1);
        b.completeMove(1, 4);
        b.completeMove(5, 2);
        b.printboard();
        System.out.println("Board eval: " + b.evaluate());
        System.out.println("Board Moves: " + b.madeMoves);
        Minimax2 m = new Minimax2();
        int[] move = m.getBestMove(b);
        System.out.println("Board eval: " + b.evaluate());
        System.out.println("Board Moves: " + b.madeMoves);
        System.out.println("Best move is : " + move[0] + ", " + move[1] + " with an eval of : " + move[2]);

        b.printboard();


    }

}