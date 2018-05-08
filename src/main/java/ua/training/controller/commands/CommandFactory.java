package ua.training.controller.commands;

import ua.training.controller.commands.redirect.AdminPageCommand;
import ua.training.controller.commands.redirect.ClientPageCommand;
import ua.training.controller.commands.redirect.LoginPageCommand;
import ua.training.controller.commands.redirect.RegistrationPageCommand;
import ua.training.constants.Commands;
import ua.training.constants.Pages;
import ua.training.controller.servlets.actions.Redirect;

import java.util.HashMap;
import java.util.Map;

/**
 * Максим
 * 03.05.2018
 */
public class CommandFactory {
    private Map<String, Command> commands = new HashMap<>();

    private CommandFactory() {
        commands.put(Commands.LOGIN_PAGE, new LoginPageCommand());
        commands.put(Commands.REGISTRATION_PAGE, new RegistrationPageCommand());
        commands.put(Commands.CLIENT_PAGE, new ClientPageCommand());
        commands.put(Commands.ADMIN_PAGE, new AdminPageCommand());
        commands.put(Commands.REGISTER, new RegisterCommand());
        commands.put(Commands.LOGIN, new LoginCommand());
        commands.put(Commands.LOGOUT, new LogoutCommand());
        commands.put(Commands.LANGUAGE, new LanguageCommand());
    }

    private static class CommandFactoryHolder {
        private static final CommandFactory INSTANCE = new CommandFactory();
    }

    public static CommandFactory getInstance() {
        return CommandFactoryHolder.INSTANCE;
    }

    public Command getCommand(String uri) {
        return commands.getOrDefault(extractCommand(uri), r -> new Redirect(Pages.INDEX));
    }

    private static String extractCommand(String uri) {
        int lastSlashInd = uri.lastIndexOf("/");
        return uri.substring(lastSlashInd + 1);
    }
}