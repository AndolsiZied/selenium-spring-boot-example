package de.computerlyrik.selenium;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.server.SeleniumServer;
import org.openqa.selenium.server.htmlrunner.HTMLLauncher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ApplicationTask {

  private static final Logger log = LoggerFactory.getLogger(ApplicationTask.class);

  private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

  @Autowired
  private ApplicationService service;

  @Autowired
  private TestConfig config;

  private Integer timeOutInSeconds = 60;
  private Boolean multiWindow = true;

  @Scheduled(fixedRate = 90000)
  public void checkBooking8881() throws UnsupportedEncodingException {
    log.info(">>>check antony 8881, the time is now {}", dateFormat.format(new Date()));
    String url = "http://www.hauts-de-seine.gouv.fr/booking/create/8881/0";
    SeleniumServer server = null;
    try {
      server = new SeleniumServer();
      server.start();

      String result;
      int cpt = 0;
      do {
        result = runTestSuite(server, config.getSuites().get(0));
        Thread.sleep(30000);
        cpt++;
      } while (!"PASSED".equals(result) && cpt < 3);

      boolean isClosed = "PASSED".equals(result);
      log.info(">>>antony is closed : " + isClosed);
      if (!isClosed) {
       service.sendEmailWithoutTemplating("Selenium test : The calendar is now available. check url : " + url);
      }

    } catch (IOException e_io) {
      // TODO Auto-generated catch block
      e_io.printStackTrace();
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    finally {
      if(server !=null) {
        server.stop();
      }
    }
  }

  private String runTestSuite(SeleniumServer server, String suite) throws IOException {
    HTMLLauncher launcher = new HTMLLauncher(server);
    File results = new File("results_" + suite + ".html");
    String suite_url = "/selenium-server/selenium/" + suite + ".html";
    return launcher.runHTMLSuite(config.getBrowser(), config.getStartUrl(), suite_url, results, timeOutInSeconds,
        multiWindow);
  }
}
