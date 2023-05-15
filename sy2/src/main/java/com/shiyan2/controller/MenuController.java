package com.shiyan2.controller;


import com.shiyan2.common.Result;
import com.shiyan2.entity.Menu;
import com.shiyan2.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 梁爽
 * @since 2023-04
 */
@RestController
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;
    @GetMapping("/list")
    public Result list(@RequestParam String role){
        List list = menuService.lambdaQuery().like(Menu::getMenuright,role).list();
        return Result.suc(list);
    }
}
