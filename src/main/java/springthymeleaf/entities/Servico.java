
package springthymeleaf.entities;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Servico implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String descricao;
    private Double valor;

        //Um para Muitos - Um Servico Para Muitas Ordem de Servicos - usar o mappedby para mapear o servicos la da classe OrdemServico - o cascade = cascadeType.ALL é para quando for deletar um cliente ou um servico, conseguir deletar tranquilamente

    @OneToMany(mappedBy = "servico", cascade = CascadeType.ALL)
    private List<OrdemItem> orderItem;


    public Servico() {
    }

    public Servico(Long id, String nome, String descricao, Double valor, List<OrdemItem> orderItem) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.valor = valor;
        this.orderItem = orderItem;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Servico other = (Servico) obj;
        return Objects.equals(this.id, other.id);
    }

    public List<OrdemItem> getOrderItem() {
        return orderItem;
    }

    public void setOrderItem(List<OrdemItem> orderItem) {
        this.orderItem = orderItem;
    }
    
    
}
