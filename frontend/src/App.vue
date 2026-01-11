<script setup>
import { onMounted } from 'vue'
import Header from './components/Header.vue'
import Footer from './components/Footer.vue'
import { useAuthStore } from '@/stores/authStore'
import ReportBugWidget from '@/components/ReportBugWidget.vue'

const auth = useAuthStore()

onMounted(async() => {
  await auth.fetchUser()
})
</script>

<template>
  <Header />
  <router-view />
  <ReportBugWidget />
  <Footer />
</template>
