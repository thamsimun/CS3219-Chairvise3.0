<template>
  <div>
  <el-button type='text' @click='dialogFormVisible=true'>Save this template</el-button>
    <el-dialog :visible.sync='dialogFormVisible' title='Save template'>
      <el-form>
        <el-form-item label='Name' label-width='120px'>
          <el-input v-model='name' autocomplete='off'></el-input>
        </el-form-item>
        <el-form-item label='Description' label-width='120px'>
          <el-input v-model='description' autocomplete='off'></el-input>
        </el-form-item>
      </el-form>
    </el-dialog>
  </div>
</template>

<script>
  export default {
    name: "SaveTemplateDialog",
    data() {
      return {
        name: '',
        description: '',
        dialogFormVisible: false
      }
    }
  }
</script>

<style scoped>

</style>