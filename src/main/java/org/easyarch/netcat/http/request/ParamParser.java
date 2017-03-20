package org.easyarch.netcat.http.request;

import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.QueryStringDecoder;
import io.netty.handler.codec.http.multipart.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xingtianyu on 17-3-9
 * 上午1:17
 * description:
 */

public class ParamParser {
    private FullHttpRequest fullReq;

    /**
     * 构造一个解析器
     * @param req
     */
    public ParamParser(FullHttpRequest req) {
        this.fullReq = req;
    }

    /**
     * 解析请求参数
     * @return 包含所有请求参数的键值对, 如果没有参数, 则返回空Map
     * @throws IOException
     */
    public Map<String, String> parse() {
        if (this.fullReq == null){
            return new HashMap<>();
        }
        HttpMethod method = fullReq.method();

        Map<String, String> parmMap = new HashMap<>();

        if (HttpMethod.GET == method) {
            // 是GET请求
            parmMap = decodeQueryString();
        } else if (HttpMethod.POST == method) {
            // 是POST请求
            parmMap = decodeQueryString();
            HttpPostRequestDecoder decoder = new HttpPostRequestDecoder(
                    new DefaultHttpDataFactory(false),fullReq);
            List<InterfaceHttpData> paramlist = decoder.getBodyHttpDatas();
            System.out.println("paramlist:"+paramlist);
            for (InterfaceHttpData param : paramlist) {
                System.out.println("data type:"+param.getHttpDataType());
                if (param.getHttpDataType() == InterfaceHttpData.HttpDataType.Attribute) {
                    MemoryAttribute data = (MemoryAttribute) param;
                    parmMap.put(data.getName(), data.getValue());
                }else if (param.getHttpDataType() == InterfaceHttpData.HttpDataType.FileUpload){
                    MemoryFileUpload data = (MemoryFileUpload) param;

                }
            }
        }
        return parmMap;
    }

    public byte[] getFile(String name){
        byte[] file = new byte[0];
        if (this.fullReq == null){
            return new byte[0];
        }
        HttpMethod method = fullReq.method();
        if (HttpMethod.POST == method){
            HttpPostRequestDecoder decoder = new HttpPostRequestDecoder(
                    new DefaultHttpDataFactory(false),fullReq);
            decoder.offer(fullReq);
            List<InterfaceHttpData> paramlist = decoder.getBodyHttpDatas();
            for (InterfaceHttpData param : paramlist) {

                System.out.println("param type:"+param.getHttpDataType());
                if (param.getHttpDataType() == InterfaceHttpData.HttpDataType.Attribute){
                    System.out.println("paramClass:"+param.getClass().getName());
                    MemoryAttribute data = (MemoryAttribute) param;
                    if (data.getName().equals(name)){
                        file = data.get();
                    }
                    System.out.println("param length:"+data.get().length);
                }
            }
        }
        return file;
    }

    private Map<String, String> decodeQueryString(){
        Map<String, String> paramMap = new HashMap<>();
        QueryStringDecoder decoder = new QueryStringDecoder(fullReq.uri());
        Map<String, List<String>> parame = decoder.parameters();
        for(Map.Entry<String, List<String>> entry:parame.entrySet()){
            paramMap.put(entry.getKey(), entry.getValue().get(0));
        }
        return paramMap;
    }
}
