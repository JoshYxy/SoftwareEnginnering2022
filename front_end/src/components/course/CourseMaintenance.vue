<template>
    <h2 style="margin-right:250px">新增课程</h2>
    <el-upload
      class="upload-demo"
      action="#"
      :http-request="Upload"
      accept=".csv"
      :limit="1"
      :before-upload="onBeforeUpload"
    >
      <el-button type="primary" round style="margin-right:250px">
        <el-icon style="vertical-align: middle;">
          <upload />
        </el-icon>
        <span style="vertical-align: middle;"> 上传文件 </span>
      </el-button>
      <template #tip>
        <div class="el-upload__tip" style="margin-right:250px">
          单个csv文件
        </div>
      </template>
    </el-upload>
    <div class="form">
      <el-form class="new-course-admin" :model="courseData" ref="courseData" :rules="rules" hide-required-asterisk>
        <el-form-item label="课程名称" prop="courseName" style="margin-left: 250px">
          <el-input v-model="courseData.courseName" style="width: 200px" />
        </el-form-item>
        <el-form-item label="课程编号" prop="courseNum" style="margin-left: 250px" >
          <el-input v-model="courseData.courseNum" style="width: 200px" />
        </el-form-item>
        <el-form-item label="课程学分" prop="credits" style="margin-left: 250px">
          <el-input v-model="courseData.credits"  style="width: 200px"/>
        </el-form-item>
        <el-form-item label="选课容量" prop="capacity" style="margin-left: 250px">
          <el-input v-model="courseData.capacity" style="width: 200px" />
        </el-form-item>
        <el-form-item label="课程简介" prop="courseInfo" style="margin-left: 250px">
          <el-input v-model="courseData.courseInfo" type="textarea" style="width: 400px" />
        </el-form-item>
        <el-form-item label="课程类型" prop="isGeneral" style="margin-left: 250px">
          <el-select v-model="courseData.isGeneral" placeholder="类型" >
            <el-option label="通选课程" value="通选课程" />
            <el-option label="专业选修" value="专业课程" />
            <el-option label="部分专业选修" value="面向部分专业课程" />
          </el-select>
        </el-form-item>
        <el-form-item label="面向专业" prop="majors" v-if="courseData.isGeneral == '面向部分专业课程'" style="margin-left: 250px">
          <el-cascader :props="majorProps" :options="collegeData" v-model="courseData.majors" placeholder="面向专业" :show-all-levels='false' @change="updateMajors" clearable/>
        </el-form-item>
        <el-form-item label="面向专业" prop="majors" v-if="courseData.isGeneral == '专业课程'" style="margin-left: 250px">
          <el-cascader :props="singlemajorProps" :options="collegeData" v-model="courseData.majors" placeholder="面向专业" :show-all-levels='false' @change="updateMajors" clearable/>
        </el-form-item>
        <el-form-item label="开课学年" prop="year" style="float:left; margin-left:250px;margin-right: 10px">
          <el-select v-model="courseData.year" placeholder="学年">
            <el-option :key="year" :value="year" :label="year" v-for="year in years" />
          </el-select>
        </el-form-item>
        <el-form-item label="开课学期" prop="year">
          <el-select v-model="courseData.semester" placeholder="学期">
            <el-option :key="semester" :value="semester" :label="semester" v-for="semester in semesters" />
          </el-select>
        </el-form-item>
        <el-form-item label="开课院系" prop="college" style="margin-left: 250px">
          <el-select v-model="courseData.college" value-key="collegeName" placeholder="学院" @change="updateCollege">
            <el-option :key="college.collegeName" :value="college" :label="college.collegeName" v-for="college in teacherData" />
          </el-select>
        </el-form-item>
        <el-form-item label="任课教师" prop="teacher" style="margin-left: 250px">
          <el-select v-model="courseData.teacher" value-key="number" placeholder="教师" no-data-text="未选择开课院系或学院无教师" @change="updateTeacher">
            <el-option :key="teacher.number" :value="teacher" :label="teacher.name+teacher.number" v-for="teacher in courseData.college.teachers" />
          </el-select>
        </el-form-item>
        <el-form-item label="上课教室" prop="selectRoom" style="margin-left: 250px">
          <el-cascader :props="roomProps" :options="classroom" v-model="courseData.selectRoom" placeholder="可输入教室号搜索" @change="updateRoom" filterable clearable/>
          <span>教室容量: {{roomCap}} </span>
        </el-form-item>
        <el-form-item label="上课时间" prop="selectTime" style="margin-left: 250px">
          <div class="time-container">
            <div class="left-part">
              <div class="line" v-for="day in times" :key="day.id">{{day.startTime}}-{{day.endTime}}</div>
            </div>
            <div class="right-part">
              <span class="class-week" v-for="period in periods" :key="period">{{period}}</span>
              <div class="right-down">
                <el-checkbox-group v-for="day in timeData" :key="day.id" v-model="courseData.selectTime[day.id]">

                  <el-checkbox-button
                      v-for="time in day.times"
                      :key="time.num"
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
        <el-form-item>
          <el-button type="primary" @click="submit" size="large" style="margin: 0 auto">提交</el-button>
        </el-form-item>
      </el-form>
    </div>
</template>

<script>
/* eslint-disable */
import CourseTime from './CourseTime.vue'
import {Check,Upload} from "@element-plus/icons";
import {validTimetable, validSelectRoom, validTeacher, validCollege} from '../jsComponents/CheckRules'
import {setCourseTime} from '../jsComponents/CourseSet'
import axios from 'axios'
import global_ from '../jsComponents/global'
import {ElMessage} from 'element-plus'
export default {
    components: {
        CourseTime,
        Check,
        Upload
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
            if(this.courseData.capacity!==''){
                this.$refs['courseData'].validateField('capacity', () => null)
            } 
            callback()
        };
        return {
            buildingToAbbr: global_.buildingToAbbr,
            periods: global_.periods,
            semesters: global_.semesters,
            years: global_.years,
            roomProps: {
                children: 'room',
                label: 'name',
                value: 'name'
            },
            majorProps: {
                children: 'majors',
                label: 'name',
                value: 'name',
                multiple: true
            },
            singlemajorProps: {
                children: 'majors',
                label: 'name',
                value: 'name',
            },
            classroom: [
                {
                    name: '第三教学楼',
                    aka: 'H3',
                    room: [
                        {name:'301', capacity:'120'},
                        {name:'402', capacity:'120'}
                    ]
                },
                {
                    name: '光华楼西辅楼',
                    aka: 'HGX',
                    room: [
                        {name:'201', capacity:'110'},
                        {name:'502', capacity:'100'}
                    ]
                },
            ],
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
            timeData:[
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
            avalTeacher: [],
            courseData: {
                selectTime:[[],[],[],[],[],[],[]],
                times:[[]],
                selectRoom: '',
                courseName: '',
                courseNum: '',
                college: {collegeName: '', teachers: []},
                period: 0,
                credits: 0,//学分
                teacher: {name:'',number:''},
                teacherNum: '',
                teacherName: '',
                courseInfo: '',
                time: '',
                position: '',
                capacity: 0,
                building: '',
                roomNum: '',
                isGeneral:'通选课程',
                majors: [
                            ["计算机科学技术学院","大数据"],
                            ["计算机科学技术学院","信息安全"],
                            ['软件工程学院','软件工程']
                        ],
                year: '',
                semester: '',
            },
            rules: {
                courseName: [{required: true, message: '请输入课程名称', trigger: 'blur'}],
                courseNum: [{required: true, message: '请输入课程编号', trigger: 'blur'}],
                credits: [{required: true, message: '请输入学分', trigger: 'blur'},
                          {pattern:/^[1-9]\d*$/, message: '请输入正整数', trigger: 'blur'}],
                courseInfo: [{required: true, message: '请输入课程介绍', trigger: 'blur'}],
                isGeneral: [{required: true, message: '请选择课程类型',trigger: ['blur','change']}],
                majors: [{required: true, message: '请选择面向专业',trigger: ['blur','change']}],
                capacity: [{required: true, message: '请输入选课容量', trigger: 'blur'},
                           {pattern:/^[1-9]\d*$/, message: '请输入正整数', trigger: 'blur'},
                           {validator: validCapacity, trigger: 'blur'}],
                college: [{validator: validCollege, trigger: ['blur','change']}],
                teacher: [{validator: validTeacher, trigger: ['blur','change']}], 
                selectRoom: [{validator: validSelectRoom, trigger: ['blur','change']},
                             {validator: validRoom, trigger: ['blur','change']}],
                selectTime: [{validator: validTimetable, trigger: ['blur','change']}],
                year: [{required: true, message: '请选择开课学年',trigger: ['blur','change']}],
                semester: [{required: true, message: '请选择开课学期',trigger: ['blur','change']}],
            }
        }
    },
    computed: {
        roomCap() {
            if(this.courseData.selectRoom == null) return 0
            for(let i = 0; i < this.classroom.length; i++){
                for(let j = 0; j < this.classroom[i].room.length; j++) {
                    if(this.courseData.selectRoom[1] == this.classroom[i].room[j].name 
                    && this.courseData.selectRoom[0] == this.classroom[i].name) {
                        return this.classroom[i].room[j].capacity
                    }
                }
            }
        }
    },
    // watch: {
    //     times:{
    //         handler() {
    //             for(let i = 0; i < 7; i++) {
    //                 this.timeData[i]['id'] = i;
    //                 this.timeData[i].name = '周'+ i
    //                 this.timeData[i].times = []
    //                 for(let j in this.times) {
    //                     this.timeData[i].times.push({name:this.times[j].name, disable:false})
    //                 }
    //             }
    //         }
    //     },
    // },
    methods: {
        test() {
            console.log(this.selectTime)
            // console.log(this.selectRoom)
        },
        updateCollege() {
            this.courseData.teacher = {}
        },
        updateMajors() {
            console.log(this.courseData.majors)
        },
        async updateTeacher(value) {
            //axios获取教师不可用时间
            await axios.put('http://localhost:8081/affair/teacher/time',
                {   
                    name: value.name, 
                    number: value.number
                })
            .then(res => {
                this.unavalTeaTimes = res.data.data1
            })
            for(let i = 0; i < 7; i++) {
                for(let j = 1; j <= this.times.length; j++) {
                    if(this.unavalTeaTimes[i].indexOf(j) > -1 || this.unavalRoomTimes[i].indexOf(j) > -1) {
                        this.timeData[i].times[j-1].disable = true
                        let p = this.courseData.selectTime[i].indexOf(j)
                        if(p > -1) {
                            this.courseData.selectTime[i].splice(p,1)
                        }
                    }
                    else
                        this.timeData[i].times[j-1].disable = false
                }
            } 
            console.log(this.courseData.selectTime)
        },
        async updateRoom(value) {
            //axios获取教室不可用时间
            if(value == null || value.length < 2) return 
            await axios.put('http://localhost:8081/affair/building/room/time',
                {   
                    building: this.buildingToAbbr[value[0]], 
                    roomNum: value[1]
                })
            .then(res => {
                // console.log(res)
                this.unavalRoomTimes = res.data.data1
            })
            
            for(let i = 0; i < 7; i++) {
                for(let j = 1; j <= this.times.length; j++) {
                    if(this.unavalTeaTimes[i].indexOf(j) > -1 || this.unavalRoomTimes[i].indexOf(j) > -1) {
                        this.timeData[i].times[j - 1].disable = true
                        let p = this.courseData.selectTime[i].indexOf(j)
                        if(p > -1) {
                            this.courseData.selectTime[i].splice(p,1)
                        }
                    }
                    else
                        this.timeData[i].times[j - 1].disable = false
                }
            } 
            
        },
        submit() {
            this.$refs['courseData'].validate(async valid => {
                if(valid){
                    if(this.courseData.majors != [] && typeof(this.courseData.majors[0]) == 'string') this.courseData.majors = [this.courseData.majors]
                    this.courseData.teacherName = this.courseData.teacher.name
                    this.courseData.teacherNum = this.courseData.teacher.number
                    this.courseData.building = this.buildingToAbbr[this.courseData.selectRoom[0]]
                    this.courseData.roomNum = this.courseData.selectRoom[1]
                    this.courseData.times = this.courseData.selectTime
                    this.courseData.collegeName = this.courseData.college.collegeName
                    setCourseTime(this.courseData, this.courseData.selectTime)
                    // setMajors(this.courseData, this.courseData.majors)
                    await axios.post('http://localhost:8081/course/new',this.courseData)
                    .then(res => {
                        console.log(res)
                        ElMessage({
                            type: 'success',
                            message: '新增成功',
                        })
                    }).catch(error => {
                        alert(error.response.data.msg)
                        console.dir(error);
                        });
                    console.log(this.courseData)
                    //axios 后重新更新unavalTime
                    //axios 获取教师，教室不可用时间
                    await axios.put('http://localhost:8081/affair/building/room/time',
                        {   
                            building: this.courseData.building, 
                            roomNum: this.courseData.roomNum
                        })
                    .then(res => {
                        this.unavalRoomTimes = res.data.data1
                    })
                    await axios.put('http://localhost:8081/affair/teacher/time',
                        {   
                            name: this.courseData.teacherName, 
                            number: this.courseData.teacherNum
                        })
                    .then(res => {
                        this.unavalTeaTimes = res.data.data1
                    })

                    // console.log(this.courseData)
                    // console.log(this.selectRoom)
                    // console.log(this.courseData.selectTime)
                }
            })
        },
        onBeforeUpload(file) {
             const isCSV = (file.type === 'application/vnd.ms-excel' || file.type === 'text/csv')
            // console.log(file.type)
            if (!isCSV) {
                this.$message.error('上传文件只能是.csv格式!');
            }
            return isCSV;
        },
        Upload(param) {
            let para = new FormData(); 
            para.append('file',param.file)
            axios.post("http://localhost:8081/course/csv",para,{headers: {'Content-Type': 'multipart/form-data'}})
                .then(function(){
                alert('批量导入信息完成，无导入失败')                  
                }).catch(error => {
                console.dir(error)
                alert(error.response.data.msg)
                })
        }
    },
    async created() {
        await axios.get('http://localhost:8081/user/course/new')
        .then(res => {
            this.teacherData = res.data.data1
            this.classroom = res.data.data2
            for(let i = 0; i < this.classroom.length; i++) {
                this.classroom[i] = JSON.parse(JSON.stringify(this.classroom[i]).replace(/fullName/g,"name"))
                this.classroom[i] = JSON.parse(JSON.stringify(this.classroom[i]).replace(/roomNum/g,"name"))
                this.classroom[i] = JSON.parse(JSON.stringify(this.classroom[i]).replace(/abbrName/g,"aka"))
            }
            this.times = res.data.data3
        }).catch(error => {
            console.dir(error)
        })
        axios.get("http://localhost:8081/user/edu")
        .then(res => {
            this.collegeData = res.data.data1
            for(let i in this.collegeData) {//替换变量名,对应后端数据
                this.collegeData[i] = JSON.parse(JSON.stringify(this.collegeData[i]).replace(/collegeVOId/g,"id"))
                this.collegeData[i] = JSON.parse(JSON.stringify(this.collegeData[i]).replace(/majorId/g,"id"))
                this.collegeData[i] = JSON.parse(JSON.stringify(this.collegeData[i]).replace(/collegeVOName/g,"name"))
                this.collegeData[i] = JSON.parse(JSON.stringify(this.collegeData[i]).replace(/majorName/g,"name"))
            }
            for(let i = 0; i < this.collegeData.length; i++) {
                if(this.collegeData[i].majors.length == 0) {
                    this.collegeData[i]['disabled'] = true
                }
            }
            console.log(this.collegeData)
        }).catch(error => {
            alert('获取服务器信息失败')
            console.dir(error);
        });
        this.timeData = []
        for(let i = 0; i < 7; i++) {//创建选课时间数组
            this.timeData.push({id:0, name:'', times:[]})
            this.timeData[i].id = i;
            this.timeData[i].name = '周'+ i
            this.timeData[i].times = []
            for(let j in this.times) {
                this.timeData[i].times.push({num: parseInt(j)+1,name:this.times[j].name, disable:false})
            }
        }
        console.log(this.timeData)
        
    }
}
</script>

<style>
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

.new-course-admin .left-part {
    flex: 0;
    text-align: right;
    margin-left:80px;
    padding-right: 20px;
    padding-top: 32px;
    min-width: 100px;
}

.new-course-admin .left-part .line {
    height:32px;
}

.new-course-admin .right-part {
    /* display: flex;
    flex-direction: column; */
    flex: 1;
    text-align: left;
    margin-left: 10px;
    /* width: 20%; */
    /* min-width:20%; */
}
.new-course-admin .right-part .right-down {
    display: flex;
}
.test{
    
    padding: 5px;
}
.new-course-admin .class-week {
    width: 100px;
    display: inline-block;
    text-align: center;
    font-size: 14px;
}
.new-course-admin .day{
    /* position: relative; */
    /* float:left; */
    font-size: 16px;
}

</style>