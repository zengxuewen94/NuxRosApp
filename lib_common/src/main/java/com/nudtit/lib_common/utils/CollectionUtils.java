package com.nudtit.lib_common.utils;

import java.util.Collection;

/**
 * @author :  zengxuewen
 * @date :  2024/5/10
 * @desc :  集合工具类
 */
public class CollectionUtils {


    public static boolean isNotEmpty(final Collection<?> coll) {
        return !isEmpty(coll);
    }

    public static boolean isEmpty(final Collection<?> coll) {
        return coll == null || coll.isEmpty();
    }
}
