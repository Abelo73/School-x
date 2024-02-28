package com.act.school_xx.repository;

import com.act.school_xx.models.Semester;
import com.act.school_xx.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SemesterRepository extends JpaRepository<Semester, Long> {

}
