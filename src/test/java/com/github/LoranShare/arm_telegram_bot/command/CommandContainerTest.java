package com.github.LoranShare.arm_telegram_bot.command;

import com.github.LoranShare.arm_telegram_bot.service.SendBotMessageServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

@DisplayName("Test of command container")
@ExtendWith(MockitoExtension.class)
class CommandContainerTest {

    private SendBotMessageServiceImpl service;
    private Message message;
    private CommandContainer container;
    private Update update;

    @BeforeEach
    void setUp() {
        message = Mockito.mock(Message.class);
        update = Mockito.mock(Update.class);
        service = Mockito.mock(SendBotMessageServiceImpl.class);
        container = new CommandContainer(service);
    }

    @ParameterizedTest
    @MethodSource("getProperlyArguments")
    void getCommand(String strCommand, List<String> comparedStrings, Long chatId) {
        Mockito.when(message.getChatId()).thenReturn(chatId);
        Mockito.when(update.getMessage()).thenReturn(message);
        container.getCommand(strCommand).execute(update);
        Mockito.verify(service).sendMessage(chatId, comparedStrings);
    }


    private static Stream<Arguments> getProperlyArguments() {
        long chatId = new Random().nextLong();
        Stream.Builder<Arguments> builder = Stream.builder();
        return builder
                .add(Arguments.of(CommandName.START.getValue(), StartCommand.getANSWER(), chatId))
                .add(Arguments.of(CommandName.STOP.getValue(), StopCommand.getANSWER(), chatId))
                .add(Arguments.of(CommandName.HELP.getValue(), HelpCommand.getANSWER(), chatId))
                .add(Arguments.of("", UnknownCommand.getANSWER(), chatId))
                .add(Arguments.of("dsfsdfs dfsdfds", UnknownCommand.getANSWER(), chatId))
                .add(Arguments.of("/sstop", UnknownCommand.getANSWER(), chatId))
                .add(Arguments.of("/stop ", UnknownCommand.getANSWER(), chatId))
                .add(Arguments.of(" /stop ", UnknownCommand.getANSWER(), chatId))
                .add(Arguments.of("/Stop ", UnknownCommand.getANSWER(), chatId)).build();

    }
}