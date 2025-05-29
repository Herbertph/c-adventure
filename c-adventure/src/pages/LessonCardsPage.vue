<template>
    <div class="min-h-screen bg-gray-100 dark:bg-gray-900 p-8">
      <h1 class="text-3xl font-bold text-center mb-8 text-gray-900 dark:text-white">
        Your Lessons
      </h1>
  
      <div class="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-6">
        <div
          v-for="lesson in lessons"
          :key="lesson.id"
          class="p-6 rounded-2xl shadow-md border transition-all cursor-pointer"
          :class="{
            'bg-white dark:bg-gray-800 border-gray-300 dark:border-gray-700 hover:shadow-lg': !lesson.locked,
            'bg-gray-200 dark:bg-gray-700 border-gray-400 cursor-not-allowed opacity-50': lesson.locked,
            'border-green-500 ring-2 ring-green-400': lesson.completed
          }"
          @click="goToLesson(lesson)"
        >
          <h2 class="text-xl font-semibold mb-2 text-gray-800 dark:text-white">
            {{ lesson.title }}
          </h2>
          <p class="text-sm text-gray-600 dark:text-gray-300">
            {{ lesson.locked ? 'Locked' : lesson.completed ? 'Completed' : 'Ready' }}
          </p>
        </div>
      </div>
    </div>
  </template>
  
  <script setup>
  import { useRouter } from 'vue-router'
  
  const router = useRouter()
  
  // Simulado: mais tarde virá de um endpoint
  const lessons = [
    { id: 1, title: 'Lesson 1: Hello World', locked: false, completed: false },
    { id: 2, title: 'Lesson 2: Number to String', locked: true, completed: false },
    { id: 3, title: 'Lesson 3: Conditionals', locked: true, completed: false },
  ]
  
  function goToLesson(lesson) {
    if (lesson.locked) return
    router.push(`/lessons/${lesson.id}`)
  }
  </script>
  
  <style scoped>
  </style>
  