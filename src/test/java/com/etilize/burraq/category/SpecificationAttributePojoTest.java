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

import org.apache.commons.lang3.ObjectUtils;
import org.junit.Test;

import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.validation.Validator;
import com.openpojo.validation.ValidatorBuilder;
import com.openpojo.validation.rule.impl.GetterMustExistRule;
import com.openpojo.validation.rule.impl.NoPublicFieldsExceptStaticFinalRule;
import com.openpojo.validation.rule.impl.NoStaticExceptFinalRule;
import com.openpojo.validation.rule.impl.SerializableMustHaveSerialVersionUIDRule;
import com.openpojo.validation.rule.impl.SetterMustExistRule;
import com.openpojo.validation.test.impl.GetterTester;
import com.openpojo.validation.test.impl.SetterTester;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

/**
 * Attribute pojo test class
 *
 * @author Ebad Hashmi
 *
 */
public class SpecificationAttributePojoTest {

    @Test
    public void shouldFollowEqualsContract() {
        EqualsVerifier.forClass(SpecificationAttribute.class) //
                .suppress(Warning.ALL_FIELDS_SHOULD_BE_USED) //
                .suppress(Warning.NONFINAL_FIELDS) //
                .verify();
    }

    @Test
    public void hasToString() {
        final SpecificationAttribute attribute = new SpecificationAttribute("1636752",
                Source.INHERITED, 1);
        assertThat(ObjectUtils.identityToString(attribute), not(attribute.toString()));
    }

    @Test
    public void validateAttributePojo() {
        final PojoClass attributePojo = PojoClassFactory.getPojoClass(
                SpecificationAttribute.class);
        final Validator validator = ValidatorBuilder.create() //
                .with(new GetterMustExistRule()) //
                .with(new SetterMustExistRule()) //
                .with(new GetterTester()) //
                .with(new SetterTester()) //
                .with(new NoPublicFieldsExceptStaticFinalRule()) //
                .with(new NoStaticExceptFinalRule()) //
                .with(new SerializableMustHaveSerialVersionUIDRule()) //
                .build();
        validator.validate(attributePojo);
    }

}
