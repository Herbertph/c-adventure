<script setup>
import { onMounted } from 'vue'
import Header from './components/Header.vue'
import Footer from './components/Footer.vue'
import { useAuthStore } from '@/stores/authStore'

const auth = useAuthStore()

onMounted(async() => {
  console.log('App onMounted, calling fetchUser')
  await auth.fetchUser()
  console.log('App fetchUser done, user:', auth.user)
})
</script>

<template>
  <Header />
  <router-view />
  <Footer />
</template>
