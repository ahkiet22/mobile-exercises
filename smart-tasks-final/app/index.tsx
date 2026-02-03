// eslint-disable-next-line import/no-unresolved
import { useTaskListViewModel } from "@/hooks/useTaskListViewModel";
import { useFocusEffect } from "@react-navigation/native";
import { useRouter } from "expo-router";
import { useCallback } from "react";
import { ActivityIndicator, FlatList, RefreshControl } from "react-native";
import EmptyView from "../components/EmptyView";
import TaskItem from "../components/TaskItem";

export default function ListScreen() {
  const router = useRouter();
  const { tasks, loading, refreshing, loadTasks, refreshTasks } =
    useTaskListViewModel();

  useFocusEffect(
    useCallback(() => {
      loadTasks();
    }, [loadTasks])
  );

  if (loading) return <ActivityIndicator size="large" />;

  if (!tasks || tasks.length === 0) return <EmptyView />;

  return (
    <FlatList
      data={tasks}
      keyExtractor={(item) => item.id.toString()}
      refreshControl={
        <RefreshControl refreshing={refreshing} onRefresh={refreshTasks} />
      }
      renderItem={({ item }) => (
        <TaskItem
          item={item}
          onPress={() => router.push(`/detail/${item.id}`)}
        />
      )}
    />
  );
}
