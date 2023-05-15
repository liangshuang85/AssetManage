<template>
  <div>
    <div style="margin-bottom: 5px">
      <el-input v-model="name" placeholder="请输入名字" style="width:200px"
        @keyup.enter.native="loadPost"></el-input>
      <el-button type="primary" style="margin-left: 5px" @click="loadPost">查询</el-button>
      <el-button type="success" >重置</el-button>
      <el-button type="primary" style="margin-left: 5px" @click="add">新增</el-button>
    </div>
  <el-table :data="tableData"
    :header-cell-style="{background:'#f7f5ff',color:'#28282a' }"
  >
    <el-table-column prop="id" label="ID" width="280">
    </el-table-column>
    <el-table-column prop="name" label="姓名" width="180">
    </el-table-column>
    <el-table-column prop="ac" label="账号" width="180">
    </el-table-column>
    <el-table-column prop="role" label="角色" width="250">
      <template slot-scope="scope">
        <el-tag
            :type="scope.row.role === 0 ? 'danger' : 'success'"
            disable-transitions>{{scope.row.role === 0 ? '管理员' : '用户'}}</el-tag>
      </template>
    </el-table-column>
    <el-table-column prop="operate" label="操作" >
      <template slot-scope="scope">
      <el-button size="small" type="success"  @click="mod(scope.row)">编辑</el-button>
        <el-popconfirm
            title="确定删除吗？"
            @confirm="del(scope.row.id)"
            style="margin-left: 5px"
        >
          <el-button slot="reference" size="small" type="danger" >删除</el-button>
        </el-popconfirm>
      </template>
    </el-table-column>
  </el-table>

    <span class="demonstration"></span>
    <el-pagination align="center"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="pageNum"
        :page-sizes="[ 5, 10, 20,30]"
        :page-size="pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total">
    </el-pagination>

    <el-dialog
        title="提示"
        :visible.sync="centerDialogVisible"
        width="30%"
        center>

      <el-form ref="form" :model="form" label-width="80px" >
        <el-form-item label="账号">
          <el-input v-model="form.ac" style="width: 70%"></el-input>
        </el-form-item>
      </el-form>

      <el-form ref="form" :model="form" label-width="80px" >
        <el-form-item label="密码">
          <el-input v-model="form.password" style="width: 70%"></el-input>
        </el-form-item>
      </el-form>

      <el-form ref="form" :model="form" label-width="80px" >
        <el-form-item label="姓名">
          <el-input v-model="form.name" style="width: 70%"></el-input>
        </el-form-item>
      </el-form>

<!--      <el-form ref="form" :model="form" label-width="80px" >-->
<!--        <el-form-item label="角色">-->
<!--          <el-input v-model="form.role" style="width: 70%"></el-input>-->
<!--        </el-form-item>-->
<!--      </el-form>-->

      <span slot="footer" class="dialog-footer">
    <el-button @click="centerDialogVisible = false">取 消</el-button>
    <el-button type="primary" @click="save">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: "Main",
  data() {
    return {
      tableData: [],
      pageSize:5,
      pageNum:1,
      total:0,
      name:'',
      centerDialogVisible:false,
      form:{
        name:'',
        role:'1',
        ac:'',
        password:'',
        id:''
      }
    }
  },
  methods:{
    //删除
    del(id){
      console.log(id)

      this.$axios.get(this.$httpUrl + '/user/del?id='+id).then(res => res.data).then(res => {
        console.log(res)

        if (res.code == 200) {
          alert("success")
          this.centerDialogVisible = false
          this.loadPost()
        } else {
          alert("fail")
          this.centerDialogVisible = false
        }
      })


    },
    //编辑
    mod(row){
      console.log(row)
      //给表单赋值
      this.form.id=row.id
      this.form.name=row.name
      this.form.ac=row.ac
      this.form.password=''
      //需要添加异步！
      this.centerDialogVisible=true

    },

    doMod(){
      this.$axios.post(this.$httpUrl+'/user/mod',this.form).then(res=>res.data).then(res=>{
            console.log(res)
        if (res.code == 200) {
          alert("success")
          this.centerDialogVisible = false
          this.loadPost()
        } else {
          alert("fail")
          this.centerDialogVisible = false
        }
      })
    },

    doSave(){
      this.$axios.post(this.$httpUrl + '/user/save', this.form).then(res => res.data).then(res => {
        console.log(res)
        if (res.code == 200) {
          alert("success")
          this.centerDialogVisible = false
          this.loadPost()
        } else {
          alert("fail")
          this.centerDialogVisible = false
        }
      })
    },
    //保存
    save(){
      if(this.form.id){
        this.doMod();
      }else{
        this.doSave();
      }

    },
    //新增功能
    add(){
      this.centerDialogVisible = true
    },

    loadGet(){
      this.$axios.get(this.$httpUrl+'/user/list').then(res=>res.data).then(res=>{
          console.log(res)})
    },
    loadPost(){
      this.$axios.post(this.$httpUrl+'/user/listPage1',{
        pageSize:this.pageSize,
        pageNum:this.pageNum,
        param:{
          name:this.name
        }
      }).then(res=>res.data).then(res=>{
        console.log(res)
        if(res.code==200){
          this.tableData=res.data
          this.total=res.total
        }else {
          alert("获取数据失败")
        }
      })
    },

    handleSizeChange(val) {
      console.log(`每页 ${val} 条`);
      this.pageSize=val
      this.loadPost()
    },

    handleCurrentChange(val) {
      console.log(`当前页: ${val}`);
      this.pageNum=val
      this.loadPost()
    }
  },

  beforeMount() {
    //this.loadGet();
    this.loadPost();
  }
}
</script>

<style scoped>

</style>