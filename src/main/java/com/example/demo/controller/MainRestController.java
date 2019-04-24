package com.example.demo.controller;

import com.example.demo.model.ClipperRestPostBody;
import com.example.demo.model.PredictionData;

import com.example.demo.service.CommandService;
import com.example.demo.service.DatabaseService;
import com.example.demo.service.RestService;
import com.example.demo.util.ClipperUtil;
import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Arrays;

@RestController
public class MainRestController {
    private final Logger logger = LogManager.getLogger("MainRestController");
    private final RestService restService;
    private final CommandService commandService;
    private final DatabaseService databaseService;

    @Autowired
    public MainRestController(RestService restService,
                              CommandService commandService,
                              DatabaseService databaseService) {
        this.commandService = commandService;
        this.restService = restService;
        this.databaseService = databaseService;
    }

    @PostMapping(path = "/ajax/predict", consumes = "application/json")
    public String predict(@RequestBody PredictionData predictionData) {
        System.out.println("model name : " + predictionData.getModelName());
        String uri = ClipperUtil.makePredictionUrl(predictionData.getModelName());
        logger.info("url : " + uri);
        ClipperRestPostBody clipperRestPostBody = new ClipperRestPostBody();
        String inputString = predictionData.inputDataToString();
        String[] inputArray = inputString.split(",");

        double[] doubleValues = Arrays.stream(inputArray)
                .mapToDouble(Double::parseDouble)
                .toArray();

        clipperRestPostBody.setInput(doubleValues);

        Gson gson = new Gson();
        String data = gson.toJson(clipperRestPostBody);
        return restService.restPostContentJson(uri, data);
    }

    @PostMapping(path = "/ajax/rest-template/predict", consumes = "application/json", produces = "application/json")
    public String predictUsingRestTemplate(@RequestBody PredictionData predictionData) {

        String url = "http://localhost:1337/hello-world/predict";
        Gson gson = new Gson();
        String data = gson.toJson(predictionData);
        return restService.restPostContentJson(url, data);
    }

    @PostMapping(path= "/ajax/testCommand")
    public void testCommand() {
        try {
            commandService.runCommand("aaa");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @PostMapping(path= "/ajax/testDb")
    public void testDb() {
        this.databaseService.getUser();
    }
}
