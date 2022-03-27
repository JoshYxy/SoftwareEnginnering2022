<template>
  <div >
    <h2 style="margin-right:250px">专业信息管理</h2>
    <el-button type='primary' @click="appendCollege()"> 新增学院 </el-button>
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
            <a v-if="data.major" @click="append(data)"> 新增专业 </a>
     
            <a @click="openAlter(data)"> 修改 </a>
            <a @click="openRemove(node, data)"> 删除 </a>
          </span>
        </span>
      </template>
    </el-tree>
  </div>
</template>

<script>
import { ElMessage, ElMessageBox } from 'element-plus'
// import axios from 'axios'
export default {
  name: 'MajorMaintenance',
  data() {
    return { 
      id: 100,
      visible: false,
      defaultProps: {
        children: 'major',
        label: 'name',
      },
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
          id: 2,
          name: '生命科学学院', 
          major: [
              {id: 1, name: '生物'},
              {id: 2, name: '123'}
          ]
        },
        { 
          id: 3,
          name: '软件工程学院', 
          major: [
              {id: 1, name: '软件工程'},
          ]
        },
      ],
    }
  },
  methods: {
    appendCollege() {
      const data = {id: this.id++, name: '新的学院', major:[]}
      this.collegeData.push(data)
      this.openAlter(data)
    },
    append(data) {
      const newChild = { id: this.id++, name: '新的专业'}
      if (!data.major) {
        data.major = []
      }
      data.major.push(newChild)
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
      const children = parent.data.major || parent.data
      const index = children.findIndex((d) => d.id === data.id)
      children.splice(index, 1)
      this.collegeData = [...this.collegeData]
    },
    openRemove(node, data) {
      ElMessageBox.confirm(
        '是否删除 ' + data.name + (data.major?"":"专业"),
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
      const alertMessage='请输入新的'+(data.major?'学院':'专业')+'名称'
      ElMessageBox.prompt(alertMessage, '修改', {
        confirmButtonText: '确认',
        cancelButtonText: '取消',
        // inputPattern:
        //   /[\w!#$%&'*+/=?^_`{|}~-]+(?:\.[\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\w](?:[\w-]*[\w])?\.)+[\w](?:[\w-]*[\w])?/,
        // inputErrorMessage: 'Invalid Email',
      })
        .then(({ value }) => {
          this.alter(data, value),
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
   
  }
}

</script>

<style>
.el-tree-container{
  width:600px;
  margin-left: 150px;

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
  font-size: 16px;
}
</style>
