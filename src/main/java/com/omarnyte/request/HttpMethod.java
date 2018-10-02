package com.omarnyte.request;

import java.util.Arrays;
import java.util.List;

public class HttpMethod {
  public final static String DELETE = "DELETE";
  public final static String GET = "GET";
  public final static String HEAD = "HEAD";
  public final static String OPTIONS = "OPTIONS";
  public final static String PATCH = "PATCH";
  public final static String POST = "POST";
  public final static String PUT = "PUT";

  public static final List<String> SUPPORTED_METHODS = Arrays.asList( 
    DELETE, 
    GET, 
    HEAD, 
    OPTIONS,
    PATCH, 
    POST, 
    PUT
  );

}
