<template>
  <div class="container">
    <div style="height: 100px;background-color:white ">
      <div style= "width: 27%; height: 100%;display:flex;align-items: center; margin: 0 auto">
        <img src="@/assets/imgs/logo.png" alt="" style="width: 40px">
        <span style="color: #2a60c9; font-size: 25px; font-weight: bold; margin-left:10px ">社区健康服务管理系统</span>
      </div>
    </div>
    <div style="height: calc(100vh - 250px);width: 55%; margin:0 auto; display: flex; align-items: center">
      <div style="flex:1;">
        <img src="@/assets/imgs/login.png" alt="" style="width: 100%; height: 290px; border-radius: 5px">
      </div>
      <div style="width:400px; height: 300px;padding: 30px; background-color: white; border-radius: 3px;">
        <div style="text-align: center; font-size: 30px; margin-bottom: 15px; font-weight: bold; color: #2a60c9">欢 迎 注 册
</div>
        <el-form :model="form" :rules="rules" ref="formRef">
          <el-form-item prop="username">
            <el-input prefix-icon="el-icon-user" placeholder="请输入账号" v-model="form.username"></el-input>
          </el-form-item>
          <el-form-item prop="password">
            <el-input prefix-icon="el-icon-lock" placeholder="请输入密码" show-password  v-model="form.password"></el-input>
          </el-form-item>
          <el-form-item prop="confirmPassword">
            <el-input prefix-icon="el-icon-lock" placeholder="请确认密码" show-password  v-model="form.confirmPassword"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button style="width: 100%; background-color: #333; border-color: #333; color: white" @click="register">注 册</el-button>
          </el-form-item>
                  <div style="display: flex; align-items: center">
                    <div style="flex: 1"></div>
                    <div style="flex: 1; text-align: right">
                      已有账号？<a href="/login">登录</a>
                    </div>
                  </div>
        </el-form>
      </div>
    </div>

  </div>
</template>

<script>
export default {
  name: "Register",
  data() {
    // 验证码校验
    const validatePassword = (rule, confirmPass, callback) => {
      if (confirmPass === '') {
        callback(new Error('请确认密码'))
      } else if (confirmPass !== this.form.password) {
        callback(new Error('两次输入的密码不一致'))
      } else {
        callback()
      }
    }
    return {
      form: { role: 'USER' },
      rules: {
        username: [
          { required: true, message: '请输入账号', trigger: 'blur' },
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' },
        ],
        confirmPassword: [
          { validator: validatePassword, trigger: 'blur' }
        ]
      }
    }
  },
  created() {

  },
  methods: {
    goBack() {
      this.$router.go(-1);
    },
    register() {
      this.$refs['formRef'].validate((valid) => {
        if (valid) {
          // 验证通过
          this.$request.post('/register', this.form).then(res => {
            if (res.code === '200') {
              this.$router.push('/login')  // 跳转登录
              this.$message.success('注册成功')
            } else {
              this.$message.error(res.msg)
            }
          })
        }
      })
    }
  }
}
</script>

<style scoped>
.container {
  height: 100vh;
  overflow: hidden;
  background-color:#f8f8f8 ;

}
a {
  color: #2a60c9;
}
</style>