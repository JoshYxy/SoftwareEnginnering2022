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
          <el-menu-item index="/home/welcome">
            <!-- <el-icon><icon-menu /></el-icon> -->
            <span >Welcome</span>
          </el-menu-item>
          <el-menu-item index="/home/admin">
            <!-- <el-icon><icon-menu /></el-icon> -->
            <span>个人信息录入</span>
          </el-menu-item>
          <el-menu-item index="/home/info">
            <!-- <el-icon><icon-menu /></el-icon> -->
            <span>Info</span>
          </el-menu-item>
          <el-menu-item index="/home/privateinfo">
            
            <span>个人信息</span>
          </el-menu-item>
        </el-menu>
      </el-aside>

      <el-main>
        <router-view />
        
      </el-main>
    </el-container>
  </div>
</template>


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