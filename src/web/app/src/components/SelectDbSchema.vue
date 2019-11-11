<template>
  <div class='card-container'>
    <div v-for='dbSchema in dbSchemas' :key='dbSchema.name'>
      <div @click='select(dbSchema)'>
        <el-card class='box-card' shadow='hover'>
          <h1>{{ dbSchema.name }}</h1>
        </el-card>
      </div>
    </div>

  </div>
</template>

<script>
  export default {
    name: "SelectDbSchema",
    mounted() {
      this.$store.dispatch('fetchDBMetaDataEntities');
    },
    computed: {
      dbSchemas() {
        return this.$store.state.dbMetaData.entities;
      }
    },
    methods: {
      select(dbSchema) {
        this.$store.commit('setDbSchema', dbSchema);
      }
    }
  }
</script>

<style scoped>
  .box-card {
    min-width: 500px;
    width: auto;
    height: auto;
    margin: 50px;
    text-overflow: ellipsis;
    text-align: center;
    color: #409EFF;
    background-color: #EBEEF5;
    cursor: pointer;
  }

  .card-container {
    width: 100%;
    justify-content: center;
    align-items: center;
    display: flex;
    flex-direction: column;
  }
</style>