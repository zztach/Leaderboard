package org.zisist.leaderboard.inputsourcehandler.impl;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.zisist.leaderboard.inputsourcehandler.InputSourceHandler;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Responsible for opening a stream to an HTTP location
 * Created by zis.tax@gmail.com on 12/10/2012 at 1:08 AM
 */
@Service("httpInputSourceHandler")
public class HttpInputSourceHandler implements InputSourceHandler {
    private static Logger log = Logger.getLogger(HttpInputSourceHandler.class.getName());
    @Override
    public InputStream openStream(String sourceLocation) {
        try {
            HttpClient client = new HttpClient();

            GetMethod method = new GetMethod(sourceLocation);
            method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(3, false));

            int statusCode = client.executeMethod(method);
            if (statusCode != HttpStatus.SC_OK) {
                log.error(String.format("Failed to open URL connection at %s due to : %s!", sourceLocation, method.getStatusText()));
            } else {
                log.info(String.format("Reading from URL : %s ", sourceLocation));
            }

            return method.getResponseBodyAsStream();
        } catch (IOException e) {
            throw new IllegalStateException(String.format("Could not open stream to http location %s!", sourceLocation), e);
        }
    }
}
