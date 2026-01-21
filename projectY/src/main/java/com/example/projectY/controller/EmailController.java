package com.example.projectY.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.projectY.entity.Job;
import com.example.projectY.entity.Skill;
import com.example.projectY.entity.Subscriber;
import com.example.projectY.repository.JobRepository;
import com.example.projectY.repository.SubscriberRepository;
import com.example.projectY.response.ResJobEmailDTO;
import com.example.projectY.response.ResSubscriberEmailDTO;
import com.example.projectY.response.ResJobEmailDTO.ResCompanyEmailDTO;
import com.example.projectY.response.ResJobEmailDTO.ResSkillEmailDTO;
import com.example.projectY.service.EmailService;
import com.example.projectY.service.SubscriberService;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/v1")
public class EmailController {
    @Autowired
    private EmailService emailService;

    @Autowired
    private JobRepository jobRepository;


    @Autowired
    private SubscriberRepository subscriberRepository;


    @GetMapping("/email")
    public List<?> sendMail() {
        // List<Subscriber> listSubscriber = this.subscriberRepository.findAll();
        //     if (listSubscriber != null && listSubscriber.size() > 0) {
        //         for(Subscriber subscriber : listSubscriber) {
        //             List<Skill> listSkill = subscriber.getSkills();
        //             if (listSkill != null && listSkill.size() > 0) {
        //                 List<Job> listJob = this.jobRepository.findBySkillsIn(listSkill);
        //                 if (listJob != null && listJob.size() > 0) {
        //                     this.emailService.handleSendMailTempate(
        //                         subscriber.getEmail(), 
        //                         "Jobhunter", 
        //                         subscriber.getName(), 
        //                         "job", listJob);
        //                 }
        //             }
        //         }
        //     }
        List<ResSubscriberEmailDTO> listSubscriberWithJob = this.emailService.handleListJobsBySubscriber();
        for (ResSubscriberEmailDTO subscriberEmailDTO : listSubscriberWithJob) {
            this.emailService.handleSendMailTempate(
                subscriberEmailDTO.getEmail(), 
                "Job hunter", 
                subscriberEmailDTO.getName(), 
                "job", 
                subscriberEmailDTO.getJobs());
        }

        return this.emailService.handleListJobsBySubscriber();
    }
    
}
