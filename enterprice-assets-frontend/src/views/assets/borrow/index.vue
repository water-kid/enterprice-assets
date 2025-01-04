<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="资产id" prop="assetsId">
        <el-input
            v-model="queryParams.assetsId"
            placeholder="请输入资产id"
            clearable
            @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="谁借的" prop="borrower">
        <el-input
            v-model="queryParams.borrower"
            placeholder="请输入谁借的"
            clearable
            @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="借出时间" prop="borrowDate">
        <el-date-picker clearable
                        v-model="queryParams.borrowDate"
                        type="date"
                        value-format="YYYY-MM-DD"
                        placeholder="请选择借出时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="归还时间" prop="returnDate">
        <el-date-picker clearable
                        v-model="queryParams.returnDate"
                        type="date"
                        value-format="YYYY-MM-DD"
                        placeholder="请选择归还时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="状态,0:借出 1:归还" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择状态,0:借出 1:归还" clearable>
          <el-option
              v-for="dict in borrow_status"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
        <el-button icon="Refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

<!--    <el-row :gutter="10" class="mb8">-->
<!--      <el-col :span="1.5">-->
<!--        <el-button-->
<!--            type="primary"-->
<!--            plain-->
<!--            icon="Plus"-->
<!--            @click="handleAdd"-->
<!--            v-hasPermi="['assets:loans:add']"-->
<!--        >新增</el-button>-->
<!--      </el-col>-->
<!--      <el-col :span="1.5">-->
<!--        <el-button-->
<!--            type="success"-->
<!--            plain-->
<!--            icon="Edit"-->
<!--            :disabled="single"-->
<!--            @click="handleUpdate"-->
<!--            v-hasPermi="['assets:loans:edit']"-->
<!--        >修改</el-button>-->
<!--      </el-col>-->
<!--      <el-col :span="1.5">-->
<!--        <el-button-->
<!--            type="danger"-->
<!--            plain-->
<!--            icon="Delete"-->
<!--            :disabled="multiple"-->
<!--            @click="handleDelete"-->
<!--            v-hasPermi="['assets:loans:remove']"-->
<!--        >删除</el-button>-->
<!--      </el-col>-->
<!--      <el-col :span="1.5">-->
<!--        <el-button-->
<!--            type="warning"-->
<!--            plain-->
<!--            icon="Download"-->
<!--            @click="handleExport"-->
<!--            v-hasPermi="['assets:loans:export']"-->
<!--        >导出</el-button>-->
<!--      </el-col>-->
<!--      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>-->
<!--    </el-row>-->

    <el-table v-loading="loading" :data="loansList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="id" align="center" prop="id" />
      <el-table-column label="资产id" align="center" prop="assetsId" />
      <el-table-column label="借的数量" align="center" prop="borrowCount" />

      <el-table-column label="谁借的" align="center" prop="borrower" />
      <el-table-column label="借出时间" align="center" prop="borrowDate" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.borrowDate) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="归还时间" align="center" prop="returnDate" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.returnDate) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="状态,0:借出 1:归还" align="center" prop="status">
        <template #default="scope">
          <dict-tag :options="borrow_status" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="创建者" align="center" prop="createBy" />
      <el-table-column label="更新者" align="center" prop="updateBy" />
      <el-table-column label="创建时间" align="center" prop="createTime" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.createTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="更新时间" align="center" prop="updateTime" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.updateTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="success" icon="Edit" @click="handleBack(scope.row)" v-hasPermi="['assets:loans:edit']">归还</el-button>
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['assets:loans:edit']">设置备注</el-button>
<!--          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['assets:loans:remove']">删除</el-button>-->
        </template>
      </el-table-column>
    </el-table>

    <pagination
        v-show="total>0"
        :total="total"
        v-model:page="queryParams.pageNum"
        v-model:limit="queryParams.pageSize"
        @pagination="getList"
    />

    <!-- 添加或修改外借信息对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="loansRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" />
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup name="Loans">
import { listLoans, getLoans, delLoans, addLoans, updateLoans } from "@/api/assets/loans";
import {parseTime} from "../../../utils/ruoyi.js";
import {returnAssets} from "@/api/assets/assets.js";

const { proxy } = getCurrentInstance();
const { borrow_status } = proxy.useDict('borrow_status');

const loansList = ref([]);
const open = ref(false);
const loading = ref(true);
const showSearch = ref(true);
const ids = ref([]);
const single = ref(true);
const multiple = ref(true);
const total = ref(0);
const title = ref("");

const data = reactive({
  form: {},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    assetsId: null,
    borrower: null,
    borrowDate: null,
    returnDate: null,
    status: null,
  },
  rules: {
    assetsId: [
      { required: true, message: "资产id不能为空", trigger: "blur" }
    ],
  }
});

const { queryParams, form, rules } = toRefs(data);

/** 查询外借信息列表 */
function getList() {
  loading.value = true;
  listLoans(queryParams.value).then(response => {
    loansList.value = response.rows;
    total.value = response.total;
    loading.value = false;
  });
}

// 取消按钮
function cancel() {
  open.value = false;
  reset();
}

// 表单重置
function reset() {
  form.value = {
    id: null,
    assetsId: null,
    borrower: null,
    borrowDate: null,
    returnDate: null,
    status: null,
    createBy: null,
    updateBy: null,
    createTime: null,
    updateTime: null,
    remark: null,
    borrowCount: null
  };
  proxy.resetForm("loansRef");
}

/** 搜索按钮操作 */
function handleQuery() {
  queryParams.value.pageNum = 1;
  getList();
}

/** 重置按钮操作 */
function resetQuery() {
  proxy.resetForm("queryRef");
  handleQuery();
}

// 多选框选中数据
function handleSelectionChange(selection) {
  ids.value = selection.map(item => item.id);
  single.value = selection.length != 1;
  multiple.value = !selection.length;
}

/** 新增按钮操作 */
function handleAdd() {
  reset();
  open.value = true;
  title.value = "添加外借信息";
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const _id = row.id || ids.value
  getLoans(_id).then(response => {
    form.value = response.data;
    open.value = true;
    title.value = "修改外借信息";
  });
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["loansRef"].validate(valid => {
    if (valid) {
      if (form.value.id != null) {
        updateLoans(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addLoans(form.value).then(response => {
          proxy.$modal.msgSuccess("新增成功");
          open.value = false;
          getList();
        });
      }
    }
  });
}

/** 删除按钮操作 */
function handleDelete(row) {
  const _ids = row.id || ids.value;
  proxy.$modal.confirm('是否确认删除外借信息编号为"' + _ids + '"的数据项？').then(function () {
    return delLoans(_ids);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("删除成功");
  }).catch(() => {
  });
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('assets/loans/export', {
    ...queryParams.value
  }, `loans_${new Date().getTime()}.xlsx`)
}

getList();

// 归还资源
const handleBack = (row)=>{

  proxy.$modal.confirm('是否归还外借信息编号为"' + row.id + '"的资源？').then(function () {
    return returnAssets(row.id)
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("归还成功");
  }).catch(() => {
  });
}
</script>
