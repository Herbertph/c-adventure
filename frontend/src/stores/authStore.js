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
      console.log('Fetching user data from /auth/me')
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
    console.log('Starting login with credentials:', credentials)
    try {
      isLoading.value = true
      console.log('Calling authApi.post /auth/login')
      const res = await authApi.post('/auth/login', credentials)
      console.log('Login response:', res)
      const newToken = res.data
      
      token.value = newToken
      console.log('Token set in store:', token.value)
      localStorage.setItem('token', newToken)
      console.log('Token stored in localStorage')
      
      console.log('Calling fetchUser')
      await fetchUser()
      console.log('Login successful, user after fetch:', user.value)
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
