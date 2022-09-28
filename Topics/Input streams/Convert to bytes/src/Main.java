import java.io.InputStream;

class Main {
    public static void main(String[] args) throws Exception {
        InputStream inputStream = System.in;
        int streamElement;
        streamElement = inputStream.read();
        while (streamElement != -1) {
            System.out.print(streamElement);
            streamElement = inputStream.read();
        }
    }
}