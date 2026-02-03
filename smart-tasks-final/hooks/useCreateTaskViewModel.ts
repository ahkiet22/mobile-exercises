import { useRouter } from "expo-router";
import { useState } from "react";
import { Alert } from "react-native";
import { addLocalTask, generateLocalTaskId } from "../services/taskStorage";
import { Task } from "../types/task";

interface CreateTaskState {
  title: string;
  description: string;
  loading: boolean;
  error: string | null;
}

export function useCreateTaskViewModel() {
  const router = useRouter();
  const [state, setState] = useState<CreateTaskState>({
    title: "",
    description: "",
    loading: false,
    error: null,
  });

  const setTitle = (title: string) => {
    setState((prev) => ({ ...prev, title, error: null }));
  };

  const setDescription = (description: string) => {
    setState((prev) => ({ ...prev, description, error: null }));
  };

  const resetForm = () => {
    setState({
      title: "",
      description: "",
      loading: false,
      error: null,
    });
  };

  const validate = (): boolean => {
    if (!state.title.trim()) {
      setState((prev) => ({ ...prev, error: "Task title is required" }));
      return false;
    }
    return true;
  };

  const createTask = async () => {
    if (!validate()) {
      Alert.alert("Error", state.error || "Please fill in all required fields");
      return;
    }

    setState((prev) => ({ ...prev, loading: true }));

    try {
      const now = new Date().toISOString();
      const newTask: Task = {
        id: generateLocalTaskId(),
        title: state.title.trim(),
        description: state.description.trim(),
        category: "General",
        status: "Pending",
        priority: "Medium",
        createdAt: now,
        updatedAt: now,
        dueDate: now,
        attachments: [],
        reminders: [],
        subtasks: [],
      };

      await addLocalTask(newTask);
      resetForm();
      router.back();
    } catch (error) {
      console.error("Error creating task:", error);
      setState((prev) => ({
        ...prev,
        loading: false,
        error: "Failed to create task",
      }));
      Alert.alert("Error", "Failed to create task. Please try again.");
    }
  };

  return {
    title: state.title,
    description: state.description,
    loading: state.loading,
    error: state.error,
    setTitle,
    setDescription,
    createTask,
    resetForm,
  };
}
