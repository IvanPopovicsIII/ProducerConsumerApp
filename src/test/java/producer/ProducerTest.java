package producer;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

import java.util.concurrent.BlockingQueue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import command.Command;
import command.CommandTask;
import model.User;

public class ProducerTest {

    @Mock
    private BlockingQueue<CommandTask> mockQueue;

    private Producer producer;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        producer = new Producer(mockQueue);
    }

    @Test
    public void testAddCommandHandlesInterruptedException() throws InterruptedException {
        CommandTask task = new CommandTask(Command.ADD, new User(1, "a1", "Robert"));

        // Configure mock to throw InterruptedException when put() is called
        doThrow(new InterruptedException()).when(mockQueue).put(any(CommandTask.class));

        // Run the method to ensure it handles the exception
        producer.addCommand(task);

        // Verify that the queue's put() method was called
        verify(mockQueue).put(task);
    }
}