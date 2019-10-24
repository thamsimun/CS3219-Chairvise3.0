<template>
  <div class='wrapper'>
    <mapping-tool-new v-if='isReady' ref='mapTool'></mapping-tool-new>
    <div v-else>
      <div v-if='schemaNotSelected'>
        <h1 style='text-align:center;padding:20px;'>Select your record type:</h1>
        <SelectDbSchema></SelectDbSchema>
      </div>
      <div v-else>
        <div>
          <el-upload drag action=''
                     :auto-upload='false'
                     :show-file-list='false'
                     :multiple='false'
                     :on-change='fileUploadHandler'>
            <i class='el-icon-upload'></i>
            <div class='el-upload__text'>Drop file here or <em>click to upload</em></div>
            <div class='el-upload__tip' slot='tip'>Please upload .csv files with filed names.</div>
          </el-upload>
        </div>
        <div class='template-container'>
          <p>CHOOSE TEMPLATE</p>
        </div>
        <div>
          <h2>Pick columns</h2>
          <ul>
            <li v-for='(field, index) in pool' v-bind:key='index' v-on:click='addToSelected(field)'>
              {{ field }}
            </li>
          </ul>
          <h2>Selected</h2>
          <ul>
            <li v-for='(field, index) in selected' v-bind:key='index' v-on:click='addToPool(field)'>
              {{ field }}
            </li>
          </ul>
        </div>
        <el-button v-on:click='nextClicked'>Next</el-button>
      </div>
    </div>
  </div>
</template>

<script>
  import MappingToolNew from '@/components/MappingToolNew.vue';
  import SelectDbSchema from "../components/SelectDbSchema";
  import _ from 'lodash';
  import Papa from 'papaparse';

  export default {
    name: "ImportDataNew",
    data: function () {
      return {
        pool: [],
        selected: [],
        isReady: false
      }
    },
    computed: {
      dbSchemas: function () {
        return this.$store.state.dbMetaData.entities;
      },
      schemaNotSelected: function () {
        return this.$store.state.dataMappingNew.data.dbSchemaName === '';
      }
    },
    methods: {
      addToSelected: function (field) {
        this.pool.splice(this.pool.indexOf(field), 1);
        this.selected.push(field);
      },
      addToPool: function (field) {
        this.selected.splice(this.selected.indexOf(field), 1);
        this.pool.push(field);
      },
      fileUploadHandler: function (file) {
        this.$store.commit('setPageLoadingStatus', true);
        Papa.parse(file.raw, {
          skipEmptyLines: true,
          header: true, // we take it as there are headers present for now
          complete: result => {
            this.pool = result.meta.fields;
            this.$store.commit('setRawData', result.data);
            this.$store.commit('setFileName', file.name);
            this.$store.commit('setPageLoadingStatus', false);
          }
        })
      },
      nextClicked: function () {
        this.$store.commit('setSelectedFields', _.cloneDeep(this.selected));

        this.isReady = true;
      }
    },
    components: {
      MappingToolNew,
      SelectDbSchema
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

  .wrapper {
    height: 100%;
    display: flex;
    justify-content: center;
    align-items: center;
  }
</style>