package demo;

import com.bring.annotation.Bean;

@Bean
public class MyBean implements Notifier {

    @Override
    public void myNotify(String message) {
        System.out.println(message + " from MyBean");
    }
}
