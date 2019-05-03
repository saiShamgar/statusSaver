package com.pg.whatsstatussaver.Events;

import android.graphics.Bitmap;

import java.util.ArrayList;

public class EventBusClass {

    public static class ActivityServiceMessage{

        private ArrayList<String> images;
        private ArrayList<Bitmap> bitmaps;

        public ActivityServiceMessage(ArrayList<String> images, ArrayList<Bitmap> bitmaps) {
            this.images = images;
            this.bitmaps = bitmaps;
        }

        public ArrayList<String> getImages() {
            return images;
        }

        public void setImages(ArrayList<String> images) {
            this.images = images;
        }

        public ArrayList<Bitmap> getBitmaps() {
            return bitmaps;
        }

        public void setBitmaps(ArrayList<Bitmap> bitmaps) {
            this.bitmaps = bitmaps;
        }
    }
}
