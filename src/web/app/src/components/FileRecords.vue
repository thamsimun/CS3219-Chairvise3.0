<template>
    <el-container>
        <el-main>
            <div slot="header">
                <span>Files Added</span>
            </div>
            <el-row>
                <el-col :span="5" v-for="file in fileListInfo"
                        :key="file.fileNumber" class="text-item" style="margin-bottom: 30px">
                    <el-card :body-style="{ padding: '5px' }">
                        <img src="../assets/csv1.png" class="image">
                        <div style="padding: 14px;">
                            <span>{{file.fileName}}</span>
                            <div class="bottom-clearfix">
                                <el-button type="text" v-on:click="deleteFile(file)" class="button">Delete</el-button>
                            </div>
                        </div>
                    </el-card>
                </el-col>
            </el-row>
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
        padding-left: 25px;
    }

    .text-item {
        font-size: 18px;
        padding-left: 55px;
    }

    .image{
        width: 100%;
    }

</style>
