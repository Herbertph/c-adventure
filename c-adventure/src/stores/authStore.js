import { defineStore } from 'pinia'
import { ref } from 'vue'
import axios from 'axios'

export const useAuthStore = defineStore('auth', () => {
  const user = ref(null)

  const fetchUser = async () => {
    const token = localStorage.getItem('token')
    if (!token) return

    try {
      const response = await axios.get('http://localhost:8080/auth/me', {
        headers: { Authorization: `Bearer ${token}` }
      })
      user.value = response.data
    } catch (e) {
      console.warn('Erro ao buscar usuário', e)
      user.value = null
      localStorage.removeItem('token')
    }
  }

  const logout = () => {
    user.value = null
    localStorage.removeItem('token')
  }

  return { user, fetchUser, logout }
})
