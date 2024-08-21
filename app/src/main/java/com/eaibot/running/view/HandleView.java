package com.eaibot.running.view;

import android.content.Context;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.eaibot.running.R;


/**
 * @author Administrator
 */
public class HandleView extends RelativeLayout implements AnimationListener {

    private static final float BOX_TO_CIRCLE_RATIO = 1.363636f;
    private static final float FLOAT_EPSILON = 0.001f;
    private static final float THUMB_DIVET_RADIUS = 16.5f;
    private static final float POST_LOCK_MAGNET_THETA = 20.0f;
    private static final int INVALID_POINTER_ID = -1;
    private RelativeLayout mainLayout;
    public ImageView thumbDivet;
    private float contactTheta;
    private float normalizedMagnitude;
    private float contactRadius;
    private float deadZoneRatio = Float.NaN;
    private float joystickRadius = Float.NaN;
    private float normalizingMultiplier;
    private volatile boolean turnInPlaceMode;
    private float turnInPlaceStartTheta = Float.NaN;
    private float rightTurnOffset;
    private volatile float currentOrientation;
    private int pointerId = INVALID_POINTER_ID;
    private Point contactUpLocation;
    private boolean previousVelocityMode;
    private boolean magnetizedXAxis;

    public HandleView(Context context) {
        super(context);
        initVirtualJoystick(context);
    }

    public HandleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initVirtualJoystick(context);
    }

    public HandleView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void onAnimationEnd(Animation animation) {
        contactRadius = 0f;
        normalizedMagnitude = 0f;
    }

    @Override
    public void onAnimationRepeat(Animation animation) {
    }

    @Override
    public void onAnimationStart(Animation animation) {
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        final int action = event.getAction();
        switch (action & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_MOVE: {
                if (pointerId != INVALID_POINTER_ID) {
                    if (previousVelocityMode) {
                        if (inLastContactRange(event.getX(event.getActionIndex()), event.getY(event.getActionIndex()))) {
                            onContactMove(contactUpLocation.x + joystickRadius, contactUpLocation.y + joystickRadius);
                        } else {
                            previousVelocityMode = false;
                        }
                    } else {
                        onContactMove(event.getX(event.findPointerIndex(pointerId)), event.getY(event.findPointerIndex(pointerId)));
                    }
                }
                break;
            }
            case MotionEvent.ACTION_DOWN: {
                pointerId = event.getPointerId(event.getActionIndex());
                onContactDown();
                if (inLastContactRange(event.getX(event.getActionIndex()), event.getY(event.getActionIndex()))) {
                    previousVelocityMode = true;
                    onContactMove(contactUpLocation.x + joystickRadius, contactUpLocation.y + joystickRadius);
                } else {
                    onContactMove(event.getX(event.getActionIndex()), event.getY(event.getActionIndex()));
                }
                break;
            }
            case MotionEvent.ACTION_POINTER_UP:
            case MotionEvent.ACTION_UP: {
                if ((action & MotionEvent.ACTION_POINTER_ID_MASK) >> MotionEvent.ACTION_POINTER_ID_SHIFT == pointerId) {
                    onContactUp();
                }
                break;
            }
            default:
                break;
        }
        return true;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int minParentSize = 200;
        int maxParentSize = 400;
        super.onLayout(changed, l, t, r, b);
        if (mainLayout.getWidth() != mainLayout.getHeight()) {
            this.setOnTouchListener(null);
        }
        float parentSize = mainLayout.getWidth();
        if (parentSize < minParentSize || parentSize > maxParentSize) {
            this.setOnTouchListener(null);
        }
        joystickRadius = mainLayout.getWidth() / 2;
        normalizingMultiplier = BOX_TO_CIRCLE_RATIO / (parentSize / 2);
        deadZoneRatio = THUMB_DIVET_RADIUS * normalizingMultiplier;
    }

    private void animateIntensityCircle(float endScale) {
        AnimationSet intensityCircleAnimation = new AnimationSet(true);
        intensityCircleAnimation.setInterpolator(new LinearInterpolator());
        intensityCircleAnimation.setFillAfter(true);
        RotateAnimation rotateAnim;
        rotateAnim = new RotateAnimation(contactTheta, contactTheta, joystickRadius, joystickRadius);
        rotateAnim.setInterpolator(new LinearInterpolator());
        rotateAnim.setDuration(0);
        rotateAnim.setFillAfter(true);
        intensityCircleAnimation.addAnimation(rotateAnim);
        ScaleAnimation scaleAnim;
        scaleAnim = new ScaleAnimation(contactRadius, endScale, contactRadius, endScale, joystickRadius, joystickRadius);
        scaleAnim.setDuration(0);
        scaleAnim.setFillAfter(true);
        intensityCircleAnimation.addAnimation(scaleAnim);
    }

    private void animateIntensityCircle(float endScale, long duration) {
        AnimationSet intensityCircleAnimation = new AnimationSet(true);
        intensityCircleAnimation.setInterpolator(new LinearInterpolator());
        intensityCircleAnimation.setFillAfter(true);
        intensityCircleAnimation.setAnimationListener(this);
        RotateAnimation rotateAnim;
        rotateAnim = new RotateAnimation(contactTheta, contactTheta, joystickRadius, joystickRadius);
        rotateAnim.setInterpolator(new LinearInterpolator());
        rotateAnim.setDuration(duration);
        rotateAnim.setFillAfter(true);
        intensityCircleAnimation.addAnimation(rotateAnim);
        ScaleAnimation scaleAnim;
        scaleAnim = new ScaleAnimation(contactRadius, endScale, contactRadius, endScale, joystickRadius, joystickRadius);
        scaleAnim.setDuration(duration);
        scaleAnim.setFillAfter(true);
        intensityCircleAnimation.addAnimation(scaleAnim);
    }

    private float differenceBetweenAngles(float angle0, float angle1) {
        return Math.abs((angle0 + 180 - angle1) % 360 - 180);
    }

    private void endTurnInPlaceRotation() {
        turnInPlaceMode = false;
    }

    public ImageView center_divet;

    private void initVirtualJoystick(Context context) {
        setGravity(Gravity.CENTER);
        LayoutInflater.from(context).inflate(R.layout.handle_view, this, true);
        mainLayout = findViewById(R.id.virtual_joystick_layout);
        thumbDivet = findViewById(R.id.thumb_divet);
        center_divet = findViewById(R.id.center_divet);
        animateIntensityCircle(0);
        contactTheta = 0;
        contactUpLocation = new Point(0, 0);
    }

    private void onContactDown() {
        thumbDivet.setAlpha(1.0f);
    }

    private float radius90 = 90;
    private float radius270 = 270;
    private void onContactMove(float x, float y) {
        float radius1 = 1f;
        float radius360 = 360;
        float thumbDivetX = x - joystickRadius;
        float thumbDivetY = y - joystickRadius;
        contactTheta = (float) (Math.atan2(thumbDivetY, thumbDivetX) * 180 / Math.PI + 90);
        contactRadius = (float) Math.sqrt(thumbDivetX * thumbDivetX + thumbDivetY * thumbDivetY) * normalizingMultiplier;
        normalizedMagnitude = (contactRadius - deadZoneRatio) / (1 - deadZoneRatio);
        if (contactRadius >= radius1) {
            thumbDivetX /= contactRadius;
            thumbDivetY /= contactRadius;
            normalizedMagnitude = 1f;
            contactRadius = 1f;
        } else if (contactRadius < deadZoneRatio) {
            thumbDivetX = 0;
            thumbDivetY = 0;
            normalizedMagnitude = 0f;
        }

        if (!magnetizedXAxis) {
            float magnetTheta = 10.0f;
            if ((contactTheta + radius360) % radius90 < magnetTheta) {
                contactTheta -= ((contactTheta + 360) % 90);
            } else if ((contactTheta + radius360) % radius90 > (radius90 - magnetTheta)) {
                contactTheta += (90 - ((contactTheta + 360) % 90));
            }
            if (floatCompare(contactTheta, radius90) || floatCompare(contactTheta, radius270)) {
                magnetizedXAxis = true;
            }
        } else {
            if (differenceBetweenAngles((contactTheta + radius360) % radius360, radius90) < POST_LOCK_MAGNET_THETA) {
                contactTheta = 90;
            } else if (differenceBetweenAngles((contactTheta + radius360) % radius360, radius270) < POST_LOCK_MAGNET_THETA) {
                contactTheta = 270;
            }
            else {
                magnetizedXAxis = false;
            }
        }

        animateIntensityCircle(contactRadius);
        updateThumbDivet(thumbDivetX, thumbDivetY);
        onMoveListener.onMove(normalizedMagnitude * Math.cos(contactTheta * Math.PI / 180.0),
                normalizedMagnitude * Math.sin(contactTheta * Math.PI / 180.0));
        updateTurnInPlaceMode();
    }

    private void updateTurnInPlaceMode() {
        if (!turnInPlaceMode) {
            if (floatCompare(contactTheta, radius270)) {
                turnInPlaceMode = true;
                rightTurnOffset = 0;
            } else if (floatCompare(contactTheta, radius90)) {
                turnInPlaceMode = true;
                rightTurnOffset = 15;
            } else {
                return;
            }
            initiateTurnInPlace();
        } else if (!(floatCompare(contactTheta, radius270) || floatCompare(contactTheta, radius90))) {
            endTurnInPlaceRotation();
        }
    }

    private void onContactUp() {
        animateIntensityCircle(0, (long) (normalizedMagnitude * 1000));
        contactUpLocation.x = (int) (thumbDivet.getTranslationX());
        contactUpLocation.y = (int) (thumbDivet.getTranslationY());
        updateThumbDivet(0, 0);
        pointerId = INVALID_POINTER_ID;
        onMoveListener.onMove(0,0);
        endTurnInPlaceRotation();
    }

    private void initiateTurnInPlace() {
        turnInPlaceStartTheta = (currentOrientation + 360) % 360;
        RotateAnimation rotateAnim;
        rotateAnim = new RotateAnimation(rightTurnOffset, rightTurnOffset, joystickRadius, joystickRadius);
        rotateAnim.setInterpolator(new LinearInterpolator());
        rotateAnim.setDuration(0);
        rotateAnim.setFillAfter(true);
        rotateAnim = new RotateAnimation(15, 15, joystickRadius, joystickRadius);
        rotateAnim.setInterpolator(new LinearInterpolator());
        rotateAnim.setDuration(0);
        rotateAnim.setFillAfter(true);
    }

    private void updateTurnInPlaceRotation() {
        final float currentTheta = (currentOrientation + 360) % 360;
        float offsetTheta;
        offsetTheta = (turnInPlaceStartTheta - currentTheta + 360) % 360;
        offsetTheta = 360 - offsetTheta;
        offsetTheta = (int) (offsetTheta - (offsetTheta % 15));
        RotateAnimation rotateAnim;
        rotateAnim = new RotateAnimation(offsetTheta + rightTurnOffset, offsetTheta + rightTurnOffset, joystickRadius, joystickRadius);
        rotateAnim.setInterpolator(new LinearInterpolator());
        rotateAnim.setDuration(0);
        rotateAnim.setFillAfter(true);
        rotateAnim = new RotateAnimation(offsetTheta + 15, offsetTheta + 15, joystickRadius, joystickRadius);
        rotateAnim.setInterpolator(new LinearInterpolator());
        rotateAnim.setDuration(0);
        rotateAnim.setFillAfter(true);
    }

    private void updateThumbDivet(float x, float y) {
        thumbDivet.setTranslationX(-THUMB_DIVET_RADIUS);
        thumbDivet.setTranslationY(-THUMB_DIVET_RADIUS);
        thumbDivet.setRotation(contactTheta);
        thumbDivet.setTranslationX(x);
        thumbDivet.setTranslationY(y);
    }

    private boolean floatCompare(float v1, float v2) {
        return Math.abs(v1 - v2) < FLOAT_EPSILON;
    }

    private boolean inLastContactRange(float x, float y) {
        return Math.sqrt((x - contactUpLocation.x - joystickRadius)
                * (x - contactUpLocation.x - joystickRadius) + (y - contactUpLocation.y - joystickRadius)
                * (y - contactUpLocation.y - joystickRadius)) < THUMB_DIVET_RADIUS;
    }

    private OnMoveListener onMoveListener;
    public void setOnMoveListener(OnMoveListener onMoveListener) {
        this.onMoveListener = onMoveListener;
    }

    public interface OnMoveListener {
        /**
         * 移动回调
         * @param linear 线速度
         * @param anguler 角速度
         */
        void onMove(double linear, double anguler);
    }

}
