import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import path from 'path'

export default defineConfig({
  plugins: [vue()],

  resolve: {
    alias: {
      '@': path.resolve(__dirname, './src'),
    },
  },

  server: {
    proxy: {
      '/auth': {
        target: 'http://auth-service:8080',
        changeOrigin: true,
      },
      '/lessons': {
        target: 'http://lesson-service:8080',
        changeOrigin: true,
      },
      '/progress': {
      target: 'http://lesson-service:8080',
      changeOrigin: true,
    },
    },
  },
})

