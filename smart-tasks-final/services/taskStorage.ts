import AsyncStorage from "@react-native-async-storage/async-storage";
import { Task } from "../types/task";

const TASKS_STORAGE_KEY = "@smart_tasks_local";

export const getLocalTasks = async (): Promise<Task[]> => {
  try {
    const jsonValue = await AsyncStorage.getItem(TASKS_STORAGE_KEY);
    return jsonValue != null ? JSON.parse(jsonValue) : [];
  } catch (error) {
    console.error("Error reading local tasks:", error);
    return [];
  }
};

export const saveLocalTasks = async (tasks: Task[]): Promise<void> => {
  try {
    const jsonValue = JSON.stringify(tasks);
    await AsyncStorage.setItem(TASKS_STORAGE_KEY, jsonValue);
  } catch (error) {
    console.error("Error saving local tasks:", error);
  }
};

export const addLocalTask = async (task: Task): Promise<void> => {
  try {
    const existingTasks = await getLocalTasks();
    const updatedTasks = [task, ...existingTasks];
    await saveLocalTasks(updatedTasks);
  } catch (error) {
    console.error("Error adding local task:", error);
  }
};

export const deleteLocalTask = async (id: number): Promise<void> => {
  try {
    const existingTasks = await getLocalTasks();
    const updatedTasks = existingTasks.filter((task) => task.id !== id);
    await saveLocalTasks(updatedTasks);
  } catch (error) {
    console.error("Error deleting local task:", error);
  }
};

export const generateLocalTaskId = (): number => {
  return Date.now() + Math.floor(Math.random() * 1000);
};
