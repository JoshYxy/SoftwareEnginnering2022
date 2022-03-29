<template>
    <h2>课程信息</h2>
    <div><el-button @click="test">
        测试
    </el-button></div>

    <div class="time-container">
        <div class="left-part">
            <div class="test" v-for="day in data" :key="day.id">{{day.name}}</div>
        </div>
        <div class="right-part">
            <span class="class-time" v-for="period in periods" :key="period">{{period}}</span> 
            <el-checkbox-group v-for="day in data" :key="day.name" v-model="selectTime[day.id]" border >
                
                <el-checkbox-button v-for="time in day.times" :key="time" :label="time" >{{
                    time
                }}</el-checkbox-button>
                <!-- <span class="day"> {{day.name}} </span> -->
            </el-checkbox-group>
        </div>
    </div>

    <!-- <el-checkbox-group v-model="selectTime" size="large">
      <el-checkbox-button v-for="time in times" :key="time" :label="time">
        {{ time }}
      </el-checkbox-button>
    </el-checkbox-group> -->
</template>

<script>
export default {
    data() {
        return {
            selectTime:[[]],
            periods:[
                '8:45-8:55',
                '9:00-',
                '10:00-11:00',
            ],
            data:[
                {
                    id:0,
                    name:'周一',
                    times: [
                        '第一节',
                        '第二节',
                        '第三节',
                        '第四节',
                        '第五节',
                        '第六节',
                        '第七节',
                        '第八节',
                        '第九节',
                        '第十节',
                        '第十一节',
                    ],
                },
                {
                    id:1,
                    name:'周二',
                    times: [
                        '第一节',
                        '第二节',
                        '第三节',
                        '第四节',
                    ],
                }
            ],

            times: [
                '第一节',
                '第二节',
                '第三节',
                '第四节',
            ],
            courseData: {
                name: '',
                number: '',
                college: '',
                period: 0,
                credit: 0,//学分
                teacher: '',
                outline: '',
                time: '',
                position: '',
                capacity: 0
            }

        }
    },
    methods: {
        test() {
            console.log(this.selectTime)
        }
    }
}
</script>

<style scoped>
.time-container {
    display:flex;
    /* flex-wrap:wrap */
}
.left-part {
    flex: 0;
    text-align: right;
    margin-left:80px;
    padding-right: 20px;

    min-width: 100px;
}
.right-part {
    display: inline-block;
    flex: 1;
    text-align: left;
    margin-left: 10px;
    /* width: 20%; */
    /* min-width:20%; */
}
.test{
    
    padding: 5px;
}
.class-time {
    min-width: 74px;
    max-width: 74px;
    width: 74px;
    display: inline-block;
    font-size: 10px;
}
.day{
    /* position: relative; */
    /* float:left; */
    font-size: 16px;
}
</style>