import { auth } from '@/config/firebase';
import { useAuth } from '@/contexts/AuthContext';
import { makeRedirectUri } from 'expo-auth-session';
import * as Google from 'expo-auth-session/providers/google';
import { useRouter } from 'expo-router';
import * as WebBrowser from 'expo-web-browser';
import { GoogleAuthProvider, signInWithCredential } from 'firebase/auth';
import { useEffect } from 'react';
import { Image, StyleSheet, Text, TouchableOpacity, View } from 'react-native';

WebBrowser.maybeCompleteAuthSession();

export default function Login() {
  const router = useRouter();
  const [request, response, promptAsync] = Google.useIdTokenAuthRequest({
    clientId: '565944352871-5o6ofd9o8eh5lr3noli2htlrhi4paaan.apps.googleusercontent.com', // TODO: Add your Web Client ID
    iosClientId: 'YOUR_IOS_CLIENT_ID', // TODO: Add your iOS Client ID
    androidClientId: 'YOUR_ANDROID_CLIENT_ID', // TODO: Add your Android Client ID
  });

  useEffect(() => {
    console.log("Redirect URI:", makeRedirectUri());
  }, []);

  const { user } = useAuth();
  
  useEffect(() => {
    if (response?.type === 'success') {
      const { id_token } = response.params;
      const credential = GoogleAuthProvider.credential(id_token);
      signInWithCredential(auth, credential);
    }
  }, [response]);

  return (
    <View style={styles.container}>
      <View style={styles.content}>
        <View style={styles.logoContainer}>
            {/* <Image 
                source={{uri: 'https://tools1s.com/images/dkmh/ut-logo.png'}} // Placeholder for UTH logo
                style={styles.logo}
                resizeMode="contain"
            /> */}
            <Text style={styles.uthText}>UTH</Text>
            <Text style={styles.subLogoText}>UNIVERSITY OF TRANSPORT HOCHIMINH CITY</Text>
        </View>

        <Text style={styles.appName}>SmartTasks</Text>
        <Text style={styles.tagline}>A simple and efficient to-do app</Text>

        <View style={styles.welcomeContainer}>
            <Text style={styles.welcomeTitle}>Welcome</Text>
            <Text style={styles.welcomeSubtitle}>Ready to explore? Log in to get started.</Text>
            
            <TouchableOpacity 
                style={styles.googleButton} 
                onPress={() => promptAsync()}
                disabled={!request}
            >
                <Image 
                    source={{uri: 'https://upload.wikimedia.org/wikipedia/commons/thumb/c/c1/Google_%22G%22_logo.svg/768px-Google_%22G%22_logo.svg.png'}} 
                    style={styles.googleIcon}
                />
                <Text style={styles.googleButtonText}>SIGN IN WITH GOOGLE</Text>
            </TouchableOpacity>


        </View>

        {/* Debug Info */}
        {/* <Text style={{ marginTop: 20, color: 'red', textAlign: 'center' }} selectable>
          Redirect URI (Copy this to Google Console):{'\n'}
          {makeRedirectUri()}
        </Text> */}

        <Text style={styles.footer}>© UTHSmartTasks</Text>
      </View>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#fff',
  },
  content: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
    paddingHorizontal: 20,
  },
  logoContainer: {
    alignItems: 'center',
    justifyContent: 'center',
    backgroundColor: '#E6F4FE',
    padding: 30,
    borderRadius: 20,
    marginBottom: 20,
    width: 200,
    height: 200,
  },
  logo: {
    width: 60,
    height: 60,
    marginBottom: 10,
    tintColor: '#008080',
  },
  uthText: {
    fontSize: 32,
    fontWeight: 'bold',
    color: '#008080',
    marginBottom: 5,
  },
  subLogoText: {
    fontSize: 10,
    color: '#FF6347',
    fontWeight: 'bold',
    textAlign: 'center',
    width: 120,
  },
  appName: {
    fontSize: 28,
    fontWeight: 'bold',
    color: '#2196F3',
    marginBottom: 5,
  },
  tagline: {
    fontSize: 14,
    color: '#666',
    marginBottom: 60,
  },
  welcomeContainer: {
    width: '100%',
    alignItems: 'center',
  },
  welcomeTitle: {
    fontSize: 18,
    fontWeight: '600',
    color: '#333',
    marginBottom: 5,
  },
  welcomeSubtitle: {
    fontSize: 14,
    color: '#666',
    marginBottom: 30,
  },
  googleButton: {
    flexDirection: 'row',
    alignItems: 'center',
    justifyContent: 'center',
    backgroundColor: '#E6F4FE',
    paddingVertical: 15,
    paddingHorizontal: 20,
    borderRadius: 8,
  },
  googleIcon: {
    width: 20,
    height: 20,
    marginRight: 10,
  },
  googleButtonText: {
    color: '#004080',
    fontWeight: 'bold',
    fontSize: 14,
  },
  footer: {
    position: 'absolute',
    bottom: 30,
    color: '#999',
    fontSize: 12,
  },
});
