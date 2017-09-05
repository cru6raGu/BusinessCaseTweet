package org.cru.tweet.repository.internal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.util.List;

@NoRepositoryBean
public interface JpaGuidRepository<T, PK extends Serializable> extends JpaRepository<T, PK> {
    T findByGuid(String guid);

    List<T> removeByGuid(String guid);

    Long deleteByGuid(String guid);
}

