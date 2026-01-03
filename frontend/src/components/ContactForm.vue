<template>
    <section class="bg-backgroundLight dark:bg-backgroundDark text-textLight dark:text-textDark py-12 shadow-top-sm dark:shadow-top-md">
      <div class="max-w-4xl mx-auto px-6">
        <div class="text-center mb-12">
          <h2 class="text-h2 font-display font-bold">Talk to us</h2>
          <p class="text-p mt-4 max-w-2xl mx-auto">
            Have questions about the course, plans or features? Send a message and we'll get back to you soon!
          </p>
        </div>
  
        <form class="space-y-6">
          <div class="grid md:grid-cols-2 gap-6">
            <div>
              <label class="block mb-1 font-medium" for="name">Name</label>
              <input
                type="text"
                id="name"
                class="w-full px-4 py-3 rounded-lg bg-white dark:bg-gray-800 border border-gray-300 dark:border-gray-700 text-textLight dark:text-textDark focus:outline-none focus:ring-2 focus:ring-primary"
                placeholder="Your name"
                required
              />
            </div>
  
            <div>
              <label class="block mb-1 font-medium" for="email">Email</label>
              <input
                type="email"
                id="email"
                class="w-full px-4 py-3 rounded-lg bg-white dark:bg-gray-800 border border-gray-300 dark:border-gray-700 text-textLight dark:text-textDark focus:outline-none focus:ring-2 focus:ring-primary"
                placeholder="your@email.com"
                required
              />
            </div>
          </div>
  
          <div>
            <label class="block mb-1 font-medium" for="message">Message</label>
            <textarea
              id="message"
              rows="5"
              class="w-full px-4 py-3 rounded-lg bg-white dark:bg-gray-800 border border-gray-300 dark:border-gray-700 text-textLight dark:text-textDark focus:outline-none focus:ring-2 focus:ring-primary"
              placeholder="Write your message here..."
              required
            ></textarea>
          </div>
  
          <button
            type="submit"
            class="bg-primary text-white font-semibold px-8 py-3 rounded-lg hover:opacity-90 transition"
          >
            Send message
          </button>
        </form>
      </div>
    </section>
  </template>
  