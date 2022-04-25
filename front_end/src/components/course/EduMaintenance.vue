<template>
    <div >
        <h2>选课状态设置</h2>
        <el-radio-group v-model="courseStatus" @change="courseChange">
            <el-radio :label='0'>关闭选课</el-radio>
            <el-radio :label="1">第一轮选课</el-radio>
            <el-radio :label="2">第二轮选课</el-radio>
        </el-radio-group>

        <!-- <el-switch    
            v-model="courseOpen"
            active-text="开始选课"
            inactive-text="停止选课" 
            :before-change="courseChange"/> -->
        <h2>教学时间设置</h2>
        <el-button @click="addCourse(-1)">增加第一节课</el-button>
        <el-button type='primary' @click="submitCourse">提交修改</el-button>
        <div class="course-container" v-for="(item, index) in startTime" :key="item.id">
            <span class="course-left">第{{index+1}}节课</span>
            <div class="course-right">
                <el-time-select
                    v-model="startTime[index]"
                    :min-time="index==0?'7:55':(endTime[index-1]?endTime[index-1]:'8:00')"
                    :max-time="endTime[index]"
                    :start="index==0?'8:00':(endTime[index-1]?endTime[index-1]:'8:00')"
                    placeholder="上课时间"
                    step="00:05"
                    end="22:30"
                    />-
                <el-time-select
                    v-model="endTime[index]"
                    :min-time="startTime[index]"
                    :max-time="startTime[index+1]"
                    :start="startTime[index]?startTime[index]:'8:00'"
                    placeholder="下课时间"
                    step="00:05"
                    end="23:30"
                    />
                <el-button @click="addCourse(index)">增加一节课</el-button>
                <el-button @click="deleteCourse(index)">删除这节课</el-button>
                <el-button @click="test(index)">test</el-button>
            </div>
        </div>
        <div class="class-container">
            <h2>上课教室设置</h2>
            <el-input
                v-model="newRoom"
                placeholder="教室号"
                class="input-with-select"
                style="width:70%"
                >
                <template #prepend>
                    <el-select v-model="newClassroom" placeholder="教学楼" value-key="name" style="width: 150px">
                        <el-option v-for="(building) in buildings" :key="building.name" :value="building" :label="building.name"  />
                    </el-select>
                </template>
                <template #append>
                    <section style="width: 80px"> {{newClassroom.aka}}{{newRoom}} </section>
                    
                </template>
            </el-input>
            <el-input v-model="newCap" placeholder="教室容量" style="width:30%"/>

            <el-button @keydown="addClassroom" @click="addClassroom">添加新教室</el-button>
        </div>
        <div class="classroom-container">
            <el-card 
                class="box-card"
                v-for="(building, index) in classroom"
                :key="building.name"
                >
                <template #header>
                <div class="card-header">
                    <span>{{building.name}}</span>
                    <el-button class="button" type="text" v-if="!onDeleteAll" @click="checkDelete(index)">清空教学楼内教室</el-button>
                </div>
                </template>
                <el-scrollbar height="200px" class="card-item-container"> 
                    <el-button v-if="!onDelete[index] && !onDeleteAll" class="button" type="text" @click="openDelete(index)">删除教室</el-button>
                    <el-button v-if="onDelete[index]" class="button2" type="text" @click="submitDelete(index)">确认删除选中教室</el-button>
                    <el-button v-if="onDelete[index]" class="button2" type="text" @click="cancelDelete(index)">取消删除</el-button>
                    <div class="hold-place" v-if="onDeleteAll && !onDelete[index]"> </div>
                    <div v-if="!onDelete[index]" class="card-item-div-container">
                        <div class="card-item" v-for="room in building.room" :key="room">{{building.aka}}{{room.name}} {{room.capacity}}人</div>
                    </div> 
                    <div v-for="(room,index2) in checkGroup" 
                            :key="room.name" 
                            class="card-item-delete" >
                        <el-checkbox 
                            v-if="onDelete[index]"
                            v-model="checkGroup[index2].status" 
                            :label='building.aka+checkGroup[index2].name' 
                            />
                    </div>
                </el-scrollbar>

            </el-card>
        </div>
    </div>
</template>

<script>
import axios from 'axios'
import { ElMessage, ElMessageBox } from 'element-plus'
export default {
    data() {
        return {

            courseOpen: true,
            courseStatus: '0',
            onDeleteAll: false,//是否有card进入删除状态
            onDelete: [false],//特定card是否进入删除教室状态
            checkGroup: [],
            startTime:[],
            endTime:[],
            newRoom:'',//存放input输入的教室号
            newCap:'',//存放input输入的教室容量
            newClassroom: {
                name: '',
                aka: '',
                room: [],
            },//存档select选择的教学楼
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
            ],//存放所有的教学楼及教室号信息
            buildings: [
                {id:1,name: '第三教学楼', aka: 'H3'},
                {id:2,name: '第四教学楼', aka: 'H4'},
                {id:3,name: '光华楼西辅楼', aka: 'HGX'},
                {id:4,name: '第五教学楼', aka: 'H5'},
                {id:5,name: '第六教学楼', aka: 'H6'},
            ]
        }
    },
    methods: {
        courseChange() {
            axios.post('http://localhost:8081/affair/curriculaVariable',null,{params:{choice:!this.courseOpen}})
            ElMessage({
                type: 'success',
                message: '修改完成',
            })
            console.log(this.courseStatus)
        },
        deleteCourse(index) {
            this.startTime.splice(index,1)
            this.endTime.splice(index,1)
        },
        addCourse(index) {
            this.startTime.splice(index+1,0,'')
            this.endTime.splice(index+1,0,'')
        },

        async addClassroom() {
            if(this.newRoom === '') {
                alert('教室号不能为空')
                return
            }
            if(this.newClassroom.name == '') {
                alert('教学楼不能为空')
                return
            }
            if(this.newCap === '') {
                alert('教室容量不能为空')
                return
            }
            if(!(/^[1-9]\d*$/.test(this.newCap))) {
                alert('教室容量为正整数')
                return
            }
            var i;
            i = this.classroom.findIndex((building) => building.name == this.newClassroom.name)//寻找classroom已有的教学楼中是否有newClassroom中的教学楼 
            if(i >= 0) {
                if(this.classroom[i].room.find(item => item.name == this.newRoom) != null) {
                    alert('教室已存在，请勿重复添加')
                    this.newRoom = ''
                    return
                }
                await axios.post('http://localhost:8081/affair/room/new', {building: this.newClassroom.aka, roomNum: this.newRoom, roomCapacity: this.newCap})
                .then(res => {
                    console.log(res)
                    this.classroom[i].room.push({name:this.newRoom, capacity: this.newCap}) 
                    this.classroom[i].room.sort((a, b) => {
                        if(a.name.length < b.name.length) return -1
                        else if(a.name.length > b.name.length) return 1
                        else return a.name.localeCompare(b.name)   
                    }) //将教室号排序
                })
                
            } 
            else {
                await axios.post('http://localhost:8081/affair/room/new', {building: this.newClassroom.aka, roomNum: this.newRoom, roomCapacity: this.newCap})
                .then(res => {
                    console.log(res)
                    this.newClassroom.room=[]
                    this.newClassroom.room.push({name:this.newRoom, capacity: this.newCap})
                    this.classroom.push(this.newClassroom)
                })

            }
            this.newRoom = ''
        },
        checkDelete(index) {
            ElMessageBox.confirm(
                '是否删除教学楼‘' + this.classroom[index].name+'’内的全部教室',
                'Warning',
                {
                    confirmButtonText: '确认',
                    cancelButtonText: '取消',
                    type: 'warning',
                }
            )
            .then(() => {
                this.deleteBuilding(index)
                ElMessage({
                    type: 'success',
                    message: '删除成功',
                })
                })
            .catch(() => {
                ElMessage({
                    type: 'info',
                    message: '删除取消',
                })
            })
        },
        deleteBuilding(index) {
            axios.delete('http://localhost:8081/affair/building/rooms', {data:{fullName: this.classroom[index].name, abbrName: this.classroom[index].aka}})
            this.classroom.splice(index, 1)
        },
        openDelete(index) {
            this.onDeleteAll = true
            this.onDelete[index] = true
            this.checkGroup = []
            for(let i in this.classroom[index].room) {
                this.checkGroup.push({name:this.classroom[index].room[i].name, status: false})
            }
            console.log(this.checkGroup)
            
        },
        async submitDelete(index) {
            this.onDelete[index] = false
            this.onDeleteAll = false
            for(let i = 0; i < this.checkGroup.length; i++) {
                if(this.checkGroup[i].status) {
                    await axios.delete('http://localhost:8081/affair/room', {data:{building: this.classroom[index].aka, roomNum: this.classroom[index].room[i].name}})
                    .then(res => {
                        console.log(res)
                        this.classroom[index].room.splice(i, 1)
                        this.checkGroup.splice(i, 1)
                    })
                    .catch(err => {
                        console.dir(err)
                    })
                    i=i-1
                }
            }
            this.checkGroup = []
        },
        cancelDelete(index) {
            this.onDelete[index] = false
            this.onDeleteAll = false
            this.checkGroup = [] 
        },
        test(index) {
            // console.log(this.startTime)
            console.log(index)
            console.log(this.newClassroom)
            console.log(this.classroom)
        },
        submitCourse() {
            let times = []
            for(let i = 0; i < this.startTime.length; i++) {
                if(this.startTime[i] == '' || this.endTime[i] == '') {
                    alert('请填写完所有上课时间后提交')
                    return
                }
                times.push({name: '第'+parseInt(i+1)+'节', startTime: this.startTime[i], endTime: this.endTime[i]})
            }
            axios.post('http://localhost:8081/affair/times', times)
            .then(res => {
                console.log(res)
                ElMessage({
                    type: 'success',
                    message: '提交成功',
                })
            })
        }
    },
    async created() {
         //获取选课状态
        axios.get('http://localhost:8081/user/curriculaVariable')
        .then(res => {
            this.courseStatus = res.data.data1
        })
        .catch(error => {
            console.dir(error)
        })
        //获取所有教学楼
        axios.get('http://localhost:8081/affair/building')
        .then(res => {
            this.buildings = res.data.data1
            for(let i = 0; i < this.buildings.length; i++) {
                this.buildings[i] = JSON.parse(JSON.stringify(this.buildings[i]).replace(/fullName/g,"name"))
                this.buildings[i] = JSON.parse(JSON.stringify(this.buildings[i]).replace(/abbrName/g,"aka"))
            }
        })
        await axios.get('http://localhost:8081/affair')
        .then(res => {
            console.log(res)
            //获取教室信息
            let classrooms = res.data.data1
            this.classroom = []
            for(let i = 0; i < classrooms.length; i++) {
                this.classroom.push({name:classrooms[i].fullName, aka:classrooms[i].abbrName, room:[]})
                for(let j = 0; j < classrooms[i].room.length; j++) {
                    this.classroom[i].room.push({name:classrooms[i].room[j].roomNum, capacity:classrooms[i].room[j].capacity})
                }
            }
            //获取上课时间信息
            let classTime = res.data.data2
            this.startTime = []
            this.endTime = []
            for(let i = 0; i < classTime.length; i++) {
                this.startTime.push(classTime[i].startTime)
                this.endTime.push(classTime[i].endTime)
            }
           
            
        })
    }
}
</script>

<style >
.el-card__body {
    padding-top: 10px;
}
.card-item-container .el-scrollbar__view {
    display: flex;
    flex-wrap: wrap;
    justify-content:space-between;
}
.card-item-div-container {
    width: 236px;
    display: flex;
    flex-wrap: wrap;
    justify-content:space-between;

}
.card-item-container .button{
    width: 100%;
}
.card-item-container .button2{
    width: 50%;
    margin: 0px;

}
.card-item {
    font-size: 16px;
    width: 100%;
}
.card-item-delete {
    font-size: 16px;
    width: 50%;
}
.card-item .el-checkbox__label {
    font-size: 16px;
}
.card-item-container .hold-place {
    width: 100%;
    height: 14px;
    padding: 8px;
    margin: 1px;
}
.classroom-container {
    width: 1200px;
    margin: auto;
    display: flex;
    flex-wrap:wrap;
    justify-content:flex-start;
}
.card-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
}

.text {
    font-size: 14px;
}
.box-card {
    margin: 0.9%;
    height: 300px;

    width: 23%;
}

.course-container {
    display: flex;
    margin: auto;
    justify-content:center
}
.course-left{
    flex: 0;
    margin-left:200px;
    min-width: 100px;
    padding-right:auto;
    /* padding-bottom:20px; */
    padding-left: auto;
    margin:10px;
    text-align:right;
}
.course-right{
    max-width:800px;
    flex: 1;
}
.class-container {
    width: 600px;
    margin: auto;
}
</style>