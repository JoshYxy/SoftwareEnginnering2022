<template>
  <div class="major-container">
    <h2 style="margin-right:250px">专业信息管理</h2>
    <el-button style="display:block;margin:0 340px" type='primary' round @click="appendCollege()" > 新增学院 </el-button>
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
            <a v-if="data.majors" @click="append(node, data)" style="font-weight: bold;color: dodgerblue"> 新增专业 </a>
            <a @click="openAlter(node, data)" style="font-weight: bold;color: dodgerblue"> 修改 </a>
            <a @click="openRemove(node, data)" style="font-weight: bold;color: red"> 删除 </a>
          </span>
        </span>
      </template>
    </el-tree>
  </div>
</template>

<script>
/* eslint-disable */
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
          id:0,
          name:'1',
          majors:{id:1,name:'2'}
        }
      ]
    }
  },
  methods: {
    setId() {//根据数据库中“新的学院xx”“新的专业xx”后的数字设置id，避免名称重复
      for(let i of this.collegeData)
      {
        if(i.name.indexOf('新的学院') > -1) this.id = Math.max(this.id, parseInt(i.name.substr(4)))
        for(let j of i.majors) {
          if(j.name.indexOf('新的专业') > -1) this.id = Math.max(this.id, parseInt(j.name.substr(4)))
        }
        // this.id = Math.max(this.id, i.name.slice(9))
      }
      // console.log(this.id)
    },
    appendCollege() {
      const data = {id: this.id++, name: '新的学院'+this.id, majors:[]}
      const node = {}
      axios.post('http://localhost:8081/admin/edu/college/new',{name: data.name})
        .then(res => {
          console.log(res)
          data.id = res.data.data1
          this.collegeData.push(data)
          this.openAlter(node, data)
        }).catch(error => {
          alert("学院名称重复")
          console.dir(error);
        });
      
    },
    append(node, data) {
      const newChild = { id: this.id++, name: '新的专业'+this.id}
      if (!data.majors) {
        data.majors = []
      }
      axios.post('http://localhost:8081/admin/edu/major/new',{collegeName: data.name, name: newChild.name})
        .then(res => {
          console.log(res)
          newChild.id = res.data.data1
          data.majors.push(newChild)
          console.log(data.majors)
          this.openAlter(node, newChild)
          this.collegeData = [...this.collegeData]//更新数据，刷新页面
        }).catch(error => {
          alert("专业名称重复")
          console.dir(error);
        });
      
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
          if(data.majors) { //删除学院
            axios.delete('http://localhost:8081/admin/edu/college',{data: {collegeId: data.id, name: data.name}})//delete传参需多裹一层{data:}
              .then(res => {
                console.log(res)
                this.remove(node, data)
                ElMessage({type: 'success', message: `删除成功`})
              }).catch(error => {
                ElMessage.error(error.response.data.msg)
                console.dir(error);
              });
          }
          else {
            axios.delete('http://localhost:8081/admin/edu/major',{data:{majorId: data.id, name: data.name, collegeName: node.parent.data.name}})
              .then(res => {
                console.log(res)
                this.remove(node, data)
                ElMessage({type: 'success', message: `删除成功`})
              }).catch(error => {
                ElMessage.error(error.response.data.msg)
                console.dir(error);
              });
          }
        })
        .catch(() => {
          ElMessage({
            type: 'info',
            message: '删除取消',
          })
        })
    },
    openAlter(node, data) {
      const alertMessage='请输入新的'+(data.majors?'学院':'专业')+'名称'
      ElMessageBox.prompt(alertMessage, '修改', {
        confirmButtonText: '确认',
        cancelButtonText: '取消',
      })
        .then(({ value }) => {
          if(data.majors) {//修改学院
            axios.post('http://localhost:8081/admin/edu/college',{collegeId: data.id, name: value})
              .then(res => {
                console.log(res)
                this.alter(data, value)
                ElMessage({type: 'success', message: `修改成功`})
              }).catch(error => {
                if(error.response.status == 428){
                  ElMessage.error('学院名称重复')
                }
                console.dir(error);
              });
          }
          else { //修改专业
            axios.post('http://localhost:8081/admin/edu/major',{majorId: data.id, name: value, collegeName: node.parent.data.name})
              .then(res => {
                console.log(res)
                this.alter(data, value)
                ElMessage({type: 'success', message: `修改成功`})
              }).catch(error => {
                if(error.response.status == 429)
                  ElMessage.error('专业名称重复')
                
                console.dir(error);
              });        
          }
        })
        .catch(() => {
          ElMessage({
            type: 'info',
            message: '修改取消',
          })
        })
    }
   
  },
  async created() {
    await axios.get("http://localhost:8081/admin/edu")
      .then(res => {
        this.collegeData = res.data.data1
        for(let i in this.collegeData) {//替换变量名,对应后端数据
          this.collegeData[i] = JSON.parse(JSON.stringify(this.collegeData[i]).replace(/collegeVOId/g,"id"))
          this.collegeData[i] = JSON.parse(JSON.stringify(this.collegeData[i]).replace(/majorId/g,"id"))
          this.collegeData[i] = JSON.parse(JSON.stringify(this.collegeData[i]).replace(/collegeVOName/g,"name"))
          this.collegeData[i] = JSON.parse(JSON.stringify(this.collegeData[i]).replace(/majorName/g,"name"))
          
        }
      }).catch(error => {
        alert('获取服务器信息失败')
        console.dir(error);
      });
    this.setId()
  }
}

</script>

<style>
.major-container .el-tree-container{
  width:600px;
  margin-left: 150px;

}
.major-container .el-tree-node__expand-icon{
  width:300px;
  max-height: 12px;
  margin-right:-140px;
  transform: none !important;
}
.major-container .el-tree-node__expand-icon .expanded{
  width:300px;
  max-height: 12px;
  transform: rotate(90deg);
  margin-right:-150px;
  position: relative;
  transform: none !important;
}
.major-container .custom-tree-node {
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: space-between;
  font-size: 14px;
  padding-right: 8px;  
  font-size: 16px;
}
</style>
