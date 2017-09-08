package org.cru.tweet.controller;

import com.jayway.jsonpath.JsonPath;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.apache.commons.lang3.StringUtils;
import org.cru.tweet.controller.internal.AbstractControllerTest;
import org.cru.tweet.domain.jpa.tweet.Tweet;
import org.cru.tweet.domain.jpa.user.User;
import org.cru.tweet.domain.util.TestTweet;
import org.cru.tweet.domain.util.TestUser;
import org.cru.tweet.service.ldap.LdapService;
import org.cru.tweet.service.tweet.TweetService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
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
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@WebAppConfiguration
public class TweetQueryControllerTest extends AbstractControllerTest {
    private MockMvc mockMvc;

    @InjectMocks
    private TweetQueryController controller;

    @Mock
    LdapService ldapService;

    @Mock
    TweetService tweetService;

    User randomUser;

    List<User> randomToUserList;

    Page<Tweet> randomTweetList;

    MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();

    public MapperFacade mapper =  mapperFactory.getMapperFacade();

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        mockMvc = standaloneSetup(controller)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .build();

        randomToUserList = new ArrayList<>();
        randomUser = TestUser.createRandom();

        for (int i = 0; i < 15; i++) {
            randomToUserList.add(TestUser.createRandom());
        }

        randomTweetList = TestTweet.createRandom(randomUser, randomToUserList, 20);
    }

    @Test
    public void testGetTweets_simple() throws Exception {

        when(ldapService.getLoggedInUser(null)).thenReturn(randomUser);
        when(tweetService.findTweetsByUser(randomUser, new PageRequest(0, 10))).thenReturn(randomTweetList);

        mockMvc.perform(get(API_BASE_URL + TWEET_BASE_URL)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(contains("tweets[0].fromUser.email", randomUser.getEmail()))
                .andExpect(contains("tweets[0].fromUser.guid", randomUser.getGuid()))
                .andExpect(contains("tweets[0].toUsers[0].email", randomToUserList.get(0).getEmail()))
                .andExpect(contains("tweets[0].toUsers[0].guid", randomToUserList.get(0).getGuid()))
                .andExpect(contains("tweets[0].links[0].href", String.format("%s" + API_BASE_URL + TWEET_BASE_URL + "/%s?userGuidBackdoor=%s", "http://localhost", randomTweetList.iterator().next().getGuid(), randomUser.getGuid())))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
    }


    @Test
    public void testGetTweet_simple() throws Exception {

        when(ldapService.getLoggedInUser(null)).thenReturn(randomUser);
        when(tweetService.findTweetsByUserAndTweetGuid(randomUser, randomTweetList.iterator().next().getGuid())).thenReturn(randomTweetList.iterator().next());

        mockMvc.perform(get(API_BASE_URL + TWEET_BASE_URL + "/" + randomTweetList.iterator().next().getGuid())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(contains("fromUser.email", randomUser.getEmail()))
                .andExpect(contains("fromUser.guid", randomUser.getGuid()))
                .andExpect(contains("toUsers[0].email", randomToUserList.get(0).getEmail()))
                .andExpect(contains("toUsers[0].guid", randomToUserList.get(0).getGuid()))
                .andExpect(contains("links[0].href", String.format("%s" + API_BASE_URL + TWEET_BASE_URL + "/%s?userGuidBackdoor=%s", "http://localhost", randomTweetList.iterator().next().getGuid(), randomUser.getGuid())))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));

    }
}
