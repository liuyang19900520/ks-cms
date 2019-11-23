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
package cn.stylefeng.guns.core.common.constant.dictmap;

import cn.stylefeng.guns.core.common.constant.dictmap.base.AbstractDictMap;

/**
 * 部门的映射
 *
 * @author fengshuonan
 * @date 2017-05-06 15:01
 */
public class ProjectDict extends AbstractDictMap {

    @Override
    public void init() {
        put("projectId", "项目id");
        put("customerSiteId", "现场id");
        put("projectName", "项目名称");
        put("projectProcess", "项目情况");
        put("projectTech", "项目技术");
    }

    @Override
    protected void initBeWrapped() {
        putFieldWrapperMethodName("projectId	", "getProjectName");
    }
}
