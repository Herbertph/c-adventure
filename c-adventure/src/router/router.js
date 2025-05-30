import { createRouter, createWebHashHistory } from 'vue-router'
import { useAuthStore } from '@/stores/authStore'

const router = createRouter({
  history: createWebHashHistory('/c-adventure/'),
  routes: [
    { path: '/', component: () => import('../pages/HomePage.vue') },
    { path: '/register', component: () => import('../pages/RegisterPage.vue') },
    { path: '/login', component: () => import('../pages/LoginPage.vue') },
    { path: '/me', component: () => import('../pages/UserPage.vue'), meta: { requiresAuth: true } },
    { path: '/lessons', component: () => import('../pages/LessonCardsPage.vue'), meta: { requiresAuth: true } },
    { path: '/lessons/:id', component: () => import('../pages/LessonView.vue'), meta: { requiresAuth: true } },
    { path: '/:pathMatch(.*)*', redirect: '/' }
  ]
})

// Protege rotas privadas usando meta + Pinia (caso queira usar o authStore depois)
router.beforeEach(async (to, from, next) => {
  const token = localStorage.getItem('token')

  if (to.meta.requiresAuth && !token) {
    next('/login')
  } else {
    next()
  }
})

export default router
