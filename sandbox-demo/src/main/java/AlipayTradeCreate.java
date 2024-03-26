import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.AlipayConfig;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeCreateModel;
import com.alipay.api.request.AlipayTradeCreateRequest;
import com.alipay.api.response.AlipayTradeCreateResponse;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class AlipayTradeCreate {
    private static String keyPath = "sandbox-demo/src/main/resources/key.properties";
    private static String privateKey;
    private static String alipayPublicKey;
    private static String appId;
    private static String buyerId;

    private static void initKey() {
        Properties prop = new Properties();

        try (FileInputStream input = new FileInputStream(keyPath)) {
            // 从输入流加载属性列表
            prop.load(input);
            // 获取属性
            privateKey = prop.getProperty("privateKey");
            alipayPublicKey = prop.getProperty("alipayPublicKey");
            appId = prop.getProperty("appId");
            buyerId = prop.getProperty("buyerId");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) throws AlipayApiException {
        initKey();
        AlipayConfig alipayConfig = new AlipayConfig();
        alipayConfig.setServerUrl("https://openapi-sandbox.dl.alipaydev.com/gateway.do");
        alipayConfig.setPrivateKey(privateKey);
        alipayConfig.setAppId(appId);
        alipayConfig.setFormat("json");
        alipayConfig.setAlipayPublicKey(alipayPublicKey);
        alipayConfig.setCharset("UTF-8");
        alipayConfig.setSignType("RSA2");
        AlipayClient alipayClient = new DefaultAlipayClient(alipayConfig);
        AlipayTradeCreateRequest request = new AlipayTradeCreateRequest();
        AlipayTradeCreateModel model = new AlipayTradeCreateModel();
        model.setOutTradeNo("20150320010101001"); // 商家订单号
        model.setTotalAmount("88.88");
        model.setSubject("Iphone6 16G");
        model.setBuyerId(buyerId);
        request.setBizModel(model);
        AlipayTradeCreateResponse response = alipayClient.execute(request);
        System.out.println(response.getBody());
        if (response.isSuccess()) {
            System.out.println("调用成功");
        } else {
            System.out.println("调用失败");
            // sdk版本是"4.38.0.ALL"及以上,可以参考下面的示例获取诊断链接
            // String diagnosisUrl = DiagnosisUtils.getDiagnosisUrl(response);
            // System.out.println(diagnosisUrl);
        }
    }
}