package lucasAlmeidaMonteiro.estoqueComProdutoPerecivel;

public class Fornecedor {
    private int cnpj;
    private String nome;

    public Fornecedor(int cnpj, String nome) {
        if((cnpj > 0) && (nome != null)) {
            this.cnpj = cnpj;
            this.nome = nome;
        }else {
            System.out.println("Cnpj ou nome inválido");
        }
    }
    public int getCnpj () {
            return cnpj;
        }
    public String getNome () {
            return nome;
        }

}
