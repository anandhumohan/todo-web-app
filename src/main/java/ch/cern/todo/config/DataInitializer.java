package ch.cern.todo.config;

import ch.cern.todo.model.TaskCategory;
import ch.cern.todo.repository.TaskCategoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {
    private final TaskCategoryRepository taskCategoryRepository;

    public DataInitializer(TaskCategoryRepository taskCategoryRepository) {
        this.taskCategoryRepository = taskCategoryRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        insertCategory("Personal", "Personal tasks");
        insertCategory("Work", "Work-related tasks");
    }

    private void insertCategory(String name, String description) {
        if (taskCategoryRepository.findByCategoryName(name).isEmpty()) {
            TaskCategory category = new TaskCategory();
            category.setCategoryName(name);
            category.setCategoryDescription(description);
            taskCategoryRepository.save(category);
        }
    }
}
