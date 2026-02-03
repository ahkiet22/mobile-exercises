import { useCallback, useState } from "react";
import { getTasks } from "../services/api";
import { getLocalTasks, saveLocalTasks } from "../services/taskStorage";
import { Task } from "../types/task";

interface TaskListState {
  tasks: Task[];
  loading: boolean;
  refreshing: boolean;
}

export function useTaskListViewModel() {
  const [state, setState] = useState<TaskListState>({
    tasks: [],
    loading: true,
    refreshing: false,
  });

  const loadTasks = useCallback(async () => {
    setState((prev) => ({ ...prev, loading: true }));
    try {
      // First, load from local storage for immediate display (offline support)
      const localTasks = await getLocalTasks();
      if (localTasks.length > 0) {
        setState((prev) => ({ ...prev, tasks: localTasks, loading: false }));
      }

      // Then, try to sync with API if online
      try {
        const res = await getTasks();
        if (res.data && res.data.length > 0) {
          // Merge API tasks with local tasks (local tasks take priority for offline-created items)
          const apiTasks = res.data;
          const localOnlyTasks = localTasks.filter(
            (local) => !apiTasks.some((api) => api.id === local.id)
          );
          const mergedTasks = [...localOnlyTasks, ...apiTasks];
          setState({ tasks: mergedTasks, loading: false, refreshing: false });
          await saveLocalTasks(mergedTasks);
        }
      } catch (apiError) {
        // If API fails, continue with local data (offline mode)
        console.log("API unavailable, using local data:", apiError);
        setState((prev) => ({ ...prev, loading: false }));
      }
    } catch (error) {
      console.error("Error loading tasks:", error);
      setState((prev) => ({ ...prev, loading: false }));
    }
  }, []);

  const refreshTasks = useCallback(async () => {
    setState((prev) => ({ ...prev, refreshing: true }));
    await loadTasks();
    setState((prev) => ({ ...prev, refreshing: false }));
  }, [loadTasks]);

  return {
    tasks: state.tasks,
    loading: state.loading,
    refreshing: state.refreshing,
    loadTasks,
    refreshTasks,
  };
}
