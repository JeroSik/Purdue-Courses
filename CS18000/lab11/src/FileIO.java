import java.io.*;

public class FileIO {
    public static void printer(File f) throws Exception {
        FileReader fr = new FileReader(f);
        BufferedReader bfr = new BufferedReader(fr);

        String line = bfr.readLine();
        while(line != null) {
            System.out.println(line);
            line = bfr.readLine();
        }
        bfr.close();
    }

    public static void main(String[] args) throws Exception {
        File f = new File("output.txt");
        FileWriter fw = new FileWriter(f);

        fw.write("Hello World!\n");
        fw.write("CS 180 is awesome!");

        fw.close();

        FileInputStream fis = new FileInputStream(f);
        BufferedReader bfr = new BufferedReader(new InputStreamReader(fis));

        System.out.println(bfr.readLine());
        System.out.println(bfr.readLine());

        bfr.close();
    }
}
