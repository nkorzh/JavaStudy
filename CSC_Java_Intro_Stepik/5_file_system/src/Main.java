import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Main {

    public static int checkSumOfStream(InputStream inputStream) throws IOException {
        int sum = 0;
        int data = inputStream.read();

        while (data != -1) {
            sum = Integer.rotateLeft(sum, 1) ^ data;
            data = inputStream.read();
        }
        return sum;
    }

    protected static void convertLineBreak(InputStream inputStream, OutputStream outputStream) throws IOException {
        int last = inputStream.read();
        int newByte = 1;

        if (last != -1)
            newByte = inputStream.read();

        while (last != -1) {
            if (last != 13 || newByte != 10)
                outputStream.write(last);
            last = newByte;
            newByte = inputStream.read();
        }
        outputStream.flush();
    }

    public static void main(String[] args) {
        try {
            convertLineBreak(System.in, System.out);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
