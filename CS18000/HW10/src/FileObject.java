import java.io.*;
import java.nio.file.*;     // For Path and Paths class
import static java.nio.file.Files.newOutputStream;
import static java.nio.file.StandardOpenOption.*;

public class FileObject {
  public void fObjOne()  throws Exception{
    // Creates a new File instance by converting the given pathname string "sample.txt".
    // into an abstract pathname. If the pathname argument is null, NullPointerException is thrown.
    File f = new File("sample.txt");

    // Creates a FileInputStream by opening a connection to the actual file f
    // If the named file does not exist, is a directory rather than a regular file,
    // or for some other reason cannot be opened for reading then a FileNotFoundException is thrown.
    FileInputStream fis = new FileInputStream(f);
  }
  public void fObjTwo() throws Exception{
    File f  = new File("sample.txt");

    if (f.exists() && !f.isDirectory()) {
      FileInputStream fis = new FileInputStream(f);
    } else {
      System.out.println("File is not there or it is a directory");
    }
  }

  public void fileCreateOne(){
    Path file = Paths.get("sampleOne.txt");
    try {
      // Create the empty file with default permissions
      Files.createFile(file);
    } catch (FileAlreadyExistsException x) {
      System.err.format("file named %s" +
              " already exists%n", file);
    } catch (IOException x) {
      // Some other sort of failure, such as permissions.
      System.err.format("createFile error: %s%n", x);
    }
  }
  public void fileCreateTwo()  throws Exception{
    Path file = Paths.get("sampleTwo.txt");

    // truncate and overwrite an existing file, or create the file if
    // it doesn't initially exist
    OutputStream out = newOutputStream(file);

    // append to an existing file, fail if the file does not exist
    out = newOutputStream(file, APPEND);

    // append to an existing file, create file if it doesn't initially exist
    out = newOutputStream(file, CREATE, APPEND);

    // always create new file, failing if it already exists
    out = newOutputStream(file, CREATE_NEW);
  }

  public static void main(String[] args) throws Exception{
    FileObject object = new FileObject();
    object.fObjOne();
    object.fObjTwo();

    System.out.println("Creating File");
    object.fileCreateOne();
    object.fileCreateTwo();
  }
}