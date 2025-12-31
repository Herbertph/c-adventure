<template>
    <section class="bg-backgroundLight dark:bg-backgroundDark text-textLight dark:text-textDark py-20 min-h-screen shadow-top-sm dark:shadow-top-md">
      <div class="max-w-7xl mx-auto px-6 space-y-12">
  
        <!-- Título -->
        <div class="text-center">
          <h2 class="text-h2 font-display font-bold">Conteúdo do Curso</h2>
          <p class="text-p mt-4 max-w-2xl mx-auto">
            Cada módulo é uma nova fase da sua jornada. Derrote inimigos, aprenda novos conceitos e avance como um verdadeiro guerreiro do código.
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
              Módulo {{ index + 1 }}
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
      title: 'Variáveis e Tipos',
      description: 'Aprenda a declarar variáveis, armazenar dados e entender os principais tipos em C#.'
    },
    {
      title: 'Condicionais',
      description: 'Tome decisões com if/else e domine o fluxo lógico da sua aventura.'
    },
    {
      title: 'Loops e Repetições',
      description: 'Use while e for para repetir ações — essencial para escapar de armadilhas mágicas.'
    },
    {
      title: 'Funções',
      description: 'Crie habilidades reutilizáveis como atacar, desviar e curar.'
    },
    {
      title: 'Classes e Objetos',
      description: 'Construa armas, inimigos e aliados com programação orientada a objetos.'
    },
    {
      title: 'Herança e Polimorfismo',
      description: 'Derrote inimigos complexos herdando habilidades e adaptando comportamentos.'
    }
  ]
  </script>
  