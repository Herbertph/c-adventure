<template>
    <section class="bg-backgroundLight dark:bg-backgroundDark text-textLight dark:text-textDark py-12 shadow-top-sm dark:shadow-top-md">
      <div class="max-w-7xl mx-auto px-6 space-y-12">
  
        <!-- Cabeçalho -->
        <div class="text-center">
          <h2 class="text-h2 font-display font-bold">Choose Your Plan</h2>
          <p class="text-p mt-4 max-w-2xl mx-auto">
            Start your journey with the plan that best fits your learning pace.
          </p>
        </div>
  
        <!-- Cards de preço -->
        <div class="grid md:grid-cols-2 gap-8">
  
          <!-- Plano Anual (destaque) -->
          <div class="relative bg-primary text-white rounded-xl p-8 shadow-xl flex flex-col">
            <div class="absolute -top-4 left-4 bg-yellow-400 text-black px-3 py-1 rounded-full text-xs font-bold uppercase">
              + Advantages
            </div>
            <h3 class="text-2xl font-bold mb-4">Lifetime Plan</h3>
            <p class="mb-6">Complete access FOREVER and with 25% discount.</p>
            <div class="text-3xl font-bold mb-1">$ 39,99</div>
            <div class="text-sm mb-6 opacity-80">One-time payment for lifetime access</div>
            <ul class="space-y-2 mb-6 text-sm">
              <li>✅ Access to all levels</li>
              <li>✅ Email support</li>
              <li>✅ Future updates included</li>
            </ul>
            <button class="mt-auto bg-white text-primary font-semibold py-3 rounded-lg hover:bg-gray-100 transition">
              Sign Up Now
            </button>
          </div>
  
          <!-- Plano Mensal -->
          <div class="bg-white dark:bg-gray-800 rounded-xl p-8 shadow-lg flex flex-col">
            <h3 class="text-2xl font-bold mb-4 text-primary">Monthly Plan</h3>
            <p class="mb-6">Ideal for those who want to test for a shorter period.</p>
            <div class="text-3xl font-bold mb-1">$ 9,99</div>
            <div class="text-sm mb-6 opacity-70">Monthly billing</div>
            <ul class="space-y-2 mb-6 text-sm">
              <li>✅ Access to basic lessons</li>
              <li>✅ Email support</li>
              <li>❌ Future updates</li>
            </ul>
            <button class="mt-auto bg-primary text-white font-semibold py-3 rounded-lg hover:opacity-90 transition">
              Sign Up Now
            </button>
          </div>
  
        </div>
  
      </div>
    </section>
  </template>
  