<template>
  <div class="info">
    <h1>信息查询</h1>

    <h2>施工中。。。。。。</h2>
    <img alt="Vue logo" src="../assets/logo.png">
    <el-table :data="userData" style="width: 100%">
      <el-table-column prop="id" label="Id" width="180" />
      <el-table-column prop="name" label="Name" width="180" />
      <el-table-column prop="role" label="Role" width="180" />
      <el-table-column prop="phone" label="Phone" width="180" />
      <el-table-column prop="email" label="Email" width="180" />
      <el-table-column prop="passport" label="Passport" width="180" />
    </el-table>
  </div>
</template>

<script>
/* eslint-disable */
import axios from 'axios'
export default {
  name: 'UserInfo',
  data() {
      return{
        userData: [
            {
            id: undefined,
            name: undefined,
            role: undefined,
            phone: undefined,
            email: undefined,
            passport: undefined,
            }
        ],
      }
  },
  created() {
      const _this = this
      // axios.get('http://localhost:8081/info/findAll')
      //      .then(function(resp){
      //          _this.userData = resp.data
      //          console.log(resp)
      //      })
  }
}
</script>
<style scoped>
/* 试图居中，会遮盖导航栏 */
.info{
  position: absolute;
  left: 50%;

  width: 1100px;

  margin-left: -550px;
}
</style>