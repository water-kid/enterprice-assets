<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="使用人" prop="consumer">
        <el-input
          v-model="queryParams.consumer"
          placeholder="请输入使用人"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="所属手机编码" prop="phoneCode">
        <el-input
          v-model="queryParams.phoneCode"
          placeholder="请输入所属手机编码"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="实名" prop="informationName">
        <el-input
          v-model="queryParams.informationName"
          placeholder="请输入实名"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="手机号" prop="tel">
        <el-input
          v-model="queryParams.tel"
          placeholder="请输入手机号"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="号码状态" prop="numberStatus">
        <el-select v-model="queryParams.numberStatus" placeholder="请选择号码状态" clearable>
          <el-option
            v-for="dict in number_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="资产状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择资产状态" clearable>
          <el-option
            v-for="dict in assets_status"
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
          v-hasPermi="['assets:phone:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['assets:phone:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['assets:phone:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['assets:phone:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="phoneList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" align="center" prop="id" />
      <el-table-column label="手机编码" align="center" prop="phoneCode" />
      <el-table-column label="所属部门" align="center" prop="department" />
      <el-table-column label="使用人" align="center" prop="consumer" />

      <el-table-column label="实名" align="center" prop="informationName" />
      <el-table-column label="手机号" align="center" prop="tel" />
      <el-table-column label="运营商" align="center" prop="operator" />
      <el-table-column label="套餐" align="center" prop="plan" />
      <el-table-column label="号码状态" align="center" prop="numberStatus">
        <template #default="scope">
          <dict-tag :options="number_status" :value="scope.row.numberStatus"/>
        </template>
      </el-table-column>
      <el-table-column label="资产状态" align="center" prop="status">
        <template #default="scope">
          <dict-tag :options="assets_status" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="二维码图片" align="center" prop="img" width="100">
        <template #default="scope">
          <image-preview :src="scope.row.img" :width="50" :height="50"/>
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
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['assets:phone:edit']">修改</el-button>
          <el-button link type="primary" icon="Edit" @click="handleGenerateCodeImg(scope.row)" v-hasPermi="['assets:phone:edit']">生成二维码</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['assets:phone:remove']">删除</el-button>
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

    <el-dialog title="code图片" v-model="codeImageDialogVisible" width="500px" append-to-body>
      <img :src="codeImage">
    </el-dialog>

    <!-- 添加或修改手机号管理与使用对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="phoneRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="所属部门" prop="department">
          <el-input v-model="form.department" placeholder="请输入所属部门" />
        </el-form-item>
        <el-form-item label="使用人" prop="consumer">
          <el-input v-model="form.consumer" placeholder="请输入使用人" />
        </el-form-item>
        <el-form-item label="所属手机编码" prop="phoneCode">
          <el-input v-model="form.phoneCode" placeholder="请输入手机编码" />
          <el-switch v-model="form.isGenerateCode" @change="handleGenerateCode"/>
        </el-form-item>
        <el-form-item label="实名" prop="informationName">
          <el-input v-model="form.informationName" placeholder="请输入实名" />
        </el-form-item>
        <el-form-item label="手机号" prop="tel">
          <el-input v-model="form.tel" placeholder="请输入手机号" />
        </el-form-item>
        <el-form-item label="运营商" prop="operator">
          <el-input v-model="form.operator" placeholder="请输入运营商" />
        </el-form-item>
        <el-form-item label="套餐" prop="plan">
          <el-input v-model="form.plan" placeholder="请输入套餐" />
        </el-form-item>
        <el-form-item label="号码状态" prop="numberStatus">
          <el-radio-group v-model="form.numberStatus">
            <el-radio
              v-for="dict in number_status"
              :key="dict.value"
              :label="dict.value"
            >{{dict.label}}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="资产状态" prop="status">
          <el-radio-group v-model="form.status">
            <el-radio
              v-for="dict in assets_status"
              :key="dict.value"
              :label="dict.value"
            >{{dict.label}}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="二维码图片" prop="img">
          <image-upload v-model="form.img"/>
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

<script setup name="Phone">
import { listPhone, getPhone, delPhone, addPhone, updatePhone } from "@/api/assets/phone";
import {generateUniqueCode} from "@/utils/utils.js";
import {generateCodeImg} from "@/api/assets/codeImg.js";
import {ElMessage} from "element-plus";

const codeImageDialogVisible = ref(false)
const codeImage = ref("")

const  handleGenerateCodeImg = async (row) => {
  if (!row.phoneCode){
    ElMessage.error("code不能为空")
    return
  }

  let response = await generateCodeImg(row.phoneCode)
  // console.log(response,response instanceof Blob,"blob")
  codeImageDialogVisible.value = true
  // const blob =  response.data
  // var reader = new FileReader();
  // reader.onload = function (event){
  //   codeImage.value = event.target.result
  // }
  // reader.readAsDataURL(response.data)
  // console.log(blob instanceof Blob,"blob")
  var url = URL.createObjectURL(response);
  // console.log( "Res", url)
  codeImage.value = url
}


const { proxy } = getCurrentInstance();
const { assets_status, number_status } = proxy.useDict('assets_status', 'number_status');

// const isGenerateCode = ref()
const handleGenerateCode = (e)=>{
  if (e){
    let code  = generateUniqueCode("phone")
    console.log(e,"e",code)
    form.value.phoneCode = code
  }else{
    form.value.phoneCode = ""
  }

}

const phoneList = ref([]);
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
    consumer: null,
    phoneCode: null,
    informationName: null,
    tel: null,
    numberStatus: null,
    status: null,
    remarks: null,
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

/** 查询手机号管理与使用列表 */
function getList() {
  loading.value = true;
  listPhone(queryParams.value).then(response => {
    phoneList.value = response.rows;
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
    department: null,
    consumer: null,
    phoneCode: null,
    informationName: null,
    tel: null,
    operator: null,
    plan: null,
    numberStatus: null,
    status: null,
    img: null,
    remarks: null,
    createTime: null,
    updateTime: null,
    createBy: null,
    updateBy: null,
    remark: null
  };
  proxy.resetForm("phoneRef");
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
  title.value = "添加手机号管理与使用";
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const _id = row.id || ids.value
  getPhone(_id).then(response => {
    form.value = response.data;
    open.value = true;
    title.value = "修改手机号管理与使用";
  });
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["phoneRef"].validate(valid => {
    if (valid) {
      if (form.value.id != null) {
        updatePhone(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addPhone(form.value).then(response => {
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
  proxy.$modal.confirm('是否确认删除手机号管理与使用编号为"' + _ids + '"的数据项？').then(function() {
    return delPhone(_ids);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("删除成功");
  }).catch(() => {});
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('assets/phone/export', {
    ...queryParams.value
  }, `phone_${new Date().getTime()}.xlsx`)
}

getList();
</script>
