package ch.cern.todo.service.impl;

import ch.cern.todo.model.TaskCategory;
import ch.cern.todo.repository.TaskCategoryRepository;
import ch.cern.todo.service.TaskCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskCategoryServiceImpl implements TaskCategoryService {
    private final TaskCategoryRepository taskCategoryRepository;

    @Autowired
    public TaskCategoryServiceImpl(TaskCategoryRepository taskCategoryRepository) {
        this.taskCategoryRepository = taskCategoryRepository;
    }

    @Override
    public List<TaskCategory> getAllTaskCategory() {
        return taskCategoryRepository.findAll();
    }
}
