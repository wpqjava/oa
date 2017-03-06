package wpq.util;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.activation.FileDataSource;
import javax.activation.URLDataSource;
import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.hibernate.pretty.MessageHelper;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import wpq.dto.AttachDto;
import wpq.model.Document;
import wpq.model.Message;
import wpq.model.UserEmail;

public class MailUtil {

	public static void sendMail(Message message, Document doc, AttachDto dto, String[] newNames,JavaMailSender mailSender,UserEmail ue,List<String> toMails) {
		try {
			JavaMailSenderImpl jim = (JavaMailSenderImpl)mailSender;
			MimeMessage msg = jim.createMimeMessage();
			jim.setHost(ue.getHost());
			jim.setProtocol("smtp");
			jim.setUsername(ue.getUsername());
			jim.setPassword(ue.getPassword());
			Properties javaMailProperties = new Properties();
			javaMailProperties.setProperty("mail.smtp.auth", "true");
			javaMailProperties.setProperty("mail.debug", "true");
			jim.setJavaMailProperties(javaMailProperties);
			MimeMessageHelper hp = new MimeMessageHelper(msg, true, "utf-8");
			hp.setFrom(ue.getUser().getEmail());
			for(String s:toMails){
				hp.addTo(s);
			}
			String content = null;
			String title = null;
			if (doc == null) {
				content = message.getContend();
				title = message.getTitle();
			} else {
				content = doc.getContent();
				title = doc.getTitle();
			}
			hp.setSubject(title);
			List<String> imgs = list(content);
			hp.setText(dealWithContent(content,imgs),true);
			for(String s:imgs){
				if(s.startsWith("http")){
					hp.addInline(s, new URLDataSource(new URL(s)));
				}else{
					String path = "D:/CodeSoftware/STS/workspace/oa_document01/WebContent/"+s;
					hp.addInline(s, new FileDataSource(new File(path)));
				}
			}
			if (dto.isHasAttach()) {
				String[] fileName = dto.getAttachFileName();
				for (int i = 0; i < fileName.length; i++) {
					hp.addAttachment(fileName[i], new FileDataSource(
							"D:/CodeSoftware/STS/workspace/oa_document01/WebContent/upload/" + newNames[i]));
				}

			}
			jim.send(msg);
		} catch (MailException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
	
	private static String dealWithContent(String content,List<String> imgs) {
		if(imgs.size()>0){
			for(String s:imgs){
				content = content.replace(s, "cid:"+s);
			}
		}
		return content;
	}

	public static List<String> list(String content){
		Pattern p = Pattern.compile("<img.*?\\s+src=['\"](.*?)['\"].*?>");
		Matcher m = p.matcher(content);
		List<String> srcs = new ArrayList<String>();
		while(m.find()) {
			srcs.add(m.group(1));
		}
		return srcs;
	}
}
