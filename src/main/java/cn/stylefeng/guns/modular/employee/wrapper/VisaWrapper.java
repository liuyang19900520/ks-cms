/**
 * Copyright 2018-2020 stylefeng & fengshuonan (https://gitee.com/stylefeng)
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.stylefeng.guns.modular.employee.wrapper;

import cn.stylefeng.guns.core.common.constant.factory.ConstantFactory;
import cn.stylefeng.guns.core.util.LDateUtils;
import cn.stylefeng.guns.modular.system.entity.Dict;
import cn.stylefeng.guns.modular.system.mapper.DictMapper;
import cn.stylefeng.roses.core.base.warpper.BaseControllerWrapper;
import cn.stylefeng.roses.core.util.SpringContextHolder;
import cn.stylefeng.roses.core.util.ToolUtil;
import cn.stylefeng.roses.kernel.model.page.PageResult;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 部门列表的包装
 *
 * @author fengshuonan
 * @date 2017年4月25日 18:10:31
 */

public class VisaWrapper extends BaseControllerWrapper {

    private DictMapper dictMapper = SpringContextHolder.getBean(DictMapper.class);

    public VisaWrapper(Map<String, Object> single) {
        super(single);
    }

    public VisaWrapper(List<Map<String, Object>> multi) {
        super(multi);
    }


    public VisaWrapper(Page<Map<String, Object>> page) {
        super(page);
    }

    public VisaWrapper(PageResult<Map<String, Object>> pageResult) {
        super(pageResult);
    }


    @Override
    protected void wrapTheMap(Map<String, Object> map) {
        String code = (String) map.get("visaType");
        map.put("visaTypeCode", code);
        //code转value
        List<Dict> list = dictMapper.selectByCode(code);
        if (ToolUtil.isNotEmpty(list)) {
            map.put("visaType", list.get(0).getName());
        } else {
        }

        //日期格式转换
        Date visaUpdateTime = (Date) map.get("visaUpdateTime");
        Date visaExpireTime = (Date) map.get("visaExpireTime");

        map.put("visaUpdateTime", LDateUtils.dateToString(visaUpdateTime,"yyyy-MM-dd"));
        map.put("visaExpireTime", LDateUtils.dateToString(visaExpireTime,"yyyy-MM-dd"));
    }
}
