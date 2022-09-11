package com.github.LoranShare.arm_telegram_bot.command;

import com.github.LoranShare.arm_telegram_bot.service.SendBotMessageService;
import lombok.Getter;
import lombok.NonNull;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Arrays;
import java.util.List;

public class UnknownCommand implements Command {

    public UnknownCommand(SendBotMessageService service) {
        this.service = service;
    }

    private final SendBotMessageService service;

    @Getter
    private final static List<String> ANSWER = Arrays.asList("Меня еще не научили регировать на такие сложные команды. " +
            "Может тебе стоит выбрать что-то из этого списка??", "👇");

    @Override
    public void execute(Update update) {
        service.sendMessage(update.getMessage().getChatId(), ANSWER);
    }
}
