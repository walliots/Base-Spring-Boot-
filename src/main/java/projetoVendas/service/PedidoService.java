package projetoVendas.service;

import projetoVendas.domain.entity.Pedido;
import projetoVendas.domain.enums.StatusPedido;
import projetoVendas.rest.dto.PedidoDTO;

import java.util.Optional;

public interface PedidoService {

    Pedido salvar(PedidoDTO dto);

    Optional<Pedido> obterPedidoCompleto(Integer id);

    void atualizaStatus(Integer id, StatusPedido statusPedido);

}
