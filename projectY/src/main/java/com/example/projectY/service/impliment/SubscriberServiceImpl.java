package com.example.projectY.service.impliment;

import java.lang.foreign.Linker.Option;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.example.projectY.entity.Skill;
import com.example.projectY.entity.Subscriber;
import com.example.projectY.repository.SubscriberRepository;
import com.example.projectY.service.SkillService;
import com.example.projectY.service.SubscriberService;


@Service
public class SubscriberServiceImpl implements SubscriberService{
    @Autowired
    private SubscriberRepository subscriberRepository;

    @Autowired
    private SkillService skillService;

    public Subscriber handleCreateSubsriber(Subscriber newsSubscriber) {
        if (this.subscriberRepository.existsByEmail(newsSubscriber.getEmail())) {
            throw new DuplicateKeyException("Subscriber already exists");
        }
        if (newsSubscriber.getSkills() != null) {
            List<Skill> validList = this.skillService.handleValidSkills(newsSubscriber.getSkills());
            newsSubscriber.setSkills(validList);
        }
        return this.subscriberRepository.save(newsSubscriber);
    }
    
    public Page<Subscriber> handleGetAllSubscribers(Pageable subscriberPageable, Specification<Subscriber> subscriberSpecification) {
        return this.subscriberRepository.findAll(subscriberSpecification, subscriberPageable);
    }
    public Subscriber handleGetSubscriberById(Long id) {
        return this.subscriberRepository.findById(id).orElseThrow(() -> 
            new NoSuchElementException("Subscriber not found")
        );
    }


    public Subscriber handleUpdateSubscriber(Long id,Subscriber updateSubscriber) {
        // if (this.subscriberRepository.exexistsByEmail(updateSubscriber.getEmail())) {
        //     throw new DuplicateKeyException("Subscriber already exists");
        // }
        Subscriber currentSubscriber = this.subscriberRepository.findById(id).orElseThrow(() ->
            new NoSuchElementException("Subscriber not found")
        );
        if (updateSubscriber.getSkills() != null) {
            List<Skill> validList = this.skillService.handleValidSkills(updateSubscriber.getSkills());
            updateSubscriber.setSkills(validList);
        }
        BeanUtils.copyProperties(updateSubscriber, currentSubscriber,"id","name","email");
        return this.subscriberRepository.save(currentSubscriber);
    }

    public void handleDeleteSubscriber(Long id) {
        Subscriber currentSubscriber = this.subscriberRepository.findById(id).orElseThrow(() ->
            new NoSuchElementException("Subscriber not found")
        );
        this.subscriberRepository.delete(currentSubscriber);
    }

    public List<Subscriber> handleValidListSubscriber(List<Subscriber> subscriberList) {
        List<Subscriber> validList = subscriberList.stream()
        .map(subscriber -> this.subscriberRepository.findById(subscriber.getId()))
        .filter(Optional::isPresent)
        .map(Optional::get)
        .toList();

        return validList;
    }
}
