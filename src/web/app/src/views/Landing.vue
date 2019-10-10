<template>
  <div>
    <el-row>
      <el-col :span="14">
          <WelcomeSection/>
      </el-col>
      <el-col :span="10">
          <SignUpSection/>
      </el-col>
    </el-row>
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
    name: 'home',
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
</style>