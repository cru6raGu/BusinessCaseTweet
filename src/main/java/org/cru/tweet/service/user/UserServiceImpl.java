package org.cru.tweet.service.user;

import lombok.extern.slf4j.Slf4j;
import org.cru.tweet.domain.jpa.user.User;
import org.cru.tweet.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Slf4j
@Service("userService")
@Transactional(propagation = Propagation.REQUIRED)
public class UserServiceImpl implements UserService {
    @Resource
    UserRepository userRepository;

    @Override
    public User getLoggedInUser(String userGuid) {
        return userRepository.findByGuid(userGuid);
    }
}
