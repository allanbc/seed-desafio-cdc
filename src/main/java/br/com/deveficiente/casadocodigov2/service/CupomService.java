package br.com.deveficiente.casadocodigov2.service;

import br.com.deveficiente.casadocodigov2.entity.Cupom;
import br.com.deveficiente.casadocodigov2.model.cupom.NovoCupomRequest;
import br.com.deveficiente.casadocodigov2.repository.CupomRepository;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.slf4j.LoggerFactory.getLogger;

@Service
public class CupomService {

    private static final Logger LOG = getLogger(CupomService.class);
    private final CupomRepository cupomRepository;

    public CupomService(CupomRepository cupomRepository) {
        this.cupomRepository = cupomRepository;
    }

    @Transactional
    public Cupom gerar(NovoCupomRequest request) {
        LOG.info("Cadastrando um cupom: {}", request);
        return cupomRepository.save(request.toRequestCupom());
    }
}
