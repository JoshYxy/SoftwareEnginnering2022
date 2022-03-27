<template>
    <h2>课程信息</h2>
    <div><el-button @click="test">
        测试
    </el-button></div>
    <div style="float:left;">
    <div class="test" v-for="day in data" :key="day.id">{{day.name}}</div>
    </div>

    <el-checkbox-group v-for="day in data" :key="day.name" v-model="selectTime[day.id]" border style="width:500px;margin:auto;">
        
        <el-checkbox-button v-for="time in day.times" :key="time" :label="time" >{{
            time
        }}</el-checkbox-button>
        <span class="day"> {{day.name}} </span>
    </el-checkbox-group>

    <el-checkbox-group v-model="selectTime" size="large">
      <el-checkbox-button v-for="time in times" :key="time" :label="time">
        {{ time }}
      </el-checkbox-button>
    </el-checkbox-group>
</template>

<script>
export default {
    data() {
        return {
            selectTime:[[]],
            data:[
                {
                    id:0,
                    name:'周一',
                    times: [
                        '第一节',
                        '第二节',
                        '第三节',
                        '第四节',
                        '第五节'
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
.test{
    margin-left: 300px;
    padding: 5px;
}
.day{
    /* position: relative; */
    /* float:left; */
    font-size: 16px;
}
</style>