public class Test {
    public static void main(String[] args) {
        int[][] array = new int[9][9];

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (i % 2 == 0) {
                    array[i][j] = 8;
                } else {
                    array[i][j] = 2;
                }
            }
        }

        Board b = new Board(array, 0, 0, false);

        b.printboard();
    }
}
