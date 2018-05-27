package ru.aptech.library.enums;

public enum RoleType {
    ROLE_ADMIN("Администратор"),
    ROLE_USER("Пользователь");

    private String text;

    RoleType(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
