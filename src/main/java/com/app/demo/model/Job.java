package com.app.demo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Job {

    private String title;
    private String link;
    private String city;
    private String type;
    private String level;
    private String studies;
    private String department;
    private String salary;
    private String industry;

}
