package springthymeleaf.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class StatusOrdemServico implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String status;

    @OneToMany(mappedBy = "statusOrdemServico", cascade = CascadeType.ALL)
    private List<OrdemServico> ordemServico;

    public StatusOrdemServico() {
    }

    public StatusOrdemServico(Long id, String status, List<OrdemServico> ordemServico) {
        this.id = id;
        this.status = status;
        this.ordemServico = ordemServico;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<OrdemServico> getOrdemServico() {
        return ordemServico;
    }

    public void setOrdemServico(List<OrdemServico> ordemServico) {
        this.ordemServico = ordemServico;
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
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        StatusOrdemServico other = (StatusOrdemServico) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    
    

    
    
}
