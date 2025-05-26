const defaultTheme = require('tailwindcss/defaultTheme')

module.exports = {
  content: ['./index.html', './src/**/*.{vue,js,ts}'],
  darkMode: 'class',
  theme: {
    extend: {
      colors: {
        primary: '#6C63FF',
        secondary: '#1E1B4B',
        backgroundDark: '#0F172A',
        backgroundLight: '#F8FAFC',
        textDark: '#E2E8F0',
        textLight: '#1E293B',
      },
      boxShadow: {
        'top-sm': '0 -4px 6px -4px rgba(0, 0, 0, 0.1)',
        'top-md': '0 -6px 10px -4px rgba(0, 0, 0, 0.15)',
      },
      fontFamily: {
        sans: ['Inter', ...defaultTheme.fontFamily.sans],
        display: ['Outfit', ...defaultTheme.fontFamily.sans],
      },
      fontSize: {
        h1: ['2.5rem', { lineHeight: '1.2' }],
        h2: ['2rem', { lineHeight: '1.3' }],
        p: ['1.125rem', { lineHeight: '1.75' }],
      },
    },
  },
  plugins: [],
}