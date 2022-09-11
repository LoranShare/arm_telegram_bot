package com.github.LoranShare.arm_telegram_bot.command;

public enum CommandName {
    START("/start", "Barev, Armman джан"),
    STOP("/stop", "Пока, Armman джан"),
    HELP("/help", "Armman, расскажи о себе");

    private final String name;
    private final String description;

    CommandName(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getValue() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public static CommandName getByValue(String value) {
        for (var element : CommandName.values()) {
            if (element.getValue().equals(value)) return element;
        }
        return null;
    }
}
