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
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import org.bson.types.ObjectId;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;

import com.etilize.burraq.category.test.AbstractTest;

public class CategoryResourceProcessorTest extends AbstractTest {

    private Resource<Category> resource;

    @Autowired
    private CategoryResourceProcessor categoryResourceProcessor;

    @Test
    public void shouldAddResourceWithUpdateStatusLink() {
        final Category category = new Category("Category",
                "some description for category", "59762d7caddb13b4a8440a38");
        category.setId(new ObjectId("59b78f244daf991ecaafa264"));
        resource = new Resource<Category>(category,
                new Link("http://localhost/categories/59b78f244daf991ecaafa264"));
        final Resource<Category> result = categoryResourceProcessor.process(resource);
        assertThat(result.hasLink(REL_UPDATE_STATUS), is(true));
        assertThat(result.getLink(REL_UPDATE_STATUS).getHref(),
                endsWith("categories/59b78f244daf991ecaafa264/update_status"));
    }
}
