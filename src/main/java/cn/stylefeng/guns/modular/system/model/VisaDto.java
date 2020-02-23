package cn.stylefeng.guns.modular.system.model;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 签证信息
 *
 * @author fengshuonan
 * @Date 2018/12/8 18:16
 */
@Data
public class VisaDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 员工签证ID
     */
    private String employeeId;
    /**类型
     *
     */
    private String visaType;
    /**
     * 签证更新时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date visaUpdateTime;
    /**
     * 签证到期时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date visaExpireTime;
}
