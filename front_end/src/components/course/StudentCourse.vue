<template>
    <div v-if="courseStatus == CLOSE"> <h2>选课时间未到</h2></div>
    <div v-if="courseStatus == ONE_OFF"> <h2>第一轮选课已结束，请等待第二轮选课开始</h2></div>
    <div v-if="courseStatus == TWO_OFF"> <h2>第二轮选课已结束</h2></div>
    <div v-if="courseStatus == ONE_ON || courseStatus == TWO_ON">
        <el-tabs v-model="activeName" type="border-card" @tab-click="handleClick">
            <el-tab-pane label="可选课程" name="available">
                <h2>可选课程</h2>
                <el-input class="search" size="large" placeholder="搜索课程" v-model="searchContent" @keyup.enter="submitSearch">
                    <template #suffix>
                        <el-icon @click="submitSearch" class="el-input__icon"><search /></el-icon>
                    </template>
                </el-input>
                <el-button @click="resetSearch" type="primary" style="margin-left: 210px">查看全部可选课程</el-button>
                <el-button @click="clearFilter" type="danger">重置筛选</el-button>
                <!-- <el-button @click="test">test </el-button> -->
                <el-table class="class-table" ref="courses" :data="courses" max-height="400px">
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
                    <el-table-column prop="building,roomNum" label="上课地点" width="100" 
                        column-key="room"
                        :filter-multiple="true"
                        :filters="roomFilters"
                        :filter-method="filterRoom">
                        <template #default="scope">
                            {{scope.row.building}}{{scope.row.roomNum}}
                        </template>
                    </el-table-column>
                    <el-table-column prop="courseTime" label="上课时间" width="180"
                                            :filter-multiple="true"
                        :filters="timeFilters"
                        :filter-method="filterTime"
                        />
                    <el-table-column prop="courseInfo" label="介绍" width="150" >
                        <template #default="scope">
                            <el-button type="text" @click="dialogAvlVis[scope.$index] = true">查看详情</el-button>
                            <el-dialog v-model="dialogAvlVis[scope.$index]" title="课程介绍" :append-to-body="true">
                            {{scope.row.courseInfo}}
                            </el-dialog>
                        </template>
                    </el-table-column>
                    <el-table-column fixed="right" prop="capacity,selected" label="选课人数" width="150">
                        <template #default="scope">
                            {{scope.row.selected}}/{{scope.row.capacity}}
                        </template>
                    </el-table-column>
                    <el-table-column fixed="right" label="操作" width="180">
                        <template #default="scope">
                            <el-button @click="selectCourse(scope.row.index, scope.row.courseId)" type="primary" round>选课</el-button>
                        </template>
                    </el-table-column>
                </el-table>  
            </el-tab-pane>
            <el-tab-pane label="已选课程" name="selected">
                <h2>已选课程</h2>
                <el-table class="class-table" :data="selectedCourses"  max-height="500px">
                    <el-table-column fixed prop="courseName" label="课程名" width="150" />
                    <el-table-column fixed prop="courseNum" label="课程编号" width="140" />
                    <el-table-column prop="collegeName" label="开课院系" width="180" />
                    <el-table-column prop="teacherName" label="课程教师姓名" width="120" />
                    <el-table-column prop="isGeneral" label="课程类型" width="220">
                        <template #default="scope">
                            <div v-if="scope.row.isGeneral == '通选课程'">通选课程</div>
                            <div v-else>
                                <span style="padding-right:10px">{{scope.row.isGeneral}}</span>
                                <el-button type="text" @click="majorSelVis[scope.$index] = true">查看专业</el-button>
                                <el-dialog v-model="majorSelVis[scope.$index]" title="课程可选专业" :append-to-body="true">
                                    <div v-for="major in scope.row.majors" :key="major">
                                        {{major[1]}} ({{major[0]}})
                                    </div>
                                </el-dialog>
                            </div>
                        </template>
                    </el-table-column>
                    <el-table-column prop="classHours" label="学时" width="60" />
                    <el-table-column prop="credits" label="学分" width="60" />
                    <el-table-column prop="building,roomNum" label="上课地点" width="80">
                        <template #default="scope">
                            {{scope.row.building}}{{scope.row.roomNum}}
                        </template>
                    </el-table-column>
                    <el-table-column prop="courseTime" label="上课时间" width="180" />
                    <el-table-column prop="courseInfo" label="介绍" width="150" >
                        <template #default="scope">
                            <el-button type="text" @click="dialogSelVis[scope.$index] = true">查看详情</el-button>
                            <el-dialog v-model="dialogSelVis[scope.$index]" title="课程介绍" :append-to-body="true">
                            {{scope.row.courseInfo}}
                            </el-dialog>
                        </template>
                    </el-table-column>
                    <el-table-column fixed="right" prop="capacity,selected" label="选课人数" width="150">
                        <template #default="scope">
                            {{scope.row.selected}}/{{scope.row.capacity}}
                        </template>
                    </el-table-column>
                    <el-table-column fixed="right" label="操作" width="180">
                        <template #default="scope">
                            <el-button @click="quitCourse(scope.$index)" type="danger">退课</el-button>
                        </template>
                    </el-table-column>
                </el-table> 
            </el-tab-pane>
            <el-tab-pane label="已修课程" name="studied">
                    <h2>已修课程</h2>
                    <el-table class="class-table" :data="studiedCourses" max-height="500px">
                        <el-table-column fixed prop="courseName" label="课程名" width="150" />
                        <el-table-column fixed prop="courseNum" label="课程编号" width="140" />
                        <el-table-column prop="year,semester" label="修读学期" width="180"
                            :filter-multiple="true"
                            :filters="yearFilters"
                            :filter-method="filterYear">
                            <template #default="scope">
                                {{scope.row.year}}{{scope.row.semester}}
                            </template>
                        </el-table-column>
                        <el-table-column prop="collegeName" label="开课院系" width="180" />
                        <el-table-column prop="teacherName" label="课程教师姓名" width="120" />
                        <el-table-column prop="isGeneral" label="课程类型" width="220">
                            <template #default="scope">
                                <div v-if="scope.row.isGeneral == '通选课程'">通选课程</div>
                                <div v-else>
                                    <span style="padding-right:10px">{{scope.row.isGeneral}}</span>
                                    <el-button type="text" @click="majorStuVis[scope.$index] = true">查看专业</el-button>
                                    <el-dialog v-model="majorStuVis[scope.$index]" title="课程可选专业" :append-to-body="true">
                                        <div v-for="major in scope.row.majors" :key="major">
                                            {{major[1]}} ({{major[0]}})
                                        </div>
                                    </el-dialog>
                                </div>
                            </template>
                        </el-table-column>
                        <el-table-column prop="classHours" label="学时" width="60" />
                        <el-table-column prop="credits" label="学分" width="60" />
                        <el-table-column prop="building,roomNum" label="上课地点" width="100">
                            <template #default="scope">
                                {{scope.row.building}}{{scope.row.roomNum}}
                            </template>
                        </el-table-column>
                        <el-table-column prop="courseTime" label="上课时间" width="180" />
                        <el-table-column prop="courseInfo" label="介绍" width="150" >
                            <template #default="scope">
                                <el-button type="text" @click="dialogStuVis[scope.$index] = true">查看详情</el-button>
                                <el-dialog v-model="dialogStuVis[scope.$index]" title="课程介绍" :append-to-body="true">
                                {{scope.row.courseInfo}}
                                </el-dialog>
                            </template>
                        </el-table-column>
                    </el-table>  
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
            CLOSE: global_.CLOSE,
            ONE_OFF: global_.ONE_OFF,
            ONE_ON: global_.ONE_ON,
            TWO_OFF: global_.TWO_OFF,
            TWO_ON: global_.TWO_ON,
            dialogAvlVis:[false,false,false,false,false,false,false,false,],
            majorAvlVis:[false,false,false,false,false,false,false,false,false,false,false,false,],
            dialogSelVis:[false,false,false,false,false,false,false,false,],
            majorSelVis:[false,false,false,false,false,false,false,false,false,false,false,false,],
            dialogStuVis:[false,false,false,false,false,false,false,false,],
            majorStuVis:[false,false,false,false,false,false,false,false,false,false,false,false,],
            semesters: global_.semesters,
            years: global_.years,
            periods: global_.periods,
            activeName: 'available',
            searchContent: '',
            courseStatus: '1',
            times: [],
            classroom: [],
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
                    index: ''
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
                    selected: '100',
                    capacity: '100',
                    type: 'normal',
                    index: '',
                }
            ],
            selectedCourses: [
                {
                    courseId: 1,
                    courseName: '软件',
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
                    courseName: '数学',
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
                    selected: '100',
                    capacity: '100',
                    type: 'normal',
                }
            ],
            studiedCourses: [
                {
                    courseId: 1,
                    courseName: '软件',
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
                    isGeneral: '专业课程',
                    majors: [
                                ["计算机科学技术学院","大数据"],
                                ["计算机科学技术学院","信息安全"],
                                ['软件工程学院','软件工程']
                            ],
                    year:'2021-2022',
                    semester:'春'
                },
                {
                    courseId: 2,
                    courseName: '数学',
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
                    isGeneral: '通选课程',
                    majors: [],
                    year:'2020-2021',
                    semester:'秋'
                }
            ],
        }
    },
    computed: {
        yearFilters() {
            let obj = []
            for(let i = 0; i < this.years.length; i++) {
                obj.push({
                    text:this.years[i]+this.semesters[0],
                    value:this.years[i]+this.semesters[0],
                })
                obj.push({
                    text:this.years[i]+this.semesters[1],
                    value:this.years[i]+this.semesters[1],
                })
            }
            return obj
        }, 
        roomFilters(){
            let obj = [];
            //找到对应的数据 并添加到obj
            for(let i = 0; i < this.classroom.length; i++) {
                for(let j = 0; j < this.classroom[i].room.length; j++) {
                    obj.push( {
                        text: this.classroom[i].aka+this.classroom[i].room[j].name,
                        value: this.classroom[i].aka+this.classroom[i].room[j].name,
                    })
                }
            }
            // this.courses.filter(item => {
            //     obj.push({
            //         text:item['building']+item['roomNum'],
            //         value:item['building']+item['roomNum'],
            //     })
            // })
            // //因为column有重复数据，所以要进行去重
            // console.log(obj)
            return obj
        },
        timeFilters() {
            let obj = []
            for(let i = 0; i < this.periods.length; i++) {
                for(let j = 0; j < this.times.length; j++) {        
                    obj.push({
                        text:this.periods[i]+'第'+(parseInt(j)+1)+'节课',
                        value:this.periods[i]+'第'+(parseInt(j)+1)+'节课',
                    })
                }
            }
            return obj
        },
    },
    methods: {
         submitSearch() {
            console.log(this.searchContent)
            axios.post("http://localhost:8081/student/course/verified/search",{search: this.searchContent})
            .then(res => {
                console.log(res.data.msg)
                this.courses = res.data.data1
                for(let i = 0; i < this.courses.length; i++) {
                    this.courses[i].index = i
                    this.courses[i].courseTime = ''
                    setCourseTime(this.courses[i], this.courses[i].times)
                }
            })
            .catch(err => {
                alert(err.response.data.msg)
                this.courses = []
            })
        },
        resetSearch() {
            axios.get('http://localhost:8081/student/course/verified')
            .then(res => {
                this.courses = res.data.data1
                for(let i = 0; i < this.courses.length; i++) {
                    this.courses[i].index = i
                    this.courses[i].courseTime = ''
                    setCourseTime(this.courses[i], this.courses[i].times)
                }

            })
            .catch(err => {
                alert(err.response.data.msg)
                this.courses = []
            })
        },
        handleClick(tab, event) {
            console.log(tab,event)
            if(tab.props.name == 'available'){
                axios.get('http://localhost:8081/student/course/verified')
                .then(res => {
                    this.courses = res.data.data1
                    for(let course of this.courses) {
                        // this.dialogTableVisible.push(false)
                        course.courseTime = ''
                        setCourseTime(course, course.times)
                    }
                })
                .catch(err => {
                    alert(err.response.data.msg)
                    this.courses = []
                })
                console.log(1)
            }
            if(tab.props.name == 'selected') {
                //axios获取已选课程
                axios.get('http://localhost:8081/student/course/selected')
                .then(res => {
                    this.selectedCourses = res.data.data1
                    for(let course of this.selectedCourses) {
                        // this.dialogTableVisible.push(false)
                        course.courseTime = ''
                        setCourseTime(course, course.times)
                    }
                })
                .catch(err => {
                    alert(err.response.data.msg)
                    this.selectedCourses = []
                })
                console.log(2)
            }
            if(tab.props.name == 'studied') {
                axios.get('http://localhost:8081/student/course/studied')
                .then(res => {
                    this.studiedCourses = res.data.data1
                    for(let course of this.studiedCourses) {
                        // this.dialogTableVisible.push(false)
                        course.courseTime = ''
                        setCourseTime(course, course.times)
                    }
                })
                .catch(err => {
                    alert(err.response.data.msg)
                    this.studiedCourses = []
                })
                console.log(3)
            }
        },
        selectCourse(index, courseId) {
            // this.selectedCourses.push(this.courses[index])
            // console.log(this.courses[index])
            axios.post("http://localhost:8081/student/selection", {courseId: courseId})
            .then(res => {
                console.log(res)
                axios.get('http://localhost:8081/student/course/verified')
                .then(res => {
                    this.courses = res.data.data1
                    for(let i = 0; i < this.courses.length; i++) {
                        this.courses[i].index = i
                        this.courses[i].courseTime = ''
                        setCourseTime(this.courses[i], this.courses[i].times)
                    }
                })
                .catch(err => {
                    alert(err.response.data.msg)
                    this.courses = []
                })
                ElMessage({
                    type: 'success',
                    message: '选课成功！',
                })
            })
            .catch(err => {
                alert(err.response.data.msg)
            })
        },
        quitCourse(index) {
            // this.courses.push(this.selectedCourses[index])
            // this.selectedCourses.splice(index,1) 
            axios.delete("http://localhost:8081/student/selection",{data:{courseId: this.selectedCourses[index].courseId}})
            .then(res => {
                ElMessage({
                    type: 'success',
                    message: '退课成功',
                })
                this.selectedCourses.splice(index,1) 
                console.log(res)
            })
        },
        filterYear(value, row) {
            return row['year'] + row['semester'] === value
        },
        filterRoom(value, row) {
            return row['building'] + row['roomNum'] === value
        },
        filterTime(value, row) {
            for(let j = 0; j < row.times.length; j++) {
                for(let k = 0; k < row.times[j].length; k++) {
                    // let num = parseInt(data.times[j][k])+j*this.times.length
                    // console.log(num)
                    if(this.periods[j]+'第'+row.times[j][k]+'节课'==value) return true
                }
            }
            return false
        },
        clearFilter() {
            this.$refs['courses'].clearFilter()
        },
    },
    async created() {
        await axios.get('http://localhost:8081/user/curriculaVariable')
        .then(res => {
            this.courseStatus = res.data.data1
            console.log(res)
        })
        .catch(error => {
            console.dir(error)
        })
        if(this.courseStatus == '二轮选课开始' || this.courseStatus == '一轮选课开始') {
            await axios.get('http://localhost:8081/student/course/verified')
            .then(res => {
                this.courses = res.data.data1
                for(let i = 0; i < this.courses.length; i++) {
                    this.courses[i].index = i
                    this.courses[i].courseTime = ''
                    setCourseTime(this.courses[i], this.courses[i].times)
                }
            })
            .catch(err => {
                alert(err.response.data.msg)
                this.courses = []
            })
            await axios.get('http://localhost:8081/user/course/new')
            .then(res => {
                this.classroom = res.data.data2
                for(let i = 0; i < this.classroom.length; i++) {
                    this.classroom[i] = JSON.parse(JSON.stringify(this.classroom[i]).replace(/fullName/g,"name"))
                    this.classroom[i] = JSON.parse(JSON.stringify(this.classroom[i]).replace(/roomNum/g,"name"))
                    this.classroom[i] = JSON.parse(JSON.stringify(this.classroom[i]).replace(/abbrName/g,"aka"))
                }
                this.times = res.data.data3
                // this.collegeData = res.data.data4
                // for(let i = 0; i < this.collegeData.length; i++) {
                //     if(this.collegeData[i].majors == []) {
                //         this.collegeData[i]['disabled'] = true
                //     }
                // }
            })
        }
    }
}
</script>

<style scoped>
.search {
    display: block;
    float: right;
    width: 200px;
    margin-bottom: 50px;
}
.search .el-input__suffix {
    cursor: pointer;
}
</style>