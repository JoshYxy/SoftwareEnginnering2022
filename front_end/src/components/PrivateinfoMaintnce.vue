<template>
  <div class="descriptions-container">
    <el-form
    label-width="120px"
    
    :model="userData"
    >
    <editable-formitem :data="userData.phone" :rule="phoneRules"></editable-formitem>
  
    <el-form-item label="电话" class="form-label" >
      <span v-if="!phoneEdit" >{{userData.phone}}</span>
      <input v-if="phoneEdit" :value="userData.phone" />
    </el-form-item>
 
  </el-form>
    
  </div>
</template>

<script>
// eslint-disable-next-line 
import EditableFormitem from './EditableFormitem.vue'

export default {
  components:{
    EditableFormitem
  },
  name: 'PrivateinfoMaintnce',
  data() {
    return {
      phoneRules:{info:[{pattern: /^1[3|4|5|7|8][0-9]\d{8}$/, message: '手机号码必须为1开头的符合规则的11位数字'}]},
      buttonOn: false,
      phoneEdit: false,
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
        college: {              
              id: 1,
              name: '计算机科学技术学院', 
              major: [
                  {id: 1, name: '大数据'},
                  {id: 2, name: '信息安全'}
                ]
        },
      },
    }
  },
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
  width:800px;
  margin:auto;
  font-size:18px !important;
}
el-form{
  font-size:18px ;
}
/* .form-label{
  padding: 30px;
} */
/* .my-label {
  width:100px !important;

} */
/* .my-content {
  margin:0px;
  padding: 0px !important;;
} */
</style>
