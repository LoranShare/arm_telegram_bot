package com.github.LoranShare.arm_telegram_bot.service;

import com.github.LoranShare.arm_telegram_bot.bot.ArmTelegramBot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

@Service
public class SendBotMessageServiceImpl implements SendBotMessageService {

    private final ArmTelegramBot armBot;

    @Autowired
    public SendBotMessageServiceImpl(ArmTelegramBot armBot) {
        this.armBot = armBot;
    }

    @Override
    public void sendMessage(Long chatId, String text) {
        SendMessage botMessage = new SendMessage();
        botMessage.enableHtml(true);
        botMessage.setChatId(chatId);
        botMessage.setText(text);
        try {
            armBot.execute(botMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendMessage(Long chatId, List<String> text) {
        for (String str : text) {
            sendMessage(chatId, str);
        }
    }
}
