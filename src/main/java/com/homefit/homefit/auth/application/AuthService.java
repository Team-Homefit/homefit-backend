package com.homefit.homefit.auth.application;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.homefit.homefit.auth.controller.request.IssueCodeRequest;
import com.homefit.homefit.auth.controller.request.VerifyCodeRequest;
import com.homefit.homefit.auth.util.CodeFactory;
import com.homefit.homefit.auth.util.CodeMailTemplateLoader;
import com.homefit.homefit.exception.HomefitException;
import com.homefit.homefit.member.persistence.MemberRepository;
import com.homefit.homefit.member.persistence.po.MemberPo;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class AuthService {
	private final JavaMailSender javaMailSender;
	private final CodeMailTemplateLoader codeMailTemplateLoader;
	private final Map<String, String> codes = new HashMap<>();
	private final MemberRepository memberRepository;
	
	public void sendCodeMail(IssueCodeRequest request) {
		// 회원가입을 위한 이메일 인증은 기가입 확인
		if (request.getIsSignUp()) {
			MemberPo memberPo = memberRepository.selectByUsername(request.getUsername());
			if (memberPo != null) {
				throw new HomefitException(HttpStatus.BAD_REQUEST, "이미 가입된 메일입니다");
			}
		}

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        String code = CodeFactory.create();
        log.info("코드 발급: {}", code);
        
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false, "UTF-8");
            mimeMessageHelper.setTo(request.getUsername());
            mimeMessageHelper.setSubject("이메일 인증 코드");
            mimeMessageHelper.setText(codeMailTemplateLoader.getCodeMail(code), true);
        } catch (MessagingException e) {
			throw new HomefitException(HttpStatus.INTERNAL_SERVER_ERROR, "메일 메시지 생성에 실패했습니다");
		}
        
        try {        	
        	javaMailSender.send(mimeMessage);
        } catch (MailException e) {
        	throw new HomefitException(HttpStatus.INTERNAL_SERVER_ERROR, "메일 전송에 실패했습니다");
		}
        
        codes.put(request.getUsername(), code);
	}
	
	public void verifyCode(VerifyCodeRequest request) {
		String issuedCode = codes.get(request.getUsername());
		
		if (issuedCode == null) {
			throw new HomefitException(HttpStatus.BAD_REQUEST, "인증 코드가 발급되지 않았습니다");
		}
		
		if (!issuedCode.equals(request.getCode())) {
			throw new HomefitException(HttpStatus.BAD_REQUEST, "인증 코드가 일치하지 않습니다");
		}
	}
}