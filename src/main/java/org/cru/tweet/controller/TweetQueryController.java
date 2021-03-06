package org.cru.tweet.controller;

import lombok.extern.slf4j.Slf4j;
import org.cru.tweet.domain.jpa.tweet.Tweet;
import org.cru.tweet.domain.jpa.user.User;
import org.cru.tweet.service.ldap.LdapService;
import org.cru.tweet.service.tweet.TweetService;
import org.cru.tweet.view.TweetView;
import org.cru.tweet.view.TweetsRetrievalView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import java.util.List;
import java.util.Objects;

import static org.cru.tweet.controller.constant.RequestMappingConstant.API_BASE_URL;
import static org.cru.tweet.controller.constant.RequestMappingConstant.TWEET_BASE_URL;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Slf4j
@RestController
@RequestMapping(API_BASE_URL + TWEET_BASE_URL)
public class TweetQueryController extends AbstractBaseController {
    @Resource
    LdapService ldapService;

    @Resource
    TweetService tweetService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<TweetsRetrievalView> getTweets(@RequestParam(value = "userGuidBackdoor", required = false) String userGuidBackdoor,
                                                         @PageableDefault(size = 10) Pageable pageable) {
        log.debug(String.format("calling getTweets %s %s", userGuidBackdoor, pageable));

        User user = ldapService.getLoggedInUser(userGuidBackdoor);
        Page<Tweet> tweets = tweetService.findTweetsByUser(user, pageable);
        List<TweetView> views = mapper.mapAsList(tweets, TweetView.class);

        for (TweetView tweetView : views) {
            tweetView.add(linkTo(methodOn(TweetQueryController.class).getTweet(user.getGuid(), tweetView.getGuid())).withRel("self"));
        }

        // add the tweets to the final response view with tweet list size
        //TODO make setting page info as a reusable component
        TweetsRetrievalView tweetsRetrievalView =  new TweetsRetrievalView();
        tweetsRetrievalView.setTweets(views);
        tweetsRetrievalView.setTotalPage(tweets.getTotalPages());
        tweetsRetrievalView.setPageSize(pageable.getPageSize());
        tweetsRetrievalView.setTotalSize(tweets.getTotalElements());
        tweetsRetrievalView.setPage(pageable.getPageNumber());
        tweetsRetrievalView.setSize(tweets.getContent().size());

        return ResponseEntity.ok(tweetsRetrievalView);
    }

    @RequestMapping(value = "/{tweetGuid}", method = RequestMethod.GET)
    public ResponseEntity<TweetView> getTweet(@RequestParam(value = "userGuidBackdoor", required = false) String userGuidBackdoor,
                                                     @PathVariable String tweetGuid) {
        log.debug(String.format("calling getTweet %s %s", userGuidBackdoor, tweetGuid));

        User user = ldapService.getLoggedInUser(userGuidBackdoor);
        Tweet tweet = tweetService.findTweetsByUserAndTweetGuid(user, tweetGuid);
        TweetView view = mapper.map(tweet, TweetView.class);

        if (Objects.nonNull(view)) {
            view.add(linkTo(methodOn(TweetQueryController.class).getTweet(user.getGuid(), tweetGuid)).withRel("self"));
        }

        return ResponseEntity.ok(view);
    }
}
