<template>
	<el-container style='height:100%;'>
		<el-aside style="width:20%; height:auto; margin-top:1px">
			<DataSideBar/>
		</el-aside>
		<div class="container">
			<el-col style="padding:10px">
				<div class='wrapper'>
					<div v-if='schemaNotSelected'>
						<h1 class="head">Upload a New File</h1>
						<h1 style='font-size:28px;'>Select your record type:</h1>
						<SelectDbSchema></SelectDbSchema>
					</div>
					<div id='file-upload' v-else-if='fileNotUploaded'>
						<h1 class="head">File Upload</h1>
						<div class="nav-buttons">
							<div class='btn-div'>
								<el-button class='back-btn' type='plain' icon='el-icon-back' circle
                           @click='clearSelectedSchema'></el-button>
								<el-button style='visibility:hidden;'></el-button>
							</div>
						</div>
						<div style='text-align:center;'>
							<h3 style='margin-bottom: 10px;'> Does your file have headers? </h3>
							<el-switch v-model="hasHeaders" active-text="Yes, I have headers"
                         inactive-text="No, I can identify my own"
                         style='margin-bottom: 50px;'
							></el-switch>
							<el-upload drag action='' :auto-upload='false' :show-file-list='false' :multiple='false'
                         :on-change='fileUploadHandler'>
								<i class='el-icon-upload'></i>
								<div class='el-upload__text'>Drop file here or <em>click to upload</em></div>
								<div class='el-upload__tip' slot='tip'>Please upload .csv files with filed names.</div>
							</el-upload>
						</div>
					</div>
					<div v-else-if='templateNotSelected'>
						<h1 class="head">Pick a Template</h1>
						<div class="nav-buttons">
							<div class='btn-div'>
								<el-button class='back-btn' type='plain' icon='el-icon-back' circle @click='clearRawData'></el-button>
								<el-button class='forward-btn' type='plain' icon='el-icon-right' circle
                           @click='skipTemplate'></el-button>
							</div>
						</div>
						<SelectTemplateTable></SelectTemplateTable>
					</div>
					<div v-else>
						<h1 class='head'>Map Fields</h1>
						<div class="nav-buttons">
							<div class='btn-div'>
								<el-button class='back-btn' type='plain' icon='el-icon-back' circle @click='clearTemplate'></el-button>
							</div>
						</div>
						<mapping-tool-new ref='mapTool'></mapping-tool-new>
					</div>
				</div>
			</el-col>
		</div>
	</el-container>
</template>

<script>
	import MappingToolNew from '../components/MappingToolNew.vue';
	import SelectDbSchema from "../components/SelectDbSchema";
	import SelectTemplateTable from '../components/SelectTemplateTable';
	import DataSideBar from "../components/DataSideBar.vue";
	import _ from 'lodash';
	import Papa from 'papaparse';
	import {formDataWithHeaders} from "../common/utility";

	export default {
		name: "ImportDataNew",

		data: function () {
			return {
				pool: [],
				selected: [],
				useNoTemplate: false,
				hasHeaders: true,
			}
		},
		computed: {
			errors() {
				return this.$store.state.dataMappingNew.error;
			},
			messages() {
				return this.$store.state.dataMappingNew.messages;
			},
			dbSchemas() {
				return this.$store.state.dbMetaData.entities;
			},
			schemaNotSelected() {
				return this.$store.state.dataMappingNew.data.dbSchemaName === '';
			},
			fileNotUploaded() {
				return this.$store.state.dataMappingNew.data.rawData.length === 0;
			},
			templateNotSelected() {
				return !this.$data.useNoTemplate && _.isEmpty(this.$store.state.fileTemplates.chosenTemplate);
			},
			// hasHeader: {
			// 	get() {
			// 		return this.$store.state.dataMappingNew.hasHeader;
			// 	},
			// 	set(value) {
			// 		this.$store.commit('setHasHeader', value)
			// 	},
			// }
		},
		methods: {
			fileUploadHandler(file) {
				this.$store.commit('setPageLoadingStatus', true);

				// Parse the raw data
				Papa.parse(file.raw, {
					skipEmptyLines: true,
					header: false,
					complete: ({data}) => {
						const headers = this.$data.hasHeaders
							                 ? data.shift()
							                 : _.first(data).map((x, index) => `Column ${index + 1}`);
						// Set the headers obtained to Pool
						this.$store.commit('setPool', headers);
						// Data is stored as {Col1: data" , Col2: data} objects that represent row
						this.$store.commit('setRawData', formDataWithHeaders(headers, data));
						this.$store.commit('setFileName', file.name);
						this.$store.commit('setPageLoadingStatus', false);
					}
				})
			},
			clearSelectedSchema() {
				this.$store.commit('setDbSchema', {name: '', fieldMetaDataList: []});
			},
			clearRawData() {
				this.$store.commit('setRawData', []);
			},
			skipTemplate() {
				this.$data.useNoTemplate = true;
			},
			clearTemplate() {
				this.$data.useNoTemplate = false;
				this.$store.commit('selectTemplate', null);
			},
		},
		watch: {
			errors: function (errList) {
				if (errList.length !== 0) {
					this.$notify.error({
						title: 'Error',
						message: errList.join('\n')
					});
				}
			},
			messages: function (msgList) {
				if (msgList.length !== 0) {
					this.$notify.success({
						title: 'Success',
						message: msgList.join('\n')
					});
				}
			}
		},
		components: {
			MappingToolNew,
			SelectDbSchema,
			SelectTemplateTable,
			DataSideBar,
		}
	}
</script>

<style scoped>
	.container {
		width: 100%;
	}

	.head {
		text-align: center;
		font-size: 56px;
		font-weight: bold;
		min-width: 800px;
	}

	.wrapper {
		height: 100%;
		display: flex;
		justify-content: center;
	}

	h1 {
		text-align: center;
		padding: 20px;
		margin: 0;
	}

	.nav-buttons {
		margin: 0;
		display: flex;
		justify-content: center;
	}

	.back-btn {
		float: left;
	}

	.forward-btn {
		float: right;
	}

	#file-upload {
		justify-content: center;
	}

	.btn-div {
		width: 600px;
	}

</style>