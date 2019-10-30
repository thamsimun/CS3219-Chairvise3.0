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
      <el-button class='back-btn' type='warning' icon='el-icon-back' circle @click='clearTemplate'></el-button>
      <mapping-tool-new ref='mapTool'></mapping-tool-new>
    </div>

  </div>
</template>

<script>
  import MappingToolNew from '../components/MappingToolNew.vue';
  import SelectDbSchema from "../components/SelectDbSchema";
  import SelectTemplateTable from '../components/SelectTemplateTable';
  import Papa from 'papaparse';

  export default {
    name: "ImportDataNew",
    data: function () {
      return {
        pool: [],
        selected: [],
        template: '' // to be implemented
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
      fileUploadHandler: function (file) {
        this.$store.commit('setPageLoadingStatus', true);
        Papa.parse(file.raw, {
          skipEmptyLines: true,
          header: true, // we take it as there are headers present for now
          complete: result => {
            this.$store.commit('setPool', result.meta.fields);
            this.$store.commit('setRawData', result.data);
            this.$store.commit('setFileName', file.name);
            this.$store.commit('setPageLoadingStatus', false);
          }
        })
      },
      clearSelectedSchema: function () {
        this.$store.commit('setDbSchema', {name: '', fieldMetaDataList: []});
      },
      clearRawData: function () {
        this.$store.commit('setRawData', []);
      },
      skipTemplate: function () {
        this.template = 'none';
      },
      clearTemplate: function () {
        this.template = '';
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
    margin: 0;
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