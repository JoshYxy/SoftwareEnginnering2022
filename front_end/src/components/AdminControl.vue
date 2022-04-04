<template>
  <div class="admin" style="margin:0px auto">
    <h1>信息录入</h1>
    <el-upload
      class="upload-demo"
      :action="Upload"
      accept=".csv"
      :limit="1"
      :before-upload="onBeforeUpload"
      :data="uploadData"
    >
      <el-button type="primary">
        <el-icon style="vertical-align: middle;">
          <upload />
        </el-icon>
        <span style="vertical-align: middle;"> 上传文件 </span>
      </el-button>
<!--      <el-button type="primary">选择文件上传</el-button>-->
      <template #tip>
        <div class="el-upload__tip">
          单个csv文件
        </div>
      </template>
    </el-upload>
    <el-form id="admin_container" ref="userData" :model="userData" :rules="rules" autocomplete="on"  text-align: center>
      <el-form-item prop="role">
        <el-select v-model="userData.role" placeholder="角色">
          <el-option label="学生" value="student"></el-option>
          <el-option label="教师" value="teacher"></el-option>
        </el-select>
      </el-form-item>

      <el-form-item class="college" prop="college">
        <el-select v-model="userData.college" value-key="name" placeholder="学院">
          <el-option :key="colleges.id" :value="colleges" :label="colleges.name" v-for="colleges in collegeData" ></el-option>
        </el-select>
      </el-form-item>

      <el-form-item class="major" prop="major" >
        <el-select v-model="userData.major" placeholder="专业">
          <el-option :key="major.id" :value="major.name" :label="major.name" v-for="major in userData.college.major" ></el-option>
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
        <el-button style="display:block;margin:0 auto" type="primary" @click="submit">
          <el-icon style="vertical-align: middle;">
            <check />
          </el-icon>
          <span style="vertical-align: middle;"> 提交 </span>
        </el-button>

      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import {Check,Upload} from "@element-plus/icons";
import axios from 'axios'

import {validName,validPhone,validPassport,validEmail} from './jsComponents/CheckRules'
//格式检查

export default {

  name: 'AdminControl',
  components:{
    Check,
    Upload
  },

  data(){
    return {
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
          id: 1,
          name: '生命科学学院', 
          major: [
              {id: 1, name: '生物'},
              {id: 2, name: '123'}
          ]
        },
        { 
          id: 1,
          name: '软件工程学院', 
          major: [
              {id: 1, name: '软件工程'},
          ]
        },
      ],
      majorData: [
      ],
      userData: {
        number: null,
        role: 'student',
        name: '',
        id: '',
        phone: '',
        email: '',
        stu_id: null,
        teach_id: null,
        password: '123456',
        major: '',
        college: {},
      },
      collegeBackup: {},
      majorBackup: '',
      rules: {
        college: [{required: true, message: '请选择学院', trigger: 'change'}],
        major: [{required: true, message: '请选择专业', trigger: 'change'}],
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
  watch:{
    //如果没有.name会出错，因为在college = college.name赋值中改变了college，但不会认为college.name改变（此时不希望watch被触发）    
    "userData.college.name": {
      handler(){
        // console.log(this.userData.college)
        this.userData.major='';  
      },
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
          this.collegeBackup = this.userData.college
          this.majorBackup = this.userData.major
          this.userData.college = this.userData.college.name
          // axios.post('http://localhost:8081/register', null, {params:this.userData})
          axios.post('http://localhost:8081/admin/new', this.userData)
          .then(function(resp){
            console.log(resp)
            alert('提交成功')
              
          }).catch(error => {
            if(error.response.status == 421){
              if(this.userData.role == 'student')
                alert('学号已存在')
              else
                alert('工号已存在')
              this.userData.stu_id = ''
              this.userData.teach_id = ''
            }
            if(error.response.status == 420){
              alert('身份证已存在');
              this.userData.id = ''
            }
            console.dir(error);
          })
          console.log(this.userData)
          this.userData.college = this.collegeBackup
          this.userData.major = this.majorBackup
        }
        else{
          console.log('error ssssubmit');
          return false
        }
      })
    },
    onBeforeUpload(file) {
      const isCSV = file.type === 'application/vnd.ms-excel'

      if (!isCSV) {
        this.$message.error('上传文件只能是.csv格式!');
      }
    
      return isCSV;
    },
    Upload(file) {
      let param = new FormData(); 
      param.append('file',file)
      axios.post("http://localhost:8081/admin/users",param)
        .then(function(){
          alert('批量导入信息完成，无导入失败')                  
        }).catch(error => {
          alert(error.response.message)
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
/* #container {
  border-radius: 15px;
  margin: 0px auto;
  width: 500px;
  padding: 35px 35px;
  background: #fff;
} */

h3 {
  margin: 40px 0 0;
}

.college {
  float: left;
  margin-right: 10px;
}
</style>
