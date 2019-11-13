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
				<div class='db-header'>{{ element.name }}</div>
				<el-select size='mini' value='name' v-model=transformations[index] value-key='name'>
					<el-option
									v-for='item in options[element.type]'
									:label='item.name'
									:key='item.name'
									:value='item'>
					</el-option>
				</el-select>
				<draggable class='assign' :list='mappingList[index]' group='fields'>
					<div class='item' v-for='(item, listIndex) in mappingList[index]' :key='listIndex'
               @dblclick='removeFromSelected(index, listIndex)'>
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
			const chosenTemplate = _.cloneDeep(this.$store.state.fileTemplates.chosenTemplate);
			const defaultMappingList = this.$store.state.dataMappingNew.data.fieldMetaData.map(() => []);
			let mappings;
			if (chosenTemplate && chosenTemplate.mappingList.length < defaultMappingList) {
				let fill = Array(defaultMappingList.length - chosenTemplate.mappingList.length).fill([]);
				mappings = chosenTemplate.mappingList.concat(fill);
			} else if (chosenTemplate) {
				mappings = chosenTemplate.mappingList.slice(0, defaultMappingList.length);
			} else {
				mappings = defaultMappingList;
			}

			// only keep those headers that is present from the user's data
			mappings = mappings.map(mapping => _.compact(mapping.map(col => this.$store.state.dataMappingNew.data.pool.includes(col) ? col : undefined)));

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
				mappingList: mappings,
			};
		},
		computed: {
			errors() {
				return this.$store.state.dataMappingNew.error;
			},
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
			currentTemplate() {
				console.log(this.$data.mappingList);
				return {
					transformations: this.transformations.map(obj => !obj ? undefined : obj.name),
					mappingList: _.clone(this.mappingList)
				};
			},
			isOldTemplate() {
				const chosenTemplate = this.$store.state.fileTemplates.chosenTemplate;

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
				if (this.transformations.length !== this.mappingList.length || this.transformations.some(f => !f)) { // or at least 1 f is undefined
					this.$store.commit('setError', 'Please ensure all transformations are selected!');
					return;
				}

				// Iterate through each row, combine row and mappingList
				let toProcess = [];
				this.rawData.forEach(row => toProcess.push(_.pick(row, this.mappingList.flat())));
				this.$store.commit('setMappingList', _.cloneDeep(this.mappingList));
				this.$store.commit('setTransformations', this.transformations.map(obj => obj.value));
				this.$store.commit('processData', toProcess);

				if (this.errors.length === 0) {
					this.$store.dispatch('persistData');
				}
			},
			addToSelected(field) {
				distribute(this.$data.mappingList, field);
			},

			removeFromSelected: function (colIdx, lstIdx) {
				this.mappingList = removeItem(this.mappingList, colIdx, lstIdx);
			}
		},
	};
</script>

<style scoped>
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

	.db-header {
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