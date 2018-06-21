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
import com.consol.citrus.message.*;
import com.etilize.burraq.category.test.base.*;

/**
 * This class contains test cases related Update Category functionality
 *
 * @author Nimra Inam
 * @see AbstractIT
 * @since 1.0.0
 */
public class UpdateCategoryIT extends AbstractIT {

    public static final String EXISTING_CATEGORY_ID_TO_UPDATE = "59fac5f70fcdf847c8eb4ca5";

    @Test
    @CitrusTest
    public void shouldUpdateNameAndDescription(@CitrusResource TestContext context)
            throws Exception {
        author("Nimra Inam");
        description("Name and description of category should be updated");

        variable(LOCATION_HEADER_VALUE, "");
        variable(CATEGORY_ID, EXISTING_CATEGORY_ID_TO_UPDATE);

        putRequest(CATEGORY_URL, //
                "${" + CATEGORY_ID + "}", //
                readFile(
                        "/datasets/categories/update/update_category_name_and_description_request.json"));

        extractHeader(HttpStatus.OK, HttpHeaders.LOCATION);
        String categoryLocation = parseAndSetVariable(CATEGORY_URL,
                context.getVariable("${locationHeaderValue}"));

        verifyResponse(HttpStatus.OK, //
                readFile(
                        "/datasets/categories/update/update_category_name_and_description_response.json"), //
                categoryLocation);
    }

    @Test
    @CitrusTest
    public void shouldUpdateStatus(@CitrusResource TestContext context) throws Exception {
        author("Nimra Inam");
        description("Status of category should be updated");

        variable(LOCATION_HEADER_VALUE, "");
        variable(CATEGORY_ID, EXISTING_CATEGORY_ID_TO_UPDATE);

        putRequest(CATEGORY_URL, //
                "${" + CATEGORY_ID + "}", //
                readFile(
                        "/datasets/categories/update/update_category_status_request.json"));

        extractHeader(HttpStatus.OK, HttpHeaders.LOCATION);
        String categoryLocation = parseAndSetVariable(CATEGORY_URL,
                context.getVariable("${locationHeaderValue}"));
        verifyResponse(HttpStatus.OK, //
                readFile(
                        "/datasets/categories/update/update_category_status_response.json"), //
                categoryLocation);
    }

    @Test
    @CitrusTest
    public void shouldUpdateIndustry(@CitrusResource TestContext context)
            throws Exception {
        author("Nimra Inam");
        description("Industry of category should be updated");

        variable(LOCATION_HEADER_VALUE, "");
        variable(CATEGORY_ID, EXISTING_CATEGORY_ID_TO_UPDATE);

        putRequest(CATEGORY_URL, //
                "${" + CATEGORY_ID + "}", //
                readFile("/datasets/categories/update/update_industry_request.json"));

        extractHeader(HttpStatus.OK, HttpHeaders.LOCATION);
        String categoryLocation = parseAndSetVariable(CATEGORY_URL,
                context.getVariable("${locationHeaderValue}"));
        verifyResponse(HttpStatus.OK, //
                readFile("/datasets/categories/update/update_industry_response.json"), //
                categoryLocation);
    }

    @Test
    @CitrusTest
    public void shouldUpdateParentCategory(@CitrusResource TestContext context)
            throws Exception {
        author("Nimra Inam");
        description("Parent category should be updated");

        variable(LOCATION_HEADER_VALUE, "");
        variable(CATEGORY_ID, EXISTING_CATEGORY_ID_TO_UPDATE);

        putRequest(CATEGORY_URL, //
                "${" + CATEGORY_ID + "}", //
                readFile(
                        "/datasets/categories/update/update_parent_category_request.json"));

        extractHeader(HttpStatus.OK, HttpHeaders.LOCATION);
        String categoryLocation = parseAndSetVariable(CATEGORY_URL,
                context.getVariable("${locationHeaderValue}"));
        verifyResponse(HttpStatus.OK, //
                readFile(
                        "/datasets/categories/update/update_parent_category_response.json"), //
                categoryLocation);
    }

    @Test
    @CitrusTest
    public void shouldUpdateAttributeIdOfExistingAttribute(
            @CitrusResource TestContext context) throws Exception {
        author("Nimra Inam");
        description("Attribute id of existing attribute of a category should be updated");

        variable(LOCATION_HEADER_VALUE, "");
        variable(CATEGORY_ID, EXISTING_CATEGORY_ID_TO_UPDATE);

        putRequest(CATEGORY_URL, //
                "${" + CATEGORY_ID + "}", //
                readFile(
                        "/datasets/categories/update/update_attribute_id_of_existing_attribute_request.json"));

        extractHeader(HttpStatus.OK, HttpHeaders.LOCATION);
        String categoryLocation = parseAndSetVariable(CATEGORY_URL,
                context.getVariable("${locationHeaderValue}"));
        verifyResponse(HttpStatus.OK, //
                readFile(
                        "/datasets/categories/update/update_attribute_id_of_existing_attribute_response.json"), //
                categoryLocation);
    }

    @Test
    @CitrusTest
    public void shouldUpdateSourceOfExistingAttribute(@CitrusResource TestContext context)
            throws Exception {
        author("Nimra Inam");
        description("Source of existing attribute of a category should be updated");

        variable(LOCATION_HEADER_VALUE, "");
        variable(CATEGORY_ID, EXISTING_CATEGORY_ID_TO_UPDATE);

        putRequest(CATEGORY_URL, //
                "${" + CATEGORY_ID + "}", //
                readFile(
                        "/datasets/categories/update/update_source_of_existing_attribute_request.json"));

        extractHeader(HttpStatus.OK, HttpHeaders.LOCATION);
        String categoryLocation = parseAndSetVariable(CATEGORY_URL,
                context.getVariable("${locationHeaderValue}"));
        verifyResponse(HttpStatus.OK, //
                readFile(
                        "/datasets/categories/update/update_source_of_existing_attribute_response.json"), //
                categoryLocation);
    }

    @Test
    @CitrusTest
    public void shouldUpdateOrderOfExistingAttribute(@CitrusResource TestContext context)
            throws Exception {
        author("Nimra Inam");
        description("Order of existing attribute of a category should be updated");

        variable(LOCATION_HEADER_VALUE, "");
        variable(CATEGORY_ID, EXISTING_CATEGORY_ID_TO_UPDATE);

        putRequest(CATEGORY_URL, //
                "${" + CATEGORY_ID + "}", //
                readFile(
                        "/datasets/categories/update/update_order_of_existing_attribute_request.json"));

        extractHeader(HttpStatus.OK, HttpHeaders.LOCATION);
        String categoryLocation = parseAndSetVariable(CATEGORY_URL,
                context.getVariable("${locationHeaderValue}"));
        verifyResponse(HttpStatus.OK, //
                readFile(
                        "/datasets/categories/update/update_order_of_existing_attribute_response.json"), //
                categoryLocation);
    }

    @Test
    @CitrusTest
    public void shouldUpdateCategoryByAddingNewAttribute(
            @CitrusResource TestContext context) throws Exception {
        author("Nimra Inam");
        description("A category should be updated by adding new attributes");

        variable(LOCATION_HEADER_VALUE, "");
        variable(CATEGORY_ID, EXISTING_CATEGORY_ID_TO_UPDATE);

        putRequest(CATEGORY_URL, //
                "${" + CATEGORY_ID + "}", //
                readFile(
                        "/datasets/categories/update/update_and_add_new_attributes_request.json"));

        extractHeader(HttpStatus.OK, HttpHeaders.LOCATION);
        String categoryLocation = parseAndSetVariable(CATEGORY_URL,
                context.getVariable("${locationHeaderValue}"));
        verifyResponse(HttpStatus.OK, //
                readFile(
                        "/datasets/categories/update/update_and_add_new_attributes_response.json"), //
                categoryLocation);
    }

    @Test
    @CitrusTest
    public void shouldReturnBadRequestOnUpdatingIndustryIdToEmptyString()
            throws Exception {
        author("Nimra Inam");
        description(
                "A category should not be updated when industry id is updated to empty string");

        variable(CATEGORY_ID, EXISTING_CATEGORY_ID_TO_UPDATE);

        putRequest(CATEGORY_URL, //
                "${" + CATEGORY_ID + "}", //
                readFile(
                        "/datasets/categories/update/update_industry_id_to_empty_string_request.json"));

        verifyResponse(HttpStatus.BAD_REQUEST, //
                readFile(
                        "/datasets/categories/update/update_industry_id_to_empty_string_response.json"));
    }

    @Test
    @CitrusTest
    public void shouldReturnBadRequestOnUpdatingNameToEmptyString() throws Exception {
        author("Nimra Inam");
        description(
                "A category should not be updated when name is updated to empty string");

        variable(CATEGORY_ID, EXISTING_CATEGORY_ID_TO_UPDATE);

        putRequest(CATEGORY_URL, //
                "${" + CATEGORY_ID + "}", //
                readFile(
                        "/datasets/categories/update/update_name_to_empty_string_request.json"));

        verifyResponse(HttpStatus.BAD_REQUEST, //
                readFile(
                        "/datasets/categories/update/update_name_to_empty_string_response.json"));
    }

    @Test
    @CitrusTest
    public void shouldReturnBadRequestOnUpdatingStatusToEmptyString() throws Exception {
        author("Nimra Inam");
        description(
                "A category should not be updated when status is updated to empty string");

        variable(CATEGORY_ID, EXISTING_CATEGORY_ID_TO_UPDATE);

        putRequest(CATEGORY_URL, //
                "${" + CATEGORY_ID + "}", //
                readFile(
                        "/datasets/categories/update/update_status_to_empty_string_request.json"));

        verifyResponse(HttpStatus.BAD_REQUEST, //
                readFile(
                        "/datasets/categories/update/update_status_to_empty_string_response.json"));
    }

    @Test
    @CitrusTest
    public void shouldReturnBadRequestOnUpdatingParentCategoryIdToEmptyString()
            throws Exception {
        author("Nimra Inam");
        description(
                "A category should not be updated when parent category id is updated to empty string");

        variable(CATEGORY_ID, EXISTING_CATEGORY_ID_TO_UPDATE);

        putRequest(CATEGORY_URL, //
                "${" + CATEGORY_ID + "}", //
                readFile(
                        "/datasets/categories/update/update_parent_category_id_to_empty_string_request.json"));

        verifyResponse(HttpStatus.BAD_REQUEST, //
                readFile(
                        "/datasets/categories/update/update_parent_category_id_to_empty_string_response.json"));
    }
}
