package org.cru.tweet.controller;

import lombok.extern.slf4j.Slf4j;
import org.cru.tweet.domain.jpa.tweet.Tweet;
import org.cru.tweet.domain.jpa.user.User;
import org.cru.tweet.service.ldap.LdapService;
import org.cru.tweet.service.tweet.TweetService;
import org.cru.tweet.view.TweetView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import java.util.List;

import static org.cru.tweet.controller.constant.RequestMappingConstant.API_BASE_URL;
import static org.cru.tweet.controller.constant.RequestMappingConstant.TWEET_BASE_URL;

@Slf4j
@RestController
@RequestMapping(API_BASE_URL + TWEET_BASE_URL)
public class TweetQueryController extends AbstractBaseController {
    @Resource
    LdapService ldapService;

    @Resource
    TweetService tweetService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<List<TweetView>> getTweet(@RequestParam(value = "userGuidBackdoor", required = false) String userGuidBackdoor,
                                                    @PageableDefault(size = 10) Pageable pageable) {
        log.debug(String.format("calling getTweet %s %s", userGuidBackdoor, pageable));

        User user = ldapService.getLoggedInUser(userGuidBackdoor);
        Page<Tweet> tweets = tweetService.findTweetsByUser(user, pageable);
        List<TweetView> views = mapper.mapAsList(tweets, TweetView.class);

        return ResponseEntity.ok(views);
    }
}
