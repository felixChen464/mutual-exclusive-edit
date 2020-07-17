package com.ringcentral.assessment.lock.impl;

import com.ringcentral.assessment.exception.ServerInternalException;
import com.ringcentral.assessment.lock.service.LockService;
import com.ringcentral.assessment.lock.entity.LockItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.params.SetParams;

/**
 * @Author cxh
 * @Date 2020/7/12
 */

@Service
public class RedisLockServiceImpl implements LockService {

    private static final String LOCK_SUCCESS = "OK";

    private static final Long MILLS_PER_SECOND = 1000L;

    @Autowired
    private JedisPool jedisPool;

    @Override
    public Boolean tryLock(String lockKey, String value, Integer lockSeconds) {

        //锁的时间不能为0
        if (lockSeconds == 0) {
            throw new ServerInternalException("lock seconds can't be zero");
        }

        try (Jedis jedis = jedisPool.getResource()) {
            if (StringUtils.equals(jedis.get(lockKey), value)) {
                return true;
            }

            //加锁并设置过期时间需要保证原子性，防止加锁成功后服务挂掉，导致锁不过期
            SetParams setParams = new SetParams().nx();
            if (lockSeconds > 0) {
                setParams.px(lockSeconds * MILLS_PER_SECOND);
            }

            //返回加锁结果
            String result = jedis.set(lockKey, value, setParams);
            return StringUtils.equals(result, LOCK_SUCCESS);
        }
    }

    @Override
    public Boolean tryLockWithRetry(String lockKey, String value, Integer lockSeconds, Integer maxTryTimes) {

        //锁的时间不能为0
        if (lockSeconds == 0) {
            throw new ServerInternalException("lock seconds can't be zero");
        }

        //加锁并设置过期时间需要保证原子性，防止加锁成功后服务挂掉，导致锁不过期
        SetParams setParams = new SetParams().nx();
        if (lockSeconds > 0) {
            setParams.px(lockSeconds * MILLS_PER_SECOND);
        }

        try (Jedis jedis = jedisPool.getResource()) {
            if (StringUtils.equals(jedis.get(lockKey), value)) {
                return true;
            }

            //多次尝试获取锁
            Integer tryTimes = 0;
            while (tryTimes++ < maxTryTimes) {
                String result = jedis.set(lockKey, value, setParams);

                //获取成功
                if (StringUtils.equals(result, LOCK_SUCCESS)) {
                    return true;
                }

                //重试间隔，避免占用大量计算时间片
                Thread.sleep(MILLS_PER_SECOND);
            }
            return false;
        } catch (Exception e) {
            throw new ServerInternalException("internal exception while try to get lock");
        }
    }

    @Override
    public LockItem getLockItem(String lockKey) {

        LockItem lockItem = new LockItem();
        try (Jedis jedis = jedisPool.getResource()) {

            lockItem.setKey(lockKey);
            Long expireTime = jedis.ttl(lockKey);
            String result = null;

            //redis2.8以上，-2表示并无存在
            if(expireTime > -2 ){
                result = jedis.get(lockKey);
            }

            //将redis的格式转换成接口定义的格式
            lockItem.setExpireTime(expireTime == -2 ? 0L : expireTime);

            //如果有持有者，则进行设置
            if (result != null) {
                return lockItem
                        .setValue(result);
            }
        }
        return lockItem;
    }


    @Override
    public void unlock(String lockKey) {

        //直接删除这个锁
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.del(lockKey);
        }
    }
}
