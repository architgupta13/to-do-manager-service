package org.archit.todomanagerservice.service;

import org.archit.todomanagerservice.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    void saveUser(User user);

}
