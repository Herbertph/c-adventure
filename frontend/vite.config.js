import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import path from 'path'

const isDev = process.env.NODE_ENV === 'development'

export default defineConfig({
  plugins: [vue()],

  resolve: {
    alias: {
      '@': path.resolve(__dirname, './src'),
    },
  },

  server: isDev
    ? {
        proxy: {
          '/auth': {
            target: 'http://localhost:8080',
            changeOrigin: true,
          },
          '/lessons': {
            target: 'http://localhost:8082',
            changeOrigin: true,
          },
          '/progress': {
            target: 'http://localhost:8082',
            changeOrigin: true,
          },
        },
      }
    : undefined,

    // PROD
  preview: {
  host: true,
  port: process.env.PORT || 4173,
  allowedHosts: true,
},
})
