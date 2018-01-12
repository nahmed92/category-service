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

package com.etilize.burraq.category.config;

import java.util.*;

import javax.validation.constraints.*;

import org.springframework.boot.context.properties.*;
import org.springframework.stereotype.*;

/**
 * Integration Test properties
 *
 * @author Faisal Feroz
 *
 */
@Component
@ConfigurationProperties("integration.test")
public class IntegrationTestProperties {

    /**
     * Url at which the deployed service is accessible
     */
    private String serviceUrl = "http://localhost:8080";

    /**
     * Url at which the deployed authentication service is accessible
     */
    private String authenticationServiceUrl = "http://localhost:8080";

    /**
     * clientId to authenticate
     */
    @NotNull
    private String clientId;

    /**
     * clientSecret to authenticate
     */
    @NotNull
    private String clientSecret;

    /**
     * users map is used to maintain user information against it's role
     */
    @NotNull
    private Map<String, User> users = new HashMap<>();

    public String getServiceUrl() {
        return serviceUrl;
    }

    public void setServiceUrl(final String serviceUrl) {
        this.serviceUrl = serviceUrl;
    }

    public String getAuthenticationServiceUrl() {
        return authenticationServiceUrl;
    }

    public void setAuthenticationServiceUrl(final String authenticationServiceUrl) {
        this.authenticationServiceUrl = authenticationServiceUrl;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(final String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(final String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public Map<String, User> getUsers() {
        return users;
    }

    public void setUsers(final Map<String, User> users) {
        this.users = users;
    }

    public User getUserByRole(final String role) {
        return this.users.get(role);
    }
}
