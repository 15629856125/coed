<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryForm"
      size="small"
      :inline="true"
      v-show="showSearch"
    >
      <el-form-item label="药品名称" prop="drugName">
        <el-input
          v-model="queryParams.drugName"
          placeholder="请输入药品名称"
          clearable
          style="width: 240px"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="药品属性" prop="drugCompany">
        <el-select
          v-model="queryParams.drugCompany"
          placeholder="请选择药品属性"
          clearable
        >
          <el-option
            v-for="dict in dict.type.drug_company"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <!-- <el-form-item label="药品状态" prop="status">
        <el-select
          v-model="queryParams.status"
          placeholder="药品状态"
          clearable
          style="width: 240px"
        >
          <el-option label="启用" value="0"></el-option>
          <el-option label="禁用" value="1"></el-option>
        </el-select>
      </el-form-item> -->
      <el-form-item>
        <el-button
          type="primary"
          icon="el-icon-search"
          size="mini"
          @click="handleQuery"
          >搜索</el-button
        >
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery"
          >重置</el-button
        >
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['system:management:add']"
          >新增</el-button
        >
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['system:management:edit']"
          >修改</el-button
        >
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['system:management:remove']"
          >删除</el-button
        >
      </el-col>
      <right-toolbar
        :showSearch.sync="showSearch"
        @queryTable="getList"
      ></right-toolbar>
    </el-row>

    <el-table
      v-loading="loading"
      :data="roleList"
      :height="Height"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column
        label="药品编号"
        align="center"
        prop="id"
        :show-overflow-tooltip="true"
        width="120"
      />
      <el-table-column
        label="药品名称"
        prop="drugName"
        align="center"
        :show-overflow-tooltip="true"
        width="150"
      />
      <el-table-column
        label="药品剂型"
        align="center"
        :show-overflow-tooltip="true"
        width="150"
        prop="drugCompany"
      >
        <template slot-scope="scope">
          <dict-tag
            :options="dict.type.drug_company"
            :value="scope.row.drugCompany"
          />
        </template>
      </el-table-column>
      <el-table-column
        label="规格"
        align="center"
        prop="drugSpecs"
        :show-overflow-tooltip="true"
        width="150"
      />
      <el-table-column
        label="规格单位"
        align="center"
        prop="drugGgdw"
        :show-overflow-tooltip="true"
        width="150"
      />
      <el-table-column
        label="是否限制药"
        prop="isLimit"
        align="center"
        :show-overflow-tooltip="true"
        width="150"
      >
        <template slot-scope="scope">
            <span v-if="scope.row.isLimit == 0">是</span>
            <span v-else-if="scope.row.isLimit == 1">否</span>
        </template>
      </el-table-column>
      <el-table-column
        label="药品价格"
        prop="drugPrice"
        align="center"
        :show-overflow-tooltip="true"
        width="150"
      />
      <el-table-column
        label="药品库存"
        prop="drugStock"
        align="center"
        width="100"
      />
      <el-table-column
        label="生产厂家"
        prop="manufacturer"
        align="center"
        width="150"
      />
      <el-table-column label="状态" align="center" width="100">
        <template slot-scope="scope">
          <el-tag
            disable-transitions
            :type="scope.row.drugStatus == 1 ? 'danger' : 'success'"
          >
            <span v-if="scope.row.drugStatus == 0">启用</span>
            <span v-else-if="scope.row.drugStatus == 1">停用</span>
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column
        label="生产日期"
        align="center"
        prop="drugCreatetime"
        width="180"
      >
      </el-table-column>
      <el-table-column
        label="有效日期"
        align="center"
        prop="drugEndtime"
        width="180"
      >
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
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:management:edit']"
            >修改</el-button
          >
          <el-button
            size="mini"
            type="text"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:management:remove']"
            >删除</el-button
          >
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

    <!-- 添加或修改药品配置对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="800px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="药品名称" prop="drugName">
              <el-input
                v-model="form.drugName"
                placeholder="请输入药品名称"
              /> </el-form-item
          ></el-col>
          <el-col :span="12">
            <el-form-item label="药品数量" prop="drugStock">
              <el-input-number
                v-model="form.drugStock"
                placeholder="请输入药品数量"
              /> </el-form-item
          ></el-col>
        </el-row>
        <el-row
          ><el-col :span="12">
            <el-form-item label="药品价格" prop="drugPrice">
              <el-input-number
                v-model="form.drugPrice"
                :min="0"
              /> </el-form-item></el-col
          ><el-col :span="12">
            <el-form-item label="状态" prop="drugStatus">
              <el-select
                v-model="form.drugStatus"
                placeholder="请选择状态"
                clearable
                style="width: 240px"
              >
                <el-option label="启用" value="0" />
                <el-option label="禁用" value="1" />
              </el-select> </el-form-item></el-col
        ></el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="剂型" prop="drugCompany">
              <el-select
                v-model="form.drugCompany"
                placeholder="请选择药品剂型"
              >
                <el-option
                  v-for="dict in dict.type.drug_company"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="是否限制药" prop="isLimit">
              <el-select
                v-model="form.isLimit"
                placeholder="是否限制药"
                clearable
                style="width: 240px"
              >
                <el-option label="是" value="0"></el-option>
                <el-option label="否" value="1"></el-option>
              </el-select> </el-form-item></el-col
        ></el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="规格" prop="drugSpecs">
              <el-input v-model="form.drugSpecs" placeholder="请输入药品规格" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="规格单位" prop="drugGgdw">
              <el-select
                v-model="form.drugGgdw"
                placeholder="规格单位"
                clearable
                style="width: 240px"
              >
                <el-option label="盒" value="盒"></el-option>
                <el-option label="瓶" value="瓶"></el-option>
                <el-option label="片" value="片"></el-option>
                <el-option label="板" value="板"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row
          ><el-col :span="12">
            <el-form-item label="生产厂家" prop="manufacturer">
              <el-input
                v-model="form.manufacturer"
                placeholder="请输入生产厂家"
              /> </el-form-item></el-col
          ><el-col :span="12">
            <el-form-item label="功效" prop="drugEffect">
              <el-input
                v-model="form.drugEffect"
                type="textarea"
                placeholder="请输入功效"
              ></el-input> </el-form-item></el-col
        ></el-row>
        <el-row
          ><el-col :span="12">
            <el-form-item label="生产日期" prop="drugCreatetime">
              <el-date-picker
                clearable
                v-model="form.drugCreatetime"
                type="date"
                value-format="yyyy-MM-dd"
                placeholder="请选择生产日期"
              >
              </el-date-picker>
            </el-form-item> </el-col
          ><el-col :span="12">
            <el-form-item label="有效日期" prop="drugEndtime">
              <el-date-picker
                clearable
                v-model="form.drugEndtime"
                type="date"
                value-format="yyyy-MM-dd"
                placeholder="请选择有效日期"
              >
              </el-date-picker> </el-form-item></el-col
        ></el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  listManagement,
  getManagement,
  delManagement,
  addManagement,
  updateManagement,
} from "@/api/pharmacy/management";
export default {
  name: "Management",
  dicts: ["drug_company"],
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
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 角色表格数据
      roleList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 是否显示弹出层（数据权限）
      openDataScope: false,
      menuExpand: false,
      menuNodeAll: false,
      deptExpand: true,
      deptNodeAll: false,
      // 日期范围
      dateRange: [],
      // 菜单列表
      menuOptions: [],
      // 部门列表
      deptOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        drugName: undefined,
        drugStatus: undefined,
        drugCompany: undefined,
      },
      pageSize: 10,
      pageIndex: 1,
      layout: "total, sizes, prev, pager, next, jumper",
      total: 0,
      companys: [
        { leble: "片剂", value: "片剂" },
        { leble: "胶囊剂", value: "胶囊剂" },
        { leble: "薄膜衣片", value: "薄膜衣片" },
        { leble: "粉剂", value: "粉剂" },
      ],

      // 表单参数
      form: {},
      // 表单校验
      rules: {
        drugName: [
          { required: true, message: "药品名称不能为空", trigger: "blur" },
        ],
        drugStock: [
          { required: true, message: "药品数量不能为空", trigger: "blur" },
          {
            message: "只能是数字",
            trigger: "change",
            pattern: /^(([1-9]\d{0,3})|0)?$/,
          },
        ],
        drugPrice: [
          { required: true, message: "药品价格不能为空", trigger: "blur" },
        ],
        drugStatus: [
          { required: true, message: "请选择状态", trigger: "blur" },
        ],
        drugCreatetime: [
          { required: true, message: "请选择生产日期", trigger: "blur" },
        ],
        drugEndtime: [
          { required: true, message: "请选择有效日期", trigger: "blur" },
        ],
        drugCompany: [
          { required: true, message: "请选择药品剂型", trigger: "blur" },
        ],
        drugSpecs: [
          { required: true, message: "请输入药品规格", trigger: "blur" },
        ],
        drugGgdw: [
          { required: true, message: "请选择药品规格单位", trigger: "blur" },
        ],
        isLimit: [
          { required: true, message: "请选择是否为限制药", trigger: "blur" },
        ],
        drugEffect: [
          { required: true, message: "请输入药品功效", trigger: "blur" },
        ],
        manufacturer: [
          { required: true, message: "请输入生产厂家", trigger: "blur" },
        ],
      },
      Height: "",
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
    /** 查询药品管理列表 */
    getList() {
      this.loading = true;
      listManagement(this.queryParams).then((response) => {
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
      this.form = {
        id: null,
        drugName: null,
        drugCompany: null,
        drugSpecs: null,
        drugPrice: null,
        drugGgdw: null,
        isLimit: null,
        drugStock: null,
        drugEffect: null,
        manufacturer: null,
        drugStatus: "0",
        drugCreatetime: null,
        drugEndtime: null,
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.id);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "入库药品";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getManagement(id).then((response) => {
        this.form = response.data;
        this.open = true;
        this.title = "编辑药品";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.id != null) {
            updateManagement(this.form).then((response) => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addManagement(this.form).then((response) => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal
        .confirm('是否确认删除药品管理编号为"' + ids + '"的数据项？')
        .then(function () {
          return delManagement(ids);
        })
        .then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        })
        .catch(() => {});
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