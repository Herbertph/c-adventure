<template>
<section class="bg-backgroundLight dark:bg-backgroundDark text-textLight dark:text-textDark py-12 shadow-top-sm dark:shadow-top-md">
  <div class="max-w-7xl mx-auto px-6 text-center space-y-12">

      <div>
        <h2 class="text-h2 font-display font-bold">How It Works</h2>
        <p class="text-p mt-4 max-w-2xl mx-auto">
          Learning was never this epic. You progress by defeating enemies with real code, unlocking new powers with each challenge.
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
    title: 'Learn Concepts',
    description: 'Each step of the course teaches a new programming concept with simple explanations and clear examples.'
  },
  {
    icon: '⚔️',
    title: 'Code is Your Weapon',
    description: 'Use what you learned to face challenges and enemies — every line of code counts.'
  },
  {
    icon: '🏆',
    title: 'Unlock New Levels',
    description: 'By winning, you unlock new levels of the story and advance in your learning journey.'
  }
]
</script>
