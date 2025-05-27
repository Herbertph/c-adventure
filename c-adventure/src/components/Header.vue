<template>
  <header class="bg-backgroundLight dark:bg-backgroundDark shadow-md sticky top-0 z-50">
    <div class="max-w-7xl mx-auto px-6 py-4 flex items-center justify-between">
      
      <!-- Esquerda: Logo -->
      <router-link to="/" class="text-primary font-display text-xl font-bold">
        C# - Adventure
      </router-link>

      <!-- Centro: Navegação -->
      <div class="flex-1 flex justify-center text-textLight dark:text-textDark gap-6">
        <router-link to="/" class="hover:text-primary transition">Home</router-link>
        <router-link to="/content" class="hover:text-primary transition">Conteúdo</router-link>
        <router-link to="/pricing" class="hover:text-primary transition">Preços</router-link>
      </div>

      <!-- Direita: Acesso -->
      <div class="flex items-center gap-2 text-textLight dark:text-textDark">
        <router-link to="/register" class="hover:text-primary transition">Sign In</router-link> /
        <router-link to="/login" class="hover:text-primary transition">Login</router-link>
        <button
          class="ml-2 text-textLight dark:text-textDark p-2 rounded hover:bg-gray-200 dark:hover:bg-gray-700 transition"
          @click="toggleDarkMode"
          :aria-label="isDark ? 'Ativar modo claro' : 'Ativar modo escuro'"
        >
          <span v-if="isDark">🌞</span>
          <span v-else>🌙</span>
        </button>
      </div>
    </div>
  </header>
</template>

<script setup>
import { ref, onMounted } from 'vue'

const isDark = ref(false)

onMounted(() => {
  isDark.value = document.documentElement.classList.contains('dark')
})

const toggleDarkMode = () => {
  isDark.value = !isDark.value
  document.documentElement.classList.toggle('dark', isDark.value)
}
</script>
