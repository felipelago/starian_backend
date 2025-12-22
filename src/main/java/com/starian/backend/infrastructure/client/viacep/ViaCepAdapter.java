package com.starian.backend.infrastructure.client.viacep;

import com.starian.backend.application.dto.response.ViaCepResponse;
import com.starian.backend.application.port.ViaCepClientPort;
import com.starian.backend.domain.exception.BusinessException;
import com.starian.backend.infrastructure.client.FeignClientAdapter;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ViaCepAdapter extends FeignClientAdapter implements ViaCepClientPort {

    @Value("${viacep.base-url:https://viacep.com.br/ws}")
    private String baseUrl;

    private ViaCepFeignClient client;

    public ViaCepResponse findByCep(String cep) {
        if (client == null) {
            client = createClient(baseUrl, ViaCepFeignClient.class);
        }

        try {
            ViaCepResponse response = client.findByCep(cep);

            if (response == null || Boolean.TRUE.equals(response.erro())) {
                throw new BusinessException("CEP não encontrado: " + cep);
            }

            return response;

        } catch (FeignException.BadRequest ex) {
            throw new BusinessException("CEP inválido: " + cep);
        } catch (FeignException.NotFound ex) {
            throw new BusinessException("CEP não encontrado: " + cep);
        }
    }
}