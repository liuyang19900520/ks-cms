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
public class MyselfDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 报销单ID
     */
    private Long claimId;
    /**
     * 员工ID
     */
    private String employeeId;
    /**
     * 报销日期
     */
    private String claimDate;
    /**
     * 报销类型
     */
    private String claimType;
    /**
     * 报销金额
     */
    private String price;
    /**
     * 状态
     */
    private String claimStatus;
    /**
     * 图片URL
     */
    private String picUrl;
    /**
     * 备注
     */
    private String remark;
}
