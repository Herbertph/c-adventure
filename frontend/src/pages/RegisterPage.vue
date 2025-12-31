<template>
    <section class="min-h-screen bg-backgroundLight dark:bg-backgroundDark text-textLight dark:text-textDark py-12">
      <div class="max-w-md mx-auto bg-white dark:bg-zinc-900 p-8 rounded-xl shadow-lg">
        <h1 class="text-2xl font-bold mb-6 text-center text-primary">Crie sua conta</h1>
        <form @submit.prevent="submitForm" class="space-y-4">
          <div>
            <label class="block mb-1 font-semibold">Nome</label>
            <input v-model="form.firstName" type="text" required class="w-full p-2 rounded border dark:bg-zinc-800 border-zinc-300 dark:border-zinc-700" />
          </div>
          <div>
            <label class="block mb-1 font-semibold">Sobrenome</label>
            <input v-model="form.lastName" type="text" required class="w-full p-2 rounded border dark:bg-zinc-800 border-zinc-300 dark:border-zinc-700" />
          </div>
          <div>
            <label class="block mb-1 font-semibold">Email</label>
            <input v-model="form.email" type="email" required class="w-full p-2 rounded border dark:bg-zinc-800 border-zinc-300 dark:border-zinc-700" />
          </div>
          <div>
            <label class="block mb-1 font-semibold">Senha</label>
            <input v-model="form.password" type="password" required class="w-full p-2 rounded border dark:bg-zinc-800 border-zinc-300 dark:border-zinc-700" />
          </div>
          <div>
            <label class="block mb-1 font-semibold">Confirmar Senha</label>
            <input v-model="form.confirmPassword" type="password" required class="w-full p-2 rounded border dark:bg-zinc-800 border-zinc-300 dark:border-zinc-700" />
          </div>
  
          <div v-if="error" class="text-red-500 text-sm">{{ error }}</div>
          <div v-if="success" class="text-green-500 text-sm">{{ success }}</div>
  
          <button type="submit" class="w-full bg-primary text-white p-2 rounded hover:opacity-90 transition">Register</button>
        </form>
  
        <p class="mt-4 text-sm text-center">
          Já tem uma conta?
          <router-link to="/login" class="text-primary hover:underline">Faça login aqui</router-link>
        </p>
      </div>
    </section>
  </template>
  
<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import api from '@/services/api'


const router = useRouter()

const form = ref({
  firstName: '',
  lastName: '',
  email: '',
  password: '',
  confirmPassword: '',
})

const error = ref('')
const success = ref('')

const submitForm = async () => {
  error.value = ''
  success.value = ''

  if (form.value.password !== form.value.confirmPassword) {
    error.value = 'Passwords do not match.'
    return
  }

  try {
    await api.post('/auth/register', form.value)

    success.value = 'Registration successful! Redirecting...'

    setTimeout(() => {
      router.push('/login')
    }, 1500)
  } catch (err) {
    error.value = err.response?.data || 'An error occurred.'
  }
}
</script>
  