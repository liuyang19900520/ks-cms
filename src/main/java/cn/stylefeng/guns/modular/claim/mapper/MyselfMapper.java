package cn.stylefeng.guns.modular.claim.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import cn.stylefeng.guns.modular.claim.entity.Myself;

/**
 * <p>
 * 客户表 Mapper 接口
 * </p>
 *
 * @author stylefeng
 * @since 2019-10-29
 */
public interface MyselfMapper extends BaseMapper<Myself> {
    /**
     * 获取所有部门列表
     */
	Page<Map<String, Object>> list(@Param("page") Page page, @Param("condition") String condition);
}
