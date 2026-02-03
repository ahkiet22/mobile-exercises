import { Ionicons } from "@expo/vector-icons";
import { StyleSheet, Text, View } from "react-native";

export default function EmptyView() {
  return (
    <View style={styles.container}>
      <Ionicons name="clipboard-outline" size={64} color="#aaa" />
      <Text style={styles.title}>No Tasks Yet!</Text>
      <Text style={styles.sub}>Stay productive – add something to do</Text>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: "center",
    alignItems: "center",
  },
  title: {
    fontSize: 18,
    fontWeight: "600",
    marginTop: 12,
  },
  sub: {
    color: "#777",
    marginTop: 4,
  },
});
