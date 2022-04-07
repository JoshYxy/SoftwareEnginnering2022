<template>
    <h2>课程信息</h2>
    <div><el-button @click="test">
        测试
    </el-button></div>
    <el-form class="new-course-admin" :model="courseData" :rules="rules" hide-required-asterisk>
        <el-form-item label="课程名称" prop="courseName">
            <el-input v-model="courseData.courseName" />
        </el-form-item>
        <el-form-item label="课程编号" prop="courseNum">
            <el-input v-model="courseData.courseNum" />
        </el-form-item>
        <el-form-item label="课程学分" prop="credits">
            <el-input v-model="courseData.credits" />
        </el-form-item>
        <el-form-item label="选课容量" prop="capacity">
            <el-input v-model="courseData.capacity" />
        </el-form-item>
        <el-form-item label="课程简介" prop="courseInfo">
            <el-input v-model="courseData.courseInfo" type="textarea" />
        </el-form-item>      
        <el-form-item label="开课院系" prop="collegeName">
            <el-select v-model="courseData.collegeName" value-key="name" placeholder="学院">
                <el-option :key="college.id" :value="college.name" :label="college.name" v-for="college in collegeData" />
            </el-select>
        </el-form-item>
        <el-form-item label="任课教师" prop="teacher">
            <el-select v-model="courseData.teacherId" value-key="number" placeholder="教师">
                <el-option :key="teacher.number" :value="teacher.number" :label="teacher.name+teacher.number" v-for="teacher in teacherData" />
            </el-select>
        </el-form-item> 
        <el-form-item label="上课教室" prop="classroom">
            <el-cascader :props="roomProps" :options="classroom" v-model="selectRoom" placeholder="可输入教室号搜索" filterable clearable/>
        </el-form-item>
        <el-form-item label="上课时间" prop="selectTime">            
            <div class="time-container">
                <div class="left-part">
                    <div class="test" v-for="day in times" :key="day.id">{{day.startTime}}-{{day.endTime}}</div>
                </div>
                <div class="right-part">
                    <span class="class-week" v-for="period in periods" :key="period">{{period}}</span> 
                        <div class="right-down">
                        <el-checkbox-group v-for="day in timeData" :key="day.name" v-model="courseData.selectTime[day.id]">
                            
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
          <el-button type="primary" @click="submit">提交</el-button>
        </el-form-item>
    </el-form>
    <!-- <course-time>
    </course-time> -->
    <!-- <el-checkbox-group v-model="selectTime" size="large">
      <el-checkbox-button v-for="time in times" :key="time" :label="time">
        {{ time }}
      </el-checkbox-button>
    </el-checkbox-group> -->
</template>

<script>
/* eslint-disable */
import CourseTime from './CourseTime.vue'
import {validTimetable} from '../jsComponents/CheckRules'
import {setCourseTime} from '../jsComponents/CourseSet'
export default {
    components: {
        CourseTime
    },
    data() {
        return {
            
            periods:[
                '周一','周二','周三','周四','周五','周六','周日',
            ],
            selectRoom: '',
            roomProps: {
                children: 'room',
                label: 'name',
                value: 'name'
            },
            classroom: [
                {
                    name: '第三教学楼',
                    aka: 'H3',
                    room: [
                        {name:'301'},
                        {name:'402'}
                    ]
                },
                {
                    name: '光华楼西辅楼',
                    aka: 'HGX',
                    room: [
                        {name:'201'},
                        {name:'502'}
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
                majors: [
                    {id: 1, name: '生物'},
                    {id: 2, name: '123'}
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
            teacherData: [
                {
                    name:'小王',
                    number: 22200000
                },
                {
                    name:'小一',
                    number: 22111100
                },
                {
                    name:'小零',
                    number: 20000000
                }
            ],
            courseData: {
                selectTime:[[]],
                courseName: '',
                courseNum: '',
                college: '',
                period: 0,
                credits: 0,//学分
                teacher: '',
                courseInfo: '',
                time: '',
                position: '',
                capacity: 0
            },
            rules: {
                courseName: [{required: true, message: '请输入课程名称', trigger: 'blur'}],
                courseNum: [{required: true, message: '请输入课程编号', trigger: 'blur'}],
                credits: [{required: true, message: '请输入学分', trigger: 'blur'},
                          {pattern:/^[1-9]\d*$/, message: '请输入正整数', trigger: 'blur'}],
                courseInfo: [{required: true, message: '请输入课程介绍', trigger: 'blur'}],
                capacity: [{required: true, message: '请输入选课容量', trigger: 'blur'},
                           {pattern:/^[1-9]\d*$/, message: '请输入正整数', trigger: 'blur'}],
                selectTime: [{validator: validTimetable, trigger: ['blur','change']}],
            }
        }
    },
    watch: {
        times:{
            handler() {
                for(let i = 0; i < 7; i++) {
                    this.timeData[i].id = i;
                    this.timeData[i].name = '周'+ i
                    this.timeData[i].times = []
                    for(let j in this.times) {
                        this.timeData[i].times.push({name:this.times[j].name, disable:false})
                    }
                }
            }
        }
    },
    methods: {
        test() {
            console.log(this.selectTime)
            // console.log(this.selectRoom)
        },
        submit() {
            setCourseTime(this.courseData, this.courseData.selectTime)
            console.log(this.courseData)
            console.log(this.selectRoom)
            console.log(this.courseData.selectTime)
            
        }
    },
    created() {
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

    min-width: 100px;
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