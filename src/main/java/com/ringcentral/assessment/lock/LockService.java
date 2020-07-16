package com.ringcentral.assessment.lock;

import com.ringcentral.assessment.lock.entity.LockItem;

public interface LockService {

    /**
     * 加锁
     *
     * @param seconds
     * @return
     */
    public Boolean tryLock(String lockKey,String value, int seconds);

    /**
     * 获取锁的过期时间
     * @param lockKey
     * @return
     */
    public LockItem getLockItem(String lockKey);


    /**
     * 解锁
     *
     * @param lockKey
     * @return
     */
    public Boolean unlock(String lockKey);

}
