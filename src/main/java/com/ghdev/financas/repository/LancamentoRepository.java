package com.ghdev.financas.repository;

import com.ghdev.financas.model.Lancamento;
import com.ghdev.financas.model.TipoLancamento;
import com.ghdev.financas.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface LancamentoRepository extends JpaRepository<Lancamento, Long> {
    List<Lancamento> findAllByUsuario(Usuario usuario);

    @Query("SELECT l FROM Lancamento l WHERE l.usuario = :usuario " +
            "AND (:tipo IS NULL OR l.tipo = :tipo) " +
            "AND (:mes IS NULL OR FUNCTION('MONTH', l.data) = :mes) " +
            "AND (:ano IS NULL OR FUNCTION('YEAR', l.data) = :ano) " +
            "AND (:categoria IS NULL OR l.categoria = :categoria)")
    List<Lancamento> filtrar(
            @Param("usuario") Usuario usuario,
            @Param("tipo") TipoLancamento tipo,
            @Param("mes") Integer mes,
            @Param("ano") Integer ano,
            @Param("categoria") String categoria
    );
}


