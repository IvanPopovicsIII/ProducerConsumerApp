package command;

import command.CommandProcessor;
import command.CommandTask;
import consumer.Consumer;
import database.H2Db;
import producer.Producer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class CommandProcessorTest {

  private CommandProcessor commandProcessor;
    private BlockingQueue<CommandTask> queue;
    private H2Db mockDatabase;
    private Producer mockProducer;
    private Consumer realConsumer;

    @BeforeEach
    public void setUp() {
        // Initialize mocks and real objects
        queue = new LinkedBlockingQueue<>();
        mockDatabase = mock(H2Db.class);
        mockProducer = mock(Producer.class);
        realConsumer = new Consumer(queue, mockDatabase); // Use real Consumer
        
        // Create CommandProcessor with real and mock objects
        commandProcessor = new CommandProcessor(queue, mockDatabase, mockProducer, realConsumer);
    }

    @Test
    public void testStart() {
        // Start the consumer
        commandProcessor.start();

        // Verify that the consumer thread has started
        assertTrue(commandProcessor.consumerThread.isAlive(), "Consumer thread should be alive after starting.");
    }

    @Test
    public void testStop() throws InterruptedException {
        // Start the consumer
        commandProcessor.start();
        Thread.sleep(100);  // Give some time for the thread to start

        // Verify that the thread is running
        assertTrue(commandProcessor.consumerThread.isAlive(), "Thread should be alive before stopping.");

        // Stop the consumer
        commandProcessor.stop();

        // Verify that the consumer thread is interrupted and joined
        assertFalse(commandProcessor.consumerThread.isAlive(), "Thread should not be alive after stopping.");
    }

    @Test
    public void testAddCommand() {
        CommandTask mockTask = mock(CommandTask.class);
        
        // Call addCommand
        commandProcessor.addCommand(mockTask);

        // Verify that addCommand is called on producer with the mock task
        verify(mockProducer, times(1)).addCommand(mockTask);
    }
}