package com.homefit.homefit.auth.util;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

@Component
public class CodeMailTemplateLoader {
	private final String codeMail;
	
	public CodeMailTemplateLoader() throws IOException {
		ClassPathResource resource = new ClassPathResource("templates/code-mail.html");
        this.codeMail = new String(resource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
	}
	
	public String getCodeMail(String code) {
		return codeMail.replace("{{AUTH_CODE}}", code);
	}
}
