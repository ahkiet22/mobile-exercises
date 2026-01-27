import { initializeApp } from 'firebase/app';
import { getAuth } from 'firebase/auth';

// TODO: Add your Firebase configuration details here
const firebaseConfig = {
  
};

const app = initializeApp(firebaseConfig);
export const auth = getAuth(app);
