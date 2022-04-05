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
/* eslint-disable */
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
    watch: {
        data: {
            handler(){
                this.edited_info=this.data;
                this.user.info=this.data;
            },
        }
    },
    methods: {
        submit() {
            //axios
            this.$refs['user'].validate(valid => {
                if(valid){
                    //匹配后端格式
                    let postData = {
                        status: this.userData.status,
                        role: this.userData.role, 
                        number: this.userData.number,
                        password: this.userData.password,
                        phone: this.label==='电话：'?this.user.info:this.userData.phone,
                        email: this.label==='邮箱：'?this.user.info:this.userData.email,
                    }
                    axios.post('http://localhost:8081/user/info',postData) 
                        .then(res =>{
                            console.log(res)
                            this.$emit('changeInfo', {label: this.label, value: this.user.info})
                            this.edited_info=this.user.info
                            this.edit=false
                        })
                        .catch(error => {
                        alert("修改失败")
                            console.dir(error)
                        })

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
