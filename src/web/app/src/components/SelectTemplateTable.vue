<template>
  <div style='width:100%;text-align:center;'>
    <el-table
      highlight-current-row
      @current-change='select'
      style='width:50%;margin:auto;'
      :data='templates'>
      <el-table-column
        property="templateId"
        label="Id"
        width="100">
      </el-table-column>
      <el-table-column
        property="name"
        label="Name"
        width="250">
      </el-table-column>
      <el-table-column
        property="description"
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
    },
    data() {
      return {
        selectedRow: null
      }
    },
    computed: {
      templates() {
        return this.$store.state.fileTemplates.templates.map(({templateId, templateMappingList}) => {
          return {
            templateId,
            ...templateMappingList
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