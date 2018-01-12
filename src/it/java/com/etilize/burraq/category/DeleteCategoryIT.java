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

import static com.etilize.burraq.category.config.Role.*;

import org.junit.*;
import org.springframework.http.*;

import com.consol.citrus.annotations.*;
import com.consol.citrus.context.*;
import com.etilize.burraq.category.config.*;
import com.etilize.burraq.category.test.base.*;

/**
 * This class contains test cases related to delete category functionality
 *
 * @author Nimra Inam
 * @see AbstractIT
 * @since 1.0.0
 */
public class DeleteCategoryIT extends AbstractIT {

    @Test
    @CitrusTest
    public void shouldDeleteCategory(@CitrusResource TestContext context)
            throws Exception {
        author("Nimra Inam");
        description("A category should be deleted");

        final User user = props.getUserByRole(DELETE);
        variable(USER_NAME_LABEL, user.getUsername());
        applyBehavior(new AuthenticationBehavior(authenticationServiceClient, //
                user.getUsername(), //
                user.getPassword(), //
                props.getClientId(), //
                props.getClientSecret()));
        String accessToken = context.getVariable("${accessToken}");

        variable(CATEGORY_ID, "59fb01890fcdf847c8eb4cec");

        deleteRequest(CATEGORY_URL, "${" + CATEGORY_ID + "}", accessToken);

        verifyResponse(HttpStatus.NO_CONTENT);

        getRequest("/categories/${" + CATEGORY_ID + "}", accessToken);

        verifyResponse(HttpStatus.NOT_FOUND);
    }

    @Test
    @CitrusTest
    public void shouldReturnNotFoundOnDeletingCategoryIfCategoryIdDoesNotExist(
            @CitrusResource TestContext context) throws Exception {
        author("Nimra Inam");
        description("A category should not be deleted if it does not exist");

        final User user = props.getUserByRole(DELETE);
        variable(USER_NAME_LABEL, user.getUsername());
        applyBehavior(new AuthenticationBehavior(authenticationServiceClient, //
                user.getUsername(), //
                user.getPassword(), //
                props.getClientId(), //
                props.getClientSecret()));
        String accessToken = context.getVariable("${accessToken}");

        variable(CATEGORY_ID, "59afe1125846b8762efc30e2");

        deleteRequest(CATEGORY_URL, "${" + CATEGORY_ID + "}", accessToken);

        verifyResponse(HttpStatus.NOT_FOUND);
    }
}
