package ch.cern.todo.service.impl;

import ch.cern.todo.dto.TaskCategoryDTO;
import ch.cern.todo.model.TaskCategory;
import ch.cern.todo.repository.TaskCategoryRepository;
import ch.cern.todo.service.TaskCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskCategoryServiceImpl implements TaskCategoryService {
    private final TaskCategoryRepository taskCategoryRepository;

    @Autowired
    public TaskCategoryServiceImpl(TaskCategoryRepository taskCategoryRepository) {
        this.taskCategoryRepository = taskCategoryRepository;
    }

    @Override
    public List<TaskCategoryDTO> getAllTaskCategory() {
        return taskCategoryRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private TaskCategoryDTO convertToDTO(TaskCategory taskCategory) {
        TaskCategoryDTO dto = new TaskCategoryDTO();
        dto.setCategoryId(taskCategory.getCategoryId());
        dto.setCategoryName(taskCategory.getCategoryName());
        dto.setCategoryDescription(taskCategory.getCategoryDescription());
        return dto;
    }
}
