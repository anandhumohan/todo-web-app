package ch.cern.todo.service;

import ch.cern.todo.model.Task;

import java.util.List;
import java.util.Optional;

public interface TaskService {
    Task createTask(Task task);

    Optional<Task> updateTask(Long id, Task task);

    Optional<Task> getTaskById(Long id);

    List<Task> getAllTasks();

}
