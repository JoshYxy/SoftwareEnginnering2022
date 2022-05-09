<template>
    <h2 style="margin-right:250px">所有课程</h2>
    <el-input class="search" size="large" placeholder="搜索课程" v-model="searchContent" @keyup.enter="submitSearch">
        <template #suffix>
            <el-icon @click="submitSearch" class="el-input__icon"><search /></el-icon>
        </template>
    </el-input>
    <el-button @click="resetSearch" type="primary">查看全部课程</el-button>
    <!-- <el-button @click="resetRoomFilter">重置教室筛选</el-button> -->
    <el-button @click="clearFilter" type="danger">重置筛选</el-button>
    <!-- <el-button @click="test">test </el-button> -->
    <el-table class="class-table" :data="courses" ref="coursesData" :row-class-name="tableRowClassName" max-height="500px" @filter-change="filterChang">
        <el-table-column fixed prop="courseName" label="课程名" width="150" />
        <el-table-column fixed prop="courseNum" label="课程编号" width="140" />
        <el-table-column fixed v-if="courseStatus != CLOSE" prop="selected" label="选课人数" width="150" >
            <template #default="scope">
                <div class="namelist">
                    <span>{{scope.row.selected}}</span>
                    <el-button class="namelist-button" type="text" @click="openList(scope.$index)" style="text-align:right">查看名单</el-button>
                </div>
            </template>
        </el-table-column>
        <el-table-column prop="year,semester" label="开课学期" width="180"
            :filter-multiple="true"
            :filters="yearFilters"
            :filter-method="filterYear">
            <template #default="scope">
                {{scope.row.year}}{{scope.row.semester}}
            </template>
        </el-table-column>
        <el-table-column prop="collegeName" label="开课院系" width="180" />
        <el-table-column prop="teacherName" label="课程教师姓名" width="120" />
        <el-table-column prop="teacherNum" label="课程教师工号" width="120" />
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
        <el-table-column prop="building,roomNum" label="上课地点" width="100" 
            column-key="room"
            :filter-multiple="true"
            :filters="roomFilters"
            :filter-method="filterRoom">
            <template #default="scope">
                {{scope.row.building}}{{scope.row.roomNum}}
            </template>
        </el-table-column>
        <el-table-column prop="courseTime" label="上课时间" width="180">
            <template #header>
                <el-popover class="time-pop" placement="bottom-start" v-model:visible="popVisible">
                    <template #reference>
                        <span class="course-time">上课时间<el-icon @click="popVisible = !popVisible"><arrow-down /></el-icon></span>
                    </template>
                    <el-scrollbar max-height="200px">
                        <el-tree
                            ref="time-tree"
                            style="margin:auto;"
                            :data="timeFilters"
                            show-checkbox
                            accordion
                            highlight-current
                            node-key="id"
                        />
                    </el-scrollbar>
                    <div class="el-table-filter__bottom">
                        <el-button size="small" @click="filterTime">确认</el-button>
                        <el-button size="small" @click="resetTime">重置</el-button>
                    </div>
                </el-popover>
            </template>
        </el-table-column>
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
            <el-button v-if="scope.row.type != 'deleted' && courseStatus == CLOSE" size="small" type="primary" @click="handleEdit(scope.$index, scope.row)">修改</el-button>
            <el-button v-if="courseStatus != CLOSE" @click="editCapacity(scope.$index)" type="primary">修改容量</el-button>
            <el-button v-if="scope.row.type != 'deleted' && courseStatus == CLOSE" size="small" type="danger" @click="handleDelete(scope.$index, scope.row)" >删除</el-button>
            <el-dialog 
                width="70%"
                v-model="editTableVisible[scope.$index]" 
                title="修改课程" 
                :append-to-body="true" 
                >
                <el-form 
                    class="course-teacher"
                    :model="editCourse" 
                    :rules="rules" 
                    hide-required-asterisk
                    ref="editCourse"
                    >
                    <el-form-item prop="courseName" label="课程名称">
                        <el-input type="text" name="courseName" v-model="editCourse.courseName"/>
                    </el-form-item>
                    <el-form-item label="课程编号" prop="courseNum">
                        <el-input v-model="editCourse.courseNum" />
                    </el-form-item>
                    <el-form-item label="课程学分" prop="credits">
                        <el-input v-model="editCourse.credits" />
                    </el-form-item>
                    <el-form-item label="选课容量" prop="capacity">
                        <el-input v-model="editCourse.capacity" />
                    </el-form-item>
                    <el-form-item label="课程简介" prop="courseInfo">
                        <el-input v-model="editCourse.courseInfo" type="textarea" />
                    </el-form-item>     
                    <el-form-item label="课程类型" prop="commonCourse">
                        <el-select v-model="editCourse.commonCourse" placeholder="类型">
                            <el-option label="通选课程" value="通选课成" />
                            <el-option label="专业选修" value="专业课程" />
                        </el-select>
                    </el-form-item> 
                    <el-form-item label="面向专业" prop="majors" v-if="editCourse.commonCourse == '专业课程'">
                        <el-cascader :props="majorProps" :options="collegeData" v-model="editCourse.majors" placeholder="面向专业" :show-all-levels='false' clearable/>
                    </el-form-item>
                    <el-form-item label="开课学年" prop="year" style="float:left; margin-right:20px">
                        <el-select v-model="editCourse.year" placeholder="学年">
                            <el-option :key="year" :value="year" :label="year" v-for="year in years" />
                        </el-select>
                    </el-form-item>
                    <el-form-item label="开课学期" prop="year">
                        <el-select v-model="editCourse.semester" placeholder="学期">
                            <el-option :key="semester" :value="semester" :label="semester" v-for="semester in semesters" />
                        </el-select>
                    </el-form-item>                   
                    <el-form-item label="开课院系" prop="college">
                        <el-select v-model="editCourse.college" value-key="collegeName" placeholder="学院" @change="updateCollege">
                            <el-option :key="college.collegeName" :value="college" :label="college.collegeName" v-for="college in teacherData" />
                        </el-select>
                    </el-form-item>
                    <el-form-item label="任课教师" prop="teacher">
                        <el-select v-model="editCourse.teacher" value-key="number" placeholder="教师" @change="updateTeacher(scope.row)">
                            <el-option :key="teacher.number" :value="teacher" :label="teacher.name+teacher.number" v-for="teacher in editCourse.college.teachers" />
                        </el-select>
                    </el-form-item>
                    <el-form-item label="上课教室" prop="selectRoom">
                        <el-cascader :props="roomProps" :options="classroom" v-model="editCourse.selectRoom" placeholder="可输入教室号搜索" @change="updateRoom(scope.row)" filterable clearable/>     
                        <span style="padding-left:20px">教室容量: {{roomCap}} </span>
                        <span style="padding-left:20px">*切换教室后需重新选择课程时间</span>           
                    </el-form-item>
                    <el-form-item label="上课时间" prop="selectTime">
                        <div class="time-container">
                            <div class="left-part">
                                <div class="line" v-for="day in times" :key="day.id">{{day.startTime}}-{{day.endTime}}</div>
                            </div>
                            <div class="right-part">
                                <span class="class-week" v-for="period in periods" :key="period">{{period}}</span> 
                                    <div class="right-down">
                                    <el-checkbox-group v-for="day in timeData" :key="day.id" v-model="editCourse.selectTime[day.id]">
                                        
                                        <el-checkbox-button 
                                            v-for="time in day.times" 
                                            :key="time.name" 
                                            :label="time.num" 
                                            :disabled="time.disable" 
                                            style="display:block;"
                                            >
                                            {{time.name}}
                                        </el-checkbox-button>
                                        <!-- <span class="day"> {{day.name}} </span> -->
                                    </el-checkbox-group>
                                </div>
                            </div>
                        </div>
                    </el-form-item>
                </el-form>
                
                <span class="dialog-footer">
                    <el-button @click="cancelEdit(scope.$index)">取消</el-button>
                    <el-button type="primary" @click="submitEdit(scope.$index)">确认</el-button>
                </span>
            </el-dialog>
        </template>
        </el-table-column>
    </el-table>  
    <el-dialog v-model="listTableVisible" title="选课名单" :append-to-body="true">
        <el-table :data="namelist" stripe max-height="450px">
            <el-table-column prop="name" label="姓名" />
            <el-table-column prop="number" label="学号" />
            <el-table-column prop="major" label="专业" />
        </el-table>
    </el-dialog>
</template>

<script>
// import CourseTime from './CourseTime.vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import {validTimetable, validSelectRoom, validTeacher, validCollege} from '../jsComponents/CheckRules'
import {setCourseTime} from '../jsComponents/CourseSet'
import global_ from '../jsComponents/global'
import axios from 'axios'
import { Search,ArrowDown } from '@element-plus/icons-vue'
export default {
    components: {
        Search,
        ArrowDown
    },
    data() {
        var validCapacity = (rule, value, callback) => {
            if(value!==''){
                var cap = parseInt(value)
                console.log(1)
                if(cap <= parseInt(this.roomCap)){
                    callback()
                }
                else {
                    return callback(new Error('选课容量需小于教室容量'))
                }
            }
        };
        var validRoom = (rule, value, callback) => {
            if(this.editCourse.capacity!==''){
                this.$refs['editCourse'].validateField('capacity', () => null)
            } 
            callback()
        };
        return {
            rules: {
                courseName: [{required: true, message: '请输入课程名称', trigger: ['blur','change']}],
                courseInfo: [{required: true, message: '请输入课程介绍', trigger: ['blur','change']}],
                courseNum: [{required: true, message: '请输入课程编号', trigger: ['blur','change']}],
                credits: [{required: true, message: '请输入学分', trigger: ['blur','change']},
                          {pattern:/^[1-9]\d*$/, message: '请输入正整数', trigger: 'blur'}],
                capacity: [{required: true, message: '请输入选课容量', trigger: 'blur'},
                           {pattern:/^[1-9]\d*$/, message: '请输入正整数', trigger: 'blur'},
                           {validator: validCapacity, trigger: 'blur'}],
                commonCourse: [{required: true, message: '请选择课程类型',trigger: ['blur','change']}],
                majors: [{required: true, message: '请选择面向专业',trigger: ['blur','change']}],
                college: [{validator: validCollege, trigger: ['blur','change']}], 
                teacher: [{validator: validTeacher, trigger: ['blur','change']}], 
                selectRoom: [{validator: validSelectRoom, trigger: ['blur','change']},
                             {validator: validRoom, trigger: ['blur','change']}],
                selectTime: [{validator: validTimetable, trigger: ['blur','change']}],
                year: [{required: true, message: '请选择开课学年',trigger: ['blur','change']}],
                semester: [{required: true, message: '请选择开课学期',trigger: ['blur','change']}],
            },
            majorProps: {
                children: 'majors',
                label: 'name',
                value: 'name',
                multiple: true
            },
            roomProps: {
                children: 'room',
                label: 'name',
                value: 'name'
            },
            
            periods: global_.periods,
            abbrToBuilding: global_.abbrToBuilding,
            buildingToAbbr: global_.buildingToAbbr,
            semesters: global_.semesters,
            years: global_.years,
            CLOSE: global_.CLOSE,
            ONE_OFF: global_.ONE_OFF,
            ONE_ON: global_.ONE_ON,
            TWO_OFF: global_.TWO_OFF,
            TWO_ON: global_.TWO_ON,
            editTableVisible:[false,false,false,false,false,false,false,false,false,false,false,false,],//传入时数量与课程数需一直
            dialogTableVisible:[false,false,false,false,false,false,false,false,false,false,false,false,],
            majorTableVisible:[false,false,false,false,false,false,false,false,false,false,false,false,],
            listTableVisible: false,
            selectTime:[[]],
            searchContent: '',
            popVisible: false,
            courseStatus: '1',
            namelist: [],
            collegeData: [
                { 
                    id: 1,
                    name: '计算机科学技术学院', 
                    majors: [
                        {id: 1, name: '大数据'},
                        {id: 2, name: '信息安全'}
                    ]
                },
                { 
                    id: 1,
                    name: '生命科学学院', 
                    disabled: true,
                    majors: [
                        // {id: 1, name: '生物'},
                        // {id: 2, name: '123'}
                    ]
                },
                { 
                    id: 1,
                    name: '软件工程学院', 
                    majors: [
                        {id: 1, name: '软件工程'},
                    ]
                },
            ],
            classroom: [
                {
                    name: '第三教学楼',
                    aka: 'H3',
                    room: [
                        {name:'301', capacity:120},
                        {name:'402', capacity:120}
                    ]
                },
                {
                    name: '光华楼西辅楼',
                    aka: 'HGX',
                    room: [
                        {name:'201', capacity:100},
                        {name:'502', capacity:110}
                    ]
                },
            ],
            times: [
                {
                    name: '第一节',
                    startTime:'08:00',
                    endTime:'08:40'
                },
                {
                    name: '第二节',
                    startTime:'09:00',
                    endTime:'09:40'
                },
                {
                    name: '第三节',
                    startTime:'09:50',
                    endTime:'10:30'
                },
                {
                    name: '第四节',
                    startTime:'10:40',
                    endTime:'11:20'
                },
                                {
                    name: '第四节',
                    startTime:'10:40',
                    endTime:'11:20'
                },   
                                {
                    name: '第四节',
                    startTime:'10:40',
                    endTime:'11:20'
                },   
                                {
                    name: '第四节',
                    startTime:'10:40',
                    endTime:'11:20'
                },   
                                {
                    name: '第四节',
                    startTime:'10:40',
                    endTime:'11:20'
                },   
                                {
                    name: '第四节',
                    startTime:'10:40',
                    endTime:'11:20'
                },   
                                {
                    name: '第四节',
                    startTime:'10:40',
                    endTime:'11:20'
                },   
                                {
                    name: '第四节',
                    startTime:'10:40',
                    endTime:'11:20'
                },   
                                {
                    name: '第四节',
                    startTime:'10:40',
                    endTime:'11:20'
                },   
                                {
                    name: '第四节',
                    startTime:'10:40',
                    endTime:'11:20'
                },                   
            ],
            timeData:[//响应式更新必备，不可删除
            ],
            unavalTeaTimes: [
                [],
                [],
                [],
                [],
                [],
                [],
                []
            ],
            unavalRoomTimes: [
                [],
                [],
                [],
                [],
                [],
                [],
                []
            ],

            teacherData: [
                {
                    collegeName:'计算机科学技术学院',
                    teachers: [
                        {name:'小王',number: 22200000}
                    ]
                },
                {
                    collegeName: '软件工程学院',
                    teachers: [
                        {name:'小一',number: 22111100},
                        {name:'小零',number: 20000000},
                    ]
                }
            ],
            courses: [
                {
                    courseId: 1,
                    courseName: '软件工程',
                    courseNum: 'SOFT220011',
                    teacherNum: 22200000,
                    teacherName: '小王',
                    classHours: '4',
                    credits: '4',
                    courseTime:'',
                    capacity: '100',
                    collegeId: '',
                    collegeName: '计算机科学技术学院',
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
                    commonCourse: '专业课程',
                    majors: [
                                ["计算机科学技术学院","大数据"],
                                ["计算机科学技术学院","信息安全"],
                                ['软件工程学院','软件工程']
                            ],
                    year:'2021-2022',
                    semester:'春',
                    selected: '100',
                },
                {
                    courseId: 2,
                    courseName: '离散数学',
                    courseNum: 'SOFT220022',
                    classHours: '3',
                    credits: '5',
                    courseTime:'',
                    teacherNum: 22111100,
                    teacherName: '小一',
                    collegeId: '',
                    collegeName: '软件工程学院',
                    times: [
                        [],
                        [3,4],
                        [],
                        [3,8],
                        []
                    ],
                    building: 'HGX',
                    roomNum: '502',
                    courseInfo: '123',
                    type: 'normal',
                    commonCourse: '通选课程',
                    majors: [],
                    year:'2020-2021',
                    semester:'秋',
                    selected: '80',
                }
            ],
            courses_back: [],
            editCourse: {
                selectTime:[[],[],[],[],[],[],[]],
                selectRoom: [],
                courseName: '',
                courseNum: '',
                courseId: null,
                collegeName: '',
                college: {collegeName: '',teachers:[]},
                period: 0,
                credits: 0,//学分
                teacher: {name:'',number:''},
                courseInfo: '',
                position: '',
                capacity: 0,
                classHours: '',
                building: '',
                roomNum: '',
                majors:[],
                commonCourse:'',
                semester: '',
                year: ''
            },
        }
    },
    computed: {    
        // 筛选项
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
        timeFilters() {
            let obj = []
            for(let i = 0; i < this.periods.length; i++) {
                const day = {id: i,label: this.periods[i], children:[]}
                for(let j = 0; j < this.times.length; j++) {
                    day.children.push({id: i*100+j+1, label: '第'+(parseInt(j)+1)+'节课'})
                }
                obj.push(day)
            }
            return obj
        },
        roomCap() {
            if(this.editCourse.selectRoom == null) return 0
            for(let i = 0; i < this.classroom.length; i++){
                for(let j = 0; j < this.classroom[i].room.length; j++) {
                    if(this.editCourse.selectRoom[1] == this.classroom[i].room[j].name 
                    && this.editCourse.selectRoom[0] == this.classroom[i].aka) {
                        return this.classroom[i].room[j].capacity
                    }
                }
            }
            return 0
        },
        roomCapa() {
            return(index => {
                for(let i = 0; i < this.classroom.length; i++){
                    for(let j = 0; j < this.classroom[i].room.length; j++) {
                        if(this.courses[index].roomNum == this.classroom[i].room[j].name 
                        && this.courses[index].building == this.classroom[i].aka) {
                            return this.classroom[i].room[j].capacity
                        }
                    }
                }
                return 0
            })
        },
    },
    methods: {
        filterChang() {
            console.log(this.courses)
        },
        submitSearch() {
            console.log(this.searchContent)
            //axios
   
        },
        resetSearch() {
            //axios
    
        },
        deWeight(arr) {
            for (var i = 0; i < arr.length - 1; i++) {
                for (var j = i + 1; j < arr.length; j++) {
                    if (arr[i].text == arr[j].text) {
                        arr.splice(j, 1);
                        j--;
                    }
                }
            }
            return arr;
        },
        filterTime() {
            let res = this.$refs['time-tree'].getCheckedKeys(true)
            let flag = []
            this.courses_back = JSON.parse(JSON.stringify(this.courses))
            for(let i = 0; i < this.courses_back.length; i++) {
                flag.push(false)
                let data = this.courses_back[i]
                for(let j = 0; j < data.times.length; j++) {
                    if(flag[i]) break
                    for(let k = 0; k < data.times[j].length; k++) {
                        let num = parseInt(data.times[j][k])+j*100
                        // console.log(num)
                        if(res.indexOf(num) > -1) flag[i] = true
                    }
                }
            }
            let del_num = 0
            for(let i = 0; i < this.courses_back.length; i++) {
                if(!flag[i]) {
                    this.courses.splice(i-del_num,1)
                    del_num++
                }
            }
            // this.courses.splice(0,1)
        },
        resetTime() {
            this.courses = this.courses_back
        },
        filterRoom(value, row) {
            return row['building'] + row['roomNum'] === value
        },
        filterYear(value, row) {
            return row['year'] + row['semester'] === value
        },
        resetRoomFilter() {
            this.$refs['coursesData'].clearFilter(['room'])
        },
        clearFilter() {
            this.$refs['coursesData'].clearFilter()
        },

        editCapacity(index) {
            console.log(index)
            var cap = this.roomCapa(index)
            var sel = this.courses[index].selected
            ElMessageBox.prompt('教室容量：'+cap+', 选课人数：'+this.courses[index].selected, '修改课程容量', {
                confirmButtonText: '确认',
                cancelButtonText: '取消',
                inputPlaceholder: '课程容量',
                inputPattern: /^[1-9]\d*$/,
                inputValidator: function(value) {if(parseInt(value)>parseInt(cap) || parseInt(value)<parseInt(sel)) return '课程容量不能大于教室容量，小于选课人数'},
                inputErrorMessage: '请输入正整数',
            })
                .then(({value}) => {
                    //axios
                    this.courses[index].capacity = value
                    ElMessage({
                        type: 'success',
                        message: '修改容量成功',
                    })
                })
                .catch(() => {
                    ElMessage({
                        type: 'info',
                        message: '修改取消',
                    })
                })
        },
        openList(index) {
            //axios courses[index].courseId
            console.log(index)
            this.listTableVisible = true
            this.namelist = [
                {name:'与',number:'220000',major:'软工'},
                {name:'我',number:'220001',major:'计算机'},
                {name:'我',number:'220001',major:'计算机'},
                {name:'我',number:'220001',major:'计算机'},
                {name:'我',number:'220001',major:'计算机'},
                {name:'我',number:'220001',major:'计算机'},
                {name:'我',number:'220001',major:'计算机'},
                {name:'我',number:'220001',major:'计算机'},
                {name:'我',number:'220001',major:'计算机'},
                {name:'我',number:'220001',major:'计算机'},
                {name:'我',number:'220001',major:'计算机'},
                {name:'我',number:'220001',major:'计算机'},
            ]
        },
        // eslint-disable-next-line
        tableRowClassName(row, rowIndex) {//根据该行课程的状态动态显示该行表格颜色
            if(row.row.type == 'changed')
                return 'changing-row';
            if(row.row.type == 'deleted')
                return 'deleting-row';
            if(row.row.type == 'new')
                return 'new-row';
            if(row.row.credits == '4')
                return 'selected'
        },
        handleDelete(index, data){
            ElMessageBox.confirm(
                '是否删除课程"'+data.courseName+'"',
                '请确认',
                {
                confirmButtonText: '确认',
                cancelButtonText: '取消',
                type: 'warning',
                }
            )
                .then(() => {
                    axios.delete('http://localhost:8081/course',{data:data})
                    .then(res => {
                        console.log(res)
                        data.type = 'deleted'
                        ElMessage({
                            type: 'success',
                            message: '已删除',
                        }) 
                    }).catch(error => {
                        console.dir(error)
                        alert('删除失败')
                    })

                })
                .catch(() => {
                    ElMessage({
                        type: 'info',
                        message: '删除取消',
                    })
                })
        },
        async handleEdit(index, data) {
            await axios.put('http://localhost:8081/affair/teacher/time',
                {
                    name: data.teacherName,
                    number: data.teacherNum
                })
            .then(res => {
                this.unavalTeaTimes = res.data.data1
            })
            .catch(err => {
                console.dir(err)
            })
            await axios.put('http://localhost:8081/affair/building/room/time',
                {
                    building: data.building,
                    roomNum: data.roomNum
                })
            .then(res => {
                this.unavalRoomTimes = res.data.data1
            })
            //axios获取教室，教师不可用时间 data.teacher data.selectRoom
            
            this.setAvalTime()
            //当前课程时间设置为可以选中
            for(let i = 0; i < data.times.length; i++) {
                for(let j = 1; j <= this.times.length; j++) {
                    if(data.times[i].indexOf(j) > -1)
                        this.timeData[i].times[j-1].disable = false
                }
            }
            //数组深拷贝
            this.editCourse.selectTime = []
            for(let i = 0; i < data.times.length; i++)
            {
                let [...arr] = data.times[i]
                this.editCourse.selectTime.push(arr)
            }
            this.editCourse.majors = []
            for(let i = 0; i < data.majors.length; i++)
            {
                let [...arr] = data.majors[i]
                this.editCourse.majors.push(arr)
            }
            this.editCourse.courseId = data.courseId
            this.editCourse.courseNum = data.courseNum
            this.editCourse.capacity = data.capacity
            this.editCourse.credits = data.credits
            this.editCourse.courseInfo = data.courseInfo
            this.editCourse.courseName = data.courseName
            this.editCourse.commonCourse = data.commonCourse
            this.editCourse.year = data.year
            this.editCourse.semester = data.semester
            // this.editCourse.collegeName = data.collegeName
            for(let i = 0; i < this.teacherData.length; i++) {
                if(data.collegeName == this.teacherData[i].collegeName) {
                    this.editCourse.college = JSON.parse(JSON.stringify(this.teacherData[i]))
                    break
                }
            }
            this.editCourse.teacher = {name: data.teacherName, number: data.teacherNum}
            this.editCourse.selectRoom = []
            this.editCourse.selectRoom[0] = this.abbrToBuilding[data.building] 
            this.editCourse.selectRoom[1] = data.roomNum
            this.editTableVisible[index] = true;
        },
        submitEdit(index) {
            
            this.$refs['editCourse'].validate(valid => {
                if(valid){

                    this.courses[index].courseName = this.editCourse.courseName
                    this.courses[index].times = this.editCourse.selectTime
                    this.courses[index].courseNum = this.editCourse.courseNum
                    this.courses[index].capacity = this.editCourse.capacity
                    this.courses[index].credits = this.editCourse.credits
                    this.courses[index].courseInfo = this.editCourse.courseInfo
                    this.courses[index].building = this.buildingToAbbr[this.editCourse.selectRoom[0]]
                    this.courses[index].roomNum = this.editCourse.selectRoom[1]
                    this.courses[index].teacherName = this.editCourse.teacher.name
                    this.courses[index].teacherNum = this.editCourse.teacher.number
                    this.courses[index].collegeName = this.editCourse.college.collegeName
                    this.courses[index].commonCourse = this.editCourse.commonCourse
                    this.courses[index].majors = this.editTableVisible.majors
                    this.courses[index].semester = this.editTableVisible.semester
                    this.courses[index].year = this.editTableVisible.year
                    setCourseTime(this.courses[index], this.editCourse.selectTime)
                    this.courses[index].type = 'changed'
                    axios.post('http://localhost:8081/course', this.courses[index])
                    .then(res => {
                        ElMessage({
                            type: 'success',
                            message: '修改成功',
                        })
                        console.log(res)
                    }).catch(error => {
                        alert('修改失败')
                        console.dir(error)
                    })  
                    this.clearAvalTime()
                    this.editTableVisible[index] = false;
                }
            })

        },
        cancelEdit(index) {
            this.clearAvalTime()
            this.editTableVisible[index] = false;
        },
        test() {
            // this.courses[0].type = 'changed'
            console.log(this.courses)
        },
        updateCollege() {
            this.editCourse.teacher = {}
        },
        async updateTeacher(data) {
            //axios获取老师不可用时间 传输editCourse.teacherName,Num
            await axios.put('http://localhost:8081/affair/teacher/time',
                {   
                    name: this.editCourse.teacher.name, 
                    number: this.editCourse.teacher.number
                })
            .then(res => {
                this.unavalTeaTimes = res.data.data1
            })
            // this.unavalTeaTimes = [[],[],[],[],[],[],[5,6,7,13],[]]
            this.clearAvalTime()
            this.setAvalTime()

            for(let i = 0; i < data.times.length; i++) {
                for(let j = 1; j <= this.times.length; j++) {
                    if(data.times[i].indexOf(j) > -1) {
                        this.timeData[i].times[j-1].disable = false
                        let p = this.editCourse.selectTime[i].indexOf(j)
                        if(p < 0) {
                            this.editCourse.selectTime[i].push(j)
                        }
                    }
                }
            }
            // console.log(this.editCourse.selectTime)
        },
        async updateRoom(data) {
            // axios获取教室不可用时间 传输editCourse.selectRoom
            if(this.editCourse.selectRoom == null ||this.editCourse.selectRoom.length < 2) return 
            await axios.put('http://localhost:8081/affair/building/room/time',
                {   
                    building: this.buildingToAbbr[this.editCourse.selectRoom[0]], 
                    roomNum: this.editCourse.selectRoom[1]
                })
            .then(res => {
                this.unavalRoomTimes = res.data.data1
            })
            // this.unavalRoomTimes = [[1,2],[],[],[],[],[],[],[]]
            this.clearAvalTime()
            this.setAvalTime()

            for(let i = 0; i < data.times.length; i++) {
                for(let j = 1; j <= this.times.length; j++) {
                    if(data.times[i].indexOf(j) > -1){
                        this.timeData[i].times[j-1].disable = false
                        let p = this.editCourse.selectTime[i].indexOf(j)
                        if(p < 0) {
                            this.editCourse.selectTime[i].push(j)
                        }
                    }
                }
            }
            //  console.log(this.editCourse.selectTime)
        },
        setAvalTime() {
            for(let i = 0; i < 7; i++) {
                for(let j = 1; j <= this.times.length; j++) {
                    if(this.unavalTeaTimes[i].indexOf(j) > -1  || this.unavalRoomTimes[i].indexOf(j) > -1){
                        this.timeData[i].times[j - 1].disable = true
                        let p = this.editCourse.selectTime[i].indexOf(j)
                        if(p > -1) {
                            this.editCourse.selectTime[i].splice(p,1)
                        }
                    }
                        
                }
            } 
        },
        clearAvalTime() {
            for(let i = 0; i < 7; i++) {
                for(let j = 0; j < this.times.length; j++) {
                    this.timeData[i].times[j].disable = false
                }
            }   
        },
        
    },
    // async created() {
            // axios.get('http://localhost:8081/user/curriculaVariable')
            // .then(res => {
            //     this.courseStatus = res.data.data1
            // })
            // .catch(error => {
            //     console.dir(error)
            // })
    //     await axios.get('http://localhost:8081/user/course/new')
    //     .then(res => {
    //         this.teacherData = res.data.data1
    //         this.classroom = res.data.data2
    //         for(let i = 0; i < this.classroom.length; i++) {
    //             this.classroom[i] = JSON.parse(JSON.stringify(this.classroom[i]).replace(/fullName/g,"name"))
    //             this.classroom[i] = JSON.parse(JSON.stringify(this.classroom[i]).replace(/roomNum/g,"name"))
    //             this.classroom[i] = JSON.parse(JSON.stringify(this.classroom[i]).replace(/abbrName/g,"aka"))
    //         }
    //         this.times = res.data.data3
    //         // this.collegeData = res.data.data4
    //         // for(let i = 0; i < this.collegeData.length; i++) {
    //         //     if(this.collegeData[i].majors == []) {
    //         //         this.collegeData[i]['disabled'] = true
    //         //     }
    //         // }
    //     })
    //     await axios.get('http://localhost:8081/course')
    //     .then(res => {
    //         for(let i = 0; i < res.data.data1.length; i++){
    //         this.editTableVisible.push(false)
    //         this.dialogTableVisible.push(false)
    //         }
    //         this.courses = res.data.data1
    //     })

    //     for(let course of this.courses) {
    //         course.courseTime = ''
    //         setCourseTime(course, course.times)

    //     }
    //     this.timeData = []
    //     for(let i = 0; i < 7; i++) {//创建选课时间数组
    //         this.timeData.push({id:0, name:'', times:[]})
    //         this.timeData[i].id = i;
    //         this.timeData[i].name = '周'+ i
    //         this.timeData[i].times = []
    //         for(let j in this.times) {
    //             this.timeData[i].times.push({num: parseInt(j)+1,name:this.times[j].name, disable:false})
    //         }
    //     }
    //     // console.log(this.editTableVisible)
    //     // console.log(this.dialogTableVisible)
    // }
}
</script>

<style>
.search {
    display: block;
    float: right;
    width: 200px;
    margin-bottom: 50px;
}
.search .el-input__suffix {
    cursor: pointer;
}
.course-time .el-icon {
    vertical-align: middle;
    cursor: pointer;
}
.time-pop {
    width: 100px;
}
.el-table .deleting-row {
  --el-table-tr-bg-color: var(--el-color-danger-light-7);
}
.deleting-row .el-table-fixed-column--left {
    background: var(--el-color-danger-light-7) !important;
}
.el-table .success-row {
  --el-table-tr-bg-color: var(--el-color-success-light-9);
}
.success-row .el-table-fixed-column--left {
    background: var(--el-color-success-light-9) !important;
}
.el-table .changing-row {
  --el-table-tr-bg-color: var(--el-color-warning-light-9);
}
.changing-row .el-table-fixed-column--left {
    background: var(--el-color-warning-light-9) !important;
}
.el-table .new-row {
  --el-table-tr-bg-color: var(--el-color-success-light-8);
}
.new-row .el-table-fixed-column--left {
    background: var(--el-color-success-light-8) !important;
}

/* 时间选择块 */
.time-container {
    display:flex;
    /* flex-wrap:wrap */
}
.time-container .el-checkbox-button:first-child .el-checkbox-button__inner {
    border-top: var(--el-border);
    border-radius: var(--el-border-radius-base) var(--el-border-radius-base) 0 0 ;
}
.time-container .el-checkbox-button:last-child .el-checkbox-button__inner {
    border-radius: 0 0 var(--el-border-radius-base) var(--el-border-radius-base);
}
.time-container .el-checkbox-button__inner {
    border-left:var(--el-border);
    border-top: 0;
    width: 100px;
}
.course-teacher .left-part {
    flex: 0;
    text-align: right;
    /* margin-left:80px; */
    padding-right: 20px;
    padding-top: 32px;
    min-width: 100px;
}
.course-teacher .left-part .line {
    height:32px;
}
.course-teacher .right-part {
    /* display: flex;
    flex-direction: column; */
    flex: 1;
    text-align: left;
    margin-left: 10px;
    /* width: 20%; */
    /* min-width:20%; */
}
.course-teacher .right-part .right-down {
    display: flex;
}
.test{
    
    padding: 5px;
}
.class-week {
    width: 100px;
    display: inline-block;
    text-align: center;
    font-size: 14px;
}
.day{
    /* position: relative; */
    /* float:left; */
    font-size: 16px;
}
</style>