package com.qvc.shoppingcart.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@ImportResource("META-INF/spring/pu.xml")
@SpringBootApplication
public class ShoppingCartRestApiApplication {

  public static void main(String[] args) {
    SpringApplication.run(ShoppingCartRestApiApplication.class, args);
  }
}
