package com.ringcentral.assessment.lock.service;

import com.ringcentral.assessment.lock.entity.LockItem;

public interface LockService {

    /**
     * 加锁，单次尝试，立即返回
     *
     * @param lockKey       锁对应的key
     * @param value         锁持有者
     * @param lockSeconds   加锁时间，-1为永久，不得为0
     * @return 加锁成功与否
     */
    Boolean tryLock(String lockKey, String value, Integer lockSeconds);


    /**
     * 加锁，重复尝试，为避免出现死锁以及一般业务场景，不提供永久尝试
     * @param lockKey
     * @param value
     * @param lockSecond
     * @param maxTryTimes
     * @return
     */
    Boolean tryLockWithRetry(String lockKey,String value,Integer lockSecond,Integer maxTryTimes);

    /**
     * 释放锁
     *
     * @param lockKey
     * @return
     */
    void unlock(String lockKey);

    /**
     * 获取锁的当前情况
     *
     * @param lockKey
     * @return 返回一个LockItem来代表当前key值所对应的锁的状态 {@link LockItem}
     */
    LockItem getLockItem(String lockKey);

}
