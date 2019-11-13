<template>
    <el-alert v-if="!isLogin" title="Please login to view presentations" type="error" show-icon
              class="errorMsg"/>
    <el-container v-else-if="isLogin">
        <el-aside style="width:200px; height:auto; margin-top:1px">
            <DataSideBar/>
        </el-aside>
        <el-container>
            <div class="parent">
                <el-col class="sharedPresentation">
                    <SharedPresentation/>
                </el-col>
            </div>
        </el-container>
    </el-container>
</template>

<script>
    import SharedPresentation from "@/components/SharedPresentation.vue";
    import DataSideBar from "@/components/DataSideBar.vue";


    export default {
        name: 'PresentationShared',
        props: {
            id: String,
        },
        computed: {
            isLogin() {
                return this.$store.state.userInfo.isLogin
            },
            isAppLoading() {
                return this.$store.state.isPageLoading
            }
        },
        components: {
            SharedPresentation,
            DataSideBar
        }
    }
</script>

<style>
    .header {
        background-color: lightblue;
        color: #333;
        line-height: 60px;
    }

    /*.sidebar {*/
    /*    padding-left:10px;*/
    /*    margin-top: 5px;*/
    /*}*/

    .parent {
        text-align: center;
        width: 100%;
        height: 100%;
    }

    .sharedPresentation {
        display: inline-block;
        padding-left: 0px;
    }
</style>