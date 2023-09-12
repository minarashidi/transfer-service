package com.rashidi.transferservice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;

@SpringBootApplication
@EnableJdbcRepositories
@Slf4j
public class Main {

  public static void main(final String[] args) {
    SpringApplication.run(Main.class, args);
  }
}
