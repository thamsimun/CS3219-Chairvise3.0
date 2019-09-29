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
        <i class="el-icon-upload" />
        <div class="el-upload__text">Drop file here or <em>click to upload</em></div>
        <div class="el-upload__tip" slot="tip">Please upload .csv files with filed names.></div>

      </el-upload>
    </div>
    <div id="table" />
  </div>
</template>

<script>
  import Papa from "papaparse";

  export default {
    /* eslint-disable */
    name: "NewImportData",
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
        const table = new Tabulator("#table", {
          height: "300px",
	        autoColumns: true,
          movableColumns: true,
          data: data
        });
        table.setColumns(table.getColumnDefinitions().map(obj =>
          Object.assign({headerClick: (e, column) => column.hide()}, obj)
        ))
      },
      fileUploadHandler(file) {
        this.$store.commit("setPageLoadingStatus", true);
        Papa.parse(file.raw, {
          skipEmptyLines: true,
          header: true,
          complete: result => {
            console.log(result);
            this.populateTable(result.data);
            this.$store.commit("setPageLoadingStatus", false);
          }
        })
      },

    }
  }
</script>

<style scoped>

</style>