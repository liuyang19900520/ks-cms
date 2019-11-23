package cn.stylefeng.guns.modular.company.mapper;

import java.util.Date;
import java.util.Map;

import cn.stylefeng.roses.core.datascope.DataScope;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import cn.stylefeng.guns.modular.company.entity.Company;

/**
 * <p>
 * 客户表 Mapper 接口
 * </p>
 *
 * @author stylefeng
 * @since 2019-10-29
 */
public interface CompanyMapper extends BaseMapper<Company> {
    /**
     * 获取所有部门列表
     */
	Page<Map<String, Object>> list(@Param("page") Page page, @Param("dataScope") DataScope dataScope, @Param("companyName") String companyName);
    Integer updateByCustomerID(@Param("customerID") Long customerID,@Param("companyName") String companyName, @Param("companyAddress")String companyAddress, @Param("tel") String tel,@Param("mail") String mail,@Param("ceoName") String ceoName,
                               @Param("ceoTel") String ceoTel,@Param("ceoMail") String ceoMail);
}
