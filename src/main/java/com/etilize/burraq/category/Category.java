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
import java.util.SortedSet;
import java.util.TreeSet;

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
    private String name;

    @JsonInclude(Include.NON_NULL)
    private String description;

    @NotNull(message = "status is required")
    private Status status = Status.PENDING;

    @NotBlank(message = "industryId is required")
    private String industryId;

    @JsonInclude(Include.NON_NULL)
    private ObjectId parentCategoryId;

    @Valid
    private Set<SpecificationAttribute> specificationAttributes = new HashSet<SpecificationAttribute>();

    @Valid
    private Set<MediaAttribute> mediaAttributes = new HashSet<MediaAttribute>();

    @NotNull(message = "identifiers is required")
    private SortedSet<String> identifiers = new TreeSet<String>();

    /**
     * Constructor used to initialize category
     *
     * @param name stores category name
     * @param description stores category description
     * @param industryId stores industryId of category
     */
    @JsonCreator
    public Category(@JsonProperty("name") final String name,
            @JsonProperty("description") final String description,
            @JsonProperty("industryId") final String industryId) {
        this.name = name;
        this.description = description;
        this.industryId = industryId;
    }

    /**
     * Returns category name
     *
     * @return Name of category
     */
    public String getName() {
        return this.name;
    }

    /**
     * Return category description
     *
     * @return Category description
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Returns category status
     *
     * @return Category {@link Status}
     */
    public Status getStatus() {
        return this.status;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Set description.
     *
     * @param description {@link String} description
     */
    public void setDescription(final String description) {
        this.description = description;
    }

    /**
     * Set industry id.
     *
     * @param industryId {@link String} industry id.
     */
    public void setIndustryId(final String industryId) {
        this.industryId = industryId;
    }

    /**
     * Sets status of a category
     *
     * @param status Category {@link Status}
     */
    public void setStatus(final Status status) {
        this.status = status;
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
     * Sets specificationAttributes in this category
     *
     * @param specificationAttribute {@link SpecificationAttribute}
     */
    public void setSpecificationAttributes(
            final Set<SpecificationAttribute> specificationAttribute) {
        this.specificationAttributes = specificationAttribute;
    }

    /**
     * Returns specificationAttributes in this category
     *
     * @return set of specificationAttributes
     */
    public Set<SpecificationAttribute> getSpecificationAttributes() {
        return this.specificationAttributes;
    }

    /**
     * Sets MediaAttributes in this category
     *
     * @param MediaAttributes {@link MediaAttribute}
     */
    public void setMediaAttributes(final Set<MediaAttribute> mediaAttributes) {
        this.mediaAttributes = mediaAttributes;
    }

    /**
     * Returns {@link MediaAttribute} in this category
     *
     * @return set of {@link MediaAttribute}
     */
    public Set<MediaAttribute> getMediaAttributes() {
        return this.mediaAttributes;
    }

    /**
     * Adds an mediaAttribute to set of mediaAttribute in this class
     *
     * @param mediaAttribute {@link MediaAttribute}
     */
    public void addMediaAttribute(final MediaAttribute mediaAttribute) {
        this.mediaAttributes.add(mediaAttribute);
    }

    /**
     * Adds an specificationAttribute to set of specificationAttribute in this class
     *
     * @param specificationAttribute specificationAttribute to be added
     */
    public void addSpecificationAttribute(
            final SpecificationAttribute specificationAttribute) {
        this.specificationAttributes.add(specificationAttribute);
    }

    /**
     * It return sorted set of attribute Id
     *
     * @return the identifiers
     */
    public SortedSet<String> getIdentifiers() {
        return identifiers;
    }

    /**
     * It sets sorted set of attribute Id
     * @param identifiers the identifiers to set
     */
    public void setIdentifiers(final SortedSet<String> identifiers) {
        this.identifiers = identifiers;
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
