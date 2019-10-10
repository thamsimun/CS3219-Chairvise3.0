<template>
  <div>
    <FeatureGuide/>
    <DetailedFeatureGuide/>
  </div>
</template>

<script>
  import MappingTool from "@/components/MappingTool.vue";
  import Papa from "papaparse";
  import {REVIEW_DATE_DAY_FIELD, REVIEW_DATE_TIME_FIELD, REVIEW_TABLE_ID} from "@/common/const"
  import {deepCopy} from "@/common/utility"
  import PredefinedMappings from "@/store/data/predefinedMapping"
  import moment from "moment"
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
