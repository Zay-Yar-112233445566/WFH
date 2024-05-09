package com.WFHS.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.WFHS.entity.Operation;
import com.WFHS.entity.User;

@Repository
public interface OperationRepository extends JpaRepository<Operation, Integer> {
	Operation findById(int id);
	Operation findByUser(User user);
}
