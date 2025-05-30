<template>
  <section
    v-if="lesson && lesson.title"
    class="min-h-screen p-8 bg-backgroundLight dark:bg-backgroundDark text-textLight dark:text-textDark"
  >
    <h1 class="text-3xl font-bold text-primary mb-6">{{ lesson.title }}</h1>
    <p class="mb-4 text-lg">{{ lesson.description }}</p>

    <div class="grid grid-cols-1 md:grid-cols-2 gap-8">
      <!-- Instruções -->
      <div class="bg-white dark:bg-zinc-800 p-6 rounded-lg shadow">
        <h2 class="text-xl font-semibold mb-2">Instruções</h2>
        <p>{{ lesson.description }}</p>
        <p class="mt-4 text-sm text-gray-500 dark:text-gray-300">
          Saída esperada: <code>{{ lesson.expectedOutput }}</code>
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
          <p class="text-green-600" v-if="result.success">✅ Código correto!</p>
          <p class="text-red-600" v-else>❌ Código incorreto.</p>
          <pre class="text-xs mt-2">
Expected: {{ result.expected }}
Actual: {{ result.actual }}
          </pre>
        </div>

        <!-- Próxima Lição -->
        <button
          v-if="result?.success && hasNextLesson"
          @click="goToNextLesson"
          class="mt-6 bg-green-600 text-white py-2 rounded hover:bg-green-700 transition"
        >
          Ir para a próxima lição
        </button>
      </div>
    </div>
  </section>

  <div v-else class="text-center py-20">Carregando lição...</div>
</template>

<script setup>
import { ref, onMounted, computed } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import authAxios from '@/axios'
import lessonApi from '@/axiosLesson'

const route = useRoute()
const router = useRouter()

const lesson = ref(null)
const code = ref('')
const result = ref(null)
const userId = ref(null)

const lessonId = computed(() => Number(route.params.id))
const hasNextLesson = computed(() => lessonId.value < 3) // ← ajuste conforme total de lições

onMounted(async () => {
  try {
    const res = await lessonApi.get(`/lessons/${lessonId.value}`)
    lesson.value = res.data
    code.value = res.data.initialCode
    console.log('Lição carregada:', lesson.value)
  } catch (err) {
    console.error('Erro ao carregar lição:', err)
  }

  try {
    const token = localStorage.getItem('token')
    console.log('Token carregado:', token)

    const resUser = await authAxios.get('/auth/me', {
      headers: { Authorization: `Bearer ${token}` },
    })

    console.log('Resposta completa de /auth/me:', resUser.data)
    userId.value = resUser.data.id
    console.log('Usuário carregado:', userId.value)
  } catch (err) {
    console.error('Erro ao carregar usuário:', err)
  }
})

const runCode = async () => {
  try {
    const res = await lessonApi.post('/execute', {
      lessonId: lesson.value.id,
      language: 'csharp',
      code: code.value,
      input: '',
    })

    if (res.data.success && userId.value) {
      await lessonApi.post('/progress', {
        userId: userId.value,
        lessonId: lesson.value.id,
      })
    }

    result.value = res.data
  } catch (err) {
    result.value = {
      success: false,
      expected: '',
      actual: 'Erro ao executar código.',
    }
    console.error(err)
  }
}

const goToNextLesson = () => {
  router.push(`/lessons/${lessonId.value + 1}`)
}
</script>
