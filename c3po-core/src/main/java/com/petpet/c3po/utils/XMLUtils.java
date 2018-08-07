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
package com.petpet.c3po.utils;

import java.io.File;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import com.petpet.c3po.common.Constants;

public final class XMLUtils {

  private static final Logger LOG = LoggerFactory.getLogger( XMLUtils.class );

  private static SAXParserFactory factory;

  public static void init() {
    XMLUtils.factory = SAXParserFactory.newInstance();
    factory.setValidating( true );
    factory.setNamespaceAware( true );
  }

  public static boolean validate( File f ) {
    try {
      SAXParser parser = factory.newSAXParser();
      parser.setProperty( Constants.XML_SCHEMA_PROPERTY, Constants.XML_SCHEMA_LANGUAGE );

      SimpleErrorHandler errorHandler = new SimpleErrorHandler();

      SAXReader reader = new SAXReader( parser.getXMLReader() );
      reader.setValidation( true );

      reader.setErrorHandler( errorHandler );
      reader.read( f );

      return errorHandler.isValid();

    } catch ( ParserConfigurationException e ) {
      LOG.error( "ParserConfigurationException: {}", e.getMessage() );
    } catch ( SAXException e ) {
      LOG.error( "SAXException: {}", e.getMessage() );
    } catch ( DocumentException e ) {
      LOG.error( "DocumentException: {}", e.getMessage() );
    } catch ( NullPointerException e ) {
      LOG.warn( "Factory is not initialized. Did you call init()" );
    }

    return false;
  }

  private XMLUtils() {}

  private static class SimpleErrorHandler implements ErrorHandler {
    private boolean valid;

    public SimpleErrorHandler() {
      this.valid = true;
    }

    @Override
    public void error( SAXParseException e ) {
      LOG.error( "Error: {}", e.getMessage() );
      this.valid = false;
    }

    @Override
    public void fatalError( SAXParseException e ) {
      LOG.error( "Fatal Error: {}", e.getMessage() );
      this.valid = false;

    }

    @Override
    public void warning( SAXParseException e ) {
      LOG.error( "Warning: {}", e.getMessage() );

    }

    public boolean isValid() {
      return this.valid;
    }

  }
}
