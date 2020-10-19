package cvapp.components;

import java.util.AbstractList;

import cvapp.views.AbstractStrutturaView;

public interface InterfacciaQuery {

    public final String[] TIPO_ATTRAZIONI = { "Musei di arte", "Musei archeologici", "Musei di storia", "Musei di scienze", "Musei di storia naturale", "Monumenti", "Parchi cittadini", "Parchi zoologici", "Acquari", "Giardini botanici", "Parchi naturali", "Escursioni" };
    public final String[] SERVIZI_ATTRAZIONI = { "WiFi free", "Parcheggio", "Punto bar", "Punto ristorazione", "Accesso disabili", "Accesso animali" };
    public final String[] GUIDE_ATTRAZIONI = { "Non obbligatoria", "Interattiva", "Operatore", "Centro informazioni" };
    public final String[] TIPO_FND = { "Bar", "Loungebar", "Gelaterie", "Pasticcerie", "Pizzerie", "Bracerie", "Taverne", "Pubs", "Tavole calde", "Osterie", "Paninoteche", "Birrerie", "Fast foods" };
    public final String[] SERVIZI_FND = { "WiFi free", "Parcheggio", "Take away", "Coperto", "Area fumatori", "Accesso disabili", "Accesso animali" };
    public final String[] CUCINE_RISTORANTI = { "Locale", "Etnica", "Ittica", "Steak", "Vegetariana", "Vegana", "Fusion", "Generica" };
    public final String[] SERVIZI_RISTORANTI = { "Bar", "Loungebar", "WiFi free", "Parcheggio", "Animazione bambini", "Area fumatori", "Accesso disabili", "Accesso animali" };
    public final String[] TIPO_ALBERGO = { "Hotels", "Affittacamere", "Villaggi turistici", "Motels", "Residents", "Ostelli" };
    public final String[] STELLE_ALBERGHI = { "Nessuna", "1 stella", "2 stelle", "3 stelle", "4 stelle", "5 stelle" };
    public final String[] SERVIZI_RIST_ALBERGHI = { "Mezza pensione", "Pensione completa", "Bed & breakfast", "Bar", "Loungebar", "Servizio in camera" };
    public final String[] SERVIZI_ALBERGHI = { "Spa", "Beauty farm", "Piscina", "Palestra", "WiFi free", "Parcheggio", "Area fumatori", "Accesso disabili", "Accesso animali" };
    public final String[] SORT_ALBERGHI = { "Nessuno", "Nome", "Indirizzo", "Città", "Paese", "Stelle", "Costo", "Voto recensioni medio", "Distanza" };
    public final String[] SORT_RISTORANTI = { "Nessuno","Nome","Indirizzo", "Città", "Paese", "Costo medio", "Voto recensioni medio", "Distanza" };
    public final String[] SORT_ATTRAZIONI = { "Nessuno","Nome","Indirizzo", "Città", "Paese", "Costo biglietto", "Voto recensioni medio", "Distanza" };
    public final String[] SORT_FND = { "Nessuno","Nome","Indirizzo", "Città", "Paese", "Costo medio", "Voto recensioni medio", "Distanza" };
    public final String[] SORT_RECENSIONI = { "Nessuno", "Voto", "Data", "Nickname" };
    public final String[] VOTI_RECENSIONI = { "1", "2", "3", "4", "5" };

    public AbstractList<Object> prepareRecensioniValues(String strutturaId, String user, String data, String titolo , float voto, String testo);

    public AbstractList<Object> prepareUtentiValues(String nome, String cognome, String nazionalita, String genere, String dataNascita, String dataRegistrazione, String userId, String nickname, String password , boolean etaPrivacy, boolean generePrivacy, boolean nazionalitaPrivacy);

    public String getUpdateRecensioniCommand(String user);

    public String getSelectAlberghi(Object...values);

    public String getSelectAttrazioni(Object...values);

    public String getSelectFND(Object...values);

    public String getSelectRistoranti(Object...values);

    public String getSelectRecensioni(AbstractStrutturaView strutturaView, Object...values);

    public String getSelectSpecificaAlbergo(String id);

    public String getSelectSpecificaRistorante(String id);

    public String getSelectSpecificaAttrazione(String id);

    public String getSelectSpecificaFND(String id);

    public String getSelectVicineAlberghi(AbstractStrutturaView ultimaStrutturaVisitata, int distanza, int numMax);

    public String getSelectVicineRistoranti(AbstractStrutturaView ultimaStrutturaVisitata, int distanza, int numMax);

    public String getSelectVicineAttrazioni(AbstractStrutturaView ultimaStrutturaVisitata, int distanza, int numMax);

    public String getSelectVicineFND(AbstractStrutturaView ultimaStrutturaVisitata, int distanza, int numMax);

    public String getSelectUtente(String user, String pass);

    public String getSelectProfiloUtente(String user);

    public boolean esisteConnessioneSingleton();

}
