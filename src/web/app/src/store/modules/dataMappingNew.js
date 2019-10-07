export default {
	state: {
		hasDbSchemaSet: false,
		hasFileUploaded: false,
		hasChosenTemplate: false,
		data: {
			dbSchema: null,
			uploadedData: [],
			selectedFields: []
		},
		error :[]
	},

	mutations: {
		setDbSchema(state, dbSchema) {
			state.data.dbSchema = dbSchema;
			state.hasDbSchemaSet = true;
		},
		setUploadedData(state, data) {
			state.data.uploadedData = data;
			state.hasFileUploaded = true;
		},
		setSelectedFields(state, fields) {
			state.data.selectedFields = fields;
		}
	}
}
