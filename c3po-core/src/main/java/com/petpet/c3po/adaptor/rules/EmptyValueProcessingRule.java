package com.petpet.c3po.adaptor.rules;

public class EmptyValueProcessingRule implements PreProcessingRule {

  @Override
  public boolean shouldSkip(String property, String value, String status, String tool, String version) {
    if (value.equals("")) {
      return true;
    }
    return false;
  }

  @Override
  public int getPriority() {
    return 1000;
  }

  @Override
  public void onCommandFinished() {
    // do nothing
  }
}
