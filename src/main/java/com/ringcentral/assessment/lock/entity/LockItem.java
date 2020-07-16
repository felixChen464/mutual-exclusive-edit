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

    private String key;

    private String value;

    private Long expireTime;
}
