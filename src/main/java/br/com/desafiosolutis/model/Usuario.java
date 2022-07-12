package br.com.desafiosolutis.model;


import br.com.desafiosolutis.model.enumereted.EnumRole;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@Builder
@Entity
@Table(name = "tbl_usuario")
@AllArgsConstructor
@NoArgsConstructor
public class Usuario implements UserDetails {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "cpf_usuario")
	private String cpfUsuario;

	@Column(name = "nome_usuario")
	private String nome;

	@Column(unique = true)
	private String email;

	@Column(name = "senha")
	private String senha;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "EnumRole")
	private EnumRole tipo;

	@OneToMany
	@JsonIgnoreProperties("usuario")
	private List<Voto> listaDeVotos = new ArrayList<>();


	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
	}

	@Override
	public String getPassword() {
		return this.senha;
	}

	@Override
	public String getUsername() {
		return this.email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
