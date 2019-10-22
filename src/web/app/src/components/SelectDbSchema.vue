<template>
  <div>
    <div class='card-container' v-for='dbSchema in dbSchemas' v-bind:key='dbSchema.tableName'>
      <div v-on:click='select(dbSchema)'>
        <el-card class='box-card' shadow='hover'>
          <h1>{{ dbSchema.name }}</h1>
          <ul>
            <li v-for='field in dbSchema.fieldMetaDataList' v-bind:key='field.name'>
              {{ field.name }}
            </li>
          </ul>
        </el-card>
      </div>
    </div>
  </div>
</template>

<script>
  export default {
    name: "SelectDbSchema",
    mounted: function () {
      this.$store.dispatch('fetchDBMetaDataEntities');
    },
    computed: {
      dbSchemas: function () {
        return this.$store.state.dbMetaData.entities;
      }
    },
    methods: {
      select: function (dbSchema) {
        this.$store.commit('setDbSchema', dbSchema);
      }
    }
  }
</script>

<style scoped>
  .card-container {
    display: inline-block;
  }

  .box-card {
    width: 480px;
    height: 480px;
    margin-right: 20px;
  }

</style>