package cn.stylefeng.guns.modular.claim.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * <p>
 * 客户表 Mapper 接口
 * </p>
 *
 * @author stylefeng
 * @since 2019-10-29
 */
@Repository
public interface SummaryMapper {

    Page<Map<String, Object>> listSummary(@Param("page") Page page, @Param("selectedMonth") String condition);

    Page<Map<String, Object>> listDetail(@Param("page") Page page, @Param("selectedMonth") String conditionMonth, @Param("employeeId") Long employeeId);

    Integer updateStatusCode(@Param("selectedMonth") String conditionMonth, @Param("employeeId") Long employeeId, @Param("code") String code);
}
