package se.sowl.roomitapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EntityScan(basePackages = {"se.sowl.roomitdomain"})
@EnableCaching
public class RoomItApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(RoomItApiApplication.class, args);
    }

}
