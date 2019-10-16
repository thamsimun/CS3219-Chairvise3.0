import axios from 'axios'

export default {
    state: {
        fileList: [],
        fileListStatus: {
            isLoading: true,
            isApiError: false,
            apiErrorMsg: '',
        },
        fileRecord: {
            id: '',
            name: '',
            creatorIdentifier: '',
        },
    },
    mutations: {
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

        //can be used in the future
        // deleteFromFileList(state, payload) {
        // },

        updateFileListWith(state, payload) {
            state.fileList = state.fileList.map(file => {
                if (file.id === payload.id) {
                    return payload
                }
                return file
            });
        },

        setFileRecord(state, payload) {
            state.FileRecord = payload;
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
        }
    }
};