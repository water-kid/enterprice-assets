<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="账号" prop="account">
        <el-input
          v-model="queryParams.account"
          placeholder="请输入账号"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="店铺名称" prop="shopName">
        <el-input
          v-model="queryParams.shopName"
          placeholder="请输入店铺名称"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="归属人" prop="ownerName">
        <el-input
          v-model="queryParams.ownerName"
          placeholder="请输入归属人"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="经营状态" prop="operationStatus">
        <el-select v-model="queryParams.operationStatus" placeholder="请选择经营状态" clearable>
          <el-option
            v-for="dict in shop_status"
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

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="Plus"
          @click="handleAdd"
          v-hasPermi="['assets:shop:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['assets:shop:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['assets:shop:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['assets:shop:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="shopList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" align="center" prop="id" />
      <el-table-column label="编码" align="center" prop="code" />
      <el-table-column label="平台" align="center" prop="platform" />
      <el-table-column label="账号" align="center" prop="account" />
      <el-table-column label="店铺名称" align="center" prop="shopName" />
      <el-table-column label="归属人" align="center" prop="ownerName" />
      <el-table-column label="官方账号所绑定手机" align="center" prop="officialAccountTel" />
      <el-table-column label="官方账号归属人" align="center" prop="officialAccountOwner" />
      <el-table-column label="作者保证金" align="center" prop="authorDeposit" />
      <el-table-column label="营业执照(图)" align="center" prop="businessLicenseImg" width="100">
        <template #default="scope">
          <image-preview :src="scope.row.businessLicenseImg" :width="50" :height="50"/>
        </template>
      </el-table-column>
      <el-table-column label="法人" align="center" prop="legalPerson" />
      <el-table-column label="保证金金额" align="center" prop="deposit" />
      <el-table-column label="经营状态" align="center" prop="operationStatus">
        <template #default="scope">
          <dict-tag :options="shop_status" :value="scope.row.operationStatus"/>
        </template>
      </el-table-column>
      <el-table-column label="备注" align="center" prop="remarks" />
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
      <el-table-column label="创建人" align="center" prop="createBy" />
      <el-table-column label="更新人" align="center" prop="updateBy" />
      <el-table-column label="备注说明" align="center" prop="remark" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" fixed="right">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['assets:shop:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['assets:shop:remove']">删除</el-button>
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



    <!-- 添加或修改店铺详情统计对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="shopRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="编码" prop="code">
          <el-input v-model="form.code" placeholder="请输入编码" />
          <el-switch v-model="form.isGenerateCode" @change="handleGenerateCode"/>
        </el-form-item>
        <el-form-item label="平台" prop="platform">
          <el-input v-model="form.platform" placeholder="请输入平台" />
        </el-form-item>
        <el-form-item label="账号" prop="account">
          <el-input v-model="form.account" placeholder="请输入账号" />
        </el-form-item>
        <el-form-item label="店铺名称" prop="shopName">
          <el-input v-model="form.shopName" placeholder="请输入店铺名称" />
        </el-form-item>
        <el-form-item label="归属人" prop="ownerName">
          <el-input v-model="form.ownerName" placeholder="请输入归属人" />
        </el-form-item>
        <el-form-item label="官方账号所绑定手机" prop="officialAccountTel">
          <el-input v-model="form.officialAccountTel" placeholder="请输入官方账号所绑定手机" />
        </el-form-item>
        <el-form-item label="官方账号归属人" prop="officialAccountOwner">
          <el-input v-model="form.officialAccountOwner" placeholder="请输入官方账号归属人" />
        </el-form-item>
        <el-form-item label="作者保证金" prop="authorDeposit">
          <el-input v-model="form.authorDeposit" placeholder="请输入作者保证金" />
        </el-form-item>
        <el-form-item label="营业执照(图)" prop="businessLicenseImg">
          <image-upload v-model="form.businessLicenseImg"/>
        </el-form-item>
        <el-form-item label="法人" prop="legalPerson">
          <el-input v-model="form.legalPerson" placeholder="请输入法人" />
        </el-form-item>
        <el-form-item label="保证金金额" prop="deposit">
          <el-input v-model="form.deposit" placeholder="请输入保证金金额" />
        </el-form-item>
        <el-form-item label="经营状态" prop="operationStatus">
          <el-select v-model="form.operationStatus" placeholder="请选择经营状态">
            <el-option
              v-for="dict in shop_status"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="备注" prop="remarks">
          <el-input v-model="form.remarks" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="备注说明" prop="remark">
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

<script setup name="Shop">
import { listShop, getShop, delShop, addShop, updateShop } from "@/api/assets/shop";
import {generateUniqueCode} from "@/utils/utils.js";
import {parseTime} from "../../../utils/ruoyi.js";

const { proxy } = getCurrentInstance();
const { shop_status } = proxy.useDict('shop_status');

const handleGenerateCode = (e)=>{
  if (e){
    let code  = generateUniqueCode("shop")
    console.log(e,"e",code)
    form.value.code = code
  }else{
    form.value.code = ""
  }

}

const shopList = ref([]);
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
    account: null,
    shopName: null,
    ownerName: null,
    operationStatus: null,
  },
  rules: {
    createTime: [
      { required: true, message: "创建时间不能为空", trigger: "blur" }
    ],
    updateTime: [
      { required: true, message: "更新时间不能为空", trigger: "blur" }
    ],
  }
});

const { queryParams, form, rules } = toRefs(data);

/** 查询店铺详情统计列表 */
function getList() {
  loading.value = true;
  listShop(queryParams.value).then(response => {
    shopList.value = response.rows;
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
    platform: null,
    account: null,
    shopName: null,
    ownerName: null,
    officialAccountTel: null,
    officialAccountOwner: null,
    authorDeposit: null,
    businessLicenseImg: null,
    legalPerson: null,
    deposit: null,
    operationStatus: null,
    remarks: null,
    createTime: null,
    updateTime: null,
    createBy: null,
    updateBy: null,
    remark: null
  };
  proxy.resetForm("shopRef");
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
  title.value = "添加店铺详情统计";
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const _id = row.id || ids.value
  getShop(_id).then(response => {
    form.value = response.data;
    open.value = true;
    title.value = "修改店铺详情统计";
  });
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["shopRef"].validate(valid => {
    if (valid) {
      if (form.value.id != null) {
        updateShop(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addShop(form.value).then(response => {
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
  proxy.$modal.confirm('是否确认删除店铺详情统计编号为"' + _ids + '"的数据项？').then(function() {
    return delShop(_ids);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("删除成功");
  }).catch(() => {});
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('assets/shop/export', {
    ...queryParams.value
  }, `shop_${new Date().getTime()}.xlsx`)
}

getList();
</script>
