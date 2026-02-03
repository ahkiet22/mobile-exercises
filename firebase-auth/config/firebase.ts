import { initializeApp } from 'firebase/app';
import { getAuth } from 'firebase/auth';

// TODO: Add your Firebase configuration details here
const firebaseConfig = {
  apiKey: "AIzaSyBk8gCCeb3gJEeO4O3kZG6HrNBMVRRV0b4",
  authDomain: "e-wallet-da28e.firebaseapp.com",
  projectId: "e-wallet-da28e",
  storageBucket: "e-wallet-da28e.firebasestorage.app",
  messagingSenderId: "565944352871",
  appId: "1:565944352871:web:b08c87e8274820bd8b14c2"
};

const app = initializeApp(firebaseConfig);
export const auth = getAuth(app);
