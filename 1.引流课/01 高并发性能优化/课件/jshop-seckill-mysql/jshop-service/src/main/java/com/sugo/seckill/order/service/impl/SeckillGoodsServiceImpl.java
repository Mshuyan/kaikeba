package com.sugo.seckill.order.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.sugo.seckill.mapper.order.SeckillGoodsMapper;
import com.sugo.seckill.order.service.SeckillGoodsService;
import com.sugo.seckill.page.PageResult;
import com.sugo.seckill.pojo.TbSeckillGoods;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 服务实现层
 * @author Administrator
 *
 */
@Service
public class SeckillGoodsServiceImpl implements SeckillGoodsService {

      @Autowired
      private SeckillGoodsMapper seckillGoodsMapper;
      
      /**
       * @Description: 商品详情信息
       * @Author: hubin
       * @CreateDate: 2020/6/9 18:44
       * @UpdateUser: hubin
       * @UpdateDate: 2020/6/9 18:44
       * @UpdateRemark: 修改内容
       * @Version: 1.0
       */
      @Override
      public PageResult findPage(int pageNum, int pageSize) {
            //PageHelper.startPage(pageNum, pageSize);
            PageInfo<TbSeckillGoods> page = new PageInfo<>(seckillGoodsMapper.selectByExample(null));
            return new PageResult(page.getTotal(), page.getList());
      }

      
      /**
       * @Description: 根据商品id获取商品详情
       * @Author: hubin
       * @CreateDate: 2020/6/9 18:31
       * @UpdateUser: hubin
       * @UpdateDate: 2020/6/9 18:31
       * @UpdateRemark: 修改内容
       * @Version: 1.0
       */
      @Override
      public TbSeckillGoods findOne(Integer id){


            //第一种方式：直接从数据库查询
            TbSeckillGoods tbSeckillGoods = seckillGoodsMapper.selectByPrimaryKey(id);
            if(tbSeckillGoods != null){
                  return tbSeckillGoods;
            }
            return tbSeckillGoods;
            
      }

      /**
       * @Description: 商品详情
       * @Author: hubin
       * @CreateDate: 2020/6/6 11:13
       * @UpdateUser: hubin
       * @UpdateDate: 2020/6/6 11:13
       * @UpdateRemark: 修改内容
       * @Version: 1.0
       */


      public int insertOne(TbSeckillGoods tbSeckillGoods){
           return seckillGoodsMapper.insertSelective(tbSeckillGoods);
      }

      /**
       * 根据秒杀ID查询
       * @param id 秒杀ID
       * @return
       */
      @Override
      public List<String> getByIdImg(Integer id)  {
            ArrayList<String> images = new ArrayList<>();
            TbSeckillGoods storeSeckill = seckillGoodsMapper.selectByPrimaryKey(id);
            if (storeSeckill != null){
                  String imageStr = storeSeckill.getImages();
                  String[] split = imageStr.split(",");
                  for (String s : split) {
                        images.add(s);
                  }
                  images.add(0,storeSeckill.getImage());
                  return images;
            }
            return null;
      }

}
