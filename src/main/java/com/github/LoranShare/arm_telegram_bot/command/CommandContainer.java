package com.github.LoranShare.arm_telegram_bot.command;

import com.github.LoranShare.arm_telegram_bot.service.SendBotMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class CommandContainer {

    private final Map<CommandName, Command> commands = new HashMap<>();
    private final Command unknownCommand;

    @Autowired
    public CommandContainer(SendBotMessageService service) {
        commands.put(CommandName.STOP, new StopCommand(service));
        commands.put(CommandName.START, new StartCommand(service));
        commands.put(CommandName.HELP, new HelpCommand(service));
        unknownCommand = new UnknownCommand(service);
    }

   /* @PostConstruct
    private void initContainer(){
        commands.put(CommandName.STOP, new StopCommand(service));
        commands.put(CommandName.START, new StartCommand(service));
        commands.put(CommandName.HELP, new HelpCommand(service));
        unknownCommand = new UnknownCommand(service);
    }*/

    public Command getCommand(String command) {
        return commands.getOrDefault(CommandName.getByValue(command), unknownCommand);
    }
}
