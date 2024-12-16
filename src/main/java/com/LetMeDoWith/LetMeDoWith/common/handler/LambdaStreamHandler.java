package com.LetMeDoWith.LetMeDoWith.common.handler;

import com.LetMeDoWith.LetMeDoWith.LetMeDoWithApplication;
import com.amazonaws.serverless.exceptions.ContainerInitializationException;
import com.amazonaws.serverless.proxy.model.AwsProxyRequest;
import com.amazonaws.serverless.proxy.model.AwsProxyResponse;
import com.amazonaws.serverless.proxy.spring.SpringBootLambdaContainerHandler;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


/**
 * API Gateway + Lambda 로 구성된 개발서버 환경에서,
 * Lambda 요청을 Spring 에서 처리 가능한 HTTP 요청으로 처리하기 위해 정의한 Proxy.
 */
@Component
@Slf4j
//@Profile("dev")
public class LambdaStreamHandler implements RequestStreamHandler {
    
    private static final SpringBootLambdaContainerHandler<AwsProxyRequest, AwsProxyResponse> handler;
    
    static {
        try {
            handler = SpringBootLambdaContainerHandler.getAwsProxyHandler(LetMeDoWithApplication.class,
                                                                          "dev",
                                                                          "dev-confidential");
            
            
        } catch (ContainerInitializationException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }
    
    @Override
    public void handleRequest(InputStream inputStream, OutputStream outputStream, Context context)
        throws IOException {
        handler.proxyStream(inputStream, outputStream, context);
    }
}