package cn.stylefeng.guns.modular.employee.wrapper;

import cn.stylefeng.guns.sys.core.constant.factory.ConstantFactory;
import cn.stylefeng.guns.sys.core.util.DecimalUtil;
import cn.stylefeng.roses.core.base.warpper.BaseControllerWrapper;
import cn.stylefeng.roses.kernel.model.page.PageResult;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;
import java.util.Map;

public class EmployeeWrapper extends BaseControllerWrapper {

    public EmployeeWrapper(Page<Map<String, Object>> page) {
        super(page);
    }

    @Override
    protected void wrapTheMap(Map<String, Object> map) {
        map.put("sexName", ConstantFactory.me().getSexName((String) map.get("sex")));
        map.put("roleName", ConstantFactory.me().getRoleName((String) map.get("roleId")));
        map.put("deptName", ConstantFactory.me().getDeptName(DecimalUtil.getLong(map.get("deptId"))));
        map.put("statusName", ConstantFactory.me().getStatusName((String) map.get("status")));
        map.put("positionName", ConstantFactory.me().getPositionName(DecimalUtil.getLong(map.get("userId"))));
        StringBuffer sbJapanAddress=new StringBuffer();
        sbJapanAddress.append(map.get("japanState"));
        sbJapanAddress.append(map.get("japanCity"));
        sbJapanAddress.append(map.get("japanAddress"));
        map.put("japanAddressAll",sbJapanAddress.toString());
        StringBuffer sbChinaAddressAll=new StringBuffer();
        sbChinaAddressAll.append(map.get("chinaProvince"));
        sbChinaAddressAll.append(map.get("chinaCity"));
        map.put("chinaAddressAll",sbChinaAddressAll.toString());

    }
}
