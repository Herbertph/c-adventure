<template>
  <Transition name="fade-slide" mode="out-in" appear>
    <template v-if="lesson">
      <section
        class="min-h-screen p-8 bg-backgroundLight dark:bg-backgroundDark text-textLight dark:text-textDark"
      >
        <h1 class="text-3xl font-bold text-primary mb-6">
          {{ lesson.title }}
        </h1>

        <!-- Narrativa -->
        <p class="mb-6 text-lg">
          {{ lesson.description }}
        </p>

        <div class="grid grid-cols-1 md:grid-cols-2 gap-8">
          <!-- Instruções -->
          <div class="bg-white dark:bg-zinc-800 p-6 rounded-lg shadow">
            <h2 class="text-xl font-semibold mb-2">Instruções</h2>

            <p>{{ lesson.instruction }}</p>

            <p class="mt-4 text-sm text-gray-500 dark:text-gray-300">
              Saída esperada:
              <code>{{ lesson.expectedOutput }}</code>
            </p>
          </div>

          <!-- Editor -->
          <div class="bg-white dark:bg-zinc-800 p-6 rounded-lg shadow flex flex-col">
            <h2 class="text-xl font-semibold mb-2">Seu código</h2>

            <textarea
              v-model="code"
              class="h-64 p-3 rounded border border-gray-300 dark:border-gray-700 dark:bg-zinc-900 font-mono text-sm"
            ></textarea>

            <button
              @click="runCode"
              class="mt-4 bg-primary text-white py-2 rounded hover:opacity-90"
            >
              RUN
            </button>

            <!-- Resultado -->
            <div v-if="result" class="mt-4">
              <p v-if="result.success" class="text-green-600">
                ✅ Código correto!
              </p>
              <p v-else class="text-red-600">
                ❌ Código incorreto.
              </p>

              <pre class="text-xs mt-2">
Expected: {{ result.expected }}
Actual: {{ result.actual }}
              </pre>
            </div>

            <!-- Próxima Lição -->
            <button
              v-if="result?.success && nextLessonId"
              @click="goToNextLesson"
              class="mt-6 bg-green-600 text-white py-2 rounded hover:bg-green-700 transition"
            >
              Ir para a próxima lição
            </button>
          </div>
        </div>
      </section>
    </template>

    <div v-else class="text-center py-20">
      Carregando lição...
    </div>
  </Transition>
</template>

<script setup>
import { ref, onMounted, watch, computed } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import lessonApi from '@/services/lessonApi'

const route = useRoute()
const router = useRouter()

const lesson = ref(null)
const lessons = ref([])

const code = ref('')
const result = ref(null)

// -----------------------
// Fetch lessons list
// -----------------------
const fetchLessons = async () => {
  const res = await lessonApi.get('/lessons')
  lessons.value = res.data
}

// -----------------------
// Fetch single lesson
// -----------------------
const fetchLesson = async () => {
  try {
    const res = await lessonApi.get(`/lessons/${route.params.id}`)
    lesson.value = res.data
    code.value = res.data.initialCode
    result.value = null
  } catch (err) {
    if (err.response?.status === 403) {
      router.push('/lessons')
    } else {
      console.error(err)
    }
  }
}

onMounted(async () => {
  await fetchLessons()
  await fetchLesson()
})

watch(() => route.params.id, fetchLesson)

// -----------------------
// Próxima lição baseada em orderIndex
// -----------------------
const nextLessonId = computed(() => {
  if (!lesson.value || lessons.value.length === 0) return null

  const next = lessons.value.find(
    l => l.orderIndex === lesson.value.orderIndex + 1
  )

  return next?.id ?? null
})

// -----------------------
// Run code
// -----------------------
const runCode = async () => {
  try {
    const res = await lessonApi.post('/lessons/submit', {
      lessonId: lesson.value.id,
      language: 'csharp',
      code: code.value,
      input: ''
    })

    result.value = res.data
  } catch (err) {
    result.value = {
      success: false,
      expected: '',
      actual: 'Erro ao executar código.'
    }
  }
}

// -----------------------
const goToNextLesson = () => {
  router.push(`/lessons/${nextLessonId.value}`)
}
</script>

<style scoped>
.fade-slide-enter-active,
.fade-slide-leave-active {
  transition: all 0.5s ease;
}
.fade-slide-enter-from {
  opacity: 0;
  transform: translateY(20px);
}
.fade-slide-leave-to {
  opacity: 0;
  transform: translateY(-20px);
}
</style>
