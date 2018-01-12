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

import org.springframework.http.*;

import com.consol.citrus.dsl.runner.*;
import com.consol.citrus.http.client.*;
import com.consol.citrus.http.message.HttpMessage;
import com.consol.citrus.message.*;

public class AuthenticationBehavior extends AbstractTestBehavior {

    private final HttpClient authenticationServiceClient;

    private final String username;

    private final String password;

    private final String clientId;

    private final String clientSecret;

    public AuthenticationBehavior(final HttpClient authenticationServiceClient,
            final String username, final String password, final String clientId,
            final String clientSecret) {
        this.authenticationServiceClient = authenticationServiceClient;
        this.username = username;
        this.password = password;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
    }

    public void apply() {
        variable("accessToken", "");

        send(builder -> builder.endpoint(authenticationServiceClient) //
                .message(new HttpMessage(String.format(
                        "client_id=%s&client_secret=%s&grant_type=password&username=%s&password=%s&response_type=token",
                        clientId, clientSecret, username, password)) //
                                .path("/uaa/oauth/token/") //
                                .method(HttpMethod.POST) //
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE) //
                                .accept(MediaType.APPLICATION_JSON_VALUE)));

        receive(builder -> builder.endpoint(authenticationServiceClient) //
                .message(new HttpMessage() //
                        .status(HttpStatus.OK)) //
                .messageType(MessageType.JSON) //
                .extractFromPayload("$.access_token", "${accessToken}"));
    }
}
