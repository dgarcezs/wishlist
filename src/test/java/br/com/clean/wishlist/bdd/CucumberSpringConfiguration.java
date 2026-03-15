package br.com.clean.wishlist.bdd;

import br.com.clean.wishlist.WishlistApplication;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;

@CucumberContextConfiguration
@SpringBootTest(
    classes = WishlistApplication.class,
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
@ActiveProfiles("test")
public class CucumberSpringConfiguration {}
