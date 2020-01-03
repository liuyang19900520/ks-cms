package cn.stylefeng.guns.modular.employee.entity;

import com.baomidou.mybatisplus.annotation.*;

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
@TableName("employee_visa")
public class Visa implements Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * 客户ID
	 */
	@TableId(value = "USER_ID",type = IdType.AUTO)
	private Long userID;
	/**
	 * 客户公司名称
	 */
	@TableField(value = "EMPLOYEE_ID")
	private String employeeId;

	@TableField(value = "EMPLOYEE_NAME_CN")
	private String employeeNameCn;
	/**
	 * 客户地址
	 */
	@TableField(value = "VISA_TYPE")
	private String visaType;
	/**
	 * 客户地址
	 */
	@TableField(value = "VISA_UPDATE_TIME")
	private Date visaUpdateTime;
	/**
	 * 客户地址
	 */
	@TableField(value = "VISA_EXPIRE_TIME")
	private Date visaExpireTime;
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

	@TableField(value = "VERSION")
	private Integer version;

	@TableField(value = "DELETE_FLG")
	private String deleteFlg;

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public Long getUserID() {
		return userID;
	}

	public void setUserID(Long userID) {
		this.userID = userID;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public String getVisaType() {
		return visaType;
	}

	public void setVisaType(String visaType) {
		this.visaType = visaType;
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

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public String getDeleteFlg() {
		return deleteFlg;
	}

	public void setDeleteFlg(String deleteFlg) {
		this.deleteFlg = deleteFlg;
	}

	public Date getVisaUpdateTime() {
		return visaUpdateTime;
	}

	public void setVisaUpdateTime(Date visaUpdateTime) {
		this.visaUpdateTime = visaUpdateTime;
	}

	public Date getVisaExpireTime() {
		return visaExpireTime;
	}

	public void setVisaExpireTime(Date visaExpireTime) {
		this.visaExpireTime = visaExpireTime;
	}

	public String getEmployeeNameCn() {
		return employeeNameCn;
	}

	public void setEmployeeNameCn(String employeeNameCn) {
		this.employeeNameCn = employeeNameCn;
	}
}
