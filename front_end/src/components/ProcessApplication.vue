<template>
  <div class="process">
    <h1>待处理申请</h1>
    <el-table class="table" :data="applicationData">
      <el-table-column fixed prop="number" label="序号" width="200" />
      <el-table-column prop="name" label="申请教师" width="200" />
      <el-table-column prop="type" label="申请类型"  width="200"/>
      <el-table-column label="详情" width="200">
        <el-button type="primary" @click="detail">详情</el-button>
      </el-table-column>
      <el-table-column fixed="right" label="审批" width="200">
        <el-button type="success" plain>通过</el-button>
        <el-button type="danger" plain>不通过</el-button>
      </el-table-column>
    </el-table>
    <el-dialog v-model="dialogFormVisible" title="详情" :append-to-body="true" width="400px">
      <el-form
          autocomplete="on"
          text-align:center
          :hide-required-asterisk=true>
        <el-form-item label="课程序号"></el-form-item>
        <el-form-item label="课程名称">
          <span>原始：</span>
          <span>修改后：</span>
        </el-form-item>
        <el-form-item label="开课院系"></el-form-item>
        <el-form-item label="学时"></el-form-item>
        <el-form-item label="学分"></el-form-item>
        <el-form-item label="上课教室">
          <span>原始：</span>
          <span>修改后：</span>
        </el-form-item>
        <el-form-item label="上课时间">
          <span>原始：</span>
          <soan>修改后：</soan>
        </el-form-item>
      </el-form>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: "ProcessApplication.vue",
  data(){
    return{
      dialogFormVisible: false,
      applicationData: [
        {
          number:1,
          name:'紫气东来',
          type:'增加课程'
        }
      ]
    }
  },
  methods: {
    detail(){
      this.dialogFormVisible = true
    }
  }
}
</script>

<style scoped>

</style>