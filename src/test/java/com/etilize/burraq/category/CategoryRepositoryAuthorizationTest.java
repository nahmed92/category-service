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

import org.bson.types.ObjectId;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;

import com.etilize.burraq.category.test.AbstractIntegrationTest;
import com.etilize.burraq.category.test.security.WithOAuth2Authentication;
import com.lordofthejars.nosqlunit.annotation.UsingDataSet;

/**
 * Houses repository test cases for authentication and authorization
 *
 * @author Uzair Zafar
 * @version 1.0
 *
 */
@UsingDataSet(locations = { "/datasets/categories/categories.bson" })
@WithOAuth2Authentication(username = "ROLE_PTM", clientId = "unauthorized")
public class CategoryRepositoryAuthorizationTest extends AbstractIntegrationTest {

    @Autowired
    private CategoryRepository repository;

    @Test(expected = AccessDeniedException.class)
    public void shouldThrowAccessDeniedExceptionExceptionWhenUnAuthorizedUserCreatesNewCategory()
            throws Exception {
        final Category category = new Category("Child Catgeory 3",
                "some description for child category 3", "59762d7caddb13b4a8440a38");
        category.setStatus(Status.INACTIVE);
        category.setParentCategoryId(new ObjectId("59b78ed24daf991ecaafa263"));
        repository.save(category);
    }

    @Test(expected = AccessDeniedException.class)
    public void shouldThrowAccessDeniedExceptionExceptionWhenUnAuthorizedUserDeletesCategory()
            throws Exception {
        Category category = repository.findOne(new ObjectId("59b795074daf991ecaafa265"));
        repository.delete(category);
    }

    @Test(expected = AccessDeniedException.class)
    public void shouldThrowAccessDeniedExceptionExceptionWhenUnAuthorizedUserUpdatesCategory()
            throws Exception {
        final Category category = new Category("Child Catgeory 1 Updated",
                "some updated description for child category 1",
                "59762d7caddb13b4a8440a38");
        category.setStatus(Status.ACTIVE);
        category.setParentCategoryId(new ObjectId("59b78ed24daf991ecaafa263"));
        category.setId(new ObjectId("59b78f244daf991ecaafa264"));
        repository.save(category);
    }
}
