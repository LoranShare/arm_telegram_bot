package com.github.LoranShare.arm_telegram_bot.service;

import java.util.List;

public interface SendBotMessageService {
    void sendMessage(Long chatId, String text);

    void sendMessage(Long chatId, List<String> text);
}
