package org.cru.tweet.controller.internal;

import com.jayway.jsonpath.JsonPath;
import org.apache.commons.lang3.StringUtils;
import org.springframework.test.web.servlet.ResultMatcher;

import static junit.framework.TestCase.fail;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class AbstractControllerTest {
    protected ResultMatcher headerContains(final String field, final String value) {
        return result -> {
            try {
                String content = (String)result.getResponse().getHeaderValue(field);
                if (StringUtils.isNotEmpty(content)) {
                    assertThat(content, is(equalTo(value)));
                } else {
                    fail(String.format("Field %s not found in response header", field));
                }
            } catch (Exception e) {
                fail(e.getMessage());
            }
        };
    }

    protected ResultMatcher contains(final String field, final String value) {
        return result -> {
            try {
                String content = result.getResponse().getContentAsString();
                if (StringUtils.isNotEmpty(content)) {
                    String fieldValue = JsonPath.read(content, "$." + field);
                    assertThat(fieldValue, is(equalTo(value)));
                } else {
                    fail(String.format("Field %s not found in response", field));
                }
            } catch (Exception e) {
                fail(e.getMessage());
            }
        };
    }

    protected ResultMatcher bodyContains(final String value) {
        return result -> {
            try {
                String content = result.getResponse().getContentAsString();
                if (StringUtils.isNotEmpty(content)) {
                    assertThat(content.indexOf(value), greaterThanOrEqualTo((0)));
                } else {
                    fail(String.format("Field %s not found in response body", value));
                }
            } catch (Exception e) {
                fail(e.getMessage());
            }
        };
    }

    protected  ResultMatcher listFirstItemContains(final String field, final String value) {
        return result -> {
            try {
                String content = result.getResponse().getContentAsString();
                if (StringUtils.isNotEmpty(content)) {
                    String fieldValue = JsonPath.read(content, "[0]." + field);
                    assertThat(fieldValue, is(equalTo(value)));
                } else {
                    fail(String.format("Field %s not found in response", field));
                }
            } catch (Exception e) {
                fail(e.getMessage());
            }
        };
    }
}
