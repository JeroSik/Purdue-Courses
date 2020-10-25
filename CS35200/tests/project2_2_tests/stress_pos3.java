class simplearray {
    public static void main(String [] args) {
        int i_size = 4, j_size=5;
        int[][] Arr2D = new int[i_size][j_size];
        int i=0, j=0;
        while (i < i_size) {
                System.out.print("i equals ");
                System.out.println(i);
           j = 0;
           while (j < j_size) {
                        Arr2D[i][j] = i+j;
                        j = j+1;
           }
           i = i + 1;
        }
        i=0;
        while (i < i_size) {
             j =0;
             while (j < j_size) {
                System.out.print("Arr2D[");
                System.out.print(i);
                System.out.print("][");
                System.out.print(j);
                System.out.print("]=");
                System.out.println(Arr2D[i][j]);
                j = j+2;
             }
             i = i + 2;
        }
    }
}
