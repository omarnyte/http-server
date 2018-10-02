package com.omarnyte.stepdefinitions;

import cucumber.api.java.en.Then;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class ResponseHeaderStepDefs {
  private World world;

  public ResponseHeaderStepDefs(World world) {
    this.world = world;
  }

  @Then("^the server should respond with the header ([A-Za-z-]+) (.+)$")
  public void the_server_should_respond_with_the_header(String field, String expectedValue) throws Throwable {
    this.world.con.getHeaderField(field);
    assertEquals(expectedValue, this.world.con.getHeaderField(field));
  }

  @Then("^the response should contain the header (.+)$")
  public void the_response_should_contain_the_header_Location(String field) throws Throwable {
    assertFalse(this.world.con.getHeaderField(field) == null);
  }

}
