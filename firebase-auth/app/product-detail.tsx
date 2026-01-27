import { Ionicons } from '@expo/vector-icons';
import { Stack, useRouter } from 'expo-router';
import React, { useEffect, useState } from 'react';
import { ActivityIndicator, Image, ScrollView, StyleSheet, Text, TouchableOpacity, View } from 'react-native';

interface Product {
  id: string;
  name: string;
  des: string;
  price: number;
  imgURL: string;
}

export default function ProductDetail() {
  const [product, setProduct] = useState<Product | null>(null);
  const [loading, setLoading] = useState(true);
  const router = useRouter();

  useEffect(() => {
    fetch('https://mock.apidog.com/m1/890655-872447-default/v2/product')
      .then((res) => res.json())
      .then((data) => {
        setProduct(data);
        setLoading(false);
      })
      .catch((error) => {
        console.error('Error fetching product:', error);
        setLoading(false);
      });
  }, []);

  if (loading) {
    return (
      <View style={styles.center}>
        <ActivityIndicator size="large" color="#FF4D4D" />
      </View>
    );
  }

  if (!product) {
    return (
      <View style={styles.center}>
        <Text>Failed to load product</Text>
      </View>
    );
  }

  return (
    <ScrollView style={styles.container} contentContainerStyle={styles.contentContainer}>
      <Stack.Screen 
        options={{ 
          title: 'Product detail',
          headerTitleStyle: {
            color: '#1A73E8',
            fontWeight: '600',
          },
          headerLeft: () => (
            <TouchableOpacity onPress={() => router.back()} style={{ marginLeft: 10, marginRight: 10 }}>
               <View style={styles.backButtonCircle}>
                 <Ionicons name="chevron-back" size={24} color="white" />
               </View>
            </TouchableOpacity>
          ),
          headerStyle: {
             backgroundColor: 'white'
          },
          headerShadowVisible: false,
        }} 
      />

      {/* Red Background Container for Image */}
      <View style={styles.imageContainer}>
        <Image 
            source={{ uri: product.imgURL }} 
            style={styles.image} 
            resizeMode="contain"
        />
      </View>

      <View style={styles.detailsContainer}>
        <Text style={styles.productName}>{product.name}</Text>
        <Text style={styles.price}>Giá: {product.price.toLocaleString('vi-VN')}đ</Text>
        
        <Text style={styles.description}>
          {product.des}
        </Text>
      </View>

    </ScrollView>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#fff',
  },
  contentContainer: {
    padding: 20,
  },
  center: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
  },
  backButtonCircle: {
    width: 32,
    height: 32,
    borderRadius: 12,
    backgroundColor: '#1A73E8', // Light blue circle for back button
    justifyContent: 'center',
    alignItems: 'center',
  },
  imageContainer: {
    backgroundColor: '#FF4D4D', // The red color from the image
    borderRadius: 20,
    height: 300,
    justifyContent: 'center',
    alignItems: 'center',
    marginBottom: 20,
    padding: 20,
    position: 'relative',
  },
  image: {
    width: '100%',
    height: '100%',
  },
  detailsContainer: {
    paddingHorizontal: 10,
  },
  productName: {
    fontSize: 18,
    fontWeight: 'bold',
    color: '#000',
    marginBottom: 10,
  },
  price: {
    fontSize: 18,
    fontWeight: 'bold',
    color: '#FF0000', // Red price
    marginBottom: 20,
  },
  description: {
    fontSize: 14,
    color: '#666',
    lineHeight: 22,
    textAlign: 'justify',
  },
});
