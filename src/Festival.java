/** @author Aaron Jaffet Vasquez Carrera */
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;

/**
 * Un objeto de esta clase almacena los datos de un
 * festival.
 * Todo festival tiene un nombre, se celebra en un lugar
 * en una determinada fecha, dura una serie de días y
 * se engloba en un conjunto determinado de estilos
 *
 */
public class Festival {
    private final String nombre;
    private final String lugar;
    private final LocalDate fechaInicio;
    private final int duracion;
    private final HashSet<Estilo> estilos;
    
    
    public Festival(String nombre, String lugar, LocalDate fechaInicio,
                    int duracion, HashSet<Estilo> estilos) {
        this.nombre = nombre;
        this.lugar = lugar;
        this.fechaInicio = fechaInicio;
        this.duracion = duracion;
        this.estilos = estilos;
        
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public String getLugar() {
        return lugar;
    }
    
    public LocalDate getFechaInicio() {
        return fechaInicio;
    }
    
    public int getDuracion() {
        return duracion;
    }
    
    public HashSet<Estilo> getEstilos() {
        return estilos;
    }
    
    public void addEstilo(Estilo estilo) {
        this.estilos.add(estilo);
        
    }

    /**
     * devuelve el mes de celebración del festival, como
     * valor enumerado
     *
     */
    public Mes getMes() {
        //TODO
         /**
           * Tambien podriamos hacerlo con if y elseif, por ejemplo:
           * if (valorMes == 1) {
           * return Mes.ENERO;
           * } else if (valorMes == 2) {
           * return Mes.FEBRERO;
           * } else if (valorMes == 3) {
           * return Mes.MARZO; ... ETC hasta el 12
           * y luego un else de default return null */
        int valorMesFest = fechaInicio.getMonthValue();
        switch (valorMesFest) {
            case 1:
                return Mes.ENERO;
            case 2:
                return Mes.FEBRERO;
            case 3:
                return Mes.MARZO;
            case 4:
                return Mes.ABRIL;
            case 5:
                return Mes.MAYO;
            case 6:
                return Mes.JUNIO;
            case 7:
                return Mes.JULIO;
            case 8:
                return Mes.AGOSTO;
            case 9:
                return Mes.SEPTIEMBRE;
            case 10:
                return Mes.OCTUBRE;
            case 11:
                return Mes.NOVIEMBRE;
            case 12:
                return Mes.DICIEMBRE;
            default:
                return null;
        }
    }
    /**
     *
     * @param otro
     * @return true si el festival actual empieza
     * en un fecha anterior a otro
     */
    public boolean empiezaAntesQue(Festival otro) {
        //TODO
         /**
          * El metodo devuelve true si la fechaInicio es anterior ala fecha
          * proporcionada por el otro festival, por ejemplo:
          * Si el festival base que tenemos comienza el 01/01/2024 y la otra
          * comienza el 02/02/2024, el return sera true */
            return this.fechaInicio.isBefore(otro.getFechaInicio());
    }
    /**
     *
     * @param otro
     * @return true si el festival actual empieza
     * en un fecha posteior a otro
     */
    public boolean empiezaDespuesQue(Festival otro) {
        //TODO
         /**
           * El metodo devuelve true si la fechaInicio es posterior ala fecha
           * proporcionada por el otro festival, por ejemplo:
           * Si el festival base que tenemos comienza el 02/02/2024 y la otra
           * comienza el 01/01/2024, el return sera true */
        return this.fechaInicio.isAfter(otro.getFechaInicio());
    }

    /**
     *
     * @return true si el festival ya ha concluido
     */
    public boolean haConcluido() {
        /**
         * LocalDate.now es la fecha del sistema que tenemos en el ordenador
         * y plusDays(duracion) añade los dias de duracion del festival
         * por lo tanto fecha fin sera la suma de la fecha inicio mas la duracion
         * del festival, luego decimos que si la fecha actual del sistema, es posterior
         * de la fechafin del festival o si la fecha actual, es la misma
         * de la fechafin del festival, en estos dos casos return sera true */
            LocalDate fechaHoyFest = LocalDate.now();
            LocalDate fechaFinalFest = fechaInicio.plusDays(duracion);
            return fechaHoyFest.isAfter(fechaFinalFest) || fechaHoyFest.equals(fechaFinalFest);
    }

    /**
     * Representación textual del festival, exactamente
     * como se indica en el enunciado */
    @Override
    public String toString() {
       //TODO
            /** Creamos StringBuilder para hacer el toString */
        StringBuilder sb = new StringBuilder();

            /** El nombre del festival y los estilos musicales separados
             * por unos espacios y luego un salto de linea */
        sb.append(nombre).append(" ").append(estilos).append("\n");

            /** Lugar del festival y puesto en uppercase es decir siempre en mayusculas
            * luego un salto de linea */
        sb.append(lugar.toUpperCase()).append("\n");

        /** Aqui diremos que si la duracion es de un dia solo aparezca la fecha de inicio entera
         * ya que el formato es de dd MMM. yyyy, la MMM. la descubri probando y hace que aparezcan las
         * tres primeras letras del mes y un punto delante */
        if (duracion == 1){
        DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd MMM. yyyy");
        String fechaInicioFest = fechaInicio.format(formatoFecha);
            sb.append(fechaInicioFest).append(" ");
        }

            /** Despues cuando la duracion es de mas de un dia, pasara otra cosa
            * hara que aparezca la fecha de inicio, dia y mes pero no el año, esto requeria crear otro formato
            * y luego tambien aparecera la fecha final la cual tendra el formato completo de dia, mes y año
            * tambien le decimos que antes de dar la fecha final, aparezca un guion */
        if (duracion > 1) {
            DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd MMM.");
            String fechaInicioFest = fechaInicio.format(formatoFecha);
            sb.append(fechaInicioFest).append(" ");
            LocalDate fechaFinal = fechaInicio.plusDays(duracion);
            DateTimeFormatter formatoFecha1 = DateTimeFormatter.ofPattern("dd MMM. yyyy");
            String fechaFinalFest = fechaFinal.format(formatoFecha1);
            sb.append("- ").append(fechaFinalFest).append(" ");
        }

            /** Creamos un String y depende de si ha concluido,
            *  si la fecha de inicio aun no ha pasado o igualado ala fecha del sistema
            * o si no esta pasando ninguna de las dos anteriores el estado seria que
            * el festival se esta celebrando actualmente
            * el metodo chronoUnit.DAYS.between lo encontre en internet y te devuelve un entero entre
            * las dos fechas dadas, en este caso la fecha actual del sistema y la fechaInicio del festival,
            * este metodo dara el numero de dias que hay entre estas fechas */
        String estadoActualFest = "";
        if (haConcluido()) {
            estadoActualFest = "(concluido)";
        } else if (fechaInicio.isAfter(LocalDate.now())) {
            estadoActualFest = "(quedan " + ChronoUnit.DAYS.between(LocalDate.now(), fechaInicio) + " días)";
        } else {
            estadoActualFest = "(ON)";
        }

        /** Metemos en el StringBuilder el estado actual del festival y saltamos de linea */
        sb.append(estadoActualFest).append("\n");

        /** Este es el separador entre festivales */
        sb.append("------------------------------------------------------------");

        return sb.toString();
    }

    /**
     * Código para probar la clase Festival
     *
     */
    public static void main(String[] args) {
        /**
         * He cambiado algunas fechas, esta parte habria que hablarla
         * con el profesor por que los datos dados en el main estan dados del 2022
         * las cambie a 2024 y el mes y dia del gazpatxo rock para que se pueda probar bien
         * los metodos como en la foto del pdf
        */
        System.out.println("Probando clase Festival");
        String datosFestival = "Gazpatxo Rock : " +
                "valencia: 16-03-2024  :1  :rock" +
                ":punk " +
                ": hiphop ";
        Festival f1 = FestivalesIO.parsearLinea(datosFestival);
        System.out.println(f1);
        
        datosFestival = "black sound fest:badajoz:05-02-2024:  21" +
                ":rock" + ":  blues";
        Festival f2 = FestivalesIO.parsearLinea(datosFestival);
        System.out.println(f2);
    
        datosFestival = "guitar bcn:barcelona: 28-01-2024 :  170" +
                ":indie" + ":pop:fusion";
        Festival f3 = FestivalesIO.parsearLinea(datosFestival);
        System.out.println(f3);
    
        datosFestival = "  benidorm fest:benidorm:26-01-2024:3" +
                ":indie" + ": pop  :rock";
        Festival f4 = FestivalesIO.parsearLinea(datosFestival);
        System.out.println(f4);
      
        
        System.out.println("\nProbando empiezaAntesQue() empiezaDespuesQue()" +
                "\n");
        if (f1.empiezaAntesQue(f2)) {
            System.out.println(f1.getNombre() + " empieza antes que " + f2.getNombre());
        } else if (f1.empiezaDespuesQue(f2)) {
            System.out.println(f1.getNombre() + " empieza después que " + f2.getNombre());
        } else {
            System.out.println(f1.getNombre() + " empieza el mismo día que " + f2.getNombre());
        }

        System.out.println("\nProbando haConcluido()\n");
        System.out.println(f4);
        /** He añadido \n para que quede igual que la foto del pdf que se nos ha entregado */
        System.out.println(f4.getNombre() + " ha concluido? " + f4.haConcluido() + "\n");
        System.out.println(f1);
        /** He añadido \n para que quede igual que la foto del pdf que se nos ha entregado */
        System.out.println(f1.getNombre() + " ha concluido? " + f1.haConcluido() + "\n");
 
        
        
    }
}
