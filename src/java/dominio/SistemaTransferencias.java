/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dominio;

import java.util.ArrayList;

/**
 *
 * @author william
 */
public class SistemaTransferencias {
    private ArrayList<Transferencia> transferenciasRespondidas = new ArrayList<>();
    private ArrayList<Transferencia> transferenciasPendientes = new ArrayList<>();

    public ArrayList<Transferencia> getTransferenciasPendientes() {
        return transferenciasPendientes;
    }
    public ArrayList<Transferencia> getTransferenciasRespondidas() {
        return transferenciasRespondidas;
    }
    public Transferencia verTransferenciasPendientes() {
        if(transferenciasPendientes.size()>0){
            return transferenciasPendientes.get(0);
        }else return null;
    }

    public void agregarTransferenciaPendiente(Transferencia trans) throws RestaurantException{
        if(trans.isValida())transferenciasPendientes.add(trans);
        else throw new RestaurantException("Destino = Origen");
        
    }/*
    public void agregarTransferenciaRespondidas(Transferencia trans) {
        transferenciasRespondidas.add(trans);
    }*/

    public Transferencia verTransferenciasPendientesPorMozo(Mozo m) {
        for(Transferencia t:transferenciasPendientes){
            Mozo destino = t.getDestino();
            if(destino.equals(m)){
                return t;
            }
        }
        return null;
    }
    public void aceptarTransferencia(Transferencia trans) {
        transferenciasPendientes.remove(trans);
        transferenciasRespondidas.add(trans);
    }
    public void rechazarTransferencia(Transferencia trans) {
        transferenciasPendientes.remove(trans);
    }
    
    
}
