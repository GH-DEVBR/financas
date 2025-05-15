package com.ghdev.financas.repository;

import com.ghdev.financas.model.Lancamento;
import com.ghdev.financas.model.TipoLancamento;
import com.ghdev.financas.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long> {
    List<Lancamento> findAllByUsuario(Usuario usuario);

    List<Lancamento> filtrar(Usuario usuario, TipoLancamento tipo, Integer mes, Integer ano, String categoria);
}

