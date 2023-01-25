package demo;

import com.bring.annotation.Bean;

@Bean("myPrinter")
public class Printer implements Notifier {

    public void myNotify(String message) {
        System.out.println(message + " from Printer");
    }
}
