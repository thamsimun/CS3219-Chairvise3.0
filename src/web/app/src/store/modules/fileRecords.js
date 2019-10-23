import axios from 'axios'

export default {
    state: {
        isDeleteSuccess: false,
        isDeleteFailure: false,
        fileList: [],
        fileListStatus: {
            isLoading: true,
            isApiError: false,
            apiErrorMsg: '',
        },
        fileRecord: {
            fileNumber: '',
            fileName: '',
            fileType: '',
        },
    },
    mutations: {

        clearDeleteSuccess(state) {
            state.isDeleteSuccess = false;
        },

        clearDeleteFailure(state) {
            state.isDeleteFailure = false;
        },

        clearFileRecord(state) {
            state.fileRecord = {
                fileNumber: '',
                fileName: '',
                fileType: '',
            }
        },

        setDeleteSuccess(state, success) {
            state.isDeleteSuccess = success;
        },

        setDeleteFailure(state, failure) {
            state.isDeleteFailure = failure;
        },

        setFileListLoading(state, payload) {
            if (payload) {
                state.fileListStatus.isApiError = false;
            }
            state.fileListStatus.isLoading = payload;
        },

        setFileListApiError(state, payload) {
            state.fileListStatus.isApiError = true;
            state.fileListStatus.apiErrorMsg = payload;
        },

        setFileList(state, payload) {
            state.fileList = payload;
        },

        //can be used in the future
        // addToFileList(state, payload) {
        // },


        deleteFromFileList(state, payload) {
            state.fileRecord = payload;
        },

        updateFileListWith(state, payload) {
            state.fileList = state.fileList.map(file => {
                if (file.id === payload.id) {
                    return payload
                }
                return file
            });
        },

        setFileRecord(state, payload) {
            state.fileRecord = payload;
        },

        // resetFileRecordState() {
        // },

        // setFileRecordField(state, {field, value}) {
        //     state.FileRecord[field] = value
        // },

    },

    actions: {
        async getFileList({commit}) {
            commit('setFileListLoading', true);
            axios.get('/api/file')
                .then(response => {
                    commit('setFileList', response.data)
                })
                .catch(e => {
                    commit('setFileListApiError', e.toString());
                })
                .finally(() => {
                    commit('setFileListLoading', false);
                })
        },

        async deleteFile({commit, state}) {
            axios.post('/api/file', {
                fileName: state.fileRecord.fileName,
                fileNumber: state.fileRecord.fileNumber,
                fileType: state.fileRecord.fileType,
            })
                .then(response => {
                    commit('setFileList', response.data);
                    commit('setDeleteSuccess', true);
                })
                .catch(e => {
                    commit('setDeleteFailure', true);
                })
                .finally(() => {
                    commit('setFileListLoading', false)
                });
        }
    }
};