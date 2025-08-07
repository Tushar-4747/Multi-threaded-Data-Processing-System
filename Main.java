package JavaProcessingSystem;

public class Main {
    public static void main(String[] args) throws Exception {
        TaskQueue queue = new TaskQueue();
        ResultWriter writer = new ResultWriter("output.txt");

        int numWorkers = 5;
        Worker[] workers = new Worker[numWorkers];

        // Start workers
        for (int i = 0; i < numWorkers; i++) {
            workers[i] = new Worker(queue, writer, "Worker-" + i);
            workers[i].start();
        }

        // Add tasks
        for (int i = 0; i < 20; i++) {
            queue.addTask(new Task(i));
        }

        // Allow time for processing then interrupt
        Thread.sleep(5000);

        for (Worker worker : workers) {
            worker.interrupt();
        }

        writer.close();
        System.out.println("Processing complete.");
    }
}

