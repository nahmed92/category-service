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
 * This class contains test cases related Search Category functionality
 *
 * @author Nimra Inam
 * @see AbstractIT
 * @since 1.0.0
 */
public class SearchCategoryIT extends AbstractIT {

    @Test
    @CitrusTest
    public void shouldFindAllCategories(@CitrusResource TestContext context)
            throws Exception {
        author("Nimra Inam");
        description("should return all existing categories");

        variable("urlToCheck", "/categories");
        variable("pageSize", "20");
        variable("totalElements", "1");
        variable("totalPages", "1");
        variable("pageNumber", "0");

        getRequest(CATEGORY_URL);

        receive(builder -> builder.endpoint(serviceClient) //
                .message(new HttpMessage() //
                        .status(HttpStatus.OK)) //
                .messageType(MessageType.JSON).validate("$._embedded.categories[*].name",
                        "@assertThat(not(isEmptyString())@") //
                .validate("$._embedded.categories[*].description",
                        "@assertThat(not(isEmptyString())@") //
                .validate("$._embedded.categories[*].status",
                        "@assertThat(not(isEmptyString())@") //
                .validate("$._embedded.categories[*].industryId",
                        "@assertThat(not(isEmptyString())@") //
                .validate("$._embedded.categories[*]._links.self.href",
                        "@contains(${urlToCheck})@") //
                .validate("$._embedded.categories[*]._links.category.href",
                        "@contains(${urlToCheck})@") //
                .validate("$.page.size", "${pageSize}") //
                .validate("$.page.totalElements",
                        "@assertThat(greaterThanOrEqualTo(${totalElements}))@") //
                .validate("$.page.totalPages",
                        "@assertThat(greaterThanOrEqualTo(${totalPages}))@") //
                .validate("$.page.number", "${pageNumber}"));

    }

    @Test
    @CitrusTest
    public void shouldReturnCategoryById(@CitrusResource TestContext context)
            throws Exception {
        author("Nimra Inam");
        description("should return category with matching id found");

        variable("categoryId", "5b2b3d143c1c263291de662f");

        getRequest(CATEGORY_URL //
                + "${categoryId}");

        verifyResponse(HttpStatus.OK, //
                readFile(
                        "/datasets/categories/search/find_category_by_id_response.json"));
    }

    @Test
    @CitrusTest
    public void shouldFindCategoryByNameDescriptionAndIndustryId(
            @CitrusResource TestContext context) throws Exception {
        author("Nimra Inam");
        description(
                "should return category with matching name, description and industry id");

        variable("name", "Wireless Routers");
        variable("description", "Wireless Routers description");
        variable("status", "PENDING");
        variable("industryId", "59762d7caddb13b4a8440a38");
        variable("categoryId", "5b2b3d013c1c263291de662e");

        getRequest(CATEGORY_URL //
                + "?name=Wireless Routers&description=Wireless Routers description&industryId=59762d7caddb13b4a8440a38");

        verifyResponse(HttpStatus.OK, //
                readFile(
                        "/datasets/categories/search/find_category_by_name_response.json"));
    }

    @Test
    @CitrusTest
    public void shouldRetunNoRecordWhenMatchingNameDoesNotExist(
            @CitrusResource TestContext context) throws Exception {
        author("Nimra Inam");
        description(
                "should return empty array when no records found with matching category");

        getRequest(CATEGORY_URL + "?name=CategoryNotFound");

        verifyResponse(HttpStatus.OK, //
                readFile(
                        "/datasets/categories/search/find_category_which_does_not_exist.json"));
    }

    @Test
    @CitrusTest
    public void shouldReturnNotFoundWhenMatchingCategoryIdDoesNotExist(
            @CitrusResource TestContext context) throws Exception {
        author("Nimra Inam");
        description("should return not found when matching category id does not exist");

        variable("categoryId", "59fac5f70fcdf847c8eb4c90");

        getRequest(CATEGORY_URL //
                + "${categoryId}");

        verifyResponse(HttpStatus.NOT_FOUND);
    }

}
