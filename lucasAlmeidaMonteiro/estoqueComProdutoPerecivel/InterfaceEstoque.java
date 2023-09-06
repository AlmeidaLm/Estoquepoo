package lucasAlmeidaMonteiro.estoqueComProdutoPerecivel;

import java.util.ArrayList;
import java.util.Date;

public interface InterfaceEstoque {
// Retorna false se ouver algum campo numérico invalido ou texto vazio ou null ou produto já cadastrado.

    public boolean incluir(Produto p) throws DadosInvalidos,ProdutoJaCadastrado;

    // Retorna false se o produto não existir, se houver valores numéricos inválidos ou se enviar data e o produto comprado não for perecível

    public boolean comprar(int cod, int quant, double preco, Date val) throws ProdutoInexistente, DadosInvalidos, ProdutoNaoPerecivel, ProdutoVencido;



    // Retorna -1 se o produto não existir, se for vencido ou se houver um dado inválido em números.

    public double vender(int cod, int quant) throws ProdutoInexistente, DadosInvalidos,ProdutoVencido;



    // Retorna null se o produto não existir

    public Produto pesquisar(int cod) throws ProdutoInexistente;



    // Retorna lista de produtos abaixo do mínimo. Lista vazia se não tiver nenhum.

    public ArrayList<Produto> estoqueAbaixoDoMinimo();


    // Retorna lista de produtos vencidos. Lista vazia se não tiver nenhum.

    public ArrayList<Produto> estoqueVencido();



    // Retorna 0 se não houver nenhum produto vencido ou a quantidade de produtos vencidos de um determinado código.

    public int quantidadeVencidos(int cod) throws ProdutoInexistente;
}
