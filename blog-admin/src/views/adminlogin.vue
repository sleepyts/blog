<template>
  <div class="container">
    <div class="card">
      <a class="login">Log in</a>
      <div class="inputBox">
        <input type="text" required="required" v-model="form.username">
        <span class="user" >用户名</span>
      </div>

      <div class="inputBox">
        <input type="password" required="required" v-model="form.password">
        <span class="pass">密码</span>
      </div>
      <button class="enter" @click="handleLogin">登录</button>
    </div>
  </div>
</template>

<script setup>
import {ref} from 'vue';
import {message} from 'ant-design-vue';
import {login} from "@/api/login.js";
import router from "@/route/index.js";
const form = ref({
  username: '',
  password: ''
})
const handleLogin = async () => {
  if (!form.value.username) {
    message.error('请输入用户名');
    return;
  }
  if (!form.value.password) {
    message.error('请输入密码');
    return;
  }
  console.log(form.value)
  await login(form.value).then(res => {
    if (res.data.code === 200) {
      message.success('登录成功')
      localStorage.setItem('token', res.data.data)
      // console.log(localStorage.getItem('token'))
      router.push('/')
    } else {
      message.error(res.data.message)
    }
  })
}
</script>

<style>
.container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
}

.login {
  color: #000;
  text-transform: uppercase;
  letter-spacing: 2px;
  display: block;
  font-weight: bold;
  font-size: x-large;
}

.card {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 350px;
  width: 300px;
  flex-direction: column;
  gap: 35px;
  background: #e3e3e3;
  box-shadow: 16px 16px 32px #c8c8c8,
  -16px -16px 32px #fefefe;
  border-radius: 8px;
}

.inputBox {
  position: relative;
  width: 250px;
}

.inputBox input {
  width: 100%;
  padding: 10px;
  outline: none;
  border: none;
  color: #000;
  font-size: 1em;
  background: transparent;
  border-left: 2px solid #000;
  border-bottom: 2px solid #000;
  transition: 0.1s;
  border-bottom-left-radius: 8px;
}

.inputBox span {
  margin-top: 5px;
  position: absolute;
  left: 0;
  transform: translateY(-4px);
  margin-left: 10px;
  padding: 10px;
  pointer-events: none;
  font-size: 12px;
  color: #000;
  text-transform: uppercase;
  transition: 0.5s;
  letter-spacing: 3px;
  border-radius: 8px;
}

.inputBox input:valid ~ span,
.inputBox input:focus ~ span {
  transform: translateX(113px) translateY(-15px);
  font-size: 0.8em;
  padding: 5px 10px;
  background: #000;
  letter-spacing: 0.2em;
  color: #fff;
  border: 2px;
}

.inputBox input:valid,
.inputBox input:focus {
  border: 2px solid #000;
  border-radius: 8px;
}

.enter {
  height: 45px;
  width: 100px;
  border-radius: 5px;
  border: 2px solid #000;
  cursor: pointer;
  background-color: transparent;
  transition: 0.5s;
  text-transform: uppercase;
  font-size: 20px;
  letter-spacing: 2px;
  margin-bottom: 1em;
}

.enter:hover {
  background-color: rgb(0, 0, 0);
  color: white;
}


</style>