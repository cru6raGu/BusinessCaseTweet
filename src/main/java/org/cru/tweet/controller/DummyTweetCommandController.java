package org.cru.tweet.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.cru.tweet.domain.jpa.user.User;
import org.cru.tweet.service.ldap.LdapService;
import org.cru.tweet.service.tweet.TweetService;
import org.cru.tweet.view.create.tweet.TweetCreateView;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.net.URI;
import java.util.Objects;

import static org.cru.tweet.controller.constant.RequestMappingConstant.*;

@Slf4j
@RestController
@RequestMapping(API_BASE_URL + MOCK_TWEET_BASE_URL)
public class DummyTweetCommandController extends AbstractBaseController {
    @Resource
    TweetService tweetService;

    @Resource
    LdapService ldapService;

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity createDummyTweets(@RequestParam(required = false) String userGuidBackdoor,
                                                @RequestParam(required = false) Integer numberOfTweets) {
        log.debug(String.format("calling createDummyTweets %s. Creating %s tweets.", userGuidBackdoor, numberOfTweets));
        //TODO make it Asynch
        StopWatch watch = new StopWatch();
        watch.start("0");

        if (numberOfTweets == null) {
            numberOfTweets = 1000;
        }

        User user = ldapService.getLoggedInUser(userGuidBackdoor);

        if (Objects.isNull(user)) {
            //TODO return more meaningful messages
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("user not found");
        }

        TweetCreateView tweetCreateView = new TweetCreateView();
        int counter = 0;

        for (int i = 0; i <numberOfTweets; i++) {
            tweetCreateView.setTweet(RandomStringUtils.randomAlphabetic(50));

            tweetService.createTweet(user, tweetCreateView);

            if (i != 0 && i % 1000 == 0) {
                watch.stop();
                log.info(String.format("%s tweets have been created. time lapse %s seconds.", i, watch.getLastTaskTimeMillis() / 1000));
                watch.start(Integer.toString(i/1000));
            }

            counter++;
        }

        URI link = uriGenerator(API_BASE_URL + TWEET_BASE_URL + "/" + counter);

        tweetCreateView = null;
        watch.stop();
        log.info(String.format("Total execution time %s seconds to create %s tweets. %s tweets created.", watch.getTotalTimeSeconds(), numberOfTweets, counter));
        return ResponseEntity.created(link).build();
    }
}