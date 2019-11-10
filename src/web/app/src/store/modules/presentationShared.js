import axios from 'axios'

export default {
    state: {
        sharedPresentationList: [],
        sharedPresentationListStatus: {
            isLoading: true,
            isApiError: false,
            apiErrorMsg: '',
        },
        sharedPresentation: {
            id: '',
            name: '',
            creatorIdentifier: '',
        },
    },
    mutations: {

        setSharedPresentationListLoading(state, payload) {
            if (payload) {
                state.sharedPresentationListStatus.isApiError = false;
            }
            state.sharedPresentationListStatus.isLoading = payload;
        },

        setSharedPresentationListApiError(state, payload) {
            state.sharedPresentationListStatus.isApiError = true;
            state.sharedPresentationListStatus.apiErrorMsg = payload;
        },

        setSharedPresentationList(state, payload) {
            state.sharedPresentationList = payload;
        },
    },

    actions: {
        async getSharedPresentationList({commit}) {
            commit('setSharedPresentationListLoading', true);
            axios.get('/api/presentations/shared')
                .then(response => {
                    commit('setSharedPresentationList', response.data)
                })
                .catch(e => {
                    commit('setSharedPresentationListApiError', e.toString());
                })
                .finally(() => {
                    commit('setSharedPresentationListLoading', false);
                })
        }
    }
};