package cn.stylefeng.guns.modular.company.entity;

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
@TableName("customer")
public class Company implements Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * 客户ID
	 */
	@TableId(value = "CUSTOMER_ID",type = IdType.AUTO)
	private Long customerID;
	/**
	 * 客户公司名称
	 */
	@TableField(value = "COMPANY_NAME")
	private String companyName;
	/**
	 * 客户地址
	 */
	@TableField(value = "COMPANY_ADDRESS")
	private String companyAddress;
	/**
	 * 联系电话
	 */
	@TableField(value = "TEL")
	private String tel;
	/**
	 * 联系电话
	 */
	@TableField(value = "MAIL")
	private String mail;
	/**
	 * 联系电话
	 */
	@TableField(value = "CEO_NAME")
	private String ceoName;/**
	 * 联系电话
	 */
	@TableField(value = "CEO_TEL")
	private String ceoTel;/**
	 * 联系电话
	 */
	@TableField(value = "CEO_MAIL")
	private String ceoMail;
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

	public Long getCustomerID() {
		return customerID;
	}

	public void setCustomerID(Long customerID) {
		this.customerID = customerID;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}


	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getCompanyAddress() {
		return companyAddress;
	}

	public void setCompanyAddress(String companyAddress) {
		this.companyAddress = companyAddress;
	}
	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getCeoName() {
		return ceoName;
	}

	public void setCeoName(String ceoName) {
		this.ceoName = ceoName;
	}

	public String getCeoTel() {
		return ceoTel;
	}

	public void setCeoTel(String ceoTel) {
		this.ceoTel = ceoTel;
	}

	public String getCeoMail() {
		return ceoMail;
	}

	public void setCeoMail(String ceoMail) {
		this.ceoMail = ceoMail;
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
