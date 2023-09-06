package lucasAlmeidaMonteiro.estoqueComProdutoPerecivel;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Estoque implements InterfaceEstoque {
    private ArrayList<Produto> produtos = new ArrayList<>();
    public boolean incluir(Produto p) throws DadosInvalidos {
        if ((p == null)|| (p.getDesc() == null) ||(p.getDesc().equals("    "))){
            throw new DadosInvalidos("Dados inválidos");
        }
        if (p.getForn() == null || p.getForn().getCnpj() <= 0 ){
            throw new DadosInvalidos("Dados inválidos");
        }
        int cod = p.getCodigo();
        try {
            if(pesquisar(cod)!= null) {
                throw new ProdutoJaCadastrado("O produto já existe");
            }
        } catch (ProdutoInexistente e) {
            if(p.getCodigo()>0) {
                produtos.add(p);
            }else {
                throw new DadosInvalidos("Dados inválidos");
            }
        }catch (ProdutoJaCadastrado e){
            System.out.println("\n");
        }
        return true;
    }

    public boolean comprar(int cod, int quant, double preco, Date val) throws ProdutoInexistente, DadosInvalidos, ProdutoNaoPerecivel,ProdutoVencido {
        Date dataat = new Date();
              if(val!= null){
                  if(val.before(dataat)){
                      throw new ProdutoVencido("Produto vencido!!!");
                  }
              }
              if(quant<=0|| preco <=0 || val == dataat){
                  throw new DadosInvalidos("Dados inválidos");
              }else {
                  Produto pro = pesquisar(cod);
                  if(val != null&& pro!= null) {
                      if (pro instanceof ProdutoPerecivel) {
                          ((ProdutoPerecivel)pro).compraProduto(quant, preco, val);
                          return true;
                      }else {
                          throw new ProdutoNaoPerecivel("Produtos não perecíveis não podem receber data");
                      }
                  }
                  else {
                      if (pro != null) {
                          pro.compraProduto(quant, preco);
                          return true;
                      }else {
                          return false;
                      }
                  }
              }

    }

    public double vender(int cod, int quant) throws ProdutoInexistente, DadosInvalidos, ProdutoVencido {
        Date val = new Date();
        if(quant<=0 || cod <= 0){
            throw new DadosInvalidos("Dados inválidos");
        }else {
            Produto pro = pesquisar(cod);
            if(pro == null){
                return -1;
            }else {
                if (pro instanceof ProdutoPerecivel) {
                    if (((ProdutoPerecivel)pro).getTotal() >= quant) {
                        return ((ProdutoPerecivel)pro).venderProduto(quant, val);
                    }
                }else {
                    if (pro.getQuant() >= quant) {
                        return pro.venderProduto(quant);
                    }
                }
            }
        }
        return  0;
    }

    public int quantidade(int cod) throws ProdutoInexistente {
        Produto pro = pesquisar(cod);
        if(pro!= null){
            return pro.getQuant();
        }
        return -1;
    }
    public Fornecedor fornecedor(int cod) throws ProdutoInexistente {
        Produto pro = pesquisar(cod);
        if(pro!= null){
           return pro.getForn();
        }
        return null;
    }
    public ArrayList<Produto> estoqueAbaixoDoMinimo(){
        if(produtos.size() == 0) {
            System.out.println("Sem produtos cadastrados");
        }else {
            ArrayList<Produto> abaixoprc = new ArrayList<>();
            for (Produto produto : produtos) {
               /* if (produto == null) {
                    break;
                }*/
                if(produto instanceof ProdutoPerecivel) {
                    if(((ProdutoPerecivel) produto).getTotal() < produto.getMin()){
                        abaixoprc.add(produto);
                    }
                }else {
                    if (produto.getQuant() < produto.getMin()) {
                        abaixoprc.add(produto);
                    }
                }
            }
            return abaixoprc;
        }
        return null;
    }

    @Override
    public ArrayList<Produto> estoqueVencido() {
        ArrayList<Produto> vencids = new ArrayList<>();
        for (Produto produto : produtos){
            if(produto instanceof ProdutoPerecivel){
                if(((ProdutoPerecivel) produto).getLotinval()!= null){
                    Date datatu = new Date();
                    List<Lote> lotiv = ((ProdutoPerecivel) produto).getLoteA();
                    Date data2 = new Date();
                    data2.setTime(data2.getTime() + 120000);
                    int i = 0;
                    while (i != lotiv.size()) {
                        if (lotiv.get(i).getValid().before(datatu)) {
                            if(i== 0) {
                                vencids.add(produto);
                            }else {
                                if (produto.getCodigo()!= vencids.get(i).getCodigo()){
                                    vencids.add(produto);
                                }
                            }
                        }
                        i++;
                    }
                }
            }
        }
        if(vencids.size()>0){
            return vencids;
        }
        return null;
    }
    @Override
    public int quantidadeVencidos(int cod) throws ProdutoInexistente {
        Produto qndvenc = pesquisar(cod);
        if(qndvenc==null){
            throw new ProdutoInexistente(cod);
        }
        if(qndvenc instanceof ProdutoPerecivel){
            List<Lote> p = ((ProdutoPerecivel) qndvenc).getLoteA();
            Date atu = new Date();
            int qntvenc =0;
            for (Lote lote : p) {
                if (lote.getValid().before(atu)) {
                    int aux;
                    aux = lote.getQuanti();
                     qntvenc = qntvenc + aux;
                }
            }
            return qntvenc;
        }else {
            throw new ProdutoInexistente(qndvenc);
        }
    }

    public Produto pesquisar(int cod) throws ProdutoInexistente {
        int i = 0;
        while (i < produtos.size()) {
            if (produtos.get(i).getCodigo() == cod) {
                return produtos.get(i);
            }
            i++;
        }
        throw new ProdutoInexistente(cod);
    }
}



