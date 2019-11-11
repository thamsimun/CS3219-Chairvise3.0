<template>
    <el-container>
        <el-main>
            <div style="padding-left: 55px; margin-bottom: 27px; margin-top: 20px">
                <span class="head">Shared Presentations</span>
            </div>
            <el-menu :default-active="$route.path" class="menu" v-loading="isLoading" router>
                <div class="container">
                    <div class="row" >
                        <div class = "column"  v-for="presentation in sharedPresentationListInfo"
                             :key="presentation.id">
                            <el-menu-item :index="`/analyze/${presentation.id}`" class="menu-item">
                                <el-card :body-style="{ padding: '10px'}" style="text-after-overflow: ellipsis; min-width: 250px; min-height: 200px">
                                    <img src="../assets/test3.png" class="image">
                                    <div class="text">
                                        <span>{{presentation.name}}</span>
        <!--                                <div class="bottom-clearfix">-->
        <!--                                    <el-button type="text" v-on:click="deleteFile(file)" class="button">Remove</el-button>-->
        <!--                                </div>-->
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
    import presentationShared from "../store/modules/presentationShared";


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
                    title: 'Shared Presentation list API request fail',
                    message: this.$store.state.presentationShared.sharedPresentationListStatus.apiErrorMsg,
                    duration: 0
                });
            }
        },
        computed: {
            isLoading() {
                return this.$store.state.presentationShared.sharedPresentationListStatus.isLoading
            },

            sharedPresentationListInfo() {
                return this.$store.state.presentationShared.sharedPresentationList;
            },

            isError() {
                return this.$store.state.presentationShared.sharedPresentationListStatus.isApiError
            },

        },
        mounted() {
            this.$store.dispatch('getSharedPresentationList');
        },


        components: {
            presentationShared
        }
    }
</script>

<style>
    .head{
        text-align: center;
        font-size: 30px;
        font-weight: bold;
    }
    .bottom-clearfix {
        font-size: 14px;
        /*padding-left: 25px;*/
        text-align: center;
    }
    .text {
        font-size: 14px;
        text-align: center;
        /*not working*/
        text-after-overflow: ellipsis;

    }

    .text-item {
        font-size: 18px;
        padding-left: 60px;
    }

    .image{
        width: 100%;
        display: block;
    }

    .row {
        display: flex;
        flex-direction: row;
        flex-wrap: wrap;
        width: 100%;
    }

    .column {
        display: flex;
        flex-direction: column;
        padding-left: 20px;
        margin-bottom: 25px;
    }
    .container {
        margin: 0px;
        display: flex;
    }

</style>
