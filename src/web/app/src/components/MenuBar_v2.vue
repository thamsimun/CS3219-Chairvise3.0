<template>
  <el-menu mode="horizontal" router :default-active="menuDefaultActive">

    <el-menu-item index="/landing" >
      <el-image :src="appLogo" style="width: 30px; height: 30px" ></el-image>
    </el-menu-item>

<!--    <el-menu-item index="/userHome" v-if="isLogin">-->
<!--      <el-image :src="appLogo" style="width: 30px; height: 30px" ></el-image>-->
<!--    </el-menu-item>-->

    <el-menu-item index="/guide">Guide</el-menu-item>
    <el-menu-item index="/explore">Explore</el-menu-item>
<!--    <el-menu-loginEmail :disabled="!isLogin"></el-menu-loginEmail>-->
<!--    <el-menu-loginPassword :disabled="!isLogin"></el-menu-loginPassword>-->

    <el-menu-item class="no_click" style="float:right" v-if="isLogin" :disabled="!isLogin">
      <el-avatar id="avatar" :size="50" :src="userLogo" style="background-color: white" > </el-avatar>
    </el-menu-item>

    <el-menu-item class="no_click" index="/logout" style="float: right" v-if="isLogin" @click="logout"
                  v-loading.fullscreen.lock="isFullscreenLoading">
      <el-button type="success" plain>Logout ({{ userEmail }})</el-button>
    </el-menu-item>

    <el-menu-item index="/userHome" :disabled="!isLogin">My Data</el-menu-item>

    <el-menu-item class="no_click" index="/login" style="float:right" v-if="!isLogin" :disabled="isApiError"
                  @click="login"
                  v-loading.fullscreen.lock="isFullscreenLoading">
      <el-button round type="success" plain :disabled="isApiError">Login</el-button>
    </el-menu-item>
  </el-menu>
</template>

<script>
  const appLogo = require('../assets/logo.png');
  const userLogo = require('../assets/user_grey.png');
  export default {
    name: 'MenuBar',
    data() {
      return {
        appLogo,
        userLogo,
        isFullscreenLoading: false,
      }
    },
    computed: {
      menuDefaultActive() {
        // need to active the menu item if sub path is used. e.g. /analyze/new
        if (this.$route.path.includes('/analyze')) {
          return '/analyze'
        }
        return this.$route.path
      },
      isLogin() {
        return this.$store.state.userInfo.isLogin
      },
      userEmail() {
        return this.$store.state.userInfo.userEmail
      },
      isApiError() {
        return this.$store.state.userInfo.isApiError
      }
    },
    methods: {
      login() {
        this.$store.dispatch('setCookies');
        // enter full screen loading and wait browser to redirect to google login page
        this.$data.isFullscreenLoading = true;
        window.location.href = this.$store.state.userInfo.loginUrl
      },
      async logout() {
        await this.$store.dispatch('clearCookies');
        // enter full screen loading and wait browser to redirect to google login page
        this.$data.isFullscreenLoading = true;
        await this.setLogout();
        this.returnToHome();
      },
      returnToHome() {
        window.location.href = ('/');
      },
      setLogout() {
          window.location.href = this.$store.state.userInfo.logoutUrl;
      }
    }
  }
</script>

<style>
  .no_click:hover {
    cursor: default;
  }
</style>