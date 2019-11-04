<template>
  <div class='wrapper'>
    <div>
      <h1>Pick columns</h1>
      <!--   List out all the fields in the pool, which is all the headers obtained from the csv    -->
      <ul>
        <li v-for='(field, index) in pool' v-bind:key='index' v-on:click='addToSelected(field)'>
          {{ field }}
        </li>
      </ul>
    </div>
    <div>
    <!--    List out all name of fields of the DB Schema obtained from store  -->
      <div class='table'>
        <div class='table-cell' v-for='(element, index) in fieldMetaData' :key='index'>

          <div class='db-field'><p>{{ element.name }}</p></div>
        </div>
      </div>
      <!--    List out all the possible transformations   -->
      <div class='table'>
        <div class='table-cell' v-for='(element, index) in fieldMetaData' :key='index'>
          <!--     Assign transformations    -->
          <el-select v-model='transformations[index]'>
            <!--       Choose from possible transformations for specific type     -->
            <el-option
              v-for='item in options[element.type]'
              :label='item.name'
              :key='item.name'
              :value='item.value'>
            </el-option>
          </el-select>
        </div>
      </div>

      <!--   For each element in mappingList, assign it a field from pool  -->
      <div class='table'>
        <div class='table-cell' v-for='(list, colIdx) in mappingList' :key='colIdx'>
          <!--     List of possible fields from pool to map to particular mapping list     -->
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
        // MappingList contains fields that contained in fieldMetaData
      }
    },
    computed: {
      pool() {
        return _.cloneDeep(this.$store.state.dataMappingNew.data.pool); // Pool data
      },
      rawData() {
        return _.cloneDeep(this.$store.state.dataMappingNew.data.rawData);  // Raw data
      },
      fieldMetaData() {
        return _.cloneDeep(this.$store.state.dataMappingNew.data.fieldMetaData); // Array of objects containing metadata
      },
      dbSchemaName() {
        return _.cloneDeep(this.$store.state.dataMappingNew.data.dbSchemaName); // Schema name
      },
      errors() {
        return this.$store.state.dataMappingNew.error;
      },
      messages() {
        return this.$store.state.dataMappingNew.messages;
      }
    },
    methods: {
      submit() {
        // make sure that user has picked all transformations
        if (this.transformations.length !== this.mappingList.length) {
          this.$store.commit('setMappingError', 'Please ensure all transformations are selected!');
          return;
        }
        // Iterate through each row, combine row and mappingList
        let toProcess = [];
        this.rawData.forEach(row => toProcess.push(_.pick(row, this.mappingList.flat())));
        this.$store.commit('setMappingList', _.cloneDeep(this.mappingList));
        this.$store.commit('setTransformations', _.cloneDeep(this.transformations));
        this.$store.commit('processData', toProcess);

        if (this.errors.length === 0) {
          this.$store.dispatch('persistData');
        }
      },

      addToSelected(field) {
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