/*
 * #region
 * category-service
 * %%
 * Copyright (C) 2017 Etilize
 * %%
 * NOTICE: All information contained herein is, and remains the property of ETILIZE.
 * The intellectual and technical concepts contained herein are proprietary to
 * ETILIZE and may be covered by U.S. and Foreign Patents, patents in process, and
 * are protected by trade secret or copyright law. Dissemination of this information
 * or reproduction of this material is strictly forbidden unless prior written
 * permission is obtained from ETILIZE. Access to the source code contained herein
 * is hereby forbidden to anyone except current ETILIZE employees, managers or
 * contractors who have executed Confidentiality and Non-disclosure agreements
 * explicitly covering such access.
 *
 * The copyright notice above does not evidence any actual or intended publication
 * or disclosure of this source code, which includes information that is confidential
 * and/or proprietary, and is a trade secret, of ETILIZE. ANY REPRODUCTION, MODIFICATION,
 * DISTRIBUTION, PUBLIC PERFORMANCE, OR PUBLIC DISPLAY OF OR THROUGH USE OF THIS
 * SOURCE CODE WITHOUT THE EXPRESS WRITTEN CONSENT OF ETILIZE IS STRICTLY PROHIBITED,
 * AND IN VIOLATION OF APPLICABLE LAWS AND INTERNATIONAL TREATIES. THE RECEIPT
 * OR POSSESSION OF THIS SOURCE CODE AND/OR RELATED INFORMATION DOES NOT CONVEY OR
 * IMPLY ANY RIGHTS TO REPRODUCE, DISCLOSE OR DISTRIBUTE ITS CONTENTS, OR TO
 * MANUFACTURE, USE, OR SELL ANYTHING THAT IT MAY DESCRIBE, IN WHOLE OR IN PART.
 * #endregion
 */

package com.etilize.burraq.category.test.base;

import java.io.IOException;
import static java.nio.file.Files.*;
import static java.nio.file.Paths.*;

import com.consol.citrus.message.*;
import com.etilize.burraq.category.config.*;
import com.consol.citrus.http.message.HttpMessage;
import com.consol.citrus.dsl.junit.JUnit4CitrusTestRunner;
import com.consol.citrus.http.client.HttpClient;

import static org.springframework.http.MediaType.*;
import org.springframework.http.*;
import org.springframework.core.io.ResourceLoader;
import org.apache.commons.lang3.*;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * This class should be extended to write IT test cases. It provides methods to read file,
 * put, post, remove and verify responses.
 *
 * @author Nimra Inam
 * @since 1.0.0
 */
public abstract class AbstractIT extends JUnit4CitrusTestRunner {

    protected final static String CATEGORY_URL = "/categories/";

    protected final static String CATEGORY_ID = "categoryId";

    protected final static String LOCATION_HEADER_VALUE = "locationHeaderValue";

    protected static final String ATHENTICATION_URL = "/uaa/oauth/token/";

    protected final static String USER_NAME_LABEL = "username";

    @Autowired
    protected HttpClient serviceClient;

    @Autowired
    protected HttpClient authenticationServiceClient;

    @Autowired
    protected IntegrationTestProperties props;

    @Autowired
    private ResourceLoader resourceLoader;

    /**
     * Reads contents of the file.
     *
     * @param fileName the file name.
     * @return contents of the file.
     * @throws IOException if file not found.
     */
    protected String readFile(String fileName) throws IOException {
        return new String(readAllBytes(
                get(resourceLoader.getResource(appendClassPath(fileName)).getURI())));
    }

    private String appendClassPath(final String fileName) {
        return String.format("classpath:%s", fileName);
    }

    /**
     * It sends get request to service
     *
     * @param url Url to get request
     * @param accessToken Holds access token to authenticate operation
     */
    public void getRequest(final String url, final String accessToken) {
        send(builder -> builder.endpoint(serviceClient) //
                .message(new HttpMessage() //
                        .path(url) //
                        .method(HttpMethod.GET) //
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken) //
                        .contentType(APPLICATION_JSON_VALUE) //
                        .accept(APPLICATION_JSON_VALUE)));

    }

    /**
     * It sends post request to service
     *
     * @param url Url to use to send request
     * @param payload to post
     * @param accessToken Holds access token to authenticate operation
     */
    protected void postRequest(final String url, final String payload,
            final String accessToken) {
        final HttpMessage request = new HttpMessage(payload) //
                .path(url) //
                .method(HttpMethod.POST) //
                .contentType(APPLICATION_JSON_VALUE) //
                .accept(APPLICATION_JSON_VALUE);

        if (StringUtils.isNotEmpty(accessToken)) {
            request.header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);
        }
        send(builder -> builder.endpoint(serviceClient) //
                .message(request));
    }

    /**
     * It sends post request to service without access token
     *
     * @param url Url to use to send request
     * @param payload to post
     */
    protected void postRequest(final String url, final String payload) {
        postRequest(url, payload, null);
    }

    /**
     * It sends put request to service
     *
     * @param url Url to use to send request
     * @param categoryId to update the exact category
     * @param payload to send with put request
     * @param accessToken Holds access token to authenticate operation
     */
    protected void putRequest(final String url, final String categoryId,
            final String payload, final String accessToken) {
        send(builder -> builder.endpoint(serviceClient) //
                .message(new HttpMessage(payload) //
                        .path(url + categoryId) //
                        .method(HttpMethod.PUT) //
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken) //
                        .contentType(APPLICATION_JSON_VALUE) //
                        .accept(APPLICATION_JSON_VALUE)));
    }

    /**
     * It sends delete request to service
     *
     * @param url Url to use to send request
     * @param categoryId to delete the exact category
     * @param accessToken Holds access token to authenticate operation
     */
    protected void deleteRequest(final String url, final String categoryId,
            final String accessToken) {
        send(builder -> builder.endpoint(serviceClient) //
                .message(new HttpMessage() //
                        .path(url + categoryId) //
                        .method(HttpMethod.DELETE) //
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken) //
                        .contentType(APPLICATION_JSON_VALUE) //
                        .accept(APPLICATION_JSON_VALUE)));
    }

    /**
     * It extracts header location
     *
     * @param httpStatus
     * @param httpHeaderName
     */
    protected void extractHeader(final HttpStatus httpStatus,
            final String httpHeaderName) {
        receive(builder -> builder.endpoint(serviceClient) //
                .message(new HttpMessage() //
                        .status(httpStatus)) //
                .messageType(MessageType.JSON) //
                .extractFromHeader(httpHeaderName, "${locationHeaderValue}"));
    }

    /**
     * It verifies the response received from service from header location
     *
     * @param httpStatus HttpStatus status code
     * @param payload response data to verify
     * @param url hold resource url
     * @param accessToken Holds access token to authenticate operation
     */
    protected void verifyResponse(final HttpStatus httpStatus, final String payload,
            final String url, final String accessToken) {
        getRequest(url, accessToken);

        receive(builder -> builder.endpoint(serviceClient) //
                .message(new HttpMessage() //
                        .status(httpStatus)) //
                .messageType(MessageType.JSON).payload(payload));
    }

    /**
     * It verifies the http response
     *
     * @param httpStatus
     */
    protected void verifyResponse(final HttpStatus httpStatus) {
        // Verify Response
        receive(builder -> builder.endpoint(serviceClient) //
                .message(new HttpMessage() //
                        .status(httpStatus)) //
                .messageType(MessageType.JSON));
    }

    /**
     * It verifies the response status code and response error message received from
     * service.
     *
     * @param httpStatus HttpStatus status code
     * @param payload to verify in response
     * @param accessToken Holds access token to authenticate operation
     */
    protected void verifyResponse(final HttpStatus httpStatus, final String payload,
            final String accessToken) {
        // Verify Response
        receive(builder -> builder.endpoint(serviceClient) //
                .message(new HttpMessage() //
                        .status(httpStatus)) //
                .messageType(MessageType.JSON) //
                .payload(payload));
    }

    /**
     * It replaces resource location with resource id and set it to context variable.
     *
     * @param url this holds api's URL
     * @param headerValue this holds complete resource location to parse
     * @return headerLocation It returns the exact location, omitting the base URL. For
     *         example: "/units/5a1805530fcdf812bee4dd66"
     */
    protected String parseAndSetVariable(final String url, final String headerValue) {
        String headerLocation = headerValue.substring(headerValue.indexOf(url));
        return headerLocation;
    }
}
