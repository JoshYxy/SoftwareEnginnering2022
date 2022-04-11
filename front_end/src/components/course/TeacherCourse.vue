<template>
    <h2>我的课程</h2>
    <el-button @click="test">test </el-button>
    <el-button @click="handleNew">申请新增 </el-button>
    <el-table class="class-table" :data="courses" :row-class-name="tableRowClassName">
        <el-table-column fixed prop="courseName" label="课程名" width="120" />
        <el-table-column fixed prop="courseNum" label="课程编号" width="120" />
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
            <el-button size="small" @click="handleEdit(scope.$index, scope.row)">申请修改</el-button>
            <el-button size="small" type="danger" @click="handleDelete(scope.$index, scope.row)">申请删除</el-button>
            <el-dialog 
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
                        <el-form-item label="上课教室" prop="selectRoom">
                            <el-cascader :props="roomProps" :options="classroom" v-model="editCourse.selectRoom" placeholder="可输入教室号搜索" @change="updateRoom(scope.row)" filterable clearable/>     
                            <span style="padding-left:20px">*切换教室后请重新选择课程时间</span>
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
    <el-dialog  v-model="newTableVisible"  title="新增课程" :append-to-body="true">
        <el-form :model="newCourse" :rules="rules" hide-required-asterisk ref="newCourse" class="course-teacher">
            <el-form-item label="课程名称" prop="courseName">
                <el-input v-model="newCourse.courseName" />
            </el-form-item>
            <el-form-item label="课程编号" prop="courseNum">
                <el-input v-model="newCourse.courseNum" />
            </el-form-item>
            <el-form-item label="课程学分" prop="credits">
                <el-input v-model="newCourse.credits" />
            </el-form-item>
            <el-form-item label="选课容量" prop="capacity">
                <el-input v-model="newCourse.capacity" />
            </el-form-item>
            <el-form-item label="课程简介" prop="courseInfo">
                <el-input v-model="newCourse.courseInfo" type="textarea" />
            </el-form-item>      
            <el-form-item label="上课教室" prop="selectRoom">
                <el-cascader :props="roomProps" :options="classroom" v-model="newCourse.selectRoom" placeholder="可输入教室号搜索" @change="updateRoom" filterable clearable/>
                <span style="padding-left:20px">*切换教室后请重新选择课程时间</span>
            </el-form-item>
            <el-form-item label="上课时间" prop="selectTime">
                <div class="time-container">
                    <div class="left-part">
                        <div class="line" v-for="day in times" :key="day.id">{{day.startTime}}-{{day.endTime}}</div>
                    </div>
                    <div class="right-part">
                        <span class="class-week" v-for="period in periods" :key="period">{{period}}</span> 
                            <div class="right-down">
                            <el-checkbox-group v-for="day in timeData" :key="day.id" v-model="newCourse.selectTime[day.id]">
                                
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
        <template #footer>
            <span class="dialog-footer">
                <el-button @click="cancelNew">取消</el-button>
                <el-button type="primary" @click="submitNew">确认</el-button>
            </span>
        </template>
    </el-dialog>
</template>

<script>
// import CourseTime from './CourseTime.vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import {validTimetable, validSelectRoom} from '../jsComponents/CheckRules'
import {setCourseTime} from '../jsComponents/CourseSet'
import global_ from '../jsComponents/global'

export default {
    data() {
        return {
            rules: {
                courseName: [{required: true, message: '请输入课程名称', trigger: ['blur','change']}],
                courseInfo: [{required: true, message: '请输入课程介绍', trigger: ['blur','change']}],
                courseNum: [{required: true, message: '请输入课程编号', trigger: ['blur','change']}],
                credits: [{required: true, message: '请输入学分', trigger: ['blur','change']},
                          {pattern:/^[1-9]\d*$/, message: '请输入正整数', trigger: 'blur'}],
                capacity: [{required: true, message: '请输入选课容量', trigger: ['blur','change']},
                           {pattern:/^[1-9]\d*$/, message: '请输入正整数', trigger: 'blur'}],
                selectTime: [{validator: validTimetable, trigger: ['blur','change']}],
                selectRoom: [{validator: validSelectRoom, trigger: ['blur','change']}],
            },
            periods: global_.periods,
            abbrToBuilding: global_.abbrToBuilding,
            buildingToAbbr: global_.buildingToAbbr,
            editTableVisible:[false,false],//传入时数量与课程数需一直
            dialogTableVisible:[false,false],
            newTableVisible: false,
            selectTime:[[]],
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
            unavalTeaTimes: [
                [],
                [1,2,3,5,6,8,10,11,12],
                [3],
                [3,8],
                [],
                [],
                []
            ],
            timeData:[//响应式更新必备，不可删除
            ],
            unavalRoomTimes: [
                [],
                [1,2,3,5],
                [3],
                [3,8],
                [],
                [],
                [1,2,3]
            ],
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
            editCourse: {
                selectTime:[[]],
                index: '',
                selectRoom: [],
                courseName: '',
                classroom: '',
                // times: '',
            },
            newCourse: {
                selectTime:[[]],
                selectRoom: [],
                courseName: '',
                courseNum: '',
                college: '',
                period: 0,
                credits: 0,//学分
                teacher: '',
                courseInfo: '',
                position: '',
                capacity: 0,
                classHours: '',
                building: '',
                roomNum: '',
            }
        }
    },
    methods: {
        // eslint-disable-next-line
        tableRowClassName(row, rowIndex) {//根据该行课程的状态动态显示该行表格颜色
            if(row.row.type == 'change')
                return 'changing-row';
            if(row.row.type == 'delete')
                return 'deleting-row';
            if(row.row.type == 'add')
                return 'new-row';
            return 'normal-row';
        },
        handleNew() {
            for(let i in this.newCourse)
                this.newCourse[i] = ''
            this.newCourse['selectTime'] = [[],[],[],[],[],[],[]]
            this.newCourse['selectRoom']= []
            // this.unavalRoomTimes = [[],[],[],[],[],[],[]]
            this.setAvalTime()
            this.newTableVisible = true
            // this.newCourse = {}
        },
        handleDelete(index, data){
            ElMessageBox.confirm(
                '是否申请删除课程"'+data.courseName+'"',
                '请确认',
                {
                confirmButtonText: '确认',
                cancelButtonText: '取消',
                type: 'warning',
                }
            )
                .then(() => {
                    data.type = 'delete'
                    ElMessage({
                        type: 'success',
                        message: '已发起申请',
                    })
                })
                .catch(() => {
                    ElMessage({
                        type: 'info',
                        message: '申请取消',
                    })
                })
        },
        handleEdit(index, data) {
            //axios获取教室不可用时间 (data.building data.roomNum)
            //设定不能选择的时间
            this.setAvalTime()
            //课程目前时间设置为可以选中
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
            // this.editCourse.selectTime = data.times

            this.editCourse.courseName = data.courseName
            this.editCourse.selectRoom = []
            this.editCourse.selectRoom[0] = this.abbrToBuilding[data.building] 
            this.editCourse.selectRoom[1] = data.roomNum
            this.editTableVisible[index] = true;

        },
        submitEdit(index) {
            this.$refs['editCourse'].validate(valid => {
                if(valid){

                    // this.courses[index].courseName = this.editCourse.courseName
                    // this.courses[index].times = this.editCourse.selectTime
                    // this.courses[index].building = this.buildingToAbbr[this.editCourse.selectRoom[0]]
                    // this.courses[index].roomNum = this.editCourse.selectRoom[1]
                    // setCourseTime(this.courses[index], this.editCourse.selectTime)
                    this.courses[index].type = 'change'
                    this.editTableVisible[index] = false;
                    this.clearAvalTime()
                }
            })

        },
        cancelEdit(index) {
            this.editTableVisible[index] = false;
            this.clearAvalTime()
        },
        submitNew() {
            this.$refs['newCourse'].validate(valid => {
                if(valid){
                    console.log(this.newCourse.selectRoom)
                    this.newCourse['times'] = this.newCourse.selectTime
                    setCourseTime(this.newCourse, this.newCourse.selectTime)
                    this.newCourse['type'] = 'add'
                    this.newCourse.building = this.buildingToAbbr[this.newCourse.selectRoom[0]]
                    this.newCourse.roomNum = this.newCourse.selectRoom[1]
                    this.courses.push(JSON.parse(JSON.stringify(this.newCourse)))
                    this.editTableVisible.push(false)
                    this.dialogTableVisible.push(false)
                    this.clearAvalTime()
                    this.newTableVisible = false                        
                }
            })
            
        },
        cancelNew() {
            for(let i in this.newCourse)
                this.newCourse[i] = ''
            this.newCourse['selectTime'] = [[],[],[],[],[],[],[]]
            this.newCourse['selectRoom']= []
            this.clearAvalTime()
            this.newTableVisible = false
        },
        test() {
            // this.courses[0].type = 'change'
            console.log(this.courses)
        },
        /* eslint-disable */
        updateRoom(data) {//editCourse中覆写了函数，使其传入的是scope.row,默认为修改后的值value
            //axios获取教室不可用时间 传输editCourse.selectRoom
            this.unavalRoomTimes = [[1,2],[],[],[],[],[],[],[]]
            this.clearAvalTime()
            this.setAvalTime()
            console.log(data)
            if(typeof data == 'Object') {
                for(let i = 0; i < data.times.length; i++) {
                    for(let j = 1; j <= this.times.length; j++) {
                        if(data.times[i].indexOf(j) > -1)
                            this.timeData[i].times[j-1].disable = false
                    }
                }
            }
        },
        setAvalTime() {
            for(let i = 0; i < 7; i++) {
                for(let j = 1; j <= this.times.length; j++) {
                    if(this.unavalTeaTimes[i].indexOf(j) > -1  || this.unavalRoomTimes[i].indexOf(j) > -1)
                        this.timeData[i].times[j - 1].disable = true
                }
            } 
        },
        clearAvalTime() {
            for(let i = 0; i < 7; i++) {
                for(let j = 0; j < this.times.length; j++) {
                    this.timeData[i].times[j].disable = false
                }
            }   
        }
    },
    created() {
        for(let course of this.courses) {
            course.courseTime = ''
            setCourseTime(course, course.times)
        }
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
        //设置已有课时间为不可选择时间
        //axios获取教师不可用时间
        this.setAvalTime()
        // console.log(this.timeData)
    }
}
</script>

<style>
.el-dialog {
    width: 70%;
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