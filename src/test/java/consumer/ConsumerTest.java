package consumer;

import static org.mockito.Mockito.verify;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import command.Command;
import command.CommandTask;
import database.H2Db;
import model.User;

public class ConsumerTest {

    @Mock
    private H2Db mockDatabase;

    private BlockingQueue<CommandTask> mockQueue;
    private Consumer consumer;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        // Initialize the BlockingQueue for testing
        mockQueue = new LinkedBlockingQueue<>();
        consumer = new Consumer(mockQueue, mockDatabase);
    }

    @Test
    public void testProcessAddCommand() throws InterruptedException {
        // Create a sample command task
        CommandTask task = new CommandTask(Command.ADD, new User(1, "a1", "Robert"));

        // Set up the queue to return the task
        mockQueue.add(task);

        // Run the consumer in a separate thread
        Thread consumerThread = new Thread(consumer);
        consumerThread.start();

        // Allow some time for the consumer to process
        Thread.sleep(100);

        // Verify the database's addUser() method was called
        verify(mockDatabase).addUser(task.getUser());

        // Interrupt the consumer thread to stop it
        consumerThread.interrupt();
        consumerThread.join();
    }

    @Test
    public void testProcessPrintAllCommand() throws InterruptedException {
        // Create a sample command task
        CommandTask task = new CommandTask(Command.PRINT_ALL, null);

        // Set up the queue to return the task
        mockQueue.add(task);

        // Run the consumer in a separate thread
        Thread consumerThread = new Thread(consumer);
        consumerThread.start();

        // Allow some time for the consumer to process
        Thread.sleep(100);

        // Verify the database's printAllUsers() method was called
        verify(mockDatabase).printAllUsers();

        // Interrupt the consumer thread to stop it
        consumerThread.interrupt();
        consumerThread.join();
    }

    @Test
    public void testProcessDeleteAllCommand() throws InterruptedException {
        // Create a sample command task
        CommandTask task = new CommandTask(Command.DELETE_ALL, null);

        // Set up the queue to return the task
        mockQueue.add(task);

        // Run the consumer in a separate thread
        Thread consumerThread = new Thread(consumer);
        consumerThread.start();

        // Allow some time for the consumer to process
        Thread.sleep(100);

        // Verify the database's deleteAllUsers() method was called
        verify(mockDatabase).deleteAllUsers();

        // Interrupt the consumer thread to stop it
        consumerThread.interrupt();
        consumerThread.join();
    }
}