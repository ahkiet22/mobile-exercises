import { useEffect, useState } from "react";
import { ActivityIndicator, FlatList } from "react-native";
import EmptyView from "../components/EmptyView";
import { getTasks } from "../services/api";

export default function ListScreen({ navigation }) {
  const [tasks, setTasks] = useState([]);
  const [loading, setLoading] = useState(true);

  const fetchTasks = async () => {
    try {
      const data = await getTasks();
      setTasks(data);
    } catch (err) {
      console.log(err);
    } finally {
      setLoading(false);
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
      renderItem={({ item }) => (
        <TaskItem
          item={item}
          onPress={() => navigation.navigate("Detail", { id: item.id })}
        />
      )}
    />
  );
}
