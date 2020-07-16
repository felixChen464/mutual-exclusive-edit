package com.ringcentral.assessment.lock.impl;

import com.ringcentral.assessment.lock.LockService;
import com.ringcentral.assessment.lock.entity.LockItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Long Mills_PER_SECOND = 1000L;

    private static Logger logger = LoggerFactory.getLogger(RedisLockServiceImpl.class);

    @Autowired
    private JedisPool jedisPool;

    @Override
    public Boolean tryLock(String lockKey, String value, int seconds) {

        if (seconds < 0) {
            logger.error("lock times invalid,lock time:{}", seconds);
            return false;
        }
        try (Jedis jedis = jedisPool.getResource()) {
            if(StringUtils.equals(jedis.get(lockKey) ,value)){
                return true;
            }

            //加锁并设置过期时间需要保证原子性，防止加锁成功后服务挂掉，导致锁不过期
            String result = jedis.set(lockKey, value, new SetParams().nx().px(seconds * Mills_PER_SECOND));
            return StringUtils.equals(result, LOCK_SUCCESS);
        }
    }

    @Override
    public LockItem getLockItem(String lockKey) {

        LockItem lockItem = new LockItem();
        try (Jedis jedis = jedisPool.getResource()) {
            Long expireTime = jedis.ttl(lockKey);
            String result = jedis.get(lockKey);
            if (result != null) {
                return lockItem.setKey(lockKey)
                        .setValue(result)
                        .setExpireTime(expireTime);
            }
        }
        return lockItem;
    }

    @Override
    public Boolean unlock(String lockKey) {

        //todo 实现解锁
        return null;
    }
}
