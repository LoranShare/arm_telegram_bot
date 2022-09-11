package com.github.LoranShare.arm_telegram_bot.command;

import com.github.LoranShare.arm_telegram_bot.service.SendBotMessageService;
import lombok.Getter;
import lombok.NonNull;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Arrays;
import java.util.List;

public class HelpCommand implements Command {

    public HelpCommand(SendBotMessageService service) {
        this.service = service;
    }

    private final SendBotMessageService service;

    @Getter
    private final static List<String> ANSWER = Arrays.asList("Ты еще не разобрался, как со мной общаться? " +
            "Я тебя быстро научу. Будь смелее! Нажми на кнопку и выбери то, что твоему глазу понравится", "👇");

    @Override
    public void execute(Update update) {
        service.sendMessage(update.getMessage().getChatId(), ANSWER);
    }
}
