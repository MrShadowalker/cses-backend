<template>
  <a-spin :spinning="confirmLoading">
    <JFormContainer :disabled="disabled">
      <template #detail>
        <a-form ref="formRef" class="antd-modal-form" :labelCol="labelCol" :wrapperCol="wrapperCol" name="CsesQuestionInfoForm">
          <a-row>
						<a-col :span="24">
							<a-form-item label="删除人" v-bind="validateInfos.deleteBy" id="CsesQuestionInfoForm-deleteBy" name="deleteBy">
								<a-input v-model:value="formData.deleteBy" placeholder="请输入删除人"  allow-clear ></a-input>
							</a-form-item>
						</a-col>
						<a-col :span="24">
							<a-form-item label="删除日期" v-bind="validateInfos.deleteTime" id="CsesQuestionInfoForm-deleteTime" name="deleteTime">
								<a-date-picker placeholder="请选择删除日期"  v-model:value="formData.deleteTime" showTime value-format="YYYY-MM-DD HH:mm:ss" style="width: 100%"  allow-clear />
							</a-form-item>
						</a-col>
						<a-col :span="24">
							<a-form-item label="试题类型" v-bind="validateInfos.type" id="CsesQuestionInfoForm-type" name="type">
								<a-input v-model:value="formData.type" placeholder="请输入试题类型"  allow-clear ></a-input>
							</a-form-item>
						</a-col>
						<a-col :span="24">
							<a-form-item label="环节" v-bind="validateInfos.segment" id="CsesQuestionInfoForm-segment" name="segment">
								<a-input v-model:value="formData.segment" placeholder="请输入环节"  allow-clear ></a-input>
							</a-form-item>
						</a-col>
						<a-col :span="24">
							<a-form-item label="版本" v-bind="validateInfos.version" id="CsesQuestionInfoForm-version" name="version">
								<a-input v-model:value="formData.version" placeholder="请输入版本"  allow-clear ></a-input>
							</a-form-item>
						</a-col>
						<a-col :span="24">
							<a-form-item label="子版本" v-bind="validateInfos.subVersion" id="CsesQuestionInfoForm-subVersion" name="subVersion">
								<a-input v-model:value="formData.subVersion" placeholder="请输入子版本"  allow-clear ></a-input>
							</a-form-item>
						</a-col>
						<a-col :span="24">
							<a-form-item label="场景" v-bind="validateInfos.scene" id="CsesQuestionInfoForm-scene" name="scene">
								<a-input v-model:value="formData.scene" placeholder="请输入场景"  allow-clear ></a-input>
							</a-form-item>
						</a-col>
						<a-col :span="24">
							<a-form-item label="子场景" v-bind="validateInfos.subScene" id="CsesQuestionInfoForm-subScene" name="subScene">
								<a-input v-model:value="formData.subScene" placeholder="请输入子场景"  allow-clear ></a-input>
							</a-form-item>
						</a-col>
						<a-col :span="24">
							<a-form-item label="权重" v-bind="validateInfos.weight" id="CsesQuestionInfoForm-weight" name="weight">
								<a-input-number v-model:value="formData.weight" placeholder="请输入权重" style="width: 100%" />
							</a-form-item>
						</a-col>
						<a-col :span="24">
							<a-form-item label="试题文案" v-bind="validateInfos.content" id="CsesQuestionInfoForm-content" name="content">
								<a-input v-model:value="formData.content" placeholder="请输入试题文案"  allow-clear ></a-input>
							</a-form-item>
						</a-col>
          </a-row>
        </a-form>
      </template>
    </JFormContainer>
  </a-spin>
</template>

<script lang="ts" setup>
  import { ref, reactive, defineExpose, nextTick, defineProps, computed, onMounted } from 'vue';
  import { defHttp } from '/@/utils/http/axios';
  import { useMessage } from '/@/hooks/web/useMessage';
  import { getValueType } from '/@/utils';
  import { saveOrUpdate } from '../CsesQuestionInfo.api';
  import { Form } from 'ant-design-vue';
  import JFormContainer from '/@/components/Form/src/container/JFormContainer.vue';
  const props = defineProps({
    formDisabled: { type: Boolean, default: false },
    formData: { type: Object, default: () => ({})},
    formBpm: { type: Boolean, default: true }
  });
  const formRef = ref();
  const useForm = Form.useForm;
  const emit = defineEmits(['register', 'ok']);
  const formData = reactive<Record<string, any>>({
    id: '',
    deleteBy: '',   
    deleteTime: '',   
    type: '',   
    segment: '',   
    version: '',   
    subVersion: '',   
    scene: '',   
    subScene: '',   
    weight: undefined,
    content: '',   
  });
  const { createMessage } = useMessage();
  const labelCol = ref<any>({ xs: { span: 24 }, sm: { span: 5 } });
  const wrapperCol = ref<any>({ xs: { span: 24 }, sm: { span: 16 } });
  const confirmLoading = ref<boolean>(false);
  //表单验证
  const validatorRules = reactive({
  });
  const { resetFields, validate, validateInfos } = useForm(formData, validatorRules, { immediate: false });

  // 表单禁用
  const disabled = computed(()=>{
    if(props.formBpm === true){
      if(props.formData.disabled === false){
        return false;
      }else{
        return true;
      }
    }
    return props.formDisabled;
  });

  
  /**
   * 新增
   */
  function add() {
    edit({});
  }

  /**
   * 编辑
   */
  function edit(record) {
    nextTick(() => {
      resetFields();
      const tmpData = {};
      Object.keys(formData).forEach((key) => {
        if(record.hasOwnProperty(key)){
          tmpData[key] = record[key]
        }
      })
      //赋值
      Object.assign(formData, tmpData);
    });
  }

  /**
   * 提交数据
   */
  async function submitForm() {
    try {
      // 触发表单验证
      await validate();
    } catch ({ errorFields }) {
      if (errorFields) {
        const firstField = errorFields[0];
        if (firstField) {
          formRef.value.scrollToField(firstField.name, { behavior: 'smooth', block: 'center' });
        }
      }
      return Promise.reject(errorFields);
    }
    confirmLoading.value = true;
    const isUpdate = ref<boolean>(false);
    //时间格式化
    let model = formData;
    if (model.id) {
      isUpdate.value = true;
    }
    //循环数据
    for (let data in model) {
      //如果该数据是数组并且是字符串类型
      if (model[data] instanceof Array) {
        let valueType = getValueType(formRef.value.getProps, data);
        //如果是字符串类型的需要变成以逗号分割的字符串
        if (valueType === 'string') {
          model[data] = model[data].join(',');
        }
      }
    }
    await saveOrUpdate(model, isUpdate.value)
      .then((res) => {
        if (res.success) {
          createMessage.success(res.message);
          emit('ok');
        } else {
          createMessage.warning(res.message);
        }
      })
      .finally(() => {
        confirmLoading.value = false;
      });
  }


  defineExpose({
    add,
    edit,
    submitForm,
  });
</script>

<style lang="less" scoped>
  .antd-modal-form {
    padding: 14px;
  }
</style>
