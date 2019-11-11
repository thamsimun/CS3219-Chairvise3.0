<template>
  <div>
    <div>
      <el-container>
        <el-aside>
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
  // @ is an alias to /src
  import WelcomeMessage from '@/components/guidePageDetail/WelcomeMessage.vue'
  import FeatureGuide from '@/components/guidePageDetail/FeatureGuide.vue'
  import DetailedFeatureGuide from '@/components/guidePageDetail/DetailedFeatureGuide.vue'
  import ListOfGuide from '@/components/guidePageDetail/ListOfGuide.vue'

  export default {
    name: 'home',
    components: {
      WelcomeMessage,
      FeatureGuide,
      DetailedFeatureGuide,
      ListOfGuide
    }
  }
</script>
<style scoped>
  .el-aside {
    width: 250px;
  }

  .el-main {
    margin: 30px;
  }
</style>