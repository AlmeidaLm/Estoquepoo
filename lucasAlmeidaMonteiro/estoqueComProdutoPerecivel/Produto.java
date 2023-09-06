package lucasAlmeidaMonteiro.estoqueComProdutoPerecivel;

import lucasAlmeidaMonteiro.estoqueComProdutoPerecivel.Fornecedor;

import java.util.Date;

public class Produto {
    private  int cod;
    private String desc;
    private int min;
    private double lucro;
    private Fornecedor forn;
    private int quant;
    private double prcomp;
    private double prcvend;
    public Produto(int cod, String desc, int min, double lucro, Fornecedor forn){
        int  a;
        String b;
        b = forn.getNome();
        a = forn.getCnpj();
        if((cod>0)&&(min>0)&&(lucro>=0)&&(desc!=null)&&(a!= 0)&&(b!=null)&& (!b.equals(" "))){
            this.cod = cod;
            this.desc = desc;
            this.min = min;
            this.lucro = lucro;
            this.forn = forn;
        }
    }
    public void compraProduto(int quant, double valor){
        int oldquant = this.quant;
        double atual;
        this.quant += quant;
        prcomp = (oldquant * prcomp + quant * valor)/this.quant;
        atual = prcomp * lucro;
        prcvend = atual + prcomp;
    }
    public double venderProduto(int quanti){
        if(quanti <= 0){
            return -1;
        }else{
            quant -= quanti;
            return quanti * prcvend;
        }
    }

    public double getLucro() {
        return lucro;
    }

    public double getPrcomp() {
        return prcomp;
    }

    public int getCodigo() {
        return cod;
    }
    public int getMin() {
        return min;
    }
    public Fornecedor getForn() {
        return forn;
    }
    public int getQuant(){
        return quant;
    }
    public int setQuant(int tot){
        this.quant = tot;
        return quant;
    }

    public void setPrcomp(double prcomp) {
        this.prcomp = prcomp;
    }

    public void setPrcvend(double prcvend) {
        this.prcvend = prcvend;
    }

    public double getPrcvend() {
        return prcvend;
    }

    public String getDesc() {
        return desc;
    }
}
