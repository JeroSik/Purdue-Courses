import java.io.*;

public class FileWrite {
  public static void sequentialWrite() throws Exception{
    File f = new File("sampleWrite.txt");
    FileWriter fw = new FileWriter(f);

    fw.write("Hello World!");
    fw.close();

    FileInputStream fis = new FileInputStream(f);
    BufferedReader bfr = new BufferedReader(new InputStreamReader(fis));

    System.out.println("Reading sampleWrite.txt");
    System.out.println(bfr.readLine());

    bfr.close();
    //f.delete();
  }

  public static void randomWrite() throws Exception {
    File f = new File("sampleRandomWrite.txt");
    RandomAccessFile rAccFile = new RandomAccessFile(f, "rw");
    System.out.println("Before writing:");
    String read = rAccFile.readLine();
    System.out.println(read);
    rAccFile.seek(22);	//moves the offset to the 22nd byte
    rAccFile.writeChars("amazing!");	//This just replaces characters
    //from 22nd byte to 30th byte
    rAccFile.seek(0);	//moves back to the start of the file
    read = rAccFile.readLine();

    System.out.println("After writing:");
    System.out.println(read);
    rAccFile.close();
  }

  public static void main(String[] args) throws Exception {
    System.out.println("Sequential Writing...");
    sequentialWrite();
    System.out.println("\nRandom Write...");
    randomWrite();
  }
}