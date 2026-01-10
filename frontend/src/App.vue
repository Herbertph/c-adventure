<script setup>
import { onMounted } from 'vue'
import Header from './components/Header.vue'
import Footer from './components/Footer.vue'
import { useAuthStore } from '@/stores/authStore'

const auth = useAuthStore()

onMounted(async() => {
  await auth.fetchUser()
})
</script>

<template>
  <Header :key="JSON.stringify(auth.user)" />
  <router-view />
  <Footer />
</template>
