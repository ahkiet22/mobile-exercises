import { useEffect, useState } from "react";
import { ActivityIndicator, Button, Text, View } from "react-native";
import { deleteTask, getTaskDetail } from "../services/api";

export default function DetailScreen({ route, navigation }) {
  const { id } = route.params;
  const [task, setTask] = useState(null);
  const [loading, setLoading] = useState(true);

  const fetchDetail = async () => {
    try {
      const data = await getTaskDetail(id);
      setTask(data);
    } catch (err) {
      console.log(err);
    } finally {
      setLoading(false);
    }
  };

  const handleDelete = async () => {
    await deleteTask(id);
    navigation.goBack();
  };

  useEffect(() => {
    fetchDetail();
  }, []);

  if (loading) return <ActivityIndicator size="large" />;

  return (
    <View>
      <Text>{task.title}</Text>
      <Text>{task.description}</Text>

      <Button title="Delete" color="red" onPress={handleDelete} />
    </View>
  );
}
