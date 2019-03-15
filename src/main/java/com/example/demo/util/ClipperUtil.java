package com.example.demo.util;

public class ClipperUtil {
    public static String makePredictionUrl(String model) {
        StringBuilder sb = new StringBuilder();
        sb.append("http://");
        sb.append("clipper.com:1337/");
        sb.append(model);
        sb.append("/predict");
        return sb.toString();
    }
}
