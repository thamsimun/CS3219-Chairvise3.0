<template>
  <div>
    <div class="register" v-if="!isLogin">
      <h1>Create an account</h1>
      <h4>It's quick and easy!</h4>

      <el-form ref="registerForm" :model="registerForm" :rules="rules">
        <el-form-item label="Email" prop="email">
          <el-input v-model="registerFormEmail"/>
        </el-form-item>
        <!--                <el-form-item label="Username">-->
        <!--                    <el-input v-model="formLabelAlign.region"></el-input>-->
        <!--                </el-form-item>-->
        <el-form-item label="Password" prop="password">
          <el-input v-model="registerFormPassword"/>
        </el-form-item>
      </el-form>
      <div>
        <el-button round id="register_button" @click="registerUser()"> Register</el-button>
      </div>
    </div>
    <div class="register" v-if="isLogin">
      <h1>Hello, {{this.$store.state.userInfo.userEmail}}</h1>
      <h4>You are already logged in!</h4>
    </div>
  </div>
</template>

<script>
  export default {
    name: "SignUpSection",
    data() {
      return {
        rules: {
          email: [
            {required: true, message: 'Please enter email', trigger: 'blur'},
            {pattern: /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,24}))$/,
            message: 'Please enter valid email', trigger: 'blur'}
          ],
          password: [
            {required: true, message: 'Please enter password', trigger: 'blur'},
            {min: 6, message: 'The password length should be more than 6 character'}
          ]
        },
      };
    },


    computed: {
      isLogin() {
        return this.$store.state.userInfo.isLogin
      },
      registerForm() {
        return {
          email: this.registerFormEmail,
          password: this.registerFormPassword,
        }
      },

      registerFormEmail: {
        get() {
          return this.$store.state.userInfo.registerForm.email
        },
        set(value) {
          this.$store.commit('setRegisterFormField', {
            field: 'email',
            value
          })
        },
      },

      registerFormPassword: {
        get() {
          return this.$store.state.userInfo.registerForm.password
        },
        set(value) {
          this.$store.commit('setRegisterFormField', {
            field: 'password',
            value
          })
        },
      },
    },

    methods: {
      registerUser() {
        this.$refs['registerForm'].validate((valid) => {
          // If invalid form
          if (!valid) {
            return
          }
          this.$refs['registerForm'].clearValidate();

          if (!this.registerFormEmail) {
            this.emptyEmail = true;
          } else if (!this.reg.test(this.registerFormEmail)) {
            this.validEmail = false;
          }
          // If not logged in
          if (!this.isLogin) {
            this.$store.dispatch('addUser')
                .then(() => {
                  if (this.isError) {
                    return
                  }
                })
          } else {
            this.$store.dispatch('setAuthInfoApiRequestFail', "You are currently logged in.")
          }
        })
      },

      isLoading() {
        return this.$store.state.userInfo.registerFormStatus.isLoading
      },

      // formError() {
      //   return this.$store.state.userInfo.registerFormStatus.formError
      // },

      apiErrorMsg() {
        return this.$store.state.presentation.presentationFormStatus.apiErrorMsg
      },

    }

  }
</script>

<style scoped>

  .register {
    margin: 40px 40px 40px 40px;
    padding: 20px 35px 35px 35px;
    border-radius: 25px;
    border: 3px solid lightgray;
    background-color: white;
  }

  #register_button:hover {
    background-color: yellowgreen;
    fill-opacity: 60%;
  }
</style>