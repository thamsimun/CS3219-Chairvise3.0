<template>
  <el-alert v-if="!isLogin" title="Please login to view presentations" type="error" show-icon
            class="errorMsg"/>
  <el-container v-else-if="isLogin">
    <el-aside style="min-width:200px; height:auto; margin-top:1px">
      <DataSideBar/>
    </el-aside>
    <el-container>
      <div class="parent">
        <el-col class="presentationCreatedByMe">
          <PresentationCreatedByMe/>
        </el-col>
      </div>
    </el-container>
  </el-container>
</template>

<script>
  import PresentationCreatedByMe from "@/components/PresentationCreatedByMe.vue";
  import DataSideBar from "@/components/DataSideBar.vue";

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
      DataSideBar,
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

  .parent {
    text-align: center;
    width: 100%;
    height: 100%;
  }

  .presentationCreatedByMe {
    display: inline-block;
    padding-left: 0px;
  }
</style>