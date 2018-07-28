package com.topica.sso.auth;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Controller
public class LoginController {
    private static final String jwtTokenCookieName = "JWT-TOKEN";
    private static final Map<String, String> credentials = new HashMap<>();

    public LoginController() {
        credentials.put("huy", "huy");
        credentials.put("dao", "dao");
    }

    @RequestMapping("/")
    public String home() {
        return "redirect:/login";
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public String login(HttpServletResponse httpServletResponse, String username, String password, String redirect, Model model, HttpServletRequest httpServletRequest) {

        if (username == null || !credentials.containsKey(username) || !credentials.get(username).equals(password)) {
            model.addAttribute("error", "Invalid username or password!");
            return "login";
        }

        String token = username;
        CookieUtil.create(httpServletResponse, jwtTokenCookieName, token, false, -1,"localhost");
        return "redirect:" + redirect;
    }
}
