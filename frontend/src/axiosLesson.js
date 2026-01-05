import axios from 'axios'

const lessonApi = axios.create({
  baseURL: import.meta.env.VITE_API_LESSON_URL,
})

lessonApi.interceptors.request.use(config => {
  const token = localStorage.getItem('token')
  if (token) {
    config.headers.Authorization = `Bearer ${token}`
  }
  return config
})

export default lessonApi
