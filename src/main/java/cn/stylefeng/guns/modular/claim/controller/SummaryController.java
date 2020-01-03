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
package cn.stylefeng.guns.modular.claim.controller;

import cn.stylefeng.guns.core.common.page.LayuiPageFactory;
import cn.stylefeng.guns.modular.claim.service.SummaryService;
import cn.stylefeng.guns.modular.claim.wrapper.MyselfWrapper;
import cn.stylefeng.guns.modular.claim.wrapper.SummaryDetailWrapper;
import cn.stylefeng.roses.core.base.controller.BaseController;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * 考勤总汇
 *
 * @author liuyang
 * @Date 2020/01/01
 */
@Controller
@RequestMapping("claim/summary")
public class SummaryController extends BaseController {

    @Autowired
    SummaryService summaryService;

    private String PREFIX = "/modular/claim/summary/";

    @RequestMapping("")
    public String index() {
        return PREFIX + "summary_list.html";
    }

    @RequestMapping("/list")
    @ResponseBody
    public Object list(@RequestParam(value = "conditionDate", required = false) String conditionDate) {
        Page<Map<String, Object>> list = this.summaryService.listSummary(conditionDate);
        return LayuiPageFactory.createPageInfo(list);
    }

    @RequestMapping("/detail")
    public String detailIndex() {
        return PREFIX + "summary_detail.html";
    }

    @RequestMapping("/detail/init")
    @ResponseBody
    public Object detail(@RequestParam(value = "conditionMonth", required = true) String conditionMonth, @RequestParam(value = "employeeId", required = true) Long employeeId) {
        Page<Map<String, Object>> list = this.summaryService.listDetail(conditionMonth, employeeId);
        Page<Map<String, Object>> wrap = new SummaryDetailWrapper(list).wrap();

        return LayuiPageFactory.createPageInfo(wrap);
    }

}