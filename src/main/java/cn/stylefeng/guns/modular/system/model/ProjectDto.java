package cn.stylefeng.guns.modular.system.model;

import lombok.Data;

import java.io.Serializable;

/**
 * 项目信息
 *
 * @author fengshuonan
 * @Date 2018/12/8 18:16
 */
@Data
public class ProjectDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 项目ID
     */
    private Long projectId;

    /**
     * 客户ID
     */
    private Long customerID;
    /**
     * 项目名称
     */
    private String projectName;
    /**
     * 项目过程
     */
    private String projectProcess;
    /**
     * 项目技术
     */
    private String projectTech;
}
