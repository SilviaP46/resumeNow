package com.app.demo.service;

import com.app.demo.model.Job;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

@Component
public class WebScrapingService {

    private ArrayList<Job> jobs;
    //private Map<Map<String, String>, String> jobs;
    private static final String WEBSITE = "https://www.ejobs.ro";

    public ArrayList<Job> scrape() throws IOException {
        jobs = new ArrayList<Job>();
        scanItems("manager");
        System.out.println(jobs);
        return jobs;
    }

    public void scanItems(String s) throws IOException {
        Document doc;

        s = s.replaceAll("\\s+", "-");

        try {
            doc = Jsoup.connect("https://www.ejobs.ro/locuri-de-munca/" + s).get();
        } catch (IOException ignored) {
            System.out.println("Could not scan items.");
            return;
        }

        Elements elems = doc.getElementsByClass("JobCard");

        int limit = 0;
        for (Element el : elems) {
            Elements links = el.getElementsByTag("a");
            for (Element l : links) {
                limit++;
                String link = l.attributes().get("href");
                if(findJobDetails(link)!=null)
                    jobs.add(findJobDetails(link));
                if (limit == 50)
                    break;

            }
        }
    }

    private Job findJobDetails(String jobLink) {
        //Map<String, String> jobDetails = new HashMap<>();
        Document doc;
        Job job = new Job();

        try {
            doc = Jsoup.connect(WEBSITE + jobLink).get();
            //System.out.println(WEBSITE + jobLink);


        } catch (IOException ignored) {
            System.out.println("Could Not find the title");
            return null;
        }


        String title = doc.getElementsByTag("h1").text();

        job.setTitle(title);
        job.setLink(WEBSITE + jobLink);

        Elements details = doc.getElementsByClass("JDSummary");

        if (details.size() != 0) {

            int i = 0;
            while (i <= 7) {
                switch (i) {
                    case 0 -> {
                        Elements d = details.get(i).getElementsByClass("JDSummary__Link");
                        if (Objects.equals(d.text(), ""))
                            job.setCity("nespecificat");
                        else
                            job.setCity(d.text());

                        break;
                    }
                    case 1 -> {
                        Elements d = details.get(i).getElementsByClass("JDSummary__Link");
                        if (Objects.equals(d.text(), ""))
                            job.setType("nespecificat");
                        else
                            job.setType(d.text());
                        break;
                    }
                    case 2 -> {
                        Elements d = details.get(i).getElementsByClass("JDSummary__Link");
                        if (Objects.equals(d.text(), ""))
                            job.setLevel("nespecificat");
                        else
                            job.setLevel(d.text());
                        break;
                    }
                    case 3 -> {
                        Elements d = details.get(i).getElementsByClass("JDSummary__Link");
                        if (Objects.equals(d.text(), ""))
                            job.setStudies("nespecificat");
                        else
                            job.setStudies(d.text());
                        break;
                    }
                    case 4 -> {
                        Elements d = details.get(i).getElementsByClass("JDSummary__Link");
                        if (Objects.equals(d.text(), ""))
                            job.setDepartment("nespecificat");
                        else
                            job.setDepartment(d.text());
                        break;
                    }
                    case 5 -> {
                        Elements d = details.get(i).getElementsByClass("JDSummary__Link");
                        if (Objects.equals(d.text(), ""))
                            job.setSalary("nespecificat");
                        else
                            job.setSalary(d.text());
                        break;
                    }
                    case 6 -> {
                        Elements d = details.get(i).getElementsByClass("JDSummary__Link");
                        if (Objects.equals(d.text(), ""))
                            job.setIndustry("nespecificat");
                        else
                            job.setIndustry(d.text());
                        break;
                    }
                    default -> {
                    }
                    //nothing
                }
                i++;
            }

        }

        if(job.getCity()==null)
            return null;

        return job;

    }


}
