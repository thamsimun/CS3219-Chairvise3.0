<template>
    <el-container>
        <el-aside class="sidebar" width="250px" v-if="isLogin"></el-aside>
        <el-header class="header" style="text-align: right; font-size: 12px">
            <el-dropdown>
                <i class="el-icon-setting" style="margin-right: 20px"></i>
                <el-dropdown-menu slot="dropdown">
                    <el-dropdown-item>View</el-dropdown-item>
                    <el-dropdown-item>Add</el-dropdown-item>
                    <el-dropdown-item>Delete</el-dropdown-item>
                </el-dropdown-menu>
            </el-dropdown>
        </el-header>
        <el-col class="fileRecords">
            <FileRecords/>
        </el-col>
    </el-container>
</template>

<script>
    import FileRecords from "@/components/FileRecords.vue";

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
    }

    .fileRecords {
        padding-left: 350px;
        margin-top: 25px;
    }
</style>
