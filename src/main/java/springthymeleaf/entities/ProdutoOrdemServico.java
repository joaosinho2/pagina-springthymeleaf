package springthymeleaf.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class ProdutoOrdemServico {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "ordemservico_id")
    private OrdemServico ordemServico;
    @ManyToOne
    @JoinColumn(name = "produto_id")
    private Produto produto; 

    public ProdutoOrdemServico(Long id, OrdemServico ordemServico, Produto produto) {
        this.id = id;
        this.ordemServico = ordemServico;
        this.produto = produto;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public OrdemServico getOrdemServico() {
        return ordemServico;
    }
    public void setOrdemServico(OrdemServico ordemServico) {
        this.ordemServico = ordemServico;
    }
    public Produto getProduto() {
        return produto;
    }
    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    
    
}
