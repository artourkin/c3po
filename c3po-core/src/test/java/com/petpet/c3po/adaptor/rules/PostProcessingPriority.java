package com.petpet.c3po.adaptor.rules;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Test;
import org.mockito.Mockito;

import com.petpet.c3po.adaptor.AbstractAdaptor;
import com.petpet.c3po.datamodel.Element;

public class PostProcessingPriority {

  @Test
  public void shouldOrderProcessingRulesInAscendingOrder() throws Exception {

    TestAdaptor a = new TestAdaptor();
    PostProcessingRule ppr1 = Mockito.mock(PostProcessingRule.class);
    PostProcessingRule ppr2 = Mockito.mock(PostProcessingRule.class);

    Mockito.when(ppr1.getPriority()).thenReturn(42);
    Mockito.when(ppr2.getPriority()).thenReturn(666);

    List<ProcessingRule> rules = new ArrayList<ProcessingRule>();
    rules.add(ppr1);
    rules.add(ppr2);

    a.setRules(rules);

    Assert.assertEquals(42, rules.get(0).getPriority());
    Assert.assertEquals(666, rules.get(1).getPriority());

    rules = a.getRules();

    Assert.assertEquals(666, rules.get(0).getPriority());
    Assert.assertEquals(42, rules.get(1).getPriority());
  }

  @Test
  public void shouldGetRulesOfType() throws Exception {
    TestAdaptor a = new TestAdaptor();
    PreProcessingRule ppr1 = new PreProcessingRule() {

      @Override
      public int getPriority() {
        return 1;
      }

      @Override
      public boolean shouldSkip(String property, String value, String status, String tool, String version) {
        return false;
      }
      
      @Override
      public void onCommandFinished() {

      }
    };

    PostProcessingRule ppr2 = new PostProcessingRule() {

      @Override
      public int getPriority() {
        return 1;
      }

      @Override
      public Element process(Element e) {
        return e;
      }
      
      @Override
      public void onCommandFinished() {

      }
    };

    List<ProcessingRule> rules = new ArrayList<ProcessingRule>();
    rules.add(ppr1);
    rules.add(ppr2);

    a.setRules(rules);

    List<PreProcessingRule> prerules = a.getPreProcessingRules();
    Assert.assertEquals(1, prerules.size());

  }

  private class TestAdaptor extends AbstractAdaptor {

    @Override
    public void run() {
    }

    @Override
    public void configure(Map<String, Object> config) {
    }

    public List<ProcessingRule> getRules() {
      return super.getRules();
    }

    public List<PreProcessingRule> getPreProcessingRules() {
      return super.getPreProcessingRules();
    }

  }
}
