<template>
  <div class="app-container">
    <el-row>
      <el-col :span="12" class="left" :style="{ height: height }">
        <el-form
          :model="search"
          ref="queryForm"
          size="small"
          :inline="true"
          v-show="showSearch"
        >
          <el-form-item label="处方号" prop="id">
            <el-input
              v-model="search.id"
              placeholder="请输入处方号"
              clearable
              style="width: 140px"
              @keyup.enter.native="list"
            />
          </el-form-item>
          <el-form-item label="姓名" prop="visitName">
            <el-input
              v-model="search.visitName"
              placeholder="请输入姓名"
              clearable
              style="width: 120px"
              @keyup.enter.native="list"
            />
          </el-form-item>
          <el-form-item>
            <el-button
              type="primary"
              icon="el-icon-search"
              size="mini"
              @click="list"
              >搜索</el-button
            >
            <el-button icon="el-icon-refresh" size="mini" @click="resetQuery"
              >重置</el-button
            >
          </el-form-item>
        </el-form>
        <el-table
          v-loading="loading"
          :data="dataList"
          :height="Height"
          @selection-change="handleSelectionChange"
        >
          <el-table-column
            :show-overflow-tooltip="true"
            label="处方号"
            prop="id"
            width="150"
            align="center"
          />
          <el-table-column
            label="姓名"
            align="center"
            prop="visitName"
            :show-overflow-tooltip="true"
            width="60"
          />
          <el-table-column
            label="性别"
            prop="visitSex"
            align="center"
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
            label="状态"
            prop="f3"
            align="center"
            :show-overflow-tooltip="true"
          >
            <template slot-scope="scope">
              <el-tag
                disable-transitions
                :type="scope.row.f3 == 0 ? 'success' : 'danger'"
              >
                <span v-if="scope.row.f3 == 0">待拿药</span>
                <span v-else>已完成</span>
              </el-tag>
            </template>
          </el-table-column>
          <el-table-column
            label="操作"
            align="center"
            class-name="small-padding fixed-width"
          >
            <template slot-scope="scope" v-if="scope.row.roleId !== 1">
              <el-button
                size="mini"
                type="text"
                icon="el-icon-edit"
                v-show="scope.row.f3 != 1"
                @click="handleCheck(scope.row)"
                >选择</el-button
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
        <el-form
          :model="queryParams"
          ref="queryForm"
          size="small"
          label-width="70px"
          v-show="showSearch"
        >
          <el-row type="flex" style="margin: 10px">
            <el-col :span="12" :push="5"
              ><div class="title">处方单</div></el-col
            >
            <el-col :span="12" :push="9">
              <div>
                <el-button @click="handleQuery">抓药</el-button>
              </div></el-col
            >
          </el-row>

          <el-row type="flex" justify="end">
            <el-form-item label="医师" prop="f2">
              <el-input v-model="queryParams.f2" disabled clearable />
            </el-form-item>
          </el-row>
          <el-form-item label="处方号" prop="id">
            <el-input
              disabled
              v-model="queryParams.id"
              placeholder="请输入处方号"
              clearable
            />
          </el-form-item>
          <el-form-item label="科室" prop="deptName">
            <el-input
              disabled
              v-model="queryParams.deptName"
              placeholder="请输入科室"
              clearable
            />
          </el-form-item>
          <el-form-item label="姓名" prop="visitName">
            <el-input
              disabled
              v-model="queryParams.visitName"
              placeholder="请输入姓名"
              clearable
            />
          </el-form-item>
          <el-form-item label="年龄" prop="f5">
            <el-input
              disabled
              v-model="queryParams.f5"
              placeholder="请输入年龄"
              clearable
            />
          </el-form-item>
          <el-form-item label="性别" prop="visitSex">
            <el-input
              disabled
              v-model="queryParams.visitSex"
              placeholder="请输入性别"
              clearable
            />
          </el-form-item>
          <el-form-item label="过敏史/往病史" prop="lastMedical">
            <el-input
              v-model="queryParams.lastMedical"
              type="textarea"
              disabled
              placeholder="请输入往病史"
              show-word-limit
              :autosize="{ minRows: 4, maxRows: 4 }"
              :style="{ width: '100%' }"
            ></el-input>
          </el-form-item>
        </el-form>
        <el-col :span="24">
          <div class="title">处方签</div>
          <el-table
            v-loading="loading"
            :data="lableList"
            :height="lableHeight"
            @selection-change="handleSelectionChange"
          >
            <el-table-column
              label="药品名称"
              prop="drugName"
              :show-overflow-tooltip="true"
              align="center"
            />
            <el-table-column
              prop="drugConsumption"
              label="用法用量"
              width="250"
              align="center"
            >
            </el-table-column>
            <el-table-column
              label="药品规格"
              prop="drugSpecs"
              :show-overflow-tooltip="true"
              align="center"
            />
            <el-table-column
              label="数量"
              prop="drugNumber"
              :show-overflow-tooltip="true"
              align="center"
            />
            <!-- <el-table-column
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
                  @click="handleUpdate(scope.row)"
                  v-hasPermi="['system:role:edit']"
                  >删除</el-button
                >
              </template>
            </el-table-column> -->
          </el-table>
        </el-col>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import {
  selectDiseaseTableEntityList,
  selectLabelTableEntityList,
  updateDiseaseTableEntity,
} from "@/api/consultation";
export default {
  data() {
    return {
      // 显示搜索条件
      search: {
        pageNum: 1,
        pageSize: 10,
        f4: "0",
        id: undefined,
        visitName: undefined,
      },
      queryParams: {},
      // 遮罩层
      loading: false,
      Height: "",
      height: "",
      lableHeight: "",
      lableList: [],
      dataList: [],
      showSearch: true,
      layout: "total, sizes, prev, pager, next, jumper",
      total: 0,
      timer: null, //定时器名字
    };
  },
  created() {
    this.list();
    this.setTime();
  },
  beforeDestroy() {
    clearInterval(this.timer);
    this.timer = null;
  },
  methods: {
    setTime() {
      this.timer = setInterval(() => {
        setTimeout(() => {
          this.list();
        }, 0);
      }, 20000);
    },
    resetQuery() {
      (this.search.id = undefined), (this.search.visitName = undefined);
      this.list();
    },
    //抓药
    async handleQuery() {
      if (this.queryParams.id == undefined) {
        this.$message.error("请选择处方后，再进行抓药");
        return;
      }
      const res = await updateDiseaseTableEntity(this.queryParams.id);
      console.log(res);
      if (res.code == 200) {
        this.$message({
          message: "操作成功",
          type: "success",
        });
        this.queryParams = {};
        this.lableList = [];
      }
      this.list();
    },
    async list() {
      const res = await selectDiseaseTableEntityList(this.search);
      console.log(res);
      if (res.code == 200) {
        this.dataList = res.rows;
        this.total = res.total;
      }
    },
    handleSelectionChange() {},
    setTableHeight() {
      const paddingHeight = 291;
      this.Height = window.innerHeight - paddingHeight;
      this.height = window.innerHeight - paddingHeight + 150 + "px";
      this.lableHeight = window.innerHeight - paddingHeight - 400;
    },
    handleCheck(row) {
      console.log(row);
      this.queryParams = row;
      selectLabelTableEntityList(row).then((res) => {
        console.log(res);
        this.lableList = res.rows;
      });
    },
    handleCurrentChange(val) {
      this.search.pageNum = val;
      this.list(); //查询数据
    },
    handleSizeChange(val) {
      this.search.pageSize = val;
      this.list();
    },
  },
  mounted() {
    this.setTableHeight();
    window.addEventListener("resize", this.setTableHeight.bind(this));
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
}
</style>