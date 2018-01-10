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

import org.junit.Test;
import org.springframework.http.MediaType;

import com.etilize.burraq.category.test.AbstractRestIntegrationTest;
import com.lordofthejars.nosqlunit.annotation.ShouldMatchDataSet;
import com.lordofthejars.nosqlunit.annotation.UsingDataSet;

/**
 * Houses rest integration tests for Update status controller
 *
 * @author Ebad Hashmi
 *
 */
@UsingDataSet(locations = { "/datasets/categories/categories.bson" })
public class UpdateStatusRestIntegrationTests extends AbstractRestIntegrationTest {

    @Test
    @ShouldMatchDataSet(location = "/datasets/categories/categories_after_update_status.json")
    public void shouldUpdateStatusOfCategoryById() throws Exception {
        final String updateStatusRequest = "{\"status\":\"ACTIVE\"}";
        mockMvc.perform(put("/categories/59b78f244daf991ecaafa264/update_status") //
                .with(bearerToken) //
                .contentType(MediaType.APPLICATION_JSON) //
                .content(updateStatusRequest)) //
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldReturnBadRequestWhenStatusIsNotProvided() throws Exception {
        final String updateStatusRequest = "{}";
        mockMvc.perform(put("/categories/59b78ed24daf991ecaafa263/update_status") //
                .with(bearerToken) //
                .contentType(MediaType.APPLICATION_JSON) //
                .content(updateStatusRequest)) //
                .andExpect(status().isBadRequest()) //
                .andExpect(jsonPath("$.errors", hasSize(1))) //
                .andExpect(jsonPath("$.errors[0].message", is("status is required")));
    }

    @Test
    public void shouldReturnBadRequestWhenInvalidStatusIsProvided() throws Exception {
        final String updateStatusRequest = "{\"status\":\"INFACTIVE\"}";
        mockMvc.perform(put("/categories/59b78ed24daf991ecaafa263/update_status") //
                .with(bearerToken) //
                .contentType(MediaType.APPLICATION_JSON) //
                .content(updateStatusRequest)) //
                .andExpect(status().isBadRequest()) //
                .andExpect(jsonPath("$.message", containsString(
                        "value not one of declared Enum instance names: [INACTIVE, ACTIVE, PENDING]")));
    }

    @Test
    public void shouldReturnBadRequestWhenInvalidHexStringIsProvidedAsId()
            throws Exception {
        final String updateStatusRequest = "{\"status\":\"INACTIVE\"}";
        mockMvc.perform(put("/categories/59b78ed2daf991ecaafa263/update_status") //
                .with(bearerToken) //
                .contentType(MediaType.APPLICATION_JSON) //
                .content(updateStatusRequest)) //
                .andExpect(status().isBadRequest()) //
                .andExpect(jsonPath("$.message", containsString(
                        "invalid hexadecimal representation of an ObjectId: [59b78ed2daf991ecaafa263]")));
    }

    @Test
    public void shouldReturnBadRequestWhenCategoryDoesNotExist() throws Exception {
        final String updateStatusRequest = "{\"status\":\"INACTIVE\"}";
        mockMvc.perform(put("/categories/59db57dc3ac1ae4216119282/update_status") //
                .with(bearerToken) //
                .contentType(MediaType.APPLICATION_JSON) //
                .content(updateStatusRequest)) //
                .andExpect(status().isNotFound());
    }

}
