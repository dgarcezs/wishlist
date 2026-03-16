package br.com.clean.wishlist.bdd;

import static io.cucumber.core.options.Constants.GLUE_PROPERTY_NAME;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectPackages("features")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "br.com.clean.wishlist.bdd")
public class CucumberTest {}
