package com.nudtit.slam.utils;


import android.graphics.Point;
import android.graphics.PointF;

import com.nudtit.slam.bean.Size;


final public class PointTranslateUtil {

    public static PointF translate(com.nudtit.slam.bean.PointF pt) {
        return new PointF(pt.getX(), pt.getY());
    }

    public static com.nudtit.slam.bean.PointF translate(PointF pt) {
        return new com.nudtit.slam.bean.PointF(pt.x, pt.y);
    }

    public static Point translate(Size sz) {
        return new Point(sz.getWidth(), sz.getHeight());
    }

}
