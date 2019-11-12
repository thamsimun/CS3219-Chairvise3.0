import axios from 'axios';

export default {
  state: {
    templates: [],
    chosenTemplate: null
  },
  mutations: {
    setTemplates(state, payload) {
      state.templates = payload;
    },
    selectTemplate(state, payload) {
      state.chosenTemplate = payload;
    }
  },
  actions: {
    async fetchFileTemplates({commit}) {
      commit('setPageLoadingStatus', true);
      try {
        const response = await axios.get('/api/file/mapping');
        const templates = response.data;
        commit('setTemplates', templates);
      } catch (e) {
        commit('setError', e.toString());
      } finally {
        commit('setPageLoadingStatus', false);
      }
    },

    // file template to be saved should be a valid template
    async saveFileTemplate({commit}, template) {
      commit('setPageLoadingStatus', true);
      try {
        await axios.post('/api/file/mapping', template);
        commit('notifySuccess', 'Template saved!');
      } catch (e) {
        commit('setError', e.toString());
      } finally {
        commit('setPageLoadingStatus', false);
      }
    },

    async deleteFileTemplate({commit}, templateId) {
      commit('setPageLoadingStatus', true);
      try {
        await axios.delete('/api/file/mapping/' + templateId);
        commit('notifySuccess', 'Template deleted!');
      } catch (e) {
        commit('setError', e.toString());
      } finally {
        commit('setPageLoadingStatus', false);
      }
    }
  }
}
