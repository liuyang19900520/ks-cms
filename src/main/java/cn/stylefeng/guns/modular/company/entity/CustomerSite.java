package cn.stylefeng.guns.modular.company.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@TableName("customer_site")
@Data
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
    @TableField(value = "CUSTOMER_SITE_ID")
    private Long customerSiteID;
    /**
     * 客户现场名称
     */
    @TableField(value = "CUSTOMER_SITE_NAME")
    private String customerSiteName;
    /**
     * 客户现场地址
     */
    @TableField(value = "CUSTOMER_SITE_ADDRESS")
    private String customerSiteAddress;
    /**
     * 客户现场车站
     */
    @TableField(value = "CUSTOMER_SITE_STATION")
    private String customerSiteStation;
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


}
