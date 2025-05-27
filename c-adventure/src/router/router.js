import { createRouter, createWebHashHistory } from 'vue-router'

const router = createRouter({
  history: createWebHashHistory('/c-adventure/'),
  routes: [
    { path: '/', component: () => import('../pages/HomePage.vue') },
    { path: '/register', component: () => import('../pages/RegisterPage.vue') },
    { path: '/login', component: () => import('../pages/LoginPage.vue') },
    { path: '/me', component: () => import('../pages/UserPage.vue') },
  ]
})


router.beforeEach((to, from, next) => {
  const token = localStorage.getItem('token')
  if (to.path === '/me' && !token) {
    next('/login')
  } else {
    next()
  }
})

export default router
