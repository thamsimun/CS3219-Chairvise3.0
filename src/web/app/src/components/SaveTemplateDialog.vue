<template>
  <div>
    <el-button class='btn-save-template' :disabled=isOldTemplate type='primary' plain @click='dialogFormVisible=true'>Save this template</el-button>
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
      isOldTemplate: Boolean
    },
    data() {
      return {
        name: '',
        description: '',
        dialogFormVisible: false,
      }
    },
    methods: {
      saveTemplate() {
        if (this.$data.name.length < 5) {
          this.$store.commit('setError', 'Name must be of length greater than 5');
          return;
        }

        if (this.$data.description < 5) {
          this.$store.commit('setError', 'Description must be of length greater than 5');
          return;
        }

        this.$store.dispatch('saveFileTemplate', {
          ...this.template,
          name: this.$data.name,
          description: this.$data.description
        }).then(() => {
            this.$data.dialogFormVisible = false;
          });
      }
    },
  }
</script>

<style scoped>
  .btn-save-template {
    margin: 20px;
  }
</style>