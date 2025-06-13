package com.infonet.ocr.model;

import java.util.List;

public class OcrRequest {
    private List<Image> images;

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }
}