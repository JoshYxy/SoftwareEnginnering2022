<template>
    <el-form
    label-width="120px"
    :rules="this.rule"
    :model="user"
    ref="user"
    >
    <el-form-item 
        label="电话" 
        @mouseover="buttonOn=true"
        @mouseout="buttonOn=false" 
        prop="info" 
        >
        <div class="data-container">
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
// import axios from 'axios'
import {Edit} from '@element-plus/icons-vue'
export default {
    name: 'EditableFormitem',
    props: ["data", "rule"],
    components:{
        Edit,
    },
    data() {
        return {
            buttonOn: false,
            edit: false,
            user:{
                info:this.data
            },
            // info: this.data,
            edited_info: this.data,
        }
    },
    methods: {
        submit() {
            //axios
            this.$refs['user'].validate(valid => {
                if(valid){
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
.data-container{
  min-width:200px;
}
</style>
