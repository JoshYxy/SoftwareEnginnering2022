<template>
    <h2>选课申请审核</h2>
    <el-table class="class-table" :data="applyCourses" :row-class-name="tableRowClassName">
        <el-table-column fixed prop="teacherName" label="申请人" width="80" />
        <el-table-column fixed prop="teacherName" label="申请学号" width="80" />
        <el-table-column prop="courseName" label="课程名" width="140" />
        <el-table-column prop="courseNum" label="课程编号" width="150" />
        <el-table-column prop="teacherName" label="课程教师" width="80" />
        <el-table-column prop="year,semester" label="开课学期" width="180">
            <template #default="scope">
                {{scope.row.year}}{{scope.row.semester}}
            </template>
        </el-table-column>
        <el-table-column prop="collegeName" label="开课院系" width="180" />
        <el-table-column prop="commonCourse" label="课程类型" width="180">
            <template #default="scope">
                <div v-if="scope.row.commonCourse == '通选课程'">通选课程</div>
                <div v-if="scope.row.commonCourse == '专业课程'">
                    <span style="padding-right:10px">专业课程</span>
                    <el-button type="text" @click="majorTableVisible[scope.$index] = true">查看专业</el-button>
                    <el-dialog v-model="majorTableVisible[scope.$index]" title="课程可选专业" :append-to-body="true">
                        <div v-for="major in applyCourses[scope.$index].majors" :key="major">
                            {{major[1]}} ({{major[0]}})
                        </div>
                    </el-dialog>
                </div>
            </template>
        </el-table-column>
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
        <el-table-column fixed="right" prop="applyReason" label="选课理由" width="150" >
            <template #default="scope">
                <el-button type="text" @click="applyTableVisible[scope.$index] = true">查看申请</el-button>
                <el-dialog v-model="applyTableVisible[scope.$index]" title="申请内容" :append-to-body="true">
                   {{scope.row.applyReason}}
                </el-dialog>
            </template>
        </el-table-column>
        <el-table-column fixed="right" label="操作" width="180">
        <template #default="scope">
            <el-button size="small" v-if="applyCourses[scope.$index].examined == false" type="primary" @click="approve(scope.row)">通过</el-button>
            <el-button size="small" v-if="applyCourses[scope.$index].examined == false" type="danger" @click="reject(scope.row)">拒绝</el-button>
            <span v-if="applyCourses[scope.$index].passed == true && applyCourses[scope.$index].examined == true" style="color:#67c23a"> 已通过 </span>
            <span v-if="applyCourses[scope.$index].passed == false && applyCourses[scope.$index].examined == true" style="color:#f56c6c"> 已拒绝 </span>                
        </template>

        </el-table-column>
    </el-table>  
</template>

<script>
import axios from 'axios'
import {setCourseTime} from '../jsComponents/CourseSet'
export default {
    data() {
        return {
            dialogTableVisible:[false,false,false,false,false,false,false,false],
            majorTableVisible:[false,false,false,false,false,false,false,false,false,false,false,false,],
            applyTableVisible:[false,false,false,false,false,false,false,false,false,false,false,false,],
            applyCourses:[
                {
                    showStatus: '',
                    courseId: 1,
                    requestId: 1,
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
                    type: 'change',
                    commonCourse: '专业课程',
                    majors: [
                                ["计算机科学技术学院","大数据"],
                                ["计算机科学技术学院","信息安全"],
                                ['软件工程学院','软件工程']
                            ],
                    year:'2021-2022',
                    semester:'春',
                    examined: false,
                    passed: false,
                },
                {
                    showStatus: '',
                    courseId: 2,
                    requestId: 2,
                    courseName: '软件',
                    courseNum: 'SOFT2132333',
                    teacherNum: '22000000',
                    teacherName: '朱二',
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
                    commonCourse: '通选课程',
                    majors: [
                                ["计算机科学技术学院","大数据"],
                                ["计算机科学技术学院","信息安全"],
                                ['软件工程学院','软件工程']
                            ],
                    year:'2021-2022',
                    semester:'春',
                    examined: false,
                    passed: false,
                }   
            ]
        }
    },
    methods: {
        approve(pending_course) {
            axios.post('http://localhost:8081/course/courseRequests', {requestId: pending_course.requestId, res: true})
            .then( res => {
                console.log(res)
                pending_course.examined = true
                pending_course.passed = true
            })
            .catch(error => {
                console.dir(error)
                alert(error.response.data.msg)
            })
        },
        
        reject(pending_course) {
            axios.post('http://localhost:8081/course/courseRequests', {requestId: pending_course.requestId, res: false})
            pending_course.examined = true
            pending_course.passed = false
        },
    },
    created() {
        axios.get('http://localhost:8081/course/courseRequests')
        .then(res => {
            let requestData = res.data.data1
            console.log(res)
            this.applyCourses = []
            for(let i = 0; i < requestData.length; i++) {
                this.applyCourses.push(requestData[i].courseVO)
                setCourseTime( this.applyCourses[i],this.applyCourses[i].times)
                this.applyCourses[i].type = requestData[i].type
                this.applyCourses[i].requestId = requestData[i].requestId
                this.applyCourses[i].examined = false
                this.applyCourses[i].passed = false
                this.applyCourses[i].showStatus = this.statusToShow[this.applyCourses[i].type]
            }
        })
        // this.applyCourses[0].showStatus = this.statusToShow[this.applyCourses[0].type]
        // console.log(this.applyCourses[0].type)
    }
}
</script>

<style scoped>
</style>