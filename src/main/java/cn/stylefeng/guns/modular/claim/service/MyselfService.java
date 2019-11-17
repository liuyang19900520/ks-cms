package cn.stylefeng.guns.modular.claim.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import cn.stylefeng.guns.core.common.exception.BizExceptionEnum;
import cn.stylefeng.guns.core.common.page.LayuiPageFactory;
import cn.stylefeng.guns.modular.system.entity.Dict;
import cn.stylefeng.guns.modular.claim.entity.Myself;
import cn.stylefeng.guns.modular.system.mapper.DictMapper;
import cn.stylefeng.guns.modular.claim.mapper.MyselfMapper;
import cn.stylefeng.roses.core.util.ToolUtil;
import cn.stylefeng.roses.kernel.model.exception.ServiceException;

/**
 * <p>
 * 部门表 服务实现类
 * </p>
 *
 * @author stylefeng
 * @since 2018-12-07
 */
@Service
public class MyselfService extends ServiceImpl<MyselfMapper, Myself> {
	
	@Resource
	private DictMapper dictMapper;
	
    /**
     * 获取所有客户信息列表
     *
     * @author fengshuonan
     * @Date 2018/12/23 5:16 PM
     */
    public Page<Map<String, Object>> list(String condition) {
        Page page = LayuiPageFactory.defaultPage();
        return this.baseMapper.list(page, condition);
    }
    /**
     * 客户信息录入
     *
     * @author fengshuonan
     * @Date 2018/12/23 5:00 PM
     */
    @Transactional(rollbackFor = Exception.class)
    public void addmyself(Myself myself) {
    	
        if (ToolUtil.isOneEmpty(myself)) {
            throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        }
        this.save(myself);
    }
    /**
     * 修改部门
     *
     * @author fengshuonan
     * @Date 2018/12/23 5:00 PM
     */
    @Transactional(rollbackFor = Exception.class)
    public void editMyself(Myself myself) {

        if (ToolUtil.isOneEmpty(myself, myself.getClaimId())) {
            throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        }
        this.updateById(myself);
    }
    
	public List<Dict> getClaimType() {
		List<Dict> returnList = dictMapper.selectByParentCode("CLAIM_TYPE");
		return returnList;
	}
}
