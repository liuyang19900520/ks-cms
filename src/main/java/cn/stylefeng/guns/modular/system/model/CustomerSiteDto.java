package cn.stylefeng.guns.modular.system.model;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户信息
 *
 * @author fengshuonan
 * @Date 2018/12/8 18:16
 */
@Data
public class CustomerSiteDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 客户ID
     */
    private Long customerID;
    /**
     * 客户现场ID
     */
    private Long customersiteID;
    /**
     * 客户现场名称
     */
    private String customersiteName;
    /**
     * 客户现场地址
     */
    private String customersiteAddress;
    /**
     * 客户现场车站
     */
    private String customersiteStation;
}
