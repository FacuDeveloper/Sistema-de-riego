package model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;

@Entity
@Table(name="ESTADO_USUARIO")
public class UserStatus {

  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Column(name="USER_STATUS_ID")
  private int id;

  @Column(name="NAME", nullable=false)
  private String name;

  @Column(name="DESCRIPTION", nullable=false)
  private String description;

  // Constructor method
  public UserStatus() {

  }

	/**
	 * Returns value of id
	 * @return
	 */
	public int getId() {
		return id;
	}

	/**
	 * Sets new value of id
	 * @param
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Returns value of name
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets new value of name
	 * @param
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Returns value of description
	 * @return
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets new value of description
	 * @param
	 */
	public void setDescription(String description) {
		this.description = description;
	}

}
