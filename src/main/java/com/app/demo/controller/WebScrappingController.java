package com.app.demo.controller;
import com.app.demo.model.Job;
import com.app.demo.service.WebScrapingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;

@RestController
@CrossOrigin(origins = "*", originPatterns = "*", allowedHeaders = "*")
public class WebScrappingController {

    @Autowired
    private WebScrapingService webScrapingService;

    @GetMapping("/getJobs")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ArrayList<Job>> findAll() throws IOException {
        return new ResponseEntity<>(webScrapingService.scrape(), HttpStatus.OK);
    }

}
