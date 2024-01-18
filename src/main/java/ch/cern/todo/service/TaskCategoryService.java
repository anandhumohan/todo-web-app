package ch.cern.todo.service;

import ch.cern.todo.model.TaskCategory;

import java.util.List;

public interface TaskCategoryService {
    List<TaskCategory> getAllTaskCategory();
}
