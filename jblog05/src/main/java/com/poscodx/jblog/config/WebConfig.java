package com.poscodx.jblog.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;

import com.poscodx.jblog.config.web.FileuploadConfig;
import com.poscodx.jblog.config.web.MessageSourceConfig;
import com.poscodx.jblog.config.web.MvcConfig;
import com.poscodx.jblog.config.web.SecurityConfig;

@Configuration
@EnableAspectJAutoProxy
@ComponentScan({"com.poscodx.jblog.controller"})
@Import({MvcConfig.class, SecurityConfig.class, FileuploadConfig.class, MessageSourceConfig.class})
public class WebConfig {

}
