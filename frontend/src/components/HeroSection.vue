<template>
   <section class="min-h-screen flex items-center bg-backgroundLight dark:bg-backgroundDark text-textLight dark:text-textDark">
    <div class="max-w-7xl mx-auto px-6 py-8 flex flex-col md:flex-row items-center gap-12">
  
        <!-- Texto da esquerda -->
        <div class="flex-1 space-y-6 text-center md:text-left">
          <h1 class="text-h1 font-display font-bold">
            Unlock your <span class="text-primary">Inner Code!</span>
          </h1>
          <p class="text-p">
            Learn programming oriented to objects in an epic way! Face enemies, advance in your journey, and master C# while living an unforgettable adventure.
          </p>
  
          <div class="flex flex-col sm:flex-row justify-center md:justify-start gap-4 mt-6">
            <button class="bg-primary text-white px-6 py-3 rounded-lg shadow hover:opacity-90 transition font-semibold">
              First Steps
            </button>
            <button class="flex items-center gap-2 border border-primary text-primary px-6 py-3 rounded-lg hover:bg-primary/10 transition">
              ▶ Watch a Teaser
            </button>
          </div>
        </div>
  
        <!-- Ilustração da direita -->
        <div class="flex-1 flex justify-center">
          <!--insert image that is hero.png in public folder  -->
          <img
  src="/Hero.png"
  alt="Coding Adventure Icon"
  class="w-80 h-80 md:w-[28rem] md:h-[28rem] object-contain"
/>
        </div>
      </div>
    </section>
  </template>
  