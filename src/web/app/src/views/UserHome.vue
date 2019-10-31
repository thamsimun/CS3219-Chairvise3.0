<template>
    <el-alert v-if="!isLogin" title="Please login to view user profile" type="error" show-icon
              class="errorMsg"/>
    <el-container v-else-if="isLogin">
        <el-container>
            <el-aside class="sidebar" width="250px">
                <DataSideBar/>
            </el-aside>
        </el-container>
        <el-container>
            <el-col class="welcome" :span="14">
                <WelcomeSection/>
            </el-col>
        </el-container>
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
    .sidebar {
        padding-left:10px;
        margin-top: 5px;
    }

    .welcome {
        height:600px;
        background-color: lightblue;
    }

</style>