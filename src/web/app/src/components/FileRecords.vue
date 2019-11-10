<template>
    <el-container>
        <el-main>
            <div style="padding-left: 55px; margin-bottom: 27px; margin-top: 20px">
                <span style="font-size: 30px; font-weight: bold">Files Added</span>
            </div>
            <div class="container">
                <div class="row" >
                    <div class = "column"  v-for="file in fileListInfo"
                         :key="file.fileNumber">
                        <el-card :body-style="{ padding: '30px'}" style="text-after-overflow: ellipsis; min-width: 250px; max-width: 250px; min-height: 200px">
                            <img src="../assets/csvicon.png" class="image">
                            <div class="text">
                                <span>{{file.fileName}}</span>
                                <div class="bottom-clearfix">
                                    <el-button type="text" v-on:click="deleteFile(file)" class="button">Delete</el-button>
                                </div>
                            </div>
                        </el-card>
                    </div>
                </div>
            </div>
        </el-main>
        <el-dialog
                title="Success"
                :visible.sync="deleteSuccess"
                width="30%" center>
            <span>You have successfully deleted the file!</span>
            <span slot="footer" class="dialog-footer">
        <el-button type="primary" v-on:click="closeDeleteSuccess">Sure</el-button>
      </span>
        </el-dialog>
        <el-dialog
                title="Failure"
                :visible.sync="deleteFailure"
                width="30%" center>
            <span>File cannot be deleted! Check that none of your presentations are using the file!</span>
            <span slot="footer" class="dialog-footer">
        <el-button type="primary" v-on:click="closeDeleteFailure">Go back</el-button>
      </span>
        </el-dialog>
    </el-container>
</template>

<script>
    import fileRecords from "../store/modules/fileRecords";

    const fileLogo = require('../assets/csv.png');
    export default {
        name: 'FileRecords',
        data() {
            return {
                fileLogo,
                file: undefined
            }
        },
        watch: {
            'isError'() {
                if (!this.isError) {
                    return
                }
                this.$notify.error({
                    title: 'File list API request fail',
                    message: this.$store.state.fileRecords.fileListStatus.apiErrorMsg,
                    duration: 0
                });
            }
        },
        computed: {
            isLoading() {
                return this.$store.state.fileRecords.fileListStatus.isLoading
            },

            fileListInfo() {
                return this.$store.state.fileRecords.fileList;
            },

            isError() {
                return this.$store.state.fileRecords.fileListStatus.isApiError
            },

            // whether upload is successful
            deleteSuccess: function () {
                return this.$store.state.fileRecords.isDeleteSuccess;
            },

            // whether upload is failed
            deleteFailure: function () {
                return this.$store.state.fileRecords.isDeleteFailure;
            },
        },
        mounted() {
            this.$store.dispatch('getFileList');
        },

        methods: {
            deleteFile(file) {
                this.$store.commit('deleteFromFileList', file);
                this.$store.dispatch('deleteFile');
            },

            closeDeleteSuccess: function() {
                this.$store.commit('clearFileRecord');
                this.$store.commit('clearDeleteSuccess');
            },

            closeDeleteFailure: function() {
                this.$store.commit('clearFileRecord');
                this.$store.commit('clearDeleteFailure');
            }
        },

        components: {
            fileRecords
        }
    }
</script>

<style>
    .bottom-clearfix {
        font-size: 14px;
        /*padding-left: 25px;*/
        text-align: center;
    }

    .text {
        font-size: 14px;
        text-align: center;
        /*not working*/
        text-after-overflow: ellipsis;

    }

    .text-item {
        font-size: 18px;
        padding-left: 60px;
    }

    .image{
        width: 100%;
        display: block;
    }

    .row {
        display: flex;
        flex-direction: row;
        flex-wrap: wrap;
        width: 100%;
    }

    .column {
        display: flex;
        flex-direction: column;
        padding-left: 20px;
        margin-bottom: 25px;
    }
    .container {
        margin: 0px;
        display: flex;
    }

</style>
