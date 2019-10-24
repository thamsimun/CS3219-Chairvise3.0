<template>
  <div class='wrapper'>
    <div v-if='schemaNotSelected'>
      <h1>Select your record type:</h1>
      <SelectDbSchema></SelectDbSchema>
    </div>
    <div v-else-if='fileNotUploaded'>
      <el-button class='back-btn' type='warning' icon='el-icon-back' circle @click='clearSelectedSchema'></el-button>
      <h1>Please upload your file:</h1>
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
    <div v-else-if='templateNotSelected'>
      <el-button class='back-btn' type='warning' icon='el-icon-back' circle @click='clearRawData'></el-button>
      <el-button class='forward-btn' type='warning' icon='el-icon-right' circle @click='skipTemplate'></el-button>
      <h1>Choose a template:</h1>
      <SelectTemplateTable></SelectTemplateTable>
    </div>
    <div v-else>
      <div class='pick-col'>
        <h1>Pick columns</h1>
        <ul>
          <li v-for='(field, index) in pool' v-bind:key='index' v-on:click='addToSelected(field)'>
            {{ field }}
          </li>
        </ul>
        <h1>Selected</h1>
        <ul>
          <li v-for='(field, index) in selected' v-bind:key='index' v-on:click='addToPool(field)'>
            {{ field }}
          </li>
        </ul>
        <mapping-tool-new ref='mapTool'></mapping-tool-new>
        <el-button v-on:click='nextClicked'>Next</el-button>
      </div>
    </div>
  </div>
</template>

<script>
  import MappingToolNew from '@/components/MappingToolNew.vue';
  import SelectDbSchema from "../components/SelectDbSchema";
  import SelectTemplateTable from '../components/SelectTemplateTable';
  import _ from 'lodash';
  import Papa from 'papaparse';

  export default {
    name: "ImportDataNew",
    data: function () {
      return {
        pool: [],
        selected: [],
        isReady: false,
        template: ''
      }
    },
    computed: {
      dbSchemas: function () {
        return this.$store.state.dbMetaData.entities;
      },
      schemaNotSelected: function () {
        return this.$store.state.dataMappingNew.data.dbSchemaName === '';
      },
      fileNotUploaded: function () {
        return this.$store.state.dataMappingNew.data.rawData.length === 0;
      },
      templateNotSelected: function () {
        return this.template === '';
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
      },
      clearSelectedSchema: function () {
        this.$store.commit('setDbSchema', {name: '', fieldMetaDataList: []});
      },
      clearRawData: function () {
        this.$store.commit('setRawData', []);
      },
      skipTemplate: function () {
        this.template = 'none';
      }
    },
    components: {
      MappingToolNew,
      SelectDbSchema,
      SelectTemplateTable
    }
  }
</script>

<style scoped>
  .wrapper {
    height: 100%;
    display: flex;
    justify-content: center;
    align-items: center;
  }

  h1 {
    text-align: center;
    padding: 20px;
  }

  .back-btn {
    position: absolute;
    left: 25%;
  }

  .forward-btn {
    position: absolute;
    right: 25%;
  }

  .pick-col {
    text-align: center;
  }

  .pick-col ul {
    display: inline-block;
    margin: 0;
    padding: 0;
  }
</style>