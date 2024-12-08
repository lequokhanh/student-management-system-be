package com.example.smsbe.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/class")
@SecurityRequirement(name = "bearerAuth")
public class ClassController {

}
