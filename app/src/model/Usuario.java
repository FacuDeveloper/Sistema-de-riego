package model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import java.util.Calendar;

@Entity
public class Usuario  {

  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Column(name="USUARIO_ID")
  private int id;

  @Column(name="USUARIO", nullable=false, unique=true)
  private String usuario;

  @Column(name="PASSWORD", nullable=false)
  private String password;

  @Column(name="NOMBRE")
  private String nombre;

  @Column(name="APELLIDO")
  private String apellido;

  @Column(name="DNI")
  private String dni;

  @Column(name="DIRECCION")
  private String direccion;

  @Column(name="TELEFONO")
  private String telefono;

  @Column(name="EMAIL")
  private String email;

  @Column(name="ESTADO", nullable=true)
  private String estado;

  @Column(name="FECHA_ALTA")
  @Temporal(TemporalType.DATE)
  private Calendar fechaAlta;

  @Column(name="FECHA_BAJA")
  @Temporal(TemporalType.DATE)
  private Calendar fechaBaja;

  @Column(name="SUPER_USUARIO")
  private boolean superUsuario;

  // Constructor method
	public Usuario() {

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
	* Returns value of usuario
	* @return
	*/
	public String getUsuario() {
		return usuario;
	}

	/**
	* Sets new value of usuario
	* @param
	*/
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	/**
	* Returns value of password
	* @return
	*/
	public String getPassword() {
		return password;
	}

	/**
	* Sets new value of password
	* @param
	*/
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	* Returns value of nombre
	* @return
	*/
	public String getNombre() {
		return nombre;
	}

	/**
	* Sets new value of nombre
	* @param
	*/
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	* Returns value of apellido
	* @return
	*/
	public String getApellido() {
		return apellido;
	}

	/**
	* Sets new value of apellido
	* @param
	*/
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	/**
	* Returns value of dni
	* @return
	*/
	public String getDni() {
		return dni;
	}

	/**
	* Sets new value of dni
	* @param
	*/
	public void setDni(String dni) {
		this.dni = dni;
	}

	/**
	* Returns value of direccion
	* @return
	*/
	public String getDireccion() {
		return direccion;
	}

	/**
	* Sets new value of direccion
	* @param
	*/
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	/**
	* Returns value of telefono
	* @return
	*/
	public String getTelefono() {
		return telefono;
	}

	/**
	* Sets new value of telefono
	* @param
	*/
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	/**
	* Returns value of email
	* @return
	*/
	public String getEmail() {
		return email;
	}

	/**
	* Sets new value of email
	* @param
	*/
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	* Returns value of estado
	* @return
	*/
	public String getEstado() {
		return estado;
	}

	/**
	* Sets new value of estado
	* @param
	*/
	public void setEstado(String estado) {
		this.estado = estado;
	}

	/**
	* Returns value of fechaAlta
	* @return
	*/
	public Calendar getFechaAlta() {
		return fechaAlta;
	}

	/**
	* Sets new value of fechaAlta
	* @param
	*/
	public void setFechaAlta(Calendar fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	/**
	* Returns value of fechaBaja
	* @return
	*/
	public Calendar getFechaBaja() {
		return fechaBaja;
	}

	/**
	* Sets new value of fechaBaja
	* @param
	*/
	public void setFechaBaja(Calendar fechaBaja) {
		this.fechaBaja = fechaBaja;
	}

	/**
	* Returns value of superUsuario
	* @return
	*/
	public boolean isSuperUsuario() {
		return superUsuario;
	}

	/**
	* Sets new value of superUsuario
	* @param
	*/
	public void setSuperUsuario(boolean superUsuario) {
		this.superUsuario = superUsuario;
	}

}
