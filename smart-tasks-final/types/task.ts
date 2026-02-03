export interface Attachment {
  id: number;
  name?: string;
  url?: string;
}

export interface Reminder {
  id: number;
  time: string; // ISO date string
  type: "Notification" | "Popup" | string;
}

export interface Subtask {
  id: number;
  title: string;
  isCompleted: boolean;
}
export interface Task {
  id: number;
  title: string;
  description: string;
  category: string;
  status: string;
  priority: string;

  createdAt: string;
  updatedAt: string;
  dueDate: string;

  attachments: Attachment[];
  reminders: Reminder[];
  subtasks: Subtask[];
}

export interface ApiResponse<T> {
  isSuccess: boolean;
  message: string;
  data: T;
}
