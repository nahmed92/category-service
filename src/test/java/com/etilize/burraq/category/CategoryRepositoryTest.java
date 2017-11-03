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

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import java.util.List;

import javax.validation.ConstraintViolationException;

import org.bson.types.ObjectId;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;

import com.etilize.burraq.category.test.AbstractIntegrationTest;
import com.etilize.burraq.category.test.security.WithOAuth2Authentication;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.collect.Sets;
import com.lordofthejars.nosqlunit.annotation.ShouldMatchDataSet;
import com.lordofthejars.nosqlunit.annotation.UsingDataSet;
import com.querydsl.core.types.Predicate;

/**
 * Houses repository test cases
 *
 * @author Ebad Hashmi
 * @version 1.0
 *
 */
@UsingDataSet(locations = { "/datasets/categories/categories.bson" })
@WithOAuth2Authentication(username = "ROLE_PTM")
public class CategoryRepositoryTest extends AbstractIntegrationTest {

    @Autowired
    private CategoryRepository repository;

    @Test
    public void shouldFindAllCategories() throws Exception {
        final List<Category> categories = repository.findAll();
        assertThat(categories, hasSize(3));
        for (final Category category : categories) {
            assertThat(category.getName(), anyOf(is("Parent Category"),
                    is("Child Category 1"), is("Child Category 2")));
            assertThat(category.getIndustryId(), is("59762d7caddb13b4a8440a38"));
            assertThat(category.getStatus(),
                    anyOf(is(Status.ACTIVE), is(Status.INACTIVE), is(Status.PENDING)));
        }
    }

    @Test
    @ShouldMatchDataSet(location = "/datasets/categories/categories_after_create.bson")
    public void shouldCreateNewCategory() throws Exception {
        final Category category = new Category("Child Category 3",
                "some description for child category 3", Status.INACTIVE,
                "59762d7caddb13b4a8440a38");
        category.setParentCategoryId(new ObjectId("59b78ed24daf991ecaafa263"));
        repository.save(category);
    }

    @Test
    @ShouldMatchDataSet(location = "/datasets/categories/categories_after_create_with_attributes.bson")
    public void shouldCreateNewCategoryWithAttributes() throws Exception {
        final Category category = new Category("Child Category 3",
                "some description for child category 3", Status.INACTIVE,
                "59762d7caddb13b4a8440a38");
        category.setParentCategoryId(new ObjectId("59b78ed24daf991ecaafa263"));
        category.setAttributes(
                Sets.newHashSet(new Attribute("attributeId-1", Source.SELF, 1)));
        repository.save(category);
    }

    @Test
    @ShouldMatchDataSet(location = "/datasets/categories/categories_after_update.bson")
    public void shouldUpdateCategory() throws JsonProcessingException, Exception {
        final Category category = repository.findOne(
                new ObjectId("59b78f244daf991ecaafa264"));
        final Category updatedCategory = new Category("Child Category 1 Updated",
                "some updated description for child category 1", Status.ACTIVE,
                "59762d7caddb13b4a8440a38");
        updatedCategory.setParentCategoryId(category.getParentCategoryId());
        updatedCategory.setId(category.getId());
        updatedCategory.setCreatedBy(category.getCreatedBy());
        updatedCategory.setCreatedDate(category.getCreatedDate());
        repository.save(updatedCategory);
    }

    @Test
    @ShouldMatchDataSet(location = "/datasets/categories/categories_after_delete.bson")
    public void shouldDeleteCategoryById() throws Exception {
        repository.delete(new ObjectId("59b795074daf991ecaafa265"));
    }

    @Test(expected = ConstraintViolationException.class)
    public void shouldThrowConstraintViolationExceptionWhenNameIsNotPresent()
            throws JsonProcessingException, Exception {
        final Category category = new Category(null,
                "some description for child category 3", Status.INACTIVE,
                "59762d7caddb13b4a8440a38");
        category.setParentCategoryId(new ObjectId("59b78ed24daf991ecaafa263"));
        repository.save(category);
    }

    @Test(expected = ConstraintViolationException.class)
    public void shouldThrowConstraintViolationExceptionWhenStatusIsNotPresent()
            throws JsonProcessingException, Exception {
        final Category category = new Category("Category",
                "some description for child category 3", null,
                "59762d7caddb13b4a8440a38");
        category.setParentCategoryId(new ObjectId("59b78ed24daf991ecaafa263"));
        repository.save(category);
    }

    @Test(expected = ConstraintViolationException.class)
    public void shouldThrowConstraintViolationExceptionWhenIndustryIdIsNotPresent()
            throws JsonProcessingException, Exception {
        final Category category = new Category("Category",
                "some description for child category 3", Status.INACTIVE, null);
        category.setParentCategoryId(new ObjectId("59b78ed24daf991ecaafa263"));
        repository.save(category);
    }

    @Test(expected = ConstraintViolationException.class)
    public void shouldThrowConstraintViolationExceptionWhenAttributeIdIsNotPresent()
            throws JsonProcessingException, Exception {
        final Category category = new Category("Category",
                "some description for child category 3", Status.INACTIVE,
                "59762d7caddb13b4a8440a38");
        category.setParentCategoryId(new ObjectId("59b78ed24daf991ecaafa263"));
        category.setAttributes(Sets.newHashSet(new Attribute(null, Source.SELF, 1)));
        repository.save(category);
    }

    @Test(expected = ConstraintViolationException.class)
    public void shouldThrowConstraintViolationExceptionWhenAttributeSourceIsNotPresent()
            throws JsonProcessingException, Exception {
        final Category category = new Category("Category",
                "some description for child category 3", Status.INACTIVE,
                "59762d7caddb13b4a8440a38");
        category.setParentCategoryId(new ObjectId("59b78ed24daf991ecaafa263"));
        category.setAttributes(Sets.newHashSet(new Attribute("attributeId-1", null, 1)));
        repository.save(category);
    }

    @Test(expected = ConstraintViolationException.class)
    public void shouldThrowConstraintViolationExceptionWhenAttributeOrderIsNotPresent()
            throws JsonProcessingException, Exception {
        final Category category = new Category("Category",
                "some description for child category 3", Status.INACTIVE,
                "59762d7caddb13b4a8440a38");
        category.setParentCategoryId(new ObjectId("59b78ed24daf991ecaafa263"));
        category.setAttributes(
                Sets.newHashSet(new Attribute("attributeId-1", Source.SELF, null)));
        repository.save(category);
    }

    @Test(expected = DuplicateKeyException.class)
    public void shouldThrowDuplicateKeyExceptionWhenCategoryNameIsAlreadyPresent()
            throws JsonProcessingException, Exception {
        final Category category = new Category("Parent Category",
                "some description for child category 3", Status.INACTIVE,
                "59762d7caddb13b4a8440a38");
        category.setParentCategoryId(new ObjectId("59b78ed24daf991ecaafa263"));
        repository.save(category);
    }

    @Test(expected = DuplicateKeyException.class)
    public void shouldThrowDuplicateKeyExceptionWhenCaseInsensitiveCategoryNameIsAlreadyPresent()
            throws JsonProcessingException, Exception {
        final Category category = new Category("PARENT CATEGORY",
                "some description for child category 3", Status.INACTIVE,
                "59762d7caddb13b4a8440a38");
        category.setParentCategoryId(new ObjectId("59b78ed24daf991ecaafa263"));
        repository.save(category);
    }

    @Test
    public void shouldFindCategoryByNameUsingQueryDslPredicate() {
        QCategory qCategory = new QCategory("name");
        Predicate predicate = qCategory.name.startsWith("Child Category 1");
        List<Category> category = (List<Category>) repository.findAll(predicate);
        assertThat(category.size(), is(1));
        assertThat(category.get(0).getName(), is("Child Category 1"));
        assertThat(category.get(0).getDescription(),
                is("some description for child category 1"));
        assertThat(category.get(0).getStatus(), is(Status.INACTIVE));
        assertThat(category.get(0).getIndustryId(), is("59762d7caddb13b4a8440a38"));
        assertThat(category.get(0).getParentCategoryId(),
                is(new ObjectId("59b78ed24daf991ecaafa263")));
    }
}
