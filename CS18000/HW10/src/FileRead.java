import java.io.*;

public class FileRead {
  public static void sequentialRead() throws Exception {
    FileReader fr = new FileReader("sampleRead.txt");
    BufferedReader bfr = new BufferedReader(fr);
    String read = bfr.readLine();
    System.out.println(read);
    // You should always close your buffer after using it
    bfr.close();
  }

  public static void randomRead() throws Exception {
    File f = new File("sampleRead.txt");
    RandomAccessFile rAccFile = new RandomAccessFile(f, "rw"); //opens the file in
    //read/write mode

    rAccFile.seek(6);	//moves the offset of the file to the sixth byte
    String read = rAccFile.readLine(); //reads from the offset to the newline
    //character or end of file
    System.out.println(read);
    rAccFile.close();
  }

  public static void main(String[] args) throws Exception {
    System.out.println("Sequential Read:");
    sequentialRead();
    System.out.println("\nRandom Read:");
    randomRead();
  }
}