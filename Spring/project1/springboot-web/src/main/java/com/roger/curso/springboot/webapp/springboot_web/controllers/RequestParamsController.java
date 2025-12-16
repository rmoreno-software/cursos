package com.roger.curso.springboot.webapp.springboot_web.controllers;

import com.roger.curso.springboot.webapp.springboot_web.models.dtos.ParamDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/params")
public class RequestParamsController {

    @GetMapping("/foo")
    public ParamDto foo(@RequestParam(required = false, defaultValue = "Hola") String message) {
        ParamDto paramDto = new ParamDto();
        paramDto.setMessage(message);
        return paramDto;
    }

    @GetMapping("/bar")
    public ParamDto bar(
            @RequestParam String text,
            @RequestParam Integer code
    ) {
        ParamDto paramDto = new ParamDto();
        paramDto.setMessage(text);
        paramDto.setCode(code);
        return paramDto;
    }

    @GetMapping("/request")
    public ParamDto request(HttpServletRequest request) {
        int code = 0;
        try {
            code = Integer.parseInt(request.getParameter("code"));
        } catch (NumberFormatException e) {
            System.out.println("NumberFormatException");
        }
        ParamDto params = new ParamDto();
        params.setCode(code);
        params.setMessage(request.getParameter("message"));
        return params;
    }
}
