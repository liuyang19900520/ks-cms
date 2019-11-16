package cn.stylefeng.guns.modular.system.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;

@TableName("customer_site")
public class CustomerSite implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 客户ID
     */
    @TableId(value = "CUSTOMER_ID")
    private Long customerID;
    /**
     * 客户现场ID
     */
    @TableId(value = "CUSTOMER_SITE_ID")
    private Long customersiteID;
    /**
     * 客户现场名称
     */
    @TableId(value = "CUSTOMER_SITE_NAME")
    private String customersiteName;
    /**
     * 客户现场地址
     */
    @TableId(value = "CUSTOMER_SITE_ADDRESS")
    private String customersiteAddress;
    /**
     * 客户现场车站
     */
    @TableId(value = "CUSTOMER_SITE_STATION")
    private String customersiteStation;
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

    public Long getCustomerID() {return customerID;}

    public void setCustomerID(Long customerID) {this.customerID = customerID;}

    public Long getCustomersiteID() {return customersiteID;}

    public void setCustomersiteID(Long customersiteID) {this.customersiteID = customersiteID;}

    public String getCustomersiteName() {return customersiteName;}

    public void setCustomersiteName(String customersiteName) {this.customersiteName = customersiteName;}

    public String getCustomersiteAddress() {return customersiteAddress;}

    public void setCustomersiteAddress(String customersiteAddress) {this.customersiteAddress = customersiteAddress;}

    public String getCustomersiteStation() {return customersiteStation;}

    public void setCustomersiteStation(String customersiteStation) {this.customersiteStation = customersiteStation;}

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
