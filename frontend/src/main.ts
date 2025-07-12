import './assets/main.css'

import { createApp } from 'vue'
import { createPinia } from 'pinia'
import piniaPluginPersistedstate from 'pinia-plugin-persistedstate'
import Elementplus from 'element-plus'
import 'element-plus/dist/index.css'


import App from './App.vue'
import router from './router'



const app = createApp(App)

const pinia = createPinia()
pinia.use(piniaPluginPersistedstate)
app.use(pinia)
app.use(router)
app.use(Elementplus)

app.mount('#app')
