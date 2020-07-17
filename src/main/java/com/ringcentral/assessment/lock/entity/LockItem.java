package com.ringcentral.assessment.lock.entity;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Author cxh
 * @Date 2020/7/12
 */

@Data
@Accessors(chain = true)
public class LockItem {

    /**
     * 锁的key
     */
    private String key;

    /**
     * 锁的value，当value为空代表当前没有持有者
     */
    private String value;

    /**
     * 锁的过期时间，-1代表永久，0代表锁没有被持有，大于0代表是被持有的有限过期时间,以秒为单位
     */
    private Long expireTime;
}
