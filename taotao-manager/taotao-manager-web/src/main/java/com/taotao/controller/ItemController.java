package com.taotao.controller;

import com.taotao.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author:
 * @Description:
 * @Date Create on 9:11 2018/8/16
 * @MOdifyBy:
 * @parameter
 */
@Controller
public class ItemController {
    @Autowired
    private ItemService itemService;
    @ResponseBody
    @RequestMapping("/list")
    public Map list(){
        Map map = new HashMap();
        map.put("data",itemService.getItemList(1,5));
        map.put("code",1);
        return map;
    }
}
