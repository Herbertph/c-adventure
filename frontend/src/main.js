import { createApp } from 'vue'
import './style.css'
import App from './App.vue'
import { MotionPlugin } from '@vueuse/motion'
import router from './router/router'
import { createPinia } from 'pinia'
import { useAuthStore } from '@/stores/authStore'

const app = createApp(App)

const pinia = createPinia()

app.use(MotionPlugin)
app.use(router)
app.use(pinia)

// 🔑 AQUI é o ponto certo
const auth = useAuthStore()
auth.fetchUser()

app.mount('#app')
