import {Component, OnInit} from '@angular/core';
import {TodoService} from '../services/todo.service';
import {Task} from '../models/task.model';
import {TaskCategory} from '../models/task-category.model';


@Component({
  selector: 'app-todo-page',
  templateUrl: './todo-page.component.html',
  styleUrl: './todo-page.component.scss'
})
export class TodoPageComponent implements OnInit {

  ngOnInit() {
    this.fetchTasks();
    this.loadTaskCategories();
  }

  taskName: string = '';
  taskDescription: string = '';
  deadline: string = '';
  tasks: Task[] = [];
  editingTaskId: number | null = null;
  taskCategories: TaskCategory[] = [];
  selectedCategoryId: number = 0;

  constructor(private todoService: TodoService) {
  }

  loadTaskCategories(): void {
    this.todoService.getCategories().subscribe({
      next: (categories) => {
        this.taskCategories = categories;
      },
      error: (err) => {
        console.error('Error fetching categories', err);
      }
    });
  }

  fetchTasks(): void {
    this.todoService.getTasks().subscribe({
      next: (tasks: Task[]) => {
        this.tasks = tasks.sort((a, b) => b.taskId - a.taskId);
      },
      error: (error: any) => {
        console.error('Error fetching tasks:', error);
      }
    });
  }

  addTask(): void {
    const newTask: Task = {
      taskId: 0,
      taskName: this.taskName,
      taskDescription: this.taskDescription,
      deadline: this.deadline ? new Date(this.deadline).toISOString() : '',
      categoryId: this.selectedCategoryId !== 0 ? this.selectedCategoryId : null
    };

    // Call the service to add the task
    this.todoService.addTask(newTask).subscribe({
      next: (task: Task) => {
        console.log('Task added successfully', task);
        this.tasks.unshift(task);
      },
      error: (error: any) => {
        console.error('There was an error!', error);
      }
    });

    // Reset form fields
    this.taskName = '';
    this.taskDescription = '';
    this.deadline = '';
    this.selectedCategoryId = 0;
  }


  deleteTask(task: Task): void {
    //console.log(task);
    if (task.taskId) {
      console.log(task);
      this.todoService.deleteTask(task.taskId).subscribe({
        next: () => {
          console.log('Task deleted successfully');
          this.tasks = this.tasks.filter(t => t.taskId !== task.taskId);
        },
        error: (error) => {
          console.error('There was an error!', error);
        }
      });
    }
  }

  editTask(task: Task): void {
    this.editingTaskId = task.taskId;
    const deadlineDate = new Date(task.deadline);
    task.deadline = this.formatDateForDatetimeLocal(deadlineDate);
  }

  formatDateForDatetimeLocal(date: Date): string {
    const year = date.getFullYear();
    const month = (date.getMonth() + 1).toString().padStart(2, '0');
    const day = date.getDate().toString().padStart(2, '0');
    const hours = date.getHours().toString().padStart(2, '0');
    const minutes = date.getMinutes().toString().padStart(2, '0');

    return `${year}-${month}-${day}T${hours}:${minutes}`;
  }

  saveTask(task: Task): void {
    this.todoService.updateTask(task).subscribe({
      next: (updatedTask) => {
        // Update the task in the tasks array
        const index = this.tasks.findIndex(t => t.taskId === updatedTask.taskId);
        if (index !== -1) {
          this.tasks[index] = updatedTask;
        }
        this.editingTaskId = null;
      },
      error: (error) => {
        console.error('There was an error!', error);
      }
    });
  }

  cancelEdit(): void {
    this.editingTaskId = null;
  }

}

