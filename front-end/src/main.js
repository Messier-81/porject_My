import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'

// 유효성 체크 라이브러리 : vee-validate ( Vue 전용 )
import VeeValidate from 'vee-validate';

// bootstrap-vue import
import { BootstrapVue } from 'bootstrap-vue'
import 'bootstrap-vue/dist/bootstrap-vue.css'

Vue.use(VeeValidate);

// VueSweetalert2
import VueSweetalert2 from 'vue-sweetalert2';

// If you don't need the styles, do not connect
import 'sweetalert2/dist/sweetalert2.min.css';

Vue.use(VueSweetalert2);

// bootstrap-vue 모듈 사용
Vue.use(BootstrapVue)
Vue.config.productionTip = false

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
