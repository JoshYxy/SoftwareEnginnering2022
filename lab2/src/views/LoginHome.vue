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
            <span>Admin</span>
          </el-menu-item>
          <el-menu-item index="/home/info">
            <!-- <el-icon><icon-menu /></el-icon> -->
            <span>Info</span>
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
.el-col {
display: block;
position: relative;
background-color: #324157;
}
</style>