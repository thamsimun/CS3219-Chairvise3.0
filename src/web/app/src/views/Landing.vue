<template>
  <div>
    <el-container class="landing">
      <el-col class="welcome" :span="14">
          <WelcomeSection/>
      </el-col>
      <el-col class="register" :span="10">
          <SignUpSection/>
      </el-col>
    </el-container>
  </div>
</template>

<script>
  // @ is an alias to /src
  // import WelcomeMessage from '@/components/homePageDetail/WelcomeMessage.vue'
  // import FeatureGuide from '@/components/homePageDetail/FeatureGuide.vue'
  // import DetailedFeatureGuide from '@/components/homePageDetail/DetailedFeatureGuide.vue'
  // import ListOfGuide from '@/components/homePageDetail/ListOfGuide.vue'
  import SignUpSection from "@/components/landingPageDetail/SignUpSection.vue";
  import WelcomeSection from "@/components/landingPageDetail/WelcomeSection";

  export default {
    name: 'landing',
    components: {
      WelcomeSection,
      // WelcomeMessage,
      // FeatureGuide,
      // DetailedFeatureGuide,
      // ListOfGuide
      SignUpSection
    }
  }
</script>
<style scoped>
  .landing {
    height:600px;
    background-color: lightblue;
  }
  .welcome {
    /*border-width: 1px;*/
    /*border-right-style: solid;*/
    /*border-right-color: #777777;*/
  }
  .register {}

</style>