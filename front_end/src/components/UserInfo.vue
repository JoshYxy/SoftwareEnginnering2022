<template>
  <div class="info">
    <h1>信息查询</h1>

    <h2>施工中。。。。。。</h2>
    <img alt="Vue logo" src="../assets/logo.png">
    <!-- table-layout为fixed时出错 -->
    <el-table class="table" :data="userData" >
      <el-table-column fixed prop="number" label="Number" width="120" />
      <el-table-column prop="name" label="Name" width="120" />
      <el-table-column prop="role" label="Role" width="120" />
      <el-table-column prop="phone" label="Phone" width="180" />
      <el-table-column prop="email" label="Email" width="180" />
      <el-table-column prop="id" label="Id" width="180" />
      <el-table-column prop="college" label="College" width="180" />
      <el-table-column prop="major_" label="Major" width="150" />
      <el-table-column prop="password" label="Password" width="150" />
      <el-table-column fixed="right" label="Status" width="180">
      <template #default="scope">
        <el-radio-group v-model="scope.row.status" @change="updataStatus">
          <el-radio v-if="scope.row.role=='student'" label="studying" >在读</el-radio>
          <el-radio v-if="scope.row.role=='student'" label="graduated" >毕业</el-radio>
          <el-radio v-if="scope.row.role=='teacher'" label="working" >在岗</el-radio>
          <el-radio v-if="scope.row.role=='teacher'" label="quit" >离职</el-radio>
        </el-radio-group>
      </template>
      </el-table-column>
      <el-table-column fixed="right" label="Operations" width="180">
      <template #default="scope">
        <userinfo-maintenance 
          @changeInfo="updateUser(scope.$index,$event)" 
          :userInfo="scope.row" 
          :collegeData="this.collegeData" 
          :major_b="this.userData[scope.$index].major_" />
        <!-- </userinfo-maintenance> -->
        <!-- <el-switch @change="test" on-value="1" off-value="0" v-model="scope.row.role"> -->
        <!-- 向组件传入用户id -->
      </template>
    </el-table-column>
    </el-table>

    
  </div>
</template>

<script>
import axios from 'axios'
import UserinfoMaintenance from './UserinfoMaintenance.vue'
export default {
  name: 'UserInfo',
  components: {
    UserinfoMaintenance
  },
  data() {
      return{
        collegeData: [
            { 
            id: 1,
            name: '计算机科学技术学院', 
            major: [
                {id: 1, name: '大数据'},
                {id: 2, name: '信息安全'}
            ]
            },
            { 
            id: 2,
            name: '生命科学学院', 
            major: [
                {id: 1, name: '生物'},
                {id: 2, name: '123'}
            ]
            },
            { 
            id: 3,
            name: '软件工程学院', 
            major: [
                {id: 1, name: '软件工程'},
            ]
            },
        ],
        userData: [
            {
            number: 1,
            name: 'undefined',
            role: 'student',
            phone: '13332221111',
            email: '123@qq.com',
            id: '311222322222111111',
            status: 'studying',
            college: '计算机科学技术学院', 
            major_: '大数据',
            password: '123455'
            },
            {
            number: 2,
            name: 'un1ed',
            role: 'teacher',
            phone: '13332221111',
            email: '123@qq.com',
            id: '31122232222221',
            status: 'working',
            major_: '信息安全',
            college: '计算机科学技术学院', 
            password: '123455'
            }
        ],
      }
  },
  // watch: {
  //   userData: {
  //     //axios post
  //   }
  // },
  methods: {
    updateUser(index, e){
      // console.log(e);
      this.userData[index].name=e.name;
      this.userData[index].id=e.id;
      this.userData[index].phone=e.phone;
      this.userData[index].major_=e.major_;
      this.userData[index].email=e.email;
      this.userData[index].password=e.password;
      this.userData[index].college=e.college;      
    },  
    updataStatus() {
      axios.post('http://localhost:8081/admin/user/info', this.user)
    },
  },

  created() {
      //获取用户信息
    axios.get('http://localhost:8081/admin/users')
      .then(res => {
        this.userData = res
      }).catch(error => {
        alert("服务器访问失败")
        console.dir(error);
      });
      //获取学院信息
    axios.get("http://localhost:8081/admin/edu")
      .then(res => {
        this.collegeData = res
        for(let i in this.collegeData) {//替换变量名,对应后端数据
          this.collegeData[i] = JSON.parse(JSON.stringify(this.collegeData[i]).replace(/collegeVOId/g,"id"))
          this.collegeData[i] = JSON.parse(JSON.stringify(this.collegeData[i]).replace(/majorId/g,"id"))
          this.collegeData[i] = JSON.parse(JSON.stringify(this.collegeData[i]).replace(/collegeVOName/g,"name"))
          this.collegeData[i] = JSON.parse(JSON.stringify(this.collegeData[i]).replace(/majorName/g,"name"))
          
        }
      }).catch(error => {
        alert('获取服务器信息失败')
        console.dir(error);
      });
  }
}
</script>
<style scoped>

</style>