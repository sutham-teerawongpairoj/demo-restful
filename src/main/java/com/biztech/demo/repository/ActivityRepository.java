package com.biztech.demo.repository;

import com.biztech.demo.model.ActivityModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivityRepository extends JpaRepository<ActivityModel, Integer> {
}
