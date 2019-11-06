import axios from 'axios';

export default {
  state: {
    templates: [],
    templatesStatus: {
      isLoading: false,
      isApiError: false,
      apiErrorMsg: ''
    }
  },

  mutations: {
    setTemplatesLoading(state, isLoading) {
      state.templatesStatus.isApiError = false;
      state.templatesStatus.isLoading = isLoading;
    },
    setTemplatesApiError(state, payload) {
      state.templatesStatus.isApiError = true;
      state.templatesStatus.apiErrorMsg = payload;
    },
    setTemplates(state, payload) {
      state.templates = payload;
    }
  },
  actions: {
    async fetchFileTemplates({commit}) {
      commit('setTemplatesLoading', true);
      try {
        const templates = await axios.get('/api/file/mapping');
        commit('setTemplates', templates);
      } catch (e) {
        commit('setTemplatesApiError', e.toString());
      } finally {
        commit('setTemplatesLoading', false);
      }
    }
  }
}
