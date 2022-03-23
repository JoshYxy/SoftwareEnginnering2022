<template>
  <div class="info">
    <h1>信息查询</h1>

    <h2>施工中。。。。。。</h2>
    <img alt="Vue logo" src="../assets/logo.png">
    <!-- table-layout为fixed时出错 -->
    <el-table class="table" :data="userData" table-layout="auto">
      <el-table-column prop="id" label="Id" width="120" />
      <el-table-column prop="name" label="Name" width="120" />
      <el-table-column prop="role" label="Role" width="120" />
      <el-table-column prop="phone" label="Phone" width="180" />
      <el-table-column prop="email" label="Email" width="180" />
      <el-table-column prop="passport" label="Passport" width="180" />
      <el-table-column fixed="right" label="Operations" width="180">
      <!-- <template #default="scope"> -->
        <userinfo-maintenance></userinfo-maintenance>
      <!-- </template> -->
    </el-table-column>
    </el-table>

    
  </div>
</template>

<script>
import UserinfoMaintenance from './UserinfoMaintenance.vue'
export default {
  name: 'UserInfo',
  components: {
    UserinfoMaintenance
  },
  data() {
      return{
        userData: [
            {
            id: '1',
            name: 'undefined',
            role: 'student',
            phone: '13332221111',
            email: '123@qq.com',
            passport: '31122232222221',
            },
            {
            id: '1',
            name: 'un1ed',
            role: 'teacher',
            phone: '13332221111',
            email: '123@qq.com',
            passport: '31122232222221',
            }
        ],
      }
  },
  methods: {
    deleteRow(index){
      this.userData.value.splice(index, 1)
    }
  },
  created() {
      // axios.get('http://localhost:8081/info/findAll')
      //      .then(function(resp){
      //          _this.userData = resp.data
      //          console.log(resp)
      //      })
  }
}
</script>
<style scoped>

</style>