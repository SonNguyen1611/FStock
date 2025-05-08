package com.example.Fstock.config;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

@Configuration
public class VnpayConfig {
    @Getter
    @Value("${payment.vnpay.url}")
    private String vnp_PayUrl;
    @Value("${payment.vnpay.returnUrl}")
    private String vnp_Returnurl;
    @Value("${payment.vnpay.tmnCode}")
    private String vnp_TmnCode;
    @Getter
    @Value("${payment.vnpay.secretKey}")
    private String vnp_SecretKey;
    @Value("${payment.vnpay.version}")
    private String vnp_Version;
    @Value("${payment.vnpay.command}")
    private String vnp_Command;
    @Value("${payment.vnpay.orderType}")
    private String vnp_OrderType;
    public Map<String , String> getVNPayConfig() {
        Map<String, String> vnpParamsMap = new HashMap<>();
        vnpParamsMap.put("vnp_TmnCode", vnp_TmnCode);
        vnpParamsMap.put("vnp_Version", vnp_Version);
        vnpParamsMap.put("vnp_Command", vnp_Command);
        vnpParamsMap.put("vnp_CurrCode", "VND");
        vnpParamsMap.put("vnp_OrderType", vnp_OrderType);
        vnpParamsMap.put("vnp_Locale", "vn");
        vnpParamsMap.put("vnp_ReturnUrl", vnp_Returnurl);
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnpCreateDate = formatter.format(calendar.getTime());
        vnpParamsMap.put("vnp_CreateDate", vnpCreateDate);
        calendar.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(calendar.getTime());
        vnpParamsMap.put("vnp_ExpireDate", vnp_ExpireDate);// hết hạn đơn thanh toán
        return vnpParamsMap;
    }
}
