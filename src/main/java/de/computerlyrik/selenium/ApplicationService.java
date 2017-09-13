package de.computerlyrik.selenium;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

import javax.mail.internet.InternetAddress;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

import it.ozimov.springboot.templating.mail.model.Email;
import it.ozimov.springboot.templating.mail.model.impl.EmailImpl;
import it.ozimov.springboot.templating.mail.service.EmailService;

@Service
public class ApplicationService {

  private static final Logger log = LoggerFactory.getLogger(ApplicationService.class);

  @Autowired
  private EmailService emailService;

  public void sendEmailWithoutTemplating(String body) throws UnsupportedEncodingException {
    log.debug(">>>sending email {}", body);
    final Email email = EmailImpl.builder().from(new InternetAddress("app.ws.client@gmail.com", "App client WS"))
        .to(Lists.newArrayList(new InternetAddress("landolsi.zied@gmail.com", "Zied ANDOLSI"),
            new InternetAddress("mejri.fatma@gmail.com", "Fatma MEJRI")))
        .subject("Selenium Test : Check the availability of an appointment").body(body).encoding(Charset.forName
            ("UTF-8")).build();
    emailService.send(email);
  }
}
