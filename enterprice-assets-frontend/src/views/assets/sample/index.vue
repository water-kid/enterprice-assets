<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="样品购买人" prop="purchaser">
        <el-input
          v-model="queryParams.purchaser"
          placeholder="请输入样品购买人"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="样品规格" prop="specification">
        <el-input
          v-model="queryParams.specification"
          placeholder="请输入样品规格"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="购买时间" prop="purchaseTime">
        <el-date-picker clearable
          v-model="queryParams.purchaseTime"
          type="date"
          value-format="YYYY-MM-DD"
          placeholder="请选择购买时间">
        </el-date-picker>
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
          v-hasPermi="['assets:sample:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['assets:sample:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['assets:sample:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['assets:sample:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="sampleList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" align="center" prop="id" />
      <el-table-column label="编码" align="center" prop="code" />
      <el-table-column label="样品购买人" align="center" prop="purchaser" />
      <el-table-column label="样品金额" align="center" prop="amount" />
      <el-table-column label="样品规格" align="center" prop="specification" />
      <el-table-column label="store" align="center" prop="store" />
      <el-table-column label="购买时间" align="center" prop="purchaseTime" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.purchaseTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="购买图片" align="center" prop="purchaseImg" width="100">
        <template #default="scope">
          <image-preview :src="scope.row.purchaseImg" :width="50" :height="50"/>
        </template>
      </el-table-column>
      <el-table-column label="购买人所属团队" align="center" prop="purchaserTeam" />
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
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['assets:sample:edit']">修改</el-button>
          <el-button link type="primary" icon="Edit" @click="handleGenerateCodeImg(scope.row)" v-hasPermi="['assets:phone:edit']">生成二维码</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['assets:sample:remove']">删除</el-button>
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

    <!-- 添加或修改样品库存管理对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="sampleRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="编码" prop="code" v-if="flag==='0'">
          <el-input v-model="form.code" placeholder="请输入编码" />
          <el-switch v-model="form.isGenerateCode" @change="handleGenerateCode"/>
        </el-form-item>
        <el-form-item label="样品购买人" prop="purchaser">
          <el-input v-model="form.purchaser" placeholder="请输入样品购买人" />
        </el-form-item>
        <el-form-item label="样品金额" prop="amount">
          <el-input v-model="form.amount" placeholder="请输入样品金额" />
        </el-form-item>
        <el-form-item label="样品规格" prop="specification">
          <el-input v-model="form.specification" placeholder="请输入样品规格" />
        </el-form-item>
        <el-form-item label="store" prop="store">
          <el-input v-model="form.store" placeholder="请输入store" />
        </el-form-item>
        <el-form-item label="购买时间" prop="purchaseTime">
          <el-date-picker clearable
            v-model="form.purchaseTime"
            type="date"
            value-format="YYYY-MM-DD"
            placeholder="请选择购买时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="购买图片" prop="purchaseImg">
          <image-upload v-model="form.purchaseImg"/>
        </el-form-item>
        <el-form-item label="购买人所属团队" prop="purchaserTeam">
          <el-input v-model="form.purchaserTeam" placeholder="请输入购买人所属团队" />
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

<script setup name="Sample">
import { listSample, getSample, delSample, addSample, updateSample } from "@/api/assets/sample";
import {generateUniqueCode} from "@/utils/utils.js";
import {generateCodeImg} from "@/api/assets/codeImg.js";
import {ElMessage} from "element-plus";


const codeImageDialogVisible = ref(false)
const codeImage = ref("")

const  handleGenerateCodeImg = async (row) => {

  if (!row.code){
    ElMessage.error("code不能为空")
    return
  }

  let response = await generateCodeImg(row.code)
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

const sampleList = ref([]);
const open = ref(false);
const loading = ref(true);
const showSearch = ref(true);
const ids = ref([]);
const single = ref(true);
const multiple = ref(true);
const total = ref(0);
const title = ref("");


const handleGenerateCode = (e)=>{
  if (e){
    let code  = generateUniqueCode("store")
    console.log(e,"e",code)
    form.value.code = code
  }else{
    form.value.code = ""
  }

}

const data = reactive({
  form: {},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    purchaser: null,
    specification: null,
    purchaseTime: null,
    purchaseImg: null,
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

/** 查询样品库存管理列表 */
function getList() {
  loading.value = true;
  listSample(queryParams.value).then(response => {
    sampleList.value = response.rows;
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
    purchaser: null,
    amount: null,
    specification: null,
    store: null,
    purchaseTime: null,
    purchaseImg: null,
    purchaserTeam: null,
    createTime: null,
    updateTime: null,
    createBy: null,
    updateBy: null,
    remark: null
  };
  proxy.resetForm("sampleRef");
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
  title.value = "添加样品库存管理";
  flag.value = "0"
}

const flag = ref("0")
/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const _id = row.id || ids.value
  getSample(_id).then(response => {
    form.value = response.data;
    open.value = true;
    title.value = "修改样品库存管理";
    flag.value ="1"
  });
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["sampleRef"].validate(valid => {
    if (valid) {
      if (form.value.id != null) {
        updateSample(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addSample(form.value).then(response => {
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
  proxy.$modal.confirm('是否确认删除样品库存管理编号为"' + _ids + '"的数据项？').then(function() {
    return delSample(_ids);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("删除成功");
  }).catch(() => {});
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('assets/sample/export', {
    ...queryParams.value
  }, `sample_${new Date().getTime()}.xlsx`)
}

getList();
</script>
