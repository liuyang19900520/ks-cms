package cn.stylefeng.guns.modular.employee.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@TableName("project_relation")
@Data
public class ProjectRelation implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "PROJECT_ID", type = IdType.ID_WORKER)
    private Long projectId;
    /**
     * 父角色id
     */
    @TableField("USER_ID")
    private Long userId;
    /**
     * 角色名称
     */
    @TableField("EMPLOYEE_ID")
    private String employeeId;
    /**
     * 提示
     */
    @TableField("POSITIOIN_CUSTOMER")
    private String positionCustomer;
    /**
     * 乐观锁
     */
    @TableField("VERSION")
    private Integer version;
    /**
     * 创建时间
     */
    @TableField(value = "CREATE_TIME", fill = FieldFill.INSERT)
    private Date createTime;
    /**
     * 修改时间
     */
    @TableField(value = "UPDATE_TIME", fill = FieldFill.UPDATE)
    private Date updateTime;
    /**
     * 创建用户
     */
    @TableField(value = "CREATE_USER", fill = FieldFill.INSERT)
    private Long createUser;
    /**
     * 修改用户
     */
    @TableField(value = "UPDATE_USER", fill = FieldFill.UPDATE)
    private Long updateUser;
}
