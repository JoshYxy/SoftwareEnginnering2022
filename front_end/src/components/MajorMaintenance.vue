<template>
  <div >
    <el-tree
      :data="this.collegeData"
      node-key="id"
      default-expand-all
      :props="this.defaultProps" 
      :expand-on-click-node="false"
      class="el-tree-container"
    >
      <template #default="{ node, data }">
        <span class="custom-tree-node">
          <span>{{ data.name }}</span>
          <span>
            <a v-if="data.major" @click="append(data)"> 新增 </a>
            <a @click="remove(node, data)"> 删除 </a>
          </span>
        </span>
      </template>
    </el-tree>
  </div>
</template>

<script>

// import axios from 'axios'
export default {
  name: 'MajorMaintenance',
  data() {
    return { 
      id: 100,
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
    append(data) {
      const newChild = { id: this.id++, name: '新的专业'}
      if (!data.major) {
        data.major = []
      }
      data.major.push(newChild)
      console.log(this.collegeData)
      this.collegeData = [...this.collegeData]//更新数据，刷新页面
    },

    remove(node, data) {
      const parent = node.parent
      const children = parent.data.children || parent.data
      const index = children.indexOf((d) => d.id === data.id)
      children.splice(index, 1)
      this.collegeData = [...this.collegeData]
    }
  }
}

</script>

<style>
.el-tree-container{
  width:500px;
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
}
</style>
