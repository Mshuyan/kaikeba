package com.sugo.seckill.mapper.order;

import com.sugo.seckill.pojo.TbSeckillGoods;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;


public interface SeckillGoodsMapper extends Mapper<TbSeckillGoods> {
    @Select(value = "select * from tb_seckill_goods where id = #{seckillId} for update")
    @Results(value = {
            @Result(column = "id",property = "id"),
            @Result(column = "product_id",property = "goodsId"),
            @Result(column = "item_id",property = "itemId"),
            @Result(column = "image",property = "image"),
            @Result(column = "images",property = "images"),
            @Result(column = "title",property = "title"),
            @Result(column = "info",property = "info"),
            @Result(column = "price",property = "price"),
            @Result(column = "cost_price",property = "costPrice"),
            @Result(column = "unit_name",property = "unitName"),
            @Result(column = "postage",property = "postage"),
            @Result(column = "add_time",property = "addTime"),
            @Result(column = "status",property = "status"),
            @Result(column = "start_time_date",property = "startTimeDate"),
            @Result(column = "end_time_date",property = "endTimeDate"),
            @Result(column = "stock",property = "stock"),
            @Result(column = "mark",property = "mark"),
            @Result(column = "stock_count",property = "stockCount"),
            @Result(column = "description",property = "description")
    })
    TbSeckillGoods selectByPrimaryKeyBySQLLock(Long seckillId);

    @Select(value = "UPDATE tb_seckill_goods SET stock_count=stock_count-1 WHERE id=? AND stock_count>0")
    int updateSeckillGoodsByPrimaryKeyByLock(Long seckillId);

    @Select(value = "UPDATE tb_seckill_goods SET stock_count= stock_count-1,version=version+1 WHERE seckill_id=? AND version = ?")
    int updateSeckillGoodsByPrimaryKeyByVersion(Long seckillId);

}
