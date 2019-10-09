<template>
  <div>
    <el-row>
      <el-col :span='8'>
        <h3>Field Meta Data</h3>
        <el-tag class='block'
                v-for='(element, index) in fieldMetaData'
                :key='element.fieldName'
        >
          {{ index }}. {{ element.name }}
        </el-tag>
      </el-col>
      <el-col :span='8'>
        <h3>Selected</h3>
        <draggable :list='selectedFields'>
          <el-tag class='block' effect='plain'
                  v-for='(element, index) in selectedFields'
                  :key='element'
          >
            {{ index }}. {{ element }}
          </el-tag>
        </draggable>
      </el-col>
    </el-row>
    <el-button v-on:click='submitClicked'>Submit</el-button>
  </div>
</template>

<script>
  import draggable from "vuedraggable";

  export default {
    name: "MappingToolNew",
    components: {
      draggable
    },
    data: function () {
      return {
        fieldMetaData: _.cloneDeep(this.$store.state.dataMappingNew.data.fieldMetaData),
        dbSchemaName: _.cloneDeep(this.$store.state.dataMappingNew.data.dbSchemaName),
        selectedFields: _.cloneDeep(this.$store.state.dataMappingNew.data.selectedFields)
      }
    },
    methods: {
      submitClicked: function () {
        this.$store.commit('setSelectedFields', _.cloneDeep(this.selectedFields));

        this.$store.dispatch('persistData');
      }
    }
  }
</script>

<style scoped>
  .block {
    display: block;
    font-size: 18px;
    text-align: center;
  }
</style>