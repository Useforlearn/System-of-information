<template>
  <div class="main-content" style="width: 60%">
    <div class="card">
      <div style="display: flex; grid-gap: 20px;">
        <img :src="medicalExam.cover" alt="" style="width: 50%">
        <div style="flex: 1">
          <div style="font-size: 22px; margin-bottom: 40px">{{medicalExam.name }}</div>
          <div class="item"><span style="color: #333">体检简介：</span>{{medicalExam.descr }}</div>
          <div class="item"><span style="color: #333">体检日期：</span>{{ medicalExam.start }} ~ {{ medicalExam.end }}</div>
          <div class="item"><span style="color: #333">体检地址：</span>{{ medicalExam.address }}</div>
          <div>
            <el-button
                @click="handleSign"
                size="medium"
                type="primary"
                v-if="medicalExam.status === '未预约'"
            >
              开始预约
            </el-button>
            <el-button
                size="medium"
                type="primary"
                v-if="medicalExam.status === '已预约'"
                disabled
                key="sign"
            >
              已预约
            </el-button>
            <el-button
                size="medium"
                type="primary"
                v-if="medicalExam.status === '未体检'"
                disabled
                key="notstart"
            >
              体检未开始
            </el-button>
          </div>
        </div>
      </div>

      <div style="margin: 50px 0">
        <div style="margin-bottom: 30px; font-size: 22px; font-weight: bold; text-align: center">- 体检详情 -</div>
        <div class="w-e-text" style="line-height: 26px">
          <div v-html="medicalExam.content"></div>
        </div>
      </div>

    </div>

    <Comment :fid ="id" module ="medicalExam" />

    <el-dialog userName="报名信息" :visible.sync="formVisible" width="40%" :close-on-click-modal="false" destroy-on-close>
      <el-form label-width="100px" style="padding-right: 50px" :model="form" :rules="rules" ref="formRef">
        <el-form-item prop="phone" label="手机号">
          <el-input  v-model="form.phone" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="formVisible = false">取 消</el-button>
        <el-button type="primary" @click="sign">报 名</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
import Comment from "@/components/Comment"
export default {
  name: "MedicalExamDetail",
  components: {Comment},
  data() {
    return {
      id: this.$route.query.id,
      medicalExam: {},
      formVisible: false,
      form:{},
      user:JSON.parse(localStorage.getItem('xm-user') || '{}'),
      rules:{
        phone:[
          {required : true , message:'手机号必填', trigger:'blur' }
        ]
      }
    }
  },
  created() {
    this.load()
  },
  methods: {
    sign(){
      this.$request.post('/medicalExamSign/add',this.form).then(res=>{
        if(res.code  === '200'){
          this.$message.success('报名成功')
          this.formVisible=false
          this.load()
        } else {
          this.$message.error(res.msg)
        }
      })
    },
    handleSign(){
      this.form={}
      this.form.userId = this.user.id
      this.form.examItemId = this.id
      this.formVisible=true
    },
    load() {
      this.$request.get('/medicalExam/selectById/' + this.id).then(res => {
        this.medicalExam = res.data || {}
      })
    }
  }
}
</script>

<style scoped>
.item {
  color: #666; margin-bottom: 40px
}
</style>