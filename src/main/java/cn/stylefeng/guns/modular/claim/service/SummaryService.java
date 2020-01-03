package cn.stylefeng.guns.modular.claim.service;

import cn.stylefeng.guns.core.common.page.LayuiPageFactory;
import cn.stylefeng.guns.modular.claim.mapper.SummaryMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * <p>
 * 部门表 服务实现类
 * </p>
 *
 * @author stylefeng
 * @since 2018-12-07
 */
@Service
public class SummaryService {

    @Autowired
    SummaryMapper summaryMapper;

    public Page<Map<String, Object>> listSummary(String selectedMonth) {
        Page page = LayuiPageFactory.defaultPage();
        return summaryMapper.listSummary(page, selectedMonth);
    }

    public Page<Map<String, Object>> listDetail(String selectedMonth, Long employeeId) {
        Page page = LayuiPageFactory.defaultPage();
        return summaryMapper.listDetail(page, selectedMonth, employeeId);
    }

    public void setStatus(Long employeeId, String selectedMonth, String statusCode) {
        summaryMapper.updateStatusCode(selectedMonth, employeeId, statusCode);
    }
}
