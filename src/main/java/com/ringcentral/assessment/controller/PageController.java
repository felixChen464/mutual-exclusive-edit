package com.ringcentral.assessment.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author cxh
 * @Date 2020/7/12
 */

@Controller
@RequestMapping
public class PageController {

    /**
     * 主页，新文件
     * @return
     */
    @RequestMapping(value = "/")
    public String mainPage(){
        return "new";
    }

    /**
     * 主页，新文件
     * @return
     */
    @RequestMapping(value = "/new")
    public String newPage(){
        return "new";
    }

    /**
     * 文件浏览页
     * @return
     */
    @RequestMapping("/view")
    public String viewPage(){
        return "view";
    }

    /**
     * 编辑页
     * @return
     */
    @RequestMapping("/edit")
    public String editPage(){
        return "edit";
    }
}
