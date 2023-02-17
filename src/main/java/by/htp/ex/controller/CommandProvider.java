package by.htp.ex.controller;

import java.util.HashMap;
import java.util.Map;

import by.htp.ex.controller.impl.*;

public class CommandProvider {
	private final Map<CommandName, Command> commands = new HashMap<>();
	
	public CommandProvider() {
		commands.put(CommandName.GO_TO_BASE_PAGE, new GoToBasePage());
		commands.put(CommandName.GO_TO_REGISTRATION_PAGE, new GoToRegistrationPage());
		commands.put(CommandName.GO_TO_NEWS_LIST, new GoToNewsList());
		commands.put(CommandName.GO_TO_VIEW_NEWS, new GoToViewNews());
		commands.put(CommandName.GO_TO_EDIT_NEWS, new GoToEditNews());
		commands.put(CommandName.GO_TO_ADD_NEWS, new GoToAddNews());
		commands.put(CommandName.GO_TO_ERROR_PAGE, new GoToErrorPage());
		
		commands.put(CommandName.DO_SIGN_IN, new DoSignIn());
		commands.put(CommandName.DO_SIGN_OUT, new DoSignOut());
		commands.put(CommandName.DO_EDIT_NEWS, new DoEditNews());
		commands.put(CommandName.DO_DELETE_NEWS, new DoDeleteNews());
		commands.put(CommandName.DO_ADD_NEWS,new DoAddNews());
		commands.put(CommandName.DO_REGISTRATION, new DoRegistration());
		commands.put(CommandName.DO_CHANGE_LANGUAGE,new DoChangeLanguage());
	}
	
	public Command getCommand(String name) {
		Command command;
		
		try {
			CommandName commandName = CommandName.valueOf(name.toUpperCase());
			command = commands.get(commandName);
			return command;
		} catch (Exception e) { // no such element
			command = commands.get(CommandName.GO_TO_ERROR_PAGE);
			return command;
		}
	}
}
