package com.shiyan2.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shiyan2.common.QueryPageParam;
import com.shiyan2.common.Result;
import com.shiyan2.entity.Asset;
import com.shiyan2.entity.Assettype;
import com.shiyan2.service.AssetService;
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
@RequestMapping("/asset")
public class AssetController {

    @Autowired
    private AssetService assetService;
    //新增
    @PostMapping("/save")
    public Result save(@RequestBody Asset asset){
        return assetService.save(asset)?Result.suc():Result.fail();
    }
    //更新
    @PostMapping("/update")
    public Result update(@RequestBody Asset asset){
        return assetService.updateById(asset)?Result.suc():Result.fail();
    }
    //删除
    @GetMapping("/del")
    public Result del(@RequestParam String id){
        return assetService.removeById(id)?Result.suc():Result.fail();
    }

    @GetMapping("/list")
    public Result list(){
        List list = assetService.list();
        return Result.suc(list);
    }
    @PostMapping("/listPage")
    public Result listPage(@RequestBody QueryPageParam queryPageParam){

        HashMap param = queryPageParam.getParam();
        String name =(String)param.get("name");
        String assettype=(String)param.get("assettype");
        Page<Asset> page = new Page();

        page.setCurrent(queryPageParam.getPageNum());
        page.setSize(queryPageParam.getPageSize());

        LambdaQueryWrapper<Asset> lambdaQueryWrapper =new LambdaQueryWrapper();
        if(StringUtils.isNotBlank(name)&&!"null".equals(name)){
            lambdaQueryWrapper.like(Asset::getName,name);
        }
        if(StringUtils.isNotBlank(assettype)&&!"null".equals(assettype)){
            lambdaQueryWrapper.eq(Asset::getAssettype,assettype);
        }
        IPage result = assetService.page(page,lambdaQueryWrapper);

        System.out.println("total===>"+result.getTotal());

        return  Result.suc(result.getRecords(), result.getTotal());


    }

}
