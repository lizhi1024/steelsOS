package com.lzos.steels.admin.utils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Auth lizhi
 * Date 2021/07/06
 * Describe httpclient请求封装
 */
public class HttpClientUtils {

    private static HttpClientUtils httpClient;

    private static final String GET = "GET";

    private static final String POST = "POST";

    private static final String LINK = "?";

    @Value("${ide.server.entryPoint}")
    private String entryPoint;

    public static HttpClientUtils getInstance() {
        if (httpClient == null) {
            httpClient = new HttpClientUtils();
        }
        return httpClient;
    }

    /**
     * 下载文件
     * @param reqUrl
     * @return byte[]
     */
    public void httpDownloadFile(String reqUrl, HttpServletResponse response) throws Exception{
        httpDownloadFile(reqUrl, response, null);
    }

    /**
     * 下载文件
     * @param reqUrl
     * @param headMap
     */
    public void httpDownloadFile(String reqUrl, HttpServletResponse response, Map<String, String> headMap) throws Exception{

        CloseableHttpClient httpclient = HttpClients.createDefault();
        CloseableHttpResponse response1 = null;
        BufferedInputStream bis = null;
        InputStream is = null;
        OutputStream os = null;

        try {
            HttpGet httpGet = new HttpGet(reqUrl);
            setGetHead(httpGet, headMap);
            response1 = httpclient.execute(httpGet);
            HttpEntity httpEntity = response1.getEntity();
            is = httpEntity.getContent();
            bis = new BufferedInputStream(is);

            response.reset();
            response.setContentType(httpEntity.getContentType().getValue());
            response.setContentLength((int) httpEntity.getContentLength());
            response.setHeader("Content-Disposition", response1.getLastHeader("Content-Disposition").getValue());

            byte[] buff = new byte[1024];
            os = response.getOutputStream();
            int i = 0;
            while ((i = bis.read(buff)) != -1) {
                os.write(buff, 0, i);
                os.flush();
            }
            EntityUtils.consume(httpEntity);

        } catch (Exception e) {
            throw e;
        } finally {
            if (httpclient != null) {
                httpclient.close();
            }

            if (response1 != null) {
                response1.close();
            }
            if (bis != null) {
                bis.close();
            }
            if (is != null) {
                is.close();
            }
            if (os != null) {
                os.close();
            }
        }
    }

    /**
     * get请求
     * @param reqUrl
     * @return
     */
    public String httpGet(String reqUrl) throws Exception{
        return httpGet(reqUrl, null);
    }

    /**
     * http get请求
     * @param reqUrl
     * @param headMap
     * @return String
     */
    public String httpGet(String reqUrl, Map<String, String> headMap) throws Exception {

        String responseContent = null;
        CloseableHttpClient httpclient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        try {
            HttpGet httpGet = new HttpGet(reqUrl);

            setGetHead(httpGet, headMap);
            httpGet.removeHeaders("Content-Length");
            response = httpclient.execute(httpGet);

            HttpEntity entity = response.getEntity();
            responseContent = getRespString(entity);
            EntityUtils.consume(entity);

        } catch (Exception e) {
            throw e;
        } finally {
            if (httpclient != null) {
                httpclient.close();
            }

            if (response != null) {
                response.close();
            }
        }
        return responseContent;
    }

    public String httpPost(String reqUrl, Map<String, String> paramsMap) throws Exception{
        return httpPost(reqUrl, paramsMap, null);
    }

    public String httpPost(Map<String, MultipartFile> filesMap, Map<String, String> paramsMap, String reqUrl) throws Exception{
        return httpPost(reqUrl, paramsMap, filesMap, null);
    }

    /**
     * http的post请求
     * @param reqUrl
     * @param paramsMap
     * @param headMap
     * @return String
     */
    public String httpPost(String reqUrl, Map<String, String> paramsMap,
                           Map<String, String> headMap) throws Exception {

        String responseContent = null;
        CloseableHttpClient httpclient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        try {

            HttpPost httpPost = new HttpPost(reqUrl);
            setPostHead(httpPost, headMap);
            setPostParams(httpPost, paramsMap);
            httpPost.removeHeaders("Content-Length");
            httpPost.removeHeaders("Content-Type");
            response = httpclient.execute(httpPost);

            HttpEntity entity = response.getEntity();
            responseContent = getRespString(entity);
            EntityUtils.consume(entity);

        } catch (Exception e) {
            throw e;
        } finally {
            if (httpclient != null) {
                httpclient.close();
            }

            if (response != null) {
                response.close();
            }
        }

        return responseContent;
    }

    /**
     * 设置POST的参数
     *
     * @param httpPost
     * @param paramsMap
     * @throws Exception
     */
    private void setPostParams(HttpPost httpPost, Map<String, String> paramsMap) {

        StringEntity stringEntity = new StringEntity(JSON.toJSONString(paramsMap, SerializerFeature.WriteMapNullValue), "utf-8");
        stringEntity.setContentEncoding("utf-8");
        stringEntity.setContentType("application/json");

        httpPost.setEntity(stringEntity);
    }

    /**
     * 设置http的HEAD
     *
     * @param httpPost
     * @param headMap
     */
    private void setPostHead(HttpPost httpPost, Map<String, String> headMap) {
        if (headMap != null && headMap.size() > 0) {
            Set<String> keySet = headMap.keySet();
            for (String key : keySet) {
                httpPost.addHeader(key, headMap.get(key));
            }
        }
    }

    /**
     * 设置http的HEAD
     *
     * @param httpGet
     * @param headMap
     */
    private void setGetHead(HttpGet httpGet, Map<String, String> headMap) {
        if (headMap != null && headMap.size() > 0) {
            Set<String> keySet = headMap.keySet();
            for (String key : keySet) {
                httpGet.addHeader(key, headMap.get(key));
            }
        }
    }

    /**
     * post请求包含文件上传
     * @param reqUrl 请求地址
     * @return String
     * @throws Exception
     */
    public String httpPost(String reqUrl, Map<String, String> paramsMap, Map<String,
            MultipartFile> filesMap, Map<String, String> headMap)
            throws Exception {

        String respStr = null;
        CloseableHttpClient httpclient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        try {

            HttpPost httppost = new HttpPost(reqUrl);
            MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder
                    .create();
            multipartEntityBuilder.setCharset(Charset.forName("UTF-8"));

            // 设置参数
            setFileParams(multipartEntityBuilder, filesMap);
            setDataParams(multipartEntityBuilder, paramsMap);
            setPostHead(httppost, headMap);

            HttpEntity reqEntity = multipartEntityBuilder.build();
            httppost.setEntity(reqEntity);
            httppost.removeHeaders("Content-Length");
            httppost.removeHeaders("Content-Type");
            response = httpclient.execute(httppost);

            HttpEntity resEntity = response.getEntity();
            respStr = getRespString(resEntity);
            EntityUtils.consume(resEntity);

        } catch (Exception e) {
            throw e;
        } finally {

            if (httpclient != null) {
                httpclient.close();
            }

            if (response != null) {
                response.close();
            }

        }
        return respStr;
    }

    /**
     * 设置post参数
     *
     * @param multipartEntityBuilder
     * @param params
     */
    private void setDataParams(MultipartEntityBuilder multipartEntityBuilder,
                                 Map<String, String> params) {
        if (params != null && params.size() > 0) {
            Set<String> keys = params.keySet();
            for (String key : keys) {
                multipartEntityBuilder
                        .addPart(key, new StringBody(params.get(key),
                                ContentType.TEXT_PLAIN));
            }
        }
    }

    /**
     * 设置post文件参数
     *
     * @param multipartEntityBuilder
     * @param params
     */
    private void setFileParams(MultipartEntityBuilder multipartEntityBuilder,
                                 Map<String, MultipartFile> params) throws Exception{
        if (params != null && params.size() > 0) {
            Set<String> keys = params.keySet();
            for (String key : keys) {
                multipartEntityBuilder
                        .addBinaryBody(key, params.get(key).getBytes(), ContentType.DEFAULT_BINARY, params.get(key).getOriginalFilename());
            }
        }
    }

    /**
     * 将返回结果转化为String
     *
     * @param entity
     * @return
     * @throws Exception
     */
    private String getRespString(HttpEntity entity) throws Exception {
        if (entity == null) {
            return null;
        }
        InputStream is = entity.getContent();
        StringBuffer strBuf = new StringBuffer();
        byte[] buffer = new byte[4096];
        int r = 0;
        while ((r = is.read(buffer)) > 0) {
            strBuf.append(new String(buffer, 0, r, "UTF-8"));
        }
        return strBuf.toString();
    }

    /**
     * 获取post请求参数
     * @param request
     * @return
     * @throws Exception
     */
//    public static Map<String, Object> getPostParamsMap(HttpServletRequest request) throws Exception{
//
//        Gson gson = new Gson();
//        String type = request.getContentType();
//
//        Map<String, Object> paramsMap = new HashMap<String, Object>();
//        Map<String, String> dataMap = new HashMap<String,String>();
//        Map<String, MultipartFile> fileMap = null;
//
//        if ("application/x-www-form-urlencoded".equals(type)){
//
//            Enumeration<String> enu = request.getParameterNames();
//            while (enu.hasMoreElements()) {
//                String key = String.valueOf(enu.nextElement());
//                String value = request.getParameter(key);
//                dataMap.put(key, value);
//            }
//
//        } else if (type.contains("multipart/form-data")) {
//
//            Enumeration<String> enu = request.getParameterNames();
//            while (enu.hasMoreElements()) {
//                String key = String.valueOf(enu.nextElement());
//                String value = request.getParameter(key);
//                dataMap.put(key, value);
//            }
//
//            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
//            fileMap = multipartRequest.getFileMap();
//
//        } else {//else是text/plain、application/json这两种情况
//
//            BufferedReader reader = null;
//            StringBuilder sb = new StringBuilder();
//            try{
//                reader = new BufferedReader(new InputStreamReader(request.getInputStream(), "utf-8"));
//                String line = null;
//                while ((line = reader.readLine()) != null){
//                    sb.append(line);
//                }
//            } catch (IOException e){
//                throw e;
//            } finally {
//                try{
//                    if (null != reader){
//                        reader.close();
//                    }
//                } catch (IOException e){
//                    throw e;
//                }
//            }
//            //把JSON字符串转为对象
//            dataMap = gson.fromJson(sb.toString(), new TypeToken<Map<String, String>>(){}.getType());
//
//        }
//
//        paramsMap.put("dataMap", dataMap);
//        paramsMap.put("filesMap", fileMap);
//
//        return paramsMap;
//    }

    /**
     * 获取请求头参数
     * @param request
     * @return Map
     */
    public static Map<String, String> getHeaderParamsMap(HttpServletRequest request) {
        Map<String, String> paramsMap = new HashMap<String,String>();

        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = String.valueOf(headerNames.nextElement());
            String value = request.getHeader(key);
            paramsMap.put(key, value);
        }
        return paramsMap;
    }

    public void httpProxy(HttpServletRequest request, HttpServletResponse response) throws Exception{

        CloseableHttpClient httpclient = HttpClients.createDefault();
        CloseableHttpResponse closeableHttpResponse = null;

        String method = request.getMethod();
        String requestUri = request.getRequestURI();
        String requestUrl = entryPoint + requestUri;
        Map<String, String> headerParamsMap = getHeaderParamsMap(request);

        try {

            switch (method) {
                case GET:
                    HttpGet httpGet = new HttpGet("http://localhost:8098/file/download.json?file=2045009.jpg");
                    setGetHead(httpGet, headerParamsMap);
                    closeableHttpResponse = httpclient.execute(httpGet);
                    HttpEntity entity = closeableHttpResponse.getEntity();
                    response.reset();
                    for (Header header : closeableHttpResponse.getAllHeaders()) {
                        if (!header.getValue().equals("chunked") && !header.getValue().equals("keep-alive")) {
                            response.setHeader(header.getName(), header.getValue());
                        }
                    }
                    entity.writeTo(response.getOutputStream());
                    EntityUtils.consume(entity);
                    break;
                case POST:
                    break;
                default:
                    System.out.println("no METHOD");
            }

        } catch (Exception e) {
            throw e;
        } finally {
            if (httpclient != null) {
                httpclient.close();
            }
            if (closeableHttpResponse != null) {
                closeableHttpResponse.close();
            }
        }

    }
}
