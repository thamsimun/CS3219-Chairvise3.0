<template>
  <div>
    <el-alert
      title="You need to log in to view the page"
      type="error"
      v-if="!isLogin && !isAppLoading"
    >
      <el-button type="warning" plain size="mini" @click="navigateToHomePage">Return to the Home Page</el-button>
    </el-alert>
    <div v-if="isLogin">
      <h1>Hello world!</h1>
      <el-upload drag action=""
                 :auto-upload="false"
                 :show-file-list="false"
                 :multiple="false"
                 :on-change="fileUploadHandler">
        <i class="el-icon-upload"/>
        <div class="el-upload__text">Drop file here or <em>click to upload</em></div>
        <div class="el-upload__tip" slot="tip">Please upload .csv files with filed names.></div>
      </el-upload>
      <p>Please make sure your upload follows this template:</p>
      <p>(for authorRecord)</p>
      <ol>
        <li>Submission Id</li>
        <li>first name</li>
        <li>last name</li>
        <li>email</li>
        <li>country</li>
        <li>organisation</li>
        <li>webpage</li>
        <li>personId</li>
        <li>isCorresponding</li>
      </ol>
    </div>
    <div id="table"/>
    <el-button v-on:click="uploadClicked">Upload</el-button>
    <div>
      <draggable class="list-group" :list="selectedColumns" group="people">
        <div
          class="list-group-item"
          v-for="(element, index) in selectedColumns"
          :key="element"
        >
          {{element}}
        </div>
      </draggable>
    </div>
  </div>
</template>

<script>
  import Papa from "papaparse";
  import draggable from "vuedraggable";

  export default {
    /* eslint-disable */
    name: "NewImportData",
    components: {
      draggable
    },
    data: function () {
      return {
        table: undefined,
        selectedColumns: []
      }
    },
    computed: {
      isLogin() {
        return this.$store.state.userInfo.isLogin;
      },
      isAppLoading() {
        return this.$store.state.isPageLoading;
      }
    },
    methods: {
      navigateToHomePage() {
        this.$router.replace("/home");
      },
      populateTable(data) {
        this.table = new Tabulator("#table", {
          height: "300px",
          autoColumns: true,
          data: data
        });

        this.table.setColumns(this.table.getColumnDefinitions().map(obj =>
          Object.assign({
            headerClick: (e, column) => {
              this.selectedColumns.push(column.getDefinition().title)
              console.log("selectedColumns", this.selectedColumns)
            },
            headerSort: false
          }, obj)
        ))
      },
      fileUploadHandler(file) {
        this.$store.commit("setPageLoadingStatus", true);
        Papa.parse(file.raw, {
          skipEmptyLines: true,
          header: true,
          complete: result => {
            console.log("result", result);
            this.populateTable(result.data);
            this.$store.commit("setPageLoadingStatus", false);
          }
        })
      },
      uploadClicked() {
        let toUpload = [];
        let tableData = this.table.getData(true);
        let sc = Array.from(this.selectedColumns)

        for (var i = 0; i < tableData.length; i++) {
          toUpload.push(Object.values(_.pick(tableData[i], sc)));
        }

        console.log("toUpload", toUpload)
        this.$store.commit("setUploadedFile", toUpload);
      }
    },
    log(event) {
      console.log(event)
    }
  }
</script>

<style scoped>

</style>