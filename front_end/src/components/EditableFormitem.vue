<template>
    <el-form
    label-width="120px"
    :rules="this.rule"
    :model="user"
    ref="user"
    >
    <el-form-item 
        @mouseover="buttonOn=true"
        @mouseout="buttonOn=false" 
        prop="info" 
        >
        <template #label>{{label}}</template>
        <div class="editabel-form-container"> 
            <!-- {{this.label}} -->
            <span class="span-container" v-show="!edit">{{edited_info}}</span>
            <el-input v-show="edit" v-model="user.info"  />
        </div>
        <el-button v-show="buttonOn&&(!edit)" type="text" style="float:right" @click="edit=true"  >
            编辑<el-icon class="el-icon--right"><Edit /></el-icon>
        </el-button>
        <el-button v-show="edit" type="text" style="float:right" @click="submit">
            提交
        </el-button>
        <el-button v-show="edit" type="text" style="float:right" @click="cancel">
            取消
        </el-button>
       
    </el-form-item>
    </el-form>
</template>

<script>
import axios from 'axios'
import {Edit} from '@element-plus/icons-vue'
export default {
    name: 'EditableFormitem',
    props: ["data", "rule", "label", "userData"],
    components:{
        Edit,
    },
    data() {
        return {
            buttonOn: false,
            edit: false,
            user:{
                info:this.data//修改中的值
            },
            // info: this.data,
            edited_info: this.data,//显示出来的值
        }
    },
    methods: {
        submit() {
            //axios
            this.$refs['user'].validate(valid => {
                if(valid){
                    //匹配后端格式
                    axios.post('http://localhost:8081/user/info',
                        {
                            role: this.userData.role, 
                            number: this.userData.number,
                            password: this.userData.password,
                            phone: this.label==='电话：'?this.user.info:this.userData.phone,
                            email: this.label==='邮箱：'?this.user.info:this.userData.email,
                        })
                    this.$emit('changeInfo', {label: this.label, value: this.user.info})
                    console.log(1)
                    this.edited_info=this.user.info
                    this.edit=false
                }
            })
        },
        cancel() {
            this.edit=false
            this.user.info=this.edited_info
        }
    }
}

</script>

<style scoped>
/* .form-item-label{
    
} */
.editabel-form-container{
  min-width:200px;
}
</style>
