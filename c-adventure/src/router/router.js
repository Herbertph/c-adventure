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
    { path: '/:pathMatch(.*)*', redirect: '/' } // fallback para rotas inválidas
  ]
})

// Protege rotas privadas
router.beforeEach((to, from, next) => {
  const token = localStorage.getItem('token')

  const protectedPaths = ['/me', '/lessons']
  const isProtected = protectedPaths.some(p => to.path.startsWith(p))

  if (isProtected && !token) {
    next('/login')
  } else {
    next()
  }
})

export default router
