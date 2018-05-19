import java.io.*;

public class MatrixOps {
    public static void mAddition(String matrixAfileName, String matrixBfileName) throws Exception {
        FileReader frA = new FileReader(matrixAfileName);
        BufferedReader bfrA = new BufferedReader(frA);
        FileReader frB = new FileReader(matrixBfileName);
        BufferedReader bfrB = new BufferedReader(frB);

        int[][] matrixA = new int[4][4];
        int[][] matrixB = new int[4][4];
        int[][] matrixC = new int[4][4];

        String[] lineA; //= bfrA.readLine().split(" ");
        String[] lineB; //= bfrB.readLine().split(" ");
        for (int i = 0; i < 4; i++) {
            lineA = bfrA.readLine().split(" ");
            lineB = bfrB.readLine().split(" ");
            for (int j = 0; j < 4; j++) {
                matrixA[i][j] = Integer.parseInt(lineA[j]);
                matrixB[i][j] = Integer.parseInt(lineB[j]);
            }
        }
        bfrA.close();
        bfrB.close();

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                matrixC[i][j] = matrixA[i][j] + matrixB[i][j];
            }
        }

        File f = new File("add.txt");
        FileWriter fw = new FileWriter(f);

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                fw.write(matrixC[i][j] + " ");
            }
            fw.write("\n");
        }

        fw.close();
    }

    public static void mMultiply(String matrixAfileName, int x) throws Exception {
        FileReader frA = new FileReader(matrixAfileName);
        BufferedReader bfrA = new BufferedReader(frA);

        int[][] matrixA = new int[4][4];
        int[][] matrixB = new int[4][4];

        String[] lineA; //= bfrA.readLine().split(" ");
        for (int i = 0; i < 4; i++) {
            lineA = bfrA.readLine().split(" ");
            for (int j = 0; j < 4; j++) {
                matrixA[i][j] = Integer.parseInt(lineA[j]);
            }
        }
        bfrA.close();

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                matrixB[i][j] = matrixA[i][j] * x;
            }
        }

        File f = new File("mult.txt");
        FileWriter fw = new FileWriter(f);

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                fw.write(matrixB[i][j] + " ");
            }
            fw.write("\n");
        }

        fw.close();
    }

    public static void main(String[] args) throws Exception{
        MatrixOps test = new MatrixOps();
        test.mAddition("matrix1.txt", "matrix2.txt");
        test.mMultiply("matrix2.txt", 10);
    }
}
