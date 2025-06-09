package com.homefit.homefit.auth.util;

public class CodeFactory {
	private static final int CODE_LENGTH = 6;

	public static String create() {
        StringBuffer code = new StringBuffer();

        for (int i = 0; i < CODE_LENGTH; i++) {
            int value = (int) (Math.random() * 10);
            
            code.append(value);
        }
        
        return code.toString();
	}
}
