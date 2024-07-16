package com.foro.ForoAlura.usuarios;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;


@Table(name = "usuarios")
@Entity(name = "Usuario")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Usuario implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String email;
    private String clave;


    public Usuario(DatosRegistrarUsuario datosRegistrarUsuario){
        this.nombre = datosRegistrarUsuario.nombre();
        this.email = datosRegistrarUsuario.email();
        this.clave = datosRegistrarUsuario.clave();
    }

    public void actualizarDatos(ActualizarUsuario actualizarUsuario) {
        if (actualizarUsuario.nombre() != null) {
            this.nombre = actualizarUsuario.nombre();
        }
        if (actualizarUsuario.email() != null) {
            this.email = actualizarUsuario.email();
        }
        if (actualizarUsuario.contrasena() != null) {
            this.clave = actualizarUsuario.contrasena();
        }
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    public String getPassword() {
        return clave;
    }

    public String getUsername() {
        return email;
    }

    public boolean isAccountNonExpired() {
        return true;
    }

    public boolean isAccountNonLocked() {
        return true;
    }

    public boolean isCredentialsNonExpired() {
        return true;
    }

    public boolean isEnabled() {
        return true;
    }
}

