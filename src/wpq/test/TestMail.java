package wpq.test;



import java.util.List;

import javax.activation.FileDataSource;
import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import wpq.util.MailUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/beans.xml")
public class TestMail {
	@Resource
	private JavaMailSender mailSender;
	
	@Test
	public void test(){
		try {
			MimeMessage msg = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(msg, true, "utf-8");
			helper.setFrom("wpq_java@sina.com");
			helper.setTo("2643996103@qq.com");
			helper.setSubject("整合SPRING邮件发送");
			helper.setText("<h1>11111</h1><img src='cid:ccc'/>", true);
			helper.addAttachment("1.jpg", new FileDataSource("D:/写真/煎蛋年度妹子图TOP1000/煎蛋年度妹子图TOP1000--福利吧fuliba.net_files/005vbOHfgw1ew5lrif5cqj309z0k40u9.jpg"));
			helper.addInline("ccc", new FileDataSource("D:/写真/煎蛋年度妹子图TOP1000/煎蛋年度妹子图TOP1000--福利吧fuliba.net_files/005vbOHfgw1ewdt4y0ye3j30cd0kuq4z.jpg"));
			mailSender.send(msg);
		} catch (MailException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void test01(){
		List<String> ls = MailUtil.list("<img src='http://img.178.com/wow/201611/272836202947/272836317670.jpg' />");
		for(String s:ls){
			System.out.println(s);
		}
	}
}
