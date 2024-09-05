package producer;

import java.util.concurrent.BlockingQueue;

import command.CommandTask;

public class Producer {
    private BlockingQueue<CommandTask> queue;

    public Producer(BlockingQueue<CommandTask> queue) {
        this.queue = queue;
    }

    public void addCommand(CommandTask commandTask) {
        try {
            queue.put(commandTask); 
            System.out.println("Produced: " + commandTask.getCommand());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Producer interrupted while adding command");
        }
    }
}

