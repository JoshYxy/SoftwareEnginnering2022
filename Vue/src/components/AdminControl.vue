<template>
  <div class="admin" style="margin:0px auto">
    <h1>信息录入</h1>
    
    <el-form id = "admin_container" ref="userData" :model="userData" :rules="rules" autocomplete="on"  text-align: center>
      <el-form-item prop="role" display: inline-block >
        <!-- label="Activity zone"  -->
        <el-select v-model="userData.role" placeholder="角色">
          <el-option label="学生" value="student"></el-option>
          <el-option label="教师" value="teacher"></el-option>
        </el-select>
      </el-form-item>

      <el-form-item prop="name">
        <el-input
          type="text"
          name="name"
          v-model="userData.name"
          placeholder="姓名"
        />
      </el-form-item> 

      <el-form-item v-if="userData.role==='student'" prop="stu_id">
        <el-input
          type="text"
          name="stu_id"
          v-model.number="userData.stu_id"
          placeholder="学号"
        />
      </el-form-item>

      <el-form-item v-if="userData.role==='teacher'" prop="teach_id">
        <el-input
          type="text"
          name="teach_id"
          v-model.number="userData.teach_id"
          placeholder="工号"
        />
      </el-form-item>

      <el-form-item prop="id">
        <el-input
          name="id"
          v-model="userData.id"
          placeholder="身份证号"
          type="text"
        />
      </el-form-item>

      <el-form-item prop="phone">
        <el-input
          type="text"
          name="phone"
          v-model="userData.phone"
          placeholder="手机号"
        />
      </el-form-item> 

      <el-form-item prop="email">
        <el-input
          type="text"
          name="email"
          v-model="userData.email"
          placeholder="邮箱"
        />
      </el-form-item>

      <el-form-item>
        <el-button type="primary" @click="submit">提交</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import axios from 'axios'
//格式检查
var validName = (rule, value, callback) => {
    const reg = /^[A-Za-z\u4E00-\u9FA5]+$/
    if (reg.test(value)) {
        callback()
      } else {
        return callback(new Error('姓名必须为汉字或英语'))
      }
}
var validPhone = (rule, value, callback) => {
  if (value.length != 11 && value.length != 0) {
    callback(new Error('电话号码必须为11位'))
  } else{
      const reg = /^1[3|4|5|7|8][0-9]\d{8}$/
      if (reg.test(value) || value.length == 0) {
          callback()
        } else {
          return callback(new Error('手机号码必须为1开头的符合规则的11位数字'))
        }
    }
}
var validPassport = (rule, value, callback) => {
  if (value.length != 18) {
    callback(new Error('身份证必须为18位'))
  } else{
      const reg =  /(^\d{18}$)|(^\d{17}(\d|X|x)$)/
      if (reg.test(value)) {
          callback()
        } else {
          return callback(new Error('身份证号不符合规则'))
        }
    }
}
var validEmail = (rule, value, callback) => {
  const reg = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((.[a-zA-Z0-9_-]{2,3}){1,2})$/
  if (reg.test(value) || value.length == 0) {
      callback()
    } else {
      return callback(new Error('邮箱地址不符合规则'))
    }
}

export default {

  name: 'AdminControl',

  data(){
    return {
      userData: {
        number: null,
        role:'student',
        name:'',
        id:'',
        phone:'',
        email:'',
        stu_id:null,
        teach_id:null,
        password:'123456'
      },
      rules: {
        role: [{required: true, message: '请选择身份', trigger: 'change'}],
        stu_id: [{required: true, message: '学号不能为空', trigger: 'blur'},
                 {pattern: /^\d{6,6}$/, message: '学号为6位数字', trigger: 'blur'},
                 {pattern: /^[0-1][0-9][0-9]*$|^2[0-2][0-9]*$/, message: '学号必须为注册年份后两位开头的数字(小于22)'}],
        teach_id: [{required: true, message: '工号不能为空', trigger: 'blur'},
                 {pattern: /^\d{8,8}$/, message: '工号为8位数字', trigger: 'blur'},
                 {pattern: /^[0-1][0-9][0-9]*$|^2[0-2][0-9]*$/, message: '工号必须为注册年份后两位开头的数字(小于22)'}],
        name: [{required: true, message: '姓名不能为空', trigger: 'blur'},
               {validator: validName, trigger: ['blur', 'change']}],
        id: [{ required: true, message: '身份证不能为空', trigger: 'blur'},
                   { validator: validPassport, trigger: 'blur' }],
        phone: [{required: false, trigger: ['blur', 'change']},
                {validator: validPhone, trigger: 'blur'}],
        email: [{required: false, trigger: 'blur'},
                {validator: validEmail, trigger: 'blur'}],
      }
    }
  },
  methods:{
    submit(){
      this.$refs['userData'].validate(valid => {
        if(valid){
          if(this.userData.role == 'student')
            this.userData.number = this.userData.stu_id
          if(this.userData.role == 'teacher')
            this.userData.number = this.userData.teach_id
          // this.info.passport = this.userData.passport
          // this.info.role = this.userData.role
          // this.info.phone = this.userData.phone
          // this.info.email = this.userData.email
          // this.info.name = this.userData.name
          // alert("提交成功")
          console.log(this.userData)
          // axios.post('http://localhost:8081/register', null, {params:this.userData})
          axios.post('http://localhost:8081/register', this.userData)
          .then(function(resp){
            console.log(resp)
            alert('提交成功')
              
          }).catch(error => {
            if(error.response.status == 420){
              if(this.userData.role == 'student')
                alert('学号已存在')
              else
                alert('工号已存在')
              this.userData.stu_id = ''
              this.userData.teach_id = ''
            }
            if(error.response.status == 421){
              alert('身份证已存在');
              this.userData.id = ''
            }
            
            console.dir(error);
          })
        }
        else{
          console.log('error ssssubmit');
          return false
        }
      })
    }
  }
}

</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.admin{
  position: absolute;
  width: 500px;
  height: 300px;
  margin: auto;
  left: 0;
  right: 0;
}
#container {
  border-radius: 15px;
  margin: 0px auto;
  width: 500px;
  padding: 35px 35px;
  background: #fff;
}

h3 {
  margin: 40px 0 0;
}
ul {
  list-style-type: none;
  padding: 0;
}
li {
  display: inline-block;
  margin: 0 10px;
}
a {
  color: #42b983;
}
</style>
