package ru.gb.Lesson2;

import java.time.LocalDateTime;

public class Ticket {
    private String number;
    private LocalDateTime createdAt;

    public Ticket(String number) {
        this.number = number;
        createdAt = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "number='" + number + '\'' +
                ", dateTime=" + createdAt +
                '}';
    }
}
