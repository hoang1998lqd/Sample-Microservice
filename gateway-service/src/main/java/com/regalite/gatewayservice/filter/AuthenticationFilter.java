package com.regalite.gatewayservice.filter;

import com.regalite.gatewayservice.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    @Autowired
    private RouteValidator validator;

        @Autowired
    private RestTemplate template;
    @Autowired
    private JwtUtil jwtUtil;

    public AuthenticationFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            if (validator.isSecured.test(exchange.getRequest())) {
                //header contains token or not
                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    throw new RuntimeException("missing authorization header");
                }

                String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
//                Optional<Cookie> cookie = CookieUtils.getCookie((HttpServletRequest) exchange.getRequest(), "token");
//                String authHeader = null;
//                if (cookie.isPresent()){
//                    authHeader = cookie.get().getValue();
//                }
                if (authHeader != null && authHeader.startsWith("Bearer ")) {
                    authHeader = authHeader.substring(7);
                }
                try {

                    //REST call to AUTH service
                    template.getForObject("http://localhost:8081/api/user/validate?token=" + authHeader, String.class);
//                   if (jwtUtil.validateToken(authHeader)){
//                       System.out.println("Success");
//                   }else {
//                       System.out.println("Fail !!!!!!!!!!!!!!");
//                   }

                } catch (Exception e) {
                    System.out.println("invalid access...!");
                    throw new RuntimeException("un authorized access to application");
                }
            }
            return chain.filter(exchange);
        });
    }

    public static class Config {

    }
}
