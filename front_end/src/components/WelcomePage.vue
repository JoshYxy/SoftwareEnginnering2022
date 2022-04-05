<template>
  <div class='welcome'>
    <span  style="text-align: center" class="welcomePage">
      <h1>登录成功</h1>
      <el-button id='logout' v-show="this.$store.state.Authorization !== ''" type="info" @click="logout">退出</el-button>
      <el-button type="info" @click="refresh">test</el-button>
    </span>
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
.welcomePage{
  border: 1px outset grey;
  box-shadow: 0 0 5px #cac6c6;
  position: absolute;
  width: 400px;
  height: 140px;
  margin: auto;
  left: 0;
  right: 0;
  top: 200px;
}
.welcome {
  width: 100%;
  height: 720px;
  background: url("../assets/3.png") no-repeat;
  background-size:cover;
  overflow: hidden;
}
</style>

