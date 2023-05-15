<template>
  <div style="display: flex;line-height: 60px">
    <div >
      <i :class="icon" style="font-size: 20px"  @click="collapse"></i>
    </div>
    <div style="flex: 1;text-align: center;font-size: 34px" >
      <span>欢迎来到资产管理系统</span>
    </div>
  <el-dropdown>
    <i class="el-icon-arrow-down" style="margin-left: 5px"></i>
      <el-dropdown-menu slot="dropdown">
        <el-dropdown-item @click.native="touser" @click.b.native="touser">个人中心</el-dropdown-item>
        <el-dropdown-item @click.native="logout" @click.b.native="logout">退出登录</el-dropdown-item>
      </el-dropdown-menu>
    </el-dropdown>
    <span>{{user.name}}</span>
  </div>
</template>

<script>
export default {
  name: "Header",
  data(){
    return{
      user:JSON.parse(sessionStorage.getItem("CurUser"))
    }
  },
  props:{
    icon:String
  },
  methods: {
    touser() {
      console.log('to_user')

      this.$router.push("/Home")
    },
    logout(){
      console.log('logout')

      this.$router.push("/")

      sessionStorage.clear()
    },
    collapse(){
      this.$emit('doCollapse')
    }

  },
  created() {
    this.$router.push('/Home')
  }
}
</script>

<style scoped>

</style>