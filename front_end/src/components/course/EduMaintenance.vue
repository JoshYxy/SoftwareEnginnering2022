<template>
    <div >
        <h2>教学时间设置</h2>
        <el-button @click="addCourse(-1)">增加第一节课</el-button>
        <el-button type='primary' @click="submit">提交修改</el-button>
        <div class="course-container" v-for="(item, index) in startTime" :key="item.id">
            <span class="left">第{{index+1}}节课</span>
            <div class="right">
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
    </div>
</template>

<script>

export default {
    data() {
        return {
            // courses: [
            //     {id:1, name:'第1节课'},
            //     {id:2, name:'第2节课'},
            //     {id:3, name:'第3节课'},

            // ],
            startTime:['08:00'],
            endTime:['08:40'],

        }
    },
    methods: {
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
        test(index) {
            console.log(this.startTime)
            console.log(index)
        },
        submit() {
            //axios
        }
    }
}
</script>

<style scoped>
.course-container {
    display: flex;
    margin: auto;
    justify-content:center
}
.left{
    flex: 0;
    margin-left:200px;
    min-width: 100px;
    padding-right:auto;
    /* padding-bottom:20px; */
    padding-left: auto;
    margin:10px;
    text-align:right;
}
.right{
    max-width:800px;
    flex: 1;
}
</style>