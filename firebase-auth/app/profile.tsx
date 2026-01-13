import { useAuth } from '@/contexts/AuthContext';
import { Ionicons } from '@expo/vector-icons';
import { useRouter } from 'expo-router';
import { Image, ScrollView, StyleSheet, Text, TextInput, TouchableOpacity, View } from 'react-native';

export default function Profile() {
  const { user } = useAuth();
  const router = useRouter();

  const handleBack = () => {
    router.back();
  };

  return (
    <ScrollView style={styles.container}>
      <View style={styles.header}>
        <TouchableOpacity style={styles.backButton} onPress={handleBack}>
           <Ionicons name="chevron-back" size={24} color="white" />
        </TouchableOpacity>
        <Text style={styles.headerTitle}>Profile</Text>
        <View style={{ width: 40 }} /> 
      </View>

      <View style={styles.content}>
        <View style={styles.avatarContainer}>
            <Image 
                source={{ uri: user?.photoURL || 'https://via.placeholder.com/150' }} 
                style={styles.avatar} 
            />
            <TouchableOpacity style={styles.cameraButton}>
                <Ionicons name="camera" size={20} color="white" />
            </TouchableOpacity>
        </View>

        <View style={styles.formGroup}>
            <Text style={styles.label}>Name</Text>
            <TextInput 
                style={styles.input} 
                value={user?.displayName || ''} 
                editable={false} 
            />
        </View>

        <View style={styles.formGroup}>
            <Text style={styles.label}>Email</Text>
            <TextInput 
                style={styles.input} 
                value={user?.email || ''} 
                editable={false} 
            />
        </View>

        <View style={styles.formGroup}>
            <Text style={styles.label}>Date of Birth</Text>
             <View style={styles.dateInputContainer}>
                <Text style={styles.dateText}>23/05/1995</Text>
                <Ionicons name="chevron-down" size={24} color="#000" />
            </View>
        </View>

        <View style={styles.spacer} />

        <TouchableOpacity style={styles.backActionButton} onPress={handleBack}>
            <Text style={styles.backActionText}>Back</Text>
        </TouchableOpacity>

      </View>
    </ScrollView>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#fff',
  },
  header: {
    flexDirection: 'row',
    alignItems: 'center',
    justifyContent: 'space-between',
    paddingHorizontal: 20,
    paddingTop: 60,
    backgroundColor: '#fff', 
  },
  backButton: {
    width: 40,
    height: 40,
    borderRadius: 12,
    backgroundColor: '#2196F3',
    alignItems: 'center',
    justifyContent: 'center',
  },
  headerTitle: {
    fontSize: 20,
    fontWeight: 'bold',
    color: '#2196F3',
  },
  content: {
    padding: 20,
    alignItems: 'center',
  },
  avatarContainer: {
    position: 'relative',
    marginBottom: 30,
  },
  avatar: {
    width: 120,
    height: 120,
    borderRadius: 60,
    borderWidth: 3,
    borderColor: '#ddd',
  },
  cameraButton: {
    position: 'absolute',
    bottom: 0,
    right: 0,
    backgroundColor: '#3b3b4f', 
    width: 36,
    height: 36,
    borderRadius: 18,
    alignItems: 'center',
    justifyContent: 'center',
    borderWidth: 2,
    borderColor: '#fff',
  },
  formGroup: {
    width: '100%',
    marginBottom: 20,
  },
  label: {
    fontSize: 16,
    fontWeight: 'bold',
    marginBottom: 8,
    color: '#000',
  },
  input: {
    width: '100%',
    borderWidth: 1,
    borderColor: '#eee',
    borderRadius: 8,
    padding: 15,
    fontSize: 16,
    color: '#555',
    backgroundColor: '#fff',
  },
   dateInputContainer: {
    flexDirection: 'row',
    alignItems: 'center',
    justifyContent: 'space-between',
     width: '100%',
    borderWidth: 1,
    borderColor: '#eee',
    borderRadius: 8,
    padding: 15,
    backgroundColor: '#fff',
   },
   dateText: {
     fontSize: 16,
    color: '#555',
   },
   spacer: {
       height: 100, 
   },
  backActionButton: {
    width: '100%',
    backgroundColor: '#2196F3',
    paddingVertical: 18,
    borderRadius: 30,
    alignItems: 'center',
    marginTop: 20,
  },
  backActionText: {
    color: '#fff',
    fontSize: 18,
    fontWeight: 'bold',
  },
});
