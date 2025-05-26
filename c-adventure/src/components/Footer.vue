<template>
    <footer class="bg-backgroundDark dark:bg-black text-textDark dark:text-textLight py-10 shadow-top-sm">
      <div class="max-w-7xl mx-auto px-6 flex flex-col md:flex-row justify-between items-center gap-6">
  
        <!-- Branding -->
        <div class="text-center md:text-left space-y-2">
          <h3 class="text-xl font-display font-bold text-primary">C-Adventure</h3>
          <p class="text-sm opacity-80">
            © {{ new Date().getFullYear() }} Todos os direitos reservados.
          </p>
        </div>
  
       <!-- Redes sociais -->
<div class="flex gap-4 text-xl">
  <a href="https://twitter.com" target="_blank" aria-label="Twitter" class="hover:text-primary transition">
    <Twitter class="w-5 h-5" />
  </a>
  <a href="https://instagram.com" target="_blank" aria-label="Instagram" class="hover:text-primary transition">
    <Instagram class="w-5 h-5" />
  </a>
  <a href="https://linkedin.com" target="_blank" aria-label="LinkedIn" class="hover:text-primary transition">
    <Linkedin class="w-5 h-5" />
  </a>
</div>
  
      </div>
    </footer>
  </template>
  
  <script setup>
import { Twitter, Instagram, Linkedin } from 'lucide-vue-next'
</script>