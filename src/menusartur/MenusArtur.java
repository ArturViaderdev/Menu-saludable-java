/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package menusartur;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author arturv
 */
public class MenusArtur {
    static final int MAXPLATOS=3;
    //Array de menús del día que contiene toda la semana
    private static Menudia[] menus;
    
    //Determina si un string contiene un plato de los tipos existentes.
    private static boolean esplato(String plato)
    {
        if(plato.equals("VG") || plato.equals("PR") || plato.equals("CR") || plato.equals("LT") || plato.equals("PS"))
        {
            return true;
        }
        else
        {
            return false;
        }
    }
      
    //Muestra el menú de un dia
    private static void muestramenu(int dia)
    {
        int cont;
        cont=0;
        //Recorre todos los platos para mostrarlos
        while(cont<MAXPLATOS)
        {
            System.out.println("Plato " + (cont +1) + " - " + menus[dia-1].dimeplato(cont));
            cont++;
        }
    }
    
    //Permite al usuario configurar el menú de un día
    private static void introducirunmenu()
    {
        Scanner entrada=new Scanner(System.in);
        BufferedReader lector = new BufferedReader(new InputStreamReader(System.in));
        String valor;
        System.out.println("Introduce el dia de la semana 1-5:");
        int dia;
        int cont;
        int opcion;
        //Array temporal para guardar los platos a añadir. No se guardan definitivamente hasta
        //que se han introducido todos.
        String[] tempplatos = new String[MAXPLATOS];
        try
        {
            //El usuario introduce el día.
            dia=entrada.nextInt();
            if(dia>=1 && dia<=5)
            {
                //Si el menú no se ha configurado todavía
                if(menus[dia-1]==null)
                {
                    System.out.println("No hay menú definido.");
                    cont=0;
                    //Se introducen todos los platos en array temporal
                    while(cont<MAXPLATOS)
                    {
                        do
                        {
                            System.out.println("Introduce el plato " + (cont +1) + " VG/PR/CR/LT/PS");
                            tempplatos[cont]=lector.readLine();
                        }while(!esplato(tempplatos[cont]));
                        //Se sigue solicitando hasta que se introduce un valor correcto.
                        cont++;
                    }
                    //Ahora que el usuario ya ha introducido todos los datos
                    //Se inicializa el menú del día actual con el constructor.
                    menus[dia-1]= new Menudia();
                    cont=0;
                    //Se ponen todos los platos copiando del array temporal.
                    while(cont<MAXPLATOS)
                    {
                        menus[dia-1].ponplato(tempplatos[cont],cont);
                        cont++;
                    }
                    System.out.println("Menú del dia configurado.");
                    //Se muestra el menú
                    muestramenu(dia);
                }
                else
                {
                    //Caso en que el menú ya había sido configurado antes
                    System.out.println("El menú del día ha sido configurado previamente. Estos son los platos:");
                    //Se muestra el menú
                    muestramenu(dia);
                    do
                    {
                        System.out.println("Introduce el número de plato que quieres modificar o 0 para salir.");
                        opcion = entrada.nextInt();
                        //Se solicita al usuario el número de plato a modificar.
                        if(opcion>0 && opcion<=MAXPLATOS)
                        {
                            do
                            {
                                System.out.println("Introduce el plato " + (opcion) + " VG/PR/CR/LT/PS");
                                valor=lector.readLine();
                            }while(!esplato(valor));
                            //Se configura el plato
                            menus[dia-1].ponplato(valor, opcion-1);
                            System.out.println("Valor cambiado.");
                        }
                        else if(opcion==0)
                        {
                            System.out.println("Fin de modificaciones.");
                            muestramenu(dia);
                        }
                        else
                        {
                            System.out.println("Opción incorrecta.");
                        }
                    }while(opcion!=0);
                }
            }
            else
            {
                System.out.println("Error, debías introducir un número del 1 al 5.");
            }
        }
        catch(Exception ex)
        {
            System.out.println("Error.");
        }
    }
    
    //Devuelve el día de la semana en letras partiendo de un número que puede valer de 0 a 4.
    //De lunes a viernes
    private static String dimediasemana(int dia)
    {
        String resultado;
        switch(dia)
        {
            case 0:
                resultado="lunes";
                break;
            case 1:
                resultado="martes";
                break;
            case 2:
                resultado="miercoles";
                break;
            case 3:
                resultado="jueves";
                break;
            case 4:
                resultado="viernes";
                break;
            default:
                resultado="ninguno";
        }
        return resultado;
    }
    
    //Detecta si el menú semanal está ya completo o no
    private static boolean menucompleto()
    {
        boolean sal=false, encontrado = false;
        int cont=0;
        //Se recorre el array de menús
        //si se encuentra un objeto sin inicializar se determina que no está completo el menú.
        while(!sal)
        {
            if(cont<menus.length)
            {
                if(menus[cont]==null)
                {
                    encontrado=true;
                    sal=true;
                }
                else
                {
                    cont++;
                }
            }
            else
            {
                sal=true;
            }
        }
        return !encontrado;
    }
    
    //Muestra un resumen y detecta si el menú es saludable.
    private static void mostrarresumen()
    {
        int cont, contb;
        int contvg=0, contpr=0, contlt=0, contps=0, contvgdia=0;
        
        boolean saludable=true;
        if(menucompleto())
        {
            for(cont=0;cont<5;cont++)
            {
                contvgdia=0;
                for(contb=0;contb<MAXPLATOS;contb++)
                {
                    if(menus[cont].dimeplato(contb).equals("VG"))
                    {
                        contvg++;
                        contvgdia++;
                    }
                    else if(menus[cont].dimeplato(contb).equals("PR"))
                    {
                        contpr++;
                    }
                    else if(menus[cont].dimeplato(contb).equals("LT"))
                    {
                        contlt++;
                    }
                    else if(menus[cont].dimeplato(contb).equals("PS"))
                    {
                        contps++;
                    }
                }
                if(contvgdia==0)
                {
                    System.out.println("El menú del dia " + dimediasemana(cont) + " no contiene suficientes VG.");
                    saludable=false;
                }
            }
            
            if(contvg<7)
            {
                System.out.println("El menú semanal no contiene suficientes VG.");
                saludable=false;
            }
            if(contpr>3)
            {
                System.out.println("El menú semanal tiene un exceso de PR.");
                saludable=false;
            }
            if((contlt + contps) >4)
            {
                System.out.println("El menú semanal tiene un exceso de LT o PS.");
                saludable=false;
            }
            if(saludable)
            {
                System.out.println("El menú semanal es saludable.");
            }
            else
            {
                System.out.println("El menú semanal no es saludable.");
            }
        }
        else
        {
            System.out.println("El menú de la semana no está completo todavía.");
        }
    }
    
    //Muestra el menú completo semanal
    private static void vermenudelasemana()
    {
        int contb;
        System.out.print("DIA     ");
        for(contb=0;contb<MAXPLATOS;contb++)
        {
            if(contb==MAXPLATOS-1)
            {
                System.out.println("     PLATO " + contb);
            }
            else
            {
                System.out.print("    PLATO " + contb);    
            }
                    
        }
        for(int cont=0;cont<5;cont++)
        {
            System.out.print(dimediasemana(cont));
            if(menus[cont]==null)
            {
                System.out.println("    Día no programado.");
            }
            else
            {
                for(contb=0;contb<MAXPLATOS;contb++)
                {
                    if(contb==MAXPLATOS-1)
                    {
                        System.out.println("     " + menus[cont].dimeplato(contb));
                    }
                    else
                    {
                        System.out.print("     " + menus[cont].dimeplato(contb));
                    }
                    
                }
            }
        }
    }
    
    //Permite introducir un menú codificado
    private static void introducircodificado()
    {
        BufferedReader lector = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Introduce el menú codificado.");
        String entrada;
        String entradas[];
        boolean sal = false, encontrado = false;
        int cont;
        int posicion;
        try
        {
            entrada = lector.readLine();
            //Separa el string en varios strings tomando como separador el ;
            entradas= entrada.split(";");
            //Si existen tantos parámetros como platos más uno de posición el número de parámetros
            //es correcto
            if(entradas.length==MAXPLATOS + 1)
            {
                //Se guarda en un entero la posición que venía en el primer parámetro.
                posicion=Integer.parseInt(entradas[0])-1;
                cont=1;
                //Se recorren los parámetros para verificar si hay alguno que no sea un plato.
                while(!sal)
                {
                    if(cont<MAXPLATOS+1)
                    {
                        if(!esplato(entradas[cont]))
                        {
                            encontrado=true;
                            sal=true;
                        }
                        else
                        {
                            cont++;
                        }
                    }
                    else
                    {
                        sal=true;
                    }
                }
                if(encontrado)
                {
                    System.out.println("Has introducido mal los platos.");
                }
                else
                {
                    //Si se han introducido bien los platos
                    //Se comprueba si existe el menú del día a configurar
                    if(menus[posicion]==null)
                    {
                        //Se inicializa el menú del día si no lo estaba
                        menus[posicion] = new Menudia();
                    }
                    
                    //Se introducen los platos
                    for(cont=1;cont<MAXPLATOS+1;cont++)
                    {
                        menus[posicion].ponplato(entradas[cont], cont-1);
                    }
                    System.out.println("Menú configurado.");
                }
            }
            else
            {
                System.out.println("Número de parámetros no correcto.");
            }
            
        }
        catch(Exception ex)
        {
            System.out.println("Error.");
        }
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        //Se inicializa la array de menús.
        menus = new Menudia[5];
        Scanner entrada=new Scanner(System.in);
        int opcion=0;
        int continco=0;
        boolean salir=false;
        
        try
        {
            do
            {
                System.out.println("1-Introducir un menú.");
                System.out.println("2-Ver el menú de la semana.");
                System.out.println("3-Introducir menú codificado.");
                System.out.println("4-Mostrar el resumen de los platos.");
                System.out.println("5-Salir.");
                System.out.println("Introduce una opción:");
                opcion=entrada.nextInt();
                //Menú
                switch(opcion)
                {
                    case 1:
                        continco=0;
                        introducirunmenu();
                        break;
                    case 2:
                        continco=0;
                        vermenudelasemana();
                        break;
                    case 3:
                        continco=0;
                        introducircodificado();
                        break;
                    case 4:
                        continco=0;
                        mostrarresumen();
                        break;
                    case 5:
                        System.out.println("Adios.");
                        salir=true;
                        break;
                    default:
                        continco++;
                        if(continco==3)
                        {
                            salir=true;
                            //Si se introduce 3 veces una opción incorrecta se sale de la aplicación.
                        }
                        System.out.println("Opción incorrecta.");
                   
                }
            }while(!salir);
            System.out.println("Adios.");
        }
        catch (Exception ex)
        {
            System.out.println("Error.");
        }
    }   
}
