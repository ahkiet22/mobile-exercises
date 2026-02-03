import { Ionicons } from "@expo/vector-icons";
import {
    DarkTheme,
    DefaultTheme,
    ThemeProvider,
} from "@react-navigation/native";
import { Stack, useRouter } from "expo-router";
import { StatusBar } from "expo-status-bar";
import { Pressable } from "react-native";
import "react-native-reanimated";

import { useColorScheme } from "@/hooks/use-color-scheme";

export const unstable_settings = {
  anchor: "(tabs)",
};

function HeaderCreateButton() {
  const router = useRouter();
  return (
    <Pressable onPress={() => router.push("/create" as never)}>
      <Ionicons name="add-circle-outline" size={28} color="#1E90FF" />
    </Pressable>
  );
}

export default function RootLayout() {
  const colorScheme = useColorScheme();

  return (
    <ThemeProvider value={colorScheme === "dark" ? DarkTheme : DefaultTheme}>
      <Stack>
        <Stack.Screen
          name="index"
          options={{
            title: "List",
            headerRight: () => <HeaderCreateButton />,
          }}
        />
        <Stack.Screen
          name="create"
          options={{
            headerShown: false,
          }}
        />
        <Stack.Screen name="detail/[id]" options={{ title: "Detail" }} />
      </Stack>
      <StatusBar style="auto" />
    </ThemeProvider>
  );
}
