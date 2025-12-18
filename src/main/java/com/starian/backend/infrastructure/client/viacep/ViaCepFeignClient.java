package com.starian.backend.infrastructure.client.viacep;

import com.starian.backend.application.dto.response.ViaCepResponse;
import feign.Param;
import feign.RequestLine;

interface ViaCepFeignClient {
    @RequestLine("GET /{cep}/json")
    ViaCepResponse findByCep(@Param("cep") String cep);
}