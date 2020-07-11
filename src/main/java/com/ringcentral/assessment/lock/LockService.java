package com.ringcentral.assessment.lock;

public interface LockService {

    /**
     * 加锁
     * @param seconds
     * @return
     */
    public Boolean tryLock(String userId,int seconds);

    /**
     * 解锁
     * @param userId
     * @return
     */
    public Boolean unlock(String userId);
}
