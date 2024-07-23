package br.gov.ce.pge.gestao.configs;

import br.gov.ce.pge.gestao.dao.DevedorDao;
import br.gov.ce.pge.gestao.dao.DividaDao;
import lombok.Getter;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class DaoConfig {
  private final DividaDao dividaDao;
  private final DevedorDao devedorDao;

  public DaoConfig(DividaDao dividaDao, DevedorDao devedorDao) {
    this.dividaDao = dividaDao;
    this.devedorDao = devedorDao;
  }
}
