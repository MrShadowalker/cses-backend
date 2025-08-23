package org.jeecg.modules.demo.question.entity;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableLogic;
import org.jeecg.common.constant.ProvinceCityArea;
import org.jeecg.common.util.SpringContextUtils;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecg.common.aspect.annotation.Dict;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Description: 试题相关信息
 * @Author: jeecg-boot
 * @Date:   2025-08-23
 * @Version: V1.0
 */
@Data
@TableName("cses_question_info")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description="试题相关信息")
public class CsesQuestionInfo implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.ASSIGN_ID)
    @Schema(description = "主键")
    private java.lang.String id;
	/**创建人*/
    @Schema(description = "创建人")
    private java.lang.String createBy;
	/**创建日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Schema(description = "创建日期")
    private java.util.Date createTime;
	/**更新人*/
    @Schema(description = "更新人")
    private java.lang.String updateBy;
	/**更新日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Schema(description = "更新日期")
    private java.util.Date updateTime;
	/**删除人*/
	@Excel(name = "删除人", width = 15)
    @Schema(description = "删除人")
    private java.lang.String deleteBy;
	/**删除日期*/
	@Excel(name = "删除日期", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Schema(description = "删除日期")
    private java.util.Date deleteTime;
	/**所属部门*/
    @Schema(description = "所属部门")
    private java.lang.String sysOrgCode;
	/**试题类型*/
	@Excel(name = "试题类型", width = 15)
    @Schema(description = "试题类型")
    private java.lang.String type;
	/**环节*/
	@Excel(name = "环节", width = 15)
    @Schema(description = "环节")
    private java.lang.String segment;
	/**版本*/
	@Excel(name = "版本", width = 15)
    @Schema(description = "版本")
    private java.lang.String version;
	/**子版本*/
	@Excel(name = "子版本", width = 15)
    @Schema(description = "子版本")
    private java.lang.String subVersion;
	/**场景*/
	@Excel(name = "场景", width = 15)
    @Schema(description = "场景")
    private java.lang.String scene;
	/**子场景*/
	@Excel(name = "子场景", width = 15)
    @Schema(description = "子场景")
    private java.lang.String subScene;
	/**权重*/
	@Excel(name = "权重", width = 15)
    @Schema(description = "权重")
    private java.math.BigDecimal weight;
	/**试题文案*/
	@Excel(name = "试题文案", width = 15)
    @Schema(description = "试题文案")
    private java.lang.String content;
}
