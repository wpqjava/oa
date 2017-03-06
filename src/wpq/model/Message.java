package wpq.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "t_msg")
public class Message {
	private int id;
	private String title;
	private String contend;
	private User user;//发件人
	private Date createDate;
	
	public Message() {
	}
	
	
	public Message(int id, String title, String contend, Date createDate) {
		super();
		this.id = id;
		this.title = title;
		this.contend = contend;
		this.createDate = createDate;
	}

	@ManyToOne
	@JoinColumn(name="user_id")
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Id
	@GeneratedValue
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	@Column(columnDefinition="text")
	public String getContend() {
		return contend;
	}

	public void setContend(String contend) {
		this.contend = contend;
	}
	@Column(name="create_date")
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

}
