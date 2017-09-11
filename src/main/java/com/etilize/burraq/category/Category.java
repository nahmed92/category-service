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

import java.util.HashSet;
import java.util.Set;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.bson.types.ObjectId;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.mongodb.core.mapping.Document;

import com.etilize.burraq.category.base.AbstractMongoEntity;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Houses details for a Category
 *
 * @author Ebad Hashmi
 * @version 1.0
 *
 */
@Document(collection = Category.COLLECTION_NAME)
public class Category extends AbstractMongoEntity<ObjectId> {

    public static final String COLLECTION_NAME = "categories";

    @NotBlank(message = "name is required")
    private final String name;

    @JsonInclude(Include.NON_NULL)
    private final String description;

    @NotNull(message = "status is required")
    private final Status status;

    @NotBlank(message = "industryId is required")
    private final String industryId;

    @JsonInclude(Include.NON_NULL)
    private ObjectId parentCategoryId;

    @Valid
    private Set<Attribute> attributes = new HashSet<Attribute>();

    /**
     * Constructor used to initialize category
     *
     * @param name stores category name
     * @param description stores category description
     * @param status stores category status
     * @param industryId stores industryId of category
     */
    @JsonCreator
    public Category(@JsonProperty("name") final String name,
            @JsonProperty("description") final String description,
            @JsonProperty("status") final Status status,
            @JsonProperty("industryId") final String industryId) {
        this.name = name;
        this.description = description;
        this.status = status;
        this.industryId = industryId;
    }

    /**
     * Returns category name
     *
     * @return name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Return category description
     *
     * @return description
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Returns category status
     *
     * @return status
     */
    public Status getStatus() {
        return this.status;
    }

    /**
     * Returns industry id to which this category is associated
     *
     * @return industryId
     */
    public String getIndustryId() {
        return this.industryId;
    }

    /**
     * Returns parent category id
     *
     * @return parentCategoryId
     */
    public ObjectId getParentCategoryId() {
        return this.parentCategoryId;
    }

    /**
     * Sets parent category id
     *
     * @param parentCategoryId parent category id
     */
    public void setParentCategoryId(final ObjectId parentCategoryId) {
        this.parentCategoryId = parentCategoryId;
    }

    /**
     * Sets attributes in this category
     *
     * @param attributes this category's attributes
     */
    public void setAttributes(final Set<Attribute> attributes) {
        this.attributes = attributes;
    }

    /**
     * Returns attributes in this category
     *
     * @return attributes
     */
    public Set<Attribute> getAttributes() {
        return this.attributes;
    }

    /**
     * Adds an attribute to set of attributes in this class
     *
     * @param attribute Attribute to be added
     */
    public void addAttribute(final Attribute attribute) {
        this.attributes.add(attribute);
    }

    @Override
    public final boolean equals(final Object object) {
        if (object == null) {
            return false;
        }
        if (object == this) {
            return true;
        }
        if (!(object instanceof Category)) {
            return false;
        }

        final Category category = (Category) object;

        return new EqualsBuilder() //
                .append(getName(), category.getName()) //
                .append(getIndustryId(), category.getIndustryId()) //
                .isEquals();
    }

    @Override
    public final int hashCode() {
        return new HashCodeBuilder() //
                .append(getName()) //
                .append(getIndustryId()) //
                .hashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this) //
                .append("Id", getId()) //
                .append("Name", name) //
                .append("IndustryId", industryId) //
                .toString();
    }

}
