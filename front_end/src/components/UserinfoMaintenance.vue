<template>
  <div>
    <el-button type="text" @click="dialogFormVisible = true"
        >编辑</el-button
    >

    <el-dialog v-model="dialogFormVisible" title="Shipping address">
        <el-form :model="form">
        <el-form-item label="Promotion name" :label-width="formLabelWidth">
            <el-input v-model="form.name" autocomplete="off" />
        </el-form-item>
        <el-form-item label="Zones" :label-width="formLabelWidth">
            <el-select v-model="form.region" placeholder="Please select a zone">
            <el-option label="Zone No.1" value="shanghai" />
            <el-option label="Zone No.2" value="beijing" />
            </el-select>
        </el-form-item>
        </el-form>
        <template #footer>
        <span class="dialog-footer">
            <el-button @click="dialogFormVisible = false">Cancel</el-button>
            <el-button type="primary" @click="dialogFormVisible = false"
            >Confirm</el-button
            >
        </span>
        </template>
    </el-dialog>
  </div>
</template>

<script>

// import axios from 'axios'


export default {
  name: 'MajorMaintenance',
  props: {
      id: Number
  },
  data() {
    return {

        dialogFormVisible: false,
        formLabelWidth: '140px',
        form: {
        }
    }
  },
}

</script>

<style scoped>

</style>
