import { Ionicons } from "@expo/vector-icons";
import { Pressable, StyleSheet, Text, View } from "react-native";

export default function TaskItem({ item, onPress }) {
  const bgColor =
    item.status === "In Progress"
      ? "#f7c6cc"
      : item.status === "Pending"
        ? "#cce8f6"
        : "#d7f5d4";

  return (
    <Pressable
      onPress={onPress}
      style={[styles.card, { backgroundColor: bgColor }]}
    >
      <View style={styles.row}>
        <Ionicons name="checkbox-outline" size={20} />
        <View style={{ marginLeft: 8, flex: 1 }}>
          <Text style={styles.title}>{item.title}</Text>
          <Text style={styles.desc}>{item.description}</Text>
          <View style={styles.footer}>
            <Text style={styles.status}>Status: {item.status}</Text>
            <Text style={styles.time}>14:00 25/03/2024</Text>
          </View>
        </View>
      </View>
    </Pressable>
  );
}

const styles = StyleSheet.create({
  card: {
    padding: 12,
    borderRadius: 12,
    marginHorizontal: 12,
    marginVertical: 6,
  },
  row: {
    flexDirection: "row",
    alignItems: "flex-start",
  },
  title: {
    fontSize: 15,
    fontWeight: "600",
  },
  desc: {
    fontSize: 13,
    marginVertical: 4,
  },
  footer: {
    flexDirection: "row",
    justifyContent: "space-between",
  },
  status: {
    fontSize: 12,
  },
  time: {
    fontSize: 12,
  },
});
