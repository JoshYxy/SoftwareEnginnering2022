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
      <el-table-column prop="college.name" label="College" width="180" />
      <el-table-column prop="major_" label="Major" width="150" />
      <el-table-column prop="password" label="Password" width="150" />
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
            
            college:  { 
              id: 1,
              name: '计算机科学技术学院', 
              major: [
                  {id: 1, name: '大数据'},
                  {id: 2, name: '信息安全'}
                ]
              },
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
              major_: '信息安全',
              college:  { 
                id: 1,
                name: '计算机科学技术学院', 
                major: [
                    {id: 1, name: '大数据'},
                    {id: 2, name: '信息安全'}
                ]
              },
              password: '123455'
              }
        ],
      }
  },
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
    }
  },
  created() {
      //获取用户信息和学院信息
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