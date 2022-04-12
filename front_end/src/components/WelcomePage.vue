<template>
  <div class='welcome'>
    <h1>登录成功</h1>
    <el-button id='logout' v-show="this.$store.state.Authorization !== ''" type="info" @click="logout">退出</el-button>
    <el-button type="info" @click="refresh">test</el-button>
  </div>
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

<style scoped>
.welcome{
  position: absolute;
  width: 500px;
  height: 300px;
  margin: auto;
  left: 0;
  right: 0;
}
</style>

