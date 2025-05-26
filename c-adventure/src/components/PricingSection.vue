<template>
    <section class="bg-backgroundLight dark:bg-backgroundDark text-textLight dark:text-textDark py-20 min-h-screen shadow-top-sm dark:shadow-top-md">
      <div class="max-w-7xl mx-auto px-6 space-y-12">
  
        <!-- Cabeçalho -->
        <div class="text-center">
          <h2 class="text-h2 font-display font-bold">Escolha seu Plano</h2>
          <p class="text-p mt-4 max-w-2xl mx-auto">
            Comece sua jornada com o plano que melhor se adapta ao seu ritmo de aprendizado.
          </p>
        </div>
  
        <!-- Cards de preço -->
        <div class="grid md:grid-cols-2 gap-8">
  
          <!-- Plano Anual (destaque) -->
          <div class="relative bg-primary text-white rounded-xl p-8 shadow-xl flex flex-col">
            <div class="absolute -top-4 left-4 bg-yellow-400 text-black px-3 py-1 rounded-full text-xs font-bold uppercase">
              Mais vantajoso
            </div>
            <h3 class="text-2xl font-bold mb-4">Plano Vitalício</h3>
            <p class="mb-6">Acesso completo PARA SEMPRE e com 25% de desconto.</p>
            <div class="text-3xl font-bold mb-1">R$ 239,00</div>
            <div class="text-sm mb-6 opacity-80">Pagamento único por ano</div>
            <ul class="space-y-2 mb-6 text-sm">
              <li>✅ Acesso a todas as fases</li>
              <li>✅ Suporte por e-mail</li>
              <li>✅ Atualizações futuras inclusas</li>
            </ul>
            <button class="mt-auto bg-white text-primary font-semibold py-3 rounded-lg hover:bg-gray-100 transition">
              Assinar agora
            </button>
          </div>
  
          <!-- Plano Mensal -->
          <div class="bg-white dark:bg-gray-800 rounded-xl p-8 shadow-lg flex flex-col">
            <h3 class="text-2xl font-bold mb-4 text-primary">Plano Mensal</h3>
            <p class="mb-6">Ideal para quem quer testar por um tempo mais curto.</p>
            <div class="text-3xl font-bold mb-1">R$ 29,90</div>
            <div class="text-sm mb-6 opacity-70">Cobrança mensal</div>
            <ul class="space-y-2 mb-6 text-sm">
              <li>✅ Acesso às aulas básicas</li>
              <li>✅ Suporte por e-mail</li>
              <li>❌ Atualizações futuras</li>
            </ul>
            <button class="mt-auto bg-primary text-white font-semibold py-3 rounded-lg hover:opacity-90 transition">
              Assinar agora
            </button>
          </div>
  
        </div>
  
      </div>
    </section>
  </template>
  