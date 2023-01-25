package demo;

import com.bring.context.ApplicationContext;
import com.bring.context.ApplicationContextImpl;
import com.bring.exception.NoSuchBeanException;
import com.bring.exception.NoUniqueBeanException;

import java.util.Map;

public class Main {
    public static void main(String[] args) {
        ApplicationContext ac = new ApplicationContextImpl("demo");
        MyBean myBean = ac.getBean(MyBean.class);
        Printer printer = ac.getBean("myPrinter", Printer.class);

        notify(myBean, "Hello");
        notify(printer, "Good evening");

        Map<String, Notifier> notifiers = ac.getAllBeans(Notifier.class);

        notifiers.forEach((key, value) -> notify(value, "HI"));

        try {
            NotBean notBean = ac.getBean(NotBean.class);
        } catch (NoSuchBeanException e) {
            System.out.println("NoSuchBeanException was thrown");
        }

        try {
            Notifier notifierList = ac.getBean(Notifier.class);
        } catch (NoUniqueBeanException e) {
            System.out.println("NoUniqueBeanException was thrown");
        }

    }

    private static void notify(Notifier notifier, String message) {
        notifier.myNotify(message);
    }
}
