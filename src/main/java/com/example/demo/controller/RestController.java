package com.example.demo.controller;

import com.example.demo.model.PredictionData;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URI;
import java.util.concurrent.TimeUnit;

@CrossOrigin
@org.springframework.web.bind.annotation.RestController
public class RestController {
    @PostMapping(path = "/ajax/predict", consumes = "application/json")
    public String predict(@RequestBody PredictionData predictionData) {
        HttpClient httpclient = HttpClientBuilder.create().setConnectionTimeToLive(1000, TimeUnit.MILLISECONDS).build();

        System.out.println("model name : " + predictionData.getModelName());
        StringBuilder sb = new StringBuilder();
        sb.append("localhost:1337/");
        sb.append(predictionData.getModelName());
        sb.append("/predict");
        String url = sb.toString();

        System.out.println("url : " + url);
        try {

            // specify the get request
            HttpPost postRequest = new HttpPost("http://localhost:1337/" + predictionData.getModelName() + "/predict");
            String inputString = "{\"input\": [" + predictionData.inputDataToString() + "]}";
            System.out.println("input : " + inputString);
            StringEntity input = new StringEntity("{\"input\": [" + predictionData.inputDataToString()+ "]}");
            input.setContentType("application/json");
            postRequest.setEntity(input);
            postRequest.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
            HttpResponse httpResponse = httpclient.execute(postRequest);
            HttpEntity entity = httpResponse.getEntity();

            System.out.println("----------------------------------------");
            System.out.println(httpResponse.getStatusLine());
            Header[] headers = httpResponse.getAllHeaders();
            for (int i = 0; i < headers.length; i++) {
                System.out.println(headers[i]);
            }
            System.out.println("----------------------------------------");
            String result = "";
            if (entity != null) {
                result = EntityUtils.toString(entity);
                System.out.println(result);
            }
            return result;
        } catch (Exception e) {
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            String exceptionAsString = sw.toString();
            return exceptionAsString;
        } finally {
            // When HttpClient instance is no longer needed,
            // shut down the connection manager to ensure
            // immediate deallocation of all system resources
            httpclient.getConnectionManager().shutdown();
        }
    }

    @PostMapping(path = "/ajax/rest-template/predict", consumes = "application/json", produces = "application/json")
    public String predictUsingRestTemplate(@RequestBody PredictionData predictionData) {

        URI uri = URI.create("http://localhost:1337/hello-world/predict");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        org.springframework.http.HttpEntity entity = new org.springframework.http.HttpEntity(predictionData, headers);

        RestTemplate restTemplate = new RestTemplate();
        try{
            ResponseEntity<String> responseEntity = restTemplate.exchange(uri, HttpMethod.POST, entity, String.class);
            return responseEntity.toString();
        }catch(Exception e) {
            System.out.println(e.getMessage());
        }
        return "error";
    }
}
