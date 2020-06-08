package http.request.mapper;

import http.HttpStatus;
import http.handler.ExceptionHandler;
import http.handler.Handler;
import http.handler.StaticResourceHandler;
import http.handler.TemplateResourceHandler;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;

import static http.handler.Handlers.*;
import static java.util.stream.Collectors.toMap;

public enum HandlerMapper {
    CREATE_USER("/user/create", CREATE_USER_HANDLER),
    LIST_USER("/user/list", LIST_USER_HANDLER),
    LOGIN("/user/login", LOGIN_HANDLER),
    ;

    private static final Map<String, Handler> requestMap = initRequestMap();

    @Getter
    private String url;

    @Getter
    private Handler handler;

    HandlerMapper(String url, Handler handler) {
        this.url = url;
        this.handler = handler;
    }

    private static Map<String, Handler> initRequestMap() {
        return Arrays.stream(values())
            .collect(toMap(HandlerMapper::getUrl, HandlerMapper::getHandler));
    }

    public static Handler getHandler(String url) {
        if (TemplateResources.matches(url)) {
            return new TemplateResourceHandler(TemplateResources.getContentType(url));
        }

        if (StaticResources.matches(url)) {
            return new StaticResourceHandler(StaticResources.getContentType(url));
        }

        if (requestMap.containsKey(url)) {
            return requestMap.get(url);
        }

        return new ExceptionHandler(HttpStatus.NOT_FOUND);
    }
}
