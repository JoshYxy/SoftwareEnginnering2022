<template>
  <div class="majorMaintenance" >
    <h2 style="margin-right:250px">专业信息管理</h2>
    <el-button style="display:block;margin:0 615px auto auto"  type='primary' @click="appendCollege()"> 新增学院 </el-button>
    <el-tree
      :data="this.collegeData"
      default-expand-all
      :props="this.defaultProps" 
      :expand-on-click-node="false"
      class="el-tree-container"
    >
      <template #default="{ node, data }">
        <span class="custom-tree-node">
          <span>{{ data.name }}</span>
          <span>
            <a v-if="data.majors" @click="append(data)" style="font-weight: bold"> 新增专业 </a>
     
            <a @click="openAlter(data)" style="font-weight: bold"> 修改 </a>
            <a @click="openRemove(node, data)" style="font-weight: bold"> 删除 </a>
          </span>
        </span>
      </template>
    </el-tree>
  </div>
</template>

<script>
import { ElMessage, ElMessageBox } from 'element-plus'
import axios from 'axios'
export default {
  name: 'MajorMaintenance',
  data() {
    return { 
      id: 0,
      visible: false,
      defaultProps: {
        children: 'majors',
        label: 'name',
      },
      collegeData: [
        { 
          id: 1,
          name: '计算机科学技术学院', 
          majors: [
              {id: 1, name: '大数据'},
              {id: 2, name: '信息安全'}
          ]
        },
        { 
          id: 2,
          name: '生命科学学院', 
          majors: [
              {id: 1, name: '生物'},
              {id: 2, name: '123'}
          ]
        },
        { 
          id: 3,
          name: '软件工程学院', 
          majors: [
              {id: 1, name: '软件工程'},
          ]
        },
      ],
    }
  },
  methods: {
    appendCollege() {
      const data = {id: this.id++, name: '新的学院'+this.id, majors:[]}
      this.collegeData.push(data)
      
      this.openAlter(data)
    },
    append(data) {
      const newChild = { id: this.id++, name: '新的专业'+this.id}
      if (!data.majors) {
        data.majors = []
      }
      data.majors.push(newChild)
      this.openAlter(newChild)
      this.collegeData = [...this.collegeData]//更新数据，刷新页面
    },
    alter(data, name) {
      data.name=name;
      //TODO:axios
      this.collegeData = [...this.collegeData]
    },
    remove(node, data) {
      //TODO:axios
      const parent = node.parent
      console.log(parent)
      const children = parent.data.majors || parent.data
      const index = children.findIndex((d) => d.id === data.id)
      children.splice(index, 1)
      this.collegeData = [...this.collegeData]
    },
    openRemove(node, data) {
      ElMessageBox.confirm(
        '是否删除 ' + data.name + (data.majors?"":"专业"),
        'Warning',
        {
          confirmButtonText: '确认',
          cancelButtonText: '取消',
          type: 'warning',
        }
      )
        .then(() => {
          this.remove(node, data)
          ElMessage({
            type: 'success',
            message: '删除成功',
          })
        })
        .catch(() => {
          ElMessage({
            type: 'info',
            message: '删除取消',
          })
        })
    },
    openAlter(data) {
      const alertMessage='请输入新的'+(data.majors?'学院':'专业')+'名称'
      ElMessageBox.prompt(alertMessage, '修改', {
        confirmButtonText: '确认',
        cancelButtonText: '取消',
      })
        .then(({ value }) => {
          this.alter(data, value)
          ElMessage({  
            type: 'success',
            message: `修改成功`,
          })
        })
        .catch(() => {
          ElMessage({
            type: 'info',
            message: '修改取消',
          })
        })
    }
   
  },
  created() {
    axios.get("http://localhost:8081/admin/edu")
      .then(res => {
        this.collegeData = res
        for(let i in this.collegeData) {//替换变量名,对应后端数据
          this.collegeData[i] = JSON.parse(JSON.stringify(this.collegeData[i]).replace(/collegeVOId/g,"id"))
          this.collegeData[i] = JSON.parse(JSON.stringify(this.collegeData[i]).replace(/majorId/g,"id"))
          this.collegeData[i] = JSON.parse(JSON.stringify(this.collegeData[i]).replace(/collegeVOName/g,"name"))
          this.collegeData[i] = JSON.parse(JSON.stringify(this.collegeData[i]).replace(/majorName/g,"name"))
          
        }
      }).catch(error => {
            
        console.dir(error);
      });
  }
}

</script>

<style>
.el-tree-container{
  width:600px;
  border: grey 2px solid;
  margin-top: 20px;
  margin-left: 50px;
  padding: 20px 200px 20px 0;
  position: absolute;
}
.el-tree-node__expand-icon{
  width:300px;
  max-height: 12px;
  margin-right:-140px;
  transform: none !important;
}
.el-tree-node__expand-icon .expanded{
  width:300px;
  max-height: 12px;
  transform: rotate(90deg);
  margin-right:-150px;
  position: relative;
  transform: none !important;
}
.custom-tree-node {
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: space-between;
  font-size: 14px;
  padding-right: 8px;
  padding-left: 0;
  font-size: 16px;
}
</style>
