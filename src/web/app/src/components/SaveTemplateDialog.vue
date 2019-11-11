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
        <el-form-item>
          <el-button @click='saveTemplate'>Save Template</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
  </div>
</template>

<script>
  export default {
    name: "SaveTemplateDialog",
    props: {
      template: Object,
    },
    data() {
      return {
        name: '',
        description: '',
        dialogFormVisible: false,
        errors: []
      }
    },
    methods: {
      saveTemplate() {
        this.errors = [];
        console.log(this.template);
        let t = {
          ...this.template,
          name: this.$data.name,
          description: this.$data.description
        };
        console.log(t);
        this.$store.dispatch('saveFileTemplate', t)
          .then(response => console.log(response));
     }
    }
  }
</script>

<style scoped>

</style>