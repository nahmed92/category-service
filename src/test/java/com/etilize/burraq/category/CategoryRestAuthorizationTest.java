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

import org.bson.types.ObjectId;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import com.etilize.burraq.category.test.AbstractRestIntegrationTest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lordofthejars.nosqlunit.annotation.UsingDataSet;

/**
 * Houses rest integration tests for authentication and authorization
 *
 * @author Uzair Zafar
 * @version 1.0
 *
 */
@UsingDataSet(locations = { "/datasets/categories/categories.bson" })
public class CategoryRestAuthorizationTest extends AbstractRestIntegrationTest {

    @Autowired
    private ObjectMapper mapper;

    @Test
    public void shouldReturnUnauthorizedStatusOnFindingCategoryByIdWithoutAuthorizationHeader()
            throws Exception {
        mockMvc.perform(get("/categories/59b795074daf991ecaafa265")) //
                .andExpect(status().isUnauthorized()) //
                .andExpect(jsonPath("$.error", is("unauthorized"))) //
                .andExpect(jsonPath("$.error_description",
                        is("Full authentication is required to access this resource")));
    }

    @Test
    public void shouldReturnUnauthorizedStatusOnCreatingNewCategoryWithoutAuthorizationHeader()
            throws Exception {
        final Category category = new Category("Child Category 3",
                "some description for child category 3", Status.INACTIVE,
                "59762d7caddb13b4a8440a38");
        category.setParentCategoryId(new ObjectId("59b78ed24daf991ecaafa263"));

        mockMvc.perform(post("/categories") //
                .contentType(MediaType.APPLICATION_JSON) //
                .content(mapper.writeValueAsString(category))) //
                .andExpect(status().isUnauthorized()) //
                .andExpect(jsonPath("$.error", is("unauthorized"))) //
                .andExpect(jsonPath("$.error_description",
                        is("Full authentication is required to access this resource")));
    }

    @Test
    public void shouldReturnUnauthorizedStatusOnDeletingCategoryWithoutAuthorizationHeader()
            throws Exception {
        mockMvc.perform(delete("/categories/59b795074daf991ecaafa265")) //
                .andExpect(status().isUnauthorized()) //
                .andExpect(jsonPath("$.error", is("unauthorized"))) //
                .andExpect(jsonPath("$.error_description",
                        is("Full authentication is required to access this resource")));
    }

    @Test
    public void shouldReturnUnauthorizedStatusOnUpdatingExistingCategoryWithoutAuthorizationHeader()
            throws Exception {
        final Category category = new Category("Child Category 1 Updated",
                "some updated description for child category 1", Status.ACTIVE,
                "59762d7caddb13b4a8440a38");
        category.setParentCategoryId(new ObjectId("59b78ed24daf991ecaafa263"));

        mockMvc.perform(put("/categories/59b78f244daf991ecaafa264") //
                .contentType(MediaType.APPLICATION_JSON) //
                .content(mapper.writeValueAsString(category))) //
                .andExpect(status().isUnauthorized()) //
                .andExpect(jsonPath("$.error", is("unauthorized"))) //
                .andExpect(jsonPath("$.error_description",
                        is("Full authentication is required to access this resource")));
    }

    @Test
    public void shouldReturnUnauthorizedStatusOntFindingCategoryByIdWithInvalidAuthorizationHeader()
            throws Exception {
        mockMvc.perform(get("/categories/59b795074daf991ecaafa265") //
                .with(revokedToken)) //
                .andExpect(status().isUnauthorized()) //
                .andExpect(jsonPath("$.error", is("invalid_token"))) //
                .andExpect(jsonPath("$.error_description",
                        containsString("Invalid access token:")));
    }

    @Test
    public void shouldReturnUnauthorizedStatusOnCreatingNewCategoryWithInvalidAuthorizationHeader()
            throws Exception {
        final Category category = new Category("Child Category 3",
                "some description for child category 3", Status.INACTIVE,
                "59762d7caddb13b4a8440a38");
        category.setParentCategoryId(new ObjectId("59b78ed24daf991ecaafa263"));

        mockMvc.perform(post("/categories") //
                .with(revokedToken) //
                .contentType(MediaType.APPLICATION_JSON) //
                .content(mapper.writeValueAsString(category))) //
                .andExpect(status().isUnauthorized()) //
                .andExpect(jsonPath("$.error", is("invalid_token"))) //
                .andExpect(jsonPath("$.error_description",
                        containsString("Invalid access token:")));
    }

    @Test
    public void shouldReturnUnauthorizedStatusOnDeletingCategoryWithInvalidAuthorizationHeader()
            throws Exception {
        mockMvc.perform(delete("/categories/59b795074daf991ecaafa265") //
                .with(revokedToken)) //
                .andExpect(status().isUnauthorized()) //
                .andExpect(jsonPath("$.error", is("invalid_token"))) //
                .andExpect(jsonPath("$.error_description",
                        containsString("Invalid access token:")));
    }

    @Test
    public void shouldReturnUnauthorizedStatusOnUpdatingExistingCategoryWithInvalidAuthorizationHeader()
            throws Exception {
        final Category category = new Category("Child Category 1 Updated",
                "some updated description for child category 1", Status.ACTIVE,
                "59762d7caddb13b4a8440a38");
        category.setParentCategoryId(new ObjectId("59b78ed24daf991ecaafa263"));

        mockMvc.perform(put("/categories/59b78f244daf991ecaafa264") //
                .with(revokedToken) //
                .contentType(MediaType.APPLICATION_JSON) //
                .content(mapper.writeValueAsString(category))) //
                .andExpect(status().isUnauthorized()) //
                .andExpect(jsonPath("$.error", is("invalid_token"))) //
                .andExpect(jsonPath("$.error_description",
                        containsString("Invalid access token:")));
    }
}
