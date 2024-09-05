package command;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import consumer.Consumer;
import database.H2Db;
import producer.Producer;

public class CommandProcessor {
	
    private BlockingQueue<CommandTask> queue = new LinkedBlockingQueue<>();
    private H2Db database;
    private Producer producer;
    protected Thread consumerThread;
    private Consumer consumer;
    
    public CommandProcessor() {
    	this.database = H2Db.getInstance();
    	this.producer = new Producer(queue);
        this.consumer = new Consumer(queue, database);
    }
    
 // Constructor for testing purposes
    public CommandProcessor(BlockingQueue<CommandTask> queue, H2Db database, Producer producer, Consumer consumer) {
        this.queue = queue;
        this.database = database;
        this.producer = producer;
        this.consumer = consumer;
    }

    public void start() {
    	 consumerThread = new Thread(consumer);
         consumerThread.start();
         System.out.println("Consumer started.");
    }

    public void stop() throws InterruptedException {
        if (consumerThread != null) {
            consumerThread.interrupt();
            consumerThread.join();
            System.out.println("Consumer stopped.");
        }
    }

    public void addCommand(CommandTask task) {
    	producer.addCommand(task);
    }


}