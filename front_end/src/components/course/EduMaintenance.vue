<template>
    <div >
        <h2>选课状态设置</h2>
        <el-switch    
            v-model="courseOpen"
            active-text="开始选课"
            inactive-text="停止选课" 
            :before-change="courseChange"/>
        <h2>教学时间设置</h2>
        <el-button @click="addCourse(-1)">增加第一节课</el-button>
        <el-button type='primary' @click="submit">提交修改</el-button>
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
                
                >
                <template #prepend>
                    <el-select v-model="newClassroom" placeholder="教学楼" value-key="name" style="width: 150px">
                        <el-option v-for="(building) in buildings" :key="building.name" :value="building" :label="building.name"  />
                    </el-select>
                </template>
                <template #append>
                    <section style="width: 150px"> {{newClassroom.aka}}{{newRoom}} </section>
                    
                </template>
            </el-input>
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
                    <el-button class="button" type="text" v-if="!onDeleteAll" @click="checkDelete(index)">删除该教学楼</el-button>
                </div>
                </template>
                <el-scrollbar height="200px" class="card-item-container"> 
                    <el-button v-if="!onDelete[index] && !onDeleteAll" class="button" type="text" @click="openDelete(index)">删除教室</el-button>
                    <el-button v-if="onDelete[index]" class="button2" type="text" @click="submitDelete(index)">确认删除选中教室</el-button>
                    <el-button v-if="onDelete[index]" class="button2" type="text" @click="cancelDelete(index)">取消删除</el-button>
                    <div class="hold-place" v-if="onDeleteAll && !onDelete[index]"> </div>
                    <div v-if="!onDelete[index]" class="card-item-div-container">
                        <div class="card-item" v-for="room in building.room" :key="room">{{building.aka}}{{room}}</div>
                    </div> 
                    <div v-for="(room,index2) in checkGroup" 
                            :key="room.name" 
                            class="card-item" >
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
import { ElMessage, ElMessageBox } from 'element-plus'
export default {
    data() {
        return {

            courseOpen: true,
            onDeleteAll: false,//是否有card进入删除状态
            onDelete: [false],//特定card是否进入删除教室状态
            checkGroup: [],
            startTime:['08:00','10:00'],
            endTime:['08:40','11:00'],
            newRoom:'',//存放input输入的教室号
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
                        '301','402'
                    ]
                },
                {
                    name: '光华楼西辅楼',
                    aka: 'HGX',
                    room: [
                        '201','502'
                    ]
                },
            ],//存放所有的教学楼及教室号信息
            buildings: [
                {name: '第三教学楼', aka: 'H3'},
                {name: '第四教学楼', aka: 'H4'},
                {name: '光华楼西辅楼', aka: 'HGX'},
                {name: '第五教学楼', aka: 'H5'},
                {name: '第六教学楼', aka: 'H6'},
            ]
        }
    },
    methods: {
        async courseChange() {
            var flag = false
            await ElMessageBox.confirm(
                '是否更改选课状态',
                '请确认',
                {
                confirmButtonText: '确认',
                cancelButtonText: '取消',
                type: 'warning',
                }
            )
                .then(() => {
                    ElMessage({
                        type: 'success',
                        message: '修改完成',
                    })
                    flag = true
                })
                .catch(() => {
                    flag = false
                })
            return flag
        },
        deleteCourse(index) {
            // this.courses.splice(index,1)
            this.startTime.splice(index,1)
            this.endTime.splice(index,1)
        },
        addCourse(index) {
            // this.courses.splice(index+1,0,{id:100, name:'dis'})
            this.startTime.splice(index+1,0,'')
            this.endTime.splice(index+1,0,'')
            // this.courses.push({
            //     id:this.courses.length+1,
            //     name:'第'+(this.courses.length+1)+'节课'
            // })
            // console.log(this.courses)
        },

        addClassroom() {
            //axios
            if(this.newRoom === '') {
                alert('教室号不能为空')
                return
            }
            var i;
            i = this.classroom.findIndex((building) => building.name == this.newClassroom.name)//寻找classroom已有的教学楼中是否有newClassroom中的教学楼 
            if(i >= 0) {
                if(this.classroom[i].room.indexOf(this.newRoom) > -1) {
                    alert('教室已存在，请勿重复添加')
                    this.newRoom = ''
                    return
                }
               
                this.classroom[i].room.push(this.newRoom) 
                this.classroom[i].room.sort((a, b) => {
                    if(a.length < b.length) return -1
                    else if(a.length > b.length) return 1
                    else return a.localeCompare(b)   
                }) //将教室号排序
            } 
            else {
                this.newClassroom.room=[]
                this.newClassroom.room.push(this.newRoom)
                this.classroom.push(this.newClassroom)
            }
            this.newRoom = ''
        },
        checkDelete(index) {
            ElMessageBox.confirm(
                '是否删除教学楼：' + this.classroom[index].name,
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
        //axios
            this.classroom.splice(index, 1)
        },
        openDelete(index) {
            this.onDeleteAll = true
            this.onDelete[index] = true
            this.checkGroup = []
            for(let i in this.classroom[index].room) {
                this.checkGroup.push({name:this.classroom[index].room[i], status: false})
            }
            console.log(this.checkGroup)
            
        },
        submitDelete(index) {
            this.onDelete[index] = false
            this.onDeleteAll = false
            for(let i = 0; i < this.checkGroup.length; i++) {
                if(this.checkGroup[i].status) {
                    this.classroom[index].room.splice(i, 1)
                    this.checkGroup.splice(i, 1)
                    i=i-1
                }
            }
            this.checkGroup = []
            //axios
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
        submit() {
            //axios
        }
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