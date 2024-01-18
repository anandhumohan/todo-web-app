package ch.cern.todo;

import ch.cern.todo.model.Task;
import ch.cern.todo.repository.TaskRepository;
import ch.cern.todo.service.impl.TaskServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TaskServiceTest {
    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskServiceImpl taskService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createTask_ShouldReturnTask() {
        Task mockTask = new Task();
        when(taskRepository.save(any(Task.class))).thenReturn(mockTask);

        Task result = taskService.createTask(new Task());

        assertNotNull(result);

        verify(taskRepository).save(any(Task.class));
    }

    @Test
    void updateTask_ShouldUpdateAndReturnUpdatedTask() {
        Long taskId = 1L;
        Task existingTask = new Task();
        Task updatedTask = new Task();
        updatedTask.setTaskName("Updated Name");

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(existingTask));
        when(taskRepository.save(any(Task.class))).thenReturn(updatedTask);

        Optional<Task> result = taskService.updateTask(taskId, updatedTask);

        assertTrue(result.isPresent());
        assertEquals("Updated Name", result.get().getTaskName());
        verify(taskRepository).findById(taskId);
        verify(taskRepository).save(any(Task.class));
    }

    @Test
    void getTaskById_ShouldReturnTaskIfExists() {
        Long taskId = 1L;
        Task task = new Task();

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));

        Optional<Task> result = taskService.getTaskById(taskId);

        assertTrue(result.isPresent());
        verify(taskRepository).findById(taskId);
    }

    @Test
    void getAllTasks_ShouldReturnAllTasks() {
        List<Task> tasks = Arrays.asList(new Task(), new Task());

        when(taskRepository.findAll()).thenReturn(tasks);

        List<Task> result = taskService.getAllTasks();

        assertEquals(2, result.size());
        verify(taskRepository).findAll();
    }


}