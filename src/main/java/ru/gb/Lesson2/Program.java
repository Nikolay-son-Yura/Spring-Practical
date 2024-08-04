package ru.gb.Lesson2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class Program {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Program.class, args);
        CallScreen callScreen = context.getBean(CallScreen.class);
        System.out.println("Билет №1 => " + callScreen.newTicket());
        System.out.println("Билет №2 => " + callScreen.newTicket());
        System.out.println("Билет №3 => " + callScreen.newTicket());

    }
}
