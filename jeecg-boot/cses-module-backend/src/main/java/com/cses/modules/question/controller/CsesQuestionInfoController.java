package org.jeecg.modules.demo.question.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.system.query.QueryRuleEnum;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.demo.question.entity.CsesQuestionInfo;
import org.jeecg.modules.demo.question.service.ICsesQuestionInfoService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.apache.shiro.authz.annotation.RequiresPermissions;
 /**
 * @Description: 试题相关信息
 * @Author: jeecg-boot
 * @Date:   2025-08-23
 * @Version: V1.0
 */
@Tag(name="试题相关信息")
@RestController
@RequestMapping("/question/csesQuestionInfo")
@Slf4j
public class CsesQuestionInfoController extends JeecgController<CsesQuestionInfo, ICsesQuestionInfoService> {
	@Autowired
	private ICsesQuestionInfoService csesQuestionInfoService;
	
	/**
	 * 分页列表查询
	 *
	 * @param csesQuestionInfo
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "试题相关信息-分页列表查询")
	@Operation(summary="试题相关信息-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<CsesQuestionInfo>> queryPageList(CsesQuestionInfo csesQuestionInfo,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {


        QueryWrapper<CsesQuestionInfo> queryWrapper = QueryGenerator.initQueryWrapper(csesQuestionInfo, req.getParameterMap());
		Page<CsesQuestionInfo> page = new Page<CsesQuestionInfo>(pageNo, pageSize);
		IPage<CsesQuestionInfo> pageList = csesQuestionInfoService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param csesQuestionInfo
	 * @return
	 */
	@AutoLog(value = "试题相关信息-添加")
	@Operation(summary="试题相关信息-添加")
	@RequiresPermissions("question:cses_question_info:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody CsesQuestionInfo csesQuestionInfo) {
		csesQuestionInfoService.save(csesQuestionInfo);

		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param csesQuestionInfo
	 * @return
	 */
	@AutoLog(value = "试题相关信息-编辑")
	@Operation(summary="试题相关信息-编辑")
	@RequiresPermissions("question:cses_question_info:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody CsesQuestionInfo csesQuestionInfo) {
		csesQuestionInfoService.updateById(csesQuestionInfo);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "试题相关信息-通过id删除")
	@Operation(summary="试题相关信息-通过id删除")
	@RequiresPermissions("question:cses_question_info:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		csesQuestionInfoService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "试题相关信息-批量删除")
	@Operation(summary="试题相关信息-批量删除")
	@RequiresPermissions("question:cses_question_info:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.csesQuestionInfoService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "试题相关信息-通过id查询")
	@Operation(summary="试题相关信息-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<CsesQuestionInfo> queryById(@RequestParam(name="id",required=true) String id) {
		CsesQuestionInfo csesQuestionInfo = csesQuestionInfoService.getById(id);
		if(csesQuestionInfo==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(csesQuestionInfo);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param csesQuestionInfo
    */
    @RequiresPermissions("question:cses_question_info:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, CsesQuestionInfo csesQuestionInfo) {
        return super.exportXls(request, csesQuestionInfo, CsesQuestionInfo.class, "试题相关信息");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequiresPermissions("question:cses_question_info:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, CsesQuestionInfo.class);
    }

}
