<template>
  <div>
    <el-button v-on:click='submitClicked'>Submit</el-button>
    <br>
    <el-row>
      <el-col v-for='(element, index) in fieldMetaData' :key='index' :span='2'>
        <div class='db-field'><p>{{ element.name }}</p></div>
      </el-col>
    </el-row>
    <br>
    <el-row>
      <el-col v-for='(list, index) in mappingList' :key='index' :span='2' class='box'>
          <draggable :list='list' group='fields'>
            <div v-for='item in list' :key='item'>
              <div class='user-field'><p>{{ item }}</p></div>
            </div>
          </draggable>
      </el-col>
    </el-row>

    <button v-on:click='log'>Log</button>
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
        rawData: _.cloneDeep(this.$store.state.dataMappingNew.data.rawData),
        fieldMetaData: _.cloneDeep(this.$store.state.dataMappingNew.data.fieldMetaData),
        dbSchemaName: _.cloneDeep(this.$store.state.dataMappingNew.data.dbSchemaName),
        mappingList: _.cloneDeep(this.$store.state.dataMappingNew.data.fieldMetaData).map((field, index) =>
          [_.cloneDeep(this.$store.state.dataMappingNew.data.selectedFields)[index]])
      }
    },
    methods: {
      submitClicked: function () {
        let toProcess = [];
        this.rawData.forEach(row => toProcess.push(Object.values(_.pick(row, this.mappingList.flat()))));

        this.$store.commit('processData', toProcess);

        this.$store.dispatch('persistData');
      },
      log: function () {
        console.log(this.mappingList);
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

  .db-field {
    border-style: solid;
    border-width: 5px;
    border-color: gold;
    margin: 5px;
    padding: 5px;
  }

  .user-field {
    border-style: solid;
    border-width: 5px;
    border-color: orange;
    margin: 5px;
    padding: 5px;
  }

  .box {
    border-style: solid;
    border-width: 5px;
    border-color: green;
  }
</style>