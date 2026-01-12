<script setup>
import { ref, onMounted, computed, watch } from 'vue'
import { useAuthStore } from '@/stores/authStore'
import { storeToRefs } from 'pinia'
import { useRouter, useRoute } from 'vue-router'

const isDark = ref(false)
const router = useRouter()
const route = useRoute()
const auth = useAuthStore()

const { user } = storeToRefs(auth)

onMounted(async () => {
  isDark.value = document.documentElement.classList.contains('dark')

  if (!user.value && auth.token) {
    await auth.fetchUser()
  }
})

watch(user, (newUser) => {
}, { deep: true })

const isLoggedIn = computed(() => Boolean(user.value))

const toggleDarkMode = () => {
  isDark.value = !isDark.value
  document.documentElement.classList.toggle('dark', isDark.value)
}

const logout = () => {
  auth.logout()
  router.push('/')
}

// 🔹 NAVEGAÇÃO INTELIGENTE
const handleNavigation = (sectionId) => {
  if (route.path === '/') {
    const element = document.getElementById(sectionId)
    if (element) {
      element.scrollIntoView({ behavior: 'smooth' })
    }
  } else {
    router.push({ path: '/', hash: `#${sectionId}` })
  }
}

const handleContentNavigation = () => {
  if (route.path === '/') {
    handleNavigation('conteudo')
  } else {
    router.push('/content')
  }
}

const handlePricingNavigation = () => {
  if (isLoggedIn.value) {
    router.push('/lessons')
  } else {
    handleContentNavigation()
  }
}
</script>


<template>
  <header class="bg-backgroundLight dark:bg-backgroundDark shadow-md sticky top-0 z-50">
    <div class="max-w-7xl mx-auto px-6 py-4 flex items-center justify-between">
      <router-link to="/" class="text-primary font-display text-xl font-bold">
        C# - Adventure
      </router-link>

      <div class="flex-1 flex justify-center text-textLight dark:text-textDark gap-6">
        <button @click="handleNavigation('home', '/')" class="hover:text-primary transition">Home</button>
        <button @click="handleContentNavigation('content')" class="hover:text-primary transition">Content</button>
        <button @click="handlePricingNavigation('pricing')" class="hover:text-primary transition">
          {{ user ? 'Lessons' : 'Prices' }}
        </button>
      </div>

      <div class="flex items-center gap-2 text-textLight dark:text-textDark">
        <template v-if="user">
          <router-link
            to="/me"
            class="font-semibold text-primary hover:underline transition"
          >
            {{ user.firstName }}
          </router-link>
          /
          <button @click="logout" class="hover:text-primary transition">Logout</button>
        </template>
        <template v-else>
          <router-link to="/register" class="hover:text-primary transition">Register</router-link> /
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