package com.example;

import java.nio.file.Paths;

import static spark.Spark.post;
import static spark.Spark.port;
import static spark.Spark.staticFiles;

import com.stripe.Stripe;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;

public class Server {

  public static void main(String[] args){
    port(4242);
    System.out.println("test");
    // This is your test secret API key.
    Stripe.apiKey = "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";
    //sk_test_51LuddYDxcu02TXZDdYee4LJyGx3ddIgNM7oon2m5LNLbFm5xLhEObgnMl0KUOwgIvcjCilB9nwr8yGjKwtyfQ37N00jTYq2Y2T
    //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
    staticFiles.externalLocation(
        Paths.get("public").toAbsolutePath().toString());



    post("/create-checkout-session", (request, response) -> {
        String price_id = "";
        String YOUR_DOMAIN = "https://maelarr.github.io";
        String paramFirebase = request.body().split("&price")[0];
        String paramPrice = request.body().split("&price=")[1];
        System.out.println("paramFirebase : "+paramFirebase);
        System.out.println("paramPrice : "+paramPrice);
        switch(paramPrice){
            case "7.00":
                price_id = "price_1LutpsDxcu02TXZDp297rarV";
                break;
            case "29.00":
                price_id = "price_1LutqoDxcu02TXZD8sANsnPg";
                break;
            case "11.00":
                price_id = "price_1Lutq1Dxcu02TXZDLjmMWwmA";
                break;
            case "47.00":
                price_id = "price_1LutoqDxcu02TXZDURByYhuD";
                break;
            case "18.00":
                price_id = "price_1LutqHDxcu02TXZDRuifB8O4";
                break;
            case "40.00":
                price_id = "price_1LutqdDxcu02TXZD5Zzr2tD8";
                break;
            case "36.00":
                price_id = "price_1Lutr8Dxcu02TXZDoAeENKut";
                break;
        }
        System.out.println("Price : "+price_id);
        SessionCreateParams params =
          SessionCreateParams.builder()
            .setMode(SessionCreateParams.Mode.PAYMENT)
            .setSuccessUrl(YOUR_DOMAIN + "/success.html?"+paramFirebase)
            .setCancelUrl(YOUR_DOMAIN + "/cancel.html?"+paramFirebase)
            .setAutomaticTax(
              SessionCreateParams.AutomaticTax.builder()
                .setEnabled(true)
                .build())
            .addLineItem(
              SessionCreateParams.LineItem.builder()
                .setQuantity(1L)
                // Provide the exact Price ID (for example, pr_1234) of the product you want to sell
                .setPrice(price_id)
                .build())
            .build();
      Session session = Session.create(params);

      response.redirect(session.getUrl(), 303);
      return "";
    });
  }
}