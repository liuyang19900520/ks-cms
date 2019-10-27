package cn.stylefeng.guns.modular.system.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 管理员表
 * </p>
 *
 * @author stylefeng
 * @since 2018-12-07
 */
@TableName("employee")
@Data
public class Employee implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long userId;

    private Long positionId;
    private Long employeeId;
    @TableField("EMPLOYEE_NAME_CN")
    private String employeeNameCN;
    @TableField("EMPLOYEE_NAME_JP")
    private String employeeNameJP;
    @TableField("EMPLOYEE_NAME_KANA")
    private String employeeNameKANA;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date entryTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @TableField("BIRTHDATE")
    private Date brithdate;
    private String  province;
    private String  city;
    private String station;
    private String  address;

    @TableField(value = "CREATE_TIME", fill = FieldFill.INSERT)
    private Date createTime;
    /**
     * 创建人
     */
    @TableField(value = "CREATE_USER", fill = FieldFill.INSERT)
    private Long createUser;
    /**
     * 更新时间
     */
    @TableField(value = "UPDATE_TIME", fill = FieldFill.UPDATE)
    private Date updateTime;
    /**
     * 更新人
     */
    @TableField(value = "UPDATE_USER", fill = FieldFill.UPDATE)
    private Long updateUser;
    /**
     * 乐观锁
     */
    @TableField("VERSION")
    private Integer version;

}
