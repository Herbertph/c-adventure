<template>
    <section class="min-h-screen bg-backgroundLight dark:bg-backgroundDark text-textLight dark:text-textDark py-12">
      <div class="max-w-md mx-auto bg-white dark:bg-zinc-900 p-8 rounded-xl shadow-lg">
        <h1 class="text-2xl font-bold mb-6 text-center text-primary">Bem-vindo de volta!</h1>
        <form @submit.prevent="submitLogin" class="space-y-4">
          <div>
            <label class="block mb-1 font-semibold">Email</label>
            <input v-model="form.email" type="email" required class="w-full p-2 rounded border dark:bg-zinc-800 border-zinc-300 dark:border-zinc-700" />
          </div>
          <div>
            <label class="block mb-1 font-semibold">Senha</label>
            <input v-model="form.password" type="password" required class="w-full p-2 rounded border dark:bg-zinc-800 border-zinc-300 dark:border-zinc-700" />
          </div>
  
          <div v-if="error" class="text-red-500 text-sm">{{ error }}</div>
  
          <button type="submit" class="w-full bg-primary text-white p-2 rounded hover:opacity-90 transition">Login</button>
        </form>
  
        <p class="mt-4 text-sm text-center">
          Não tem uma conta?
          <router-link to="/register" class="text-primary hover:underline">Crie uma conta aqui</router-link>
        </p>
      </div>
    </section>
  </template>
  
<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { useAuthStore } from '@/stores/authStore'
import api from '@/services/api'


const router = useRouter()
const auth = useAuthStore()

const form = ref({
  email: '',
  password: '',
})

const error = ref('')

const submitLogin = async () => {
  error.value = ''

  try {
    const response = await api.post('/auth/login', form.value)

    localStorage.setItem('token', response.data)

    await auth.fetchUser()
    localStorage.setItem('userId', auth.user.id)

    router.push('/me')
  } catch (err) {
    error.value = err.response?.data || 'Invalid credentials.'
  }
}

</script>


  