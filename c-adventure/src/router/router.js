import { createRouter, createWebHashHistory } from 'vue-router'

const router = createRouter({
  history: createWebHashHistory('/c-adventure/'),
  routes: [
    { path: '/', component: () => import('../pages/HomePage.vue') },
    { path: '/register', component: () => import('../pages/RegisterPage.vue') },
    { path: '/login', component: () => import('../pages/LoginPage.vue') },
    { path: '/me', component: () => import('../pages/UserPage.vue') },
    { path: '/lessons', component: () => import('../pages/LessonCardsPage.vue') },
    { path: '/lessons/:id', component: () => import('../pages/LessonView.vue') },
  ]
})

// Protege rotas privadas
router.beforeEach((to, from, next) => {
  const token = localStorage.getItem('token')
  if (['/me', '/lessons', '/lessons/:id'].includes(to.path) && !token) {
    next('/login')
  } else {
    next()
  }
})

export default router
