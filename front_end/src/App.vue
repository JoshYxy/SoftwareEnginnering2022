<template>
 
  <nav>
    <router-link to="/login">Login</router-link> |
    <router-link to="/home/welcome">Home</router-link> |
    <!-- <router-link to="/admin">Admin</router-link> | -->
    <!-- <router-link to="/info">Info</router-link> | -->
    <router-link to="/password">Password</router-link> 
    <!-- <el-button id='logout' v-show="this.$store.state.Authorization !== ''" type="info" @click="logout">退出</el-button>
    <el-button type="info" @click="refresh">test</el-button> -->
  </nav>
  <router-view/>
</template>
<script>
export default {
    methods:{
        logout(){
          if(localStorage.getItem('Authorization') !== ''){
            localStorage.removeItem('Authorization');
            localStorage.removeItem('role');
            this.$store.commit('check');
            this.$router.push('/');
          }
          else{
            alert("未登录");
          }
        },
        refresh(){
          this.$store.commit('check');
        }
    }
};
</script>
<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}

nav {
  padding: 20px;
}

nav a {
  font-weight: bold;
  color: #2c3e50;
}

nav a.router-link-exact-active {
  color: #42b983;
}
.el-table__header col[name="gutter"] {
  display: table-cell !important;
}
.el-descriptions__title{
  font-size: 20px;
}
</style>
