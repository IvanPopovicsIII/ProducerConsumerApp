package command;

import model.User;

public class CommandTask {

	private Command command;
	private User user;
	

	public CommandTask(Command command, User user) {
		super();
		this.command = command;
		this.user = user;
	}


	public CommandTask(Command command) {
		super();
		this.command = command;
	}

	public Command getCommand() {
		return command;
	}
	
	public User getUser() {
		return user;
	}
	
}
