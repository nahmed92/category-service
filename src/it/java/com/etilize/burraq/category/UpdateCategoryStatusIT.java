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

import com.consol.citrus.annotations.*;
import com.consol.citrus.context.*;
import com.consol.citrus.http.message.HttpMessage;
import com.consol.citrus.message.*;
import com.etilize.burraq.category.test.base.*;

/**
 * This class contains test cases related to Update Category Status functionality
 *
 * @author Nimra Inam
 * @see AbstractIT
 * @since 1.0.0
 */
public class UpdateCategoryStatusIT extends AbstractIT {

    public static final String EXISTING_CATEGORY_ID_TO_UPDATE = "59fac5f70fcdf847c8eb4ca5";

    public static final String ACTIVE = "ACTIVE";

    public static final String INACTIVE = "INACTIVE";

    public static final String PENDING = "PENDING";

    @Test
    @CitrusTest
    public void shouldUpdateStatusToActiveOfANewCategoryById(
            @CitrusResource TestContext context) throws Exception {
        author("Nimra Inam");
        description("Status of a new category should be updated");
        variable("status", INACTIVE);

        // Add a new category to update it's status using update_status end point and keep
        // the status "ACTIVE"
        postRequest(CATEGORY_URL, //
                readFile(
                        "/datasets/categories/update_status/category_to_update_status_request.json"));

        extractHeader(HttpStatus.CREATED, HttpHeaders.LOCATION);
        String categoryLocation = parseAndSetVariable(CATEGORY_URL,
                context.getVariable("${locationHeaderValue}"));
        String categoryId = categoryLocation.substring(
                categoryLocation.lastIndexOf('/') + 1);
        variable("categoryId", categoryId);

        verifyResponse(HttpStatus.OK, //
                readFile(
                        "/datasets/categories/update_status/category_to_update_status_response.json"), //
                categoryLocation);

        // Update status of a newly added category using update_status end point to
        // "INACTIVE"
        putRequest(CATEGORY_URL, //
                categoryId + "/update_status/", //
                "{ \"status\":\"${status}\"}");

        verifyResponse(HttpStatus.NO_CONTENT);

        // Now make GET call to the newly added category and verify if it's status is
        // updated to "INACTIVE"
        verifyResponse(HttpStatus.OK, //
                readFile(
                        "/datasets/categories/update_status/update_new_category_status_to_inactive_response.json"), //
                categoryLocation);
    }

    @Test
    @CitrusTest
    public void shouldUpdateStatusOfCategoryToInactive(
            @CitrusResource TestContext context) throws Exception {
        author("Nimra Inam");
        description("Status of category should be updated to inactive");

        variable("name", "Paper Racks");
        variable("description", "Paper Racks description");
        variable("status", INACTIVE);
        variable("industryId", "59762d7caddb13b4a8440a38");
        variable("categoryId", "5b28dc287ef998165c5b9d48");

        // Update status of an existing category using update_status end point to
        // "INACTIVE"
        putRequest(CATEGORY_URL, //
                "${" + CATEGORY_ID + "}" + "/update_status/", //
                "{ \"status\":\"${status}\"}");

        verifyResponse(HttpStatus.NO_CONTENT);

        // Now make GET call to the existing category and verify if it's status is updated
        // to "INACTIVE"
        verifyResponse(HttpStatus.OK, //
                readFile(
                        "/datasets/categories/update_status/find_updated_category_by_id_response.json"), //
                CATEGORY_URL + "${" + CATEGORY_ID + "}");
    }

    @Test
    @CitrusTest
    public void shouldUpdateStatusOfCategoryToPending(@CitrusResource TestContext context)
            throws Exception {
        author("Nimra Inam");
        description("Status of category should be updated to pending");

        variable("name", "Remote Controls");
        variable("description", "Remote Controls description");
        variable("status", PENDING);
        variable("industryId", "59762d7caddb13b4a8440a38");
        variable("categoryId", "5b28e2337ef998165c5b9d4a");

        // Update status of an existing category using update_status end point to
        // "PENDING"
        putRequest(CATEGORY_URL, //
                "${" + CATEGORY_ID + "}" + "/update_status/", //
                "{ \"status\":\"${status}\"}");

        verifyResponse(HttpStatus.NO_CONTENT);

        // Now make GET call to the existing category and verify if it's status is updated
        // to "PENDING"
        verifyResponse(HttpStatus.OK, //
                readFile(
                        "/datasets/categories/update_status/find_updated_category_by_id_response.json"), //
                CATEGORY_URL + "${" + CATEGORY_ID + "}");
    }

    @Test
    @CitrusTest
    public void shouldUpdateStatusOfCategoryToActive(@CitrusResource TestContext context)
            throws Exception {
        author("Nimra Inam");
        description("Status of category should be updated to active");

        variable("name", "REV Media");
        variable("description", "REV Media description");
        variable("status", ACTIVE);
        variable("industryId", "59762d7caddb13b4a8440a38");
        variable("categoryId", "5b28e53d7ef998165c5b9d4b");

        // Update status of an existing category using update_status end point to "ACTIVE"
        putRequest(CATEGORY_URL, //
                "${" + CATEGORY_ID + "}" + "/update_status/", //
                "{ \"status\":\"${status}\"}");

        verifyResponse(HttpStatus.NO_CONTENT);

        // Now make GET call to the existing category and verify if it's status is updated
        // to "ACTIVE"
        verifyResponse(HttpStatus.OK, //
                readFile(
                        "/datasets/categories/update_status/find_updated_category_by_id_response.json"), //
                CATEGORY_URL + "${" + CATEGORY_ID + "}");
    }

    @Test
    @CitrusTest
    public void shouldReturnBadRequestWhenStatusIsMissing(
            @CitrusResource TestContext context) throws Exception {
        author("Nimra Inam");
        description(
                "Category Status shouldn't update when status field is missing in request payload");

        variable(CATEGORY_ID, EXISTING_CATEGORY_ID_TO_UPDATE);
        variable("error", "status is required");

        putRequest(CATEGORY_URL, //
                "${" + CATEGORY_ID + "}" + "/update_status/", //
                "{}");

        receive(builder -> builder.endpoint(serviceClient) //
                .message(new HttpMessage() //
                        .status(HttpStatus.BAD_REQUEST)) //
                .messageType(MessageType.JSON).validate("$.errors[*].message",
                        "${error}"));
    }

    @Test
    @CitrusTest
    public void shouldReturnBadRequestWhenStatusIsInvalid(
            @CitrusResource TestContext context) throws Exception {
        author("Nimra Inam");
        description("Invalid category status should not get updated");

        variable(CATEGORY_ID, EXISTING_CATEGORY_ID_TO_UPDATE);
        variable("error",
                "value not one of declared Enum instance names: [INACTIVE, ACTIVE, PENDING]");

        putRequest(CATEGORY_URL, //
                "${" + CATEGORY_ID + "}" + "/update_status/", //
                "{ \"status\":\"INVALID\"}");

        receive(builder -> builder.endpoint(serviceClient) //
                .message(new HttpMessage() //
                        .status(HttpStatus.BAD_REQUEST)) //
                .messageType(MessageType.JSON).validate("$.message",
                        "@contains(${error})@"));
    }

    @Test
    @CitrusTest
    public void shouldReturnNotFoundWhenInvalidCategoryIDIsProvided(
            @CitrusResource TestContext context) throws Exception {
        author("Nimra Inam");
        description("Category status should not get updated with an invalid category id");

        variable(CATEGORY_ID, "59fac5f70fcdf847c8eb4ca9");
        variable("error", "category not found.");

        putRequest(CATEGORY_URL, //
                "${" + CATEGORY_ID + "}" + "/update_status/", //
                "{ \"status\":\"ACTIVE\"}");

        receive(builder -> builder.endpoint(serviceClient) //
                .message(new HttpMessage() //
                        .status(HttpStatus.NOT_FOUND)) //
                .messageType(MessageType.JSON).validate("$.message", "${error}"));
    }

    @Test
    @CitrusTest
    public void shouldReturnBadRequestWhenCategoryIDIsNotProvided(
            @CitrusResource TestContext context) throws Exception {
        author("Nimra Inam");
        description(
                "Category status should not get updated without category id in the URL");

        variable("error",
                "invalid hexadecimal representation of an ObjectId: [update_status]");

        putRequest(CATEGORY_URL, //
                "/update_status/", //
                "{ \"status\":\"ACTIVE\"}");

        receive(builder -> builder.endpoint(serviceClient) //
                .message(new HttpMessage() //
                        .status(HttpStatus.BAD_REQUEST)) //
                .messageType(MessageType.JSON).validate("$.message",
                        "@contains(${error})@"));
    }
}
