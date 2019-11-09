import axios from 'axios'
import VueCookies from 'vue-cookies'
import {sha256} from "js-sha256";


export default {
  state: {
    isSignUpFailed: false,
    isInvalidUserOrPass: false,
    isApiError: false,
    apiErrorMsg: '',
    isLogin: false,
    loginUrl: '',
    logoutUrl: '',
    userEmail: '',
    userId: '',
    registerForm: {
      userEmail: '',
      userPassword: '',
      hashedPassword: '',
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
    clearSignUpFailure(state) {
      state.isSignUpFailed = false;
    },

    setSignUpFailed(state) {
      state.registerForm.userPassword = '';
      state.registerForm.userEmail = '';
      state.isSignUpFailed = true;
    },

    clearInvalidCredentials(state) {
      state.isInvalidUserOrPass = false;
    },

    hashPassword(state) {
      if (state.registerForm.userPassword === '') {
        return;
      }
      var hash = sha256.create();
      hash.update(state.registerForm.userPassword);
      state.registerForm.hashedPassword = hash.hex();
    },

    setCookies(state) {
      VueCookies.set('userEmail', state.registerForm.userEmail, '1d', "/");
      VueCookies.set('userPassword', state.registerForm.hashedPassword, '1d', "/");
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

    invalidCredentialResult(state) {
      state.registerForm.userPassword = '';
      state.registerForm.userEmail = '';
      state.isInvalidUserOrPass = true;
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
      const urlToGetBack = encodeURI(window.location.href);
      // TODO: hook up URL with the backend
      axios.post('/api/auth?redirectUrl=' + urlToGetBack)
          .then(response => {
            commit('setAuthInfo', response.data)
          })
          .catch(e => {
            commit('setSignUpFailed');
          })
          .finally(() => {
            commit('setPageLoadingStatus', false)
          })
    },
    async logUser({commit}) {
      commit('setPageLoadingStatus', true);
      const urlToGetBack = encodeURI(window.location.href);
      axios.get('/api/auth?redirectUrl=' + urlToGetBack)
          .then(response => {
            commit('setAuthInfo', response.data)
          })
          .catch(e => {
            commit('invalidCredentialResult');
          })
          .finally(() => {
            commit('setPageLoadingStatus', false)
          })
    },
  }
};