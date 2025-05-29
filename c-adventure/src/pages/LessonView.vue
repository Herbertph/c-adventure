<template>
    <div class="min-h-screen grid grid-cols-1 md:grid-cols-2 bg-gray-100 dark:bg-gray-900">
      <!-- Esquerda: Instruções -->
      <div class="p-6 md:p-10 border-r border-gray-300 dark:border-gray-700">
        <h2 class="text-2xl font-bold mb-4 text-gray-800 dark:text-white">{{ lesson.title }}</h2>
        <p class="text-gray-700 dark:text-gray-300 whitespace-pre-line">
          {{ lesson.description }}
        </p>
      </div>
  
      <!-- Direita: Editor de código -->
      <div class="p-6 md:p-10">
        <textarea
          v-model="code"
          class="w-full h-64 p-4 rounded-lg border shadow-sm text-sm font-mono bg-white dark:bg-gray-800 dark:text-white resize-none"
        ></textarea>
  
        <div class="mt-4 flex items-center gap-4">
          <button
            @click="runCode"
            class="bg-blue-600 hover:bg-blue-700 text-white px-4 py-2 rounded-lg shadow-md disabled:opacity-50"
            :disabled="loading"
          >
            RUN
          </button>
          <span v-if="result" class="text-sm text-gray-700 dark:text-gray-300">
            Resultado: {{ result.success ? '✅ Correto' : '❌ Incorreto' }}
          </span>
        </div>
  
        <div class="mt-2">
          <p v-if="result" class="text-sm text-gray-500 dark:text-gray-400">
            <strong>Esperado:</strong> {{ result.expected }}<br />
            <strong>Atual:</strong> {{ result.actual }}
          </p>
  
          <button
            v-if="result?.success"
            class="mt-4 bg-green-600 hover:bg-green-700 text-white px-4 py-2 rounded-lg"
          >
            Avançar para a próxima lição
          </button>
        </div>
      </div>
    </div>
  </template>
  
  <script setup>
  import { ref, onMounted } from 'vue'
  import { useRoute } from 'vue-router'
  import axios from 'axios'
  
  const route = useRoute()
  const lessonId = route.params.id
  
  const lesson = ref({
    id: lessonId,
    title: `Lesson ${lessonId}`,
    description: 'Desafio: Transforme um número inteiro em uma string.\n\nExemplos:\n123 => "123"\n-100 => "-100"',
    initialCode: 'public class Kata {\n  public static string NumberToString(int num) {\n    // seu código aqui\n  }\n}'
  })
  
  const code = ref(lesson.value.initialCode)
  const result = ref(null)
  const loading = ref(false)
  
  const runCode = async () => {
    loading.value = true
    result.value = null
  
    try {
      const response = await axios.post('http://localhost:8081/lessons/submit', {
        lessonId: parseInt(lessonId),
        language: 'csharp',
        code: code.value,
        input: ''
      })
      result.value = response.data
    } catch (err) {
      result.value = {
        success: false,
        expected: 'Erro',
        actual: 'Erro ao executar'
      }
    } finally {
      loading.value = false
    }
  }
  </script>
  
  <style scoped>
  </style>
  