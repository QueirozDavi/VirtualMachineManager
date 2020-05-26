package com.ustore.virtualmachinemanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class VirtualMachineManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(VirtualMachineManagerApplication.class, args);
	}

}
