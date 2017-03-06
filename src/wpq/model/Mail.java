package wpq.model;

import java.util.List;

import org.springframework.mail.javamail.JavaMailSender;

import wpq.dto.AttachDto;
import wpq.util.MailUtil;

public class Mail implements Runnable {
	private Message msg;
	private Document doc;
	private AttachDto dto;
	private UserEmail ue;
	private List<String> toMails;
	private String[] newNames;
	private JavaMailSender mailSender;
	

	public Mail(Document doc, AttachDto dto, UserEmail ue, List<String> toMails, String[] newNames,
			JavaMailSender mailSender) {
		super();
		this.doc = doc;
		this.dto = dto;
		this.ue = ue;
		this.toMails = toMails;
		this.newNames = newNames;
		this.mailSender = mailSender;
	}


	public Mail(Message msg, AttachDto dto, UserEmail ue, List<String> toMails, String[] newNames,
			JavaMailSender mailSender) {
		super();
		this.msg = msg;
		this.dto = dto;
		this.ue = ue;
		this.toMails = toMails;
		this.newNames = newNames;
		this.mailSender = mailSender;
	}


	@Override
	public void run() {
		MailUtil.sendMail(msg, doc, dto, newNames,mailSender,ue,toMails);
	}

}
