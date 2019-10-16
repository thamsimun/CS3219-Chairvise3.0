import axios from 'axios'

export default {
  state: {
    isApiError: false,
    apiErrorMsg: '',
    isLogin: false,
    loginUrl: '',
    logoutUrl: '',
    userEmail: '',
    userNickname: '',
    userId: '',
    registerForm: {
      email: '',
      password: '',
    },
    registerFormStatus: {
      isLoading: false,
      // isApiError: false,
      // formErrorMsg: '',
    },
  },
  mutations: {
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
        state.userNickname = payload.userInfo.userNickname;
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
      console.log("asdfadfafdfdaf");
      axios.post('/api/auth...' , state.registerForm)
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