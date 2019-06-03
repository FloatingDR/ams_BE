package cn.cafuc.flyeat.sb.dormitorymanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@SpringBootApplication
public class DormitorymanagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(DormitorymanagementApplication.class, args);
    }

}

