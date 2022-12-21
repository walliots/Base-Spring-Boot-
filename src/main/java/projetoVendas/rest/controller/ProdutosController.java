package projetoVendas.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import projetoVendas.domain.entity.Cliente;
import projetoVendas.domain.entity.Produto;
import projetoVendas.domain.repository.Produtos;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/produtos")
public class ProdutosController {

    @Autowired
    private Produtos produtos;

    @GetMapping("{id}")
    public Produto getProdutosById(@PathVariable Integer id){
        return produtos.findById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado"));
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Produto save(@RequestBody @Valid Produto produto){
        return produtos.save(produto);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id){
        produtos.findById(id).map(produto -> {
            produtos.delete(produto);
            return produto;
        }).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado"));
    }
    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable Integer id,@RequestBody @Valid Produto produto){
        produtos.findById(id).map(produtoExistente -> {
            produto.setId(produtoExistente.getId());
            produtos.save(produto);
            return  produtoExistente;
        }).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Produto não encontrado"));
    }
    @GetMapping
    public List<Cliente> find (Produto filtro){
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example example = Example.of(filtro, matcher);
        return produtos.findAll(example);
    }
}
