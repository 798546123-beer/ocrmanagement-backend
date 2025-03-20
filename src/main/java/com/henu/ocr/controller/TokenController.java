package com.henu.ocr.controller;

import com.henu.ocr.util.JWTUtil;
import com.henu.ocr.util.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/token")
public class TokenController {
    @GetMapping("/getToken")
    public Result getToken(HttpServletRequest request) {
        String token = request.getHeader("token");
        return Result.ok(JWTUtil.refreshToken(token));
    }
}
