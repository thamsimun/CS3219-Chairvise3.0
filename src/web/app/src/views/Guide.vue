<template>
  <div>
    <div>
      <el-container>
        <el-aside style="width:260px">
          <ListOfGuide/>
        </el-aside>
        <el-main>
          <WelcomeMessage/>
          <FeatureGuide/>
          <br/>
          <DetailedFeatureGuide/>
        </el-main>
      </el-container>
    </div>
  </div>
</template>

<script>
  import ListOfGuide from "../components/guidePageDetail/ListOfGuide";
  import WelcomeMessage from "../components/guidePageDetail/WelcomeMessage";
  import DetailedFeatureGuide from "../components/guidePageDetail/DetailedFeatureGuide";
  import FeatureGuide from "../components/guidePageDetail/FeatureGuide";

  export default {
    name: "Guide",
    components: {
      ListOfGuide,
      WelcomeMessage,
      FeatureGuide,
      DetailedFeatureGuide
    }
  }
</script>

<style scoped>
  .el-aside {
    height: auto;
    min-width: 100px;
    border-right: 1px solid #e8e8e8 ;
  }
  .welcome {

  }
  .featureGuide {

  }
  .detailedFeatureGuide {

  }
</style>
