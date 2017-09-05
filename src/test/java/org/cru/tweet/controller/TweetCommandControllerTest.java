package org.cru.tweet.controller;

import com.jayway.jsonpath.JsonPath;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.cru.tweet.controller.internal.AbstractControllerTest;
import org.cru.tweet.domain.jpa.tweet.Tweet;
import org.cru.tweet.domain.jpa.user.User;
import org.cru.tweet.domain.util.TestTweet;
import org.cru.tweet.domain.util.TestUser;
import org.cru.tweet.service.ldap.LdapService;
import org.cru.tweet.service.tweet.TweetService;
import org.cru.tweet.view.create.tweet.TweetCreateView;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.fail;
import static org.cru.tweet.controller.constant.RequestMappingConstant.API_BASE_URL;
import static org.cru.tweet.controller.constant.RequestMappingConstant.TWEET_BASE_URL;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@WebAppConfiguration
public class TweetCommandControllerTest  extends AbstractControllerTest {
    private MockMvc mockMvc;

    @InjectMocks
    private TweetCommandController controller;

    @Mock
    LdapService ldapService;

    @Mock
    TweetService tweetService;

    User randomUser;

    List<User> randomToUserList;

    Page<Tweet> randomTweetList;

    TweetCreateView tweetCreateView;

    Tweet tweet;

    MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();

    public MapperFacade mapper =  mapperFactory.getMapperFacade();

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        tweetCreateView = new TweetCreateView();
        tweetCreateView.setTweet(RandomStringUtils.randomAlphanumeric(50));

        randomUser = TestUser.createRandom();

        randomToUserList = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            randomToUserList.add(TestUser.createRandom());
        }

        mockMvc = standaloneSetup(controller).build();

        tweet = TestTweet.createRandom(randomUser, randomToUserList, tweetCreateView.getTweet());
    }

    @Test
    public void testCreateTweets_simple() throws Exception {

        when(ldapService.getLoggedInUser(null)).thenReturn(randomUser);
        when(tweetService.createTweet(eq(randomUser), anyObject())).thenReturn(tweet);

        mockMvc.perform(post(API_BASE_URL + TWEET_BASE_URL)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(String.format("{\n" +
                                    "    \"tweet\": \"%s\"\n" +
                                    "}\n", tweetCreateView.getTweet())))
                .andExpect(status().isCreated())
                .andExpect(headerContains("location", API_BASE_URL + TWEET_BASE_URL + "/" + tweet.getGuid()));

    }

    @Test
    public void testCreateTweets_emptyTweetShouldReturnBadRequest() throws Exception {

        when(ldapService.getLoggedInUser(null)).thenReturn(randomUser);
        when(tweetService.createTweet(eq(randomUser), anyObject())).thenReturn(tweet);

        mockMvc.perform(post(API_BASE_URL + TWEET_BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(String.format("{\n" +
                        "    \"tweet\": \"%s\"\n" +
                        "}\n", "")))
                .andExpect(status().isBadRequest())
                .andExpect(bodyContains("tweet is empty"));

    }

    @Test
    public void testCreateTweets_nonExistUserShouldReturnBadRequest() throws Exception {

        when(ldapService.getLoggedInUser(null)).thenReturn(null);
        when(tweetService.createTweet(eq(randomUser), anyObject())).thenReturn(tweet);

        mockMvc.perform(post(API_BASE_URL + TWEET_BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(String.format("{\n" +
                        "    \"tweet\": \"%s\"\n" +
                        "}\n", tweetCreateView.getTweet())))
                .andExpect(status().isBadRequest())
                .andExpect(bodyContains("user not found"));

    }


    @Test
    public void testCreateTweets_NullTweetShouldReturnBadRequest() throws Exception {

        when(ldapService.getLoggedInUser(null)).thenReturn(randomUser);
        when(tweetService.createTweet(eq(randomUser), anyObject())).thenReturn(null);

        mockMvc.perform(post(API_BASE_URL + TWEET_BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(String.format("{\n" +
                        "    \"tweet\": \"%s\"\n" +
                        "}\n", tweetCreateView.getTweet())))
                .andExpect(status().isBadRequest())
                .andExpect(bodyContains("tweet is not created"));

    }
}
