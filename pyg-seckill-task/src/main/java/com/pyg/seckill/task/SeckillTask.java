package com.pyg.seckill.task;

import com.pyg.mapper.TbSeckillGoodsMapper;
import com.pyg.pojo.TbSeckillGoods;
import com.pyg.pojo.TbSeckillGoodsExample;
import com.pyg.pojo.TbSeckillGoodsExample.Criteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * Description:
 *
 * @author AK
 * @date 2018/8/31 21:30
 * @since 1.0.0
 */
@Component
public class SeckillTask {

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private TbSeckillGoodsMapper seckillGoodsMapper;

    @Scheduled(cron = "*/10 * * * * ?")
    public void startSeckill() {
        TbSeckillGoodsExample example = new TbSeckillGoodsExample();
        Criteria criteria = example.createCriteria();
        criteria.andStatusEqualTo("1");
        criteria.andStockCountGreaterThan(0);
        Date date = new Date();
        Date oldDate = new Date(date.getTime()+43200000);
        criteria.andStartTimeLessThanOrEqualTo(oldDate);
        criteria.andStartTimeGreaterThanOrEqualTo(date);
        List<TbSeckillGoods> seckillGoodsList = seckillGoodsMapper.selectByExample(example);
        for (TbSeckillGoods seckillGoods : seckillGoodsList) {
            redisTemplate.boundHashOps(TbSeckillGoods.class.getName()).put(seckillGoods.getId(), seckillGoods);
        }
        System.out.println("秒杀数据导入成功");
    }
}