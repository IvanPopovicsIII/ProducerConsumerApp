package app;

public class ProducerConsumerApp {

	public static void main(String[] args) throws InterruptedException {
		CommandProcessor processor = new CommandProcessor();
        processor.start();
//
        processor.addCommand(new CommandTask(Command.ADD, new User(1, "a1", "Robert")));
        processor.addCommand(new CommandTask(Command.ADD, new User(2, "a2", "Martin")));
        processor.addCommand(new CommandTask(Command.PRINT_ALL));
        processor.addCommand(new CommandTask(Command.DELETE_ALL));
        processor.addCommand(new CommandTask(Command.PRINT_ALL));

        Thread.sleep(1000);  
        processor.stop();
	}

}
