package ch.cern.todo.service.impl;

import ch.cern.todo.dto.TaskDTO;
import ch.cern.todo.exception.CategoryNotFoundException;
import ch.cern.todo.exception.TaskNotFoundException;
import ch.cern.todo.model.Task;
import ch.cern.todo.model.TaskCategory;
import ch.cern.todo.repository.TaskCategoryRepository;
import ch.cern.todo.repository.TaskRepository;
import ch.cern.todo.service.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {
    private static final Logger logger = LoggerFactory.getLogger(TaskServiceImpl.class);
    private final TaskRepository taskRepository;
    private final TaskCategoryRepository taskCategoryRepository;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository, TaskCategoryRepository taskCategoryRepository) {
        this.taskRepository = taskRepository;
        this.taskCategoryRepository = taskCategoryRepository;
    }


    @Override
    public TaskDTO createTask(TaskDTO taskDTO) {
        logger.info("Creating task with name: {}", taskDTO.getTaskName());

        Task task = convertToEntity(taskDTO);
        task = taskRepository.save(task);
        return convertToDTO(task);
    }

    @Override
    public Optional<TaskDTO> updateTask(Long id, TaskDTO taskDTO) {
        logger.info("Updating task with ID: {}", id);

        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task not found with id " + id));

        updateEntityWithDTO(task, taskDTO);
        Task savedTask = taskRepository.save(task);
        return Optional.of(convertToDTO(savedTask));
    }

    @Override
    public Optional<Task> getTaskById(Long id) {
        logger.info("Getting task with ID: {}", id);

        return taskRepository.findById(id);
    }

    @Override
    public List<TaskDTO> getAllTasks() {
        logger.info("getting all tasks");

        List<Task> tasks = taskRepository.findAll();
        return tasks.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteTask(Long id) {
        logger.info("Deleting task with ID: {}", id);
        if (!taskRepository.existsById(id)) {
            logger.error("Error in deleteTask: Task not found with ID {}", id);
            throw new TaskNotFoundException("Task not found with id " + id);
        }
        taskRepository.deleteById(id);
    }

    private Task convertToEntity(TaskDTO taskDTO) {
        logger.debug("Converting TaskDTO to Task entity: {}", taskDTO);

        Task task = new Task();
        task.setTaskName(taskDTO.getTaskName());
        task.setTaskDescription(taskDTO.getTaskDescription());
        task.setDeadline(taskDTO.getDeadline());
        if (taskDTO.getCategoryId() != null) {
            try {
                TaskCategory category = taskCategoryRepository.findById(taskDTO.getCategoryId())
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found"));
                task.setTaskCategory(category);
            } catch (ResponseStatusException e) {
                logger.error("Error in convertToEntity: {}", e.getMessage());
                throw e;
            }
        }

        logger.debug("Task entity created: {}", task);
        return task;
    }

    public TaskDTO convertToDTO(Task task) {
        logger.debug("Converting Task entity to TaskDTO: {}", task);

        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setTaskId(task.getTaskId());
        taskDTO.setTaskName(task.getTaskName());
        taskDTO.setTaskDescription(task.getTaskDescription());
        taskDTO.setDeadline(task.getDeadline());
        if (task.getTaskCategory() != null) {
            taskDTO.setCategoryId(task.getTaskCategory().getCategoryId());
        }

        logger.debug("TaskDTO created: {}", taskDTO);
        return taskDTO;
    }

    private void updateEntityWithDTO(Task task, TaskDTO taskDTO) {
        logger.debug("Updating Task entity with TaskDTO data: {}", taskDTO);

        task.setTaskName(taskDTO.getTaskName());
        task.setTaskDescription(taskDTO.getTaskDescription());
        task.setDeadline(taskDTO.getDeadline());
        if (taskDTO.getCategoryId() != null) {
            TaskCategory category = taskCategoryRepository.findById(taskDTO.getCategoryId())
                    .orElseThrow(() -> new CategoryNotFoundException("Category not found with id " + taskDTO.getCategoryId()));
            task.setTaskCategory(category);
        }

        logger.debug("Task entity updated for TaskDTO: {}", taskDTO);
    }
}
