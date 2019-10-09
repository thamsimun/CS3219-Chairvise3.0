<template>
	<div>
		<h1>Select record type</h1>
		<div class='card-container' v-for='dbSchema in dbSchemas' v-bind:key='dbSchema.tableName'>
			<div v-on:click='select(dbSchema)'>
				<el-card class='box-card' shadow='hover'>
					<h1>{{ dbSchema.name }}</h1>
					<ul>
						<li v-for='field in dbSchema.fieldMetaDataList' v-bind:key='field.name'>
							{{ field.name }}
						</li>
					</ul>
				</el-card>
			</div>
		</div>
		<div>
			<el-upload drag action=''
                 :auto-upload='false'
                 :show-file-list='false'
                 :multiple='false'
                 :on-change='fileUploadHandler'>
				<i class='el-icon-upload'></i>
				<div class='el-upload__text'>Drop file here or <em>click to upload</em></div>
				<div class='el-upload__tip' slot='tip'>Please upload .csv files with filed names.</div>
			</el-upload>
		</div>
		<div class='template-container'>
			<p>CHOOSE TEMPLATE</p>
		</div>
		<div>
			<h2>Pick columns</h2>
			<ul>
				<li v-for='field in pool' v-bind:key='field' v-on:click='addToSelected(field)'>
					{{ field }}
				</li>
			</ul>
			<h2>Selected</h2>
			<ul>
				<li v-for='field in selected' v-bind:key='field' v-on:click='addToPool(field)'>
					{{ field }}
				</li>
			</ul>
		</div>
		<el-button v-on:click='nextClicked'>Next</el-button>
	</div>
</template>

<script>
	import Papa from "papaparse";

	export default {
		name: "PromptTableType",
		mounted: function() {
			this.$store.dispatch('fetchDBMetaDataEntities');
		},
		data: function () {
			return {
				pool: [],
				selected: []
			}
		},
		computed: {
			dbSchemas: function() {
				return this.$store.state.dbMetaData.entities;
			}
		},
		methods: {
			select: function(dbSchema) {
				this.$store.commit('setDbSchema', dbSchema);
			},
			addToSelected: function(field) {
				this.pool.splice(this.pool.indexOf(field), 1);
				this.selected.push(field);
			},
			addToPool: function(field) {
				this.selected.splice(this.selected.indexOf(field), 1);
				this.pool.push(field);
			},
			fileUploadHandler: function(file) {
				this.$store.commit('setPageLoadingStatus', true);
				Papa.parse(file.raw, {
					skipEmptyLines: true,
					header: true, // we take it as there are headers present for now
					complete: result => {
						this.pool = result.meta.fields;
						this.$store.commit('setUploadedData', result.data);
						this.$store.commit('setPageLoadingStatus', false);
					}
				})
			},
			nextClicked: function() {
				this.$store.commit('setSelectedFields', _.cloneDeep(this.selected));
			}
		}
	}
</script>

<style scoped>
	.box-card {
		width: 480px;
		height: 480px;
		margin-right: 20px;
	}

	.card-container {
		display: inline-block;
	}

	.template-container {
		display: inline-block;
		width: 1000px;
		height: 480px;
		border-style: solid;
		border-width: 10px;
		border-color: gold;
	}

</style>