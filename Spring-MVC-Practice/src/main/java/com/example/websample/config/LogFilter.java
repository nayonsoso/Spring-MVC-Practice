package com.example.websample.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;


@Slf4j
public class LogFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        // 외부 -> filter (-> 처리 ->) filter -> 외부
        // 처리를 하고 다시 filter로 오기 위해서 FilterChain을 활용한다.
        // 따라서 reqeust와 response는 이어줘야 한다.
        log.info("Hello LogFilter : " + Thread.currentThread());
        chain.doFilter(request, response);
        log.info("Bye LogFilter : " + Thread.currentThread());
    }
}
