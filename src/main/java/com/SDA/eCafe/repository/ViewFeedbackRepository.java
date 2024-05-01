package com.SDA.eCafe.repository;

import com.SDA.eCafe.model.Feedback;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ViewFeedbackRepository extends JpaRepository<Feedback, Long> 
{
    List<Feedback> findAll();
}
