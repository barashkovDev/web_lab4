package com.barashkov.web_lab4.repo;

import com.barashkov.web_lab4.models.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
}
