import { Task } from "@/types/task";
import { Ionicons } from "@expo/vector-icons";
import { useLocalSearchParams, useRouter } from "expo-router";
import { useEffect, useState } from "react";
import {
  ActivityIndicator,
  Alert,
  Pressable,
  ScrollView,
  StyleSheet,
  Text,
  View,
} from "react-native";
import { deleteTask, getTaskDetail } from "../../services/api";

export default function DetailScreen() {
  const { id } = useLocalSearchParams();
  const router = useRouter();
  const [task, setTask] = useState<Task | null>(null);
  const [loading, setLoading] = useState(true);

  const fetchDetail = async () => {
    try {
      const res = await getTaskDetail(String(id));
      setTask(res.data);
    } catch (err) {
      console.log(err);
    } finally {
      setLoading(false);
    }
  };

  const handleDelete = async () => {
    Alert.alert("Confirm", "Delete this task?", [
      { text: "Cancel" },
      {
        text: "Delete",
        style: "destructive",
        onPress: async () => {
          await deleteTask(String(id));
          router.back();
        },
      },
    ]);
  };

  useEffect(() => {
    fetchDetail();
  }, []);

  if (loading || !task) return <ActivityIndicator size="large" />;

  return (
    <ScrollView style={styles.container}>
      <View style={styles.header}>
        <Text style={styles.title}>{task.title}</Text>
        <Pressable onPress={handleDelete}>
          <Ionicons name="trash-outline" size={22} color="red" />
        </Pressable>
      </View>

      <Text style={styles.desc}>{task.description}</Text>

      <View style={styles.badgeRow}>
        <Badge label={task.category} />
        <Badge label={task.status} />
        <Badge label={task.priority} />
      </View>

      <Text style={styles.section}>Subtasks</Text>
      {task.subtasks.map((s) => (
        <View key={s.id} style={styles.subtask}>
          <Ionicons
            name={s.isCompleted ? "checkbox" : "square-outline"}
            size={20}
          />
          <Text style={{ marginLeft: 8 }}>{s.title}</Text>
        </View>
      ))}

      <Text style={styles.section}>Attachments</Text>
      {task.attachments.map((a) => (
        <View key={a.id} style={styles.attachment}>
          <Ionicons name="attach" size={16} />
          <Text style={{ marginLeft: 6 }}>document_{a.id}.pdf</Text>
        </View>
      ))}
    </ScrollView>
  );
}

function Badge({ label }: { label: string }) {
  return (
    <View style={styles.badge}>
      <Text style={styles.badgeText}>{label}</Text>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    padding: 16,
  },
  header: {
    flexDirection: "row",
    justifyContent: "space-between",
    alignItems: "center",
  },
  title: {
    fontSize: 20,
    fontWeight: "600",
  },
  desc: {
    marginVertical: 8,
    color: "#555",
  },
  badgeRow: {
    flexDirection: "row",
    gap: 8,
    marginVertical: 12,
  },
  badge: {
    backgroundColor: "#eee",
    paddingHorizontal: 10,
    paddingVertical: 4,
    borderRadius: 8,
  },
  badgeText: {
    fontSize: 12,
  },
  section: {
    marginTop: 16,
    fontWeight: "600",
  },
  subtask: {
    flexDirection: "row",
    alignItems: "center",
    marginVertical: 6,
  },
  attachment: {
    flexDirection: "row",
    alignItems: "center",
    backgroundColor: "#f2f2f2",
    padding: 8,
    borderRadius: 8,
    marginVertical: 6,
  },
});
