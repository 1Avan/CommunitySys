package com.ivan.communitymanagementsys;

import com.ivan.communitymanagementsys.context.BaseContext;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.SecretKey;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class CommunityManagementSysApplicationTests {

    @Test
    void contextLoads() {
    }

    /**
     * 测试jwt
     */
    @Test
    public void testGenJwt(){
        Map<String,Object> claim = new HashMap<>();
        claim.put("name","ivan");
        claim.put("id",1);
        //1.自定义密钥
        SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode("ivan1111111111111111111111111111111111111111111"));
//        2.自动生成密钥
//        SecretKey key = Jwts.SIG.HS256.key().build();
        String jwt = Jwts.builder()
                .claims(claim)//自定义内容（载荷）
                .expiration(new Date(System.currentTimeMillis() + 3600 * 1000))
                .signWith(key, Jwts.SIG.HS256)//算法签名,(密钥，加密算法)
                .compact();//返回字符串类型的jwt
        System.out.println(jwt);
    }
    /**
     * 解析jwt令牌
     */
/*    @Test
    public void testParseJwt(){
//        Jwts.parser().
        CharSequence jwt = "eyJhbGciOiJIUzI1NiJ9.eyJuYW1lIjoiaXZhbiIsImlkIjoxLCJleHAiOjE3MTAxNzAzNzB9.FgzravctAcBXt6UGyXws36bhQBjHlFMO97fRsrpwYQQ";
        //设置解析令牌密钥
        SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode("ivan1111111111111111111111111111111111111111111"));
        //解析令牌
        Claims claims = Jwts.parser()
                .verifyWith(key)//传递密钥
                .build()
                .parseSignedClaims(jwt)//传递jwt令牌
                .getPayload();//获取第二部分：Payload(有效载荷），携带一些自定义信息、默认信息等

        System.out.println(claims);

    }*/

    /**
     * 测试md5加密
     */
    @Test
    public void testMd5(){
        String s = DigestUtils.md5DigestAsHex("123456".getBytes());
        System.out.println(s);
    }
    /**
     * 测试Apache POI导出excel
     */

    @Test
    public void testPOI() throws IOException {

        //在内存中，创建一个excel文件
        XSSFWorkbook excel = new XSSFWorkbook();

        //在excel文件中创建sheet页
        XSSFSheet sheet = excel.createSheet("测试111");

        //在sheet页中创建行，rownum编号从0开始
        XSSFRow row = sheet.createRow(1);

        //创建单元格，并将数据写入单元格
        row.createCell(1).setCellValue("姓名");
        row.createCell(2).setCellValue("城市");

        //创建新行
        row = sheet.createRow(2);
        row.createCell(1).setCellValue("张三");
        row.createCell(2).setCellValue("北京");

        //再创建新行
        row = sheet.createRow(3);
        row.createCell(1).setCellValue("李四");
        row.createCell(2).setCellValue("南京");

        //创建输出流对象，将内存中的excel写入到磁盘
        FileOutputStream outputStream =  new FileOutputStream(new File("C:\\Users\\ivan's小新\\Desktop\\名单.xlsx"));
        excel.write(outputStream);

        //关闭资源
        outputStream.close();
        excel.close();
    }



}
