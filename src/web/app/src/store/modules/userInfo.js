import axios from 'axios'
import VueCookies from 'vue-cookies'
import {sha256} from "js-sha256";


export default {
  state: {
    isApiError: false,
    apiErrorMsg: '',
    isLogin: false,
    loginUrl: '',
    logoutUrl: '',
    userEmail: '',
    userId: '',
    hashedPassword: '',
    registerForm: {
      userEmail: '',
      userPassword: '',
    },
    registerFormStatus: {
      isLoading: false,
      // isApiError: false,
      // formErrorMsg: '',
    },
  },

  mounted: {

  },

  mutations: {
    hashPassword(state) {
      if (state.registerForm.userPassword === '') {
        return;
      }
      sha256(state.registerForm.userPassword);
      var hash = sha256.create();
      state.hashedPassword = hash.toString();
    },

    setCookies(state) {
      VueCookies.set('userEmail', state.registerForm.userEmail, '1d', "/");
      VueCookies.set('userPassword', state.hashedPassword, '1d', "/");
    },

    clearCookies() {
      VueCookies.set('userEmail', "", '1d', "/");
      VueCookies.set('userPassword', "", '1d', "/");
    },

    setAuthInfoApiRequestFail(state, payload) {
      state.isApiError = true;
      state.apiErrorMsg = payload;
    },

    setAuthInfo(state, payload) {
      state.isLogin = payload.isLogin;
      state.loginUrl = payload.loginUrl;
      state.logoutUrl = payload.logoutUrl;

      if (payload.isLogin) {
        state.userEmail = payload.userInfo.userEmail;
        state.userNickname = payload.userInfo.userPassword;
        state.userId = payload.userInfo.userId;
      }
    },

    // setRegisterFormApiError(state, msg) {
    //   state.registerFormStatus.isApiError = true;
    //   state.registerFormStatus.apiErrorMsg = msg
    // },
    //
    // setRegisterForm(state, payload) {
    //   state.registerForm = payload;
    // },
    //
    // resetRegisterForm(state) {
    //   state.registerForm.email = '';
    //   state.registerForm.password = '';
    //   state.registerForm.isLoading = false;
    //   state.registerForm.isApiError = false;
    //   state.registerForm.apiErrorMsg = '';
    // },

    setRegisterFormField(state, {field, value}) {
      state.registerForm[field] = value;
    }

  },
  actions: {
    async setCookies({commit}) {
      commit('hashPassword');
      commit('setCookies');
    },

    async clearCookies({commit}) {
      commit('clearCookies');
    },

    async getAuthInfo({commit}) {
      commit('setPageLoadingStatus', true);
      const urlToGetBack = encodeURI(window.location.href);
      axios.get('/api/auth?redirectUrl=' + urlToGetBack)
        .then(response => {
          commit('setAuthInfo', response.data)
        })
        .catch(e => {
          commit('setAuthInfoApiRequestFail', e.toString());
        })
        .finally(() => {
          commit('setPageLoadingStatus', false)
        })
    },
    async addUser({commit, state}) {
      commit('setPageLoadingStatus', true);
      // TODO: hook up URL with the backend
      axios.post('/api/auth')
          .then(response => {
            commit('setAuthInfo', response.data)
          })
          .catch(e => {
            commit('setAuthInfoApiRequestFail', e.toString());
          })
          .finally(() => {
            commit('setPageLoadingStatus', false)
          })
    }
  }
};