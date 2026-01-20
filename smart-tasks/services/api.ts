import { ApiResponse, Task } from "../types/task";

const BASE_URL = "https://amock.io/api/researchUTH";

export const getTasks = async (): Promise<ApiResponse<Task[]>> => {
  const res = await fetch(`${BASE_URL}/tasks`);
  return res.json();
};

export const getTaskDetail = async (id: string): Promise<ApiResponse<Task>> => {
  const res = await fetch(`${BASE_URL}/task/${id}`);
  return res.json();
};

export const deleteTask = async (id: string): Promise<ApiResponse<null>> => {
  const res = await fetch(`${BASE_URL}/task/${id}`, {
    method: "DELETE",
  });
  return res.json();
};
