package com.SDA.eCafe.repository;

import com.SDA.eCafe.model.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedbackRepository extends JpaRepository<Feedback, Integer> {
}

