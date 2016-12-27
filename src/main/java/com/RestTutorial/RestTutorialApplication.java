package com.RestTutorial;

import com.RestTutorial.dao.AccountRepository;
import com.RestTutorial.dao.BookmarkRepository;
import com.RestTutorial.model.Account;
import com.RestTutorial.model.Bookmark;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

@SpringBootApplication
class RestTutorialApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestTutorialApplication.class, args);
    }

    @Bean
    CommandLineRunner init(AccountRepository accountRepository, BookmarkRepository bookmarkRepository) {
        return (args) -> Arrays.asList("jhoeller,dsyer,pwebb,ogierke,rwinch,mfisher,mpollack,jlong".split(","))
                .forEach(a -> {
                    Account account = accountRepository.save(new Account(a, "password"));
                    bookmarkRepository.save(new Bookmark(account, "http://bookmark.com/1/" + a, "A description"));
                    bookmarkRepository.save(new Bookmark(account, "http://bookmark.com/2/" + a, "A description"));
                });
    }

    @Bean
    FilterRegistrationBean corsFilter(@Value("${tagit.origin:http://localhost:9000}") String origin) {
        return new FilterRegistrationBean(new Filter() {
            @Override
            public void init(FilterConfig filterConfig) throws ServletException {
            }

            @Override
            public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
                HttpServletRequest servletRequest = (HttpServletRequest) request;
                HttpServletResponse servletResponse = (HttpServletResponse) response;
                String method = servletRequest.getMethod();
                // this origin value could just as easily have come from a database
                servletResponse.setHeader("Access-Control-Allow-Origin", origin);
                servletResponse.setHeader("Access-Control-Allow-Methods","POST,GET,OPTIONS,DELETE");
                servletResponse.setHeader("Access-Control-Max-Age", Long.toString(60 * 60));
                servletResponse.setHeader("Access-Control-Allow-Credentials", "true");
                servletResponse.setHeader("Access-Control-Allow-Headers",
                        "Origin,Accept,X-Requested-With,Content-Type,Access-Control-Request-Method,Access-Control-Request-Headers,Authorization");
                if ("OPTIONS".equals(method)){
                    servletResponse.setStatus(HttpStatus.OK.value());
                } else {
                    chain.doFilter(request,response);
                }
            }

            @Override
            public void destroy() {

            }
        });
    }
}
