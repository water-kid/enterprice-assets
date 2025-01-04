package com.cj.project.assets.controller;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author cc
 * @date 2025-01-02 18:10
 **/
@RestController
public class QrCodeController {

    @GetMapping("/assets/generate-qrcode")
    public void generateQrCode(String code, HttpServletResponse response) throws WriterException, IOException {
        response.setContentType("image/png");
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate, post-check=0, pre-check=0"); // 避免缓存
        response.setHeader("Pragma", "no-cache"); // 避免缓存
        response.setHeader("Access-Control-Allow-Origin", "*"); // 如果跨域访问

        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        int width  = 200;
        int height = 200;
        BitMatrix bitMatrix = qrCodeWriter.encode(code, BarcodeFormat.QR_CODE, width, height);

        MatrixToImageWriter.writeToStream(bitMatrix,"PNG",response.getOutputStream());
    }
}
