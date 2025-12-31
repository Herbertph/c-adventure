<template>
<section class="bg-backgroundLight dark:bg-backgroundDark text-textLight dark:text-textDark py-20 min-h-screen shadow-top-sm dark:shadow-top-md">
  <div class="max-w-7xl mx-auto px-6 text-center space-y-12">

      <div>
        <h2 class="text-h2 font-display font-bold">Como Funciona</h2>
        <p class="text-p mt-4 max-w-2xl mx-auto">
          Aprender nunca foi tão épico. Você progride ao derrotar inimigos com código real, desbloqueando novos poderes a cada desafio.
        </p>
      </div>

      <div class="grid gap-8 md:grid-cols-3">
        <div
          v-for="card in cards"
          :key="card.title"
          class="bg-white dark:bg-gray-800 shadow-lg rounded-lg p-6 flex flex-col items-center text-center transition hover:scale-105 hover:shadow-xl"
        >
          <div class="text-4xl mb-4">{{ card.icon }}</div>
          <h3 class="text-xl font-semibold mb-2 text-primary">{{ card.title }}</h3>
          <p class="text-sm">{{ card.description }}</p>
        </div>
      </div>

    </div>
  </section>
</template>

<script setup>
const cards = [
  {
    icon: '🧠',
    title: 'Aprenda Conceitos',
    description: 'Cada etapa do curso ensina um novo conceito de programação com explicações simples e exemplos claros.'
  },
  {
    icon: '⚔️',
    title: 'O código é sua arma',
    description: 'Use o que aprendeu para enfrentar desafios e inimigos — cada linha de código conta.'
  },
  {
    icon: '🏆',
    title: 'Desbloqueie Novas Fases',
    description: 'Ao vencer, você desbloqueia novas fases da história e avança em sua jornada de aprendizado.'
  }
]
</script>
