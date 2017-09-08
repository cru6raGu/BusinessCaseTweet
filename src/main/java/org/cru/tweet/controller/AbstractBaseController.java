package org.cru.tweet.controller;

import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.cru.tweet.view.internal.RetrievalView;
import org.springframework.data.domain.Pageable;

import java.net.URI;
import java.net.URISyntaxException;

@Slf4j
public abstract class AbstractBaseController {
    MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();

    public MapperFacade mapper =  mapperFactory.getMapperFacade();;

    protected URI uriGenerator(String uri) {
        try {
            return new URI(uri);
        } catch (URISyntaxException e) {
            log.error(String.format("error when creating URI %s", uri), e);
        }

        return null;
    }
}
