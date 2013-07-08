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
package com.petpet.c3po.parameters.validation;

import com.beust.jcommander.IValueValidator;
import com.beust.jcommander.ParameterException;

/**
 * A empty string validator for the passed command line parameters.
 * 
 * @author Petar Petrov <me@petarpetrov.org>
 * 
 */
public class EmptyStringValidator implements IValueValidator<String> {

  /**
   * @throws ParameterException
   *           if the value is null or an empty string.
   */
  @Override
  public void validate( String name, String val ) throws ParameterException {
    if ( val == null || val.equals( "" ) ) {
      throw new ParameterException( "The value of the " + name + " option cannot be empty" );
    }
  }

}
