<template>
  <div>
    <div class="register" v-if="!isLogin">
      <h1>Create an account</h1>
      <h4>It's quick and easy!</h4>

      <el-form ref="registerForm" :model="registerForm">
        <el-form-item label="Email">
          <el-input v-model="registerFormEmail"/>
        </el-form-item>
        <!--                <el-form-item label="Username">-->
        <!--                    <el-input v-model="formLabelAlign.region"></el-input>-->
        <!--                </el-form-item>-->
        <el-form-item label="Password">
          <el-input v-model="registerFormPassword"/>
        </el-form-item>
      </el-form>
      <div>
        <el-button round id="login_button" @click="loginUser()"> Login </el-button>
        <el-button round id="register_button" @click="registerUser()"> Register</el-button>
      </div>
      <el-dialog
              title="Failure"
              :visible.sync="isInvalidCredentials"
              width="30%" center>
        <span>Unable to login! Please ensure your username and password are correct!</span>
        <span slot="footer" class="dialog-footer">
        <el-button type="primary" v-on:click="closeInvalidCredentials">Okay</el-button>
      </span>
      </el-dialog>
      <el-dialog
              title="Failure"
              :visible.sync="isSignUpFailed"
              width="30%" center>
        <span>Unable to sign up! Username has been taken!</span>
        <span slot="footer" class="dialog-footer">
        <el-button type="primary" v-on:click="closeInvalidSignUp">Okay</el-button>
      </span>
      </el-dialog>
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
        formLabelAlign: {
          name: '',
          region: '',
          type: ''
        },
      };
    },
    computed: {
      isSignUpFailed: function () {
        return this.$store.state.userInfo.isSignUpFailed;
      },

      isInvalidCredentials: function () {
        return this.$store.state.userInfo.isInvalidUserOrPass;
      },

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
          return this.$store.state.userInfo.registerForm.userEmail
        },
        set(value) {
          this.$store.commit('setRegisterFormField', {
            field: 'userEmail',
            value
          })
        },
      },

      registerFormPassword: {
        get() {
          return this.$store.state.userInfo.registerForm.userPassword
        },
        set(value) {
          this.$store.commit('setRegisterFormField', {
            field: 'userPassword',
            value
          })
        },
      },
    },

    mounted() {
      this.$store.dispatch('setCookies');
    },

    methods: {
      closeInvalidSignUp: function () {
        this.$store.commit('clearSignUpFailure');
      },
      closeInvalidCredentials: function() {
        this.$store.commit('clearInvalidCredentials');
      },
      registerUser() {
        this.$refs['registerForm'].validate((valid) => {
          // If invalid form
          if (!valid) {
            return
          }
          this.$refs['registerForm'].clearValidate();
          // If not logged in
          if (!this.isLogin) {
            this.$store.dispatch('setCookies');
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

      loginUser() {
        this.$refs['registerForm'].validate((valid) => {
          // If invalid form
          if (!valid) {
            return
          }
          this.$refs['registerForm'].clearValidate();
          // If not logged in
          if (!this.isLogin) {
            this.$store.dispatch('setCookies');
            this.$store.dispatch('logUser')
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
      }
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
    min-height: 400px;
    text-align: center;
  }

  #register_button:hover {
    background-color: yellowgreen;
    fill-opacity: 60%;
  }
</style>