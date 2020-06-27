package cn.stylefeng.guns.modular.employee.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * ksg员工信息表
 * </p>
 *
 * @author liuyang
 * @since 2020-06-25
 */
@Data
public class Employee implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer employeeId;
    private Integer userId;
    private String chineseName;
    private String japaneseName;
    private String japaneseKana;
    private String chinaProvince;
    private String chinaCity;
    private String japanState;
    private String japanCity;
    private String japanAddress;
    private String japanStation;
    private Integer degree;
    private String school;
    private String major;

    private Date createTime;
    private Integer createUser;
    private Date updateTime;
    private Integer updateUser;
    private Integer version;


}
