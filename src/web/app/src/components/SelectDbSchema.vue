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
    display: table;
    width: 100%;
    table-layout: fixed;
    align-content: center;
  }

  .table-cell {
    display: table-cell;
  }

  .box-card {
    width: 480px;
    height: 480px;
    margin: auto;
  }
</style>