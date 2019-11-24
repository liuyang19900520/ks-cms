package cn.stylefeng.guns.modular.employee.entity;

import java.io.Serializable;

import lombok.Data;

/**
 * <p>
 * 部门表
 * </p>
 *
 * @author stylefeng
 * @since 2018-12-07
 */
@Data
public class InfoMgr implements Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * 员工号
	 */
	private Long employeeId;
	/**
	 * 姓名
	 */
	private String employeeNameCn;
	/**
	 * 所属客户
	 */
	private String companyName;
	/**
	 * 所在现场
	 */
	private String customerSiteName;
	/**
	 * 所在项目
	 */
	private String projectName;
	
}
