<template>
    <div class="register" >
        <div>
            <h1>Create an account</h1>
            <h4>It's quick and easy!</h4>

            <el-form :label-position='top' :model="formLabelAlign">
                <el-form-item label="Email">
                    <el-input v-model="formLabelAlign.name"></el-input>
                </el-form-item>
                <el-form-item label="Username">
                    <el-input v-model="formLabelAlign.region"></el-input>
                </el-form-item>
                <el-form-item label="Password">
                    <el-input v-model="formLabelAlign.type"></el-input>
                </el-form-item>
            </el-form>
            <div>
                <el-button round id="register_button" > Register </el-button>
            </div>
        </div>
    </div>
</template>

<script>
    export default {
        name: "SignUpSection",
        data() {
            return {
                formLabelAlign: {
                    name: '',
                    region: '',
                    type: ''
                }
            };
        }
    }
</script>

<style scoped>

    .register {
        padding:30px 35px 35px 35px;
        border-radius: 25px;
        border: 3px solid lightgray;
    }

    #register_button:hover {
        background-color: yellowgreen;
        fill-opacity: 60%;
    }
</style>