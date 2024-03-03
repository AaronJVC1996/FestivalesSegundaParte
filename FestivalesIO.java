/** @author Aaron Jaffet Vasquez Carrera */
import festivales.modelo.Festival;
import festivales.modelo.AgendaFestivales;
import festivales.modelo.Estilo;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
/**
 * La clase contiene méodos estáticos que permiten
 * cargar la agenda de festivales leyendo los datos desde
 * un fichero
 */
public class FestivalesIO {
    public static void cargarFestivales(AgendaFestivales agenda) {
        Scanner sc = null;
        try {
            sc = new Scanner(FestivalesIO.class.
                    getResourceAsStream("/festivales.csv"));
            while (sc.hasNextLine()) {
                String lineaFestival = sc.nextLine();
                Festival festival = parsearLinea(lineaFestival);
                agenda.addFestival(festival);
            }
        } finally {
            if (sc != null) {
                sc.close();
            }
        }

    }

    /**
     * se parsea la línea extrayendo sus datos y creando y
     * devolviendo un objeto Festival
     * @param lineaFestival los datos de un festival
     * @return el festival creado
     */
    public static Festival parsearLinea(String lineaFestival) {
        /** Con el trim eliminamos los espacios que hay al comienzo y al final de la linea */
        lineaFestival = lineaFestival.trim();

        /** Separamos los datos por casillas con el split, donde cada : que haya sera un separador */
        String[] datosFest = lineaFestival.split(":");

        /** Este es el nombre, le decimos que escoja la casilla del indice 0 generada por el split y que aplique el
         * metodo local que hemos creado llamado arreglarNombre, mas abajo esta la explicacion de lo que hace */
        String nombreFest = arreglarNombre(datosFest[0]);

        /** Esta es la ciudad donde se celebra el festival
         * escogemos la casilla del indice 1 la cual contiene la ciudad donde se celebra el festival
         * tambien aplicamos uppercase para que siempre este en mayusculas
         * el trim hace que se borren los espacios que tiene la ciudad ala izquierda y ala derecha */
        String ciudadFest = datosFest[1].trim().toUpperCase();

        /** Aqui escogemos la fecha, escogemos la casilla del indice 2 la cual contiene la fecha
         * esta fecha la pasamos por el metodo local que hemos creado llamado fechaBuena la cual la explicamos
         * mas abajo y su parametro de entrada es un string como lo que da el split
         * el trim borra los espacios como siempre...*/
        LocalDate fechaInicioFest = fechaBuena(datosFest[2].trim());

        /** Esta es la duracion del festival, y como el split nos ha dado Strings
         * lo pasaremos de String a int usando integer.parseInt usando la casilla del indice 3 del split
         * y como siempre el trim que hace borrar los espacios...*/
        int duracionFest = Integer.parseInt(datosFest[3].trim());

        /** Estos son los estilos del Fest que es de tipo HashSet, le decimos
         * que de las casillas del split apartir del indice 4 incluido en adelante los copie todos pasandolos
         * por el metodo local que hemos creado la cual explicamos mas abajo como funciona */
        HashSet<Estilo> estilosFest = estiloBueno(Arrays.copyOfRange(datosFest, 4, datosFest.length));

       /** creamos el objeto festival con todas las variables necesarias para crearlo */
        return new Festival(nombreFest, ciudadFest, fechaInicioFest, duracionFest, estilosFest);
    }

    /** Este es el metodo local que hemos creado para arreglar el nombre
     * primero creamos el array de Strings la cual sera el nombre que tiene dos palabras
     * primero quitamos los espacios de la izquierda y de la derecha de la palabra compuesta con el trim
     * luego con el split le decimos que el espacio entre la palabra uno del nombre y la palabra dos del nombre
     * se vuelvan dos casillas por que las separa un espacio el cual indicamos con el parametro puesto en el split
     * luego con for mejorado recorremos el array nombreArreglar, con el StringBuilder que hemos creado
     * añadiremos con un append diciendole que la primera letra de la palabra uno y la palabra dos
     * se cambie a mayuscula, esto se logra con el toUpperCase y el charAt indicando el indice 0,
     * despues hacemos otro append diciendo que las letras que le siguen sean todas minusculas,
     * esto se logra con el substring, el cual dice que las pase todas a lowercase apartir del indice 1
     * de la palabra en adelante, tambien añadimos un espacio para que separe la palabra uno de la palabra dos
     * esto se repite en la palabra uno y la palabra dos la cuales generamos con el split,
     * luego retornamos el StringBuilder con un trim para borrar el espacio generado por la palabra dos */
    private static String arreglarNombre(String nombre) {
        String[] nombreArreglar = nombre.trim().split(" ");
        StringBuilder sb = new StringBuilder();
        for (String nombreArreglo : nombreArreglar) {
            sb.append(Character.toUpperCase(nombreArreglo.charAt(0)))
                    .append(nombreArreglo.substring(1).toLowerCase())
                    .append(" ");
        }
        return sb.toString().trim();
    }

    /** Hacemos un formato para la fecha, en este caso sera el dia - el mes - el año
     * por ejemplo 16-02-2024, luego usamos el metodo parse de la clase Localdate donde pondremos
     * el String fecha que tenemos en el parametro y el formato que hemos creado
     * al unir estas dos en el parse tenemos un LocalDate con esa fecha y formato */
    private static LocalDate fechaBuena(String fecha) {
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return LocalDate.parse(fecha, formato);
    }

    /** Este metodo local hace que dado un conjunto de estilos en Strings, itera sobre cada estilo
     * con un for mejorado y lo añade al HashSet conjuntoEstilos, de una forma que
     * se borran los espacios ala izquierda y ala derecha con el trim, y tambien lo convertiremos
     * a que todas sus letras sean en mayusculas con el toUpperCase,
     * al final retorna el HashSet conjuntoEstilos */
    private static HashSet<Estilo> estiloBueno(String[] estilos) {
        HashSet<Estilo> conjuntoEstilos = new HashSet<>();
        for (String estilo : estilos) {
            conjuntoEstilos.add(Estilo.valueOf(estilo.trim().toUpperCase()));
        }
        return conjuntoEstilos;
    }
}