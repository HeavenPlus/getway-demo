package com.heaven.filter;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author zhanggq
 * @date 2023/3/18 18:11
 */
@Component
public class TokenGlobalFilter implements GlobalFilter {

    @Value("${server.port}")
    private String serverPort;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String token = exchange.getRequest().getQueryParams().getFirst("token");
        if(StringUtils.isBlank(token)){
            ServerHttpResponse response = exchange.getResponse();
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
            String msg = "token is null";
            DataBuffer wrap = response.bufferFactory().wrap(msg.getBytes());
            return response.writeWith(Mono.just(wrap));
        }
        // 在请求头中存放serverPort serverPort
        ServerHttpRequest request = exchange.getRequest().mutate().header("serverPort", serverPort).build();
        return chain.filter(exchange.mutate().request(request).build());
    }
}
