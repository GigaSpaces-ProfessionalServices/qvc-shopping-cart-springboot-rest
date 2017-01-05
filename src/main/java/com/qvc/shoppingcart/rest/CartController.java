package com.qvc.shoppingcart.rest;

import com.gigaspaces.document.SpaceDocument;
import com.qvc.shoppingcart.common.Cart;
import com.qvc.shoppingcart.service.ICartService;
import org.openspaces.remoting.ExecutorProxy;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@RestController
public class CartController {

  @ExecutorProxy
  private ICartService cartService;

  @RequestMapping(value = "/cart/{id}", method = RequestMethod.GET)
  public String getCart(
          @PathVariable("id") int id,
          HttpServletResponse response
  ) throws IOException
  {
    System.out.println("Fetching cart with id " + id);

    PrintWriter pw = response.getWriter();

    String cartJson = cartService.getCart(id);
    pw.print(cartJson);

    System.out.printf("Cart with id [%d]", id);

    pw.flush();

    pw.close();

    return null;
  }

  @RequestMapping(value = "/scan", method = RequestMethod.GET)
  public String scan(
          HttpServletResponse response
  ) throws IOException
  {
    PrintWriter pw = response.getWriter();

    cartService.scan();
    pw.print("scan complete");

    pw.flush();
    pw.close();

    return null;
  }

  @RequestMapping(value = "/touch/{cartId}", method = RequestMethod.GET)
  public String scan(
          @PathVariable("cartId") Integer cartId,
          HttpServletResponse response
  ) throws IOException
  {
    PrintWriter pw = response.getWriter();

    System.out.printf("CartController => cartId = [%s]\n", cartId);
    cartService.touch(cartId);
    pw.printf("cart [%s] touched", cartId);

    pw.flush();
    pw.close();

    return null;
  }

  @RequestMapping(value = "/cart/{id}", method = RequestMethod.POST)
  public String createCart(
          @PathVariable("id") int id,
          @RequestBody String cartJson,
          HttpServletResponse response
  ) throws IOException
  {
    System.out.printf("cartJson => %s\n", cartJson);

    boolean success = cartService.createCart(id, cartJson);

    PrintWriter pw = response.getWriter();

    if (success) {
      pw.printf("Cart created:\n\n%s", cartJson);
    } else {
      pw.printf("Cart not created:\n\n%s", cartJson);
    }

    pw.flush();
    pw.close();

    return null;
  }

  @RequestMapping(value = "/updatePaymentData/{id}", method = RequestMethod.POST)
  public String updatePaymentData(
          @PathVariable("id") int cartId,
          @RequestBody String paymentJson,
          HttpServletResponse response
  ) throws IOException
  {
    System.out.printf("paymentJson => %s\n", paymentJson);

    PrintWriter pw = response.getWriter();

    try {
      cartService.updatePaymentData(cartId, paymentJson);
      pw.printf("payment info updated for cart %d\n", cartId);
    } catch (Exception e) {
      pw.println("could not update payment data");
    }

    pw.flush();
    pw.close();

    return null;
  }

  // TODO
  private SpaceDocument createPayload(String cartJson) {
    return null;
  }

  // TODO
  private int getCartId(String cartJson) {
    return 0;
  }

  @RequestMapping(value = "/cart/{id}", method = RequestMethod.PUT)
  public String updateCart(
          @PathVariable("id") int id,
          @RequestBody String cartJson,
          HttpServletResponse response
  ) throws IOException
  {

    System.out.println("Updating cart " + id);

    System.out.printf("cartJson => %s\n", cartJson);

    PrintWriter pw = response.getWriter();

    pw.println(cartJson);

    pw.flush();
    pw.close();

    return null;
  }

}
