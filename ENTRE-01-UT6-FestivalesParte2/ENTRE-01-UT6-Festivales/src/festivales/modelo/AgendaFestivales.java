package src.festivales.modelo; /** @author Aaron Jaffet Vasquez Carrera */
import java.util.*;
/**
 * Esta clase guarda una agenda con los festivales programados
 * en una serie de meses
 * La agenda guardalos festivales en una colección map
 * La clave del map es el mes (un enumerado festivales.modelo.festivales.modelo.Mes)
 * Cada mes tiene asociados en una colección ArrayList
 * los festivales  de ese mes
 * Solo aparecen los meses que incluyen algún festival
 * Las claves se recuperan en orden alfabéico
 */
public class AgendaFestivales {
        private TreeMap<Mes, ArrayList<Festival>> agenda;

    public AgendaFestivales() {
        this.agenda = new TreeMap<>();
    }

    /**
     * añade un nuevo festival a la agenda
     * <p>
     * Si la clave (el mes en el que se celebra el festival)
     * no existe en la agenda se creará una nueva entrada
     * con dicha clave y la colección formada por ese único festival
     * <p>
     * Si la clave (el mes) ya existe se añade el nuevo festival
     * a la lista de festivales que ya existe ese ms
     * insertándolo de forma que quede ordenado por nombre de festival.
     * Para este segundo caso usa el método de ayuda
     * obtenerPosicionDeInsercion()
     */
    public void addFestival(Festival festival) {
        //TODO
        //creamos una variable de tipo festival para obtener el mes del festival pasado por parametro
        Mes mesFesti = festival.getMes();

        // vemos si el mes de ese festival en la agenda no existe, si no existe entonces hara lo siguiente
        if (!agenda.containsKey(mesFesti)) {
            // creara una nueva entrada en la agenda, para aniadir algo en la agenda nececitaremos
            // un mes como key y un valor o valores de tipo arraylist<festivales.modelo.Festival>
            // creamos un arraylist de festivales y metemos el festival que tenemos en el parametro del metodo
            ArrayList<Festival> festisEnMes = new ArrayList<>();
            festisEnMes.add(festival);
            //metera el mes y tambien los festivales en ese mes, tecnicamente aniade 1 por que el parametro del
            //metodo solo tiene de entrada un festival pero lo hacemos arraylist de festivales por que es lo
            //que nececita la agenda para crear una entrada
            agenda.put(mesFesti, festisEnMes);
        } else {
            // si el mes ya existe, lo haremos con el metodo obtenerposicióninserción
            ArrayList<Festival> festivalesMes = agenda.get(mesFesti);
            int posiInser = obtenerPosicionDeInsercion(festivalesMes, festival);

            // aniadir el festival en la posición obtenida, el primer parametro es el del indice de insercion
            // y el segundo el objeto a aniadir en este caso un festival
            // no hace falta aniadirlo en la agenda, puesto que festivalesMes = agenda.get(mesFesti)
            // no crea una nueva lista, mas bien lo que hace es apuntar ala misma direccion, este seria una referencia
            // de agenda, es decir que festivalesMes y agenda.get(mesFesti) es el mismo objeto en la memoria
             festivalesMes.add(posiInser, festival);
        }
    }
    /**
     * @param festivales una lista de festivales
     * @param festival
     * @return la posición en la que debería ir el nuevo festival
     * de forma que la lista quedase ordenada por nombre
     */
    private int obtenerPosicionDeInsercion(ArrayList<Festival> festivales,
                                           Festival festival) {
        //TODO
        int posi = 0;
        for (Festival festi : festivales) {
            if (festi.getNombre().compareToIgnoreCase(festival.getNombre()) < 0) {
                // Vemos si el nombre de festival es menor que el nombre del festival que queremos meter en el
                // parametro, si el nombre del festival de la lista es menor que el del parametro entonces
                // avanzaremos ala siguiente posicion
                // por ejemplo, si tenemos "aa" en festi y "bb" de parametro, aa sera menos que bb por lo tanto
                // avanzaria ala siguiente posicion con un ++
                posi++;
            } else {
                // si el nombre es igual o mayor que 0 entonces es por que hemos encontrado ya la posicion y
                // entonces tendremos que terminar la busqueda
                break;
            }
        }
        return posi;
    }
    /**
     * Representación textual del festival
     * De forma eficiente
     * Usa el conjunto de entradas para recorrer el map
     */
    @Override
    public String toString() {
        //TODO
        //lo haremos con un stringbuilder, haremos el map.entry para iterar sobre cada key y sus valores usando el
        //entrySet en agenda, cogeremos la key y sus valores y luego haremos el inicio del String
        //que seria el nombre del mes y el numero de festivales que hay en ese mes, despues
        //le pedimos que itere sobre los festivales de ese mes asignados y que ponga el festival.toString() de
        //cada festival de ese mes, despues se repite pasando al siguiente mes...
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<Mes, ArrayList<Festival>> festisEnMes : agenda.entrySet()) {
            Mes mes = festisEnMes.getKey();
            ArrayList<Festival> festivales = festisEnMes.getValue();
            sb.append(mes).append(" (").append(festivales.size()).append(" festival/es)\n");
            for (Festival festival : festivales) {
                sb.append(festival).append("\n");
            }
        }
        return sb.toString();
    }
    /**
     * @param mes el mes a considerar
     * @return la cantidad de festivales que hay en ese mes
     * Si el mes no existe se devuelve -1 <<- en el pdf dice 0
     */
    public int festivalesEnMes(Mes mes) {
        //TODO
        //si agenda contiene el mes ingresado, llama a dicho mes con un .get(mes)
        //luego le pide la cantidad de festivales en este arraylist
        //el else devuelve 0 por que simplemente no existe este mes
        if (agenda.containsKey(mes)) {
            return agenda.get(mes).size();
        } else {
            return 0;
        }
    }
    /**
     * Se trata de agrupar todos los festivales de la agenda
     * por estilo.
     * Cada estilo que aparece en la agenda tiene asociada una colección
     * que es el conjunto de nombres de festivales que pertenecen a ese estilo
     * Importa el orden de los nombres en el conjunto
     * <p>
     * Identifica el tipo exacto del valor de retorno
     */
    public TreeMap<Estilo, TreeSet<String>> festivalesPorEstilo() {
        //TODO
        //Guardaremos aqui los datos para el return
        TreeMap<Estilo, TreeSet<String>> festivalesPorEstilo = new TreeMap<>();

        //Meteremos los estilos del enum como las llaves de festivalesPorEstilo, ninguna tendra valores de momento
        for (Estilo estilo : Estilo.values()) {
            festivalesPorEstilo.put(estilo, new TreeSet<>());
        }
        // recorremos los meses para obtener todos los festivales en ese mes
        for (Mes mes : agenda.keySet()) {
            ArrayList<Festival> festivales = agenda.get(mes);
            // recorremos los festivales en ese mes uno por uno y los estilos de ese festival uno por uno
            for (Festival festival : festivales) {
                for (Estilo estilo : festival.getEstilos()) {
                    // aniadimos en el treemap el festival indicando la key que seria el estilo
                    // el estilo seria aniadido por ejemplo: el festival Aaron tiene 3 estilos de musica
                    // pues mete el nombre del festival en estos 3 estilos
                    festivalesPorEstilo.get(estilo).add(festival.getNombre());
                }
            }
        }
        // Ordenar los nombres de festivales por estilo y agregarlos al TreeMap final
        for (Estilo estilo : festivalesPorEstilo.keySet()) {
            TreeSet<String> nombresFestivales = new TreeSet<>(festivalesPorEstilo.get(estilo));
            festivalesPorEstilo.put(estilo, nombresFestivales);
        }
        //Map.Entry comentado en clase y investigado en casa
        //le metemos entrysSet para obtener los estilos y los festivales
        Iterator<Map.Entry<Estilo, TreeSet<String>>> iterador = festivalesPorEstilo.entrySet().iterator();
        while (iterador.hasNext()) {
            Map.Entry<Estilo, TreeSet<String>> iterador2 = iterador.next();
            //si el estilo no tiene ningun valor, es decir ningun festival, este estilo se borra
            if (iterador2.getValue().isEmpty()) {
                iterador.remove();
            }
        }
        //esta forma la encontre en internet pero no la entiendo en su totalidad, envez del iterador ---->
        //festivalesPorEstilo.entrySet().removeIf(entry -> entry.getValue().isEmpty());
        return festivalesPorEstilo;
    }
    /**
     * Se cancelan todos los festivales organizados en alguno de los
     * lugares que indica el conjunto en el mes indicado. Los festivales
     * concluidos o que no empezados no se tienen en cuenta
     * Hay que borrarlos de la agenda
     * Si el mes no existe se devuelve -1
     * <p>
     * Si al borrar de un mes los festivales el mes queda con 0 festivales
     * se borra la entrada completa del map
     */
    public int cancelarFestivales(HashSet<String> lugares, Mes mes) {
        //TODO
            if (!agenda.containsKey(mes)) {
                //Devuelve -1 si el mes no existe en la agenda
                return -1;
            }
        int festisCancelados = 0;
            //Si el mes existe en la agenda, obtenemos la lista de todos los festivales de ese mes.
            ArrayList<Festival> festivalesMes = agenda.get(mes);
            //recorremos la lista de festivales del mes con un iterador que es lo que solemos usar para eliminar
            Iterator<Festival> iterator = festivalesMes.iterator();
            while (iterator.hasNext()) {
                Festival festival = iterator.next();
                if (!festival.haConcluido() && lugares.contains(festival.getLugar())) {
                    //si el festival no ha concluido y el o los lugares hashset del parametro esta el lugar
                    //del festival sobre el que estamos iterando
                    iterator.remove();
                    festisCancelados++;
                }
            }
            if (festivalesMes.isEmpty()) {
                //si no hay festivales en el mes, eliminar toda la entrada de ese mes
                agenda.remove(mes);
            }
            return festisCancelados;
        }
    }

