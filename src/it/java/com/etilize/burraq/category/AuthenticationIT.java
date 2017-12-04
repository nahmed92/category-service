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

package com.etilize.burraq.category;

import org.junit.*;
import org.springframework.http.*;

import com.consol.citrus.context.TestContext;
import com.consol.citrus.http.message.HttpMessage;
import com.consol.citrus.annotations.*;
import com.consol.citrus.message.*;
import com.etilize.burraq.category.test.base.*;

/**
 * This class contains test cases related Add Category functionality
 *
 * @author Nimra Inam
 * @see AbstractIT
 * @since 1.0.0
 */
public class AuthenticationIT extends AbstractIT {

    @Test
    @CitrusTest
    public void shouldAllowAuthenticationUserToAddCategory(
            @CitrusResource TestContext context) throws Exception {

        author("Nimra Inam");
        description("A user should be authenticated");

        /*
         * Acquires access token from authentication service using behavior instead of
         * before suits. Following issue is the reason for opting this method:
         * https://github.com/christophd/citrus/issues/306
         */

        variable(USER_NAME_LABEL, props.getUsername());
        applyBehavior(new AuthenticationBehavior(authenticationServiceClient, props.getUsername(), props.getPassword()));
        String accessToken = context.getVariable("${accessToken}");

        postRequest(CATEGORY_URL, //
                readFile(
                        "/datasets/categories/add/authenticate_category_with_active_status_request.json"), //
                accessToken);

        extractHeader(HttpStatus.CREATED, HttpHeaders.LOCATION);
        String categoryLocation = parseAndSetVariable(CATEGORY_URL,
                context.getVariable("${locationHeaderValue}"));
        verifyResponse(HttpStatus.OK, //
                readFile(
                        "/datasets/categories/add/authenticate_category_with_active_status_response.json"), //
                categoryLocation, //
                accessToken);
    }

    @Test
    @CitrusTest
    public void shouldNotAllowUnAuthorizedUserToAddCategory(
            @CitrusResource TestContext context) throws Exception {

        author("Nimra Inam");
        description("An unauthenticated user should not be allowed to add a category");

        variable(USER_NAME_LABEL, props.getUsername());
        applyBehavior(new AuthenticationBehavior(authenticationServiceClient, props.getUsername(), props.getPassword()));

        postRequest(CATEGORY_URL, //
                readFile(
                        "/datasets/categories/add/category_with_active_status_request.json"));

        String payload = readFile("/datasets/categories/add/authentication_error.json");
        receive(builder -> builder.endpoint(serviceClient) //
                .message(new HttpMessage() //
                        .status(HttpStatus.UNAUTHORIZED)) //
                .messageType(MessageType.JSON) //
                .payload(payload));

    }
}
