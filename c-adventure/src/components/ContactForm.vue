<template>
    <section class="bg-backgroundLight dark:bg-backgroundDark text-textLight dark:text-textDark py-20 min-h-screen shadow-top-sm dark:shadow-top-md">
      <div class="max-w-4xl mx-auto px-6">
        <div class="text-center mb-12">
          <h2 class="text-h2 font-display font-bold">Fale com a gente</h2>
          <p class="text-p mt-4 max-w-2xl mx-auto">
            Tem dúvidas sobre o curso, planos ou funcionalidades? Envie uma mensagem e retornaremos em breve!
          </p>
        </div>
  
        <form class="space-y-6">
          <div class="grid md:grid-cols-2 gap-6">
            <div>
              <label class="block mb-1 font-medium" for="name">Nome</label>
              <input
                type="text"
                id="name"
                class="w-full px-4 py-3 rounded-lg bg-white dark:bg-gray-800 border border-gray-300 dark:border-gray-700 text-textLight dark:text-textDark focus:outline-none focus:ring-2 focus:ring-primary"
                placeholder="Seu nome"
                required
              />
            </div>
  
            <div>
              <label class="block mb-1 font-medium" for="email">Email</label>
              <input
                type="email"
                id="email"
                class="w-full px-4 py-3 rounded-lg bg-white dark:bg-gray-800 border border-gray-300 dark:border-gray-700 text-textLight dark:text-textDark focus:outline-none focus:ring-2 focus:ring-primary"
                placeholder="seu@email.com"
                required
              />
            </div>
          </div>
  
          <div>
            <label class="block mb-1 font-medium" for="message">Mensagem</label>
            <textarea
              id="message"
              rows="5"
              class="w-full px-4 py-3 rounded-lg bg-white dark:bg-gray-800 border border-gray-300 dark:border-gray-700 text-textLight dark:text-textDark focus:outline-none focus:ring-2 focus:ring-primary"
              placeholder="Escreva sua mensagem aqui..."
              required
            ></textarea>
          </div>
  
          <button
            type="submit"
            class="bg-primary text-white font-semibold px-8 py-3 rounded-lg hover:opacity-90 transition"
          >
            Enviar mensagem
          </button>
        </form>
      </div>
    </section>
  </template>
  