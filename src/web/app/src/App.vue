<template>
  <el-container id="container" v-loading.fullscreen.lock="isAppLoading">
    <el-header id="header">
      <menu-bar></menu-bar>
    </el-header>
    <el-main id="main">
      <router-view/>
    </el-main>
  </el-container>
</template>

<script>
  import MenuBar from '@/components/MenuBar_v2.vue'
  // import Footer from '@/components/FooterBar.vue'

  export default {
    watch: {
      '$route'() {
        this.$store.dispatch('getAuthInfo');
      },
      'isFetchUserInfoError'() {
        if (!this.isFetchUserInfoError) {
          return
        }
        this.$notify.error({
          title: 'Auth request fail',
          message: this.$store.state.userInfo.apiErrorMsg,
          duration: 0
        });
      }
    },
    components: {
      'menu-bar': MenuBar,
      // 'footer': Footer,
    },
    computed: {
      isAppLoading() {
        return this.$store.state.isPageLoading
      },
      isFetchUserInfoError() {
        return this.$store.state.userInfo.isApiError
      }
    },
  }
</script>

<style>
  @import url('https://fonts.googleapis.com/css?family=Montserrat:300,400,500');

  #container, #header, #main, #footer {
    padding: 0;
  }

  html, body, #container, #main {
    height: 100%;
  }

  body {
    font-family: 'Montserrat', sans-serif;
  }
</style>

