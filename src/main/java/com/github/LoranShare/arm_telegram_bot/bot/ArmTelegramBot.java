package com.github.LoranShare.arm_telegram_bot.bot;


import com.github.LoranShare.arm_telegram_bot.command.CommandContainer;
import com.github.LoranShare.arm_telegram_bot.command.CommandName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;


@Component
public class ArmTelegramBot extends TelegramLongPollingBot {
    private static final String COMMAND_LEAD_SYMBOL = "/";

    @Autowired
    @Lazy
    private CommandContainer commandContainer;

    @Value("${armBot.name}")
    private String botName;

    @Value("${armBot.token}")
    private String botToken;

    @PostConstruct
    public void init() {
        try {
            List<BotCommand> botCommands = Arrays.stream(CommandName
                            .values())
                    .map(val -> BotCommand.builder().command(val.getValue()).description(val.getDescription()).build())
                    .toList();

            SetMyCommands myCommands = SetMyCommands.builder()
                    .clearCommands()
                    .scope(BotCommandScopeDefault.builder().build())
                    .build();

            myCommands.setCommands(botCommands);
            execute(myCommands);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String textOfCmd = update.getMessage()
                    .getText()
                    .trim()
                    .split(" ")[0]
                    .toLowerCase();
            commandContainer.getCommand(textOfCmd).execute(update);
        }

    }

    @Override
    public String getBotUsername() {
        return botName;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }
}
