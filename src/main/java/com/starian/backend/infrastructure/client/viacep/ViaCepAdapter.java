package com.starian.backend.infrastructure.client.viacep;

import com.starian.backend.application.dto.response.ViaCepResponse;
import com.starian.backend.application.port.ViaCepClientPort;
import com.starian.backend.domain.exception.BusinessException;
import com.starian.backend.infrastructure.client.FeignClientAdapter;
import feign.FeignException;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ViaCepAdapter extends FeignClientAdapter implements ViaCepClientPort {
    private static final Logger logger = LoggerFactory.getLogger(ViaCepAdapter.class);

    @Value("${viacep.base-url:https://viacep.com.br/ws}")
    private String baseUrl;

    private ViaCepFeignClient client;

    @PostConstruct
    protected void init() {
        logger.info("Inicializando ViaCep client com base URL: {}", baseUrl);
        this.client = createClient(baseUrl, ViaCepFeignClient.class);
    }

    public ViaCepResponse findByCep(String cep) {
        logger.debug("Buscando CEP: {}", cep);
        try {
            ViaCepResponse response = client.findByCep(cep);

            if (response == null || Boolean.TRUE.equals(response.erro())) {
                logger.warn("CEP não encontrado: {}", cep);
                throw new BusinessException(String.format("CEP não encontrado: %s", cep));
            }

            logger.debug("CEP encontrado com sucesso: {}", cep);
            return response;

        } catch (FeignException.BadRequest ex) {
            logger.error("CEP inválido: {}", cep, ex);
            throw new BusinessException(String.format("CEP inválido: %s", cep));
        } catch (FeignException.NotFound ex) {
            logger.warn("CEP não encontrado na API: {}", cep);
            throw new BusinessException(String.format("CEP não encontrado: %s", cep));
        }
    }
}