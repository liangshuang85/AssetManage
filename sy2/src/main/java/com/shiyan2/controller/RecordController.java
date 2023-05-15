package com.shiyan2.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shiyan2.common.QueryPageParam;
import com.shiyan2.common.Result;
import com.shiyan2.entity.Asset;
import com.shiyan2.entity.Record;
import com.shiyan2.service.AssetService;
import com.shiyan2.service.RecordService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.UTFDataFormatException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.time.LocalDateTime;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 梁爽
 * @since 2023-04
 */
@RestController
@RequestMapping("/record")
public class RecordController {

    @Autowired
    private RecordService recordService;

    @Autowired
    private AssetService assetService;

    @PostMapping("/listPage")
    public Result listPage(@RequestBody QueryPageParam queryPageParam) {

        HashMap param = queryPageParam.getParam();
        String name = (String) param.get("name");
        String assettype = (String) param.get("assettype");

        Page<Record> page = new Page();
        page.setCurrent(queryPageParam.getPageNum());
        page.setSize(queryPageParam.getPageSize());

        QueryWrapper<Record> queryWrapper = new QueryWrapper();
        queryWrapper.apply(" a.asset=b.id  and b.assettype=d.id ");

//        if("2".equals(roleId)){
//            // queryWrapper.eq(Record::getUserid,userId);
//            queryWrapper.apply(" a.userId= "+userId);
//        }

        if (StringUtils.isNotBlank(name) && !"null".equals(name)) {
            queryWrapper.like("b.name", name);
        }
        if (StringUtils.isNotBlank(assettype) && !"null".equals(assettype)) {
            queryWrapper.eq("d.id", assettype);
        }

    IPage result = recordService.pageCC(page,queryWrapper);
        return Result.suc(result.getRecords(),result.getTotal());

}

    @GetMapping("/list")
    public Result list(){
        List list = recordService.list();
        return Result.suc(list);
    }

    @PostMapping("/save")
    public Result save(@RequestBody Record record) throws ParseException {

        LocalDateTime now =LocalDateTime.now();
        Date date = Date.valueOf(now.toLocalDate());
        record.setCreatetime(date);
        Asset asset= assetService.getById(record.getAsset());
        int n = record.getCount();
        if("2".equals(record.getAction())){
            n = -n;
            record.setCount(n);
        }
        int num =asset.getCount()+n;
        asset.setCount(num);
        assetService.updateById(asset);

        return recordService.save(record)?Result.suc():Result.fail();
    }

}
