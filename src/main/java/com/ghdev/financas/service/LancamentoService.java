package com.ghdev.financas.service;

import com.ghdev.financas.dto.*;
import com.ghdev.financas.model.*;
import com.ghdev.financas.repository.LancamentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LancamentoService {
    private final LancamentoRepository repo;

    public Lancamento salvar(LancamentoDTO dto, Usuario usuario) {
        Lancamento l = Lancamento.builder()
                .descricao(dto.getDescricao())
                .valor(dto.getValor())
                .data(dto.getData())
                .tipo(dto.getTipo())
                .categoria(dto.getCategoria())
                .usuario(usuario)
                .build();
        return repo.save(l);
    }

    public List<Lancamento> listarPorUsuario(Usuario usuario) {
        return repo.findAllByUsuario(usuario);
    }

    public Lancamento buscarPorId(Long id, Usuario usuario) {
        return repo.findById(id)
                .filter(l -> l.getUsuario().getId().equals(usuario.getId()))
                .orElseThrow(() -> new RuntimeException("Lançamento não encontrado"));
    }

    public Lancamento atualizar(Long id, LancamentoDTO dto, Usuario usuario) {
        Lancamento l = buscarPorId(id, usuario);
        l.setDescricao(dto.getDescricao());
        l.setValor(dto.getValor());
        l.setData(dto.getData());
        l.setTipo(dto.getTipo());
        l.setCategoria(dto.getCategoria());
        return repo.save(l);
    }

    public void excluir(Long id, Usuario usuario) {
        Lancamento l = buscarPorId(id, usuario);
        repo.delete(l);
    }

    public List<Lancamento> filtrar(Usuario usuario, TipoLancamento tipo, Integer mes, Integer ano, String categoria) {
        return repo.filtrar(usuario, tipo, mes, ano, categoria);
    }

    public ResumoFinanceiroDTO gerarResumo(Usuario usuario) {
        List<Lancamento> lancamentos = listarPorUsuario(usuario);

        BigDecimal receitas = lancamentos.stream()
                .filter(l -> l.getTipo() == TipoLancamento.RECEITA)
                .map(Lancamento::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal despesas = lancamentos.stream()
                .filter(l -> l.getTipo() == TipoLancamento.DESPESA)
                .map(Lancamento::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal saldo = receitas.subtract(despesas);

        return new ResumoFinanceiroDTO(receitas, despesas, saldo);
    }
}


