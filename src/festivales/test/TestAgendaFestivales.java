package festivales.test; /** @author Aaron Jaffet Vasquez Carrera */
import festivales.io.*;
import festivales.modelo.AgendaFestivales;
import festivales.modelo.Estilo;
import festivales.modelo.Mes;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.TreeSet;

public class TestAgendaFestivales {
    
    /**
     * Código para probar la clase festivales.modelo.AgendaFestivales
     *
     */
    public static void main(String[] args) {
        //He cambiado los años de la lista festivales.csv por que estaba en 2022 o 2021, he añadido 2 años a todas
        AgendaFestivales agenda = new AgendaFestivales();
        
        testCargarAgenda(agenda);
        testFestivalesEnMes(agenda);
        testFestivalesPorEstilo(agenda);
        testCancelarFestivales(agenda);
        
        
    }
    
    
    private static void testCargarAgenda(AgendaFestivales agenda) {
         FestivalesIO.cargarFestivales(agenda);
         System.out.println(agenda);
         System.out.println();
    }
    
    
    private static void testFestivalesEnMes(AgendaFestivales agenda) {
         System.out.println("Meses y nº festivales en ese mes\n");
         Mes[] meses = {Mes.FEBRERO, Mes.ABRIL, Mes.MAYO, Mes.JUNIO,
                 Mes.SEPTIEMBRE, Mes.OCTUBRE};
         for (Mes mes : meses) {
             System.out.println(mes + ": " + agenda.festivalesEnMes(mes));
            
         }
         System.out.println();
    }
    
    
    private static void testFestivalesPorEstilo(AgendaFestivales agenda) {
         System.out.println("Nombres de festivales agrupados por estilos\n");
         //es un pequenio chivato para hacer festivalesporestilo, si lo haces sin esos tipos dara error
         for (Map.Entry<Estilo, TreeSet<String>> entrada :
                 agenda.festivalesPorEstilo().entrySet()) {
             System.out.println(entrada.getKey() + " - " + entrada.getValue());
            
         }
         System.out.println();
    }
    
    private static void testCancelarFestivales(AgendaFestivales agenda) {
   
         HashSet<String> lugares = new HashSet<>(Arrays.asList("VITORIA",
                 "BILBAO", "ZARAGOZA"));
         testCancelarFestivales(agenda, Mes.JUNIO, lugares);

         lugares = new HashSet<>(Arrays.asList("SEVILLA"));
         testCancelarFestivales(agenda, Mes.MARZO, lugares);
    
         lugares = new HashSet<>(Arrays.asList("MALLORCA"));
         testCancelarFestivales(agenda, Mes.DICIEMBRE, lugares);
        
    }

    private static void testCancelarFestivales(AgendaFestivales agenda,
                                               Mes mes,
                                               HashSet<String> lugares) {
        //He añadido un \n antes de lugares para que quede igual que en pdf
         System.out.println("\n"+lugares + " cancela/n su/s festival/es de " + mes +
                 "\n\n");
         int borrados = agenda.cancelarFestivales(lugares, mes);
         if (borrados == -1) {
             System.out.println("No hay programados festivales en " + mes);
         } else {
             System.out.println("Cancelados " + borrados + " festival/es");
         }
         //Si queremos que quede igual que el pdf he añadido saltos de linea y Festivales
         System.out.println("Después de borrar ....\n\nFestivales\n");
         System.out.println(agenda);
    }
}
