<template>
    <h2>课程审核</h2>
    <el-table class="class-table" :data="pendingCourses" :row-class-name="tableRowClassName">
        <el-table-column fixed prop="teacherName" label="申请人" width="80" />
        <el-table-column fixed prop="showStatus" label="申请类型" width="80" />
        <el-table-column prop="courseName" label="课程名" width="120" />
        <el-table-column prop="courseNum" label="课程编号" width="120" />
        <el-table-column prop="collegeName" label="开课院系" width="180" />
        <el-table-column prop="classHours" label="学时" width="60" />
        <el-table-column prop="credits" label="学分" width="60" />
        <el-table-column prop="building,roomNum" label="上课地点" width="140" >
            <template #default="scope">
                {{scope.row.building}}{{scope.row.roomNum}}
            </template>
        </el-table-column>
        <el-table-column prop="courseTime" label="上课时间" width="180" />
        <el-table-column prop="capacity" label="选课容量" width="150" />
        <el-table-column prop="courseInfo" label="介绍" width="150" >
            <template #default="scope">
                <el-button type="text" @click="dialogTableVisible[scope.$index] = true">查看详情</el-button>
                <el-dialog v-model="dialogTableVisible[scope.$index]" title="课程介绍" :append-to-body="true">
                   {{scope.row.courseInfo}}
                </el-dialog>
            </template>
        </el-table-column>
        <el-table-column fixed="right" label="操作" width="180">
        <template #default="scope">
            <el-button size="small" v-if="pendingCourses[scope.$index].examined == false" type="primary" @click="approve(scope.row)">通过</el-button>
            <el-button size="small" v-if="pendingCourses[scope.$index].examined == false" type="danger" @click="reject(scope.row)">拒绝</el-button>
            <span v-if="pendingCourses[scope.$index].passed == true && pendingCourses[scope.$index].examined == true" style="color:#67c23a"> 已通过 </span>
            <span v-if="pendingCourses[scope.$index].passed == false && pendingCourses[scope.$index].examined == true" style="color:#f56c6c"> 已拒绝 </span>                
        </template>

        </el-table-column>
    </el-table>  
</template>

<script>

export default {
    data() {
        return {
            statusToShow: {
                add: '新增',
                delete: '删除',
                change: '修改',
            },
            dialogTableVisible:[false],
            pendingCourses:[
                {
                    showStatus: '',
                    courseId: 1,
                    courseName: '软件工程',
                    courseNum: 'SOFT220011',
                    teacherNum: '22000001',
                    teacherName: '朱一',
                    classHours: '4',
                    credits: '4',
                    courseTime:'周二: 1-3,5-6,8,10-12; 周三: 3;',
                    capacity: '100',
                    collegeId: '',
                    collegeName: '计算机与技术学院',
                    times: [
                        [],
                        [1,2,3,5,6,8,10,11,12],
                        [3],
                        [],
                        [],
                        [],
                        []
                    ],
                    building: 'H3',
                    roomNum: '301',
                    courseInfo: '123',
                    type: 'delete',
                    examined: false,
                    passed: false,
                }   
            ]
        }
    },
    methods: {
        tableRowClassName(row) {//根据该行课程的状态动态显示该行表格颜色
            if(row.row.type == 'change')
                return 'changing-row';
            if(row.row.type == 'delete')
                return 'deleting-row';
            if(row.row.type == 'add')
                return 'new-row';
        },
        approve(pending_course) {
            pending_course.examined = true
            pending_course.passed = true
        },
        
        reject(pending_course) {
            pending_course.examined = true
            pending_course.passed = false
        },
    },
    created() {
        this.pendingCourses[0].showStatus = this.statusToShow[this.pendingCourses[0].type]
        console.log(this.pendingCourses[0].type)
    }
}
</script>

<style scoped>
</style>