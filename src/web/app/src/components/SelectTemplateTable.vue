<template>
  <div style='width: 100%'>
    <table>
      <tr>
        <th>Name</th>
        <th>Description</th>
      </tr>
      <tr>
        <td>test</td>
        <td>description</td>
      </tr>
      <tr>
        <td>test2</td>
        <td>description2</td>
      </tr>
      <tr v-for='template in templates'>
        <td> {{ template.templateId }} </td>
        <td> test description </td>
      </tr>
    </table>

    <el-table
      highlight-current-row
      @current-change='handleCurrentChange'
      style='width:100%;'
      :data='templates'>
      <el-table-column
        type="index"
        width="50">
      </el-table-column>
      <el-table-column
        property="Name"
        label="Name"
        width="120">
      </el-table-column>
      <el-table-column
        property="Description"
        label="Description"
        width="120">
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
        return this.$store.state.fileTemplates.templates;
      }
    },
    methods: {
      select(template) {
        this.$store.commit('selectTemplate', template);
      },
      handleCurrentChange(val) {
        this.selectedRow = val;
      }
    }
  }
</script>

<style scoped>
  .template-container {
    display: inline-block;
    width: 1000px;
    height: 480px;
    border-style: solid;
    border-width: 10px;
    border-color: gold;
  }

  table {
    border-collapse: collapse;
    width: 100%;
  }

  table, th, td {
    border: 1px solid black;
  }

  th, td {
    padding: 15px;
    text-align: left;
  }
</style>