package lucasAlmeidaMonteiro.estoqueComProdutoPerecivel;
public class ProdutoInexistente extends Exception{
    public ProdutoInexistente(int cod){
        super("Este produto" + "[ "+ cod + " ]"+ "não existe");
    }public ProdutoInexistente(Produto p){
        super("O produto" + "["+p.getCodigo()+"]"+ "[ "+p.getDesc()+" ]" + "é não perecível");
    }

}
