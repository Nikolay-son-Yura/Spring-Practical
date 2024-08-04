package ru.gb.Lesson8.model;




public enum Roles {

    ADMIN("admin"), USER("user"), REST("rest");

    private final String name;

    Roles(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
