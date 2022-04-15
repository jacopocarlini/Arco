//package com.jacopocarlini.arco.controller;
//
//import com.jacopocarlini.arco.config.GatewayRoutesRefresher;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cloud.gateway.route.Route;
//import org.springframework.cloud.gateway.route.RouteLocator;
//import org.springframework.cloud.gateway.route.builder.GatewayFilterSpec;
//import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
//import org.springframework.cloud.gateway.route.builder.UriSpec;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RestController;
//import reactor.core.publisher.Flux;
//
//import java.net.URI;
//import java.net.URISyntaxException;
//
//@RestController
//public class ApiController implements RouteLocator {
//
//    @Autowired
//    RouteLocatorBuilder builder;
//
//    @Autowired
//    GatewayRoutesRefresher gatewayRoutesRefresher;
//
//    private RouteLocatorBuilder.Builder routesBuilder;
//    private Flux<Route> route = Flux.empty();
//
//    @GetMapping("/api")
//    public void addApi() throws URISyntaxException {
//        this.routesBuilder = builder.routes();
//        this.addRoute("graphql", "/graphql", new URI("lb://graphql"));
//        this.route = routesBuilder.build().getRoutes();
//        gatewayRoutesRefresher.refreshRoutes();
//    }
//
//    @Override
//    public Flux<Route> getRoutes() {
//        return this.route;
//    }
//
//    public ApiController addRoute(final String id, final String path, final URI uri) throws URISyntaxException {
//
//
//        routesBuilder.route(id, fn -> fn
//                .path(path + "/**")
//                .filters(filterSpec -> setupRouteFilters(path, uri, filterSpec))
//                .uri(uri)
//        );
//
//        return this;
//    }
//
//    private UriSpec setupRouteFilters(final String path, final URI uri, GatewayFilterSpec filterSpec) {
//        filterSpec.stripPrefix(1);
//
//        // setup the retry filter, it is important as during transitions from one page to another access problems can occur.
////        filterSpec.retry(config -> {
////            config.setRetries(5);
////            config.setStatuses(HttpStatus.INTERNAL_SERVER_ERROR);
////            config.setMethods(HttpMethod.GET, HttpMethod.POST, HttpMethod.PUT, HttpMethod.DELETE);
////        });
////
////        String prefixPath = uri.getPath();
////        filterSpec.setPath(prefixPath);
//
//        // handle redirects coming from a routed service
//        //  the service may be aware of request header 'X-Forwarded-Prefix'
////        filterSpec.addRequestHeader("X-Forwarded-Prefix", path);
//        //  as a fallback for services not aware of 'X-Forwarded-Prefix' we correct the Location header in response
////        filterSpec.filter(new ModifyResponseHeaderLocationGatewayFilterFactory().apply(c -> c.setName(path + "/")));
//
//        return filterSpec;
//    }
//
//}
