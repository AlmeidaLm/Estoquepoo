package lucasAlmeidaMonteiro.estoqueComProdutoPerecivel;
import org.junit.Assert;
import org.junit.Test;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.*;
public class Novoteste {
    @Test
    public void Compraprod() throws ProdutoInexistente, DadosInvalidos, ProdutoNaoPerecivel, ProdutoVencido {
        Estoque estoque = new Estoque();
        Fornecedor forn1 = new Fornecedor(48, "Nestle");
        Produto prod1 = new ProdutoPerecivel(12, "Sorvete", 5, 1, forn1);
        estoque.incluir(prod1);
        Date date = new Date(123,Calendar.AUGUST,13);
        Date date1 = new Date(123,Calendar.JULY,13);
        estoque.comprar(12,5,2,date);
        estoque.comprar(12,5,2,date1);
        estoque.comprar(12,5,2,date);
        estoque.vender(12,14);
        ArrayList<Produto> abaixoMinActual = estoque.estoqueAbaixoDoMinimo();
        assertEquals(1, abaixoMinActual.size());
    }
    @Test
    public void BuscaQuantidadecodinexistente()  {
        Estoque estoque = new Estoque();
        try {
            estoque.quantidade(34);
            fail("pesquisou onde não devia");
        }catch (ProdutoInexistente Exception){
            System.out.println("sucesso!");
        }
    }
    @Test
    public void Buscaforncomcodinexistente()  {
        Estoque estoque = new Estoque();
        try {
            estoque.fornecedor(34);
            fail("pesquisou onde não devia");
        }catch (ProdutoInexistente Exception){
            System.out.println("sucesso!");
        }
    }
    @Test
    public void incluirestoquetesteexp() throws ProdutoInexistente, DadosInvalidos {
        Estoque estoque = new Estoque();
        Fornecedor forn1 = new Fornecedor(48, "Nestle");
        Produto prod1 = new ProdutoPerecivel(12, "Sorvete", 5, 1, forn1);
        estoque.incluir(prod1);
        assertEquals(prod1,estoque.pesquisar(prod1.getCodigo()));
    }

    @Test
    public void testaestoqueminemestoquevazio() {
        Estoque estoque = new Estoque();
        Fornecedor forn1 = new Fornecedor(48, "Nestle");
        assertNull(estoque.estoqueAbaixoDoMinimo());
    }
    @Test
    public void testaestoqueTamUm() throws ProdutoInexistente, DadosInvalidos, ProdutoNaoPerecivel, ProdutoVencido {
        Estoque estoque = new Estoque();
        Fornecedor forn1 = new Fornecedor(48, "Nestle");
        Produto prod1 = new ProdutoPerecivel(12, "Sorvete", 5, 1, forn1);
        estoque.incluir(prod1);
        Date date = new Date(123,Calendar.AUGUST,9);
        estoque.comprar(12,20,22,date);
        ArrayList<Produto> prods = estoque.estoqueAbaixoDoMinimo();
        assertEquals(0,prods.size());
    }
    @Test
    public void qntvencidoscomCodigInexistenteNaoPereciv() {
        Estoque estoque = new Estoque();
        Fornecedor forn1 = new Fornecedor(48, "Nestle");
        Produto prod1 = new ProdutoPerecivel(12, "Sorvete", 5, 1, forn1);
        try {
            estoque.quantidadeVencidos(12);
            fail("Não era para ter pesquisado");
        }catch (ProdutoInexistente Exception){
            System.out.println("o produto com código inválido");
        }

    }
    @Test
    public void qntvencidosPereciv() throws ProdutoInexistente, DadosInvalidos {
        Estoque estoque = new Estoque();
        Fornecedor forn1 = new Fornecedor(48, "Nestle");
        Produto prod1 = new Produto(12, "Sorvete", 5, 1, forn1);
        estoque.incluir(prod1);
        try {
            estoque.quantidadeVencidos(12);
            fail("Não era para ter pesquisado");
        }catch (ProdutoInexistente Exception){
            System.out.println("o produto pesquisado é Não perecível");
        }
    }
    @Test
    public void testaquandidadedeNaovencidos() throws ProdutoInexistente, DadosInvalidos, ProdutoNaoPerecivel, ProdutoVencido {
        Estoque estoque = new Estoque();
        Fornecedor forn1 = new Fornecedor(48, "Nestle");
        Produto prod1 = new ProdutoPerecivel(12, "Sorvete", 5, 1, forn1);
        estoque.incluir(prod1);
        Date date = new Date(123, Calendar.AUGUST,23);
        estoque.comprar(12,5,5,date);
        estoque.comprar(12,5,5,date);
        estoque.comprar(12,5,5,date);
        estoque.quantidadeVencidos(12);
        assertEquals(0,estoque.quantidadeVencidos(12));
    }
    @Test
    public void testaquandComprodvencido() throws ProdutoInexistente, DadosInvalidos, ProdutoNaoPerecivel, ProdutoVencido {
        Estoque estoque = new Estoque();
        Fornecedor forn1 = new Fornecedor(48, "Unilever");
        Fornecedor forn2 = new Fornecedor(19, "Gilette");
        Fornecedor forn3 = new Fornecedor(33, "Nestle");
        Fornecedor forn4 = new Fornecedor(24, "Ambev");
        Produto prod1 = new Produto(12, "Shampoo", 5, 2, forn1);
        Produto prod2 = new Produto(13, "Aparelho de Barbear", 5, 1, forn2);
        Produto prod3 = new ProdutoPerecivel(14, "Sorvete", 5, 2, forn3);
        Produto prod4 = new ProdutoPerecivel(15, "Cerveja", 5, 1, forn4);
        Produto prod5 = new ProdutoPerecivel(16, "Cerveja Pilsen", 5, 1, forn4);
        Date data1 = Date.from(Instant.now(Clock.system(ZoneId.of("America/Sao_Paulo"))));
        data1.setTime(data1.getTime() + 120000);
        Date data2 = new Date();
        data2.setTime(data1.getTime());
        estoque.incluir(prod1);
        estoque.incluir(prod2);
        estoque.incluir(prod3);
        estoque.incluir(prod4);
        estoque.incluir(prod5);
        estoque.comprar(prod1.getCodigo(), 10, 5, null);
        estoque.comprar(prod2.getCodigo(), 30, 2.5, null);
        estoque.comprar(prod3.getCodigo(), 10, 5, data1);
        estoque.comprar(prod3.getCodigo(), 20, 5, data2);
        estoque.comprar(prod4.getCodigo(), 5, 2.5, data2);
        estoque.comprar(prod4.getCodigo(), 5, 2.5, data2);
        estoque.comprar(prod5.getCodigo(), 5, 2.5, data1);
        estoque.comprar(prod5.getCodigo(), 5, 2.5, data1);
        data1.setTime(data1.getTime() - 120000);

    }
}
