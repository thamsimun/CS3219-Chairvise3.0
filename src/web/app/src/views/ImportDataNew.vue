<template>
  <el-container>
    <el-container>
      <el-aside class="sidebar" width="250px">
        <DataSideBar/>
      </el-aside>
    </el-container>
    <el-container>
      <el-col>
        <div class='wrapper'>

          <div v-if='schemaNotSelected'>
            <h1>Select your record type:</h1>
            <SelectDbSchema></SelectDbSchema>
          </div>

          <div v-else-if='fileNotUploaded'>
            <el-button class='back-btn' type='warning' icon='el-icon-back' circle @click='clearSelectedSchema'></el-button>
            <h1>Please upload your file:</h1>
            <p> Does your file have headers? </p>
            <el-switch v-model="hasHeader" active-text="Yes, I have headers" inactive-text="No, I can identify my own"
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
      </el-col>
    </el-container>
  </el-container>
</template>

<script>
  import MappingToolNew from '../components/MappingToolNew.vue';
  import SelectDbSchema from "../components/SelectDbSchema";
  import SelectTemplateTable from '../components/SelectTemplateTable';
  import Papa from 'papaparse';
  import DataSideBar from "@/components/DataSideBar.vue";

  export default {
    name: "ImportDataNew",
    data: function () {
      return {
        headerMode: false,
        pool: [],
        selected: [],
        template: '' // to be implemented
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
        return this.template === '';
      },
      hasHeader: {
        get() {
          return this.$store.state.dataMappingNew.hasHeader;
        },
        set (value) {
          this.$store.commit('setHasHeader', value)
        },
      }
    },

    methods: {
      fileUploadHandler(file) {
        this.$store.commit('setPageLoadingStatus', true);
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
      },
      clearSelectedSchema() {
        this.$store.commit('setDbSchema', {name: '', fieldMetaDataList: []});
      },
      clearRawData() {
        this.$store.commit('setRawData', []);
      },
      skipTemplate() {
        this.template = 'none';
      },
      clearTemplate() {
        this.template = '';
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
  .wrapper {
    height: 100%;
    display: flex;
    justify-content: center;
    align-items: center;
    padding-left: 210px;
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
  .upload{
    margin-left: 100px;
  }

</style>