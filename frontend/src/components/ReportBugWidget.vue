<template>
  <!-- Floating Button -->
  <button
    class="fixed bottom-6 right-6 z-50 w-14 h-14 rounded-full bg-red-600 text-white shadow-lg flex items-center justify-center hover:bg-red-700 transition"
    @click="toggle"
  >
    🐞
  </button>

  <!-- Chat Window -->
  <transition name="fade-scale">
    <div
      v-if="open"
      class="fixed bottom-24 right-6 z-50 w-80 bg-white dark:bg-zinc-800 rounded-xl shadow-xl flex flex-col"
    >
      <!-- Header -->
      <div class="flex justify-between items-center px-4 py-3 border-b dark:border-zinc-700">
        <h3 class="font-semibold text-gray-800 dark:text-white">
          Report a bug
        </h3>
        <button @click="toggle" class="text-gray-500 hover:text-red-500">
          ✕
        </button>
      </div>

      <!-- Body -->
      <div class="p-4 flex flex-col gap-3">
        <textarea
          v-model="message"
          placeholder="Describe the issue..."
          class="w-full h-24 p-2 text-sm border rounded resize-none dark:bg-zinc-900 dark:border-zinc-700"
        />

        <button
          :disabled="loading || !message"
          @click="submit"
          class="bg-red-600 text-white py-2 rounded hover:bg-red-700 disabled:opacity-50"
        >
          {{ loading ? 'Sending...' : 'Send' }}
        </button>

        <p v-if="success" class="text-green-600 text-sm">
          Thanks! Your report was sent.
        </p>
      </div>
    </div>
  </transition>
</template>

<script setup>
import { ref } from 'vue'

const open = ref(false)
const message = ref('')
const loading = ref(false)
const success = ref(false)

const toggle = () => {
  open.value = !open.value
  success.value = false
}

const submit = async () => {
  loading.value = true

  try {
    await fetch('https://formspree.io/f/xpqqzaja', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({
        message: message.value,
        page: window.location.href,
        userAgent: navigator.userAgent,
      }),
    })

    success.value = true
    message.value = ''
  } catch (err) {
    alert('Failed to send bug report')
  } finally {
    loading.value = false
  }
}
</script>

<style scoped>
.fade-scale-enter-active,
.fade-scale-leave-active {
  transition: all 0.25s ease;
}

.fade-scale-enter-from,
.fade-scale-leave-to {
  opacity: 0;
  transform: scale(0.95);
}
</style>
