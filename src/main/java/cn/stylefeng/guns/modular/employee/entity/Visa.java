package cn.stylefeng.guns.modular.employee.entity;

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
@TableName("employee_visa")
@Data
public class Visa implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 客户ID
     */
    @TableField(value = "USER_ID")
    private Long userId;
    /**
     * 客户公司名称
     */
    @TableId(value = "EMPLOYEE_ID")
    private Long employeeId;

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

    private String employeeSelectValue;


}
