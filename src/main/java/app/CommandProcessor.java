package app;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class CommandProcessor {
    private BlockingQueue<CommandTask> queue = new LinkedBlockingQueue<>();
    private H2Db database;
    private boolean running = true;
    
    public CommandProcessor() {
    	this.database = H2Db.getInstance();
    }

    public void start() {
        Thread consumerThread = new Thread(() -> {
            while (running) {
                try {
                    CommandTask task = queue.take();
                    processCommand(task);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        });
        consumerThread.start();
    }

    public void stop() {
        running = false;
    }

    public void addCommand(CommandTask task) {
        queue.offer(task);
    }

    private void processCommand(CommandTask task) {
        switch (task.getCommand()) {
            case ADD:
                database.addUser(task.getUser());
                break;
            case PRINT_ALL:
                database.printAllUsers();
                break;
            case DELETE_ALL:
                database.deleteAllUsers();
                break;
            default:
                throw new UnsupportedOperationException("Unknown command: " + task.getCommand());
        }
    }
}