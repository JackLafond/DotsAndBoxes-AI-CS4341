public class Test {
    public static void main(String[] args) {
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

        Board b = new Board(array, 0, 0, false);

        b.printboard();
    }
}
