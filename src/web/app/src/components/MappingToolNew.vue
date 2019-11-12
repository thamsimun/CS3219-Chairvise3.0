<template>
	<div>
		<div>
			<h1 style='text-align:center;font-size:28px;margin:0 0 1% 0;'>Your columns</h1>
			<div id='pick-cols'>
				<!--   List out all the fields in the pool, which is all the headers obtained from the csv    -->
				<el-button class='column-select' type='primary' v-for='(field, index) in pool' :key='index'
				           @click='addToSelected(field)'>{{ field }}
				</el-button>
			</div>
		</div>
		<!-- New -->
		<div id='map'>
			<div class='block' v-for='(element, index) in fieldMetaData' :key='index'>
				<div class='header'>{{ element.name }}</div>
				<el-select size='mini' value='name' v-model=transformations[index] value-key='name'>
					<el-option
									v-for='item in options[element.type]'
									:label='item.name'
									:key='item.name'
									:value='item'>
					</el-option>
				</el-select>
				<draggable class='assign' :list='mappingList[index]' group='fields'>
					<div class='item' v-for='(item, listIndex) in mappingList[index]' :key='listIndex' @dblclick='removeFromSelected(index, listIndex)'>
						{{ item }}
					</div>
				</draggable>
			</div>
		</div>

		<div id='controls'>
			<SaveTemplateDialog :isOldTemplate=isOldTemplate v-bind:template='currentTemplate'></SaveTemplateDialog>
			<el-button class='btn-complete' plain type='success' v-on:click='submit'>Complete import</el-button>
		</div>
	</div>
</template>

<script>
	import draggable from 'vuedraggable';
	import SaveTemplateDialog from './SaveTemplateDialog';
	import _ from 'lodash';
	import op from '../store/data/predefinedTransformations';
	import {distribute, getTransformations, removeItem} from '../common/utility';

	export default {
		name: 'MappingToolNew',
		components: {
			draggable,
			SaveTemplateDialog
		},
		data() {
			const chosenTemplate = this.$store.state.fileTemplates.chosenTemplate;

			return {
				dialogFormVisible: false,
				newTemplateDetails: {
					name: '',
					description: ''
				},
				options: op,
				transformations: !chosenTemplate
					? []
					: getTransformations(
						this.$store.state.fileTemplates.chosenTemplate.transformations,
						this.$store.state.dataMappingNew.data.fieldMetaData,
						op
					),
				mappingList: !chosenTemplate
					? this.$store.state.dataMappingNew.data.fieldMetaData.map(() => [])
					: _.cloneDeep(this.$store.state.fileTemplates.chosenTemplate.mappingList)
			};
		},
		computed: {
			pool() {
				return _.cloneDeep(this.$store.state.dataMappingNew.data.pool); // Pool data
			},
			rawData() {
				return _.cloneDeep(this.$store.state.dataMappingNew.data.rawData);  // Raw data
			},
			fieldMetaData() {
				return _.cloneDeep(this.$store.state.dataMappingNew.data.fieldMetaData); // Array of objects containing metadata
			},
			dbSchemaName() {
				return _.cloneDeep(this.$store.state.dataMappingNew.data.dbSchemaName); // Schema name
			},
			errors() {
				return this.$store.state.dataMappingNew.error;
			},
			messages() {
				return this.$store.state.dataMappingNew.messages;
			},
			currentTemplate() {
				return {
					transformations: this.transformations.map(obj => !obj ? undefined : obj.name),
					mappingList: _.clone(this.mappingList)
				};
			},
			isOldTemplate() {
				const chosenTemplate = this.$store.state.fileTemplates.chosenTemplate;
				console.log(chosenTemplate);
				console.log(this.$data.transformations);
				console.log(this.$data.mappingList);

				if (!chosenTemplate) {
					// user skipped selecting a template, trivially a new template
					return false;
				}
				// check whether the current template were any different that the chosen template earlier
				return _.isEqual(chosenTemplate.transformations.map(x => x), this.$data.transformations.map(obj => !obj ? undefined : obj.name)) &&
					_.isEqual(chosenTemplate.mappingList, this.$data.mappingList);
			}
		},
		methods: {
			submit() {
				// make sure that user has picked all transformations
				if (this.transformations.length !== this.mappingList.length) {
					this.$store.commit('setError', 'Please ensure all transformations are selected!');
					return;
				}

				// Iterate through each row, combine row and mappingList
				console.log('in submit');
				let toProcess = [];
				this.rawData.forEach(row => toProcess.push(_.pick(row, this.mappingList.flat())));
				this.$store.commit('setMappingList', _.cloneDeep(this.mappingList));
				this.$store.commit('setTransformations', this.transformations.map(obj => obj.value));
				this.$store.commit('processData', toProcess);

				if (this.errors.length === 0) {
					this.$store.dispatch('persistData');
				}
			},

			createNewTemplate() {
				const template = {
					transformations: this.$data.transformations.map(f => f.name),
					mappingList: _.cloneDeep(this.$data.mappingList),
				};
				this.$store.dispatch('saveFileTemplate', template);
			},

			addToSelected(field) {
				distribute(this.mappingList, field);
			},

			removeFromSelected: function (colIdx, lstIdx) {
				this.mappingList = removeItem(this.mappingList, colIdx, lstIdx);
			}
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
		}
	};
</script>

<style scoped>
	.db-field {
		border-style: solid;
		border-width: 5px;
		border-color: gold;
		margin: 5px;
		padding: 5px;
	}

	.user-field {
		border-style: solid;
		border-width: 5px;
		border-color: orange;
		margin: 5px;
		padding: 5px;
	}

	.table {
		display: table;
		width: 100%;
		table-layout: fixed;
		margin-top: 10px;
	}

	.table-cell {
		display: table-cell;
	}

	.wrapper {
		padding: 20px;
		flex-direction: column;
		height: 100%;
		width: 100%;
	}

	.btn-complete {
		margin: 20px;
	}

	ul {
		height: 500px;
		overflow: auto;
	}

	#controls {
		display: flex;
		justify-content: center;
	}

	#pick-cols {
		margin: auto;
		display: flex;
		justify-content: center;
		flex-wrap: wrap;
		padding-bottom: 50px;
		width: 500px;
	}

	.column-select {
		margin: 5px;
	}

	#map {
		display: flex;
		flex-direction: row;
		flex-wrap: wrap;
		justify-content: center;
	}

	.header {
		text-align: center;
		font-weight: bold;
		font-size: 24px;
		cursor: default;
	}

	.block {
		margin: 10px;
		text-align: center;
		min-height: 100px;
		cursor: grabbing;
	}

	.assign {
		color: #409EFF;
	}

	.item {
		cursor: grab;
	}
</style>