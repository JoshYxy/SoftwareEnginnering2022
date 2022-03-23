<template>
  <div class="password">
    <el-form class='reset-container' ref="passwordForm" :model="passwordForm" :rules="rules" autocomplete="on" label-position="left">
      <h2 class='reset-title'>重置密码</h2>
      <el-form-item prop="password">
        <el-input
          name="password"
          v-model="passwordForm.password"
          placeholder="密码"
          type="text"
          maxlength="40"
        />
      </el-form-item> 
      <el-form-item prop="password2">
        <el-input
          name="password2"
          v-model="passwordForm.password2"
          placeholder="确认密码"
          type="password"
          maxlength="40"
          @keydown.enter="submitt"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="submitt">提交</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
// eslint-disable-next-line
import axios from 'axios'


export default {
  name: 'ResetPassword',

  data(){
    var validPassword = (rule, value, callback) => {
      const reg = /^(?![0-9]+$)(?![a-zA-Z]+$)(?![-_()]+$)[0-9A-Za-z-_()]{6,32}$/
      if(value!==''){
          if (reg.test(value)) {
            if(this.passwordForm.password2!==''){
              this.$refs['passwordForm'].validateField('password2', () => null)
            } 
            callback()
          } else {
          return callback(new Error('字母，数字或者特殊字符(-_)至少包含两种'))
          }
      }
    }
    var validPassword2 = (rule, value, callback) => {
        if (value === this.passwordForm.password) {
            callback()
        } else {
            return callback(new Error('两次密码不一致'))
        }
    };
    return {
      passwordForm: {
        password: '',
        password2: ''
      },
      passwordForm2: {
        password: ''
      },
      rules: {
        password:  [{required: true, message: '请输入密码', trigger: 'blur'},
                    {min: 6, max: 32, message: '长度为6-32个字符', trigger: ['blur', 'change']},
                    {validator: validPassword, trigger: ['blur', 'change']}],
        password2: [{required: true, message: '请输入确认密码', trigger: 'blur'},
                    {validator: validPassword2, trigger: ['blur', 'change']}]
      }
    }
  },
  methods:{
    submitt(){
      this.$refs['passwordForm'].validate(valid => {
        if(valid){
          this.passwordForm2.password = this.passwordForm.password
          // axios.post('http://localhost:8081/reset', null, {params:this.passwordForm2})
          axios.post('http://localhost:8081/user/password', this.passwordForm2)
          .then(res => {
            console.log(res);
            alert("重置成功")
            console.log(this.passwordForm)
            this.passwordForm.password=''
            this.passwordForm.password2=''
            this.$router.push('/home/welcome')
          }).catch(error => {
            alert('重置失败');
            console.dir(error);
          });
        }
        else{
          console.log('error submit');
          return false
        }
      })
    }
  }
}

</script>

<style scoped>
h3 {
  margin: 40px 0 0;
}

.reset-container {
  border-radius: 15px;
  background-clip: padding-box;
  margin: 90px auto;
  width: 350px;
  padding: 35px 35px 15px 35px;
  background-color: transparent;
  border: 1px solid transparent;
  box-shadow: 0 0 25px transparent;
}

.reset-title {
  margin: 0px auto 40px auto;
  text-align: center;
  color: #505458;
}

.password {
  width: 100%;
  height: 530px;
  background: url("../assets/1.jpg") no-repeat;
  background-size: cover;
  overflow: hidden;
}

</style>