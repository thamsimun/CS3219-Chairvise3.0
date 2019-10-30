<template>
  <div class='wrapper'>
    <div>
      <h1>Pick columns</h1>
      <ul>
        <li v-for='(field, index) in pool' v-bind:key='index' v-on:click='addToSelected(field)'>
          {{ field }}
        </li>
      </ul>
    </div>
    <div>
      <div class='table'>
        <div class='table-cell' v-for='(element, index) in fieldMetaData' :key='index'>
          <div class='db-field'><p>{{ element.name }}</p></div>
        </div>
      </div>
      <div class='table'>
        <div class='table-cell' v-for='(element, index) in fieldMetaData' :key='index'>
          <el-select v-model='transformations[index]'>
            <el-option
              v-for='item in options[element.type]'
              :key='item.name'
              :value='item.value'
              :label='item.name'>
            </el-option>
          </el-select>
        </div>
      </div>
      <div class='table'>
        <div class='table-cell' v-for='(list, colIdx) in mappingList' :key='colIdx'>
          <draggable :list='list' group='fields'>
            <div v-for='(item, lstIdx) in list' :key='lstIdx' @dblclick='removeFromSelected(colIdx, lstIdx)'>
              <div class='user-field'><p>{{ item }}</p></div>
            </div>
          </draggable>
        </div>
      </div>
    </div>
    <el-button class='btn-complete' plain type='success' v-on:click='submit'>Complete</el-button>
  </div>
</template>

<script>
  import draggable from 'vuedraggable';
  import _ from 'lodash';
  import op from '../store/data/predefinedTransformations';
  import {distribute, removeItem} from '../common/utility';

  export default {
    name: "MappingToolNew",
    components: {
      draggable
    },
    data() {
      return {
        transformations: [],
        options: op,
        mappingList: this.$store.state.dataMappingNew.data.fieldMetaData.map(() => [])
      }
    },
    computed: {
      pool: function () {
        return _.cloneDeep(this.$store.state.dataMappingNew.data.pool);
      },
      rawData: function () {
        return _.cloneDeep(this.$store.state.dataMappingNew.data.rawData);
      },
      fieldMetaData: function () {
        return _.cloneDeep(this.$store.state.dataMappingNew.data.fieldMetaData);
      },
      dbSchemaName: function () {
        return _.cloneDeep(this.$store.state.dataMappingNew.data.dbSchemaName);
      },
      errors: function () {
        return this.$store.state.dataMappingNew.error;
      },
      messages: function () {
        return this.$store.state.dataMappingNew.messages;
      }
    },
    methods: {
      submit: function () {
        // make sure that user has picked all transformations
        if (this.transformations.length !== this.mappingList.length) {
          this.$store.commit('setMappingError', 'Please ensure all transformations are selected!');
          return;
        }

        let toProcess = [];
        this.rawData.forEach(row => toProcess.push(_.pick(row, this.mappingList.flat())));

        this.$store.commit('setMappingList', _.cloneDeep(this.mappingList));
        this.$store.commit('setTransformations', _.cloneDeep(this.transformations));
        this.$store.commit('processData', toProcess);

        if (this.errors.length === 0) {
          this.$store.dispatch('persistData');
        }
      },
      addToSelected: function (field) {
        distribute(this.mappingList, field);
      },
      removeFromSelected: function (colIdx, lstIdx) {
        this.mappingList = removeItem(this.mappingList, colIdx, lstIdx);
      }
    },
    watch: {
      errors: function (errList) {
        if (errList.length !== 0) {
          this.$notify.error({
            title: 'Error',
            message: errList.join('\n')
          });
        }
      },
      messages: function (msgList) {
        if (msgList.length === 0) {
          this.$notify.success({
            title: 'Success',
            message: msgList.join('\n')
          });
        }
      }
    }
  }
</script>

<style scoped>
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

  .table {
    display: table;
    width: 100%;
    table-layout: fixed;
    margin-top: 10px;
  }

  .table-cell {
    display: table-cell;
  }

  .wrapper {
    padding: 20px;
    flex-direction: column;
  }

  .btn-complete {
    margin: 20px;
  }

  ul {
    height:500px;
    overflow:auto;
  }
</style>