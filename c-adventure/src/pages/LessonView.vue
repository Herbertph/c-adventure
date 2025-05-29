<template>
    <section v-if="lesson && lesson.title" class="min-h-screen p-8 bg-backgroundLight dark:bg-backgroundDark text-textLight dark:text-textDark">
      <h1 class="text-3xl font-bold text-primary mb-6">{{ lesson.title }}</h1>
      <p class="mb-4 text-lg">{{ lesson.description }}</p>
  
      <div class="grid grid-cols-1 md:grid-cols-2 gap-8">
        <!-- Esquerda: Narrativa e instruções -->
        <div class="bg-white dark:bg-zinc-800 p-6 rounded-lg shadow">
          <h2 class="text-xl font-semibold mb-2">Instruções</h2>
          <p>{{ lesson.description }}</p>
          <p class="mt-4 text-sm text-gray-500 dark:text-gray-300">Saída esperada: <code>{{ lesson.expectedOutput }}</code></p>
        </div>
  
        <!-- Direita: Editor de código -->
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
  
          <div v-if="result" class="mt-4">
            <p class="text-green-600" v-if="result.success">✅ Código correto!</p>
            <p class="text-red-600" v-else>❌ Código incorreto.</p>
            <pre class="text-xs mt-2">
  Expected: {{ result.expected }}
  Actual: {{ result.actual }}
            </pre>
          </div>
        </div>
      </div>
    </section>
  
    <div v-else class="text-center py-20">Carregando lição...</div>
  </template>
  
  <script setup>
  import { ref, onMounted } from 'vue'
  import { useRoute } from 'vue-router'
  import axios from 'axios'
  
  const route = useRoute()
  const lesson = ref(null)
  const code = ref('')
  const result = ref(null)
  
  onMounted(async () => {
    const id = route.params.id
    try {
      const res = await axios.get(`http://localhost:8081/lessons/${id}`)
      lesson.value = res.data
      code.value = res.data.initialCode
      console.log('Lição carregada:', lesson.value)
    } catch (err) {
      console.error('Erro ao carregar lição:', err)
    }
  })
  
  const runCode = async () => {
    try {
      const res = await axios.post('http://localhost:8081/execute', {
        lessonId: lesson.value.id,
        language: 'csharp',
        code: code.value,
        input: '',
      })
  
      result.value = res.data
    } catch (err) {
      result.value = { success: false, expected: '', actual: 'Erro ao executar código.' }
      console.error(err)
    }
  }
  </script>
  