package ch.cern.todo.service.impl;

import ch.cern.todo.dto.TaskDTO;
import ch.cern.todo.model.Task;
import ch.cern.todo.model.TaskCategory;
import ch.cern.todo.repository.TaskCategoryRepository;
import ch.cern.todo.repository.TaskRepository;
import ch.cern.todo.service.TaskService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final TaskCategoryRepository taskCategoryRepository;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository, TaskCategoryRepository taskCategoryRepository) {
        this.taskRepository = taskRepository;
        this.taskCategoryRepository = taskCategoryRepository;
    }


    @Override
    public TaskDTO createTask(TaskDTO taskDTO) {
        Task task = convertToEntity(taskDTO);
        task = taskRepository.save(task);
        return convertToDTO(task);
    }
    @Override
    public Optional<TaskDTO> updateTask(Long id, TaskDTO taskDTO) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Task not found with id " + id));

        updateEntityWithDTO(task, taskDTO);
        Task savedTask = taskRepository.save(task);
        return Optional.of(convertToDTO(savedTask));
    }

    @Override
    public Optional<Task> getTaskById(Long id) {
        return taskRepository.findById(id);
    }

    @Override
    public List<TaskDTO> getAllTasks() {
        List<Task> tasks = taskRepository.findAll();
        return tasks.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

    private Task convertToEntity(TaskDTO taskDTO) {
        Task task = new Task();
        task.setTaskName(taskDTO.getTaskName());
        task.setTaskDescription(taskDTO.getTaskDescription());
        task.setDeadline(taskDTO.getDeadline());
        if (taskDTO.getCategoryId() != null) {
            TaskCategory category = taskCategoryRepository.findById(taskDTO.getCategoryId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found"));
            task.setTaskCategory(category);
        }
        return task;
    }

    public TaskDTO convertToDTO(Task task) {
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setTaskId(task.getTaskId());
        taskDTO.setTaskName(task.getTaskName());
        taskDTO.setTaskDescription(task.getTaskDescription());
        taskDTO.setDeadline(task.getDeadline());
        if (task.getTaskCategory() != null) {
            taskDTO.setCategoryId(task.getTaskCategory().getCategoryId());
        }
        return taskDTO;
    }

    private void updateEntityWithDTO(Task task, TaskDTO taskDTO) {
        task.setTaskName(taskDTO.getTaskName());
        task.setTaskDescription(taskDTO.getTaskDescription());
        task.setDeadline(taskDTO.getDeadline());
        if (taskDTO.getCategoryId() != null) {
            TaskCategory category = taskCategoryRepository.findById(taskDTO.getCategoryId())
                    .orElseThrow(() -> new EntityNotFoundException("Category not found with id " + taskDTO.getCategoryId()));
            task.setTaskCategory(category);
        }
    }
}
