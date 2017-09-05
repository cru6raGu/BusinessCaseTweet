package org.cru.tweet.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.cru.tweet.domain.jpa.tweet.Tweet;
import org.cru.tweet.domain.jpa.user.User;
import org.cru.tweet.service.ldap.LdapService;
import org.cru.tweet.service.tweet.TweetService;
import org.cru.tweet.view.create.tweet.TweetCreateView;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.net.URI;
import java.util.Objects;

import static org.cru.tweet.controller.constant.RequestMappingConstant.API_BASE_URL;
import static org.cru.tweet.controller.constant.RequestMappingConstant.TWEET_BASE_URL;

@Slf4j
@RestController
@RequestMapping(API_BASE_URL + TWEET_BASE_URL)
public class TweetCommandController extends AbstractBaseController {
    @Resource
    TweetService tweetService;

    @Resource
    LdapService ldapService;

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity createTweet(@RequestParam(required = false) String userGuidBackdoor,
                                        @RequestBody(required = true) TweetCreateView tweetCreateView) {
        log.debug(String.format("calling createTweet %s %s", userGuidBackdoor, tweetCreateView));

        if (tweetCreateView == null || StringUtils.isBlank(tweetCreateView.getTweet())) {
            //TODO return more meaningful messages
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("tweet is empty");
        }

        User user = ldapService.getLoggedInUser(userGuidBackdoor);

        if (Objects.isNull(user)) {
            //TODO return more meaningful messages
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("user not found");
        }

        Tweet tweet = tweetService.createTweet(user, tweetCreateView);

        if (Objects.isNull(tweet) || Objects.isNull(tweet.getId())) {
            //TODO return more meaningful messages
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("tweet is not created");
        }

        URI link = uriGenerator(API_BASE_URL + TWEET_BASE_URL + "/" + tweet.getGuid());
        return ResponseEntity.created(link).build();
    }
}