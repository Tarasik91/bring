package demo;

import com.bring.annotation.Bean;

@Bean
public class WriterMessage implements Notifier{
    public void myNotify(String  message) {
        System.out.println(message +  " from WriterMessage");
    }
}
