import {applyTransformations} from '@/store/helpers/processorNew.js';
import axios from 'axios';

export default {
  state: {

    hasDbSchemaSet: false,
    hasFileUploaded: false,
    hasChosenTemplate: false,
    hasHeader: true,
    data: {
      dbSchemaName: '',
      fieldMetaData: [],
      rawData: [],
      fileName: '',
      uploadedData: [],
      processedResult: [],
      mappingList: [],
      transformations: [],
      pool: []
    },
    error: [],
    messages: []
  },

  mutations: {
    setHasHeader(state, payload) {
      state.hasHeader = payload;
    },
    setDbSchema(state, {name, fieldMetaDataList}) {
      state.data.dbSchemaName = name;
      state.data.fieldMetaData = fieldMetaDataList;
      state.hasDbSchemaSet = true;
    },
    setRawData(state, data) {
      state.data.rawData = data;
    },
    setFileName(state, fileName) {
      state.data.fileName = fileName
    },
    setMappingList(state, mappingList) {
      state.data.mappingList = mappingList;
    },
    setTransformations(state, transformations) {
      state.data.transformations = transformations;
    },
    setMappingError(state, err) {
      state.error.length = 0;
      state.error.push(err);
    },
    setPool(state, fields) {
      state.data.pool = fields;
    },
    processData(state, data) {
      try {
        state.error = [];
        state.data.processedResult =
          applyTransformations(
            data,
            state.data.mappingList,
            state.data.transformations,
            this.state.dataMappingNew.data.fieldMetaData
          );
      } catch (err) {
        state.error.push(err);
        state.data.processedResult = [];
      }
    },
    notifySuccess(state, message) {
      state.messages.length = 0;
      state.messages.push(message);
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

      await axios.post('/api/record/' + endpoint, {
        'fileName': state.data.fileName,
        'records': state.data.processedResult
      })
        .then(() => {
          commit('setPageLoadingStatus', false);
          commit('notifySuccess', 'Upload success!');
        }).catch(e => {
          commit('setPageLoadingStatus', false);
          commit('setMappingError', e.toString());
        })
    }
  }
}
