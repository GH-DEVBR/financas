package com.ghdev.financas.dto;

import com.ghdev.financas.model.TipoLancamento;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class LancamentoDTO {
    private String descricao;
    private BigDecimal valor;
    private LocalDate data;
    private TipoLancamento tipo;
    private String categoria;
}

