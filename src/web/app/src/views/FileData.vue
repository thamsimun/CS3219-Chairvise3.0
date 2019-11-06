<template>
    <el-alert v-if="!isLogin" title="Please login to view files added" type="error" show-icon
              class="errorMsg"/>
    <el-container v-else-if="isLogin">
        <el-container>
            <el-aside class="sidebar" width="250px">
                <DataSideBar/>
            </el-aside>
        </el-container>
        <el-container>
            <el-col class="fileRecords">
                <FileRecords/>
            </el-col>
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
    .header {
        background-color: lightblue;
        color: #333;
        line-height: 60px;
    }

    .sidebar {
        padding-left:10px;
        margin-top: 5px;
    }

    .fileRecords {
        padding-left: 150px;
        margin-top: 25px;
    }
</style>
