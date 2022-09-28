import java.io.BufferedReader;
import java.io.InputStreamReader;

class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int element;
        int count = 0;
        int prevElement;
        element = reader.read();
        if(element == 32) {
            count = count - 1;
        }
        prevElement = element;
        while(element != -1) {
            if(prevElement == 32 && element != 32) {
                count++;
            }
            //System.out.println(element);
            prevElement = element;
            element = reader.read();

        }
        reader.close();
        System.out.println(count+1);
    }
}