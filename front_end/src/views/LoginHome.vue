<template>
  <div class="student">
    <!-- /*active = 1 默认激活导航栏为1*/ -->
    <el-container>
      <el-aside width=200px>
        <el-menu
          default-active="/home/welcome" 
          :router="true"
          class="el-menu"
        >
          <el-menu-item v-for="route in vueRoute" :key="route.name" :index="`/home/${route.path}`">
            <span>{{route.meta.nav_name}} </span>
          </el-menu-item>
          <!-- <el-menu-item index="/home/welcome">
            <span >Welcome</span>
          </el-menu-item>
          <el-menu-item index="/home/admin">
            <span>个人信息录入</span>
          </el-menu-item>
          <el-menu-item index="/home/info">
            <span>用户管理</span>
          </el-menu-item>
          <el-menu-item index="/home/privateinfo">
            
            <span>个人信息</span>
          </el-menu-item>
          <el-menu-item index="/home/collegeinfo">
            
            <span>学院专业信息维护</span>
          </el-menu-item>
          <el-menu-item index="/home/course">
            
            <span>课程管理</span>
          </el-menu-item>
          <el-menu-item index="/home/edu">
            
            <span>教务管理</span>
          </el-menu-item> -->
        </el-menu>
      </el-aside>

      <el-main>
        <router-view />
        
      </el-main>
    </el-container>
  </div>
</template>
<script>
export default {
  data() {
    return {
        vueRoute: []
    }
  },
  created() {
    for(let i of this.$router.options.routes[2].children) {//home组件在router的第二个z
      if(i.meta.role.indexOf(localStorage.getItem('role')) > -1) {
        this.vueRoute.push(i)
      }
      
    }
    
  }
}
</script>

<style scoped>
.el-main {
  padding: 0;
}
.el-aside {
  width: 240px;
  color: var(--el-text-color-primary);
  background: #fff !important;
  border-right: solid 1px #e6e6e6;
  box-sizing: border-box;
}
</style>