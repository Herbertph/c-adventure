<script setup>
import { ref, onMounted } from 'vue'
import { useAuthStore } from '@/stores/authStore'
import { useRouter } from 'vue-router'

const isDark = ref(false)
const router = useRouter()
const auth = useAuthStore()

onMounted(() => {
  isDark.value = document.documentElement.classList.contains('dark')
  auth.fetchUser()
})

const toggleDarkMode = () => {
  isDark.value = !isDark.value
  document.documentElement.classList.toggle('dark', isDark.value)
}

const logout = () => {
  auth.logout()
  router.push('/')
}
</script>

<template>
  <header class="bg-backgroundLight dark:bg-backgroundDark shadow-md sticky top-0 z-50">
    <div class="max-w-7xl mx-auto px-6 py-4 flex items-center justify-between">
      <router-link to="/" class="text-primary font-display text-xl font-bold">
        C# - Adventure
      </router-link>

      <div class="flex-1 flex justify-center text-textLight dark:text-textDark gap-6">
        <router-link to="/" class="hover:text-primary transition">Home</router-link>
        <router-link to="/content" class="hover:text-primary transition">Conteúdo</router-link>
        <router-link to="/pricing" class="hover:text-primary transition">Preços</router-link>
      </div>

      <div class="flex items-center gap-2 text-textLight dark:text-textDark">
        <template v-if="auth.user">
          <span class="font-semibold text-primary">{{ auth.user.firstName }}</span> /
          <button @click="logout" class="hover:text-primary transition">Logout</button>
        </template>
        <template v-else>
          <router-link to="/register" class="hover:text-primary transition">Cadastro</router-link> /
          <router-link to="/login" class="hover:text-primary transition">Login</router-link>
        </template>

        <button
          class="ml-2 text-textLight dark:text-textDark p-2 rounded hover:bg-gray-200 dark:hover:bg-gray-700 transition"
          @click="toggleDarkMode"
        >
          <span v-if="isDark">🌞</span>
          <span v-else>🌙</span>
        </button>
      </div>
    </div>
  </header>
</template>
