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
public class CompanyDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 客户ID
     */
    private Long customerID;
    /**
     * 客户公司名称
     */
    private String companyName;
    /**
     * 客户地址
     */
    private String companyAddress;
    /**
     * 联系电话
     */
    private String tel;
    /**
     * 邮箱
     */
    private String mail;
    /**
     * 社长姓名
     */
    private String ceoName;
    /**
     * 社长联系方式
     */
    private String ceoTel;
    /**
     * 社长邮箱
     */
    private String ceoMail;
}
