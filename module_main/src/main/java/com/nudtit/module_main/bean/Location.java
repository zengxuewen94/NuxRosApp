package com.nudtit.module_main.bean;

public class Location {
    private float x;
    private float y;
    private float z;

    public Location() {
        this.x = this.y = this.z = 0.0F;
    }

    public Location(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Location(Location rhs) {
        this.x = rhs.x;
        this.y = rhs.y;
        this.z = rhs.z;
    }

    public float distanceTo(Location that) {
        float dx = that.x - this.x;
        float dy = that.y - this.y;
        float dz = that.z - this.z;
        return (float)Math.sqrt((double)(dx * dx + dy * dy + dz * dz));
    }

    public float getX() {
        return this.x;
    }

    public void setX(float v) {
        this.x = v;
    }

    public float getY() {
        return this.y;
    }

    public void setY(float v) {
        this.y = v;
    }

    public float getZ() {
        return this.z;
    }

    public void setZ(float v) {
        this.z = v;
    }
}
