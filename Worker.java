package JavaProcessingSystem;

public class Worker extends Thread {
    private final TaskQueue taskQueue;
    private final ResultWriter resultWriter;

    public Worker(TaskQueue taskQueue, ResultWriter resultWriter, String name) {
        super(name);
        this.taskQueue = taskQueue;
        this.resultWriter = resultWriter;
    }

    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Task task = taskQueue.getTask();
                if (task == null) break;
                String result = task.process();
                resultWriter.write(result);
                System.out.println(result);
            } catch (Exception e) {
                System.err.println(getName() + " encountered error: " + e.getMessage());
            }
        }
    }
}
