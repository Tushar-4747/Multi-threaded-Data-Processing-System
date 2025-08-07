package main

import (
    "fmt"
    "os"
    "sync"
    "time"
)

type Task struct {
    ID int
}

func (t Task) Process() string {
    time.Sleep(time.Millisecond * 500)
    return fmt.Sprintf("Task %d processed by goroutine", t.ID)
}

func worker(id int, tasks <-chan Task, wg *sync.WaitGroup, mutex *sync.Mutex, file *os.File) {
    defer wg.Done()
    for task := range tasks {
        result := task.Process()

        mutex.Lock()
        _, err := file.WriteString(result + "\n")
        if err != nil {
            fmt.Printf("Worker-%d encountered error: %v\n", id, err)
        }
        mutex.Unlock()

        fmt.Printf("Worker-%d: %s\n", id, result)
    }
}

func main() {
    tasks := make(chan Task, 100)
    file, err := os.Create("output.txt")
    if err != nil {
        fmt.Println("File creation error:", err)
        return
    }
    defer file.Close()

    var wg sync.WaitGroup
    var mutex sync.Mutex

    // Start workers
    for i := 0; i < 5; i++ {
        wg.Add(1)
        go worker(i, tasks, &wg, &mutex, file)
    }

    // Add tasks
    for i := 0; i < 20; i++ {
        tasks <- Task{ID: i}
    }
    close(tasks)

    wg.Wait()
    fmt.Println("Processing complete.")
}
