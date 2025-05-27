<template>
    <section class="min-h-screen bg-backgroundLight dark:bg-backgroundDark text-textLight dark:text-textDark py-12">
      <div class="max-w-md mx-auto bg-white dark:bg-zinc-900 p-8 rounded-xl shadow-lg">
        <h1 class="text-2xl font-bold mb-6 text-center text-primary">Your Profile</h1>
  
        <div v-if="user" class="space-y-4">
          <p><strong>First Name:</strong> {{ user.firstName }}</p>
          <p><strong>Last Name:</strong> {{ user.lastName }}</p>
          <p><strong>Email:</strong> {{ user.email }}</p>
        </div>
  
        <div v-else-if="error" class="text-red-500 text-sm text-center">
          {{ error }}
        </div>
  
        <div v-else class="text-center">Loading...</div>
  
        <button
          @click="logout"
          class="mt-6 w-full bg-red-600 text-white p-2 rounded hover:opacity-90 transition"
        >
          Logout
        </button>
      </div>
    </section>
  </template>
  
  <script setup>
  import { ref, onMounted } from 'vue'
  import axios from 'axios'
  import { useRouter } from 'vue-router'
  
  const user = ref(null)
  const error = ref('')
  const router = useRouter()
  
  onMounted(async () => {
  const token = localStorage.getItem('token')
  console.log('Token carregado:', token) // 👈 Adicione isto

  try {
    const response = await axios.get('http://localhost:8080/auth/me', {
      headers: {
        Authorization: `Bearer ${token}`, // 👈 Certifique-se do espaço depois de "Bearer"
      },
    })
    user.value = response.data
  } catch (err) {
    error.value = 'Failed to load user data.'
    console.error('Erro ao carregar usuário:', err) // 👈 log completo
  }
})
  
  const logout = () => {
    localStorage.removeItem('token')
    router.push('/login')
  }
  </script>
  