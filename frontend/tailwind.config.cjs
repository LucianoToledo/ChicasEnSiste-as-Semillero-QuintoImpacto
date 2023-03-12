/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
    "./index.html",
    "./src/**/*.{js,ts,jsx,tsx}",
    './node_modules/tw-elements/dist/js/**/*.js'
    
  ],
  theme: {
    extend: {
      colors: {
        primary: "#494697",
        secondary: "#e94b7b",
        orange: "#fbcb41",
        yellow: "#f9c943",
        "dark-steal": "#4b4696",
      },
      keyframes: {
        fadeAndScale: {
          "0%": {
            opacity: 0,
            transform: 'scale3D(0.75, 0.75, 1)'
          },
        
          "70%": {
            opacity: 0,
            transform: 'scale3D(0.75, 0.75, 1)',
          },
        
          "100%": {
            opacity: 1,
            transform: 'scale3D(1, 1, 1)',
          }
        },
        inRight: {
          "0%": {
              opacity: 0
              /* transform: translateX(60px); */
          },
      
          "100%": {
              opacity: 1
              /* transform: translateX(0px); */
          }
        }
      },
      animation: {
        fadeAndScale: 'fadeAndScale 1s cubic-bezier(0.39,-1.05,0.58,1.95)',
        inRight: 'inRight 2s'
      }
    },
  },
  plugins: [
    require("tailwindcss"),
    require("autoprefixer"),
    require('tw-elements/dist/plugin')
  ],
}
