import { defineStore } from 'pinia'
import { ref } from 'vue'
import authApi from '@/services/authApi' 

export const useAuthStore = defineStore('auth', () => {
  const user = ref(null)

  const fetchUser = async () => {
    const token = localStorage.getItem('token')
    if (!token) return

    try {
      const res = await authApi.get('/auth/me')
      user.value = res.data
    } catch (err) {
      console.error('Erro ao buscar usuário', err)
      user.value = null
      localStorage.removeItem('token')
      localStorage.removeItem('userId')
    }
  }

  const logout = () => {
    user.value = null
    localStorage.removeItem('token')
    localStorage.removeItem('userId')
  }

  return { user, fetchUser, logout }
})
