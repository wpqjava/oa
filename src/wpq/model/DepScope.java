package wpq.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "t_dep_scope")
public class DepScope {
	private int id;
	private int depId;
	private Department toDep;
	@Id
	@GeneratedValue
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	@Column(name="dep_id")
	public int getDepId() {
		return depId;
	}

	public void setDepId(int depId) {
		this.depId = depId;
	}
	@ManyToOne
	@JoinColumn(name="to_dep_id")
	public Department getToDep() {
		return toDep;
	}

	public void setToDep(Department toDep) {
		this.toDep = toDep;
	}

}
