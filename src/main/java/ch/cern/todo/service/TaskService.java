package ch.cern.todo.service;

import ch.cern.todo.dto.TaskDTO;
import ch.cern.todo.model.Task;

import java.util.List;
import java.util.Optional;

public interface TaskService {
    TaskDTO createTask(TaskDTO taskDTO);

    Optional<TaskDTO> updateTask(Long id, TaskDTO taskDTO);

    Optional<Task> getTaskById(Long id);

    List<TaskDTO> getAllTasks();

    void deleteTask(Long id);
}
