package com.zy.mylib.security.casbin;

/**
 * casbin model and policy
 */
public class ModelAndPolicy {
  private String model;
  private String policy;

  /**
   * Gets model.
   *
   * @return Value of model.
   */
  public String getModel() {
    return model;
  }

  /**
   * Sets new model.
   *
   * @param model New value of model.
   */
  public void setModel(String model) {
    this.model = model;
  }

  /**
   * Sets new policy.
   *
   * @param policy New value of policy.
   */
  public void setPolicy(String policy) {
    this.policy = policy;
  }

  /**
   * Gets policy.
   *
   * @return Value of policy.
   */
  public String getPolicy() {
    return policy;
  }
}
