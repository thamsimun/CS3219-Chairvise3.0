import {processMapping} from '@/store/helpers/processorNew.js';
import axios from 'axios';

export default {
  state: {
    hasDbSchemaSet: false,
    hasFileUploaded: false,
    hasChosenTemplate: false,
    data: {
      dbSchemaName: '',
      fieldMetaData: [],
      uploadedData: [],
      selectedFields: [],
      processedResult: []
    },
    error: []
  },

  mutations: {
    setDbSchema(state, {name, fieldMetaDataList}) {
      state.data.dbSchemaName = name;
      state.data.fieldMetaData = fieldMetaDataList;
      state.hasDbSchemaSet = true;
    },
    setUploadedData(state, data) {
      state.data.uploadedData = data;
      state.hasFileUploaded = true;
    },
    setSelectedFields(state, fields) {
      state.data.selectedFields = fields;
    },
    processData(state) {
      try {
        state.error = [];
        state.data.processedResult = processMapping(this.state.dataMappingNew.data.uploadedData,
          this.state.dataMappingNew.data.fieldMetaData);
      } catch (err) {
        state.error.push(err);
        state.data.processedResult = [];
      }
    }
  },

  actions: {
    async persistData({commit, state}) {
      commit('setPageLoadingStatus', true);
      let endpoint;

      switch (state.data.dbSchemaName) {
        case 'Author Record':
          endpoint = 'author';
          break;
        case 'Review Record':
          endpoint = 'review';
          break;
        case 'Submission Record':
          endpoint = 'submission';
          break;
      }

      await axios.post('/api/record/' + endpoint, state.data.processedResult)
        .then(() => {
          commit('setPageLoadingStatus', false);
          commit('setUploadSuccess', true);
        }).catch(e => {
          commit('setPageLoadingStatus', false);
          commit('setUploadSuccess', false);
          commit('setDataMappingError', e.toString());
        })
    }
  }
}
