package com.example.projectY.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.example.projectY.entity.Subscriber;

public interface SubscriberService {
    public Subscriber handleCreateSubsriber(Subscriber newsSubscriber);
    
    public Page<Subscriber> handleGetAllSubscribers(Pageable subscriberPageable, Specification<Subscriber> subscriberSpecification);

    public Subscriber handleGetSubscriberById(Long id);

    public Subscriber handleUpdateSubscriber(Long id,Subscriber updateSubscriber);

    public void handleDeleteSubscriber(Long id);

    public List<Subscriber> handleValidListSubscriber(List<Subscriber> subscriberList);
}
