<template>
    <header class="bg-backgroundLight dark:bg-backgroundDark shadow-md sticky top-0 z-50">
      <div class="max-w-7xl mx-auto px-6 py-4 flex items-center justify-between">
        
        <!-- Logo -->
        <div class="text-primary font-display text-xl font-bold">
          C-Adventure
        </div>
  
        <!-- Navegação -->
        <nav class="hidden md:flex items-center gap-6 text-textLight dark:text-textDark">
          <a href="#" class="hover:text-primary transition">Home</a>
          <a href="#" class="hover:text-primary transition">Sign In</a>
          <a href="#" class="hover:text-primary transition">Login</a>
        </nav>
  
        <!-- Toggle dark mode -->
        <button
          class="ml-4 text-textLight dark:text-textDark p-2 rounded hover:bg-gray-200 dark:hover:bg-gray-700 transition"
          @click="toggleDarkMode"
          :aria-label="isDark ? 'Ativar modo claro' : 'Ativar modo escuro'"
        >
          <span v-if="isDark">🌞</span>
          <span v-else>🌙</span>
        </button>
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
  