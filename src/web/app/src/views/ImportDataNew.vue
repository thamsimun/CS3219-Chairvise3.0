<template>
  <el-container style='height:100%;'>
    <el-aside style="width:20%; height:auto; margin-top:1px">
      <DataSideBar/>
    </el-aside>
    <div class="container">
        <el-col style="padding:10px">
          <div class='wrapper'>
            <div v-if='schemaNotSelected'>
              <h1 class="head">Upload a New File</h1>
              <h1 style='font-size:28px;'>Select your record type:</h1>
              <SelectDbSchema></SelectDbSchema>
            </div>
            <div v-else-if='fileNotUploaded'>

              <h1 class="head">File upload</h1>
              <div class="nav-buttons">
                <el-button class='back-btn' type='plain' icon='el-icon-back' circle
                           @click='clearSelectedSchema'></el-button>
              </div>
              <h3> Does your file have headers? </h3>
              <el-switch v-model="hasHeader" active-text="Yes, I have headers"
                         inactive-text="No, I can identify my own"
              ></el-switch>
              <br><br>
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
              <h1 class="head">Choose a template:</h1>
              <div class="nav-buttons">
                <el-button class='back-btn' type='plain' icon='el-icon-back' circle @click='clearRawData'></el-button>
                <el-button class='forward-btn' type='plain' icon='el-icon-right' circle @click='skipTemplate'></el-button>
              </div>
              <SelectTemplateTable></SelectTemplateTable>
            </div>
            <div v-else>
              <el-button class='back-btn' type='warning' icon='el-icon-back' circle @click='clearTemplate'></el-button>
              <mapping-tool-new ref='mapTool'></mapping-tool-new>
            </div>
          </div>
        </el-col>
    </div>
  </el-container>
</template>

<script>
  import MappingToolNew from '../components/MappingToolNew.vue';
  import SelectDbSchema from "../components/SelectDbSchema";
  import SelectTemplateTable from '../components/SelectTemplateTable';
  import DataSideBar from "../components/DataSideBar.vue";
  import _ from 'lodash';
  import Papa from 'papaparse';
  // import dataMappingNew from "../store/modules/dataMappingNew";
  export default {
    name: "ImportDataNew",

    data: function () {
      return {
        headerMode: false,
        pool: [],
        selected: [],
        useNoTemplate: false
      }
    },
    computed: {
      dbSchemas() {
        return this.$store.state.dbMetaData.entities;
      },
      schemaNotSelected() {
        return this.$store.state.dataMappingNew.data.dbSchemaName === '';
      },
      fileNotUploaded() {
        return this.$store.state.dataMappingNew.data.rawData.length === 0;
      },
      templateNotSelected() {
        return !this.$data.useNoTemplate && _.isEmpty(this.$store.state.fileTemplates.chosenTemplate);
      },
      hasHeader: {
        get() {
          return this.$store.state.dataMappingNew.hasHeader;
        },
        set(value) {
          this.$store.commit('setHasHeader', value)
        },
      }
    },
    methods: {
      fileUploadHandler(file) {
        this.$store.commit('setPageLoadingStatus', true);
        // If there is header, papaparse with headers
        // if (this.$store.state.dataMappingNew.data.hasHeader) {
          // Parse the raw data
        Papa.parse(file.raw, {
          skipEmptyLines: true,
          header: true, // we take it as there are headers present for now
          complete: result => {
            this.$store.commit('setPool', result.meta.fields);  // Set the headers obtained to Pool
            this.$store.commit('setRawData', result.data);
            // Data is stored as {Col1: data" , Col2: data} objects that represent row
            this.$store.commit('setFileName', file.name);       //  Set the file name
            this.$store.commit('setPageLoadingStatus', false);
          }
        })
        // } else {
        //   // Parse the raw data
        //   Papa.parse(file.raw, {
        //     skipEmptyLines: true,
        //     header: false, // we take it as there are headers present for now
        //     complete: result => {
        //       this.$store.commit('setPool', result.meta.fields);  // Set the headers obtained to Pool
        //       this.$store.commit('setRawData', result.data);
        //       // Data is stored as {Col1: data" , Col2: data} objects that represent row
        //       this.$store.commit('setFileName', file.name);       //  Set the file name
        //       this.$store.commit('setPageLoadingStatus', false);
        //     }
        //   })
        // }

      },
      clearSelectedSchema() {
        this.$store.commit('setDbSchema', {name: '', fieldMetaDataList: []});
      },
      clearRawData() {
        this.$store.commit('setRawData', []);
      },
      skipTemplate() {
        this.$data.useNoTemplate = true;
      },
      clearTemplate() {
        this.$data.useNoTemplate = false;
        this.$store.commit('selectTemplate', null);
      }
    },
    components: {
      MappingToolNew,
      SelectDbSchema,
      SelectTemplateTable,
      DataSideBar,
    }
  }
</script>

<style scoped>
  .container{
    width: 100%;
  }
  .head{
    text-align: center;
    font-size: 56px;
    font-weight: bold;
  }
  .wrapper {
    height: 100%;
    display: flex;
    justify-content: center;
  }
  h1 {
    text-align: center;
    padding: 20px;
    margin: 0;
  }
  .nav-buttons {
    justify-items: center;
    text-align: center;
  }
  .back-btn {
    position: center;
  }
  .forward-btn {
    position: center;
  }
  .pick-col {
    text-align: center;
  }
  .pick-col ul {
    display: inline-block;
    margin: 0;
    padding: 0;
  }

  div {
    width: 100%;
  }

</style>