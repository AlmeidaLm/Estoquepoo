package lucasAlmeidaMonteiro.estoqueComProdutoPerecivel;

import java.util.Date;
public class Lote {
        int quanti;
        Date valid;
        public Lote(int quant, Date val){
            quanti = quant;
            valid = val;
        }

    public Date getValid() {
        return valid;
    }

    public int getQuanti() {
        return quanti;
    }
}
