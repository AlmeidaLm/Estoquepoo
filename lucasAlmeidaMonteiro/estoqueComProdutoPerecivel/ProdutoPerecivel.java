package lucasAlmeidaMonteiro.estoqueComProdutoPerecivel;

import java.util.ArrayList;
import java.util.Date;// criar funções conversoras no main.
import java.util.List;

public class ProdutoPerecivel extends Produto {
    int total;// soma dos lotes;
    List<Lote> LoteA = new ArrayList<>();
    List<Lote> LotVal = new ArrayList<>();
    List<Lote> Lotinval = new ArrayList<>();
    public ProdutoPerecivel(int cod, String desc, int min, double lucro, Fornecedor forn){
        super(cod, desc, min, lucro, forn);
    }
    public void compraProduto(int quant, double valor, Date val) {
        int oldtot = this.total;
        Lote loti = new Lote(quant,val);
        double atu;
        LoteA.add(loti) ;
        this.total += loti.getQuanti();
        double pcomp = (oldtot * getPrcomp() + quant * valor)/this.total;
        setPrcomp(pcomp);
        atu = pcomp * getLucro();
        double novop = atu + pcomp;
        setPrcvend(novop);
    }
    public double venderProduto(int quanti,Date val) throws DadosInvalidos, ProdutoVencido {
        boolean c = false;
        int loca = quanti;
        if(quanti <=0){
            throw new DadosInvalidos("Dados inválidos");
        }
        if(quanti<= total){
            int i = 0;
            int b = LoteA.size();
            for (Lote Lotiatu : LoteA) {
                if (Lotiatu.getValid().after(val) || Lotiatu.getValid() == val) {
                    LotVal.add(Lotiatu);
                } else {
                    Lotinval.add(Lotiatu);
                    total = total - Lotiatu.getQuanti();
                }
            }
            boolean v = false;
            int n = 0;
            while(true){
                if(LotVal.size() == 0 && !v){
                    throw new ProdutoVencido("Produto vencido!!!");
                }
               Lote lotpcomp = encMenorData((ArrayList<Lote>) LotVal);
               if (lotpcomp== null && !v){
                   throw new ProdutoVencido("Produto vencido!!!");
               }
                int tamlot;
                if( lotpcomp != null) {
                    tamlot = lotpcomp.getQuanti();
                    if (loca >= tamlot) {
                        LotVal.remove(lotpcomp);
                        loca = loca - tamlot;
                        total = total - loca;
                        v = true;
                    } else {
                        loca = tamlot - loca;
                        Lote lotdes = new Lote(loca, lotpcomp.getValid());
                        LotVal.remove(lotpcomp);
                        LotVal.add(lotdes);
                        v = true;
                        break;
                    }
                }
                if(lotpcomp == null){
                    n++;
                }
                if(n==1){
                    break;
                }
            }
            int aux = 0;
            for (int o = 0; o < getLotVal().size();o++){
                Lote a = LotVal.get(o);
                aux = aux + a.getQuanti();
            }
            total = aux;
            setQuant(total);
        }else {
            System.out.println("A quantidade de produtos excede o disponível.");
        }
        return quanti*getPrcvend();
    }
    public Lote encMenorData(ArrayList<Lote> LotVal) {
        if(LotVal.size()>0) {
            Lote atu = LotVal.get(0);
            for (int i = 1; i < LotVal.size(); i++) {
                Lote b = LotVal.get(i);
                if (b.getValid().before(atu.getValid())) {
                    atu = b;
                }
            }
            return atu;
        }
        return null;
    }
    public void getProdutoPerecivel(){
        return ;
    }

    public List<Lote> getLoteA() {
        return LoteA;
    }

    public int getTotal() {
        return total;
    }

    public List<Lote> getLotinval() {
        return Lotinval;
    }

    public List<Lote> getLotVal() {
        return LotVal;
    }

}

