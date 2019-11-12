<template>
	<div style='width:100%;text-align:center;'>
		<el-table
						highlight-current-row
						@current-change='select'
						style='width:100%;'
						:data='templates'>
			<el-table-column
							property="templateId"
							label="Id"
							width="80">
			</el-table-column>
			<el-table-column
							property="name"
							label="Name"
							width="300">
			</el-table-column>
			<el-table-column
							property="description"
							label="Description">
			</el-table-column>
			<el-table-column align='right'>
				<template slot-scope="scope">
					<el-button
									size="mini"
									type="danger"
									style='float:right;'
									@click.stop="handleDelete(scope)">Delete
					</el-button>
				</template>
			</el-table-column>
		</el-table>
	</div>
</template>

<script>
	export default {
		name: 'SelectTemplateTable',
		mounted() {
			this.$store.dispatch('fetchFileTemplates');
		},
		data() {
			return {
				selectedRow: null
			};
		},
		computed: {
			templates() {
				return this.$store.state.fileTemplates.templates.map(({templateId, templateMappingList}) => {
					return {
						templateId,
						...templateMappingList
					};
				});
			}
		},
		methods: {
			select(template) {
				this.$store.commit('selectTemplate', template);
			},
			handleDelete({row}) {
				this.$store.dispatch('deleteFileTemplate', row.templateId)
					.then(() => this.$store.dispatch('fetchFileTemplates'));
			},
		}
	};
</script>

<style scoped>
</style>