package com.example.demo.Service;

import com.example.demo.DTO.BalanceDTO;
import com.example.demo.DTO.HistoryDTO;
import com.example.demo.DTO.LoginDTO;
import com.example.demo.Obj.Balance;
import com.example.demo.Obj.DCTK;
import com.example.demo.Obj.History;
import com.example.demo.Obj.ResponseDctk;
import com.example.demo.Utils.DctkUtils;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
@Service
public class DctkService {
    private static boolean isRun = true;
    private RestTemplate restTemplate = new RestTemplate();
    private static String url = "https://api.dctk.me/api/home/history?page=1";
    private static String urlToken = "https://api.dctk.me/api/login";
    private static String urlPlay = "https://api.dctk.me/api/user/join";
    private static String urlBalance = "https://api.dctk.me/api/user/balance-history?page=1&limit=20";
    private static  String userName = "lecuong2201";
    private static  String password = "Lecuong220120a@";
    private static String token = "";
    private static String to = "cutoongacteo@gmail.com";
    //dc
    private static List<Integer> list1 = new ArrayList<>(Arrays.asList(0, 0, 0));
    private static List<Integer> list2 = new ArrayList<>(Arrays.asList(0, 0, 1));
    private static List<Integer> list3 = new ArrayList<>(Arrays.asList(0, 1, 0));
    private static List<Integer> list4 = new ArrayList<>(Arrays.asList(0, 1, 1));
    private static List<Integer> list5 = new ArrayList<>(Arrays.asList(1, 0, 0));
    private static List<Integer> list6 = new ArrayList<>(Arrays.asList(1, 0, 1));
    private static List<Integer> list7 = new ArrayList<>(Arrays.asList(1, 1, 0));
    private static List<Integer> list8 = new ArrayList<>(Arrays.asList(1, 1, 1));
    //tc
    private static List<Integer> list9  = new ArrayList<>(Arrays.asList(2, 2, 2));
    private static List<Integer> list10 = new ArrayList<>(Arrays.asList(2, 2, 3));
    private static List<Integer> list11 = new ArrayList<>(Arrays.asList(2, 3, 2));
    private static List<Integer> list12 = new ArrayList<>(Arrays.asList(2, 3, 3));
    private static List<Integer> list13 = new ArrayList<>(Arrays.asList(3, 2, 2));
    private static List<Integer> list14 = new ArrayList<>(Arrays.asList(3, 2, 3));
    private static List<Integer> list15 = new ArrayList<>(Arrays.asList(3, 3, 2));
    private static List<Integer> list16 = new ArrayList<>(Arrays.asList(3, 3, 3));
    //last play
    public static History history;

    public HistoryDTO getHistory(){
        ResponseEntity<HistoryDTO> response
                = restTemplate.getForEntity(url, HistoryDTO.class);
        return  response.getBody();
    }
    public String getToken(){
        LoginDTO loginDTO = new LoginDTO(userName,  password);
        ResponseEntity<LoginDTO> response
                = restTemplate.postForEntity(urlToken, loginDTO, LoginDTO.class);
        return response.getBody().getJwt();
    }
    public void autoPlay(){
        try{
            if(history != null){
                Integer idPrev =  history.getId() + 1;
                List<Balance> balances = getBalance().stream().
                        filter(item -> item.getNote().indexOf(idPrev.toString()) != -1)
                        .collect(Collectors.toList());
                if(!CollectionUtils.isEmpty(balances)){
                    String text = "";
                    int countSuccess = 0;
                    for (Balance balance : balances) {
                        if(balance.getNote().indexOf("Trao thưởng phiên") != -1){
                            countSuccess++;
                            text = text + "Lần: "+ countSuccess + ", cảm ơn bạn: "+ balance.toString() + "\n";
                        }
                    }
                    if (countSuccess ==0){
                        text = "It's nothing";
                    }
                    System.out.println("======================" + text);
                    sendMail(text);
                }
            }
            List<History> historiesRes = getHistory().getHistories();
            List<History> histories = new ArrayList<>();
            history = historiesRes.get(0);

            for (int i = 0; i< historiesRes.size(); i++){
                histories.add(historiesRes.get(i));
                if(i == 2){
                    break;
                }
            }
            List<Integer> listDC = histories.stream().map(History::getResult_cd).collect(Collectors.toList());
            List<Integer> listTK = histories.stream().map(History::getResult_tk).collect(Collectors.toList());
            Integer playDC = preditionDCTK(listDC, true);
            Integer playTk = preditionDCTK(listTK, false);
            play(playDC, playTk);
        }catch (Exception e){
            System.out.printf("ERRORRRRRRRRRRRRRRR" + e);
        }

    }
    public int preditionDCTK(List<Integer> listInteger, boolean isDC){
        if (isDC){
            if(listInteger.equals(list1)){
                return DctkUtils.DCTK.C;
            }else if(listInteger.equals(list2)){
                return DctkUtils.DCTK.C;
            }else if(listInteger.equals(list3)){
                return DctkUtils.DCTK.D;
            }else if(listInteger.equals(list4)){
                return DctkUtils.DCTK.D;
            }else if(listInteger.equals(list5)){
                return DctkUtils.DCTK.C;
            }else if(listInteger.equals(list6)){
                return DctkUtils.DCTK.C;
            }else if(listInteger.equals(list7)){
                return DctkUtils.DCTK.D;
            }else if (listInteger.equals(list8)){
                return DctkUtils.DCTK.D;
            }
            return 1;
        }else{
            if(listInteger.equals(list9)){
                return DctkUtils.DCTK.T;
            }else if(listInteger.equals(list10)){
                return DctkUtils.DCTK.T;
            }else if(listInteger.equals(list11)){
                return DctkUtils.DCTK.K;
            }else if(listInteger.equals(list12)){
                return DctkUtils.DCTK.K;
            }else if(listInteger.equals(list13)){
                return DctkUtils.DCTK.T;
            }else if(listInteger.equals(list14)){
                return DctkUtils.DCTK.T;
            }else if(listInteger.equals(list15)){
                return DctkUtils.DCTK.K;
            }else if (listInteger.equals(list16)){
                return DctkUtils.DCTK.K;
            }
            return 3;
        }
    }
    public List<Balance> getBalance(){
        HttpHeaders httpHeaders = new HttpHeaders();
        ResponseEntity<BalanceDTO> response = null;
        HttpEntity<String> requestEntity = null;
        try{
            httpHeaders.set("Authorization", "Bearer " + token);
            httpHeaders.set("Content-Type", "application/json");
            requestEntity = new HttpEntity<>(httpHeaders);
            response = restTemplate.exchange(urlBalance, HttpMethod.GET, requestEntity, BalanceDTO.class);
        }catch (Exception e){
            token = getToken();
            httpHeaders.set("Authorization", "Bearer " + token);
            httpHeaders.set("Content-Type", "application/json");
            requestEntity = new HttpEntity<>(httpHeaders);
            response = restTemplate.exchange(urlBalance, HttpMethod.GET, requestEntity, BalanceDTO.class);
        }
        return response.getBody().getBalances();
    }
    public void play(Integer dc, Integer tk){
        try{
            //dc
            DCTK dcApi = new DCTK(0, dc, DctkUtils.DCTK.coin, 4);
            //tk
            DCTK tkApi = new DCTK(0, tk, DctkUtils.DCTK.coin, 4);
            ResponseDctk responseDc = callApi(dcApi);
            System.out.println("===================Response DC: " + responseDc.getMessage());
            Thread.sleep(15  * 1000);
            ResponseDctk responseTk = callApi(tkApi);
            System.out.println("===================Response TK: " + responseTk.getMessage());

        }catch (Exception e){
            System.out.printf("===============================ERROROOOOOOOOO" + e);
        }
    }
    public ResponseDctk callApi(DCTK bodyObj){
        HttpHeaders httpHeaders = new HttpHeaders();
        ResponseEntity<ResponseDctk> response = null;
        HttpEntity<DCTK> requestEntity = null;
        try{
            httpHeaders.set("Authorization", "Bearer " + token);
            httpHeaders.set("Content-Type", "application/json");
            requestEntity = new HttpEntity<>(bodyObj, httpHeaders);
            response = restTemplate.exchange(urlPlay, HttpMethod.POST, requestEntity, ResponseDctk.class);
        }catch (Exception e){
            token = getToken();
            httpHeaders.set("Authorization", "Bearer " + token);
            httpHeaders.set("Content-Type", "application/json");
            requestEntity = new HttpEntity<>(bodyObj, httpHeaders);
            response = restTemplate.exchange(urlPlay, HttpMethod.POST, requestEntity, ResponseDctk.class);
        }
        return response.getBody();
    }
    public void sendMail(String text){
        // Cấu hình SMTP server
        String host = "smtp.gmail.com";
        final String user = "lecuong220120@gmail.com"; // Thay thế bằng email của bạn
        final String password = "whye puod tlxo oxmq"; // Thay thế bằng mật khẩu email của bạn

        // Thiết lập thuộc tính cho SMTP
        Properties properties = new Properties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "587"); // Cổng SMTP cho Gmail
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true"); // Bật TLS

        // Tạo phiên làm việc với cấu hình đã thiết lập
        Session session = Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, password);
            }
        });
        try {
            // Tạo email message
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(user));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject("Thông báo từ hệ thống phát triển bởi Nê");
            message.setText(text);

            // Gửi email
            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
    public void playNow() throws InterruptedException {
        isRun = true;
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        DctkService dctkService  = new DctkService();
        while(isRun){
            History history = dctkService.getHistory().getHistories().get(0);
            if(dctkService.history == null || !Objects.equals(history.getId(),dctkService.history.getId()) ){
                dctkService.autoPlay();
            }
            System.out.println("Thread sleep 20s: " + dateFormat.format(new Date(System.currentTimeMillis())));
            Thread.sleep(20 * 1000);
            System.out.println("Thread start at " + dateFormat.format(new Date(System.currentTimeMillis())));
        }
        if(!isRun){
            System.out.println("================STOPPPP DCTK");
        }
    }
    public void stop(){
        isRun = false;
    }
}
