package com.cbh.geo.util;

import com.cbh.mongo.location.GeoLocation;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.net.URLEncoder;

/**
 * Created by Tommy on 2017/8/9.
 */
public class GeoUtil {
    public GeoLocation googleLocation(String address) throws IOException {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("http://maps.googleapis.com/maps/api/geocode/json?address="+ URLEncoder.encode(address,"UTF8")+"&sensor=false")
                .get()
                .addHeader("cache-control", "no-cache")
                .build();

        Response response = client.newCall(request).execute();
        ObjectMapper objectMapper=new ObjectMapper();
        GeoLocation geoLocation=objectMapper.readValue(response.body().string(),GeoLocation.class);
        return geoLocation;
    }
}
