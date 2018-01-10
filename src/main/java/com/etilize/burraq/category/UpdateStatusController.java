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

import static com.etilize.burraq.category.CategoryLinks.*;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Houses update status endpoints for category services
 *
 * @author Ebad Hashmi
 * @version 1.0
 *
 */
@RepositoryRestController
public class UpdateStatusController {

    private final CategoryService categoryService;

    /**
     * Constructor to initialize this controller
     *
     * @param categoryService Category Service
     */
    @Autowired
    public UpdateStatusController(final CategoryService categoryService) {
        Assert.notNull(categoryService, "categoryService should not be null.");
        this.categoryService = categoryService;
    }

    /**
     * Updates a status of a category
     *
     * @param category The category whose status will be changed
     * @param request Update Status Request
     * @return {@link ResponseEntity}
     */
    @PutMapping(CATEGORIES_URL + "/{id}/" + REL_UPDATE_STATUS)
    @ResponseBody
    public ResponseEntity<Object> updateStatus(
            @PathVariable(value = "id") final Category category,
            @RequestBody @Valid final UpdateStatusRequest request) {
        if (category == null) {
            throw new ResourceNotFoundException("category not found.");
        }
        categoryService.updateStatus(category, request.getStatus());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
