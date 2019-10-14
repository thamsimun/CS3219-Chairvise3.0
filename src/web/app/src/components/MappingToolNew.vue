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
      <el-col v-for='(element, index) in transformations' :key='index' :span='2'>
        <el-select v-model='transformations[index]'>
          <el-option
            v-for='item in options'
            :key='item.name'
            :value='item.value'
            :label='item.name'>
          </el-option>
        </el-select>
      </el-col>
    </el-row>
    <br>
    <el-row>
      <el-col v-for='(list, index) in mappingList' :key='index' :span='2' class='box'>
        <draggable :list='list' group='fields'>
          <div v-for='(item, index) in list' :key='index'>
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
  import {noTransformation, transformToDateTime} from "../store/helpers/processorNew";

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
        mappingList: this.zip(_.cloneDeep(this.$store.state.dataMappingNew.data.fieldMetaData),
          _.cloneDeep(this.$store.state.dataMappingNew.data.selectedFields)),
        transformations: this.$store.state.dataMappingNew.data.fieldMetaData.map(() => noTransformation),
        options: [
          {
            value: noTransformation,
            name: 'none'
          },
          {
            value: transformToDateTime,
            name: 'date and time'
          }
        ]
      }
    },
    methods: {
      zip: function (list1, list2) {
        return list1.map((field, index) =>
          (index === list1.length - 1)
            ? list2.slice(index)
            : list2.slice(index, index + 1));
      },
      submitClicked: function () {
        let toProcess = [];
        this.rawData.forEach(row => toProcess.push(_.pick(row, this.mappingList.flat())));

        this.$store.commit('setMappingList', _.cloneDeep(this.mappingList));
        this.$store.commit('setTransformations', _.cloneDeep(this.transformations));
        this.$store.commit('processData', toProcess);

        this.$store.dispatch('persistData');
      },
      log: function () {
        console.log(this.mappingList);
        let toProcess = [];
        this.rawData.forEach(row => toProcess.push(_.pick(row, this.mappingList.flat())));
        console.log(toProcess);
        console.log(this.transformations);
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