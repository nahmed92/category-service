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
import com.etilize.burraq.category.config.*;
import com.etilize.burraq.category.test.base.*;

/**
 * This class contains test cases related Add Category functionality
 *
 * @author Nimra Inam
 * @see AbstractIT
 * @since 1.0.0
 */
public class CategoryIdentifiersIT extends AbstractIT {

    public static final String EXISTING_CATEGORY_ID_TO_UPDATE = "5ad86b140fcdf819732fcbd6";

    @Test
    @CitrusTest
    public void shouldAddCategoryIdentifier(@CitrusResource TestContext context)
            throws Exception {
        author("Nimra Inam");
        description("A category identifier should be added");

        variable(LOCATION_HEADER_VALUE, "");
        variable(CATEGORY_ID, EXISTING_CATEGORY_ID_TO_UPDATE);

        putRequest(CATEGORY_URL, //
                "${" + CATEGORY_ID + "}", //
                readFile(
                        "/datasets/categories/update/update_category_identifier_request.json"));

        extractHeader(HttpStatus.OK, HttpHeaders.LOCATION);
        String categoryLocation = parseAndSetVariable(CATEGORY_URL,
                context.getVariable("${locationHeaderValue}"));
        verifyResponse(HttpStatus.OK, //
                readFile(
                        "/datasets/categories/update/update_category_identifier_response.json"), //
                categoryLocation);
    }

    @Test
    @CitrusTest
    public void shouldAddCategoryIdentifierAsSubsetOfAttributes(
            @CitrusResource TestContext context) throws Exception {
        author("Nimra Inam");
        description("A category identifier should be added");

        variable(LOCATION_HEADER_VALUE, "");
        variable(CATEGORY_ID, EXISTING_CATEGORY_ID_TO_UPDATE);

        putRequest(CATEGORY_URL, //
                "${" + CATEGORY_ID + "}", //
                readFile(
                        "/datasets/categories/update/update_category_identifier_subset_of_attributes_request.json"));

        extractHeader(HttpStatus.OK, HttpHeaders.LOCATION);
        String categoryLocation = parseAndSetVariable(CATEGORY_URL,
                context.getVariable("${locationHeaderValue}"));
        verifyResponse(HttpStatus.OK, //
                readFile(
                        "/datasets/categories/update/update_category_identifier_subset_of_attributes_response.json"), //
                categoryLocation);
    }

    @Test
    @CitrusTest
    public void shouldAddInvalidCategoryIdentifier(@CitrusResource TestContext context)
            throws Exception {
        author("Nimra Inam");
        description(
                "An invalid category identifier should be added (it's a bug for now)");

        variable(LOCATION_HEADER_VALUE, "");
        variable(CATEGORY_ID, EXISTING_CATEGORY_ID_TO_UPDATE);

        putRequest(CATEGORY_URL, //
                "${" + CATEGORY_ID + "}", //
                readFile(
                        "/datasets/categories/update/update_category_identifier_with_invalid_value_request.json"));

        extractHeader(HttpStatus.OK, HttpHeaders.LOCATION);
        String categoryLocation = parseAndSetVariable(CATEGORY_URL,
                context.getVariable("${locationHeaderValue}"));
        verifyResponse(HttpStatus.OK, //
                readFile(
                        "/datasets/categories/update/update_category_identifier_with_invalid_value_response.json"), //
                categoryLocation);
    }
}
