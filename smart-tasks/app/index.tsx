// eslint-disable-next-line import/no-unresolved
import { getTasks } from "@/services/api";
import { Task } from "@/types/task";
import { useRouter } from "expo-router";
import { useEffect, useState } from "react";
import { ActivityIndicator, FlatList, RefreshControl } from "react-native";
import EmptyView from "../components/EmptyView";
import TaskItem from "../components/TaskItem";

export default function ListScreen() {
  const router = useRouter();
  const [tasks, setTasks] = useState<Task[]>([]);
  const [loading, setLoading] = useState(true);
  const [refreshing, setRefreshing] = useState(false);

  const fetchTasks = async () => {
    try {
      const res = await getTasks();
      setTasks(res.data);
    } catch (err) {
      console.log(err);
    } finally {
      setLoading(false);
      setRefreshing(false);
    }
  };

  useEffect(() => {
    fetchTasks();
  }, []);

  if (loading) return <ActivityIndicator size="large" />;

  if (!tasks || tasks.length === 0) return <EmptyView />;

  return (
    <FlatList
      data={tasks}
      keyExtractor={(item) => item.id.toString()}
      refreshControl={
        <RefreshControl refreshing={refreshing} onRefresh={fetchTasks} />
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
