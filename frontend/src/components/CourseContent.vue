<template>
    <section class="bg-backgroundLight dark:bg-backgroundDark text-textLight dark:text-textDark py-12  shadow-top-sm dark:shadow-top-md">
      <div class="max-w-7xl mx-auto px-6 space-y-12">
  
        <!-- Título -->
        <div class="text-center">
          <h2 class="text-h2 font-display font-bold">Course Content</h2>
          <p class="text-p mt-4 max-w-2xl mx-auto">
            Each module is a new phase of your journey. Defeat enemies, learn new concepts and advance as a true warrior of the code.
          </p>
        </div>
  
        <!-- Lista de módulos -->
        <div class="grid gap-8 md:grid-cols-2 lg:grid-cols-3">
          <div
            v-for="(module, index) in modules"
            :key="module.title"
            class="bg-white dark:bg-gray-800 rounded-xl shadow p-6 hover:shadow-lg transition"
          >
            <div class="text-sm text-gray-500 dark:text-gray-400 mb-2">
              Module {{ index + 1 }}
            </div>
            <h3 class="text-lg font-semibold text-primary mb-2">{{ module.title }}</h3>
            <p class="text-sm">{{ module.description }}</p>
          </div>
        </div>
  
      </div>
    </section>
  </template>
  
  <script setup>
  const modules = [
    {
      title: 'Variables and Data Types',
      description: 'Learn to declare variables, store data and understand the main types in C#.'
    },
    {
      title: 'Conditional Statements',
      description: 'Make decisions with if/else and master the logical flow of your adventure.'
    },
    {
      title: 'Loops and Repetitions',
      description: 'Use while and for to repeat actions — essential to escape magical traps.'
    },
    {
      title: 'Functions and Methods',
      description: 'Create reusable skills like attack, dodge and heal.'
    },
    {
      title: 'Classes and Objects',
      description: 'Build weapons, enemies and allies with object-oriented programming.'
    },
    {
      title: 'Inheritance and Polymorphism',
      description: 'Defeat complex enemies by inheriting skills and adapting behaviors.'
    }
  ]
  </script>
  