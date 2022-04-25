<template>
    <div v-if="!courseOpen"> <h2>选课时间未到</h2></div>
    <div v-if="courseOpen">
    <h2>可选课程</h2>
    <!-- <el-button @click="test">test </el-button> -->
    <el-table class="class-table" :data="courses">
        <el-table-column fixed prop="courseName" label="课程名" width="150" />
        <el-table-column fixed prop="courseNum" label="课程编号" width="140" />
        <el-table-column prop="collegeName" label="开课院系" width="180" />
        <el-table-column prop="teacherName" label="课程教师姓名" width="120" />
        <el-table-column prop="commonCourse" label="课程类型" width="180">
            <template #default="scope">
                <div v-if="scope.row.commonCourse == '通选课程'">通选课程</div>
                <div v-if="scope.row.commonCourse == '专业课程'">
                    <span style="padding-right:10px">专业课程</span>
                    <el-button type="text" @click="majorTableVisible[scope.$index] = true">查看专业</el-button>
                    <el-dialog v-model="majorTableVisible[scope.$index]" title="课程可选专业" :append-to-body="true">
                        <div v-for="major in courses[scope.$index].majors" :key="major">
                            {{major[1]}} ({{major[0]}})
                        </div>
                    </el-dialog>
                </div>
            </template>
        </el-table-column>
        <el-table-column prop="classHours" label="学时" width="60" />
        <el-table-column prop="credits" label="学分" width="60" />
        <el-table-column prop="building,roomNum" label="上课地点" width="80" >
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

    </el-table>  
    </div>
</template>

<script>
import {setCourseTime} from '../jsComponents/CourseSet'
import axios from 'axios'
export default {
    data() {
        return {
            dialogTableVisible:[false,false,false,false,false,false,false,false,],
            majorTableVisible:[false,false,false,false,false,false,false,false,false,false,false,false,],
            courseOpen: false,
            courses: [
                {
                    courseId: 1,
                    courseName: '软件工程',
                    courseNum: 'SOFT220011',
                    classHours: '4',
                    credits: '4',
                    courseTime:'',
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
                    type: 'normal',
                },
                {
                    courseId: 2,
                    courseName: '离散数学',
                    courseNum: 'SOFT220022',
                    classHours: '3',
                    credits: '5',
                    courseTime:'',
                    collegeId: '',
                    collegeName: '计算机与技术学院',
                    times: [
                        [],
                        [],
                        [],
                        [3,8],
                        []
                    ],
                    building: 'HGX',
                    roomNum: '502',
                    courseInfo: '123',
                    type: 'normal',
                }
            ],
        }
    },
    async created() {
        axios.get('http://localhost:8081/user/curriculaVariable')
        .then(res => {
            this.courseOpen = res.data.data1
        })
        .catch(error => {
            console.dir(error)
        })
        await axios.get('http://localhost:8081/student/course')
        .then(res => {
            this.courses = res.data.data1
        })
        for(let course of this.courses) {
            this.dialogTableVisible.push(false)
            course.courseTime = ''
            setCourseTime(course, course.times)
        }
    }
}
</script>

<style>

</style>