import { createStore } from 'vuex'
// import Vue from 'vue';
// import Vuex from 'vuex';
// Vue.use(Vuex);
 
// const store = createStore({
//   state: {
//     // 存储token
//     Authorization: localStorage.getItem('Authorization') ? localStorage.getItem('Authorization') : ''
//   },
 
//   mutations: {
//     // 修改token，并将token存入localStorage
//     changeLogin (state, user) {
//       state.Authorization = user.Authorization;
//       localStorage.setItem('Authorization', user.Authorization);
//     }
//   }
// });
 
export default createStore({
  state: {
    // 存储token
    Authorization: ''
  },
  getters: {
  },
  mutations: {
    // 修改token，并将token存入localStorage
    changeLogin (state, user) {
      state.Authorization = user.Authorization;
      localStorage.setItem('Authorization', user.Authorization);
    },
    check (state) {
      state.Authorization = localStorage.getItem('Authorization')
    }
  },
  actions: {
  },
  modules: {
  }
})
