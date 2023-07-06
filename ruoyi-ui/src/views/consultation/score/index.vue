<template>
  <div class="app-container">
    <el-table
      v-loading="loading"
      :data="roleList"
      :height="Height"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column
        label="评价编号"
        align="center"
        prop="id"
        :show-overflow-tooltip="true"
        width="180"
      />
      <el-table-column
        label="病情单id"
        prop="diseaseId"
        align="center"
        :show-overflow-tooltip="true"
      />
      <el-table-column
        label="评价分数"
        align="center"
        :show-overflow-tooltip="true"
        width="150"
        prop="fraction"
      >
        <template slot-scope="scope">
          <el-rate
            v-model="scope.row.fraction"
            disabled
            show-score
            text-color="#ff9900"
          >
          </el-rate>
        </template>
      </el-table-column>
      <el-table-column
        label="医师姓名"
        align="center"
        prop="userName"
        :show-overflow-tooltip="true"
        width="150"
      />
      <el-table-column
        label="评价人"
        align="center"
        prop="visitName"
        :show-overflow-tooltip="true"
        width="150"
      >
        <template slot-scope="scope">
          <span v-if="scope.row.isShow == 'true'">****</span>
          <span v-else-if="scope.row.isShow == 'false'">{{
            scope.row.visitName
          }}</span>
        </template>
      </el-table-column>
      <el-table-column
        label="创建时间"
        prop="createTime"
        align="center"
        :show-overflow-tooltip="true"
        width="150"
      >
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column
        label="操作"
        align="center"
        class-name="small-padding fixed-width"
      >
        <template slot-scope="scope">
          <el-button size="mini" type="text" @click="handledetail(scope.row)"
            >详情</el-button
          >
          <el-button
            type="text"
            size="mini"
            @click="handleUpdate(scope.row)"
            v-hasRole="['admin']"
            style="margin-right: 10px"
            >修改</el-button
          >
          <el-link
            :disabled="scope.row.payType == 0"
            :underline="false"
            type="danger"
            @click="handleDelete(scope.row.id)"
            v-hasRole="['admin']"
          >
            删除
          </el-link>
        </template>
      </el-table-column>
    </el-table>
    <div style="text-align: center">
      <el-pagination
        background
        :current-page="queryParams.pageNum"
        :layout="layout"
        :page-size="queryParams.pageSize"
        :page-sizes="[5, 10, 20]"
        :total="total"
        @current-change="handleCurrentChange"
        @size-change="handleSizeChange"
      />
    </div>

    <!-- 详情对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="800px" append-to-body>
      <el-form ref="form" :model="form" label-width="100px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="评价id" prop="id">
              <el-input disabled v-model="form.id" /> </el-form-item
          ></el-col>
          <el-col :span="12">
            <el-form-item label="病单id" prop="diseaseId">
              <el-input disabled v-model="form.diseaseId" /> </el-form-item
          ></el-col>
        </el-row>
        <el-row
          ><el-col :span="12">
            <el-form-item label="评分" prop="fraction">
              <el-input-number
                v-model="form.fraction"
                :min="1"
                step-strictly
                :disabled="title != '编辑评价'"
                :max="5"
                style="width: 100%"
              />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="理由" prop="memo">
              <el-input
                type="textarea"
                :rows="2"
                :disabled="title != '编辑评价'"
                placeholder="请输入内容"
                v-model="form.memo"
              />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="医师id" prop="userId">
              <el-input disabled v-model="form.userId" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="医师姓名" prop="userName">
              <el-input disabled v-model="form.userName" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="评价人" prop="visitName">
              <div>
                <div v-if="form.isShow == 'true'">
                  <el-input
                    disabled
                    v-model="person"
                    placeholder="请输入评价人"
                  />
                </div>
                <div v-else>
                  <el-input
                    disabled
                    v-model="form.visitName"
                    placeholder="请输入评价人"
                  />
                </div>
              </div>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="评价时间" prop="createTime">
              <el-date-picker
                disabled
                v-model="form.createTime"
                type="date"
                style="width: 100%"
              >
              </el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" v-hasRole="['admin']" @click="submitForm"
          >确 定</el-button
        >
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  selectScoreList,
  updateScore,
  deleteScore,
} from "@/api/consultation/index";

export default {
  name: "Management",
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 总条数
      total: 0,
      // 角色表格数据
      roleList: [],
      //角色筛选统计数组
      roles: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        userId: undefined,
      },
      layout: "total, sizes, prev, pager, next, jumper",
      total: 0,
      // 表单参数
      form: {},
      // 表单校验
      Height: "",
      person: "****",
    };
  },
  created() {
    this.getList();
  },
  mounted() {
    this.setTableHeight();
    window.addEventListener("resize", this.setTableHeight.bind(this));
  },
  methods: {
    /** 查询评价列表 */
    getList() {
      this.loading = true;
      if (this.$store.state.user.userId != 1) {
        this.queryParams.userId = this.$store.state.user.userId;
      }
      console.log(this.queryParams);
      selectScoreList(this.queryParams).then((response) => {
        console.log(response);
        this.roleList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    setTableHeight() {
      const paddingHeight = 291;
      this.Height = window.innerHeight - paddingHeight;
    },
    // 表单重置
    reset() {
      this.resetForm("form");
    },
    //删除评价
    handleDelete(row) {
      this.$modal
        .confirm("是否确认删除？")
        .then(function () {
          return deleteScore(row);
        })
        .then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        })
        .catch(() => {});
    },
    //修改功能
    handleUpdate(row) {
      this.title = "编辑评价";
      this.open = true;
      this.form = row;
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.id);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    handledetail(row) {
      this.title = "评价详情";
      this.open = true;
      this.form = row;
    },
    /** 提交按钮 */
    submitForm() {
      updateScore(this.form).then((response) => {
        console.log(response);
        this.$modal.msgSuccess("修改成功");
        this.open = false;
        this.getList();
      });
    },
    handleCurrentChange(val) {
      this.queryParams.pageNum = val;
      this.getList(); //查询数据
    },
    handleSizeChange(val) {
      this.queryParams.pageSize = val;
      this.getList();
    },
  },
};
</script>