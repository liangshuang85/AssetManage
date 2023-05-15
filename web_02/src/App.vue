<template>
  <div id="app">
    <router-view/>
  </div>
</template>
<script>



export default {
  name: 'App',
  components: {},
  data(){
    return{
      user:JSON.parse(sessionStorage.getItem('CurUser'))
    }
  },
  //监控刷新
  watch:{
      '$store.state.menu':{
        //val是当前取出的对象
        handler(val,old){
          if(!old && this.user && this.user.ac){
            this.$store.commit("setMenu",val)
          }
        },
        immediate:true
      }
  }
}
</script>

<style>
#app {
  height: 100%;
}
</style>
