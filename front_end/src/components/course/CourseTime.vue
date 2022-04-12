<template>
    <!-- <div><el-button @click="test">
        测试
    </el-button></div> -->
    <div class="course-time">
        <div class="time-container">
            <div class="left-part">
                <div class="test" v-for="day in times" :key="day.id">{{day.startTime}}-{{day.endTime}}</div>
            </div>
            <div class="right-part">
                <span class="class-week" v-for="period in periods" :key="period">{{period}}</span> 
                    <div class="right-down">
                    <el-checkbox-group v-for="day in timeData" :key="day.id" v-model="selectTime[day.id]">
                        
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
    </div>
</template>

<script>
export default {
    props:['formerTime','changing'],
    emits: ['change'],
    data() {
        return {
            periods:[
                '周日','周一','周二','周三','周四','周五','周六',
            ],
            selectTime:this.formerTime,
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
        }
    },
    methods: {
        test() {
            console.log(this.selectTime)
            // console.log(this.selectRoom)
        }
    },
    watch: {
        changing: {
            handler() {
                // console.log(this.selectTime)
                this.$emit('change', this.selectTime)
            }
        },
        formerTime: {
            handler() {
                console.log(123)
                this.selectTime = this.formerTime
                console.log(this.selectTime)
            }
            
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
        // console.log(this.timeData)
        
    }
}
</script>

<style>
.course-time .time-container {
    display:flex;
    /* flex-wrap:wrap */
}
.course-time .time-container .el-checkbox-button:first-child .el-checkbox-button__inner {
    border-top: var(--el-border);
    border-radius: var(--el-border-radius-base) var(--el-border-radius-base) 0 0 ;
}
.course-time .time-container .el-checkbox-button:last-child .el-checkbox-button__inner {
    border-radius: 0 0 var(--el-border-radius-base) var(--el-border-radius-base);
}
.course-time .time-container .el-checkbox-button__inner {
    border-left:var(--el-border);
    border-top: 0;
    width: 100px;
}

.course-time .left-part {
    flex: 0;
    text-align: right;
    margin-left:80px;
    padding-right: 20px;

    min-width: 100px;
}
.course-time .right-part {
    /* display: flex;
    flex-direction: column; */
    flex: 1;
    text-align: left;
    margin-left: 10px;
    /* width: 20%; */
    /* min-width:20%; */
}
.course-time .right-part .right-down {
    display: flex;
}
.course-time .test{
    
    padding: 5px;
}
.course-time .class-week {
    width: 100px;
    display: inline-block;
    text-align: center;
    font-size: 14px;
}
.course-time .day{
    /* position: relative; */
    /* float:left; */
    font-size: 16px;
}
</style>