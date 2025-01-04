<template>
  <div class="app-container">

    <div id="box" ref="echartRef" style="width: 1000px;height: 300px;">

    </div>
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="资产类别" prop="category">
        <el-select v-model="queryParams.category" placeholder="请选择资产类别" clearable>
          <el-option
            v-for="dict in assets_category"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="固定资产编号" prop="assetsCode">
        <el-input
          v-model="queryParams.assetsCode"
          placeholder="请输入固定资产编号"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="资产名" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入资产名"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="序列号/MEID" prop="serialNumberMeid">
        <el-input
          v-model="queryParams.serialNumberMeid"
          placeholder="请输入序列号/MEID"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="采购人" prop="purchaser">
        <el-input
          v-model="queryParams.purchaser"
          placeholder="请输入采购人"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="使用人" prop="consumer">
        <el-input
          v-model="queryParams.consumer"
          placeholder="请输入使用人"
          clearable
          @keyup.enter="handleQuery"
        />
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
          v-hasPermi="['assets:assets:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['assets:assets:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['assets:assets:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['assets:assets:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="assetsList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" align="center" prop="id" />
      <el-table-column label="固定资产编号" align="center" prop="assetsCode" />
      <el-table-column label="总数量" align="center" prop="totalCount" />
      <el-table-column label="剩余数量" align="center" prop="remainingCount" />
      <el-table-column label="备注说明" align="center" prop="remark" />
      <el-table-column label="购买日期" align="center" prop="purchaseDate" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.purchaseDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="资产类别" align="center" prop="category">
        <template #default="scope">
          <dict-tag :options="assets_category" :value="scope.row.category"/>
        </template>
      </el-table-column>
      <el-table-column label="资产用途分类" align="center" prop="assetUsage" />
      <el-table-column label="资产名" align="center" prop="name" />
      <el-table-column label="资产照" align="center" prop="img" width="100">
        <template #default="scope">
          <image-preview :src="scope.row.img" :width="50" :height="50"/>
        </template>
      </el-table-column>
      <el-table-column label="品牌" align="center" prop="brand" />
      <el-table-column label="数量/单位" align="center" prop="quantity" />
      <el-table-column label="颜色" align="center" prop="color" />
      <el-table-column label="内存" align="center" prop="memory" />
      <el-table-column label="型号" align="center" prop="model" />
      <el-table-column label="序列号/MEID" align="center" prop="serialNumberMeid" />
      <el-table-column label="购买价" align="center" prop="price" />
      <el-table-column label="采购人" align="center" prop="purchaser" />
      <el-table-column label="使用人" align="center" prop="consumer" />
      <el-table-column label="使用部门" align="center" prop="consumerDepartment" />
      <el-table-column label="存放地" align="center" prop="store" />

<!--      <el-table-column label="备注" align="center" prop="remarks" />-->
      <el-table-column label="创建时间" align="center" prop="createTime" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="更新时间" align="center" prop="updateTime" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.updateTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="创建人" align="center" prop="createBy" />
      <el-table-column label="更新人" align="center" prop="updateBy" />

      <el-table-column label="单价" align="center" prop="unitPrice" />
      <el-table-column label="总价" align="center" prop="totalPrice" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" fixed="right">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleGenerateCodeImg(scope.row)" v-hasPermi="['assets:phone:edit']">生成二维码</el-button>
          <el-button link type="primary" icon="Edit" @click="handleOpenBorrow(scope.row)" v-hasPermi="['assets:assets:edit']">借出</el-button>
          <el-button link type="primary" icon="Edit" @click="handleOpenConsume(scope.row)" v-hasPermi="['assets:assets:edit']">损耗</el-button>
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['assets:assets:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['assets:assets:remove']">删除</el-button>
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


<!--    借出form-->
    <el-dialog :title="consumeType==='1'?'外借':'损耗'" v-model="borrowDialogVisible" width="500px" append-to-body>
      <div>

        <el-form ref="borrowRef" :model="borrowForm"  label-width="80px">

          <el-form-item label="固定资产id" prop="assetsId">
            <el-input v-model="borrowForm.assetsId" placeholder="请输入固定资产编号" disabled/>
          </el-form-item>
          <el-form-item label="固定资产名字" prop="assetsName">
            <el-input v-model="borrowForm.assetsName" placeholder="请输入固定资产编号" disabled/>
          </el-form-item>
          <el-form-item :label="consumeType==='1'?'借出数量':'损耗数量'" prop="borrowCount">
            <el-input v-model="borrowForm.borrowCount" placeholder="请输入数量" type="number" />
          </el-form-item>
          <el-form-item :label="consumeType==='1'?'借给谁':'损耗人'" prop="borrower">
            <el-input v-model="borrowForm.borrower" placeholder="请输入名字" />
          </el-form-item>

          <el-form-item label="备注" prop="remark">
            <el-input v-model="borrowForm.remark" placeholder="请输入备注" />
          </el-form-item>



        </el-form>


      </div>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="handleBorrow">确 定</el-button>
          <el-button @click="borrowDialogVisible=false">取 消</el-button>
        </div>
      </template>


<!--        <el-form-item label="购买日期" prop="purchaseDate">-->
<!--          <el-date-picker clearable-->
<!--                          v-model="form.purchaseDate"-->
<!--                          type="date"-->
<!--                          value-format="YYYY-MM-DD"-->
<!--                          placeholder="请选择购买日期">-->
<!--          </el-date-picker>-->
<!--        </el-form-item>-->
<!--        <el-form-item label="购买日期" prop="purchaseDate">-->
<!--          <el-date-picker clearable-->
<!--                          v-model="form.purchaseDate"-->
<!--                          type="date"-->
<!--                          value-format="YYYY-MM-DD"-->
<!--                          placeholder="请选择购买日期">-->
<!--          </el-date-picker>-->
<!--        </el-form-item>-->
    </el-dialog>


    <!-- 添加或修改固定资产盘点明细对话框 -->
    <el-dialog :title="title" v-model="open" width="600px" append-to-body>
      <el-form ref="assetsRef" :model="form" :rules="rules" label-width="120px">
        <el-form-item label="资产编号" prop="assetsCode">
          <el-input v-model="form.assetsCode" placeholder="请输入固定资产编号" />
          <el-switch v-model="form.isGenerateCode" @change="handleGenerateCode"/>
        </el-form-item>
        <el-form-item label="资产总个数" prop="assetsCode">
          <el-input v-model="form.totalCount" placeholder="请输入资产总个数" type="number" />
        </el-form-item>
        <el-form-item label="剩余资产个数" prop="assetsCode">
          <el-input v-model="form.remainingCount" placeholder="请输入剩余资产个数" type="number" />
        </el-form-item>

        <el-form-item label="购买日期" prop="purchaseDate">
          <el-date-picker clearable
            v-model="form.purchaseDate"
            type="date"
            value-format="YYYY-MM-DD"
            placeholder="请选择购买日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="资产类别" prop="category">
          <el-select v-model="form.category" placeholder="请选择资产类别">
            <el-option
              v-for="dict in assets_category"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="资产用途分类" prop="assetUsage">
          <el-input v-model="form.assetUsage" placeholder="请输入资产用途分类" />
        </el-form-item>
        <el-form-item label="资产名" prop="name">
          <el-input v-model="form.name" placeholder="请输入资产名" />
        </el-form-item>
        <el-form-item label="品牌" prop="brand">
          <el-input v-model="form.brand" placeholder="请输入品牌" />
        </el-form-item>
        <el-form-item label="数量/单位" prop="quantity">
          <el-input v-model="form.quantity" placeholder="请输入数量/单位" />
        </el-form-item>
        <el-form-item label="颜色" prop="color">
          <el-input v-model="form.color" placeholder="请输入颜色" />
        </el-form-item>
        <el-form-item label="内存" prop="memory">
          <el-input v-model="form.memory" placeholder="请输入内存" />
        </el-form-item>
        <el-form-item label="型号" prop="model">
          <el-input v-model="form.model" placeholder="请输入型号" />
        </el-form-item>
        <el-form-item label="序列号/MEID" prop="serialNumberMeid">
          <el-input v-model="form.serialNumberMeid" placeholder="请输入序列号/MEID" />
        </el-form-item>
        <el-form-item label="购买价" prop="price">
          <el-input v-model="form.price" placeholder="请输入购买价" />
        </el-form-item>
        <el-form-item label="采购人" prop="purchaser">
          <el-input v-model="form.purchaser" placeholder="请输入采购人" />
        </el-form-item>
        <el-form-item label="使用人" prop="consumer">
          <el-input v-model="form.consumer" placeholder="请输入使用人" />
        </el-form-item>
        <el-form-item label="使用部门" prop="consumerDepartment">
          <el-input v-model="form.consumerDepartment" placeholder="请输入使用部门" />
        </el-form-item>
        <el-form-item label="存放地" prop="store">
          <el-input v-model="form.store" placeholder="请输入存放地" />
        </el-form-item>
        <el-form-item label="资产照" prop="img">
          <image-upload v-model="form.img"/>
        </el-form-item>
        <el-form-item label="单个价格" prop="assetsCode">
          <el-input v-model="form.unitPrice" placeholder="请输入固定资产单个价格" />
        </el-form-item>
        <el-form-item label="总价" prop="assetsCode">
          <el-input v-model="form.totalPrice" placeholder="请输入固定资产总价" />
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



    <el-dialog :title="title" v-model="updateDialogVisible" width="600px" append-to-body>
      <el-form ref="assetsUpdateRef" :model="form"  label-width="120px">


        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入备注" />
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="updateForm">确 定</el-button>
          <el-button @click="updateDialogVisible=false">取 消</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import * as echarts from "echarts"
import {
  listAssets,
  getAssets,
  delAssets,
  addAssets,
  updateAssets,
  borrowAssets,
  consumeAssets, getTop5Assets
} from "@/api/assets/assets";
import {generateUniqueCode} from "@/utils/utils.js";
import {ElMessage} from "element-plus";
import {ref,onMounted} from "vue"
import {generateCodeImg} from "@/api/assets/codeImg.js";
import { markRaw } from 'vue';
const echartRef = ref(null)
const myChart = ref(null)
onMounted(async () => {
  // var box = document.getElementById("#box");
  myChart.value = markRaw(echarts.init(echartRef.value));


  await  initEcharts()

})


const initEcharts = async () => {

  let res = await getTop5Assets()
  console.log(res.data, "data")
  myChart.value.setOption({
    title: {
      text: '资源top5'
    },
    tooltip: {
      trigger: "axis",
      axisPointer: {
        type: "shadow"
      }
    },
    xAxis: {
      name: "类别",
      type: "category"
    },
    yAxis: {
      name: "个数"
    },

    dataset: {
      source: res.data


      // [
      //   // ['类别', '全部', '外借'],
      //   ['衬衫', 5, 51],
      //   ['羊毛衫', 20, 20],
      //   ['雪纺衫', 36, 36],
      //   ['裤子', 10, 10],
      //   ['高跟鞋', 10, 10],
      //   ['袜子', 20, 20]
      // ]
    },
    series: [
      {
        name: '全部',
        type: 'bar',
        encode: {
          x: 0,
          y: 1
        }
      },
      {
        name: '剩余',
        type: 'bar',
        encode: {
          x: 0,
          y: 2
        }
      }
    ]
  });
}





const codeImageDialogVisible = ref(false)
const codeImage = ref("")

const  handleGenerateCodeImg = async (row) => {
  if (!row.assetsCode){
    ElMessage.error("code不能为空")
    return
  }

  let response = await generateCodeImg(row.assetsCode)
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
const { assets_category } = proxy.useDict('assets_category');
const assetsUpdateRef = ref(null)
const updateDialogVisible = ref(false)
// 切换生成code
const handleGenerateCode = (e)=>{
  if (e){
    let code  = generateUniqueCode("assets")
    console.log(e,"e",code)
    form.value.assetsCode = code
  }else{
    form.value.assetsCode = ""
  }

}

let borrowDialogVisible  = ref(false)
const borrowForm = ref({})
const borrowRef = ref(null)
const consumeType = ref("1")
const handleOpenBorrow = (row)=>{

  console.log(row,"row")
  borrowForm.value.assetsId =  row.id
  borrowForm.value.assetsName =  row.name
  borrowForm.value.borrowCount =  ""
  borrowForm.value.borrower =  ""
  borrowForm.value.remark =  ""
  // borrowDialogVisible.value =true
  borrowDialogVisible.value =true
  consumeType.value = "1"

}

const handleOpenConsume = (row)=>{
  borrowForm.value.assetsId =  row.id
  borrowForm.value.assetsName =  row.name
  borrowForm.value.borrowCount =  ""
  borrowForm.value.borrower =  ""
  borrowForm.value.remark =  ""
  // borrowDialogVisible.value =true
  borrowDialogVisible.value =true
  consumeType.value = "2"
}
// //
// 外借
const handleBorrow = async () => {
  let res = ""
 if (consumeType.value ==="1"){
   res = await borrowAssets(borrowForm.value)
   console.log(res,"res")

 }else{
    res = await consumeAssets(borrowForm.value)
 }
 if (res.code===200){
   ElMessage.success(res.msg)
   borrowDialogVisible.value = false

   await initEcharts()
 }
  // console.log(res,"res")
}




const assetsList = ref([]);
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
    category: null,
    assetsCode: null,
    name: null,
    serialNumberMeid: null,
    purchaser: null,
    consumer: null,
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

/** 查询固定资产盘点明细列表 */
function getList() {
  loading.value = true;
  listAssets(queryParams.value).then(response => {
    assetsList.value = response.rows;
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
    purchaseDate: null,
    category: null,
    assetsCode: null,
    assetUsage: null,
    name: null,
    brand: null,
    quantity: null,
    color: null,
    memory: null,
    model: null,
    serialNumberMeid: null,
    price: null,
    purchaser: null,
    consumer: null,
    consumerDepartment: null,
    store: null,
    img: null,
    remarks: null,
    createTime: null,
    updateTime: null,
    createBy: null,
    updateBy: null,
    remark: null
  };
  proxy.resetForm("assetsRef");
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
  title.value = "添加固定资产盘点明细";
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const _id = row.id || ids.value
  getAssets(_id).then(response => {
    form.value = response.data;
    updateDialogVisible.value = true;
    title.value = "修改固定资产盘点明细";
  });
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["assetsRef"].validate(valid => {
    if (valid) {
      if (form.value.id != null) {
        updateAssets(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addAssets(form.value).then(response => {
          proxy.$modal.msgSuccess("新增成功");
          open.value = false;
          getList();
          initEcharts()
        });
      }
    }
  });
}


function updateForm() {
  proxy.$refs["assetsUpdateRef"].validate(valid => {
    if (valid) {
      if (form.value.id != null) {
        updateAssets(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          updateDialogVisible.value = false;
          getList();
        });
      }
    }
  });
}

/** 删除按钮操作 */
function handleDelete(row) {
  const _ids = row.id || ids.value;
  proxy.$modal.confirm('是否确认删除固定资产盘点明细编号为"' + _ids + '"的数据项？').then(function() {
    return delAssets(_ids);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("删除成功");
  }).catch(() => {});
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('assets/assets/export', {
    ...queryParams.value
  }, `assets_${new Date().getTime()}.xlsx`)
}

getList();
</script>
