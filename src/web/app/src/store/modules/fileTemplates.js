import axios from 'axios';

export default {
  state: {
    templates: [],
    templatesStatus: {
      isLoading: false,
      isApiError: false,
      apiErrorMsg: ''
    },
    chosenTemplate: null
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
    },
    selectTemplate(state, payload) {
      state.chosenTemplate = payload;
    }
  },
  actions: {
    async fetchFileTemplates({commit}) {
      commit('setTemplatesLoading', true);
      try {
        const response = await axios.get('/api/file/mapping');
        console.log(response);
        const templates = response.data;
        commit('setTemplates', templates);
      } catch (e) {
        commit('setTemplatesApiError', e.toString());
      } finally {
        commit('setTemplatesLoading', false);
      }
    },

    // file template to be saved should be a valid template
    async saveFileTemplate({commit}, template) {
      commit('setTemplatesLoading', true);
      try {
        await axios.post('/api/file/mapping', {
          ...template
        })
      } catch (e) {
        commit('setTemplatesApiError', e.toString());
      } finally {
        commit('setTemplatesLoading', false);
      }
    }
  }
}
