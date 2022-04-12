<template>
  <div class="home">
    <!-- <h1>登录</h1> -->
    <!-- <img alt="Vue logo" src="../assets/logo.png"> -->

    <UserLogin msg="Welcome to My Vue.js App"/>
  </div>
</template>

<script>
// @ is an alias to /src
import UserLogin from '@/views/Login.vue'
export default {
  name: 'HomeView',
  components: {
    UserLogin
  }
}
</script>

<style scoped>
.home {
  width: 100%;
  height: 720px;
  background: url("../assets/2.jpg") no-repeat;
  background-size: cover;
  overflow: hidden;
}

</style>>
