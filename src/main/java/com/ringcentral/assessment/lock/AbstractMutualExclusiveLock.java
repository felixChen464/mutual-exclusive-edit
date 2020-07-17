package com.ringcentral.assessment.lock;

import com.ringcentral.assessment.lock.entity.LockItem;
import com.ringcentral.assessment.lock.service.LockService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author cxh
 * @Date 2020/7/17
 */
public abstract class AbstractMutualExclusiveLock {

    public static final String LOCK_SEPARATOR = ":";

    @Autowired
    private LockService lockService;

    /**
     * 锁的前缀
     *
     * @return
     */
    public abstract String getLockKeyPrefix();

    /**
     * 过期时间
     *
     * @return
     */
    public abstract Integer getExpireSeconds();

    /**
     * 锁的内部实现
     *
     * @return
     */
    public final Boolean tryLock(String key, String value) {

        return lockService.tryLock(getLockKeyPrefix() + LOCK_SEPARATOR + key, value, getExpireSeconds());

    }


    /**
     * 加锁，重复尝试，为避免出现死锁以及考慮到一般业务场景，暂不提供永久尝试
     *
     * @param lockKey
     * @param value
     * @param lockSeconds
     * @param maxTryTimes
     * @return
     */
    public Boolean tryLockWithRetry(String lockKey, String value, Integer lockSeconds, Integer maxTryTimes) {
        return lockService.tryLockWithRetry(getLockKeyPrefix() + LOCK_SEPARATOR + lockKey, value, lockSeconds, maxTryTimes);
    }

    /**
     * 释放锁
     *
     * @param lockKey
     * @return
     */
    public void unlock(String lockKey) {
        lockService.unlock(lockKey);
    }

    /**
     * 获取锁的当前情况
     *
     * @param lockKey
     * @return 返回一个LockItem来代表当前key值所对应的锁的状态 {@link LockItem}
     */
    public LockItem getLockItem(String lockKey) {
        return lockService.getLockItem(getLockKeyPrefix() + LOCK_SEPARATOR + lockKey);
    }
}
