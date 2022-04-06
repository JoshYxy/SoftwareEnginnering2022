<template>
  <div class = "studentCourse">
    <h2>可选课程查询</h2>
    <el-table class="courseTable">
      <el-table-column fixed prop="number" label="课程序号" width="150" />
      <el-table-column fixed prop="curriculum" label="课程名称" width="150" />
      <el-table-column fixed prop="department" label="开课院系" width="150" />
      <el-table-column fixed prop="credit" label="学分" width="150" />
      <el-table-column fixed prop="teacher" label="教师" width="150" />
      <el-table-column fixed prop="time" label="上课时间" width="150" />
    </el-table>
  </div>
</template>

<script>
export default {
  name: "OptionalCourse.vue"
}
</script>

<style scoped>
.studentCourse{
  position: absolute;
  width: 900px;
  height: 300px;
  margin:auto;
}
</style>