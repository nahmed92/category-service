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
 * This class contains test cases related Add Category functionality
 *
 * @author Nimra Inam
 * @see AbstractIT
 * @since 1.0.0
 */
public class AddCategoryIT extends AbstractIT {

    @Test
    @CitrusTest
    public void shouldAddCategoryWithActiveStatus(@CitrusResource TestContext context)
            throws Exception {
        author("Nimra Inam");
        description("A category should be added with active status");

        variable(LOCATION_HEADER_VALUE, "");

        postRequest(CATEGORY_URL, //
                readFile(
                        "/datasets/categories/add/category_with_active_status_request.json"));

        extractHeader(HttpStatus.CREATED, HttpHeaders.LOCATION);

        String categoryLocation = parseAndSetVariable(CATEGORY_URL,
                context.getVariable("${locationHeaderValue}"));

        verifyResponse(HttpStatus.OK, //
                readFile(
                        "/datasets/categories/add/category_with_active_status_response.json"), //
                categoryLocation);
    }

    @Test
    @CitrusTest
    public void shouldAddCategoryWithPendingStatus(@CitrusResource TestContext context)
            throws Exception {
        author("Nimra Inam");
        description("A category should be added with pending status");

        variable(LOCATION_HEADER_VALUE, "");

        postRequest(CATEGORY_URL, //
                readFile(
                        "/datasets/categories/add/category_with_pending_status_request.json"));

        extractHeader(HttpStatus.CREATED, HttpHeaders.LOCATION);
        String categoryLocation = parseAndSetVariable(CATEGORY_URL,
                context.getVariable("${locationHeaderValue}"));
        verifyResponse(HttpStatus.OK, //
                readFile(
                        "/datasets/categories/add/category_with_pending_status_response.json"), //
                categoryLocation);
    }

    @Test
    @CitrusTest
    public void shouldAddCategoryWithInactiveStatus(@CitrusResource TestContext context)
            throws Exception {
        author("Nimra Inam");
        description("A category should be added with inactive status");

        variable(LOCATION_HEADER_VALUE, "");

        postRequest(CATEGORY_URL, //
                readFile(
                        "/datasets/categories/add/category_with_inactive_status_request.json"));

        extractHeader(HttpStatus.CREATED, HttpHeaders.LOCATION);
        String categoryLocation = parseAndSetVariable(CATEGORY_URL,
                context.getVariable("${locationHeaderValue}"));
        verifyResponse(HttpStatus.OK, //
                readFile(
                        "/datasets/categories/add/category_with_inactive_status_response.json"), //
                categoryLocation);
    }

    @Test
    @CitrusTest
    public void shouldCreateCategoryWithDefaultPendingStatusOnMissingStatusValue(
            @CitrusResource TestContext context) throws Exception {
        author("Nimra Inam");
        description(
                "A category should be added with default category status i.e pending");

        variable(LOCATION_HEADER_VALUE, "");

        postRequest(CATEGORY_URL, //
                readFile(
                        "/datasets/categories/add/category_with_missing_status_request.json"));

        extractHeader(HttpStatus.CREATED, HttpHeaders.LOCATION);
        String categoryLocation = parseAndSetVariable(CATEGORY_URL,
                context.getVariable("${locationHeaderValue}"));

        verifyResponse(HttpStatus.OK, //
                readFile(
                        "/datasets/categories/add/category_with_missing_status_response.json"), //
                categoryLocation);
    }

    @Test
    @CitrusTest
    public void shouldReturnBadRequestOnInvalidStatus() throws Exception {
        author("Nimra Inam");
        description(
                "Post request should return bad request when a category with invalid status is added");

        postRequest(CATEGORY_URL, //
                readFile(
                        "/datasets/categories/add/category_with_invalid_status_request.json"));

        verifyResponse(HttpStatus.BAD_REQUEST, //
                readFile(
                        "/datasets/categories/add/category_with_invalid_status_response.json"));
    }

    @Test
    @CitrusTest
    public void shouldAddCategoryWithIndustryAttribute(
            @CitrusResource TestContext context) throws Exception {
        author("Nimra Inam");
        description("A category should be added with industry attribute");

        variable(LOCATION_HEADER_VALUE, "");

        postRequest(CATEGORY_URL, //
                readFile(
                        "/datasets/categories/add/category_with_industry_attribute_request.json"));

        extractHeader(HttpStatus.CREATED, HttpHeaders.LOCATION);
        String categoryLocation = parseAndSetVariable(CATEGORY_URL,
                context.getVariable("${locationHeaderValue}"));
        verifyResponse(HttpStatus.OK, //
                readFile(
                        "/datasets/categories/add/category_with_industry_attribute_response.json"), //
                categoryLocation);
    }

    @Test
    @CitrusTest
    public void shouldAddCategoryWithInheritedAttribute(
            @CitrusResource TestContext context) throws Exception {
        author("Nimra Inam");
        description("A category should be added with inherited attribute");

        variable(LOCATION_HEADER_VALUE, "");

        postRequest(CATEGORY_URL, //
                readFile(
                        "/datasets/categories/add/category_with_inherited_attribute_request.json"));

        extractHeader(HttpStatus.CREATED, HttpHeaders.LOCATION);
        String categoryLocation = parseAndSetVariable(CATEGORY_URL,
                context.getVariable("${locationHeaderValue}"));
        verifyResponse(HttpStatus.OK, //
                readFile(
                        "/datasets/categories/add/category_with_inherited_attribute_response.json"), //
                categoryLocation);
    }

    @Test
    @CitrusTest
    public void shouldAddCategoryWithSelfAttribute(@CitrusResource TestContext context)
            throws Exception {
        author("Nimra Inam");
        description("A category should be added with self attribute");

        variable(LOCATION_HEADER_VALUE, "");

        postRequest(CATEGORY_URL, //
                readFile(
                        "/datasets/categories/add/category_with_self_attribute_request.json"));

        extractHeader(HttpStatus.CREATED, HttpHeaders.LOCATION);
        String categoryLocation = parseAndSetVariable(CATEGORY_URL,
                context.getVariable("${locationHeaderValue}"));
        verifyResponse(HttpStatus.OK, //
                readFile(
                        "/datasets/categories/add/category_with_self_attribute_response.json"), //
                categoryLocation);
    }

    @Test
    @CitrusTest
    public void shouldRetrunBadRequestOnInvalidSource() throws Exception {
        author("Nimra Inam");
        description(
                "Post request should return bad request when a category with invalid source is added");

        postRequest(CATEGORY_URL, //
                readFile(
                        "/datasets/categories/add/category_with_invalid_source_request.json"));

        verifyResponse(HttpStatus.BAD_REQUEST, //
                readFile(
                        "/datasets/categories/add/category_with_invalid_source_response.json"));
    }

    @Test
    @CitrusTest
    public void shouldRetrunBadRequestWithoutName() throws Exception {
        author("Nimra Inam");
        description(
                "Post request should return bad request when a category without name is added");

        postRequest(CATEGORY_URL, //
                readFile("/datasets/categories/add/category_without_name_request.json"));

        verifyResponse(HttpStatus.BAD_REQUEST, //
                readFile("/datasets/categories/add/category_without_name_response.json"));
    }

    @Test
    @CitrusTest
    public void shouldRetrunBadRequestOnAddingDuplicateCategory() throws Exception {
        author("Nimra Inam");
        description("Post request should return bad request when a duplicate category");

        postRequest(CATEGORY_URL, //
                readFile("/datasets/categories/add/duplicate_category_request.json"));

        // Add again to produce duplicate
        postRequest(CATEGORY_URL, //
                readFile("/datasets/categories/add/duplicate_category_request.json"));

        verifyResponse(HttpStatus.CONFLICT, //
                readFile("/datasets/categories/add/duplicate_category_response.json"));
    }

    @Test
    @CitrusTest
    public void shouldAddCategoryWithNullInParentCategoryId(
            @CitrusResource TestContext context) throws Exception {
        author("Nimra Inam");
        description("A category should be added with null in parent category id");

        variable(LOCATION_HEADER_VALUE, "");

        postRequest(CATEGORY_URL, //
                readFile(
                        "/datasets/categories/add/category_with_null_in_parent_category_id_request.json"));

        extractHeader(HttpStatus.CREATED, HttpHeaders.LOCATION);
        String categoryLocation = parseAndSetVariable(CATEGORY_URL,
                context.getVariable("${locationHeaderValue}"));
        verifyResponse(HttpStatus.OK, //
                readFile(
                        "/datasets/categories/add/category_with_null_in_parent_category_id_response.json"), //
                categoryLocation);
    }

    @Test
    @CitrusTest
    public void shouldAddCategoryWithoutAttributes(@CitrusResource TestContext context)
            throws Exception {
        author("Nimra Inam");
        description("A category should be added without any attributes");

        variable(LOCATION_HEADER_VALUE, "");

        postRequest(CATEGORY_URL, //
                readFile(
                        "/datasets/categories/add/category_without_attributes_request.json"));

        extractHeader(HttpStatus.CREATED, HttpHeaders.LOCATION);
        String categoryLocation = parseAndSetVariable(CATEGORY_URL,
                context.getVariable("${locationHeaderValue}"));
        verifyResponse(HttpStatus.OK, //
                readFile(
                        "/datasets/categories/add/category_without_attributes_response.json"), //
                categoryLocation);
    }

    @Test
    @CitrusTest
    public void shouldAddCategoryWithAttributesHavingSameSourceAndDistinctOrder(
            @CitrusResource TestContext context) throws Exception {
        author("Nimra Inam");
        description("A category should be added with same source and distinct order");

        postRequest(CATEGORY_URL, //
                readFile(
                        "/datasets/categories/add/category_with_same_source_and_distinct_order_request.json"));

        extractHeader(HttpStatus.CREATED, HttpHeaders.LOCATION);
        String categoryLocation = parseAndSetVariable(CATEGORY_URL,
                context.getVariable("${locationHeaderValue}"));

        verifyResponse(HttpStatus.OK, readFile(
                "/datasets/categories/add/category_with_same_source_and_distinct_order_response.json"), //
                categoryLocation);
    }

    @Test
    @CitrusTest
    public void shouldAddCategoryWithAttributesHavingDistinctSourceAndDistinctOrder(
            @CitrusResource TestContext context) throws Exception {
        author("Nimra Inam");
        description("A category should be added with distinct source and distinct order");

        postRequest(CATEGORY_URL, //
                readFile(
                        "/datasets/categories/add/category_with_distinct_source_and_distinct_order_request.json"));

        extractHeader(HttpStatus.CREATED, HttpHeaders.LOCATION);
        String categoryLocation = parseAndSetVariable(CATEGORY_URL,
                context.getVariable("${locationHeaderValue}"));

        verifyResponse(HttpStatus.OK, readFile(
                "/datasets/categories/add/category_with_distinct_source_and_distinct_order_response.json"), //
                categoryLocation);
    }

    @Test
    @CitrusTest
    public void shouldAddCategoryWithAttributesHavingSameSourceAndDistinctOrderWithGaps(
            @CitrusResource TestContext context) throws Exception {
        author("Nimra Inam");
        description(
                "A category should be added with same source and distinct order with gaps");

        postRequest(CATEGORY_URL, //
                readFile(
                        "/datasets/categories/add/category_with_same_source_and_distinct_order_with_gaps_request.json"));

        extractHeader(HttpStatus.CREATED, HttpHeaders.LOCATION);
        String categoryLocation = parseAndSetVariable(CATEGORY_URL,
                context.getVariable("${locationHeaderValue}"));

        verifyResponse(HttpStatus.OK, readFile(
                "/datasets/categories/add/category_with_same_source_and_distinct_order_with_gaps_response.json"), //
                categoryLocation);
    }

    @Test
    @CitrusTest
    public void shouldAddCategoryWithAttributesHavingDistinctSourceAndDistinctOrderWithGaps(
            @CitrusResource TestContext context) throws Exception {
        author("Nimra Inam");
        description(
                "A category should be added with distinct source and distinct order with gaps");

        postRequest(CATEGORY_URL, //
                readFile(
                        "/datasets/categories/add/category_with_distinct_source_and_distinct_order_with_gaps_request.json"));

        extractHeader(HttpStatus.CREATED, HttpHeaders.LOCATION);
        String categoryLocation = parseAndSetVariable(CATEGORY_URL,
                context.getVariable("${locationHeaderValue}"));

        verifyResponse(HttpStatus.OK, readFile(
                "/datasets/categories/add/category_with_distinct_source_and_distinct_order_with_gaps_response.json"), //
                categoryLocation);
    }

    @Test
    @CitrusTest
    public void shouldReturnBadRequestOnAddingCategoryWithAttributesHavingSameSourceAndSameOrder()
            throws Exception {
        author("Nimra Inam");
        description("A category should not be added with same source and same order");

        postRequest(CATEGORY_URL, //
                readFile(
                        "/datasets/categories/add/category_with_same_source_and_same_order_request.json"));

        variable("error", "attribute order can't be repeated.");
        receive(builder -> builder.endpoint(serviceClient) //
                .message(new HttpMessage() //
                        .status(HttpStatus.BAD_REQUEST)) //
                .messageType(MessageType.JSON).validate("$.message", "${error}"));
    }

    @Test
    @CitrusTest
    public void shouldReturnBadRequestOnAddingCategoryWithAttributesHavingDifferentSourceAndSameOrder()
            throws Exception {
        author("Nimra Inam");
        description("A category should not be added with same source and same order");

        postRequest(CATEGORY_URL, //
                readFile(
                        "/datasets/categories/add/category_with_different_source_and_same_order_request.json"));

        variable("error", "attribute order can't be repeated.");
        receive(builder -> builder.endpoint(serviceClient) //
                .message(new HttpMessage() //
                        .status(HttpStatus.BAD_REQUEST)) //
                .messageType(MessageType.JSON).validate("$.message", "${error}"));

    }

}
