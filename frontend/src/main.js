import { createApp } from 'vue'
import './style.css'
import App from './App.vue'
import { MotionPlugin } from '@vueuse/motion'
import router from './router/router'
import { createPinia } from 'pinia'

const app = createApp(App)
app.use(MotionPlugin)
app.use(router)
app.use(createPinia())
app.mount('#app')

