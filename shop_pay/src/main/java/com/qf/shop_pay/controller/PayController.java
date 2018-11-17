package com.qf.shop_pay.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.qf.Utils.IsLogin;
import com.qf.entity.Orders;
import com.qf.service.IOrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("/pay")
public class PayController {

    @Reference
    private IOrderService orderService;

    @IsLogin
    @RequestMapping("/alipay")
    public String aliPay(String orderid, Model model){

        //通过订单id查询订单详情
        //Orders orders = orderService.queryByOrderid(orderid);

        //调用支付宝进行支付 -订单号，总金额
       // pay(orders,response);
        model.addAttribute("orderid",orderid);
        return "gopay";
    }

    @RequestMapping("/goalipay")
    public void pay(String orderid, HttpServletResponse response){
        Orders orders = orderService.queryByOrderid(orderid);


        AlipayClient alipayClient = new DefaultAlipayClient(
                "https://openapi.alipaydev.com/gateway.do",
                 "2016092100559206",
                "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCt6nHegMmRwGry5LbEEQuXJQkl4yQkvxNLynq92rNy15Mr+V/zSpFYUpLoAq5+eiZ295QMt1PCN6K1kcMlmJ6zItEHdzJnd3DwIP4iXlG9WOHZg2pF84tBDiHwj+v30By27jDI2mpHIQstW3cs7U7RxJe1pQHivkeuwtgIGPl3IoFXzTaMQ2VS9dCVV3O5qM6laRdMuhRVJ3vnGvDfPhzWOQLVdCnKHEgR7BSE2K58Dvsb313nRtM1A/sWfjYM2zTp+tWm3WRy3nwBm8K0W1xJ/HFiqNTPdAmlHLwF+gnnJT6b53hSXwqXGFClSR8CIzP1X7dlWqsMSUJ6/IC4kOkPAgMBAAECggEBAKGMALG514K3xaBRqyN84dNH57N1xgEMLBRexJzOJZorfN2ACwym21O7gBJbS1LYjBo95xvYvsjNfSpDd43YsrGbSacxpXROkm27d9Tc3/iOM1wkofbQPy3yvrwCNCJBAis6Hb60g7wGiadcKttko65eer2TxKimL1e6TYlYCYIK1uHLSydAEZV5sck5zkRIZc5JjR4wAw8D2aFoyYt6s8/8r2yXtLPwiBtKZ8Q6T0Kxi4krRMoQavP9VY7rgZb4HA0TTOlS/nu9jn7YrFzTc7BZeTPQFhRFURo6icRw2N645FfCnmDjBMuWaz1q4sHn9E+HMwyeWAWsVs9JB6TQ8OECgYEA26Sd+rf+8mFPpvL/mMbOnU5ImW9TMDokrMTmRV1/pMe700PSDp2w02I6eysGsPLkwIweaz954WpGoJm4v8T8fheyCyx423M5NEroWBJk3eVw+X8IovIVRZkbcm5mgJVIFE0z6M70t8LI5LHItsDmfj+ilxtgrrxT89YdqOxwhlUCgYEAyrQiWENOfNhWHT3Wxb7L9AzDfYvdiqwDZSfQViwfqgVNd4YqN4S99odp3HBm+hECM7iEJfRDnOk8LYpXapi0drYYTeG5maPWBKnE2LQYxd3JR31Qha7C8kXr6GvHsWxMm90hPeqXBuNVs2y0kZS1cTMopwbN2Cnsm9IaYJQgbdMCgYAikROzw4VCDAKHGbMUDiWyKAHQ8+45psziy9IZlI9kFXL/2qqSVkrf2ZOukqTo8DEEBIk9A65gxWL8H3Ut6Qh7p6sDiyoyDz4vdqJ+SdbJ/TP8b/uh+UtTurbRGYLUNao0V7oVp4xn5yXKqBgYet9LeyRRd6ONnGHx+/6K+Tr0SQKBgCwzmaWrj6rztduMh4/5ahoeymZPIJFDrz3Yr7qKxqDZpSniLMSMgqwGnVP/gsUW/mbu3oo0OKY3yfKYTzWkdP3J+YTKKyHQszOzHYSvyRMkqOaY8f0kvcf87VNyZUpQNmhqkPwicqqC2kZU9w8j4Wj84emCN1Li2YClETFsO6IXAoGAJiLb406lv0OQPXepT6MTcJTKnSbz8GLivgsNq6PK5suUJISmBhXWPYkxC5LYUijQTJFfcdKS54TcoWY0csLvYgRxhrw8hsAl769waFKj7aTpdzwVl6E9eM6Emde9HWP9HOAppPTVy02z4ufm/zVN6DjWfv8CtHjvPsy47vrPuhg=",
                "json",
                "UTF-8",
                "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA4IvN5C8wtzII+/WnVpJLILa5v9MBj566m1X7F1/2IIKFmVq8/8xbAa1GYUIvU+N1reUqN6Op8K7TfZAthvCpEaeASJuxzHDDS+epnrN4SWgGNssoohlj5sn8PT4PZfcjM3PQ06Ojh+jg3FSyFjXQZzeRVdQUxoFNIkQT4KpMOD7RDI5FgPJyVsmqNU4Dd2D/xo52bLm3HV95CD2HdFrUoM9lJzZDlxnbZFqpNaxsVsvV0mPj7NUBhoPcmGE37jXc7h3CAUCEedSeTIuf8bWn6bNtPw4kvAzqKpfJA0GUR6DYJZtBXXGEXjMIm+Yt7hrGIbNiK4vqIHe5LB71abL4gQIDAQAB",
                "RSA2"); //获得初始化的AlipayClient
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();//创建API对应的request
        alipayRequest.setReturnUrl("http://www.jd.com");//设置同步响应的URL
        alipayRequest.setNotifyUrl("http://www.baidu.com"); //在公共参数中设置回跳和通知地址，异步响应的URL
        alipayRequest.setBizContent("{" +
                    "    \"out_trade_no\":\""+orders.getOrderid()+"\"," +
                "    \"product_code\":\"FAST_INSTANT_TRADE_PAY\"," +
                "    \"total_amount\":"+orders.getOprice()+"," +
                "    \"subject\":\""+orders.getOrderid()+"\"," +
                "    \"body\":\""+orders.getOrderid()+"\"," +
                "    \"passback_params\":\"merchantBizType%3d3C%26merchantBizNo%3d2016010101111\"," +
                "    \"extend_params\":{" +
                "    \"sys_service_provider_id\":\"2088511833207846\"" +
                "    }"+
                "  }");//填充业务参数
        String form="";
        try {
            form = alipayClient.pageExecute(alipayRequest).getBody(); //调用SDK生成表单
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        response.setContentType("text/html;charset=utf-8"  );

        try {
            response.getWriter().write(form);//直接将完整的表单html输出到页面
            response.getWriter().flush();
            response.getWriter().close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    //支付成功修改状态

    @RequestMapping("/isok")
    public String ispayok(String orderid){
        //通过订单号查询支付宝是否支付成功
        AlipayClient alipayClient = new DefaultAlipayClient(
                "https://openapi.alipaydev.com/gateway.do",
                "2016092100559206",
                "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCt6nHegMmRwGry5LbEEQuXJQkl4yQkvxNLynq92rNy15Mr+V/zSpFYUpLoAq5+eiZ295QMt1PCN6K1kcMlmJ6zItEHdzJnd3DwIP4iXlG9WOHZg2pF84tBDiHwj+v30By27jDI2mpHIQstW3cs7U7RxJe1pQHivkeuwtgIGPl3IoFXzTaMQ2VS9dCVV3O5qM6laRdMuhRVJ3vnGvDfPhzWOQLVdCnKHEgR7BSE2K58Dvsb313nRtM1A/sWfjYM2zTp+tWm3WRy3nwBm8K0W1xJ/HFiqNTPdAmlHLwF+gnnJT6b53hSXwqXGFClSR8CIzP1X7dlWqsMSUJ6/IC4kOkPAgMBAAECggEBAKGMALG514K3xaBRqyN84dNH57N1xgEMLBRexJzOJZorfN2ACwym21O7gBJbS1LYjBo95xvYvsjNfSpDd43YsrGbSacxpXROkm27d9Tc3/iOM1wkofbQPy3yvrwCNCJBAis6Hb60g7wGiadcKttko65eer2TxKimL1e6TYlYCYIK1uHLSydAEZV5sck5zkRIZc5JjR4wAw8D2aFoyYt6s8/8r2yXtLPwiBtKZ8Q6T0Kxi4krRMoQavP9VY7rgZb4HA0TTOlS/nu9jn7YrFzTc7BZeTPQFhRFURo6icRw2N645FfCnmDjBMuWaz1q4sHn9E+HMwyeWAWsVs9JB6TQ8OECgYEA26Sd+rf+8mFPpvL/mMbOnU5ImW9TMDokrMTmRV1/pMe700PSDp2w02I6eysGsPLkwIweaz954WpGoJm4v8T8fheyCyx423M5NEroWBJk3eVw+X8IovIVRZkbcm5mgJVIFE0z6M70t8LI5LHItsDmfj+ilxtgrrxT89YdqOxwhlUCgYEAyrQiWENOfNhWHT3Wxb7L9AzDfYvdiqwDZSfQViwfqgVNd4YqN4S99odp3HBm+hECM7iEJfRDnOk8LYpXapi0drYYTeG5maPWBKnE2LQYxd3JR31Qha7C8kXr6GvHsWxMm90hPeqXBuNVs2y0kZS1cTMopwbN2Cnsm9IaYJQgbdMCgYAikROzw4VCDAKHGbMUDiWyKAHQ8+45psziy9IZlI9kFXL/2qqSVkrf2ZOukqTo8DEEBIk9A65gxWL8H3Ut6Qh7p6sDiyoyDz4vdqJ+SdbJ/TP8b/uh+UtTurbRGYLUNao0V7oVp4xn5yXKqBgYet9LeyRRd6ONnGHx+/6K+Tr0SQKBgCwzmaWrj6rztduMh4/5ahoeymZPIJFDrz3Yr7qKxqDZpSniLMSMgqwGnVP/gsUW/mbu3oo0OKY3yfKYTzWkdP3J+YTKKyHQszOzHYSvyRMkqOaY8f0kvcf87VNyZUpQNmhqkPwicqqC2kZU9w8j4Wj84emCN1Li2YClETFsO6IXAoGAJiLb406lv0OQPXepT6MTcJTKnSbz8GLivgsNq6PK5suUJISmBhXWPYkxC5LYUijQTJFfcdKS54TcoWY0csLvYgRxhrw8hsAl769waFKj7aTpdzwVl6E9eM6Emde9HWP9HOAppPTVy02z4ufm/zVN6DjWfv8CtHjvPsy47vrPuhg=",
                "json",
                "UTF-8",
                "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA4IvN5C8wtzII+/WnVpJLILa5v9MBj566m1X7F1/2IIKFmVq8/8xbAa1GYUIvU+N1reUqN6Op8K7TfZAthvCpEaeASJuxzHDDS+epnrN4SWgGNssoohlj5sn8PT4PZfcjM3PQ06Ojh+jg3FSyFjXQZzeRVdQUxoFNIkQT4KpMOD7RDI5FgPJyVsmqNU4Dd2D/xo52bLm3HV95CD2HdFrUoM9lJzZDlxnbZFqpNaxsVsvV0mPj7NUBhoPcmGE37jXc7h3CAUCEedSeTIuf8bWn6bNtPw4kvAzqKpfJA0GUR6DYJZtBXXGEXjMIm+Yt7hrGIbNiK4vqIHe5LB71abL4gQIDAQAB",
                "RSA2"); //获得初始化的AlipayClient
        AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
        request.setBizContent("{" +
                "\"out_trade_no\":\"" + orderid + "\"" +
                "  }");
        AlipayTradeQueryResponse response = null;
        try {
            response = alipayClient.execute(request);
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        if(response.isSuccess()){
            String tradeStatus = response.getTradeStatus();
            if(tradeStatus.equals("TRADE_SUCCESS")){
                //支付成功
                orderService.updateStatusByOrderid(orderid, 1);
            }
        } else {
            System.out.println("调用失败");
        }


        return "redirect:http://localhost:8086/order/orderlist";
    }


    }


