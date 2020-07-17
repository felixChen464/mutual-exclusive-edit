package com.ringcentral.assessment.lock;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 用于文件模块的互斥锁
 *
 * @Author cxh
 * @Date 2020/7/17
 */
@Component
public class FileMutualExclusiveLock extends AbstractMutualExclusiveLock {

    public static final String FILE_PREFIX = "file";

    /**
     * 锁要维持的时间
     */
    @Value(value = "${file.lock.seconds}")
    private Integer fileLockSeconds;

    @Override
    public String getLockKeyPrefix() {
        return FILE_PREFIX;
    }

    @Override
    public Integer getExpireSeconds() {
        return fileLockSeconds;
    }
}
