package cn.stylefeng.guns.modular.company.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 部门表
 * </p>
 *
 * @author stylefeng
 * @since 2018-12-07
 */
@TableName("project")
@Data
public class Project implements Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * 项目ID
	 */
	@TableId(value = "PROJECT_ID")
	private Long projectId;
	/**
	 * 现场ID
	 */
	@TableId(value = "CUSTOMER_SITE_ID")
	@TableField(value = "CUSTOMER_SITE_ID")
	private Long customerSiteID;

	/**
	 * 项目名称
	 */
	@TableField(value = "PROJECT_NAME")
	private String projectName;
	/**
	 * 项目过程
	 */
	@TableField(value = "PROJECT_PROCESS")
	private String projectProcess;
	/**
	 * 项目技术
	 */
	@TableField(value = "PROJECT_TECH")
	private String projectTech;
	/**
	 * 考勤类型
	 */
	@TableField(value = "ATTENDANCE_TYPE")
	private Integer attendanceType;

	/**
	 * 创建时间
	 */
	@TableField(value = "CREATE_TIME", fill = FieldFill.INSERT)
	private Date createTime;
	/**
	 * 创建人
	 */
	@TableField(value = "CREATE_USER", fill = FieldFill.INSERT)
	private Long createUser;
	/**
	 * 修改时间
	 */
	@TableField(value = "UPDATE_TIME", fill = FieldFill.UPDATE)
	private Date updateTime;
	/**
	 * 修改人
	 */
	@TableField(value = "UPDATE_USER", fill = FieldFill.UPDATE)
	private Long updateUser;

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public Long getCustomerSiteID() {
		return customerSiteID;
	}

	public void setCustomerSiteID(Long customerSiteID) {
		this.customerSiteID = customerSiteID;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getProjectProcess() {
		return projectProcess;
	}

	public void setProjectProcess(String projectProcess) {
		this.projectProcess = projectProcess;
	}

	public String getProjectTech() {
		return projectTech;
	}

	public void setProjectTech(String projectTech) {
		this.projectTech = projectTech;
	}

	public Integer getAttendanceType() {
		return attendanceType;
	}

	public void setAttendanceType(Integer attendanceType) {
		this.attendanceType = attendanceType;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Long getCreateUser() {
		return createUser;
	}

	public void setCreateUser(Long createUser) {
		this.createUser = createUser;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Long getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(Long updateUser) {
		this.updateUser = updateUser;
	}
}
