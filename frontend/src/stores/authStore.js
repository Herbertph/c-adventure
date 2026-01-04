import { defineStore } from 'pinia'
import { ref } from 'vue'
import authApi from '@/services/authApi'

export const useAuthStore = defineStore('auth', () => {
  const user = ref(null)
  const isLoading = ref(false)

  const fetchUser = async () => {
    const token = localStorage.getItem('token')
    if (!token) return

    try {
      isLoading.value = true
      const res = await authApi.get('/auth/me')
      user.value = res.data
    } catch (err) {
      console.error('Erro ao buscar usuário', err)
      user.value = null
      localStorage.removeItem('token')
    } finally {
      isLoading.value = false
    }
  }

  const logout = () => {
    user.value = null
    localStorage.removeItem('token')
  }

  return { user, isLoading, fetchUser, logout }
})
