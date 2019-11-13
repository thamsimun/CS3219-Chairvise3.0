<template>
    <el-container style="min-width: 200px">
        <el-main>
            <div style="text-align: center; margin-bottom: 0px; margin-top: 30px">
                <span class="head">Presentations Created By Me</span>
            </div>
            <el-menu :default-active="$route.path" class="menu" v-loading="isLoading" router>
                <div class="container">
                    <div class="row" >
                        <div class = "column"  v-for="presentation in presentations"
                                :key="presentation.id">
                            <el-menu-item :index="`/analyze/${presentation.id}`" class="menu-item">
                                <el-card class="present-tiles">
<!--                                    body-style="{ padding: '10px'}" style="text-after-overflow: ellipsis; min-width: 250px; min-height: 200px"-->
                                    <img src="../assets/test3.png" class="image">
                                    <div class="text">
                                        <div class="presentationname">{{presentation.name}}</div>
                                    </div>
                                </el-card>
                            </el-menu-item>
                        </div>
                    </div>
                </div>
            </el-menu>
        </el-main>
    </el-container>
</template>

<script>
    export default {
        name: 'PresentationCreatedByMe',
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

    .head{
        text-align: center;
        font-size: 30px;
        font-weight: bold;
    }

    .container {
        margin: 0px;
        display: flex;
    }

    .present-tiles {
        width: 100%;
        height: 100%;
    }

    .row {
        display: flex;
        flex-direction: row;
        flex-wrap: wrap;
        width: 100%;
    }

    .presentationname {
        width: 100px;
        overflow: hidden;
        text-overflow: ellipsis;
    }
    .presentationname:hover {
        overflow: visible;
    }

    .column {
        display: flex;
        flex-direction: column;
        padding-left: 20px;
        margin-bottom: 25px;
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