<template>
    <el-container style="min-width: 200px">
        <el-main>
            <div style="padding-left: 27px; margin-bottom: 0px; margin-top: 30px">
                <span style="font-size: 30px; font-weight: bold">Presentations Created By Me</span>
            </div>
            <el-menu :default-active="$route.path" class="menu" v-loading="isLoading" router>
                <el-row>
                    <el-col span="7" v-for="presentation in presentations"
                            :key="presentation.id" class="text-item" style="margin-bottom: 80px; padding-left: 0px">
                        <el-menu-item :index="`/analyze/${presentation.id}`" class="menu-item">
                            <el-card :body-style="{ padding: '0px'}" style="text-after-overflow: ellipsis; min-width: 250px; min-height: 200px">
                                <img src="../assets/test3.png" class="image">
                                <div class="text">
                                    <span>{{presentation.name}}</span>
                                </div>
                            </el-card>
                        </el-menu-item>
                    </el-col>
                </el-row>
            </el-menu>
        </el-main>
    </el-container>
</template>

<script>
    export default {
        name: 'SharedPresentation',
        data() {
            return {}
        },
        watch: {
            'isError'() {
                if (!this.isError) {
                    return
                }
                this.$notify.error({
                    title: 'Presentation list API request fail',
                    message: this.$store.state.presentation.presentationListStatus.apiErrorMsg,
                    duration: 0
                });
            }
        },
        computed: {
            isLoading() {
                return this.$store.state.presentation.presentationListStatus.isLoading
                    || this.$store.state.presentation.presentationFormStatus.isLoading
                    || this.$store.state.section.sectionListStatus.isLoading
                    || this.$store.state.section.sectionList.some(s => s.status.isLoading)
            },
            presentations() {
                return this.$store.state.presentation.presentationList
            },
            isError() {
                return this.$store.state.presentation.presentationListStatus.isApiError
            },
        },
        mounted() {
            this.$store.dispatch('getPresentationList')
        },
        methods: {
        }
    }
</script>

<style>
    .text-item {
        font-size: 20px;
        padding-left: 25px;
        text-after-overflow: ellipsis;
    }

    .image{
        width: 100%;
        display: block;
    }

    .menu{
        height: 450px;
    }
    .menu-item{
        margin-bottom:175px;
        margin-top: 50px;
        height: 85px;
        width: 250px;
    }

    .text {
        font-size: 18px;
        text-align: center;
        /*not working*/
        text-after-overflow: ellipsis;

    }

</style>