package springthymeleaf.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class OrdemServico implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Temporal(TemporalType.DATE)
    private Date dataInicial = new Date();
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date dataFinal;

    private String descricao;
    private String defeito;
    private String observacoes;
    private String laudotecnico;
    private int garantia;

    //@Enumerated -> vai ser enumerada e com o EnumType.STRING vai fazer ele vir em forma de Texto no banco de Dados!
    //@Enumerated(EnumType.STRING)
    //private StatusOrdemServico statusOrdemServico;
    //Muitos para UM - Muitas Listas de Compras para um Cliente -  e la na classe Cliente tem que usar o @OneToMany(mappedby = "cliente")
    //@JoinColumn(name = "") -> Especifica qual nome da foreign key
    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private StatusOrdemServico statusOrdemServico;

    @ManyToOne
    @JoinColumn(name = "tecnico_id")
    private Tecnico tecnico;

    @OneToMany(mappedBy = "ordemServico", cascade = CascadeType.ALL)
    private List<ProdutoOrdem> produtoOrdem;

    @OneToMany(mappedBy = "ordemServico", cascade = CascadeType.ALL)
    private List<ServicoOrdem> servicoOrdem;

    public OrdemServico() {
    }

    public OrdemServico(Long id, Date dataInicial, Date dataFinal, String descricao, String defeito, String observacoes,
            String laudotecnico, int garantia, Cliente cliente, StatusOrdemServico statusOrdemServico, Tecnico tecnico,
            List<ProdutoOrdem> produtoOrdem, List<ServicoOrdem> servicoOrdem) {
        this.id = id;
        this.dataInicial = dataInicial;
        this.dataFinal = dataFinal;
        this.descricao = descricao;
        this.defeito = defeito;
        this.observacoes = observacoes;
        this.laudotecnico = laudotecnico;
        this.garantia = garantia;
        this.cliente = cliente;
        this.statusOrdemServico = statusOrdemServico;
        this.tecnico = tecnico;
        this.produtoOrdem = produtoOrdem;
        this.servicoOrdem = servicoOrdem;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDataInicial() {
        return dataInicial;
    }

    public void setDataInicial(Date dataInicial) {
        this.dataInicial = dataInicial;
    }

    public Date getDataFinal() {
        return dataFinal;
    }

    public void setDataFinal(Date dataFinal) {
        this.dataFinal = dataFinal;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getDefeito() {
        return defeito;
    }

    public void setDefeito(String defeito) {
        this.defeito = defeito;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public String getLaudotecnico() {
        return laudotecnico;
    }

    public void setLaudotecnico(String laudotecnico) {
        this.laudotecnico = laudotecnico;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public StatusOrdemServico getStatusOrdemServico() {
        return statusOrdemServico;
    }

    public void setStatusOrdemServico(StatusOrdemServico statusOrdemServico) {
        this.statusOrdemServico = statusOrdemServico;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
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
        OrdemServico other = (OrdemServico) obj;
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        return true;
    }

    public int getGarantia() {
        return garantia;
    }

    public void setGarantia(int garantia) {
        this.garantia = garantia;
    }

    public Tecnico getTecnico() {
        return tecnico;
    }

    public void setTecnico(Tecnico tecnico) {
        this.tecnico = tecnico;
    }

    public List<ProdutoOrdem> getProdutoOrdem() {
        return produtoOrdem;
    }

    public void setProdutoOrdem(List<ProdutoOrdem> produtoOrdem) {
        this.produtoOrdem = produtoOrdem;
    }

    public List<ServicoOrdem> getServicoOrdem() {
        return servicoOrdem;
    }

    public void setServicoOrdem(List<ServicoOrdem> servicoOrdem) {
        this.servicoOrdem = servicoOrdem;
    }

    

   

}
