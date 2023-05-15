package com.shiyan2.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shiyan2.common.QueryPageParam;
import com.shiyan2.common.Result;
import com.shiyan2.entity.Assettype;
import com.shiyan2.entity.User;
import com.shiyan2.service.AssettypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
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
@RequestMapping("/assettype")
public class AssettypeController {

    @Autowired
    private AssettypeService assettypeService;
    //新增
    @PostMapping("/save")
    public Result save(@RequestBody Assettype assettype){
        return assettypeService.save(assettype)?Result.suc():Result.fail();
    }
    //更新
    @PostMapping("/update")
    public Result update(@RequestBody Assettype assettype){
        return assettypeService.updateById(assettype)?Result.suc():Result.fail();
    }
    //删除
    @GetMapping("/del")
    public Result del(@RequestParam String id){
        return assettypeService.removeById(id)?Result.suc():Result.fail();
    }

    @GetMapping("/list")
    public Result list(){
        List list = assettypeService.list();
        return Result.suc(list);
    }
    @PostMapping("/listPage")
    public Result listPage(@RequestBody QueryPageParam queryPageParam){

        HashMap param = queryPageParam.getParam();
        String name =(String)param.get("name");

        Page<Assettype> page = new Page();

        page.setCurrent(queryPageParam.getPageNum());
        page.setSize(queryPageParam.getPageSize());

        LambdaQueryWrapper<Assettype> lambdaQueryWrapper =new LambdaQueryWrapper();
        if(StringUtils.isNotBlank(name)&&!"null".equals(name)){
            lambdaQueryWrapper.like(Assettype::getName,name);
        }
        IPage result = assettypeService.page(page,lambdaQueryWrapper);

        System.out.println("total===>"+result.getTotal());

        return  Result.suc(result.getRecords(), result.getTotal());


    }
}
