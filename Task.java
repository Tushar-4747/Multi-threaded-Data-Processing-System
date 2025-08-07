package JavaProcessingSystem;

public class Task {
    private final int taskId;

    public Task(int taskId) {
        this.taskId = taskId;
    }

    public int getTaskId() {
        return taskId;
    }

    public String process() throws InterruptedException {
        Thread.sleep(500); // simulate work
        return "Task " + taskId + " processed by " + Thread.currentThread().getName();
    }
}
