<template>
  <div class='welcome'>
    <h1>登录成功</h1>
    <el-button id='logout' v-show="this.$store.state.Authorization !== ''" type="info" @click="logout">退出</el-button>
    <el-button type="info" @click="refresh">test</el-button>
    <el-table :data="userData" style="width: 100%" max-height="250">
    <el-table-column prop="id" label="Date" width="150" />
    <el-table-column prop="name" label="Name" width="120" />
    <el-table-column prop="role" label="State" width="120" />
    <el-table-column prop="phone" label="City" width="120" />
    <el-table-column prop="email" label="Zip" width="120" />
    <el-table-column fixed="passport" label="Operations" width="120">
      <template #default="scope">
        <el-button
          type="text"
          size="small"
          @click.prevent="deleteRow(scope.$index)"
        >
          Remove
        </el-button>
      </template>
    </el-table-column>
  </el-table>
  <el-button class="mt-4" style="width: 100%" @click="onAddItem"
    >Add Item</el-button
  >
  </div>
</template>

<script>
export default {
    methods:{
        logout(){
          if(localStorage.getItem('Authorization') !== ''){
            localStorage.removeItem('Authorization');
            localStorage.removeItem('role');
            this.$store.commit('check');
            this.$router.push('/');
          }
          else{
            alert("未登录");
          }
        },
        refresh(){
          this.$store.commit('check');
        }
    }
};
</script>

<style scoped>
.welcome{
  position: absolute;
  width: 500px;
  height: 300px;
  margin: auto;
  left: 0;
  right: 0;
}
</style>

