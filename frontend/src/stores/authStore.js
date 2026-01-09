import { defineStore } from 'pinia'
import { ref } from 'vue'
import authApi from '@/services/authApi'

export const useAuthStore = defineStore('auth', () => {
  const user = ref(null)
  const token = ref(localStorage.getItem('token') || null)
  const isLoading = ref(false)

  const fetchUser = async () => {
    const storedToken = token.value || localStorage.getItem('token')
    console.log('fetchUser called, token:', storedToken)
    if (!storedToken) {
      console.log('No token found, returning')
      return
    }

    try {
      isLoading.value = true
      console.log('Fetching user data...')
      const res = await authApi.get('/auth/me')
      console.log('User data received:', res.data)
      user.value = res.data
      console.log('user.value updated:', user.value)
    } catch (err) {
      console.error('Erro ao buscar usuário', err)
      user.value = null
      token.value = null
      localStorage.removeItem('token')
    } finally {
      isLoading.value = false
    }
  }

  const login = async (credentials) => {
    try {
      isLoading.value = true
      const res = await authApi.post('/auth/login', credentials)
      const newToken = res.data
      
      token.value = newToken
      localStorage.setItem('token', newToken)
      
      await fetchUser()
      console.log('Login successful, user:', user.value)
      return true
    } catch (err) {
      console.error('Erro ao fazer login', err)
      token.value = null
      user.value = null
      localStorage.removeItem('token')
      throw err
    } finally {
      isLoading.value = false
    }
  }

  const logout = () => {
    user.value = null
    token.value = null
    localStorage.removeItem('token')
  }

  return { user, token, isLoading, fetchUser, login, logout }
})
