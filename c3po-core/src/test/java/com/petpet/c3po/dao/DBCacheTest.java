/*******************************************************************************
 * Copyright 2013 Petar Petrov <me@petarpetrov.org>
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package com.petpet.c3po.dao;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Iterator;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import com.petpet.c3po.api.dao.PersistenceLayer;
import com.petpet.c3po.api.model.Property;
import com.petpet.c3po.api.model.Source;
import com.petpet.c3po.api.model.helper.Filter;
import com.petpet.c3po.utils.DataHelper;

/**
 * Tests the {@link DBCache} class.
 * 
 * @author Petar Petrov <me@petarpetrov.org>
 * 
 */
public class DBCacheTest {

  private DBCache cache;
  private PersistenceLayer pl;
  private Iterator cursor;

  @BeforeClass
  public static void beforeAllTests() {
    DataHelper.init();
  }

  @Before
  public void setup() {

    pl = mock(PersistenceLayer.class);
    cursor = mock(Iterator.class);

    when(pl.find(Mockito.eq(Property.class), Mockito.any(Filter.class))).thenReturn(cursor);
    when(pl.find(Mockito.eq(Source.class), Mockito.any(Filter.class))).thenReturn(cursor);

    this.cache = new DBCache();
    this.cache.setPersistence(this.pl);

  }

  @Test
  public void shouldTestCacheMissAndDBMiss() {
    when(cursor.hasNext()).thenReturn(false);

    this.cache.clear();

    String key = "test";
    Property property = this.cache.getProperty(key);

    Assert.assertNotNull(property);
    Assert.assertNotNull(property.getId());
    Assert.assertEquals(key, property.getKey());
    verify(pl, Mockito.times(1)).find(Mockito.eq(Property.class), Mockito.any(Filter.class));

  }

  @Test
  public void shouldTestCacheMissAndDBHit() {
    when(cursor.hasNext()).thenReturn(true, false);
    when(cursor.next()).thenReturn(Mockito.mock(Property.class));

    this.cache.clear();

    String key = "test";
    Property property = this.cache.getProperty(key);

    Assert.assertNotNull(property);
    verify(pl, Mockito.times(1)).find(Mockito.eq(Property.class), Mockito.any(Filter.class));
  }

  @Test
  public void shouldTestCacheHit() {
    // when(cursor.hasNext()).thenReturn(false);

    this.cache.clear();

    Source s = this.cache.getSource("test", "v0.1");
    s = this.cache.getSource("test", "v0.1");

    Assert.assertNotNull(s);
    Assert.assertNotNull(s.getId());

    verify(pl, Mockito.times(1)).find(Mockito.eq(Source.class), Mockito.any(Filter.class));

  }

  @Test(expected = RuntimeException.class)
  public void shouldThrowAnException() {
    // this will return a second hit in the db
    // which signals a faulty state.
    when(cursor.hasNext()).thenReturn(true);

    this.cache.clear();
    this.cache.getSource("test", "v0.1");

    Assert.fail("This code should not have been reached, failing test");
  }

  @Test
  public void shouldTestCacheClear() {
    this.cache.clear();

    String key = "test";
    Object test = this.cache.getObject(key);
    Assert.assertNull(test);

    this.cache.put(key, new Object());

    test = this.cache.getObject(key);
    Assert.assertNotNull(test);

    this.cache.clear();

    test = this.cache.getObject(key);
    Assert.assertNull(test);
  }

}
