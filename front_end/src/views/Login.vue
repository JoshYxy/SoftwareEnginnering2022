<template>
  <div class="login">
    <el-form class='login-container' ref="loginForm" :model="loginData" autocomplete="on" label-position="left">
      <h2 class='login-title'>登录</h2>
      <el-form-item
        prop="number"
        :rules="[{ required: true, message: '用户名不能为空'}]"
      >
        <el-input 
          prefix-icon="Avatar" 
          name="number" 
          v-model="loginData.number" 
          ref="number" 
          placeholder="学号/工号" 
          type="text" 
          maxlength="40">
          <template #prefix>
            <el-icon class="el-icon_a"><avatar /></el-icon>
          </template>
        </el-input>
      </el-form-item> 
      <el-form-item
        prop="password"
        :rules="[{ required: true, message: '密码不能为空'}]"
      >
        <el-input
          prefix-icon="Key" 
          name="password"
          v-model="loginData.password"
          placeholder="密码"
          type="password"
          maxlength="40"
          @keydown.enter="submit"
          >
          <template #prefix>
            <el-icon class="el-icon_k"><key /></el-icon>
          </template>
        </el-input>
      </el-form-item>
      <el-form-item>
        <el-button  style="display:block;margin:0 auto" type="primary" @click="submit">
          <el-icon :size="20">
            <circle-check />
          </el-icon>
        </el-button>
      </el-form-item>
    </el-form>
    
  </div>
</template>

<script>
import { mapMutations } from 'vuex';
import {CircleCheck,Avatar,Key} from "@element-plus/icons";
import axios from 'axios'
export default {
  name: 'UserLogin',
    components:{
    Avatar,
    CircleCheck,
    Key
  },
  data() {
    return {
      userToken: '',
      loginData: {
        number:null,
        password:''
      },
    }
  },
  // this.axios({
  //           method: 'post',
  //           url: '/user/login',
  //           data: _this.loginForm
  //         })
  methods: {
     ...mapMutations(['changeLogin']),
    submit() {
      this.$refs['loginForm'].validate(valid => {
        if(valid) {
          // axios.post('http://localhost:8081/login', null,{params:this.loginData})
          axios.post('http://localhost:8081/user', this.loginData)
          .then(res => {
            console.log(res);
            alert('登陆成功');
            this.userToken = res.headers.token;
            // 将用户token保存到vuex中
            this.changeLogin({ Authorization: this.userToken });//传参内容为一个对象
            localStorage.setItem('role', res.headers.role)

            if(this.loginData.password == '123456') {
              alert('初次登录，请重置密码')
              this.$router.push({path:"/password"})}
            else this.$router.push({path:"/home/welcome"});
          }).catch(error => {
            if(error.response.status == 426){
                alert('身份已过期');
              }
            else alert('账号或密码错误!!');
            console.dir(error);
          });
        }
        else {
          console.log('error submit');
          return false
        }
      })
    }
  }
}

</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h3 {
  margin: 40px 0 0;
}

.login-container {
  border-radius: 15px;
  background-clip: padding-box;
  margin: 120px 150px auto auto;
  width: 350px;
  padding: 35px 35px 15px 35px;
  background-color: transparent;
  border: 1px solid #eaeaea;
  box-shadow: 0 0 25px #cac6c6;
}

.login-title {
  margin: 0 auto 40px auto;
  text-align: center;
  color: #505458;
}

.el-icon_a {
  margin-top: 10px;
}

.el-icon_k {
  margin-top: 10px;
}
</style>
