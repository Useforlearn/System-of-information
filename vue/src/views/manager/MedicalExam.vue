<template>
  <div>
    <div class="search">
      <el-input placeholder="请输入标题查询" style="width: 200px" v-model="name"></el-input>
      <el-button type="info" plain style="margin-left: 10px" @click="load(1)">查询</el-button>
      <el-button type="warning" plain style="margin-left: 10px" @click="reset">重置</el-button>
    </div>

    <div class="operation">
      <el-button type="primary" plain @click="handleAdd">新增</el-button>
      <el-button type="danger" plain @click="delBatch">批量删除</el-button>
    </div>

    <div class="table">
      <el-table :data="tableData" strip @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55" align="center"></el-table-column>
        <el-table-column prop="id" label="序号" width="70" align="center" sortable></el-table-column>
        <el-table-column prop="name" label="体检项目名称"></el-table-column>
        <el-table-column prop="cover" label="体检项目封面">
          <template v-slot="scope">
            <el-image :preview-src-list="[scope.row.cover]" :src="scope.row.cover" style=" height: 90px;"></el-image>
          </template>
        </el-table-column>
        <el-table-column prop="descr" label="体检项目简介" show-overflow-tooltip></el-table-column>
        <el-table-column prop="start" label="体检开始日期"></el-table-column>
        <el-table-column prop="end" label="体检结束日期"></el-table-column>
        <el-table-column label="查看详情">
          <template v-slot="scope">
            <el-button @click="preview(scope.row.content)">查看详情</el-button>
          </template>
        </el-table-column>
        <el-table-column prop="address" label="体检地址"></el-table-column>
        <el-table-column label="操作" align="center" width="180">
          <template v-slot="scope">
            <el-button size="mini" type="primary" plain @click="handleEdit(scope.row)">编辑</el-button>
            <el-button size="mini" type="danger" plain @click="del(scope.row.id)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>

      <div class="pagination">
        <el-pagination
            background
            @current-change="handleCurrentChange"
            :current-page="pageNum"
            :page-sizes="[5, 10, 20]"
            :page-size="pageSize"
            layout="total, prev, pager, next"
            :total="total">
        </el-pagination>
      </div>
    </div>

    <el-dialog name="体检项目" :visible.sync="fromVisible" width="50%" :close-on-click-modal="false" destroy-on-close>
      <el-form :model="form" label-width="100px" style="padding-right: 50px" :rules="rules" ref="formRef">
        <el-form-item label="体检项目名称" prop="name">
          <el-input v-model="form.name" placeholder="体检项目名称"></el-input>
        </el-form-item>
        <el-form-item label="体检项目封面" prop="cover">
          <el-upload
              class="avatar-uploader"
              :action="$baseUrl + '/files/upload'"
              :headers="{ token: user.token }"
              list-type="picture"
              :on-success="handleCoverSuccess"
          >
            <el-button type="primary">上传封面</el-button>
          </el-upload>
        </el-form-item>
        <el-form-item label="体检项目简介" prop="descr">
          <el-input type="textarea" v-model="form.descr" placeholder="体检项目简介"></el-input>
        </el-form-item>
        <el-form-item label="体检地址" prop="address">
          <el-input type="textarea" v-model="form.address" placeholder="体检地址"></el-input>
        </el-form-item>
        <el-form-item label="体检开始日期" prop="start">
          <el-date-picker style="width: 100%" value-format="yyyy-MM-dd" format="yyyy-MM-dd" v-model="form.start" placeholder="体检开始日期"></el-date-picker>
        </el-form-item>
        <el-form-item label="体检结束日期" prop="end">
          <el-date-picker style="width: 100%" value-format="yyyy-MM-dd" format="yyyy-MM-dd" v-model="form.end" placeholder="体检结束日期"></el-date-picker>
        </el-form-item>
        <el-form-item label="体检详情" prop="content">
          <div id="editor"></div>
        </el-form-item>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="fromVisible = false">取 消</el-button>
        <el-button type="primary" @click="save">确 定</el-button>
      </div>
    </el-dialog>

    <el-dialog title="体检详情" :visible.sync="fromVisible1" width="50%" :close-on-click-modal="false" destroy-on-close>
      <div class="w-e-text">
        <div v-html="content"></div>
      </div>

      <div slot="footer" class="dialog-footer">
        <el-button @click="fromVisible1 = false">关 闭</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import E from "wangeditor"

export default {
  name: "MedicalExam",
  data() {
    return {
      tableData: [],  // 所有的数据
      pageNum: 1,   // 当前的页码
      pageSize: 10,  // 每页显示的个数
      total: 0,
      name: null,
      fromVisible: false,
      form: {},
      user: JSON.parse(localStorage.getItem('xm-user') || '{}'),
      rules: {
        name: [
          { required: true, message: '请输入体检项目名称', trigger: 'blur' },
        ],
        descrption: [
          { required: true, message: '请输入体检项目简介', trigger: 'blur' },
        ],
        address: [
          { required: true, message: '请输入体检地址', trigger: 'blur' },
        ],
        start: [
          { required: true, message: '请选择体检开始日期', trigger: 'blur' },
        ],
        end: [
          { required: true, message: '请选择体检结束日期', trigger: 'blur' },
        ]
      },
      ids: [],
      content: '',
      fromVisible1: false
    }
  },
  created() {
    this.load(1)
  },
  methods: {
    handleAdd() {   // 新增数据
      this.form = {}  // 新增数据的时候清空数据
      this.fromVisible = true   // 打开弹窗
      this.setRichText('')
    },
    handleEdit(row) {   // 编辑数据
      this.form = JSON.parse(JSON.stringify(row))  // 给form对象赋值  注意要深拷贝数据
      this.fromVisible = true   // 打开弹窗
      this.setRichText(this.form.content)
    },
    save() {   // 保存按钮触发的逻辑  它会触发新增或者更新
      this.$refs.formRef.validate((valid) => {
        if (valid) {
          this.form.content = this.editor.txt.html()
          this.$request({
            url: this.form.id? '/medicalExam/update' : '/medicalExam/add',
            method: this.form.id? 'PUT' : 'POST',
            data: this.form
          }).then(res => {
            if (res.code === '200') {  // 表示成功保存
              this.$message.success('保存成功')
              this.load(1)
              this.fromVisible = false
            } else {
              this.$message.error(res.msg)  // 弹出错误的信息
            }
          })
        }
      })
    },
    del(id) {   // 单个删除
      this.$confirm('您确定删除吗？', '确认删除', { type: "warning" }).then(response => {
        this.$request.delete('/medicalExam/delete/' + id).then(res => {
          if (res.code === '200') {   // 表示操作成功
            this.$message.success('操作成功')
            this.load(1)
          } else {
            this.$message.error(res.msg)  // 弹出错误的信息
          }
        })
      }).catch(() => {
      })
    },
    handleSelectionChange(rows) {   // 当前选中的所有的行数据
      this.ids = rows.map(v => v.id)   //  [1,2]
    },
    delBatch() {   // 批量删除
      if (!this.ids.length) {
        this.$message.warning('请选择数据')
        return
      }
      this.$confirm('您确定批量删除这些数据吗？', '确认删除', { type: "warning" }).then(response => {
        this.$request.delete('/medicalExam/delete/batch', { data: this.ids }).then(res => {
          if (res.code === '200') {   // 表示操作成功
            this.$message.success('操作成功')
            this.load(1)
          } else {
            this.$message.error(res.msg)  // 弹出错误的信息
          }
        })
      }).catch(() => {
      })
    },
    load(pageNum) {  // 分页查询
      if (pageNum) this.pageNum = pageNum
      this.$request.get('/medicalExam/selectPage', {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          name: this.name,
        }
      }).then(res => {
        this.tableData = res.data?.list
        this.total = res.data?.total
      })
    },
    reset() {
      this.name = null
      this.load(1)
    },
    handleCurrentChange(pageNum) {
      this.load(pageNum)
    },
    handleCoverSuccess(res) {
      this.form.cover = res.data
    },
    setRichText(html) {
      this.$nextTick(() => {
        this.editor = new E(`#editor`)
        this.editor.config.uploadImgServer = this.$baseUrl + '/files/editor/upload'
        this.editor.config.uploadFileName = 'file'
        this.editor.config.uploadImgHeaders = {
          token: this.user.token
        }
        this.editor.config.uploadImgParams = {
          type: 'img',
        }
        this.editor.create()  // 创建
        this.editor.txt.html(html)
      })
    },
    preview(content) {
      this.content = content
      this.fromVisible1 = true
    },
  }
}
</script>

<style scoped>

</style>