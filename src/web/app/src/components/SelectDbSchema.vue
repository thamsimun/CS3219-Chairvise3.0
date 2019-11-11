<template>
  <div class='card-container'>
    <div class='table-cell' v-for='dbSchema in dbSchemas' v-bind:key='dbSchema.tableName'
         v-on:click='select(dbSchema)'>
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
  .card-container {
    width: auto;
    display: inline-flex;
    table-layout: fixed;
    align-content: center;
  }

  .table-cell {
    display: table-cell;
    margin: 5px;
  }

  .box-card {
    min-width: 250px;
    max-width: 300px;
    width: auto;
    height: auto;
    overflow: hidden;
    text-overflow: ellipsis;

  }
</style>