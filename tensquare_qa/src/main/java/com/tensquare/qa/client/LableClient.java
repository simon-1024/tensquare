package com.tensquare.qa.client;

import com.tensquare.qa.client.impl.LabelClientImpl;
import entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(value = "tensquare-base",fallback = LabelClientImpl.class)
public interface LableClient {
    @GetMapping("/label/{id}")
    public Result findById(@PathVariable("id") String id);
}
