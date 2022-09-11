package com.github.LoranShare.arm_telegram_bot.command;

import com.github.LoranShare.arm_telegram_bot.service.SendBotMessageService;
import lombok.Getter;
import lombok.NonNull;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Arrays;
import java.util.List;

public class StartCommand implements Command {

    public StartCommand(SendBotMessageService service) {
        this.service = service;
    }

    private final SendBotMessageService service;

    @Getter
    private final static List<String> ANSWER = Arrays.asList("Barev dzez \uD83D\uDC4B" + System.lineSeparator() +
            "Я, <b><i>Armman</i></b> \uD83C\uDDE6\uD83C\uDDF2, " + "виртуальный, но очень гордый и богатый опытом помощник. " + System.lineSeparator() +
            System.lineSeparator() + "Меня пока не научили ввести долгие диалоги, поэтому, <b><i>джан</i></b>," +
            "выбери что тебя интересует из меню." + System.lineSeparator(), "👇");

    @Override
    public void execute(Update update) {
        service.sendMessage(update.getMessage().getChatId(), ANSWER);
    }
}
