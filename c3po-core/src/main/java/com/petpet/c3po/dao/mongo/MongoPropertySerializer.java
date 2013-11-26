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
package com.petpet.c3po.dao.mongo;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.petpet.c3po.api.model.Property;

/**
 * Serializes a {@link Property} object to a mongo {@link DBObject}.
 * 
 * @author Petar Petrov <me@petarpetrov.org>
 * 
 */
public class MongoPropertySerializer implements MongoModelSerializer {

  /**
   * Serializes the given {@link Property} object to a mongo NoSQL document.
   * Note that if the object is null or not of type {@link Property}, null is
   * returned.
   */
  @Override
  public DBObject serialize( Object object ) {
    BasicDBObject property = null;

    if ( object != null && object instanceof Property ) {
      Property p = (Property) object;

      property = new BasicDBObject();
      if ( p.getId() != null && !p.getId().equals( "" ) ) {
        property.put( "_id", p.getId() );
      }

      if ( p.getKey() != null && !p.getKey().equals( "" ) ) {
        property.put( "key", p.getKey() );
      }

      if ( p.getType() != null && !p.getType().equals( "" ) ) {
        property.put( "type", p.getType() );
      }

    }
    return property;
  }

}
