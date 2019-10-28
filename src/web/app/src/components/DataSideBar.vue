<template>
    <el-aside width="200px" style="background-color: rgb(238, 241, 246)">
        <el-menu :default-active="$route.path" v-loading="isLoading" router>
            <li>
                <el-menu-item :index="'/presentationData'">
                    <i class="el-icon-menu"></i>
                    <span slot="title">Presentations</span>
                </el-menu-item>
            </li>
        </el-menu>
    </el-aside>
    
</template>



<script>
    export default {
        name: "DataSideBar"
    }
</script>

<style scoped>

</style>