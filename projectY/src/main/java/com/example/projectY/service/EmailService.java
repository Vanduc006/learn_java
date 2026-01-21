    package com.example.projectY.service;

    import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
    import org.springframework.mail.SimpleMailMessage;
    import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import com.example.projectY.entity.Job;
import com.example.projectY.entity.Skill;
import com.example.projectY.entity.Subscriber;
import com.example.projectY.repository.JobRepository;
import com.example.projectY.repository.SubscriberRepository;
import com.example.projectY.response.ResJobEmailDTO;
import com.example.projectY.response.ResSubscriberEmailDTO;
import com.example.projectY.response.ResJobEmailDTO.ResCompanyEmailDTO;
import com.example.projectY.response.ResJobEmailDTO.ResSkillEmailDTO;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

    @Service
    public class EmailService {
        @Autowired
        private SpringTemplateEngine springTemplateEngine;

        @Autowired 
        private JavaMailSender mailSender;

        @Autowired
        private JobRepository jobRepository;


        @Autowired
        private SubscriberRepository subscriberRepository;


        public void handleSendMail() {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setTo("ducnv2411150@usth.edu.vn");
            simpleMailMessage.setSubject("Job hunter");
            simpleMailMessage.setText("Test mail service");
            this.mailSender.send(simpleMailMessage);
        }

        public void handleSendMailSync(String to, String subject, String content, boolean isMultiPart, boolean isHtml) {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            try {
                MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, isMultiPart, StandardCharsets.UTF_8.name());
                messageHelper.setTo(to);
                messageHelper.setSubject(subject);
                messageHelper.setText(content,isHtml);
                this.mailSender.send(mimeMessage);
            }
            catch (MailException | MessagingException e) {
                System.out.println(e);
            }
        }

        public List<ResSubscriberEmailDTO> handleListJobsBySubscriber() {

            List<Subscriber> listSubscriber = this.subscriberRepository.findAll();
            List<ResSubscriberEmailDTO> listSubscriberEmailDTO = new ArrayList<>();

            for (Subscriber subscriber : listSubscriber) {
                ResSubscriberEmailDTO resSubscriberEmailDTO = ResSubscriberEmailDTO.builder()
                .name(subscriber.getName())
                .email(subscriber.getEmail())
                .build();

                List<Skill> listSkill = subscriber.getSkills();
                if (listSkill != null && listSkill.size() > 0) {
                    List<Job> listJob = this.jobRepository.findBySkillsIn(listSkill);
                    if (listJob != null && listJob.size() > 0) {
                        List<ResJobEmailDTO> listJobEmailDTO = listJob.stream().map(job -> {
                            ResJobEmailDTO resJobEmailDTO = new ResJobEmailDTO();
                            BeanUtils.copyProperties(job, resJobEmailDTO);

                            ResCompanyEmailDTO resCompanyEmailDTO = new ResCompanyEmailDTO();
                            BeanUtils.copyProperties(job.getCompany(), resCompanyEmailDTO);
                            
                            resJobEmailDTO.setCompany(resCompanyEmailDTO);

                            resJobEmailDTO.setLevel(job.getLevel());

                            List<ResSkillEmailDTO> listSkillEmailDTO = job.getSkills().stream().map(
                                skill -> {
                                    ResSkillEmailDTO resSkillEmailDTO = new ResSkillEmailDTO();
                                    BeanUtils.copyProperties(skill, resSkillEmailDTO);
                                    return resSkillEmailDTO;
                                }
                            ).toList();

                            resJobEmailDTO.setSkills(listSkillEmailDTO);

                            return resJobEmailDTO;
                        }).toList();
                        resSubscriberEmailDTO.setJobs(listJobEmailDTO);
                    }
                }
                listSubscriberEmailDTO.add(resSubscriberEmailDTO);
            }

            // if (listJob != null && listJob.size() > 0) {
                

            //     // this.emailService.handleSendMailTempate(
            //     //     subscriber.getEmail(), 
            //     //     "Jobhunter", 
            //     //     subscriber.getName(), 
            //     //     "job", listJob)
            // }

            return listSubscriberEmailDTO;
        }

        @Async
        public void handleSendMailTempate(String to, String subject, String name, String templateName, List<?> listJob) {
            Context context = new Context();
            // List<Job> listJobb = this.jobRepository.findAll();
            context.setVariable("component", name);
            context.setVariable("jobs", listJob);
            String content = springTemplateEngine.process(templateName, context);
            this.handleSendMailSync(to, subject, content, false,
                 true);
        }

        // @Scheduled(fixedRate = 5000)
        public void handleCron() {
            System.out.println("Cron");
            // Using cron in controller
        }

        
    }
    
