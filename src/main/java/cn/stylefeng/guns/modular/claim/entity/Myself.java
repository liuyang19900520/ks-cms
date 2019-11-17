package cn.stylefeng.guns.modular.claim.entity;

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
@TableName("claim")
public class Myself implements Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * 报销单ID
	 */
	@TableId(value = "CLAIM_ID")
	private Long claimId;
	/**
	 * 员工ID
	 */
	@TableId(value = "EMPLOYEE_ID")
	private String employeeId;
	/**
	 * 员工名
	 */
	@TableId(value = "EMPLOYEE_NAME")
	private String employeeName;
	/**
	 * 报销日期
	 */
	@TableId(value = "CLAIM_DATE")
	private String claimDate;
	/**
	 * 报销金额
	 */
	@TableId(value = "PRICE")
	private String price;
	/**
	 * 图片路径
	 */
	@TableId(value = "PIC_URL")
	private String picUrl;
	/**
	 * 报销类型
	 */
	@TableId(value = "CLAIM_TYPE")
	private String claimType;
	/**
	 * 报销状态
	 */
	@TableId(value = "CLAIM_STATUS")
	private String claimStatus;
	/**
	 * 备注
	 */
	@TableId(value = "REMARK")
	private String remark;
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
	/**
	 * 版本
	 */
	@TableField(value = "VERSION", fill = FieldFill.UPDATE)
	private Long version;
	public Long getClaimId() {
		return claimId;
	}
	public void setClaimId(Long claimId) {
		this.claimId = claimId;
	}
	public String getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	public String getClaimDate() {
		return claimDate;
	}
	public void setClaimDate(String claimDate) {
		this.claimDate = claimDate;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getPicUrl() {
		return picUrl;
	}
	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
	public String getClaimType() {
		return claimType;
	}
	public void setClaimType(String claimType) {
		this.claimType = claimType;
	}
	public String getClaimStatus() {
		return claimStatus;
	}
	public void setClaimStatus(String claimStatus) {
		this.claimStatus = claimStatus;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
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
	public Long getVersion() {
		return version;
	}
	public void setVersion(Long version) {
		this.version = version;
	}

	

}
