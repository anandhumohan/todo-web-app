export interface Task {
  taskId: number;
  taskName: string;
  taskDescription: string;
  deadline: string;
  categoryId: number | null;
}
