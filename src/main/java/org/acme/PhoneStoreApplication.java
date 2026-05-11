package org.acme;

import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;

@QuarkusMain
public class PhoneStoreApplication implements QuarkusApplication {

  public static void main(String... args) {
    Quarkus.run(PhoneStoreApplication.class, args);
  }

  @Override
  public int run(String... args) throws Exception {
    System.out.print("Website running in http://localhost:8080/");
    Quarkus.waitForExit();
    return 0;
  }
}
