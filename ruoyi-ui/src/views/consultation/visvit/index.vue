<template>
  <div class="app-container">
    <el-row>
      <el-col :span="12" class="left" :style="{ height: height }">
        <div class="button1">
          <el-button type="primary" @click="resetQuery()">叫号</el-button>
        </div>
        <el-table
          v-loading="loading"
          :data="roleList"
          :height="Height"
          @selection-change="handleSelectionChange"
        >
          <el-table-column label="挂号编号" prop="id" />
          <el-table-column
            label="姓名"
            prop="visitName"
            :show-overflow-tooltip="true"
            width="50"
          />
          <el-table-column
            label="性别"
            prop="visitSex"
            :show-overflow-tooltip="true"
            width="50"
          />
          <el-table-column
            label="创建时间"
            align="center"
            prop="createTime"
            width="180"
          >
            <template slot-scope="scope">
              <span>{{ parseTime(scope.row.createTime) }}</span>
            </template>
          </el-table-column>
          <el-table-column
            align="center"
            label="状态"
            prop="status"
            show-overflow-tooltip
          >
            <template slot-scope="scope">
              <el-tag
                disable-transitions
                :type="
                  scope.row.status == 1
                    ? 'success'
                    : scope.row.status == 3
                    ? 'danger'
                    : scope.row.status == 2
                    ? 'warning'
                    : 'primary'
                "
              >
                <span v-if="scope.row.status == 0">正在进行</span>
                <span v-else-if="scope.row.status == 1">待就诊</span>
                <span v-else-if="scope.row.status == 2">暂跳</span>
                <span v-else>已完成</span>
              </el-tag>
            </template>
          </el-table-column>
          <el-table-column
            label="操作"
            align="center"
            class-name="small-padding fixed-width"
          >
            <template slot-scope="scope">
              <el-button
                v-show="scope.row.status != 3"
                size="mini"
                type="text"
                @click="handleUpdate(scope.row)"
                >就诊</el-button
              >
              <el-button
                v-show="scope.row.status != 3 && scope.row.status != 2"
                size="mini"
                type="text"
                @click="jump(scope.row)"
                >暂跳</el-button
              >
            </template>
          </el-table-column>
        </el-table>
        <el-pagination
          background
          :current-page="search.pageNum"
          :layout="layout"
          :page-size="search.pageSize"
          :page-sizes="[5, 10, 20]"
          :total="total"
          @current-change="handleCurrentChange"
          @size-change="handleSizeChange"
        />
      </el-col>
      <el-col :span="12" class="left" :style="{ height: height }">
        <el-tabs v-model="activeName" @tab-click="handleClick">
          <el-tab-pane label="病患信息" name="first">
            <el-row :gutter="15">
              <el-col :span="24">
                <div class="title">病患基本信息</div>
              </el-col>
              <el-form
                ref="elForm"
                :model="formData1"
                size="medium"
                label-width="100px"
                label-position="left"
              >
                <el-col :span="11">
                  <el-form-item label="姓名">
                    <el-input
                      v-model="formData1.visitName"
                      placeholder="请输入姓名"
                      disabled
                      :maxlength="11"
                      :style="{ width: '100%' }"
                    ></el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="11">
                  <el-form-item label="性别">
                    <el-input
                      v-model="formData1.visitSex"
                      placeholder="请输入性别"
                      disabled
                      :style="{ width: '100%' }"
                    >
                    </el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="11">
                  <el-form-item label="年龄">
                    <el-input
                      v-model="formData1.visitAge"
                      placeholder="请输入年龄"
                      :maxlength="11"
                      disabled
                      :style="{ width: '100%' }"
                    ></el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="11">
                  <el-form-item label="手机号">
                    <el-input
                      v-model="formData1.phonenumber"
                      placeholder="请输入手机号"
                      :maxlength="11"
                      show-word-limit
                      disabled
                      :style="{ width: '100%' }"
                    ></el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="24">
                  <el-form-item label="就诊时间">
                    <el-input
                      v-model="formData1.time"
                      placeholder="请输入就诊时间"
                      disabled
                      :style="{ width: '100%' }"
                    >
                    </el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="24">
                  <el-form-item label="过敏信息">
                    <el-input
                      v-model="formData1.visitMemo"
                      type="textarea"
                      placeholder="请输入过敏信息"
                      disabled
                      :autosize="{ minRows: 4, maxRows: 4 }"
                      :style="{ width: '100%' }"
                    ></el-input>
                  </el-form-item>
                </el-col>
              </el-form>
            </el-row>
          </el-tab-pane>
          <el-tab-pane label="病情/诊断" name="second">
            <el-row :gutter="0">
              <el-form
                ref="elForm"
                :model="formData2"
                :rules="rules"
                size="mini"
                label-width="80px"
                label-position="left"
              >
                <el-col :span="24">
                  <div class="title">病情单</div>
                </el-col>
                <el-col :span="8">
                  <el-form-item
                    label-width="65px"
                    label="体温"
                    prop="temperature"
                  >
                    <el-input
                      v-model="formData2.temperature"
                      placeholder="请输入体温"
                      clearable
                      :style="{ width: '100%' }"
                    ></el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item
                    label-width="65px"
                    label="血压"
                    prop="bloodPressure"
                  >
                    <el-input
                      v-model="formData2.bloodPressure"
                      placeholder="请输入血压"
                      clearable
                      :style="{ width: '100%' }"
                    >
                    </el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item
                    label-width="65px"
                    label="心率"
                    prop="heartRate"
                  >
                    <el-input
                      v-model="formData2.heartRate"
                      placeholder="请输入心率"
                      clearable
                      :style="{ width: '100%' }"
                    >
                    </el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="24">
                  <el-form-item label="主诉" prop="mainNarration">
                    <el-input
                      v-model="formData2.mainNarration"
                      type="textarea"
                      placeholder="请输入主诉"
                      show-word-limit
                      :autosize="{ minRows: 4, maxRows: 4 }"
                      :style="{ width: '100%' }"
                    ></el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="24">
                  <el-form-item label="往病史/过敏信息" prop="lastMedical">
                    <el-input
                      v-model="formData2.lastMedical"
                      type="textarea"
                      placeholder="请输入往病史"
                      show-word-limit
                      :autosize="{ minRows: 4, maxRows: 4 }"
                      :style="{ width: '100%' }"
                    ></el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="24">
                  <el-form-item label="医师诊断" prop="diagnosis">
                    <el-input
                      v-model="formData2.diagnosis"
                      type="textarea"
                      placeholder="请输入医师诊断"
                      show-word-limit
                      :autosize="{ minRows: 4, maxRows: 4 }"
                      :style="{ width: '100%' }"
                    ></el-input>
                  </el-form-item>
                </el-col>
              </el-form> </el-row
          ></el-tab-pane>
          <el-tab-pane label="处方" name="three">
            <el-row>
              <el-form
                :model="queryParams"
                ref="queryForm"
                @submit.native.prevent
                size="small"
                :inline="true"
              >
                <div class="buttom-top">
                  <div>
                    <el-form-item label="药品名称" prop="drugName">
                      <el-input
                        v-model="queryParams.drugName"
                        placeholder="请输入药品名称"
                        clearable
                        @keyup.enter.native="handleQuery"
                        style="width: 240px"
                      />
                    </el-form-item>
                    <el-form-item>
                      <el-button
                        type="primary"
                        icon="el-icon-search"
                        size="mini"
                        @click="handleQuery"
                      >
                        搜索
                      </el-button>
                    </el-form-item>
                  </div>
                  <div>
                    <el-form-item>
                      <el-button
                        type="danger"
                        icon="el-icon-search"
                        size="mini"
                        @click="submit"
                        >提交</el-button
                      >
                    </el-form-item>
                  </div>
                </div>
              </el-form>
              <el-col :span="24">
                <el-table
                  v-loading="loading"
                  :data="drugList"
                  :height="thHeight"
                  @selection-change="handleSelectionChange"
                >
                  <el-table-column
                    label="药品名称"
                    prop="drugName"
                    :show-overflow-tooltip="true"
                    width="150"
                  />
                  <el-table-column
                    label="药品规格"
                    prop="drugSpecs"
                    :show-overflow-tooltip="true"
                    width="150"
                  />
                  <el-table-column
                    label="药品数量"
                    prop="drugStock"
                    :show-overflow-tooltip="true"
                    width="150"
                  />
                  <el-table-column
                    label="操作"
                    align="center"
                    class-name="small-padding fixed-width"
                  >
                    <template slot-scope="scope">
                      <el-button
                        size="mini"
                        type="text"
                        icon="el-icon-edit"
                        @click="handlecheck(scope.row)"
                        >选择</el-button
                      >
                    </template>
                  </el-table-column>
                </el-table>
              </el-col>
              <el-col :span="24">
                <div class="title">处方签</div>
                <el-table
                  v-loading="loading"
                  :data="outList"
                  :height="outHeight"
                  @selection-change="handleSelectionChange"
                  @cell-dblclick="tabClick"
                  :row-class-name="tableRowClassName"
                  :cell-class-name="tableCellClassName"
                >
                  <el-table-column
                    label="药品名称"
                    prop="drugName"
                    :show-overflow-tooltip="true"
                    width="150"
                  />
                  <el-table-column
                    prop="drugConsumption"
                    label="用法用量"
                    width="250"
                    align="center"
                  >
                    <template slot-scope="scope">
                      <span
                        v-if="
                          scope.row.index === tabClickIndex &&
                          tabClickLabel === '用法用量'
                        "
                      >
                        <el-input
                          v-model="scope.row.drugConsumption"
                          maxlength="300"
                          placeholder="请输入用法用量"
                          size="mini"
                          @blur="inputBlur"
                        />
                      </span>
                      <span v-else>{{ scope.row.drugConsumption }}</span>
                    </template>
                  </el-table-column>
                  <el-table-column
                    label="药品规格"
                    prop="drugSpecs"
                    :show-overflow-tooltip="true"
                    width="150"
                  />
                  <el-table-column
                    label="数量"
                    prop="drugNumber"
                    :show-overflow-tooltip="true"
                  />
                  <el-table-column
                    fixed="right"
                    label="操作"
                    align="center"
                    class-name="small-padding fixed-width"
                  >
                    <template slot-scope="scope" v-if="scope.row.roleId !== 1">
                      <el-button
                        size="mini"
                        type="text"
                        icon="el-icon-edit"
                        @click="handleDelete(scope.row)"
                        >删除</el-button
                      >
                    </template>
                  </el-table-column>
                </el-table>
              </el-col>
            </el-row>
          </el-tab-pane>
        </el-tabs>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import { mapState } from "vuex";
import { listManagement } from "@/api/pharmacy/management";
import {
  selectList,
  insertHallRecordEntityList,
  selectByid,
  insert,
  insertDiseaseTableEntity,
} from "@/api/consultation";
export default {
  data() {
    return {
      // 遮罩层
      loading: false,
      Height: "",
      height: "",
      roleList: [],
      showSearch: true,
      pageSize: 10,
      pageIndex: 1,
      layout: "total, sizes, prev, pager, next, jumper",
      total: 0,
      activeName: "first",
      search: {
        pageNum: 1,
        pageSize: 10,
        userId: undefined,
        f2: "ASC",
      },
      patientInfo: {},
      //--
      formData1: {},
      //--
      formData2: {
        temperature: "",
        bloodPressure: "",
        heartRate: "",
        mainNarration: "",
        nowIllness: "",
        lastMedical: "",
      },
      rules: {
        temperature: [
          {
            required: true,
            message: "请输入体温",
            trigger: "blur",
          },
        ],
        bloodPressure: [
          {
            required: true,
            message: "请输入血压",
            trigger: "blur",
          },
        ],
        heartRate: [
          {
            required: true,
            message: "请输入心率",
            trigger: "blur",
          },
        ],
        mainNarration: [
          {
            required: true,
            message: "请输入主诉",
            trigger: "blur",
          },
        ],
        lastMedical: [
          {
            required: true,
            message: "请输入往病史",
            trigger: "blur",
          },
        ],
        diagnosis: [
          {
            required: true,
            message: "请输入诊断信息",
            trigger: "blur",
          },
        ],
      },
      //--
      // 显示搜索条件
      queryParams: {
        pageNum: 1,
        pageSize: 10000,
        drugName: undefined,
        drugStatus: "0",
        drugCompany: undefined,
      },
      // 遮罩层
      loading: false,
      thHeight: "",
      outHeight: "",
      drugList: [],
      outList: [],
      tabClickIndex: null, // 点击的单元格
      tabClickLabel: "", // 当前点击的列名
      timer: null, //定时器名字
    };
  },
  computed: {
    ...mapState(["user"]),
  },
  created() {
    this.selectList();
    this.setTime();
  },
  mounted() {
    this.setTableHeight();
    window.addEventListener("resize", this.setTableHeight.bind(this));
  },
  beforeDestroy() {
    clearInterval(this.timer);
    this.timer = null;
  },
  methods: {
    setTime() {
      this.timer = setInterval(() => {
        setTimeout(() => {
          this.selectList();
        }, 0);
      }, 20000);
    },
    //清空数据
    clear() {
      this.outList = [];
      this.formData1 = {};
      this.formData2 = {};
      this.patientInfo = {};
    },
    //提交
    submit() {
      this.$refs["elForm"].validate(async (valid) => {
        if (valid) {
          console.log(this.outList);
          console.log(this.formData1);
          console.log(this.formData2);
          let data = {
            temperature: this.formData2.temperature,
            bloodPressure: this.formData2.bloodPressure,
            heartRate: this.formData2.heartRate,
            mainStatement: this.formData2.mainNarration,
            lastMedical: this.formData2.lastMedical,
            diagnosis: this.formData2.diagnosis,
            visitName: this.formData1.visitName,
            visitSex: this.formData1.visitSex,
            visitId: this.formData1.id,
            deptId: this.patientInfo.deptId,
            deptName: this.patientInfo.deptName,
            f1: this.patientInfo.userId,
            f2: this.patientInfo.userName,
            f3: this.patientInfo.id,
            labelList: this.outList,
            visitAge: this.formData1.visitAge,
          };
          const res = await insertDiseaseTableEntity(data);
          console.log(res);
          if (res.code == 200) {
            this.$message({
              message: "提交成功",
              type: "success",
            });
            this.clear();
            this.selectList();
          }
        } else {
          this.activeName = "second";
        }
      });
    },
    //暂跳
    jump(row) {
      this.$confirm("请确定叫号后已经等待了10分钟, 是否跳过该病患?", "提示", {
        cancelButtonText: "取消",
        confirmButtonText: "跳过",
        type: "warning",
      }).then(() => {
        row.status = "2";
        insert(row).then((res) => {
          this.selectList();
          this.$message({
            type: "success",
            message: "操作成功!",
          });
        });
      });
    },
    //删除处签方数据
    handleDelete(row) {
      console.log(row);
      this.outList.forEach((item, index) => {
        if (item.drugId == row.drugId) {
          this.outList.splice(index, 1);
        }
        this.$message({
          message: "删除成功",
          type: "success",
        });
      });
    },
    //处方选择
    handlecheck(row) {
      console.log(row);
      let data = {
        drugId: row.id,
        drugName: row.drugName,
        drugSpecs: row.drugSpecs,
        drugNumber: 1,
      };
      let x = 0;
      if (this.outList.length != 0) {
        this.outList.forEach((item) => {
          if (item.drugId == row.id) {
            x += 1;
          }
        });
        console.log(x);
        if (x == 0) {
          this.outList.push(data);
        } else {
          this.outList.forEach((item) => {
            if (item.drugId == row.id) {
              item.drugNumber += 1;
            }
          });
        }
      } else {
        this.outList.push(data);
      }
      console.log(this.outList);
    },
    resetQuery() {
      // console.log(this.$store.state.user.userId);
      console.log(this.roleList[0]);
      insertHallRecordEntityList(this.roleList[0]).then((res) => {
        console.log(res);
        if (res.code == 200) {
          this.$message({
            message: "叫号成功，请耐心等待",
            type: "success",
          });
        }
      });
    },
    //获取药品列表
    handleQuery() {
      listManagement(this.queryParams).then((response) => {
        console.log(response);
        this.drugList = response.rows;
      });
    },
    tabClick(row, column, cell, event) {
      switch (column.label) {
        case "用法用量":
          this.tabClickIndex = row.index;
          this.tabClickLabel = column.label;
          break;
        default:
          return;
      }
      console.log("添加明细原因", this.tabClickIndex, row, column, cell, event);
    },
    inputBlur(row, event, column) {
      var that = this;
      that.clickRow = null;
      that.clickCell = null;
      that.tabClickLabel = "";
    },
    //就诊
    async handleUpdate(row) {
      this.activeName = "first";
      this.patientInfo = row;
      row.status = "0";
      insert(row).then(() => {
        selectByid(row.visitId).then((res) => {
          console.log(res);
          this.formData1 = res.data;
          this.formatDate();
        });
      });
    },
    handleClick(tab, event) {
      console.log(tab, event);
    },
    handleSelectionChange() {},
    setTableHeight() {
      const paddingHeight = 291;
      this.Height = window.innerHeight - paddingHeight;
      this.height = window.innerHeight - paddingHeight + 150 + "px";
      this.thHeight = window.innerHeight - paddingHeight - 300;
      this.outHeight = window.innerHeight - paddingHeight - 400;
    },
    tableRowClassName({ row, rowIndex }) {
      row.index = rowIndex;
    },
    // 把每一列的索引放进column
    tableCellClassName({ column, columnIndex }) {
      column.index = columnIndex;
    },
    async selectList() {
      if (this.$store.state.user.userId != 1) {
        this.search.userId = this.$store.state.user.userId;
      }
      console.log(this.$store.state.user.userId);
      selectList(this.search).then((res) => {
        console.log(res);
        this.roleList = res.rows;
        this.total = res.total;
      });
    },
    formatDate(time = new Date()) {
      let date = new Date(time);

      let YY = date.getFullYear();
      let MM =
        date.getMonth() + 1 < 10
          ? "0" + (date.getMonth() + 1)
          : date.getMonth() + 1;
      let DD = date.getDate() < 10 ? "0" + date.getDate() : date.getDate();
      let hh = date.getHours() < 10 ? "0" + date.getHours() : date.getHours();
      let mm =
        date.getMinutes() < 10 ? "0" + date.getMinutes() : date.getMinutes();
      let ss =
        date.getSeconds() < 10 ? "0" + date.getSeconds() : date.getSeconds();
      this.formData1.time =
        YY + "-" + MM + "-" + DD + " " + hh + ":" + mm + ":" + ss;
    },
    handleCurrentChange(val) {
      console.log("Num", val);
      this.search.pageNum = val;
      this.selectList(); //查询数据
    },
    handleSizeChange(val) {
      console.log("pageSize", val);
      this.search.pageSize = val;
      this.selectList();
    },
  },
};
</script>

<style lang="scss" scoped>
.left {
  border: 1px solid rgba($color: #000000, $alpha: 0.1);
  margin-top: 10px;
  padding: 10px;
}
.title {
  text-align: center;
  font-size: 20px;
  margin-bottom: 10px;
}
.button1 {
  display: flex;
  justify-content: flex-end;
  margin-bottom: 10px;
}
.buttom-top {
  display: flex;
  justify-content: space-between;
}
</style>