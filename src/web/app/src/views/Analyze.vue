<template>
  <div>
    <div>
      <el-container>
        <el-aside class="sidebar" width="250px" v-if="isLogin">
          <DataSideBar/>
        </el-aside>
        <el-main>
          <presentation-brief :id="id"/>
          <section-list-panel :presentationId="id"/>
        </el-main>
      </el-container>
    </div>
  </div>
</template>

<script>
  import ChartJsPluginDataLabels from 'chartjs-plugin-datalabels';
  import ListOfPresentation from '@/components/ListOfPresentation.vue';
  import PresentationBrief from "@/components/PresentationBrief.vue";
  import SectionListPanel from "@/components/SectionListPanel.vue";
  import DataSideBar from "@/components/DataSideBar.vue";


  export default {
    name: 'Analyze',
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
      PresentationBrief,
      ListOfPresentation,
      SectionListPanel,
      ChartJsPluginDataLabels
    }
  }
</script>

<style>
  .sidebar {
    padding-left:10px;
  }
</style>