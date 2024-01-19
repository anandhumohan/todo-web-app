package ch.cern.todo.controller;

import ch.cern.todo.model.TaskCategory;
import ch.cern.todo.repository.TaskCategoryRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class TaskCategoryController {
    private final TaskCategoryRepository taskCategoryRepository;

    public TaskCategoryController(TaskCategoryRepository taskCategoryRepository) {
        this.taskCategoryRepository = taskCategoryRepository;
    }

    @GetMapping
    public List<TaskCategory> getAllCategories() {
        return taskCategoryRepository.findAll();
    }
}
