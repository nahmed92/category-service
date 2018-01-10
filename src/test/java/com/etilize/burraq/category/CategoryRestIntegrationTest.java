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

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Date;

import org.bson.types.ObjectId;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import com.etilize.burraq.category.test.AbstractRestIntegrationTest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Sets;
import com.lordofthejars.nosqlunit.annotation.ShouldMatchDataSet;
import com.lordofthejars.nosqlunit.annotation.UsingDataSet;

/**
 * Houses rest integration tests
 *
 * @author Ebad Hashmi
 * @version 1.0
 *
 */
@UsingDataSet(locations = { "/datasets/categories/categories.bson" })
public class CategoryRestIntegrationTest extends AbstractRestIntegrationTest {

    @Autowired
    private ObjectMapper mapper;

    @Test
    public void shouldFindAllCategories() throws Exception {
        mockMvc.perform(get("/categories") //
                .with(bearerToken)) //
                .andExpect(status().isOk()) //
                .andExpect(jsonPath("$._embedded.categories[*]", hasSize(3))) //
                .andExpect(jsonPath("$._embedded.categories[*].name", //
                        containsInAnyOrder("Parent Category", "Child Category 1",
                                "Child Category 2"))) //
                .andExpect(jsonPath("$._embedded.categories[*].industryId", //
                        hasItem("59762d7caddb13b4a8440a38"))) //
                .andExpect(jsonPath("$._embedded.categories[*].status", //
                        containsInAnyOrder("ACTIVE", "INACTIVE", "PENDING"))) //
                .andExpect(jsonPath("$._embedded.categories[1].parentCategoryId", //
                        is("59b78ed24daf991ecaafa263"))) //
                .andExpect(jsonPath("$._embedded.categories[2].parentCategoryId", //
                        is("59b78ed24daf991ecaafa263"))) //
                .andExpect(jsonPath("$._embedded.categories[2].attributes", hasSize(1))) //
                .andExpect(jsonPath("$._embedded.categories[2].attributes[0].attributeId",
                        is("59b78ed24daf991ecaafgfh"))) //
                .andExpect(jsonPath("$._embedded.categories[2].attributes[0].source",
                        is("INHERITED"))) //
                .andExpect(
                        jsonPath("$._embedded.categories[2].attributes[0].order", is(1))) //
                .andExpect(jsonPath("$._embedded.categories[*]._links.self.href") //
                        .value(contains(endsWith("categories/59b78ed24daf991ecaafa263"), //
                                endsWith("categories/59b78f244daf991ecaafa264"), //
                                endsWith("categories/59b795074daf991ecaafa265")))) //
                .andExpect(jsonPath("$._embedded.categories[*]._links.category.href") //
                        .value(contains(endsWith("categories/59b78ed24daf991ecaafa263"), //
                                endsWith("categories/59b78f244daf991ecaafa264"), //
                                endsWith("categories/59b795074daf991ecaafa265")))) //
                .andExpect(jsonPath("$._embedded.categories[*]._links.update_status.href") //
                        .value(contains(endsWith(
                                "categories/59b78ed24daf991ecaafa263/update_status"), //
                                endsWith(
                                        "categories/59b78f244daf991ecaafa264/update_status"), //
                                endsWith(
                                        "categories/59b795074daf991ecaafa265/update_status")))) //
                .andExpect(jsonPath("$.page.size", is(20))) //
                .andExpect(jsonPath("$.page.totalElements", is(3))) //
                .andExpect(jsonPath("$.page.totalPages", is(1))) //
                .andExpect(jsonPath("$.page.number", is(0)));

    }

    @Test
    public void shouldFindCategoryById() throws Exception {
        mockMvc.perform(get("/categories/59b795074daf991ecaafa265") //
                .with(bearerToken)) //
                .andExpect(status().isOk()).andExpect(
                        jsonPath("$.name", is("Child Category 2"))) //
                .andExpect(jsonPath("$.description",
                        is("some description for child category 2"))) //
                .andExpect(jsonPath("$.status", is("PENDING"))) //
                .andExpect(jsonPath("$.parentCategoryId", is("59b78ed24daf991ecaafa263"))) //
                .andExpect(jsonPath("$.industryId", is("59762d7caddb13b4a8440a38"))) //
                .andExpect(jsonPath("$.attributes", hasSize(1))) //
                .andExpect(jsonPath("$.attributes[0].attributeId",
                        is("59b78ed24daf991ecaafgfh"))) //
                .andExpect(jsonPath("$.attributes[0].source", is("INHERITED"))) //
                .andExpect(jsonPath("$.attributes[0].order", is(1))) //
                .andExpect(jsonPath("$._links.update_status").exists()) //
                .andExpect(jsonPath("$._links.update_status.href") //
                        .value(endsWith(
                                "categories/59b795074daf991ecaafa265/update_status")));

    }

    @Test
    @ShouldMatchDataSet(location = "/datasets/categories/categories_after_create.bson")
    public void shouldCreateNewCategory() throws Exception {
        final Category category = new Category("Child Category 3",
                "some description for child category 3", "59762d7caddb13b4a8440a38");
        category.setStatus(Status.INACTIVE);
        category.setParentCategoryId(new ObjectId("59b78ed24daf991ecaafa263"));

        mockMvc.perform(post("/categories") //
                .with(bearerToken) //
                .contentType(MediaType.APPLICATION_JSON) //
                .content(mapper.writeValueAsString(category))) //
                .andExpect(status().isCreated());
    }

    @Test
    @ShouldMatchDataSet(location = "/datasets/categories/categories_after_create_with_attributes.bson")
    public void shouldCreateNewCategoryWithAttributes() throws Exception {
        final Category category = new Category("Child Category 3",
                "some description for child category 3", "59762d7caddb13b4a8440a38");
        category.setStatus(Status.INACTIVE);
        category.setParentCategoryId(new ObjectId("59b78ed24daf991ecaafa263"));
        category.setAttributes(
                Sets.newHashSet(new Attribute("attributeId-1", Source.SELF, 1)));

        mockMvc.perform(post("/categories") //
                .with(bearerToken) //
                .contentType(MediaType.APPLICATION_JSON) //
                .content(mapper.writeValueAsString(category))) //
                .andExpect(status().isCreated());
    }

    @Test
    @ShouldMatchDataSet(location = "/datasets/categories/categories_after_update.bson")
    public void shouldUpdateCategoryById() throws JsonProcessingException, Exception {
        final Category category = new Category("Child Category 1 Updated",
                "some updated description for child category 1",
                "59762d7caddb13b4a8440a38");
        category.setStatus(Status.INACTIVE);
        category.setParentCategoryId(new ObjectId("59b78ed24daf991ecaafa263"));

        mockMvc.perform(put("/categories/59b78f244daf991ecaafa264") //
                .with(bearerToken) //
                .contentType(MediaType.APPLICATION_JSON) //
                .content(mapper.writeValueAsString(category))) //
                .andExpect(status().isNoContent());
    }

    @Test
    @ShouldMatchDataSet(location = "/datasets/categories/categories_after_update.bson")
    public void shouldNotUpdateCategoryCreatedByAndCreatedDate()
            throws JsonProcessingException, Exception {
        final Category category = new Category("Child Category 1 Updated",
                "some updated description for child category 1",
                "59762d7caddb13b4a8440a38");
        category.setStatus(Status.INACTIVE);
        category.setParentCategoryId(new ObjectId("59b78ed24daf991ecaafa263"));
        category.setCreatedBy("ROLE_PTM");
        category.setCreatedDate(new Date());

        mockMvc.perform(put("/categories/59b78f244daf991ecaafa264") //
                .with(bearerToken) //
                .contentType(MediaType.APPLICATION_JSON) //
                .content(mapper.writeValueAsString(category))) //
                .andExpect(status().isNoContent());
    }

    @Test
    @ShouldMatchDataSet(location = "/datasets/categories/categories_after_delete.bson")
    public void shouldDeleteCategoryById() throws Exception {
        mockMvc.perform(delete("/categories/59b795074daf991ecaafa265") //
                .with(bearerToken)) //
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldReturnBadRequestWhenNameIsNotPresent()
            throws JsonProcessingException, Exception {
        final Category category = new Category(null,
                "some description for child category 3", "59762d7caddb13b4a8440a38");
        category.setStatus(Status.INACTIVE);
        category.setParentCategoryId(new ObjectId("59b78ed24daf991ecaafa263"));

        mockMvc.perform(post("/categories") //
                .with(bearerToken) //
                .contentType(MediaType.APPLICATION_JSON) //
                .content(mapper.writeValueAsString(category))) //
                .andExpect(status().isBadRequest()) //
                .andExpect(jsonPath("$.errors", notNullValue())) //
                .andExpect(jsonPath("$.errors", hasSize(1))) //
                .andExpect(jsonPath("$.errors[0].message", is("name is required")));
    }

    @Test
    public void shouldReturnBadRequestWhenStatusIsNotPresent()
            throws JsonProcessingException, Exception {
        final Category category = new Category("Category",
                "some description for child category 3", "59762d7caddb13b4a8440a38");
        category.setStatus(null);
        category.setParentCategoryId(new ObjectId("59b78ed24daf991ecaafa263"));

        mockMvc.perform(post("/categories") //
                .with(bearerToken) //
                .contentType(MediaType.APPLICATION_JSON) //
                .content(mapper.writeValueAsString(category))) //
                .andExpect(status().isBadRequest()) //
                .andExpect(jsonPath("$.errors", notNullValue())) //
                .andExpect(jsonPath("$.errors", hasSize(1))) //
                .andExpect(jsonPath("$.errors[0].message", is("status is required")));
    }

    @Test
    public void shouldReturnBadRequestWhenIndustryIdIsNotPresent()
            throws JsonProcessingException, Exception {
        final Category category = new Category("Category",
                "some description for child category 3", null);
        category.setStatus(Status.INACTIVE);
        category.setParentCategoryId(new ObjectId("59b78ed24daf991ecaafa263"));

        mockMvc.perform(post("/categories") //
                .with(bearerToken) //
                .contentType(MediaType.APPLICATION_JSON) //
                .content(mapper.writeValueAsString(category))) //
                .andExpect(status().isBadRequest()) //
                .andExpect(jsonPath("$.errors", notNullValue())) //
                .andExpect(jsonPath("$.errors", hasSize(1))) //
                .andExpect(jsonPath("$.errors[0].message", is("industryId is required")));
    }

    @Test
    public void shouldReturnBadRequestWhenAttributeIdIsNotPresent()
            throws JsonProcessingException, Exception {
        final Category category = new Category("Category",
                "some description for child category 3", "59762d7caddb13b4a8440a38");
        category.setStatus(Status.INACTIVE);
        category.setParentCategoryId(new ObjectId("59b78ed24daf991ecaafa263"));
        category.setAttributes(Sets.newHashSet(new Attribute(null, Source.SELF, 1)));

        mockMvc.perform(post("/categories") //
                .with(bearerToken) //
                .contentType(MediaType.APPLICATION_JSON) //
                .content(mapper.writeValueAsString(category))) //
                .andExpect(status().isBadRequest()) //
                .andExpect(jsonPath("$.errors", notNullValue())) //
                .andExpect(jsonPath("$.errors", hasSize(1))) //
                .andExpect(
                        jsonPath("$.errors[0].message", is("attributeId is required")));
    }

    @Test
    public void shouldReturnBadRequestWhenAttributeSourceIsNotPresent()
            throws JsonProcessingException, Exception {
        final Category category = new Category("Category",
                "some description for child category 3", "59762d7caddb13b4a8440a38");
        category.setStatus(Status.INACTIVE);
        category.setParentCategoryId(new ObjectId("59b78ed24daf991ecaafa263"));
        category.setAttributes(Sets.newHashSet(new Attribute("attributeId-1", null, 1)));

        mockMvc.perform(post("/categories") //
                .with(bearerToken) //
                .contentType(MediaType.APPLICATION_JSON) //
                .content(mapper.writeValueAsString(category))) //
                .andExpect(status().isBadRequest()) //
                .andExpect(jsonPath("$.errors", notNullValue())) //
                .andExpect(jsonPath("$.errors", hasSize(1))) //
                .andExpect(jsonPath("$.errors[0].message", is("source is required")));
    }

    @Test
    public void shouldReturnBadRequestWhenAttributeOrderIsNotPresent()
            throws JsonProcessingException, Exception {
        final Category category = new Category("Category",
                "some description for child category 3", "59762d7caddb13b4a8440a38");
        category.setStatus(Status.INACTIVE);
        category.setParentCategoryId(new ObjectId("59b78ed24daf991ecaafa263"));
        category.setAttributes(
                Sets.newHashSet(new Attribute("attributeId-1", Source.SELF, null)));

        mockMvc.perform(post("/categories") //
                .with(bearerToken) //
                .contentType(MediaType.APPLICATION_JSON) //
                .content(mapper.writeValueAsString(category))) //
                .andExpect(status().isBadRequest()) //
                .andExpect(jsonPath("$.errors", notNullValue())) //
                .andExpect(jsonPath("$.errors", hasSize(1))) //
                .andExpect(jsonPath("$.errors[0].message", is("order is required")));
    }

    @Test
    public void shouldReturnConflictWhenCategoryNameIsAlreadyPresent()
            throws JsonProcessingException, Exception {
        final Category category = new Category("Parent Category",
                "some description for child category 3", "59762d7caddb13b4a8440a38");
        category.setStatus(Status.INACTIVE);
        category.setParentCategoryId(new ObjectId("59b78ed24daf991ecaafa263"));

        mockMvc.perform(post("/categories") //
                .with(bearerToken) //
                .contentType(MediaType.APPLICATION_JSON) //
                .content(mapper.writeValueAsString(category))) //
                .andExpect(status().isConflict()) //
                .andExpect(jsonPath("$.message", is("name already exists in industry.")));
    }

    @Test
    public void shouldReturnConflictWhenCaseInsensitiveCategoryNameIsAlreadyPresent()
            throws JsonProcessingException, Exception {
        final Category category = new Category("PARENT CATEGORY",
                "some description for child category 3", "59762d7caddb13b4a8440a38");
        category.setStatus(Status.INACTIVE);
        category.setParentCategoryId(new ObjectId("59b78ed24daf991ecaafa263"));

        mockMvc.perform(post("/categories") //
                .with(bearerToken) //
                .contentType(MediaType.APPLICATION_JSON) //
                .content(mapper.writeValueAsString(category))) //
                .andExpect(status().isConflict());
    }

    @Test
    public void shouldFindCategoryByName() throws Exception {
        mockMvc.perform(get("/categories?name={name}", "Child Category 2") //
                .with(bearerToken)) //
                .andExpect(status().isOk()) //
                .andExpect(jsonPath("$._embedded.categories[0].name",
                        is("Child Category 2"))) //
                .andExpect(jsonPath("$._embedded.categories[0].description",
                        is("some description for child category 2"))) //
                .andExpect(jsonPath("$._embedded.categories[0].status", is("PENDING"))) //
                .andExpect(jsonPath("$._embedded.categories[0].parentCategoryId",
                        is("59b78ed24daf991ecaafa263"))) //
                .andExpect(jsonPath("$._embedded.categories[0].industryId",
                        is("59762d7caddb13b4a8440a38"))) //
                .andExpect(jsonPath("$._embedded.categories[0].attributes", hasSize(1))) //
                .andExpect(jsonPath("$._embedded.categories[0].attributes[0].attributeId",
                        is("59b78ed24daf991ecaafgfh"))) //
                .andExpect(jsonPath("$._embedded.categories[0].attributes[0].source",
                        is("INHERITED"))) //
                .andExpect(
                        jsonPath("$._embedded.categories[0].attributes[0].order", is(1))) //
                .andExpect(jsonPath("$._embedded.categories[0]._links.self.href",
                        endsWith("/categories/59b795074daf991ecaafa265"))) //
                .andExpect(jsonPath("$.page.size", is(20))) //
                .andExpect(jsonPath("$.page.totalElements", is(1))) //
                .andExpect(jsonPath("$.page.totalPages", is(1))) //
                .andExpect(jsonPath("$.page.number", is(0)));
    }

    @Test
    public void shouldReturnBadRequestWhenStatusIsInValid()
            throws JsonProcessingException, Exception {
        mockMvc.perform(post("/categories") //
                .contentType(MediaType.APPLICATION_JSON) //
                .content("{" + "\"name\": \"Child Category 1\","
                        + "\"description\": \"some description for child catgeory 1\","
                        + "\"status\": \"INVALID\","
                        + "\"industryId\": \"59762d7caddb13b4a8440a38\"}") //
                .with(bearerToken)) //
                .andExpect(status().isBadRequest()) //
                .andExpect(jsonPath("$.message",
                        startsWith("JSON parse error: Can not deserialize value of"
                                + " type com.etilize.burraq.category.Status from String"
                                + " \"INVALID\": value not one of declared Enum instance names:"
                                + " [INACTIVE, ACTIVE, PENDING]")));
    }

    @Test
    public void shouldReturnBadRequestWhenAttributeSourceIsInValid()
            throws JsonProcessingException, Exception {
        mockMvc.perform(post("/categories") //
                .contentType(MediaType.APPLICATION_JSON) //
                .content("{" + "\"name\": \"Child Category 1\","
                        + "\"description\": \"some description for child catgeory 1\","
                        + "\"status\": \"ACTIVE\","
                        + "\"industryId\": \"59762d7caddb13b4a8440a38\","
                        + "\"attributes\":[" + "{"
                        + "\"attributeId\": \"59762d7caddb13b4a8440a3g\","
                        + "\"source\": \"Invalid\"," + "\"order\": 1" + "}" + "]" + "}") //
                .with(bearerToken)) //
                .andExpect(status().isBadRequest()) //
                .andExpect(jsonPath("$.message",
                        startsWith("JSON parse error: Can not deserialize value of"
                                + " type com.etilize.burraq.category.Source"
                                + " from String \"Invalid\": value not one of declared"
                                + " Enum instance names: [INHERITED, SELF, SYSTEM]")));
    }

    @Test
    public void shouldReturnBadRequestWhenAttributeOrderRepeatedAtCreate()
            throws JsonProcessingException, Exception {
        final Category category = new Category("Child Category 3",
                "some description for child category 3", "59762d7caddb13b4a8440a38");
        category.setStatus(Status.INACTIVE);
        category.setParentCategoryId(new ObjectId("59b78ed24daf991ecaafa263"));
        category.addAttribute(new Attribute("79b78ed24daf991ecaafgfe", Source.SYSTEM, 1));
        category.addAttribute(new Attribute("59b78ed24daf991ecaafgfe", Source.SELF, 1));
        mockMvc.perform(post("/categories") //
                .with(bearerToken) //
                .contentType(MediaType.APPLICATION_JSON) //
                .content(mapper.writeValueAsString(category))) //
                .andExpect(status().isBadRequest()) //
                .andExpect(
                        jsonPath("$.message", is("attribute order can't be repeated.")));
    }

    @Test
    public void shouldReturnBadRequestWhenAttributeOrderRepeatedAtUpdate()
            throws JsonProcessingException, Exception {
        final Category category = new Category("Child Category 2",
                "some description for child category 2", "59762d7caddb13b4a8440a38");
        category.setStatus(Status.ACTIVE);
        category.setParentCategoryId(new ObjectId("59b78ed24daf991ecaafa263"));
        category.addAttribute(new Attribute("79b78ed24daf991ecaafgfe", Source.SYSTEM, 1));
        category.addAttribute(new Attribute("59b78ed24daf991ecaafgfe", Source.SELF, 1));
        mockMvc.perform(put("/categories/59b795074daf991ecaafa265") //
                .with(bearerToken) //
                .contentType(MediaType.APPLICATION_JSON) //
                .content(mapper.writeValueAsString(category))) //
                .andExpect(status().isBadRequest()) //
                .andExpect(
                        jsonPath("$.message", is("attribute order can't be repeated.")));
    }
}
