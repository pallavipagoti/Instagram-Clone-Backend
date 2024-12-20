package com.palla.Insta_Clone.Config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SecurityContext {

    @Value("${app.api.key}")
    private String JWT_KEY;
    public static final String HEADER="Authorization";

    public String getJWT_KEY() {
        return JWT_KEY;
    }
}
//abcdefghigyufrytertertrtyy6567567tyfgfgdgfghdgghthtyfytrtyryrytrtyrtyr