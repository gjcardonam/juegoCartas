package juegocartas;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import javax.swing.JPanel;
import java.util.Comparator;


public class Jugador {

    public int TOTAL_CARTAS = 10;

    private Random r = new Random();

    private Carta[] cartas;

    public void repartir() {
        cartas = new Carta[TOTAL_CARTAS];
        for (int i = 0; i < cartas.length; i++) {
            cartas[i] = new Carta(r);
        }
    }

    public void mostrar(JPanel pnl) {
        pnl.removeAll();
        for (int i = 0; i < cartas.length; i++) {
            cartas[i].mostrar(pnl, 5 + i * 40, 5);
        }
        pnl.repaint();
    }
    
    public String getEscaleras(){
        
//      Primero que todo ordeno el vector de Cartas
        Comparator<Carta> comparador = (o1,o2) -> o1.getIndice() - o2.getIndice();
        Arrays.sort(cartas, comparador);
        
//      Inicializo el mensaje que voy a imprimir en pantalla
        String mensaje = "No hay escaleras";
        
//      Genero un contador que almacenará cuantas escaleras hay y de cuántas cartas
//      Ademas genero otro array que almacena la pinta de la escalera
//      Creo un vector para almacenar los puntos que suman las cartas
        ArrayList<Integer> escaleras = new ArrayList<>();
        ArrayList<String> pintasEscalera = new ArrayList<>();
        ArrayList<Integer> puntosCartas = new ArrayList<>();
        
//      Como el primer elemento no lo puedo comparar con el anterior, entonces inicio diciendo que la primer escalera tiene 1 carta y su pinta
        escaleras.add(1);
        pintasEscalera.add(cartas[0].getPinta().name());
//      En el contador de cartas que no son escalera observo si la primera es una letra para asignar 10 o si no, asigno el valor de la carta
        if (cartas[0].getIndice()% 13 == 0 || cartas[0].getIndice()% 13 == 1 || cartas[0].getIndice()% 13 == 11 || cartas[0].getIndice()% 13 == 12){
            puntosCartas.add(10);
        }
        else{
            puntosCartas.add(cartas[0].getIndice()% 13);
        }
        
//      Recorro el resto del vector de Cartas buscando escaleras, notese que empiezo en 1 y no en 0 para poder comparar con el anterior siempre
        for (int i = 1; i < cartas.length; i++) {
//          Comparo el valor del indice con el anterior y sus pintas para saber si hay escalera
            if (cartas[i-1].getIndice() == cartas[i].getIndice()-1 && cartas[i-1].getPinta() == cartas[i].getPinta()){
//              En caso de que si haya escalera le sumo 1 al ultimo valor del vector Escalera
                Integer lastElement = escaleras.get(escaleras.size() - 1);
                lastElement++;
                escaleras.set(escaleras.size() - 1, lastElement);
//              Y almaceno la pinta de la escalera, la cual solo importara cuando la escalera tenga mas de 2 cartas, esto se vera mas adelante
                pintasEscalera.set(pintasEscalera.size() - 1, cartas[i].getPinta().name());
                
//              Ahora voy sumando el valor de las cartas para mas adelante sumar el total de cartas que no son escalera
                if (cartas[i].getIndice()% 13 == 0 || cartas[i].getIndice()% 13 == 1 || cartas[i].getIndice()% 13 == 11 || cartas[i].getIndice()% 13 == 12){
                    lastElement = puntosCartas.get(puntosCartas.size() - 1);
                    lastElement += 10;
                    puntosCartas.set(puntosCartas.size() - 1, lastElement);
                }
                else{
                    lastElement = puntosCartas.get(puntosCartas.size() - 1);
                    lastElement += cartas[i].getIndice()% 13;
                    puntosCartas.set(puntosCartas.size() - 1, lastElement);
                }
            }
//          En caso de que la carta sea la misma, no se hace nada y se continua a la siguiente iteracion
            else if (cartas[i-1].getIndice() == cartas[i].getIndice() && cartas[i-1].getPinta() == cartas[i].getPinta()){
                continue;
            }
//          En caso de que no haya escalera, agrego un nuevo 1 al vector Escalera
            else{
                escaleras.add(1);
                pintasEscalera.add(cartas[i].getPinta().name());
                
//              Aqui nuevamente voy sumando el valor de las cartas para mas adelante sumar el total de cartas que no son escalera
                if (cartas[i].getIndice()% 13 == 0 || cartas[i].getIndice()% 13 == 1 || cartas[i].getIndice()% 13 == 11 || cartas[i].getIndice()% 13 == 12){
                    puntosCartas.add(10);
                }
                else{
                    puntosCartas.add(cartas[i].getIndice()% 13);
                }
            }
//          Hasta este momento el vector escaleras almacena en cada posicion el numero de cartas sumadas de escaleras
        }
        
//      Ahora voy a verificar si quedo alguna escalera para saber que mensaje imprimo
        boolean hayEscalera = false;
        for (int i = 0; i < escaleras.size(); i++) {
            if (escaleras.get(i)> 2) {
                // El código se ejecutará si se encuentra un valor igual a 2
                hayEscalera = true;
                break;
            }
        }
        
//      En caso de que si haya alguna escalera imprimimos los mensajes de las escaleras, en caso de que no, queda el mensaje inicial
        Integer puntosNoEscalera = 0;
        if (hayEscalera){
            mensaje = "Las escaleras encontradas fueron:\n";
        
            for (int i = 0; i < escaleras.size(); i++) {
                if (escaleras.get(i)> 2){
                    mensaje += "Escalera de " + escaleras.get(i) + " cartas de la pinta "  + pintasEscalera.get(i) + "\n";
                }
                else{
//                  Cuando no es escalera vamos sumando los puntos
                    puntosNoEscalera += puntosCartas.get(i);
                }
            }
            mensaje += "\nPuntos que no son escalera: " + puntosNoEscalera;
        }
        else{
//          Cuando no hubo ninguna escalera, sumamos todos los puntos de las cartas
            for (int i = 0; i < escaleras.size(); i++) {
                puntosNoEscalera += puntosCartas.get(i);
            }
            mensaje += "\nPuntos que no son escalera: " + puntosNoEscalera;
        }
        mensaje = "Escaleras\n";
        for (int i = 0; i < escaleras.size(); i++) {
            mensaje += " " + escaleras.get(i);
        }
        mensaje += "\nPuntos cartas\n";
        for (int i = 0; i < puntosCartas.size(); i++) {
            mensaje += " " + puntosCartas.get(i);
        }
        
        return mensaje;
            
    }

    public String getGrupos() {
        String mensaje = "No hay grupos";

        int[] contadores = new int[NombreCarta.values().length];
        for (int i = 0; i < cartas.length; i++) {
            contadores[cartas[i].getNombre().ordinal()]++;
        }

        int totalGrupos = 0;
        for (int i = 0; i < contadores.length; i++) {
            if (contadores[i] >= 2) {
                totalGrupos++;
            }
        }
        if (totalGrupos > 0) {
            mensaje = "Los grupos encontrados fueron:\n";
            for (int i = 0; i < contadores.length; i++) {
                if (contadores[i] >= 2) {
                    mensaje += Grupo.values()[contadores[i]] + " de " + NombreCarta.values()[i]+"\n";
                }
            }
        }
        return mensaje;
    }
}
