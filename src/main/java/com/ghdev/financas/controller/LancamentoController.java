package com.ghdev.financas.controller;

import com.ghdev.financas.dto.*;
import com.ghdev.financas.model.*;
import com.ghdev.financas.service.LancamentoService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lancamentos")
@RequiredArgsConstructor
public class LancamentoController {

    private final LancamentoService service;

    @PostMapping
    public Lancamento criar(@RequestBody LancamentoDTO dto, HttpServletRequest request) {
        Usuario usuario = (Usuario) request.getUserPrincipal();
        return service.salvar(dto, usuario);
    }

    @GetMapping
    public List<Lancamento> listar(HttpServletRequest request) {
        Usuario usuario = (Usuario) request.getUserPrincipal();
        return service.listarPorUsuario(usuario);
    }

    @GetMapping("/{id}")
    public Lancamento buscar(@PathVariable Long id, HttpServletRequest request) {
        Usuario usuario = (Usuario) request.getUserPrincipal();
        return service.buscarPorId(id, usuario);
    }

    @PutMapping("/{id}")
    public Lancamento atualizar(@PathVariable Long id, @RequestBody LancamentoDTO dto, HttpServletRequest request) {
        Usuario usuario = (Usuario) request.getUserPrincipal();
        return service.atualizar(id, dto, usuario);
    }

    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Long id, HttpServletRequest request) {
        Usuario usuario = (Usuario) request.getUserPrincipal();
        service.excluir(id, usuario);
    }

    @GetMapping("/filtrar")
    public List<Lancamento> filtrar(
            @RequestParam(required = false) TipoLancamento tipo,
            @RequestParam(required = false) Integer mes,
            @RequestParam(required = false) Integer ano,
            @RequestParam(required = false) String categoria,
            HttpServletRequest request) {

        Usuario usuario = (Usuario) request.getUserPrincipal();
        return service.filtrar(usuario, tipo, mes, ano, categoria);
    }

    @GetMapping("/resumo")
    public ResumoFinanceiroDTO resumo(HttpServletRequest request) {
        Usuario usuario = (Usuario) request.getUserPrincipal();
        return service.gerarResumo(usuario);
    }
}
