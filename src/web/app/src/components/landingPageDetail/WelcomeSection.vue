<template>
    <div class="welcome">
        <h1 class="welcome_heading">
            Welcome to CharVise!
        </h1>
        <div class="welcome_content">
            Get the most out of your conference submission data.
            <br><br>
            Create and Share beautiful visualisations with your friends.
        </div>
        <div class="welcome_button">
            <el-button round id="welcome_button" >Get started</el-button>
        </div>
    </div>
</template>

<script>
    export default {
        name: "WelcomeSection"
    }
</script>

<style scoped>
    .welcome {
        padding:30px 35px 0 35px;
    }
    .welcome_heading {
        padding: 5px 0 5px 0;
        text-align: center;
        font-size: xx-large;
    }

    .welcome_content {
        padding: 5px 0 5px 0;
        text-align: justify;
        font-size: large;
    }

    .welcome_button {
        padding-top: 20px;
        text-align: center;
    }

    #welcome_button:hover {
        background-color: yellowgreen;
        fill-opacity: 60%;
    }
</style>