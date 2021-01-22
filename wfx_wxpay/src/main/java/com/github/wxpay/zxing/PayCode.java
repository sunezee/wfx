package com.github.wxpay.zxing;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

/**
 * 依赖谷歌zxing jar包生成支付二维码
 */
public class PayCode {

    /**
     *
     * @param response//以流的形式返回给浏览器
     * @param payUrl//根据生成的支付连接生成二维码
     */
    public void createQrcode(HttpServletResponse response, String payUrl){
//        String payUrl = session.getAttribute("code_url").toString();
        //通过支付链接生成二维码
        HashMap<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
        hints.put(EncodeHintType.MARGIN, 2);
        try {
            BitMatrix bitMatrix = new MultiFormatWriter().encode(payUrl, BarcodeFormat.QR_CODE, 200, 200, hints);
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", response.getOutputStream());
            System.out.println("创建二维码完成");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

