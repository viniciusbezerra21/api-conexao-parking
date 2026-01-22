package conexao_parking.api.domain.usuario;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "usuario")
@Entity(name = "Usuario")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id_usuario")
public class Usuario {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_usuario;

    private String email_corporativo;
    private String senha;

    private Boolean ativo;

    public Usuario(DadosCadastroUsuario dados) {
        this.email_corporativo = dados.email_corporativo();
        this.senha = dados.senha();
        this.ativo = true;
    }

    public void atualizarInformacoes(@Valid DadosAtualizacaoUsuario dados) {
        if (dados.email_corporativo() != null) {
            this.email_corporativo = dados.email_corporativo();
        }
        if (dados.senha() != null) {
            this.senha = dados.senha();
        }
    }


    public void excluir() {
        this.ativo = false;
    }
}

