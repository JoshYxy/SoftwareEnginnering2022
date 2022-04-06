<template>
  <div class="teacherCourse">
    <h1>课程维护</h1>
    <el-button type="primary" @click="addCourse">增加课程</el-button>
    <el-table class="table" :data="courseData">
      <el-table-column prop="courseNumber" label="课程序号" width="120"/>
      <el-table-column prop="name" label="课程名称" width="120"/>
      <el-table-column prop="departments" label="开课院系" width="120"/>
      <el-table-column prop="classHour" label="学时" width="120"/>
      <el-table-column prop="credit" label="学分" width="120"/>
      <el-table-column prop="time" label="时间"  width="120"/>
      <el-table-column prop="classroom" label="教室" width="120"/>
      <el-table-column fixed="right"  width="200">
        <el-button type="primary" @click="startChange">编辑</el-button>
        <el-button type="primary" >删除</el-button>
      </el-table-column>
    </el-table>
    <el-dialog v-model="dialogFormVisible" title="课程信息修改" :append-to-body="true" width="400px">
      <el-form
          :model="courseData"
          autocomplete="on"
          text-align:center
          :hide-required-asterisk=true>
<!--        课程原始信息无法显示-->
        <el-form-item prop="courseNumber" label="课程序号">
          <span>{{courseData.courseNumber}}</span>
        </el-form-item>
        <el-form-item prop="name" label="课程名称">
          <el-input
              type="text"
              name="name"
              v-model="courseData.name"
              placeholder="课程名称"
          />
        </el-form-item>
        <el-form-item prop="departments" label="开课院系">
          <span>{{courseData.departments}}</span>
        </el-form-item>
        <el-form-item prop="classHour" label="学时">
          <span>{{courseData.classHour}}</span>
        </el-form-item>
        <el-form-item prop="credit" label="学分">
          <span>{{courseData.credit}}</span>
        </el-form-item>
        <el-form-item prop="classroom" label="教室">
          <el-select placeholder="教室">
          </el-select>
        </el-form-item>
        <el-form-item prop="time" label="上课时间">
          <el-input
              type="text"
              name="time"
              v-model="courseData.time"
              placeholder="上课时间"
          />
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
            <el-button>取消</el-button>
            <el-button type="primary">提交申请</el-button>
        </span>
      </template>
    </el-dialog>
    <el-dialog v-model="dialogTableVisible" title="新增课程" :append-to-body="true" width="400px">
      <el-form >
        <el-form-item prop="name" label="课程名称">
          <el-input
              type="text"
              name="name"
              placeholder="课程名称"
          />
        </el-form-item>
        <el-form-item prop="departments" label="开课院系">
          <el-select placeholder="开课院系">
          </el-select>
        </el-form-item>
        <el-form-item prop="classHour" label="学时">
          <el-input
              type="text"
              name="classHour"
              placeholder="学时"
          />
        </el-form-item>
        <el-form-item prop="credit" label="学分">
          <el-input
              type="text"
              name="credit"
              placeholder="学分"
          />
        </el-form-item>
        <el-form-item prop="classroom" label="教室">
          <el-select placeholder="教室">
          </el-select>
        </el-form-item>
        <el-form-item prop="time" label="上课时间">
          <el-input
              type="text"
              name="time"
              placeholder="上课时间"
          />
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
            <el-button type="primary">提交申请</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script>

export default {
  name: "TeacherCourse.vue",
  data(){
    return{
      dialogFormVisible: false,
      dialogTableVisible:false,
      courseData: [
        {
          courseNumber:'10001',
          name:'软件工程',
          departments:'计算机学院',
          classHour: '3',
          credit:'3',
          time:'周三4-5',
          classroom:'H3209'
        }
      ]
    }
  },
  methods: {
    startChange(){
      this.dialogFormVisible = true
      // this.user.major_=this.major_b//开始时major会因为college的更新被清除，简单解决方案
    },
    addCourse(){
      this.dialogTableVisible = true
    }
  }

}
</script>

<style scoped>

</style>