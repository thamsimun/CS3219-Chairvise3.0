<template>
  <div>
    <h3 id="general-guide">Getting started with ChairVisE3.0</h3>
    <el-collapse>
      <el-collapse-item title="Get Started!">
        <ol>
          <li>Log in using your Google account</li>
          <li>Upload any number of csv files under "Files" section in "My Data".</li>
          <li>Create a presentation and add visualizations using the data uploaded.</li>
          <li>Share your presentation with others to view or edit.</li>
          <img alt="Diagram" src="@/assets/GeneralGuide.png">
        </ol>
      </el-collapse-item>
    </el-collapse>
    <br>
    <h3 id="features-available">Available Visualisations:</h3>
    <el-collapse>
      <el-collapse-item title="Visualisations">
        <ul>
          <li>Word cloud (Submission)</li>
          <li>Rank authors (Submission)</li>
          <li>Rank country (Submission)</li>
          <li>Rank organization (Submission)</li>
          <li>Rank organizations for accepted Submissions</li>
          <li>Score Distribution (Review)</li>
          <li>Weighted evaluation score statistic summary (Review)</li>
          <li>Reviewer Expertise Level Statistic Summary</li>
          <li class="more_detail">More options can be found under "Presentations" in "My Data".</li>
        </ul>
      </el-collapse-item>
    </el-collapse>
  </div>

</template>

<script>
  export default {
    name: "FeatureGuide",
  }
</script>

<style scoped>
  img {
    width: 100%;
    height: auto;
  }

  h3 {
    margin: 15px 0 5px 0;
  }

  .more_detail {
    font-style: italic;
    color: #777777;
  }
</style>
