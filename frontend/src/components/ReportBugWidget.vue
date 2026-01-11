<template>
  <!-- Floating Button -->
  <button
    class="fixed bottom-6 right-6 z-50 w-14 h-14 rounded-full
           bg-indigo-600 text-white shadow-lg
           flex items-center justify-center
           hover:bg-indigo-700 transition"
    @click="toggle"
    aria-label="Report a bug"
  >
    <span class="text-2xl font-bold">!</span>
  </button>

  <!-- Chat Window -->
  <transition name="fade-scale">
    <div
      v-if="open"
      class="fixed bottom-24 right-6 z-50 w-80
             bg-white dark:bg-zinc-800
             rounded-xl shadow-xl flex flex-col"
    >
      <!-- Header -->
      <div
        class="flex justify-between items-center px-4 py-3
               border-b dark:border-zinc-700
               bg-indigo-50 dark:bg-zinc-900 rounded-t-xl"
      >
        <h3 class="font-semibold text-indigo-700 dark:text-indigo-400">
          Report a bug
        </h3>
        <button
          @click="toggle"
          class="text-gray-400 hover:text-gray-600 dark:hover:text-gray-300"
        >
          ✕
        </button>
      </div>

      <!-- Body -->
      <div class="p-4 flex flex-col gap-3">
        <textarea
          v-model="message"
          placeholder="Describe the issue..."
          class="w-full h-24 p-3 text-sm rounded-lg resize-none
                 border border-gray-300
                 focus:outline-none focus:ring-2 focus:ring-indigo-500
                 dark:bg-zinc-900 dark:border-zinc-700 dark:text-white"
        />

        <button
          :disabled="loading || !message"
          @click="submit"
          class="bg-indigo-600 text-white py-2 rounded-lg
                 hover:bg-indigo-700 transition
                 disabled:opacity-50 disabled:cursor-not-allowed"
        >
          {{ loading ? 'Sending...' : 'Send' }}
        </button>

        <p v-if="success" class="text-sm text-green-600">
          Thank you! Your report was sent.
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
