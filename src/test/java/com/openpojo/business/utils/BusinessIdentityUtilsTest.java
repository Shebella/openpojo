/*
 * Copyright (c) 2010-2014 Osman Shoukry
 *
 *    This program is free software: you can redistribute it and/or modify
 *    it under the terms of the GNU Lesser General Public License as published by
 *    the Free Software Foundation, either version 3 of the License or any
 *    later version.
 *
 *    This program is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU Lesser General Public License for more details.
 *
 *    You should have received a copy of the GNU Lesser General Public License
 *    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.openpojo.business.utils;

import junit.framework.Assert;

import org.junit.Test;

import com.openpojo.business.exception.BusinessException;

public class BusinessIdentityUtilsTest {

    @Test
    public void shouldThrowBusinessExceptionWhenNullParameter() {
        try {
            BusinessIdentityUtils.anyNull((Object[]) null);
            Assert.fail("Expected BusinessException not thrown");
        } catch (final BusinessException be) {
            Assert.assertEquals("objects parameter cannot be null", be.getMessage());
        }
    }
}
