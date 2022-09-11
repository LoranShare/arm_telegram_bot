package com.github.LoranShare.arm_telegram_bot.service;

import com.github.LoranShare.arm_telegram_bot.bot.ArmTelegramBot;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

import static org.mockito.Mockito.times;

@DisplayName("Test of service what send text message")
@ExtendWith(MockitoExtension.class)
class SendBotMessageServiceImplTest {

    //@Mock
    private ArmTelegramBot armTelegramBot;

    // @InjectMocks
    private SendBotMessageService service;

    @BeforeEach
    void setUp() {
        armTelegramBot = Mockito.mock(ArmTelegramBot.class);
        service = new SendBotMessageServiceImpl(armTelegramBot);
    }

    @ParameterizedTest
    @CsvSource({
            "-1, '',",
            "20, Privet "
    })
    void sendMessageShouldBeEqual(Long chatId, String text) throws TelegramApiException {
        service.sendMessage(chatId, text);
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(text);
        message.enableHtml(true);
        Mockito.verify(armTelegramBot).execute(message);
    }

    @ParameterizedTest
    @CsvSource({
            " , Lojka,",
            "20,  ",
            ",",
    })
    void sendMessageShouldNullPointerException(Long chatId, String text) {
        Assertions.assertThrows(NullPointerException.class, () -> service.sendMessage(chatId, text));
    }

    @ParameterizedTest
    @MethodSource("generateArgument")
    void sendMessageListShouldBeCallsRequiredNumber(Long chatId, List<String> list) throws TelegramApiException {
        List<SendMessage> sourceVal = list.stream().map(val -> {
            SendMessage message = new SendMessage();
            message.setChatId(chatId);
            message.setText(val);
            message.enableHtml(true);
            return message;
        }).toList();

        service.sendMessage(chatId, list);
        ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
        Mockito.verify(armTelegramBot, times(list.size())).execute(argumentCaptor.capture());

        List<SendMessage> allValues = argumentCaptor.getAllValues();
        Assertions.assertEquals(allValues, sourceVal);
    }


    private static Stream<Arguments> generateArgument() {
        int size = new Random().nextInt(50) + 1;
        List<Arguments> args = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            args.add(Arguments.of(
                            new Random().nextLong(),
                            generateStringList(new Random().nextInt(200)))
                    //Arrays.asList("lol"))
            );
        }
        return args.stream();
    }

    private static List<String> generateStringList(int size) {
        List<String> listOfString = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            int lengthString = new Random().nextInt(50);
            byte[] array = new byte[lengthString];
            new Random().nextBytes(array);
            listOfString.add(new String(array, StandardCharsets.UTF_8));
        }
        return listOfString;
    }
}