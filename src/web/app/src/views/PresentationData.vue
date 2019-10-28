<template>
  <el-alert v-if="!isLogin" title="Please login to view presentations" type="error" show-icon
            class="errorMsg"/>
  <el-container v-else-if="isLogin">
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
    <el-col class="presentationCreatedByMe">
      <PresentationCreatedByMe/>
    </el-col>
  </el-container>
</template>

<script>
  import PresentationCreatedByMe from "@/components/PresentationCreatedByMe.vue";

  export default {
    name: 'PresentationData',
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
      PresentationCreatedByMe
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

  .presentationCreatedByMe {
    min-width: 300px;
    padding-left: 375px;
    margin-top: 0px;
  }
</style>