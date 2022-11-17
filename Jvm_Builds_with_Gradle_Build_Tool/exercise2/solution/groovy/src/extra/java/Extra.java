import org.joda.time.LocalTime;

public class Extra {

    public static void main(String[] argv) {
        System.out.println("Hello Extra! Time is " + LocalTime.now().toString());
        String msg = "Hello Extra! Time is " + LocalTime.now().toString();
        System.out.println(msg.indent(4));
    }
}