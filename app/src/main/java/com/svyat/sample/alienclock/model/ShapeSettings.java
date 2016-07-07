package com.svyat.sample.alienclock.model;

import android.graphics.RectF;

/**
 * Created by shromyak on 07.07.2016.
 */
public class ShapeSettings {

    private final RectF frame;
    private final ShapeType shapeType;
    private int intValue;
    private float floatValue;

    private ShapeSettings(Builder builder) {
        this.frame = builder.frame;
        this.shapeType = builder.shapeType;
    }

    public RectF getFrame() {
        return frame;
    }

    public ShapeType getShapeType() {
        return shapeType;
    }

    public int getIntValue() {
        return intValue;
    }

    public float getFloatValue() {
        return floatValue;
    }

    public static class Builder {

        private RectF frame;
        private ShapeType shapeType;
        private int intValue;
        private float floatValue;

        public Builder setFloatValue(float floatValue) {
            this.floatValue = floatValue;
            return this;
        }

        public Builder setIntValue(int intValue) {
            this.intValue = intValue;
            return this;
        }

        public Builder setShapeType(ShapeType shapeType) {
            this.shapeType = shapeType;
            return this;
        }

        public Builder setFrame(RectF frame) {
            this.frame = frame;
            return this;
        }

        public ShapeSettings build() {
            return new ShapeSettings(this);
        }
    }

    public enum ShapeType {
        SECONDS
    }
}
