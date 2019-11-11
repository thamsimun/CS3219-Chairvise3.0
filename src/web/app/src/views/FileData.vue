<template>
    <el-alert v-if="!isLogin" title="Please login to view files added" type="error" show-icon
              class="errorMsg"/>
    <el-container class="file-page" v-else-if="isLogin">
        <el-aside style="width:200px; height:auto; margin-top:1px">
            <DataSideBar/>
        </el-aside>
        <el-container>
            <div class="parent">
                <el-col class="fileRecords">
                    <FileRecords/>
                </el-col>
            </div>
        </el-container>
    </el-container>
</template>

<script>
    import FileRecords from "@/components/FileRecords.vue";
    import DataSideBar from "@/components/DataSideBar.vue";

    export default {
        name: 'FileData',
        computed: {
            isLogin() {
                return this.$store.state.userInfo.isLogin
            },
            isAppLoading() {
                return this.$store.state.isPageLoading
            }
        },
        components: {
            DataSideBar,
            FileRecords
        }
    }

</script>

<style>
    /*.sidebar{*/
    /*    width:20%;*/
    /*    height:auto;*/
    /*    margin-top:1px;*/
    /*}*/
    .header {
        background-color: lightblue;
        color: #333;
        line-height: 60px;
    }
    .file-page {
        min-height: 500px
    }

    .fileRecords {
        display: inline-block;
        padding-left: 0px;
    }

    .parent {
        text-align: center;
        width: 100%
    }
</style>
