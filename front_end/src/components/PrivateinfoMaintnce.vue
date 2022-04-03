<template>
  <div class="descriptions-container">
    <el-form
    label-width="120px"
    
    :model="userData"
    >
    <el-form-item label="姓名：" class="form-label" >
      <span class="info-container">{{userData.name}}</span>
    </el-form-item>
    <el-form-item label="学号：" class="form-label" >
      <span class="info-container">{{userData.number}}</span>
    </el-form-item>
    <el-form-item label="身份证：" class="form-label" >
      <span class="info-container">{{userData.id}}</span>
    </el-form-item>
    <el-form-item label="学院：" class="form-label" >
      <span class="info-container">{{userData.college}}</span>
    </el-form-item>
    <el-form-item label="专业：" class="form-label" >
      <span class="info-container">{{userData.major}}</span>
    </el-form-item>

    <editable-formitem label="电话：" :data="userData.phone" :rule="phoneRules"></editable-formitem>
    <editable-formitem label="邮箱：" :data="userData.email" :rule="emailRules"></editable-formitem>
    <el-form-item label="密码：" class="form-label" @mouseover="buttonOn=true"
        @mouseout="buttonOn=false" >
      <span class="info-container">{{userData.password}}</span>

      <el-button v-show="buttonOn" type="text" style="float:right" @click="editPassword()" >
        编辑<el-icon class="el-icon--right"><Edit /></el-icon>
      </el-button>
    </el-form-item>
 
  </el-form>
  <el-dialog v-model="dialogFormVisible" title="修改密码" :append-to-body="true" >
      
        <el-form 
          ref="userPassword" 
          :model="userPassword" 
          :rules="rules" 
          autocomplete="on"  
          text-align:center 
          hide-required-asterisk
          >
            <el-form-item prop="password" label="新密码">
                <el-input type="text" name="password" v-model="userPassword.password"/>
            </el-form-item>
            <el-form-item prop="checkPassword" label="确认密码">
                <el-input type="text" name="password" v-model="userPassword.checkPassword"/>
            </el-form-item>
        </el-form>
        <template #footer>
        <span class="dialog-footer">
            <el-button @click="dialogFormVisible=false">取消</el-button>
            <el-button type="primary" @click="submitPassword">确认</el-button>
        </span>
        </template>
    </el-dialog>
  </div>
</template>

<script>
// eslint-disable-next-line 
import EditableFormitem from './EditableFormitem.vue'
import {Edit} from '@element-plus/icons-vue'
export default {
  components:{
    EditableFormitem,
    Edit
  },
  name: 'PrivateinfoMaintnce',
  data() {
    var validPassword = (rule, value, callback) => {
      const reg = /^(?![0-9]+$)(?![a-zA-Z]+$)(?![-_()]+$)[0-9A-Za-z-_()]{6,32}$/
      if(value!==''){
          if (reg.test(value)) {
            if(this.userPassword.checkPassword!==''){
              this.$refs['userPassword'].validateField('checkPassword', () => null)
            } 
            callback()
          } else {
          return callback(new Error('字母，数字或者特殊字符(-_)至少包含两种'))
          }
      }
    }
    var validPassword2 = (rule, value, callback) => {
        if (value === this.userPassword.password) {
            callback()
        } else {
            return callback(new Error('两次密码不一致'))
        }
    };
    return {
      phoneRules:{info:[{pattern: /^1[3|4|5|7|8][0-9]\d{8}$/, message: '手机号码必须为1开头的符合规则的11位数字'}]},
      emailRules:{info:[{pattern: /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((.[a-zA-Z0-9_-]{2,3}){1,2})$/, message: '邮箱地址不符合规则'}]},
      buttonOn: false,
      phoneEdit: false,
      // checkPassword: '',
      dialogFormVisible: false,
      userPassword: {
        password: '',
        checkPassword: '',
      },
      userData: {
        number: 220001,
        role: 'student',
        name: '张晓王',
        id: '310111200201553211',
        phone: '13301182222',
        email: '123@qq.com',
        stu_id: null,
        teach_id: null,
        password: '123456',
        major: '软件工程班',
        college: '计算机科学技术学院'
      },
      rules: {
        password:  [{required: true, message: '请输入新密码', trigger: 'blur'},
                    {min: 6, max: 32, message: '长度为6-32个字符', trigger: ['blur', 'change']},
                    {validator: validPassword, trigger: ['blur', 'change']}],
        checkPassword: [{required: true, message: '请输入确认密码', trigger: 'blur'},
                    {validator: validPassword2, trigger: ['blur', 'change']}]
      },
    }
  },
  methods: {
    editPassword() {
      this.userPassword.password=''
      this.userPassword.checkPassword=''
      this.dialogFormVisible=true
    },
    submitPassword() {
      this.$refs['userPassword'].validate(valid => {
        if(valid){
          this.dialogFormVisible=false
          //axios
          this.userData.password=this.userPassword.password
        }
      })
    }
  }
  // created: {

  // }
}

</script>

<style scoped>
.data-container{
  min-width:200px;
}
/* .span-container{
  width:100px;
} */
.descriptions-container{
  width:600px;
  margin:auto;
  /* font-size:18px !important; */
}
.info-container{
  min-width: 200px;
}

</style>
