<template>
  <div class="listGuide">
    <h4><i class="el-icon-document"></i> Guide Outline </h4>
    <el-menu style="border-width:0;" @select="scrollToId($event)" default-active="general-guide">
      <el-menu-item index="general-guide">
        <i class="el-icon-question"></i>
        <span>
          General User Guide
        </span>
      </el-menu-item>
      <el-menu-item index="features-available">
        <i class="el-icon-question"></i>
        <span>
          Features Supported
        </span>
      </el-menu-item>
      <el-menu-item index="import-guide">
        <i class="el-icon-question"></i>
        <span>
          How to Import Data
        </span>
      </el-menu-item>
      <el-menu-item index="add-guide">
        <i class="el-icon-question"></i>
        <span>
          How to Add Presentation
        </span>
      </el-menu-item>
      <el-menu-item index="share-guide">
        <i class="el-icon-question"></i>
        <span>
          How to Share Presentation
        </span>
      </el-menu-item>
    </el-menu>
  </div>
</template>

<script>
  export default {
    name: 'ListOfGuide',
    props: {
      msg: String
    },

    methods: {
      scrollToId(id) {
        document.getElementById(id).scrollIntoView();
      }
    }
  }
</script>

<style>
  .listGuide {
    width: 245px;
    margin-left: 10px;
    position: fixed;
  }
  .el-menu-item{
    width: auto;
  }
</style>
