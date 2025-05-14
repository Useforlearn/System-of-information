<template>
  <div class="main-content">
    <div style="font-size: 20px; margin-bottom: 20px">我报名的体检</div>
    <div class="table">
      <el-table size="medium" :data="tableData" strip @selection-change="handleSelectionChange">
        <el-table-column label="体检项目" width="300" show-overflow-tooltip>
          <template v-slot="scope">
            <a :href="'/front/medicalExamDetail?id=' + scope.row.examItemId">{{ scope.row.examItemName }}</a>
          </template>
        </el-table-column>
        <el-table-column prop="phone" label="联系方式"></el-table-column>
        <el-table-column prop="date" label="预约日期"></el-table-column>
        <el-table-column prop="status" label="审核状态">
          <template v-slot="scope">
            <el-tag type="primary" v-if="scope.row.status === '待审核'">待审核</el-tag>
            <el-tag type="success" v-if="scope.row.status === '审核通过'">审核通过</el-tag>
            <el-tag type="danger" v-if="scope.row.status === '审核拒绝'">审核拒绝</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="reason" label="审核意见"></el-table-column>
        <el-table-column label="操作" align="center" width="180">
          <template v-slot="scope">
            <el-button size="mini" type="danger" plain @click="del(scope.row.id)">取消报名</el-button>
          </template>
        </el-table-column>
      </el-table>

      <div style="margin: 10px 0">
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
  </div>
</template>

<script>
export default {
  name: "FrontMedicalExamSign",
  data() {
    return {
      tableData: [],
      pageNum: 1,
      pageSize: 10,
      total: 0,
      userName: null,
      fromVisible: false,
      form: {},
      user: JSON.parse(localStorage.getItem('xm-user') || '{}'),
      ids: []
    };
  },
  created() {
    this.load(1);
  },
  methods: {
    del(id) {
      this.$confirm('您确定取消吗？', '确认取消', { type: "warning" }).then(response => {
        this.$request.delete('/medicalExamSign/delete/' + id).then(res => {
          if (res.code === '200') {
            this.$message.success('操作成功');
            this.load(1);
          } else {
            this.$message.error(res.msg);
          }
        });
      }).catch(() => {});
    },
    handleSelectionChange(rows) {
      this.ids = rows.map(v => v.id);
    },
    delBatch() {
      if (!this.ids.length) {
        this.$message.warning('请选择数据');
        return;
      }
      this.$confirm('您确定批量删除这些数据吗？', '确认删除', { type: "warning" }).then(response => {
        this.$request.delete('/medicalExamSign/delete/batch', { data: this.ids }).then(res => {
          if (res.code === '200') {
            this.$message.success('操作成功');
            this.load(1);
          } else {
            this.$message.error(res.msg);
          }
        });
      }).catch(() => {});
    },
    load(pageNum) {
      if (pageNum) this.pageNum = pageNum;
      this.$request.get('/medicalExamSign/selectPage', {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          userName: this.userName
        }
      }).then(res => {
        this.tableData = res.data?.list;
        this.total = res.data?.total;
      });
    },
    reset() {
      this.userName = null;
      this.load(1);
    },
    handleCurrentChange(pageNum) {
      this.load(pageNum);
    }
  }
};
</script>

<style scoped>
</style>