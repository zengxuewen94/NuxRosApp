package com.nudtit.lib_common.click;

import android.app.Activity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import com.nudtit.lib_common.utils.LogUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

/**
 * @author : LJY
 * @date : 2022/2/24
 * 使用AOP实现避免重复点击。
 *      1，hook机制 在事件传送的过程中截获并监控事件的传输，将自身的代码与系统方法进行融入
 *      2，调用proceedingJoinPoint.proceed()释放监控事件
 *      3，只能截获使用onClickListener方法的点击事件
 * 如果需要使用重复点击 在onClick方法上加上注解 @RepeatClick
 */

@Aspect
public class ClickFilterHook {
    private final String TAG = ClickFilterHook.class.getSimpleName();

    //上次点击的时间
    private Long sLastclick = 0L;
    //拦截所有两次点击时间间隔小于一秒的点击事件
    private final Long FILTER_TIMEM = 1000L;
    //上次点击事件View
    private View lastView;
    //---- add content -----
    //是否过滤点击 默认是
    private boolean checkClick = true;
    //---- add content -----

    @Around("execution(* android.view.View.OnClickListener.onClick(..))")
    public void onClickLitener(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        //大于指定时间
        if (System.currentTimeMillis() - sLastclick >= FILTER_TIMEM) {
            doClick(proceedingJoinPoint);
        } else {
            //---- update content -----  判断是否需要过滤点击
            //小于指定秒数 但是不是同一个view 可以点击  或者不过滤点击
            if (!checkClick || lastView == null || lastView != (proceedingJoinPoint).getArgs()[0]) {
                //---- update content -----  判断是否需要过滤点击
                doClick(proceedingJoinPoint);
            } else {
                //大于指定秒数 且是同一个view
                LogUtils.e(TAG, "重复点击,已过滤");
            }
        }
    }

    @Before("execution(@com.nudtit.lib_common.click.RepeatClick  * *(..))")
    public void beforeEnableDoubleClcik(JoinPoint joinPoint) throws Throwable {
        checkClick = false;
    }

    @Before("execution(@com.nudtit.lib_common.click.FinishClick  * *(..))")
    public void finishActivityClcik(JoinPoint joinPoint) throws Throwable {
        if (lastView != null){
            lastView = null;
            Log.i(TAG, "FinishActivityClcik: 界面销毁  释放View");
        }
    }

    //执行原有的 onClick 方法
    private void doClick(ProceedingJoinPoint joinPoint) throws Throwable {
        //判断 view 是否存在
        if (joinPoint.getArgs().length == 0) {
            joinPoint.proceed();
            return;
        }
        //记录点击的 view
        lastView = (View) (joinPoint).getArgs()[0];
        //---- add content -----
        //修改默认过滤点击
        checkClick = true;
        //---- add content -----
        //记录点击事件
        sLastclick = System.currentTimeMillis();
        //执行点击事件
        try {
            joinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

}
