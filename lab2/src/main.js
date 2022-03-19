import { createApp } from 'vue'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import App from './App.vue'
import axios from 'axios'
import VueAxios from 'vue-axios'
import router from './router'
import store from './store'

const app = createApp(App)
app.use(VueAxios, axios)
app.use(ElementPlus)
app.use(store).use(router).mount('#app')

axios.interceptors.request.use(config => {
    /* 判断token是否存在和是否需要token验证的路由 */
    let token = localStorage.getItem('Authorization') || ''
    config.headers.token = token;
    return config;
});
/* eslint-disable */
axios.interceptors.response.use(response => {
        return response
    }, err=> {
        /*判断一下未授权 */
        if(err.response.status == 401) alert("权限不足，无法访问")
        return Promise.reject(err)
    });

// 用来清空localStoreage 和 vuex里面的内容

