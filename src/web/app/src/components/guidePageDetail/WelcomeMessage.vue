<template>
  <div>
    <!--can change to a better logo next time-->
    <img alt="Vue logo" src="@/assets/logo.png">
    <h2>Welcome to ChairVisE3.0</h2>
    <h3>
      ChairVisE3.0 is a Conference Data Visualization Management System that enables conference program
      chairpersons to visualize and share conference submission statistics.

      We parse information in different formats, assisting the user in obtaining the most value out of the
      information uploaded through data visualisation.
      We also support sharing and exporting of such visualizations
    </h3>
  </div>
</template>

<script>
  export default {
    name: 'WelcomeMessage',
    props: {
      msg: String
    },
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
  h3 {
    font-weight: normal;
    text-align: justify;
  }

  img {
    width: 30%;
  }

  div {
    text-align: center;
  }
</style>
