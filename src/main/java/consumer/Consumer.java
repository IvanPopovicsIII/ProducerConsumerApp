package consumer;

import java.util.concurrent.BlockingQueue;

import command.CommandTask;
import database.H2Db;

public class Consumer implements Runnable {
    private BlockingQueue<CommandTask> queue;
    private H2Db database;

    
    
    public Consumer(BlockingQueue<CommandTask> queue, H2Db database) {
        this.queue = queue;
        this.database = database;
    }

    @Override
    public void run() {
        try {
            while (true) {
                CommandTask commandTask = queue.take(); // Take command from the queue
                if (commandTask != null) { // Check for null
                    processCommand(commandTask);
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Consumer interrupted.");
        }
    }

    private void processCommand(CommandTask commandTask) {
        switch (commandTask.getCommand()) {
            case ADD:
                database.addUser(commandTask.getUser());
                break;
            case PRINT_ALL:
                database.printAllUsers();
                break;
            case DELETE_ALL:
                database.deleteAllUsers();
                break;
        }
    }

	public BlockingQueue<CommandTask> getQueue() {
		return queue;
	}

	public H2Db getDatabase() {
		return database;
	}


    
    
    
    
}