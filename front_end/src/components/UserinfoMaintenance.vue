<template>
  <div>
    <el-button type="text" @click="startChange"
        >编辑</el-button
    >

    <el-dialog v-model="dialogFormVisible" title="信息修改" :append-to-body="true" >
      
        <el-form 
          id="admin_modify" 
          ref="user" 
          :model="user" 
          :rules="rules" 
          autocomplete="on"  
          text-align:center 
          hide-required-asterisk=true
          >
            <el-form-item prop="college" label="学院">
                <el-select v-model="user.college" value-key="name" placeholder="学院">
                <el-option :key="colleges.id" :value="colleges" :label="colleges.name" v-for="colleges in collegeData" ></el-option>
                </el-select>
            </el-form-item>

            <el-form-item prop="major_" label="专业">
                <el-select v-model="user.major_" placeholder="专业">
                <el-option :key="major.id" :value="major.name" :label="major.name" v-for="major in user.college.major" ></el-option>
                </el-select>
            </el-form-item>
            
            <el-form-item prop="name" label="姓名">
                <el-input
                type="text"
                name="name"
                v-model="user.name"
                placeholder="姓名"
                />
            </el-form-item> 

            <el-form-item prop="id" label="身份证">
                <el-input
                name="id"
                v-model="user.id"
                placeholder="身份证号"
                type="text"
                />
            </el-form-item>

            <el-form-item prop="phone" label="手机号">
                <el-input
                type="text"
                name="phone"
                v-model="user.phone"
                placeholder="手机号"
                />
            </el-form-item> 

            <el-form-item prop="email" label="邮箱">
                <el-input
                type="text"
                name="email"
                v-model="user.email"
                placeholder="邮箱"
                />
            </el-form-item>
            <el-form-item prop="password" label="密码">
                <el-input
                type="text"
                name="password"
                v-model="user.password"
                placeholder="密码"
                />
            </el-form-item>
        </el-form>
        <template #footer>
        <span class="dialog-footer">
            <el-button @click="cancel">取消</el-button>
            <el-button type="primary" @click="submit">确认</el-button>
        </span>
        </template>
    </el-dialog>
  </div>
</template>

<script>

// import axios from 'axios'

import {validName,validPhone,validPassport,validEmail} from './jsComponents/CheckRules'//格式检查
export default {
  name: 'UserinfoMaintenance',
  props: ['userInfo','collegeData','major_b'],
  data() {
    return {

        dialogFormVisible: false,
        formLabelWidth: '140px',
//与录入信息处相同
        // this.userInfo,
        user: {
          number: null,
          role: '',
          name: '',
          id: '',
          phone: '',
          email: '',
          stu_id: null,
          teach_id: null,
          password: '',
          major_: '',
          college: {},
        },

        rules: {
            college: [{required: true, message: '请选择学院', trigger: 'change'}],
            major_: [{required: true, message: '请选择专业', trigger: 'change'}],
            role: [{required: true, message: '请选择身份', trigger: 'change'}],
            name: [{required: true, message: '姓名不能为空', trigger: 'blur'},
                {validator: validName, trigger: ['blur', 'change']}],
            id: [{ required: true, message: '身份证不能为空', trigger: 'blur'},
                    { validator: validPassport, trigger: 'blur' }],
            phone: [{required: false, trigger: ['blur', 'change']},
                    {validator: validPhone, trigger: 'blur'}],
            email: [{required: false, trigger: 'blur'},
                    {validator: validEmail, trigger: 'blur'}],
            password: [{required: true, message: '密码不能为空', trigger: 'blur'},
                       {min: 6, max: 32, message: '长度为6-32个字符', trigger: ['blur', 'change']}]
        },
    }
  },
  watch:{
    "user.college.name": {
      handler(){
        // console.log(123)
        // console.log(this.user)
        this.user.major_='';  
      },  
    }
  },
  methods: {
      startChange(){
        this.dialogFormVisible = true
        //从数据库获取user信息
        //或许不用，父组件信息更新，子组件自动更新
        this.user.major_=this.major_b//开始时major会因为college的更新被清除，简单解决方案
      },
      cancel(){
        this.dialogFormVisible = false
        this.user=JSON.parse(JSON.stringify(this.userInfo))
      },
      submit(){
      this.$refs['user'].validate(valid => {
        if(valid){
          //axios
            this.dialogFormVisible = false
            this.$emit('changeInfo', this.user);
        //   console.log(this.user)
        //   // axios.post('http://localhost:8081/register', null, {params:this.user})
        //   axios.post('http://localhost:8081/user/new', this.user)
        //   .then(function(resp){
        //     console.log(resp)
        //     alert('提交成功')
              
        //   }).catch(error => {
        //     if(error.response.status == 421){
        //       if(this.user.role == 'student')
        //         alert('学号已存在')
        //       else
        //         alert('工号已存在')
        //       this.user.stu_id = ''
        //       this.user.teach_id = ''
        //     }
        //     if(error.response.status == 420){
        //       alert('身份证已存在');
        //       this.user.id = ''
        //     }
        //     console.dir(error);
        //   })
        }
        else{
          console.log('error ssssubmit');
          return false
        }
      })
        
    }
  },
  created: function(){
    this.user=JSON.parse(JSON.stringify(this.userInfo))
    
  }
}

</script>

<style scoped>

</style>
