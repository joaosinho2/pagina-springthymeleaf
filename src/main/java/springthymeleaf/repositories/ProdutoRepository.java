
package springthymeleaf.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import springthymeleaf.entities.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long>{
    
}
