package stateless;

import java.util.Collection;

import model.Usuario;
import model.UserStatus;

public interface UsuarioService {
  public Usuario find(int id);
  public Usuario findByUserName(String username);
  public Collection<Usuario> findAll();
  public Collection<Usuario> findAllUsuariosActivos();
  public Collection<Usuario> findAllUsuariosExcept(String username);
  public Usuario create(Usuario user);
  public Usuario update(int id, Usuario user);
  public Usuario updatePassword(int id, String oldPassword, String newPassword);
  public Usuario remove(int id, UserStatus downStatus);
  public Usuario validarUsuario(String usr, String pwd);
}
