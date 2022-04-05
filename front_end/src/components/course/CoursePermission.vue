<template>
  <div class = "coursePermission" >
    <span style="text-align: center">
      <h1>选课权限管理</h1>
      <el-button type="primary" style="margin-right: 30px">开放选课</el-button>
      <el-button type="primary" style="margin-left: 30px">关闭选课</el-button>
    </span>
  </div>
</template>

<script>
export default {
  name: "CoursePermission.vue"
}
</script>

<style scoped>
.coursePermission{
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



</style>