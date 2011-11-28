package com.petpet.c3po.utils;

import org.junit.Test;

import com.petpet.c3po.datamodel.BooleanValue;
import com.petpet.c3po.datamodel.IntegerValue;
import com.petpet.c3po.datamodel.PropertyType;
import com.petpet.c3po.datamodel.StringValue;
import com.petpet.c3po.datamodel.Value;
import com.petpet.c3po.utils.Helper;

import junit.framework.Assert;

public class HelperTest {
    
    @Test
    public void shouldGetCorrectValueType() throws Exception {
        Value value = Helper.getTypedValue(PropertyType.BOOL, "true");
        Assert.assertTrue(value instanceof BooleanValue);
        
        value = Helper.getTypedValue(PropertyType.BOOL, "this wont be true");
        Assert.assertTrue(value instanceof BooleanValue);
        Assert.assertFalse(((BooleanValue)value).getTypedValue());
        
        value = Helper.getTypedValue(PropertyType.DEFAULT, "test");
        Assert.assertTrue(value instanceof StringValue);
        
        value = Helper.getTypedValue(PropertyType.STRING, "woot");
        Assert.assertTrue(value instanceof StringValue);
        
        value = Helper.getTypedValue(PropertyType.NUMERIC, "42");
        Assert.assertTrue(value instanceof IntegerValue);
        
        value = Helper.getTypedValue(PropertyType.NUMERIC, "NAN");
        Assert.assertTrue(value instanceof IntegerValue);
        
        value = Helper.getTypedValue(PropertyType.ARRAY, "fail");
        Assert.assertNull(value);
        
    }
}