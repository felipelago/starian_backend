package com.starian.backend.application.port;

import com.starian.backend.application.dto.response.ViaCepResponse;

public interface ViaCepClientPort {
    /**
     * Consulta o endereço correspondente ao CEP informado.
     *
     * @param cep O CEP a ser consultado.
     * @return Um objeto ViaCepResponse contendo os dados do endereço.
     */
    ViaCepResponse findByCep(String cep);
}
