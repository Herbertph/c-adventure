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
import { ref, onMounted, watch } from 'vue'
import { useRouter } from 'vue-router'
import { useAuthStore } from '@/stores/auth'
import lessonApi from '@/services/lessonApi'

const router = useRouter()
const authStore = useAuthStore()

const lessons = ref([
  { id: 1, title: 'Lesson 1: Hello World', locked: false, completed: false },
  { id: 2, title: 'Lesson 2: Number to String', locked: true, completed: false },
  { id: 3, title: 'Lesson 3: Conditionals', locked: true, completed: false },
])

const loadProgress = async (userId) => {
  if (!userId) return

  try {
    const res = await lessonApi.get(`/progress/${userId}`)
    const completedIds = res.data.map(Number) // blindagem extra

    lessons.value = lessons.value.map((lesson, index, arr) => {
      const completed = completedIds.includes(lesson.id)
      const previousCompleted =
        index === 0 || completedIds.includes(arr[index - 1].id)

      return {
        ...lesson,
        completed,
        locked: !completed && !previousCompleted,
      }
    })
  } catch (err) {
    console.error('Erro ao carregar progresso:', err)
  }
}


watch(
  () => authStore.user,
  (user) => {
    if (user?.id) {
      loadProgress(user.id)
    }
  },
  { immediate: true }
)

function goToLesson(lesson) {
  if (lesson.locked) return
  router.push(`/lessons/${lesson.id}`)
}
</script>