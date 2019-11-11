<template>
    <el-alert v-if="!isLogin" title="Please login to view user profile" type="error" show-icon
              class="errorMsg"/>
    <el-container v-else-if="isLogin">
            <el-aside class="sidebar">
                <DataSideBar/>
            </el-aside>
            <el-col class="welcome" :span="14">
                <WelcomeSection/>
            </el-col>
    </el-container>
</template>

<script>
    import DataSideBar from "@/components/DataSideBar.vue";
    import WelcomeSection from "@/components/landingPageDetail/WelcomeSection.vue";
    export default {
        name: "UserHome",
        computed: {
            isLogin() {
                return this.$store.state.userInfo.isLogin
            },
            isAppLoading() {
                return this.$store.state.isPageLoading
            }
        },
        components: {
            WelcomeSection,
            DataSideBar
        }
    }
</script>

<style>
    .el-aside{
        width:20% !important;
    }
    .sidebar {
        height:100%;
    }

    .welcome {
        height:1000px;
        width:100%;
        background-color: lightblue;
    }

</style>