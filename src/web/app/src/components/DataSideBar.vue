<template>
    <el-aside width="200px" style="background-color: rgb(238, 241, 246)">
        <el-menu :default-active="$route.path" v-loading="isLoading" router>
            <el-submenu index="1">
                <template slot="title"><i class="el-icon-menu"></i>Presentations</template>
                <el-menu-item-group>
                    <el-menu-item :index="'/analyze/__NEW__'">
                        <i class="el-icon-edit-outline"></i>
                        <span slot="title">Create New</span>
                    </el-menu-item>
                    <el-menu-item :index="'/presentationCreated'">
                        <i class="el-icon-edit"></i>
                        <span slot="title">Created By Me</span>
                    </el-menu-item>
                    <el-menu-item :index="'/presentationShared'">
                        <i class="el-icon-s-custom"></i>
                        <span slot="title">Shared With Me</span>
                    </el-menu-item>
                </el-menu-item-group>
            </el-submenu>
            <el-submenu index="2">
                <template slot="title"><i class="el-icon-menu"></i>Files</template>
                <el-menu-item-group>
                    <el-menu-item :index="'/importDataNew'">
                        <i class="el-icon-upload"></i>
                        <span slot="title">Upload New</span>
                    </el-menu-item>
                    <el-menu-item :index="'/fileData'">
                        <i class="el-icon-files"></i>
                        <span slot="title">Added By Me</span>
                    </el-menu-item>
                </el-menu-item-group>
            </el-submenu>
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