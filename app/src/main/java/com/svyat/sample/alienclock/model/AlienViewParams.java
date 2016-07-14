package com.svyat.sample.alienclock.model;

/**
 * Created by shromyak on 07.07.2016.
 *
 * View settings used in appearance decorator
 */
public class AlienViewParams {

    private final int top;
    private final int right;
    private final int bottom;
    private final int left;

    private AlienViewParams(Builder builder){
        this.top = builder.top;
        this.right = builder.right;
        this.bottom = builder.bottom;
        this.left = builder.left;
    }

    public int getTop() {
        return top;
    }

    public int getLeft() {
        return left;
    }

    public int getBottom() {
        return bottom;
    }

    public int getRight() {
        return right;
    }

    public int getWidth() {
        return right - left;
    }

    public int getHeight() {
        return bottom - top;
    }

    public int getMinDim() {
        return Math.min(getHeight(), getWidth());
    }

    public int getMaxDim() {
        return Math.min(getHeight(), getWidth());
    }

    public static class Builder {

        private int top;
        private int right;
        private int bottom;
        private int left;

        public Builder setBottom(int bottom) {
            this.bottom = bottom;
            return this;
        }

        public Builder setLeft(int left) {
            this.left = left;
            return this;
        }

        public Builder setRight(int right) {
            this.right = right;
            return this;
        }

        public Builder setTop(int top) {
            this.top = top;
            return this;
        }

        private void validate() {
            assert left <= right;
            assert top <= bottom;
        }

        public AlienViewParams build() {
            validate();
            return new AlienViewParams(this);
        }
    }
}
