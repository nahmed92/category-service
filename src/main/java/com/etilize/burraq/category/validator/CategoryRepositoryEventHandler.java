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

package com.etilize.burraq.category.validator;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.etilize.burraq.category.SpecificationAttribute;
import com.etilize.burraq.category.Category;

/**
 * Category Event Handler
 *
 * @author Nasir Ahmed
 *
 */
@Component
@RepositoryEventHandler
public class CategoryRepositoryEventHandler {

    /**
     * Validates attribute order must not exist
     * repeatedly for create and update requests
     *
     * @param category entity
     */
    @HandleBeforeCreate(Category.class)
    @HandleBeforeSave(Category.class)
    public void handleBeforeCategorySave(final Category category) {
        if (!category.getSpecificationAttributes().isEmpty()) {
            final Set<Integer> orders = new HashSet<>();
            final Set<SpecificationAttribute> duplicates = category.getSpecificationAttributes().stream().filter(
                    // Add order in Set return false if already exist
                    // the return Attribute having duplicate order collect in duplicates
                    n -> !orders.add(n.getOrder())).collect(Collectors.toSet());
            Assert.isTrue(duplicates.isEmpty(), "attribute order can't be repeated.");
        }
    }
}
