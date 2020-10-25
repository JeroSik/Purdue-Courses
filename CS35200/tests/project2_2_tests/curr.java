class Foo{
    public static void main(String[] args) {
        int row = 5, col = 10;
        String[][] test = new String[row][col];
        String[] replacement = new String[col];
        
        int i = 0, j = 0;
        while (i < test.length) {
            j = 0;
            while (j < test[i].length) {
                test[i][j] = "same";
                j = j + 1;
            }
            i = i + 1;
        }

        System.out.println("Initial array");
        i = 0;
        while (i < row) {
            j = 0;
            while (j < col) {
                System.out.print(test[i][j]);
                System.out.print(" ");

                j = j + 1;
            }
            System.out.println("");
            i = i + 1;
        }

        System.out.println("First Replacement");
        i = 0;
        while (i < col) {
            replacement[i] = "DIFF";
            i = i + 1;
        }
        test[3] = replacement;

        i = 0;
        while (i < row) {
            j = 0;
            while (j < col) {
                System.out.print(test[i][j]);
                System.out.print(" ");

                j = j + 1;
            }
            System.out.println("");
            i = i + 1;
        }

        System.out.println("Second Replacement");
        test[2] = new String[7];
        test[2][3] = "VERYDIFFERENT";

        i = 0;
        while (i < row) {
            j = 0;
            while (j < col) {
                System.out.print(test[i][j]);
                System.out.print(" ");

                j = j + 1;
            }
            System.out.println("");
            i = i + 1;
        }
    }
}