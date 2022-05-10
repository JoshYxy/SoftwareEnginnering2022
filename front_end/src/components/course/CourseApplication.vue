<template>
    <div v-if="!courseOpen"> <h2>选课时间未到</h2></div>
    <div v-if="courseOpen">
        <el-tabs class="course-application" v-model="activeName" type="border-card" @tab-click="handleClick">
            <el-tab-pane label="选课申请" name="available">
                <h2>选课申请</h2>
                <el-input class="search" placeholder="搜索课程" v-model="searchContent" @keyup.enter="submitSearch"> 
                    <template #suffix>
                        <el-icon @click="submitSearch" class="el-input__icon"><search /></el-icon>
                    </template>
                </el-input>
                <!-- <el-button @click="test">test </el-button> -->
                <el-table class="class-table" :data="courses" max-height="500px">
                    <el-table-column fixed prop="courseName" label="课程名" width="150" />
                    <el-table-column fixed prop="courseNum" label="课程编号" width="140" />
                    <el-table-column prop="collegeName" label="开课院系" width="180" />
                    <el-table-column prop="teacherName" label="课程教师姓名" width="120" />
                    <el-table-column prop="isGeneral" label="课程类型" width="220">
                        <template #default="scope">
                            <div v-if="scope.row.isGeneral == '通选课程'">通选课程</div>
                            <div v-else>
                                <span style="padding-right:10px">{{scope.row.isGeneral}}</span>
                                <el-button type="text" @click="majorAvlVis[scope.$index] = true">查看专业</el-button>
                                <el-dialog v-model="majorAvlVis[scope.$index]" title="课程可选专业" :append-to-body="true">
                                    <div v-for="major in scope.row.majors" :key="major">
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
                    <el-table-column prop="capacity,selected" label="选课人数" width="150">
                        <template #default="scope">
                            {{scope.row.selected}}/{{scope.row.capacity}}
                        </template>
                    </el-table-column>
                    <el-table-column prop="courseInfo" label="介绍" width="150" >
                        <template #default="scope">
                            <el-button type="text" @click="dialogAvlVis[scope.$index] = true">查看详情</el-button>
                            <el-dialog v-model="dialogAvlVis[scope.$index]" title="课程介绍" :append-to-body="true">
                            {{scope.row.courseInfo}}
                            </el-dialog>
                        </template>
                    </el-table-column>
                    <el-table-column fixed="right" label="操作" width="180">
                        <template #default="scope">
                            <el-button @click="handleApply(scope.$index)">填写申请</el-button>
                        </template>
                    </el-table-column>
                </el-table>
                <el-dialog 
                    width="50%"
                    v-model="applyTableVisible" 
                    title="选课申请" 
                    :append-to-body="true" 
                    >
                    <el-form 
                        class="course-apply"
                        :model="applyCourse" 
                        :rules="rules" 
                        hide-required-asterisk
                        ref="applyCourse"
                        >
                        <el-form-item prop="courseName" label="课程名称">
                            <span>{{applyCourse.courseName}}</span>  
                        </el-form-item>
                        <el-form-item label="课程编号" prop="courseNum">
                            <span>{{applyCourse.courseNum}}</span> 
                        </el-form-item>
                        <el-form-item label="课程学分" prop="credits">
                            <span>{{applyCourse.credits}}</span> 
                        </el-form-item>                     
                        <el-form-item label="任课教师" prop="teacher">
                            <span>{{applyCourse.teacherName}}</span> 
                        </el-form-item>
                        <el-form-item label="上课教室" prop="selectRoom">
                            <span>{{applyCourse.building}}{{applyCourse.roomNum}}</span>       
                        </el-form-item>
                        <el-form-item label="上课时间" prop="selectRoom">
                            <span>{{applyCourse.courseTime}}</span>           
                        </el-form-item>
                        <el-form-item label="申请理由" prop="applyReason" >
                            <el-input type="textarea" v-model="applyCourse.applyReason" />
                        </el-form-item>
                    </el-form>
                    
                    <span class="dialog-footer">
                        <el-button @click="cancelApply">取消</el-button>
                        <el-button type="primary" @click="submitApply">确认</el-button>
                    </span>
                </el-dialog>
            </el-tab-pane>
            <el-tab-pane label="我的申请" name="application">
            </el-tab-pane>
        </el-tabs>

    </div>
</template>

<script>
import {setCourseTime} from '../jsComponents/CourseSet'
import { ElMessage} from 'element-plus'
import axios from 'axios'
import global_ from '../jsComponents/global'
import { Search } from '@element-plus/icons-vue'
export default {
    components: {
        Search
    },
    data() {
        return {
            rules: {applyReason: [{required: true, message: '请输入申请理由', trigger: 'blur'}]},
            dialogAvlVis:[false,false,false,false,false,false,false,false,],
            majorAvlVis:[false,false,false,false,false,false,false,false,false,false,false,false,],
            applyTableVisible:false,
            semesters: global_.semesters,
            years: global_.years,
            activeName: 'available',
            searchContent: '',
            courseOpen: true,
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
                    selected: '80',
                    capacity: '100',
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
                    courseInfo: '321',
                    selected: '100',
                    capacity: '100',
                    type: 'normal',
                }
            ],
            applyCourse: {applyReason: '', courseId: ''},
            // applyReason: '',
        }
    },
    computed: {

    },
    methods: {
         submitSearch() {
            console.log(this.searchContent)
            axios.post("http://localhost:8081/student/requestCourses/search",{search: this.searchContent})
            .then(res => {
                console.log(res.data.msg)
                this.courses = res.data.data1
                for(let course of this.courses) {
                    course.courseTime = ''
                    setCourseTime(course, course.times)
                }
            })
        },
        handleClick(tab, event) {
            console.log(tab,event)
            if(tab.props.name == 'available'){
                //axios获取可选课程
                console.log(1)
            }
            if(tab.props.name == 'selected') {
                //axios获取已选课程
                console.log(2)
            }
        },
        handleApply(index) {
            // this.selectedCourses.push(this.courses[index])
            this.applyTableVisible = true
            this.applyCourse = JSON.parse(JSON.stringify(this.courses[index]))
            this.applyCourse.applyReason = ''
            console.log(this.applyCourse)
        },
        cancelApply() {
            this.applyTableVisible = false
        },
        submitApply() {
            this.$refs['applyCourse'].validate(valid => {
                if(valid) {
                    axios.post("http://localhost:8081/student/course/request", {courseId: this.applyCourse.courseId, reason: this.applyCourse.applyReason})
                    .then(res => {
                        ElMessage({
                            type: 'success',
                            message: res.data.msg,
                        })
                        this.applyTableVisible = false
                    })
                    
                }
            })
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
        await axios.get('http://localhost:8081/student/requestCourses')
        .then(res => {
            this.courses = res.data.data1
        })
        for(let course of this.courses) {
            course.courseTime = ''
            setCourseTime(course, course.times)
        }
    }
}
</script>

<style scoped>
.course-application .search {
    display: block;
    float: none;
    margin: auto;
    width: 300px;
    margin-bottom: 50px;
}
.search .el-input__suffix {
    cursor: pointer;
}
</style>