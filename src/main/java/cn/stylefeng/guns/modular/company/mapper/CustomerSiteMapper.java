package cn.stylefeng.guns.modular.company.mapper;


import cn.stylefeng.guns.modular.company.entity.CustomerSite;
import cn.stylefeng.roses.core.datascope.DataScope;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 客户现场Mapper 接口
 * </p>
 *
 * @author stylefeng
 * @since 2019-10-29
 */

public interface CustomerSiteMapper extends BaseMapper<CustomerSite> {
    /**
     * 获取所有现场列表
     */
    Page<Map<String, Object>> list(@Param("page") Page page,@Param("dataScope") DataScope dataScope, @Param("customerSiteName") String customerSiteName,
                                   @Param("customerSiteId") String customerSiteId);

    Long getMaxCustomerSiteId();

    List<Long> getCustomerSiteIdByCustomerId(@Param("customerID") Long customerID);
}
