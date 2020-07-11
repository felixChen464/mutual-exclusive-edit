package com.ringcentral.assessment.util;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author cxh
 * @Date 2020/7/11
 */
public class MapUtil {

    public static Map<Object, Object> asMap(Object... elements){
        if(elements.length % 2 != 0){
            throw new RuntimeException("the number of elements should be even");
        }

        Map<Object, Object> result = new HashMap<>(elements.length / 2);
        for(int i = 0 ; i < elements.length;i++){
            result.put(elements[2 * i],elements[2 * i + 1]);
        }

        return result;
    }
}
