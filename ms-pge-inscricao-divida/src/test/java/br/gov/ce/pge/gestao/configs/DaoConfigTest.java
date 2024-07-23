package br.gov.ce.pge.gestao.configs;

import br.gov.ce.pge.gestao.dao.DevedorDao;
import br.gov.ce.pge.gestao.dao.DividaDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DaoConfigTest {

  @Mock
  private DividaDao mockDividaDao;

  @Mock
  private DevedorDao mockDevedorDao;

  private DaoConfig daoConfig;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    daoConfig = new DaoConfig(mockDividaDao, mockDevedorDao);
  }

  @Test
  void test_constructor() {
    assertEquals(mockDividaDao, daoConfig.getDividaDao());
    assertEquals(mockDevedorDao, daoConfig.getDevedorDao());
  }
}
