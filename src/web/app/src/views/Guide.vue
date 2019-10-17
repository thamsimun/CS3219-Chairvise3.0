<template>
  <div>
    <FeatureGuide/>
    <DetailedFeatureGuide/>
  </div>
</template>

<script>
  import DetailedFeatureGuide from "../components/homePageDetail/DetailedFeatureGuide";
  import FeatureGuide from "../components/homePageDetail/FeatureGuide";

  export default {
    name: "Guide",
    components: {
      FeatureGuide,
      DetailedFeatureGuide,
    }
  }
</script>

<style scoped>

</style>
