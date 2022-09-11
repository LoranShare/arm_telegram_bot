package com.github.LoranShare.arm_telegram_bot.command;

import com.github.LoranShare.arm_telegram_bot.service.SendBotMessageService;
import lombok.Getter;
import lombok.NonNull;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Arrays;
import java.util.List;

public class StopCommand implements Command {

    public StopCommand(SendBotMessageService service) {
        this.service = service;
    }

    private final SendBotMessageService service;

    @Getter
    private final static List<String> ANSWER = Arrays.asList("Джан, мне очень грустно с тобой расставаться, но ты " +
            "не забывай, заходи. <b>Сtesutyun</b>, дорогой", "\uD83D\uDC4B");

    @Override
    public void execute(Update update) {
        service.sendMessage(update.getMessage().getChatId(), ANSWER);
    }
}
