package org.cru.tweet.repository;

import org.cru.tweet.domain.jpa.user.User;
import org.cru.tweet.repository.internal.JpaGuidRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaGuidRepository<User, Long> {
}
