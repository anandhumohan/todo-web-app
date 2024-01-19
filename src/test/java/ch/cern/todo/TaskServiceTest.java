package ch.cern.todo;

import ch.cern.todo.dto.TaskDTO;
import ch.cern.todo.exception.TaskNotFoundException;
import ch.cern.todo.model.Task;
import ch.cern.todo.model.TaskCategory;
import ch.cern.todo.repository.TaskRepository;
import ch.cern.todo.service.impl.TaskServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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
    void createTask_ShouldCreateAndReturnTaskDTO() {

        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setTaskName("New Task");

        Task savedTask = new Task();
        savedTask.setTaskId(1L);
        savedTask.setTaskName("New Task");

        when(taskRepository.save(any(Task.class))).thenReturn(savedTask);

        TaskDTO result = taskService.createTask(taskDTO);

        assertNotNull(result);
        assertEquals(1L, result.getTaskId());
        assertEquals("New Task", result.getTaskName());

        verify(taskRepository).save(any(Task.class));
    }

    @Test
    void updateTask_ShouldUpdateAndReturnUpdatedTaskDTO() {

        Long taskId = 1L;
        TaskDTO taskDTOToUpdate = new TaskDTO();
        taskDTOToUpdate.setTaskName("Updated Name");

        Task existingTask = new Task();
        existingTask.setTaskId(taskId);
        existingTask.setTaskName("Old Name");

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(existingTask));
        when(taskRepository.save(any(Task.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Optional<TaskDTO> resultOptional = taskService.updateTask(taskId, taskDTOToUpdate);

        assertTrue(resultOptional.isPresent());
        TaskDTO updatedTaskDTO = resultOptional.get();
        assertEquals(taskId, updatedTaskDTO.getTaskId());
        assertEquals("Updated Name", updatedTaskDTO.getTaskName());
        verify(taskRepository).findById(taskId);
        verify(taskRepository).save(eq(existingTask));
    }

    @Test
    void updateTask_ShouldThrowTaskNotFoundExceptionIfTaskNotExists() {

        Long taskId = 1L;
        TaskDTO taskDTOToUpdate = new TaskDTO();
        taskDTOToUpdate.setTaskName("Updated Name");

        when(taskRepository.findById(taskId)).thenReturn(Optional.empty());

        assertThrows(TaskNotFoundException.class, () -> taskService.updateTask(taskId, taskDTOToUpdate));
        verify(taskRepository).findById(taskId);
        verify(taskRepository, never()).save(any(Task.class));
    }

    @Test
    void getTaskById_ShouldReturnTaskIfExists() {

        Long taskId = 1L;
        Task task = new Task();

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));

        Optional<Task> result = taskService.getTaskById(taskId);

        assertTrue(result.isPresent());
        assertEquals(task, result.get());
        verify(taskRepository).findById(taskId);
    }

    @Test
    void getTaskById_ShouldReturnEmptyOptionalIfTaskNotExists() {

        Long taskId = 1L;

        when(taskRepository.findById(taskId)).thenReturn(Optional.empty());

        Optional<Task> result = taskService.getTaskById(taskId);

        assertTrue(result.isEmpty());
        verify(taskRepository).findById(taskId);
    }

    @Test
    void getAllTasks_ShouldReturnAllTaskDTOs() {

        List<Task> tasks = Arrays.asList(new Task(), new Task());

        when(taskRepository.findAll()).thenReturn(tasks);

        List<TaskDTO> result = taskService.getAllTasks();

        assertEquals(2, result.size());
        verify(taskRepository).findAll();
    }

    @Test
    void deleteTask_ShouldDeleteTaskIfExists() {

        Long taskId = 1L;
        when(taskRepository.existsById(taskId)).thenReturn(true);

        taskService.deleteTask(taskId);

        verify(taskRepository).existsById(taskId);
        verify(taskRepository).deleteById(taskId);
    }

    @Test
    void deleteTask_ShouldThrowTaskNotFoundExceptionIfTaskNotExists() {

        Long taskId = 1L;
        when(taskRepository.existsById(taskId)).thenReturn(false);

        assertThrows(TaskNotFoundException.class, () -> taskService.deleteTask(taskId));
        verify(taskRepository).existsById(taskId);
        verify(taskRepository, never()).deleteById(anyLong());
    }

    @Test
    void convertToDTO_ShouldConvertTaskEntityToTaskDTO() {
        Task task = new Task();
        task.setTaskId(1L);
        task.setTaskName("New Task");
        task.setTaskDescription("Description");

        TaskCategory category = new TaskCategory();
        category.setCategoryId(1L);
        task.setTaskCategory(category);

        TaskDTO result = taskService.convertToDTO(task);

        assertNotNull(result);
        assertEquals(1L, result.getTaskId());
        assertEquals("New Task", result.getTaskName());
        assertEquals("Description", result.getTaskDescription());
        assertNotNull(result.getCategoryId());
        assertEquals(1L, result.getCategoryId());
    }



}
