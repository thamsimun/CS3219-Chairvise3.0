<template>
  <div style='width:100%;text-align:center;'>
    <el-table
      highlight-current-row
      @current-change='select'
      style='width:50%;margin:auto;'
      :data='templates'>
      <el-table-column
        property="Name"
        label="Name"
        width="120">
      </el-table-column>
      <el-table-column
        property="Description"
        label="Description">
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
  export default {
    name: "SelectTemplateTable",
    mounted() {
      this.$store.dispatch('fetchFileTemplates');
      console.log(this.$store.state.fileTemplates.templates);
    },
    data() {
      return {
        selectedRow: null
      }
    },
    computed: {
      templates() {
        return this.$store.state.fileTemplates.templates.map(template => {
          return {
            Name: template.templateId,
            Description: 'description here..',
            ...template.templateMappingList
          };
        });
      }
    },
    methods: {
      select(template) {
        this.$store.commit('selectTemplate', template);
      }
    }
  }
</script>

<style scoped>
</style>