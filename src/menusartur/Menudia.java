package menusartur;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author arturv
 */

//Clase que define la estructura para guardar el menú de un día
public class Menudia {
    //El array platos guardará cada uno de los platos del menú del día.
    String[] platos;
    final int MAXPLATOS=3;
    
    //Cambia el valor de una posición del array de platos.
    public void ponplato(String plato,int posicion)
    {
        platos[posicion]=plato;
    }
    
    //Devuelve el valor de una posición del array de platos.
    public String dimeplato(int posicion)
    {
        return platos[posicion];
    }
    
    //Constructor
    public Menudia()
    {
        //Se inicializa el array de platos.
      platos = new String[MAXPLATOS];
    }   
}
