package com.shiyan2.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.shiyan2.entity.Record;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 梁爽
 * @since 2023-04-29
 */
public interface RecordService extends IService<Record> {
    IPage pageCC(IPage<Record> page, Wrapper wrapper);
}
