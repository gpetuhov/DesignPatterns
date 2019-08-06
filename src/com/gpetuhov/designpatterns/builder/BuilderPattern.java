package com.gpetuhov.designpatterns.builder;

// BuilderPattern

class AppleDevice {

    public static final int WHITE = 0;
    public static final int BLACK = 1;
    public static final int GOLD = 2;

    public static final int IPHONE = 3;
    public static final int IPAD = 4;
    public static final int IPOD = 5;

    public static class Builder {
        private int mType;
        private int mColor;

        public Builder type(int deviceType) {
            mType = deviceType;
            return this;
        }

        public Builder color(int deviceColor) {
            mColor = deviceColor;
            return this;
        }

        public AppleDevice build() {
            return new AppleDevice(mType, mColor);
        }
    }

    private int mType;
    private int mColor;

    public AppleDevice(int mType, int mColor) {
        this.mType = mType;
        this.mColor = mColor;
    }
}

public class BuilderPattern {
    public static void main(String[] args) {
        AppleDevice device = new AppleDevice.Builder()
                .type(AppleDevice.IPHONE)
                .color(AppleDevice.BLACK)
                .build();
    }
}
