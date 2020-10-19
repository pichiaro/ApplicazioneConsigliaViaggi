package cvapp.components;
import android.app.Activity;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;

import androidx.core.content.ContextCompat;

import java.text.SimpleDateFormat;
import java.util.AbstractList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Random;

import cvapp.R;
import cvapp.models.AbstractAlbergoModel;
import cvapp.models.AbstractAttrazioneModel;
import cvapp.models.AbstractFNDModel;
import cvapp.models.AbstractRistoranteModel;
import cvapp.models.LocaleAlbergoModel;
import cvapp.models.LocaleAttrazioneModel;
import cvapp.models.LocaleFNDModel;
import cvapp.models.LocaleRecensioneModel;
import cvapp.models.LocaleRistoranteModel;
import cvapp.models.LocaleUtenteModel;
import cvapp.views.RecensioneView;
import cvapp.views.AbstractStrutturaView;
import database.AbstractObjectsModelRWDatabase;
import models.ObjectsModel;
import utilities.RandomUtility;

public final class LocaleDatabase extends AbstractObjectsModelRWDatabase {

    private final String[] GIORNI = {"lunedi", "martedi", "mercoledi", "giovedi", "venerdi", "sabato", "domenica"};
    private final String[] ALBERGHI_TIPOLOGIE = {"Hotels", "Affittacamere", "Villaggi turistici", "Motels", "Residents", "Ostelli"};
    private final int[] ALBERGHI_STELLE = { 1, 2, 3, 4, 5};
    private final String[] ALBERGHI_SERVIZI = {"Spa", "Beauty farm", "Piscina", "Palestra", "WiFi free", "Parcheggio", "Area fumatori", "Accesso disabili", "Accesso animali", "Mezza pensione", "Pensione completa", "Bed & breakfast", "Bar", "Loungebar", "Servizio in camera"};
    private final String[] ALBERGHI_SERVIZI_BASE = {"WiFi free", "Parcheggio", "Area fumatori", "Accesso disabili", "Accesso animali", "Mezza pensione", "Pensione completa", "Bed & breakfast", "Bar", "Servizio in camera"};
    private final String[] RISTORANTI_SERVIZI = {"Cucina locale", "Cucina etnica", "Cucina ittica", "Cucina steak", "Cucina vegetariana", "Cucina vegana", "Cucina fusion", "Cucina generica", "Bar", "Loungebar", "WiFi free", "Parcheggio", "Animazione bambini", "Area fumatori", "Accesso disabili", "Accesso animali"};
    private final String[] ATTRAZIONI_TIPOLOGIE = {"Musei di arte", "Musei archeologici", "Musei di storia", "Musei di scienze", "Musei di storia naturale", "Monumenti", "Parchi cittadini", "Parchi zoologici", "Acquari", "Giardini botanici", "Parchi naturali", "Escursioni"};
    private final String[] ATTRAZIONI_SERVIZI = {"WiFi free", "Parcheggio", "Punto bar", "Punto ristorazione", "Accesso disabili", "Accesso animali", "Guida non obbligatoria", "Guida interattiva", "Operatore", "Centro informazioni"};
    private final String[] FND_TIPOLOGIE = {"Bar", "Loungebar", "Gelaterie", "Pasticcerie", "Pizzerie", "Bracerie", "Taverne", "Pubs", "Tavole calde", "Osterie", "Paninoteche", "Birrerie", "Fast foods"};
    private final String[] FND_SERVIZI = {"WiFi free", "Parcheggio", "Take away", "Coperto", "Area fumatori", "Accesso disabili", "Accesso animali"};
    private final double[] RECENSIONI_VOTI = {1, 1.5, 2, 2.5, 3, 3.5, 4, 4.5, 5};
    private final int NUMERO_STRUTTURE = 220;
    private final int NUMERO_UTENTI = 315;
    private final int NUMERO_RECENSIONI_UTENTE_STRUTTURA_MAX = 5;
    private final int LUNGHEZZA_RECENSIONE_MAX = 250;
    private final String INDIRIZZO = "Indirizzo";
    private final String CITTA = "Città";
    private final String PAESE = "Paese";
    private final String CREA_ALBERGHI = "create table if not exists alberghi (id integer primary key, nome text, indirizzo text, citta text, paese text, tipologia text, stelle integer, servizi text, voto_recensioni_medio decimal(10,6), latitudine decimal(10,6), longitudine decimal(10,6),  unique(nome, indirizzo, citta, paese), unique(latitudine, longitudine)) without rowid";
    private final String INSERT_ALBERGHI = "insert into alberghi (id,nome,indirizzo,citta,paese,tipologia,stelle,servizi,voto_recensioni_medio,latitudine,longitudine) values (?,?,?,?,?,?,?,?,?,?,?)";
    private SQLiteStatement INSERT_ALBERGHI_STATEMENT;
    private final String CREA_CAMERE_ALBERGHI = "create table if not exists camere_alberghi (id_albergo integer, id integer, persone integer, data_arrivo text, ora_arrivo text, data_partenza text, ora_partenza text, costo decimal(10,2), foreign key(id_albergo) references alberghi(id), primary key (id_albergo,id)) without rowid";
    private final String INSERT_CAMERE_ALBERGHI = "insert into camere_alberghi (id_albergo,id,persone,data_arrivo,ora_arrivo,data_partenza,ora_partenza,costo) values (?,?,?,?,?,?,?,?)";
    private SQLiteStatement INSERT_CAMERE_ALBERGHI_STATEMENT;
    private final String CREA_RISTORANTI = "create table if not exists ristoranti (id integer primary key, nome text, indirizzo text, citta text, paese text, costo_medio decimal(10,2), giorno_chiusura text, ora_apertura text, ora_chiusura, servizi text, voto_recensioni_medio decimal(10,6), latitudine decimal(10,6), longitudine decimal(10,6), unique(nome, indirizzo, citta, paese), unique(latitudine, longitudine)) without rowid";
    private final String INSERT_RISTORANTI = "insert into ristoranti (id,nome,indirizzo,citta,paese,costo_medio,giorno_chiusura,ora_apertura,ora_chiusura,servizi,voto_recensioni_medio,latitudine,longitudine) values (?,?,?,?,?,?,?,?,?,?,?,?,?)";
    private SQLiteStatement INSERT_RISTORANTI_STATEMENT;
    private final String CREA_TAVOLI_RISTORANTI = "create table if not exists tavoli_ristoranti (id_ristorante integer, id integer, persone integer, data_prenotato text, da_ora text, a_ora text, foreign key(id_ristorante) references ristoranti(id), primary key(id_ristorante,id)) without rowid";
    private final String INSERT_TAVOLI_RISTORANTI = "insert into tavoli_ristoranti (id_ristorante,id,persone,data_prenotato,da_ora,a_ora) values (?,?,?,?,?,?)";
    private SQLiteStatement INSERT_TAVOLI_RISTORANTI_STATEMENT;
    private final String CREA_ATTRAZIONI = " create table if not exists attrazioni (id integer primary key, nome text, indirizzo text, citta text, paese text, tipologia text, costo_biglietto decimal (10,2), giorno_chiusura text, ora_apertura text, ora_chiusura text,servizi text, voto_recensioni_medio decimal(10,6), latitudine decimal(10,6), longitudine decimal(10,6), unique(nome, indirizzo, citta, paese), unique(latitudine, longitudine)) without rowid";
    private final String INSERT_ATTRAZIONI = "insert into attrazioni (id,nome,indirizzo,citta,paese,tipologia,costo_biglietto,giorno_chiusura,ora_apertura,ora_chiusura,servizi,voto_recensioni_medio,latitudine,longitudine) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    private SQLiteStatement INSERT_ATTRAZIONI_STATEMENT;
    private final String CREA_FND = "create table if not exists fnd (id integer primary key, nome text, indirizzo text, citta text, paese text, tipologia text, costo_medio decimal(10,2), giorno_chiusura text, ora_apertura text, ora_chiusura text, servizi text, voto_recensioni_medio decimal(10,6), latitudine decimal(10,6), longitudine decimal(10,6), unique(nome, indirizzo, citta, paese), unique(latitudine, longitudine)) without rowid";
    private final String INSERT_FND = "insert into fnd (id,nome,indirizzo,citta,paese,tipologia,costo_medio,giorno_chiusura,ora_apertura,ora_chiusura,servizi,voto_recensioni_medio,latitudine,longitudine) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    private SQLiteStatement INSERT_FND_STATEMENT;
    private final String CREA_TAVOLI_FND = "create table if not exists tavoli_fnd (id_fnd integer, id integer, persone integer, data_prenotato text, da_ora text, a_ora text, foreign key(id_fnd) references fnd(id), primary key(id_fnd, id)) without rowid";
    private final String INSERT_TAVOLI_FND = "insert into tavoli_fnd (id_fnd,id,persone,data_prenotato,da_ora,a_ora) values (?,?,?,?,?,?)";
    private SQLiteStatement INSERT_TAVOLI_FND_STATEMENT;
    private final String CREA_UTENTI = "create table if not exists utenti (nome text, cognome text, nazionalita text, sesso text, data_nascita text, data_registrazione text,id text not null unique, nickname text primary key, password text, nazionalita_flag integer, sesso_flag integer, data_nascita_flag integer, numero_recensioni integer) without rowid";
    private final String INSERT_UTENTI = "insert into utenti (nome,cognome,nazionalita,sesso,data_nascita,data_registrazione,id,nickname,password,nazionalita_flag,sesso_flag,data_nascita_flag,numero_recensioni) values (?,?,?,?,?,?,?,?,?,?,?,?,?)";
    private SQLiteStatement INSERT_UTENTI_STATEMENT;
    private final String CREA_RECENSIONI_ALBERGHI = " create table if not exists recensioni_alberghi (id integer, nickname text, data text, titolo text, voto decimal(10,6), testo text, foreign key(id) references alberghi(id), foreign key(nickname) references utenti(nickname), primary key(id, nickname, data)) without rowid";
    private final String INSERT_RECENSIONI_ALBERGHI = "insert into recensioni_alberghi (id,nickname,data,titolo,voto,testo) values (?,?,?,?,?,?)";
    private SQLiteStatement INSERT_RECENSIONI_ALBERGHI_STATEMENT;
    private final String CREA_RECENSIONI_RISTORANTI = " create table if not exists recensioni_ristoranti (id integer, nickname text, data text, titolo text, voto decimal(10,6), testo text, foreign key(id) references ristoranti(id), foreign key(nickname) references utenti(nickname), primary key(id, nickname, data)) without rowid";
    private final String INSERT_RECENSIONI_RISTORANTI = "insert into recensioni_ristoranti (id,nickname,data,titolo,voto,testo) values (?,?,?,?,?,?)";
    private SQLiteStatement INSERT_RECENSIONI_RISTORANTI_STATEMENT;
    private final String CREA_RECENSIONI_ATTRAZIONI = " create table if not exists recensioni_attrazioni (id integer, nickname text, data text, titolo text, voto decimal(10,6), testo text, foreign key(id) references attrazioni(id), foreign key(nickname) references utenti(nickname), primary key(id, nickname, data)) without rowid";
    private final String INSERT_RECENSIONI_ATTRAZIONI = "insert into recensioni_attrazioni (id,nickname,data,titolo,voto,testo) values (?,?,?,?,?,?)";
    private SQLiteStatement INSERT_RECENSIONI_ATTRAZIONI_STATEMENT;
    private final String CREA_RECENSIONI_FND = " create table if not exists recensioni_fnd (id integer, nickname text, data text, titolo text, voto decimal(10,6), testo text, foreign key(id) references fnd(id), foreign key(nickname) references utenti(nickname), primary key(id, nickname, data)) without rowid";
    private final String INSERT_RECENSIONI_FND = "insert into recensioni_fnd (id,nickname,data,titolo,voto,testo) values (?,?,?,?,?,?)";
    private SQLiteStatement INSERT_RECENSIONI_FND_STATEMENT;
    private final RandomUtility RANDOM_HELPER = new RandomUtility();
    private final Exception SIMULAZIONE_VIOLAZIONE_VINCOLO_EXCEPTION = new Exception();
    private final SimpleDateFormat YYYYMMDD_SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyyMMdd");
    private final SimpleDateFormat GIORNI_SIMPLE_DATE_FORMAT = new SimpleDateFormat("EEEE", Locale.ITALIAN);
    private Activity activity;
    private final int ALTEZZA_IMG_STRUTTURA = (int) (AbstractStrutturaView.ALTEZZA_IMG / Resources.getSystem().getDisplayMetrics().density);
    private final int LARGHEZZA_IMG_STRUTTURA = (int) (Resources.getSystem().getDisplayMetrics().widthPixels / Resources.getSystem().getDisplayMetrics().density);
    private final int MAX_POSTFISSO = 1000;
    private final String ATTRIBUTO_IMG = "img";
    private final String ALBERGHI_QUERY = " alberghi,";
    private final String ALBERGHI_QUERY_2 = " alberghi ";
    private final String RISTORANTI_QUERY = " ristoranti ";
    private final String ATTRAZIONI_QUERY = " attrazioni ";
    private final String FND_QUERY = " fnd ";
    private final String RECENSIONI_QUERY = " recensioni";
    private final String MUSEI_QUERY = "Musei";
    private final String UTENTI_QUERY = " utenti ";
    private final String ANNO_ATTUALE = "2020";
    private final String ANNO_PRECEDENTE = String.valueOf((Integer.parseInt(this.ANNO_ATTUALE) - 1));
    private static LocaleDatabase SINGLETON;

    public static void createSingleton(Activity activity, String nome, int versione) {
        if (LocaleDatabase.SINGLETON == null) {
            LocaleDatabase.SINGLETON = new LocaleDatabase(activity, nome, versione);
        }
    }

    public static LocaleDatabase getSingleton() {
        return LocaleDatabase.SINGLETON;
    }

    private LocaleDatabase(Activity activity, String nome, int versione) {
        super(activity, nome, null, versione);
        this.activity = activity;
        SQLiteDatabase scrivibileSQLiteDatabase = this.getWritableDatabase();
        this.onCreate(scrivibileSQLiteDatabase);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        /*database.execSQL("delete from recensioni_attrazioni");
        database.execSQL("delete from recensioni_fnd");
        database.execSQL("delete from recensioni_alberghi");
        database.execSQL("delete from recensioni_ristoranti");
        database.execSQL("delete from utenti");
        database.execSQL("delete from tavoli_ristoranti");
        database.execSQL("delete from ristoranti");
        database.execSQL("delete from camere_alberghi");
        database.execSQL("delete from alberghi");
        database.execSQL("delete from attrazioni");
        database.execSQL("delete from tavoli_fnd");
        database.execSQL("delete from fnd");
        database.execSQL("drop table recensioni_attrazioni");
        database.execSQL("drop table recensioni_fnd");
        database.execSQL("drop table recensioni_alberghi");
        database.execSQL("drop table recensioni_ristoranti");
        database.execSQL("drop table utenti");
        database.execSQL("drop table tavoli_ristoranti");
        database.execSQL("drop table ristoranti");
        database.execSQL("drop table camere_alberghi");
        database.execSQL("drop table alberghi");
        database.execSQL("drop table attrazioni");
        database.execSQL("drop table tavoli_fnd");
        database.execSQL("drop table fnd");*/
        if (!this.hasResult("select * from alberghi")) {
            try {
                database.beginTransaction();
                database.execSQL(this.CREA_ALBERGHI);
                this.INSERT_ALBERGHI_STATEMENT = database.compileStatement(this.INSERT_ALBERGHI);
                database.execSQL(this.CREA_CAMERE_ALBERGHI);
                this.INSERT_CAMERE_ALBERGHI_STATEMENT = database.compileStatement(this.INSERT_CAMERE_ALBERGHI);
                database.execSQL(this.CREA_RISTORANTI);
                this.INSERT_RISTORANTI_STATEMENT = database.compileStatement(this.INSERT_RISTORANTI);
                database.execSQL(this.CREA_TAVOLI_RISTORANTI);
                this.INSERT_TAVOLI_RISTORANTI_STATEMENT = database.compileStatement(this.INSERT_TAVOLI_RISTORANTI);
                database.execSQL(this.CREA_ATTRAZIONI);
                this.INSERT_ATTRAZIONI_STATEMENT = database.compileStatement(this.INSERT_ATTRAZIONI);
                database.execSQL(this.CREA_FND);
                this.INSERT_FND_STATEMENT = database.compileStatement(this.INSERT_FND);
                database.execSQL(this.CREA_TAVOLI_FND);
                this.INSERT_TAVOLI_FND_STATEMENT = database.compileStatement(this.INSERT_TAVOLI_FND);
                database.execSQL(this.CREA_UTENTI);
                this.INSERT_UTENTI_STATEMENT = database.compileStatement(this.INSERT_UTENTI);
                database.execSQL(this.CREA_RECENSIONI_ALBERGHI);
                this.INSERT_RECENSIONI_ALBERGHI_STATEMENT = database.compileStatement(this.INSERT_RECENSIONI_ALBERGHI);
                database.execSQL(this.CREA_RECENSIONI_RISTORANTI);
                this.INSERT_RECENSIONI_RISTORANTI_STATEMENT = database.compileStatement(this.INSERT_RECENSIONI_RISTORANTI);
                database.execSQL(this.CREA_RECENSIONI_ATTRAZIONI);
                this.INSERT_RECENSIONI_ATTRAZIONI_STATEMENT = database.compileStatement(this.INSERT_RECENSIONI_ATTRAZIONI);
                database.execSQL(this.CREA_RECENSIONI_FND);
                this.INSERT_RECENSIONI_FND_STATEMENT = database.compileStatement(this.INSERT_RECENSIONI_FND);
                this.popolaAlberghi();
                this.popolaRistoranti();
                this.popolaAttrazioni();
                this.popolaFND();
                this.popolaUtenti();
                database.setTransactionSuccessful();
                database.endTransaction();
                /*database.beginTransaction();
                database.execSQL("delete from camere_alberghi");
                database.execSQL("delete from alberghi");
                database.execSQL("delete from tavoli_ristoranti");
                database.execSQL("delete from ristoranti");
                database.execSQL("delete from attrazioni");
                database.execSQL("delete from tavoli_fnd");
                database.execSQL("delete from fnd");
                database.execSQL("delete from utenti");
                database.execSQL("delete from recensioni_alberghi");
                database.execSQL("delete from recensioni_ristoranti");
                database.execSQL("delete from recensioni_attrazioni");
                database.execSQL("delete from recensioni_fnd");
                database.setTransactionSuccessful();
                database.endTransaction();*/
            } catch (Exception exception) {

            }
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {

    }

    public void popolaAlberghi() {
        String nome = "Albergo";
        int id;
        for (id = 1; id < this.NUMERO_STRUTTURE + 1; id++) {
            try {
                this.INSERT_ALBERGHI_STATEMENT.bindLong(1, id);
                int numNome = this.RANDOM_HELPER.getRandomPositiveInt(this.MAX_POSTFISSO);
                this.INSERT_ALBERGHI_STATEMENT.bindString(2, nome + numNome);
                int numIndirizzo = this.RANDOM_HELPER.getRandomPositiveInt(this.MAX_POSTFISSO);
                this.INSERT_ALBERGHI_STATEMENT.bindString(3, this.INDIRIZZO + numIndirizzo);
                int numCitta = this.RANDOM_HELPER.getRandomPositiveInt(this.MAX_POSTFISSO);
                this.INSERT_ALBERGHI_STATEMENT.bindString(4, this.CITTA + numCitta);
                int numPaese = this.RANDOM_HELPER.getRandomPositiveInt(this.MAX_POSTFISSO);
                this.INSERT_ALBERGHI_STATEMENT.bindString(5, this.PAESE + numPaese);
                String tipologia = this.RANDOM_HELPER.selectFrom(this.ALBERGHI_TIPOLOGIE);
                this.INSERT_ALBERGHI_STATEMENT.bindString(6, tipologia);
                String servizi = "";
                int stelle = 0;
                if (tipologia.compareTo(this.ALBERGHI_TIPOLOGIE[1]) == 0 || tipologia.compareTo(this.ALBERGHI_TIPOLOGIE[3]) == 0 || tipologia.compareTo(this.ALBERGHI_TIPOLOGIE[5]) == 0) {
                    this.INSERT_ALBERGHI_STATEMENT.bindLong(7, stelle);
                    for (int i = 0; i < this.ALBERGHI_SERVIZI_BASE.length; i++) {
                        boolean alternativa = this.RANDOM_HELPER.getRandomBoolean();
                        if (alternativa) {
                            servizi = servizi + this.ALBERGHI_SERVIZI_BASE[i] + "\n";
                        }
                    }
                }
                else {
                    stelle = this.RANDOM_HELPER.selectFrom(this.ALBERGHI_STELLE);
                    this.INSERT_ALBERGHI_STATEMENT.bindLong(7, stelle);
                    for (int i = 0; i < this.ALBERGHI_SERVIZI.length; i++) {
                        boolean alternativa = this.RANDOM_HELPER.getRandomBoolean();
                        if (alternativa) {
                            servizi = servizi + this.ALBERGHI_SERVIZI[i] + "\n";
                        }
                    }
                }
                servizi = servizi.replaceAll("(\n)$", "");
                this.INSERT_ALBERGHI_STATEMENT.bindString(8, servizi);
                double votoMedioRecensioni = this.RANDOM_HELPER.selectFrom(this.RECENSIONI_VOTI);
                this.INSERT_ALBERGHI_STATEMENT.bindDouble(9, votoMedioRecensioni);
                double latitudine = this.RANDOM_HELPER.getRandomDoubleBetween(40.838, 40.945);
                this.INSERT_ALBERGHI_STATEMENT.bindDouble(10, latitudine);
                double longitudine = this.RANDOM_HELPER.getRandomDoubleBetween(14.048, 14.465);
                this.INSERT_ALBERGHI_STATEMENT.bindDouble(11, longitudine);
                this.INSERT_ALBERGHI_STATEMENT.executeInsert();
                int numeroCamere = this.RANDOM_HELPER.getRandomIntBetween(10, 100);
                numeroCamere = numeroCamere++;
                for (int idCamera = 1; idCamera < numeroCamere; idCamera++) {
                    try {
                        this.INSERT_CAMERE_ALBERGHI_STATEMENT.bindLong(1, id);
                        this.INSERT_CAMERE_ALBERGHI_STATEMENT.bindLong(2, idCamera);
                        int numeroPersone = this.RANDOM_HELPER.getRandomPositiveInt(15);
                        this.INSERT_CAMERE_ALBERGHI_STATEMENT.bindLong(3, numeroPersone);
                        boolean alternativa = this.RANDOM_HELPER.getRandomBoolean();
                        if (alternativa) {
                            String dataArrivo;
                            String dataPartenza;
                            String dataUno = this.RANDOM_HELPER.getRandomYYYYMMDDDate(this.ANNO_ATTUALE);
                            String dataDue = this.RANDOM_HELPER.getRandomYYYYMMDDDate(this.ANNO_ATTUALE);
                            if (dataUno.compareTo(dataDue) < 0) {
                                dataArrivo = dataUno;
                                dataPartenza = dataDue;
                            } else {
                                dataArrivo = dataDue;
                                dataPartenza = dataUno;
                            }
                            this.INSERT_CAMERE_ALBERGHI_STATEMENT.bindString(4, dataArrivo);
                            this.INSERT_CAMERE_ALBERGHI_STATEMENT.bindString(6, dataPartenza);
                            String oraArrivo;
                            String oraPartenza;
                            String oraUno = this.RANDOM_HELPER.getRandomHourWithMinutes();
                            String oraDue = this.RANDOM_HELPER.getRandomHourWithMinutes();
                            if (dataArrivo.compareTo(dataPartenza) == 0) {
                                if (oraUno.compareTo(oraDue) < 0) {
                                    oraArrivo = oraUno;
                                    oraPartenza = oraDue;
                                } else {
                                    if (oraUno.compareTo(oraDue) > 0) {
                                        oraArrivo = oraDue;
                                        oraPartenza = oraUno;
                                    } else {
                                        throw this.SIMULAZIONE_VIOLAZIONE_VINCOLO_EXCEPTION;
                                    }
                                }
                            } else {
                                oraArrivo = oraUno;
                                oraPartenza = oraDue;
                            }
                            this.INSERT_CAMERE_ALBERGHI_STATEMENT.bindString(5, oraArrivo);
                            this.INSERT_CAMERE_ALBERGHI_STATEMENT.bindString(7, oraPartenza);
                        } else {
                            this.INSERT_CAMERE_ALBERGHI_STATEMENT.bindNull(4);
                            this.INSERT_CAMERE_ALBERGHI_STATEMENT.bindNull(5);
                            this.INSERT_CAMERE_ALBERGHI_STATEMENT.bindNull(6);
                            this.INSERT_CAMERE_ALBERGHI_STATEMENT.bindNull(7);
                        }
                        double costoCamera = 0;
                        if (stelle == 0) {
                            costoCamera = this.RANDOM_HELPER.getRandomDoubleBetween(10, 200);
                        }
                        else {
                            if (stelle <= 3) {
                                costoCamera = this.RANDOM_HELPER.getRandomDoubleBetween(50, 400);
                            }
                            else {
                                costoCamera = this.RANDOM_HELPER.getRandomDoubleBetween(300, 1000);
                            }
                        }
                        costoCamera = (double) Math.round(costoCamera * 100) / 100;
                        this.INSERT_CAMERE_ALBERGHI_STATEMENT.bindDouble(8, costoCamera);
                        this.INSERT_CAMERE_ALBERGHI_STATEMENT.executeInsert();
                    } catch (Exception exception) {

                    }
                }
            } catch (Exception exception) {

            }
        }
    }

    public void popolaRistoranti() {
        String nome = "Ristorante";
        int id;
        for (id = 1; id < this.NUMERO_STRUTTURE + 1; id++) {
            try {
                this.INSERT_RISTORANTI_STATEMENT.bindLong(1, id);
                int numNome = this.RANDOM_HELPER.getRandomPositiveInt(this.MAX_POSTFISSO);
                this.INSERT_RISTORANTI_STATEMENT.bindString(2, nome + numNome);
                int numIndirizzo = this.RANDOM_HELPER.getRandomPositiveInt(this.MAX_POSTFISSO);
                this.INSERT_RISTORANTI_STATEMENT.bindString(3, this.INDIRIZZO + numIndirizzo);
                int numCitta = this.RANDOM_HELPER.getRandomPositiveInt(this.MAX_POSTFISSO);
                this.INSERT_RISTORANTI_STATEMENT.bindString(4, this.CITTA + numCitta);
                int numPaese = this.RANDOM_HELPER.getRandomPositiveInt(this.MAX_POSTFISSO);
                this.INSERT_RISTORANTI_STATEMENT.bindString(5, this.PAESE + numPaese);
                double costoMedio = this.RANDOM_HELPER.getRandomDoubleBetween(50, 1000);
                costoMedio = (double) Math.round(costoMedio * 100) / 100;
                this.INSERT_RISTORANTI_STATEMENT.bindDouble(6, costoMedio);
                String giornoChiusura = this.RANDOM_HELPER.selectFrom(this.GIORNI);
                this.INSERT_RISTORANTI_STATEMENT.bindString(7, giornoChiusura);
                String oraUno = this.RANDOM_HELPER.getRandomHourWithMinutes();
                String oraDue = this.RANDOM_HELPER.getRandomHourWithMinutes();
                String oraApertura = "";
                String oraChiusura = "";
                if (oraUno.compareTo(oraDue) == 0) {
                    throw this.SIMULAZIONE_VIOLAZIONE_VINCOLO_EXCEPTION;
                } else {
                    if (oraUno.compareTo(oraDue) < 0) {
                        oraApertura = oraUno;
                        oraChiusura = oraDue;
                    } else {
                        oraApertura = oraDue;
                        oraChiusura = oraUno;
                    }
                }
                this.INSERT_RISTORANTI_STATEMENT.bindString(8, oraApertura);
                this.INSERT_RISTORANTI_STATEMENT.bindString(9, oraChiusura);
                String servizi = "";
                for (int i = 0; i < this.RISTORANTI_SERVIZI.length; i++) {
                    boolean alternativa = this.RANDOM_HELPER.getRandomBoolean();
                    if (alternativa) {
                        servizi = servizi + this.RISTORANTI_SERVIZI[i] + "\n";
                    }
                }
                servizi = servizi.replaceAll("(\n)$", "");
                this.INSERT_RISTORANTI_STATEMENT.bindString(10, servizi);
                double votoMedioRecensioni = this.RANDOM_HELPER.selectFrom(this.RECENSIONI_VOTI);
                this.INSERT_RISTORANTI_STATEMENT.bindDouble(11, votoMedioRecensioni);
                double latitudine = this.RANDOM_HELPER.getRandomDoubleBetween(40.838, 40.945);
                this.INSERT_RISTORANTI_STATEMENT.bindDouble(12, latitudine);
                double longitudine = this.RANDOM_HELPER.getRandomDoubleBetween(14.048, 14.465);
                this.INSERT_RISTORANTI_STATEMENT.bindDouble(13, longitudine);
                this.INSERT_RISTORANTI_STATEMENT.executeInsert();
                int numeroTavoli = this.RANDOM_HELPER.getRandomIntBetween(10, 100);
                numeroTavoli = numeroTavoli++;
                for (int idTavoli = 1; idTavoli < numeroTavoli; idTavoli++) {
                    try {
                        this.INSERT_TAVOLI_RISTORANTI_STATEMENT.bindLong(1, id);
                        this.INSERT_TAVOLI_RISTORANTI_STATEMENT.bindLong(2, idTavoli);
                        int numeroPersone = this.RANDOM_HELPER.getRandomPositiveInt(15);
                        this.INSERT_TAVOLI_RISTORANTI_STATEMENT.bindLong(3, numeroPersone);
                        boolean alternativa = this.RANDOM_HELPER.getRandomBoolean();
                        if (alternativa) {
                            String dataPrenotazione = this.RANDOM_HELPER.getRandomYYYYMMDDDate(this.ANNO_ATTUALE);
                            Date date = this.YYYYMMDD_SIMPLE_DATE_FORMAT.parse(dataPrenotazione);
                            String day = this.GIORNI_SIMPLE_DATE_FORMAT.format(date);
                            if (day.compareTo(giornoChiusura) == 0) {
                                break;
                            }
                            this.INSERT_TAVOLI_RISTORANTI_STATEMENT.bindString(4, dataPrenotazione);
                            String daOra;
                            String aOra;
                            oraUno = this.RANDOM_HELPER.getRandomHourWithMinutes();
                            oraDue = this.RANDOM_HELPER.getRandomHourWithMinutes();
                            if (oraUno.compareTo(oraDue) == 0) {
                                throw this.SIMULAZIONE_VIOLAZIONE_VINCOLO_EXCEPTION;
                            } else {
                                if (oraUno.compareTo(oraDue) < 0) {
                                    daOra = oraUno;
                                    aOra = oraDue;
                                } else {
                                    daOra = oraDue;
                                    aOra = oraUno;
                                }
                            }
                            if (daOra.compareTo(oraApertura) < 0 || aOra.compareTo(oraChiusura) > 0) {
                                break;
                            }
                            this.INSERT_TAVOLI_RISTORANTI_STATEMENT.bindString(5, daOra);
                            this.INSERT_TAVOLI_RISTORANTI_STATEMENT.bindString(6, aOra);
                        } else {
                            this.INSERT_TAVOLI_RISTORANTI_STATEMENT.bindNull(4);
                            this.INSERT_TAVOLI_RISTORANTI_STATEMENT.bindNull(5);
                            this.INSERT_TAVOLI_RISTORANTI_STATEMENT.bindNull(6);
                        }
                        this.INSERT_TAVOLI_RISTORANTI_STATEMENT.executeInsert();
                    } catch (Exception exception) {

                    }
                }
            } catch (Exception exception) {

            }
        }
    }

    public void popolaAttrazioni() {
        String nome = "Attrazione";
        int id;
        for (id = 1; id < this.NUMERO_STRUTTURE + 1; id++) {
            try {
                this.INSERT_ATTRAZIONI_STATEMENT.bindLong(1, id);
                int numNome = this.RANDOM_HELPER.getRandomPositiveInt(this.MAX_POSTFISSO);
                this.INSERT_ATTRAZIONI_STATEMENT.bindString(2, nome + numNome);
                int numIndirizzo = this.RANDOM_HELPER.getRandomPositiveInt(this.MAX_POSTFISSO);
                this.INSERT_ATTRAZIONI_STATEMENT.bindString(3, this.INDIRIZZO + numIndirizzo);
                int numCitta = this.RANDOM_HELPER.getRandomPositiveInt(this.MAX_POSTFISSO);
                this.INSERT_ATTRAZIONI_STATEMENT.bindString(4, this.CITTA + numCitta);
                int numPaese = this.RANDOM_HELPER.getRandomPositiveInt(this.MAX_POSTFISSO);
                this.INSERT_ATTRAZIONI_STATEMENT.bindString(5, this.PAESE + numPaese);
                String tipologia = this.RANDOM_HELPER.selectFrom(this.ATTRAZIONI_TIPOLOGIE);
                this.INSERT_ATTRAZIONI_STATEMENT.bindString(6, tipologia);
                double costoBiglietto = 0;
                if (tipologia.compareTo(this.ATTRAZIONI_TIPOLOGIE[6]) != 0) {
                    costoBiglietto = this.RANDOM_HELPER.getRandomDoubleBetween(5, 125);
                    costoBiglietto = (double) Math.round(costoBiglietto * 100) / 100;
                }
                this.INSERT_ATTRAZIONI_STATEMENT.bindDouble(7, costoBiglietto);
                String giornoChiusura = this.RANDOM_HELPER.selectFrom(this.GIORNI);
                this.INSERT_ATTRAZIONI_STATEMENT.bindString(8, giornoChiusura);
                String oraUno = this.RANDOM_HELPER.getRandomHourWithMinutes();
                String oraDue = this.RANDOM_HELPER.getRandomHourWithMinutes();
                String oraApertura = "";
                String oraChiusura = "";
                if (oraUno.compareTo(oraDue) == 0) {
                    throw this.SIMULAZIONE_VIOLAZIONE_VINCOLO_EXCEPTION;
                } else {
                    if (oraUno.compareTo(oraDue) < 0) {
                        oraApertura = oraUno;
                        oraChiusura = oraDue;
                    } else {
                        oraApertura = oraDue;
                        oraChiusura = oraUno;
                    }
                }
                this.INSERT_ATTRAZIONI_STATEMENT.bindString(9, oraApertura);
                this.INSERT_ATTRAZIONI_STATEMENT.bindString(10, oraChiusura);
                String servizi = "";
                for (int i = 0; i < this.ATTRAZIONI_SERVIZI.length; i++) {
                    boolean alternativa = this.RANDOM_HELPER.getRandomBoolean();
                    if (alternativa) {
                        servizi = servizi + this.ATTRAZIONI_SERVIZI[i] + "\n";
                    }
                }
                servizi = servizi.replaceAll("(\n)$", "");
                this.INSERT_ATTRAZIONI_STATEMENT.bindString(11, servizi);
                double votoMedioRecensioni = this.RANDOM_HELPER.selectFrom(this.RECENSIONI_VOTI);
                this.INSERT_ATTRAZIONI_STATEMENT.bindDouble(12, votoMedioRecensioni);
                double latitudine = this.RANDOM_HELPER.getRandomDoubleBetween(40.838, 40.945);
                this.INSERT_ATTRAZIONI_STATEMENT.bindDouble(13, latitudine);
                double longitudine = this.RANDOM_HELPER.getRandomDoubleBetween(14.048, 14.465);
                this.INSERT_ATTRAZIONI_STATEMENT.bindDouble(14, longitudine);
                this.INSERT_ATTRAZIONI_STATEMENT.executeInsert();
            } catch (Exception exception) {

            }
        }
    }

    public void popolaFND() {
        String nome = "FND";
        int id;
        for (id = 1; id < this.NUMERO_STRUTTURE + 1; id++) {
            try {
                this.INSERT_FND_STATEMENT.bindLong(1, id);
                int numNome = this.RANDOM_HELPER.getRandomPositiveInt(this.MAX_POSTFISSO);
                this.INSERT_FND_STATEMENT.bindString(2, nome + numNome);
                int numIndirizzo = this.RANDOM_HELPER.getRandomPositiveInt(this.MAX_POSTFISSO);
                this.INSERT_FND_STATEMENT.bindString(3, this.INDIRIZZO + numIndirizzo);
                int numCitta = this.RANDOM_HELPER.getRandomPositiveInt(this.MAX_POSTFISSO);
                this.INSERT_FND_STATEMENT.bindString(4, this.CITTA + numCitta);
                int numPaese = this.RANDOM_HELPER.getRandomPositiveInt(this.MAX_POSTFISSO);
                this.INSERT_FND_STATEMENT.bindString(5, this.PAESE + numPaese);
                String tipologia = this.RANDOM_HELPER.selectFrom(this.FND_TIPOLOGIE);
                this.INSERT_FND_STATEMENT.bindString(6, tipologia);
                double costoMedio = this.RANDOM_HELPER.getRandomDoubleBetween(5, 250);
                costoMedio = (double) Math.round(costoMedio * 100) / 100;
                this.INSERT_FND_STATEMENT.bindDouble(7, costoMedio);
                String giornoChiusura = this.RANDOM_HELPER.selectFrom(this.GIORNI);
                this.INSERT_FND_STATEMENT.bindString(8, giornoChiusura);
                String oraUno = this.RANDOM_HELPER.getRandomHourWithMinutes();
                String oraDue = this.RANDOM_HELPER.getRandomHourWithMinutes();
                String oraApertura = "";
                String oraChiusura = "";
                if (oraUno.compareTo(oraDue) == 0) {
                    throw this.SIMULAZIONE_VIOLAZIONE_VINCOLO_EXCEPTION;
                } else {
                    if (oraUno.compareTo(oraDue) < 0) {
                        oraApertura = oraUno;
                        oraChiusura = oraDue;
                    } else {
                        oraApertura = oraDue;
                        oraChiusura = oraUno;
                    }
                }
                this.INSERT_FND_STATEMENT.bindString(9, oraApertura);
                this.INSERT_FND_STATEMENT.bindString(10, oraChiusura);
                String servizi = "";
                for (int i = 0; i < this.FND_SERVIZI.length; i++) {
                    boolean alternativa = this.RANDOM_HELPER.getRandomBoolean();
                    if (alternativa) {
                        servizi = servizi + this.FND_SERVIZI[i] + "\n";
                    }
                }
                servizi = servizi.replaceAll("(\n)$", "");
                this.INSERT_FND_STATEMENT.bindString(11, servizi);
                double votoMedioRecensioni = this.RANDOM_HELPER.selectFrom(this.RECENSIONI_VOTI);
                this.INSERT_FND_STATEMENT.bindDouble(12, votoMedioRecensioni);
                double latitudine = this.RANDOM_HELPER.getRandomDoubleBetween(40.838, 40.945);
                this.INSERT_FND_STATEMENT.bindDouble(13, latitudine);
                double longitudine = this.RANDOM_HELPER.getRandomDoubleBetween(14.048, 14.465);
                this.INSERT_FND_STATEMENT.bindDouble(14, longitudine);
                this.INSERT_FND_STATEMENT.executeInsert();
                if (servizi.contains("Coperto")) {
                    int numeroTavoli = this.RANDOM_HELPER.getRandomIntBetween(10, 100);
                    numeroTavoli = numeroTavoli++;
                    for (int idTavoli = 1; idTavoli < numeroTavoli; idTavoli++) {
                        try {
                            this.INSERT_TAVOLI_FND_STATEMENT.bindLong(1, id);
                            this.INSERT_TAVOLI_FND_STATEMENT.bindLong(2, idTavoli);
                            int numeroPersone = this.RANDOM_HELPER.getRandomPositiveInt(15);
                            this.INSERT_TAVOLI_FND_STATEMENT.bindLong(3, numeroPersone);
                            boolean alternativa = this.RANDOM_HELPER.getRandomBoolean();
                            if (alternativa) {
                                String dataPrenotazione = this.RANDOM_HELPER.getRandomYYYYMMDDDate(this.ANNO_ATTUALE);
                                Date date = this.YYYYMMDD_SIMPLE_DATE_FORMAT.parse(dataPrenotazione);
                                String day = this.GIORNI_SIMPLE_DATE_FORMAT.format(date);
                                if (day.compareTo(giornoChiusura) == 0) {
                                    break;
                                }
                                this.INSERT_TAVOLI_FND_STATEMENT.bindString(4, dataPrenotazione);
                                String daOra;
                                String aOra;
                                oraUno = this.RANDOM_HELPER.getRandomHourWithMinutes();
                                oraDue = this.RANDOM_HELPER.getRandomHourWithMinutes();
                                if (oraUno.compareTo(oraDue) == 0) {
                                    throw this.SIMULAZIONE_VIOLAZIONE_VINCOLO_EXCEPTION;
                                } else {
                                    if (oraUno.compareTo(oraDue) < 0) {
                                        daOra = oraUno;
                                        aOra = oraDue;
                                    } else {
                                        daOra = oraDue;
                                        aOra = oraUno;
                                    }
                                }
                                this.INSERT_TAVOLI_FND_STATEMENT.bindString(5, daOra);
                                this.INSERT_TAVOLI_FND_STATEMENT.bindString(6, aOra);
                            } else {
                                this.INSERT_TAVOLI_FND_STATEMENT.bindNull(4);
                                this.INSERT_TAVOLI_FND_STATEMENT.bindNull(5);
                                this.INSERT_TAVOLI_FND_STATEMENT.bindNull(6);
                            }
                            this.INSERT_TAVOLI_FND_STATEMENT.executeInsert();
                        } catch (Exception exception) {

                        }
                    }
                }
            } catch (Exception exception) {

            }
        }
    }

    public void popolaUtenti() {
        String nome = "Nome";
        String cognome = "Cognome";
        String nazionalita = "Nazionalita";
        String userId = "UserID";
        String password = "Password";
        String nickname = "Nickname";
        for (int i = 1; i < this.NUMERO_UTENTI + 1; i++) {
            try {
                this.INSERT_UTENTI_STATEMENT.bindString(1, nome + i);
                this.INSERT_UTENTI_STATEMENT.bindString(2, cognome + i);
                int numNazionalita = this.RANDOM_HELPER.getRandomPositiveInt(196);
                this.INSERT_UTENTI_STATEMENT.bindString(3, nazionalita + numNazionalita);
                String sesso;
                int sessoInt = this.RANDOM_HELPER.getRandomPositiveInt(3);
                if (sessoInt == 1) {
                    sesso = "m";
                }
                else {
                    if (sessoInt == 2) {
                        sesso = "f";
                    }
                    else {
                        sesso = "x";
                    }
                 }
                this.INSERT_UTENTI_STATEMENT.bindString(4, sesso);
                int annoNascitaInt = this.RANDOM_HELPER.getRandomIntBetween(1970, 2005);
                String annoNascita = String.valueOf(annoNascitaInt);
                String dataNascita = this.RANDOM_HELPER.getRandomYYYYMMDDDate(annoNascita);
                this.INSERT_UTENTI_STATEMENT.bindString(5, dataNascita);
                boolean alternativa = this.RANDOM_HELPER.getRandomBoolean();
                String annoIscrizione;
                if (alternativa) {
                    annoIscrizione = this.ANNO_PRECEDENTE;
                }
                else {
                    annoIscrizione = this.ANNO_ATTUALE;
                }
                String dataIscrizione = this.RANDOM_HELPER.getRandomYYYYMMDDDate(annoIscrizione);
                this.INSERT_UTENTI_STATEMENT.bindString(6, dataIscrizione);
                this.INSERT_UTENTI_STATEMENT.bindString(7, userId + i);
                alternativa = this.RANDOM_HELPER.getRandomBoolean();
                String nick;
                if (alternativa) {
                    nick = nickname + i;
                }
                else {
                    nick = nome + i + " " + cognome + i;
                }
                this.INSERT_UTENTI_STATEMENT.bindString(8, nick);
                this.popolaRecensioni(nick);
                this.INSERT_UTENTI_STATEMENT.bindString(9, password + i);
                alternativa = this.RANDOM_HELPER.getRandomBoolean();
                if (alternativa) {
                    this.INSERT_UTENTI_STATEMENT.bindLong(10, 1);
                }
                else {
                    this.INSERT_UTENTI_STATEMENT.bindLong(10, 0);
                }
                alternativa = this.RANDOM_HELPER.getRandomBoolean();
                if (alternativa) {
                    this.INSERT_UTENTI_STATEMENT.bindLong(11, 1);
                }
                else {
                    this.INSERT_UTENTI_STATEMENT.bindLong(11, 0);
                }
                alternativa = this.RANDOM_HELPER.getRandomBoolean();
                if (alternativa) {
                    this.INSERT_UTENTI_STATEMENT.bindLong(12, 1);
                }
                else {
                    this.INSERT_UTENTI_STATEMENT.bindLong(12, 0);
                }
                int numeroRecensioni = this.RANDOM_HELPER.getRandomPositiveInt(100);
                this.INSERT_UTENTI_STATEMENT.bindLong(13, numeroRecensioni);
                this.INSERT_UTENTI_STATEMENT.executeInsert();
            }
            catch (Exception exception) {

            }
        }
    }

    public void popolaRecensioni(String nickName) {
        String titolo =  "Titolo";
        for (int i = 1; i < this.NUMERO_STRUTTURE + 1; i++) {
            boolean utenteHaScritto = this.RANDOM_HELPER.getRandomBoolean();
            if (utenteHaScritto) {
                try {
                    int numeroRecensioni = this.RANDOM_HELPER.getRandomPositiveInt(this.NUMERO_RECENSIONI_UTENTE_STRUTTURA_MAX);
                    for (int k = 0; k < numeroRecensioni; k++) {
                        boolean alternativa = this.RANDOM_HELPER.getRandomBoolean();
                        String annoRecensione;
                        if (alternativa) {
                            annoRecensione = this.ANNO_PRECEDENTE;
                        } else {
                            annoRecensione = this.ANNO_ATTUALE;
                        }
                        this.INSERT_RECENSIONI_ALBERGHI_STATEMENT.bindLong(1, i);
                        this.INSERT_RECENSIONI_ALBERGHI_STATEMENT.bindString(2, nickName);
                        String data = this.RANDOM_HELPER.getRandomYYYYMMDDDate(annoRecensione);
                        this.INSERT_RECENSIONI_ALBERGHI_STATEMENT.bindString(3, data);
                        int numTitolo = this.RANDOM_HELPER.getRandomPositiveInt(this.MAX_POSTFISSO);
                        this.INSERT_RECENSIONI_ALBERGHI_STATEMENT.bindString(4, titolo + numTitolo);
                        double voto = this.RANDOM_HELPER.selectFrom(this.RECENSIONI_VOTI);
                        this.INSERT_RECENSIONI_ALBERGHI_STATEMENT.bindDouble(5, voto);
                        String testo = this.RANDOM_HELPER.getRandomAlphabeticAzString(this.LUNGHEZZA_RECENSIONE_MAX);
                        this.INSERT_RECENSIONI_ALBERGHI_STATEMENT.bindString(6, testo);
                        this.INSERT_RECENSIONI_ALBERGHI_STATEMENT.executeInsert();
                    }
                } catch (Exception exception) {

                }
            }
        }
        for (int i = 1; i < this.NUMERO_STRUTTURE + 1; i++) {
            boolean utenteHaScritto = this.RANDOM_HELPER.getRandomBoolean();
            if (utenteHaScritto) {
                try {
                    int numeroRecensioni = this.RANDOM_HELPER.getRandomPositiveInt(this.NUMERO_RECENSIONI_UTENTE_STRUTTURA_MAX);
                    for (int k = 0; k < numeroRecensioni; k++) {
                        boolean alternativa = this.RANDOM_HELPER.getRandomBoolean();
                        String annoRecensione;
                        if (alternativa) {
                            annoRecensione = this.ANNO_PRECEDENTE;
                        } else {
                            annoRecensione = this.ANNO_ATTUALE;
                        }
                        this.INSERT_RECENSIONI_RISTORANTI_STATEMENT.bindLong(1, i);
                        this.INSERT_RECENSIONI_RISTORANTI_STATEMENT.bindString(2, nickName);
                        String data = this.RANDOM_HELPER.getRandomYYYYMMDDDate(annoRecensione);
                        this.INSERT_RECENSIONI_RISTORANTI_STATEMENT.bindString(3, data);
                        int numTitolo = this.RANDOM_HELPER.getRandomPositiveInt(this.MAX_POSTFISSO);
                        this.INSERT_RECENSIONI_RISTORANTI_STATEMENT.bindString(4, titolo + numTitolo);
                        double voto = this.RANDOM_HELPER.selectFrom(this.RECENSIONI_VOTI);
                        this.INSERT_RECENSIONI_RISTORANTI_STATEMENT.bindDouble(5, voto);
                        String testo = this.RANDOM_HELPER.getRandomAlphabeticAzString(this.LUNGHEZZA_RECENSIONE_MAX);
                        this.INSERT_RECENSIONI_RISTORANTI_STATEMENT.bindString(6, testo);
                        this.INSERT_RECENSIONI_RISTORANTI_STATEMENT.executeInsert();
                    }
                } catch (Exception exception) {

                }
            }
        }
        for (int i = 1; i < this.NUMERO_STRUTTURE + 1; i++) {
            boolean utenteHaScritto = this.RANDOM_HELPER.getRandomBoolean();
            if (utenteHaScritto) {
                try {
                    int numeroRecensioni = this.RANDOM_HELPER.getRandomPositiveInt(this.NUMERO_RECENSIONI_UTENTE_STRUTTURA_MAX);
                    for (int k = 0; k < numeroRecensioni; k++) {
                        boolean alternativa = this.RANDOM_HELPER.getRandomBoolean();
                        String annoRecensione;
                        if (alternativa) {
                            annoRecensione = this.ANNO_PRECEDENTE;
                        } else {
                            annoRecensione = this.ANNO_ATTUALE;
                        }
                        this.INSERT_RECENSIONI_ATTRAZIONI_STATEMENT.bindLong(1, i);
                        this.INSERT_RECENSIONI_ATTRAZIONI_STATEMENT.bindString(2, nickName);
                        String data = this.RANDOM_HELPER.getRandomYYYYMMDDDate(annoRecensione);
                        this.INSERT_RECENSIONI_ATTRAZIONI_STATEMENT.bindString(3, data);
                        int numTitolo = this.RANDOM_HELPER.getRandomPositiveInt(this.MAX_POSTFISSO);
                        this.INSERT_RECENSIONI_ATTRAZIONI_STATEMENT.bindString(4, titolo + numTitolo);
                        double voto = this.RANDOM_HELPER.selectFrom(this.RECENSIONI_VOTI);
                        this.INSERT_RECENSIONI_ATTRAZIONI_STATEMENT.bindDouble(5, voto);
                        String testo = this.RANDOM_HELPER.getRandomAlphabeticAzString(this.LUNGHEZZA_RECENSIONE_MAX);
                        this.INSERT_RECENSIONI_ATTRAZIONI_STATEMENT.bindString(6, testo);
                        this.INSERT_RECENSIONI_ATTRAZIONI_STATEMENT.executeInsert();
                    }
                } catch (Exception exception) {

                }
            }
        }
        for (int i = 1; i < this.NUMERO_STRUTTURE + 1; i++) {
            boolean utenteHaScritto = this.RANDOM_HELPER.getRandomBoolean();
            if (utenteHaScritto) {
                try {
                    int numeroRecensioni = this.RANDOM_HELPER.getRandomPositiveInt(this.NUMERO_RECENSIONI_UTENTE_STRUTTURA_MAX);
                    for (int k = 0; k < numeroRecensioni; k++) {
                        boolean alternativa = this.RANDOM_HELPER.getRandomBoolean();
                        String annoRecensione;
                        if (alternativa) {
                            annoRecensione = this.ANNO_PRECEDENTE;
                        } else {
                            annoRecensione = this.ANNO_ATTUALE;
                        }
                        this.INSERT_RECENSIONI_FND_STATEMENT.bindLong(1, i);
                        this.INSERT_RECENSIONI_FND_STATEMENT.bindString(2, nickName);
                        String data = this.RANDOM_HELPER.getRandomYYYYMMDDDate(annoRecensione);
                        this.INSERT_RECENSIONI_FND_STATEMENT.bindString(3, data);
                        int numTitolo = this.RANDOM_HELPER.getRandomPositiveInt(this.MAX_POSTFISSO);
                        this.INSERT_RECENSIONI_FND_STATEMENT.bindString(4, titolo + numTitolo);
                        double voto = this.RANDOM_HELPER.selectFrom(this.RECENSIONI_VOTI);
                        this.INSERT_RECENSIONI_FND_STATEMENT.bindDouble(5, voto);
                        String testo = this.RANDOM_HELPER.getRandomAlphabeticAzString(this.LUNGHEZZA_RECENSIONE_MAX);
                        this.INSERT_RECENSIONI_FND_STATEMENT.bindString(6, testo);
                        this.INSERT_RECENSIONI_FND_STATEMENT.executeInsert();
                    }
                } catch (Exception exception) {

                }
            }
        }
    }

    @Override
    public LinkedList<ObjectsModel> getObjectsModels(String query) {
        LinkedList<ObjectsModel> objectsModelLinkedList = super.getObjectsModels(query);
        if (objectsModelLinkedList != null) {
            if (objectsModelLinkedList.size() > 0) {
                Iterator<ObjectsModel> iterator = objectsModelLinkedList.iterator();
                Random random = new Random();
                Drawable drawable = null;
                Bitmap bitmap = null;
                int rand;
                int imageId = 0;
                ObjectsModel objectsModel = null;
                if (query.contains(this.ALBERGHI_QUERY)) {
                    while (iterator.hasNext()) {
                        objectsModel = iterator.next();
                        String tipo = (String) objectsModel.getValue(5);
                        if (tipo.compareTo(this.ALBERGHI_TIPOLOGIE[1]) == 0 || tipo.compareTo(this.ALBERGHI_TIPOLOGIE[3]) == 0 || tipo.compareTo(this.ALBERGHI_TIPOLOGIE[5]) == 0) {
                            rand = random.nextInt(8);
                            switch (rand) {
                                case 0:
                                    imageId = R.drawable.ost1;
                                    break;
                                case 1:
                                    imageId = R.drawable.ost2;
                                    break;
                                case 2:
                                    imageId = R.drawable.ost3;
                                    break;
                                case 3:
                                    imageId = R.drawable.ost4;
                                    break;
                                case 4:
                                    imageId = R.drawable.ost5;
                                    break;
                                case 5:
                                    imageId = R.drawable.ost6;
                                    break;
                                case 6:
                                    imageId = R.drawable.ost7;
                                    break;
                                case 7:
                                    imageId = R.drawable.ost8;
                                    break;
                            }
                            drawable = ContextCompat.getDrawable(this.activity, imageId);
                            bitmap = ((BitmapDrawable) drawable).getBitmap();
                            bitmap = Bitmap.createScaledBitmap(bitmap, this.LARGHEZZA_IMG_STRUTTURA, this.ALTEZZA_IMG_STRUTTURA, false);
                            objectsModel.addFirstCouple(this.ATTRIBUTO_IMG, bitmap);
                            rand = random.nextInt(8);
                            switch (rand) {
                                case 0:
                                    imageId = R.drawable.ostc1;
                                    break;
                                case 1:
                                    imageId = R.drawable.ostc2;
                                    break;
                                case 2:
                                    imageId = R.drawable.ostc3;
                                    break;
                                case 3:
                                    imageId = R.drawable.ostc4;
                                    break;
                                case 4:
                                    imageId = R.drawable.ostc5;
                                    break;
                                case 5:
                                    imageId = R.drawable.ostc6;
                                    break;
                                case 6:
                                    imageId = R.drawable.ostc7;
                                    break;
                                case 7:
                                    imageId = R.drawable.ostc8;
                                    break;
                            }
                            drawable = ContextCompat.getDrawable(this.activity, imageId);
                            bitmap = ((BitmapDrawable) drawable).getBitmap();
                            bitmap = Bitmap.createScaledBitmap(bitmap, this.LARGHEZZA_IMG_STRUTTURA, this.ALTEZZA_IMG_STRUTTURA, false);
                            objectsModel.addFirstCouple(this.ATTRIBUTO_IMG, bitmap);
                            rand = random.nextInt(8);
                            switch (rand) {
                                case 0:
                                    imageId = R.drawable.ostc1;
                                    break;
                                case 1:
                                    imageId = R.drawable.ostc2;
                                    break;
                                case 2:
                                    imageId = R.drawable.ostc3;
                                    break;
                                case 3:
                                    imageId = R.drawable.ostc4;
                                    break;
                                case 4:
                                    imageId = R.drawable.ostc5;
                                    break;
                                case 5:
                                    imageId = R.drawable.ostc6;
                                    break;
                                case 6:
                                    imageId = R.drawable.ostc7;
                                    break;
                                case 7:
                                    imageId = R.drawable.ostc8;
                                    break;
                            }
                            drawable = ContextCompat.getDrawable(this.activity, imageId);
                            bitmap = ((BitmapDrawable) drawable).getBitmap();
                            bitmap = Bitmap.createScaledBitmap(bitmap, this.LARGHEZZA_IMG_STRUTTURA, this.ALTEZZA_IMG_STRUTTURA, false);
                            objectsModel.addFirstCouple(this.ATTRIBUTO_IMG, bitmap);
                        } else {
                            rand = random.nextInt(10);
                            switch (rand) {
                                case 0:
                                    imageId = R.drawable.hotel1;
                                    break;
                                case 1:
                                    imageId = R.drawable.hotel2;
                                    break;
                                case 2:
                                    imageId = R.drawable.hotel3;
                                    break;
                                case 3:
                                    imageId = R.drawable.hotel4;
                                    break;
                                case 4:
                                    imageId = R.drawable.hotel5;
                                    break;
                                case 5:
                                    imageId = R.drawable.hotel6;
                                    break;
                                case 6:
                                    imageId = R.drawable.hotel7;
                                    break;
                                case 7:
                                    imageId = R.drawable.hotel8;
                                    break;
                                case 8:
                                    imageId = R.drawable.hotel9;
                                    break;
                                case 9:
                                    imageId = R.drawable.hotel10;
                                    break;
                            }
                            drawable = ContextCompat.getDrawable(this.activity, imageId);
                            bitmap = ((BitmapDrawable) drawable).getBitmap();
                            bitmap = Bitmap.createScaledBitmap(bitmap, this.LARGHEZZA_IMG_STRUTTURA, this.ALTEZZA_IMG_STRUTTURA, false);
                            objectsModel.addFirstCouple(this.ATTRIBUTO_IMG, bitmap);
                            rand = random.nextInt(10);
                            switch (rand) {
                                case 0:
                                    imageId = R.drawable.camera1;
                                    break;
                                case 1:
                                    imageId = R.drawable.camera2;
                                    break;
                                case 2:
                                    imageId = R.drawable.camera3;
                                    break;
                                case 3:
                                    imageId = R.drawable.camera4;
                                    break;
                                case 4:
                                    imageId = R.drawable.camera5;
                                    break;
                                case 5:
                                    imageId = R.drawable.camera6;
                                    break;
                                case 6:
                                    imageId = R.drawable.camera7;
                                    break;
                                case 7:
                                    imageId = R.drawable.camera8;
                                    break;
                                case 8:
                                    imageId = R.drawable.camera9;
                                    break;
                                case 9:
                                    imageId = R.drawable.camera10;
                                    break;
                            }
                            drawable = ContextCompat.getDrawable(this.activity, imageId);
                            bitmap = ((BitmapDrawable) drawable).getBitmap();
                            bitmap = Bitmap.createScaledBitmap(bitmap, this.LARGHEZZA_IMG_STRUTTURA, this.ALTEZZA_IMG_STRUTTURA, false);
                            objectsModel.addFirstCouple(this.ATTRIBUTO_IMG, bitmap);
                            rand = random.nextInt(10);
                            switch (rand) {
                                case 0:
                                    imageId = R.drawable.reception1;
                                    break;
                                case 1:
                                    imageId = R.drawable.reception2;
                                    break;
                                case 2:
                                    imageId = R.drawable.reception3;
                                    break;
                                case 3:
                                    imageId = R.drawable.reception4;
                                    break;
                                case 4:
                                    imageId = R.drawable.reception5;
                                    break;
                                case 5:
                                    imageId = R.drawable.reception6;
                                    break;
                                case 6:
                                    imageId = R.drawable.reception7;
                                    break;
                                case 7:
                                    imageId = R.drawable.reception8;
                                    break;
                                case 8:
                                    imageId = R.drawable.reception9;
                                    break;
                                case 9:
                                    imageId = R.drawable.reception10;
                                    break;
                            }
                            drawable = ContextCompat.getDrawable(this.activity, imageId);
                            bitmap = ((BitmapDrawable) drawable).getBitmap();
                            bitmap = Bitmap.createScaledBitmap(bitmap, this.LARGHEZZA_IMG_STRUTTURA, this.ALTEZZA_IMG_STRUTTURA, false);
                            objectsModel.addFirstCouple(this.ATTRIBUTO_IMG, bitmap);
                        }
                    }
                }
                else {
                    if (query.contains(this.RISTORANTI_QUERY)) {
                        while (iterator.hasNext()) {
                            objectsModel = iterator.next();
                            rand = random.nextInt(10);
                            switch (rand) {
                                case 0:
                                    imageId = R.drawable.rest1;
                                    break;
                                case 1:
                                    imageId = R.drawable.rest2;
                                    break;
                                case 2:
                                    imageId = R.drawable.rest3;
                                    break;
                                case 3:
                                    imageId = R.drawable.rest4;
                                    break;
                                case 4:
                                    imageId = R.drawable.rest5;
                                    break;
                                case 5:
                                    imageId = R.drawable.rest6;
                                    break;
                                case 6:
                                    imageId = R.drawable.rest7;
                                    break;
                                case 7:
                                    imageId = R.drawable.rest8;
                                    break;
                                case 8:
                                    imageId = R.drawable.rest9;
                                    break;
                                case 9:
                                    imageId = R.drawable.rest10;
                                    break;
                            }
                            drawable = ContextCompat.getDrawable(this.activity, imageId);
                            bitmap = ((BitmapDrawable) drawable).getBitmap();
                            bitmap = Bitmap.createScaledBitmap(bitmap, this.LARGHEZZA_IMG_STRUTTURA, this.ALTEZZA_IMG_STRUTTURA, false);
                            objectsModel.addFirstCouple(this.ATTRIBUTO_IMG, bitmap);
                            rand = random.nextInt(10);
                            switch (rand) {
                                case 0:
                                    imageId = R.drawable.tav1;
                                    break;
                                case 1:
                                    imageId = R.drawable.tav2;
                                    break;
                                case 2:
                                    imageId = R.drawable.tav3;
                                    break;
                                case 3:
                                    imageId = R.drawable.tav4;
                                    break;
                                case 4:
                                    imageId = R.drawable.tav5;
                                    break;
                                case 5:
                                    imageId = R.drawable.tav6;
                                    break;
                                case 6:
                                    imageId = R.drawable.tav7;
                                    break;
                                case 7:
                                    imageId = R.drawable.tav8;
                                    break;
                                case 8:
                                    imageId = R.drawable.tav9;
                                    break;
                                case 9:
                                    imageId = R.drawable.tav10;
                                    break;
                            }
                            drawable = ContextCompat.getDrawable(this.activity, imageId);
                            bitmap = ((BitmapDrawable) drawable).getBitmap();
                            bitmap = Bitmap.createScaledBitmap(bitmap, this.LARGHEZZA_IMG_STRUTTURA, this.ALTEZZA_IMG_STRUTTURA, false);
                            objectsModel.addFirstCouple(this.ATTRIBUTO_IMG, bitmap);
                            rand = random.nextInt(10);
                            switch (rand) {
                                case 0:
                                    imageId = R.drawable.food1;
                                    break;
                                case 1:
                                    imageId = R.drawable.food2;
                                    break;
                                case 2:
                                    imageId = R.drawable.food3;
                                    break;
                                case 3:
                                    imageId = R.drawable.food4;
                                    break;
                                case 4:
                                    imageId = R.drawable.food5;
                                    break;
                                case 5:
                                    imageId = R.drawable.food6;
                                    break;
                                case 6:
                                    imageId = R.drawable.food7;
                                    break;
                                case 7:
                                    imageId = R.drawable.food8;
                                    break;
                                case 8:
                                    imageId = R.drawable.food9;
                                    break;
                                case 9:
                                    imageId = R.drawable.food10;
                                    break;
                            }
                            drawable = ContextCompat.getDrawable(this.activity, imageId);
                            bitmap = ((BitmapDrawable) drawable).getBitmap();
                            bitmap = Bitmap.createScaledBitmap(bitmap, this.LARGHEZZA_IMG_STRUTTURA, this.ALTEZZA_IMG_STRUTTURA, false);
                            objectsModel.addFirstCouple(this.ATTRIBUTO_IMG, bitmap);
                        }
                    }
                    else {
                        if (query.contains(this.ATTRAZIONI_QUERY)) {
                            while (iterator.hasNext()) {
                                objectsModel = iterator.next();
                                String tipo = (String) objectsModel.getValue(5);
                                if (tipo.contains(this.MUSEI_QUERY)) {
                                    rand = random.nextInt(10);
                                    switch (rand) {
                                        case 0:
                                            imageId = R.drawable.mus1;
                                            break;
                                        case 1:
                                            imageId = R.drawable.mus2;
                                            break;
                                        case 2:
                                            imageId = R.drawable.mus3;
                                            break;
                                        case 3:
                                            imageId = R.drawable.mus4;
                                            break;
                                        case 4:
                                            imageId = R.drawable.mus5;
                                            break;
                                        case 5:
                                            imageId = R.drawable.mus6;
                                            break;
                                        case 6:
                                            imageId = R.drawable.mus7;
                                            break;
                                        case 7:
                                            imageId = R.drawable.mus8;
                                            break;
                                        case 8:
                                            imageId = R.drawable.mus9;
                                            break;
                                        case 9:
                                            imageId = R.drawable.mus10;
                                            break;
                                    }
                                    drawable = ContextCompat.getDrawable(this.activity, imageId);
                                    bitmap = ((BitmapDrawable) drawable).getBitmap();
                                    bitmap = Bitmap.createScaledBitmap(bitmap, this.LARGHEZZA_IMG_STRUTTURA, this.ALTEZZA_IMG_STRUTTURA, false);
                                    objectsModel.addFirstCouple(this.ATTRIBUTO_IMG, bitmap);
                                    if (tipo.compareTo(this.ATTRAZIONI_TIPOLOGIE[0]) == 0) {
                                        rand = random.nextInt(10);
                                        switch (rand) {
                                            case 0:
                                                imageId = R.drawable.musart1;
                                                break;
                                            case 1:
                                                imageId = R.drawable.musart2;
                                                break;
                                            case 2:
                                                imageId = R.drawable.musart3;
                                                break;
                                            case 3:
                                                imageId = R.drawable.musart4;
                                                break;
                                            case 4:
                                                imageId = R.drawable.musart5;
                                                break;
                                            case 5:
                                                imageId = R.drawable.musart6;
                                                break;
                                            case 6:
                                                imageId = R.drawable.musart7;
                                                break;
                                            case 7:
                                                imageId = R.drawable.musart8;
                                                break;
                                            case 8:
                                                imageId = R.drawable.musart9;
                                                break;
                                            case 9:
                                                imageId = R.drawable.musart10;
                                                break;
                                        }
                                        drawable = ContextCompat.getDrawable(this.activity, imageId);
                                        bitmap = ((BitmapDrawable) drawable).getBitmap();
                                        bitmap = Bitmap.createScaledBitmap(bitmap, this.LARGHEZZA_IMG_STRUTTURA, this.ALTEZZA_IMG_STRUTTURA, false);
                                        objectsModel.addFirstCouple(this.ATTRIBUTO_IMG, bitmap);
                                    }
                                    else {
                                        if (tipo.compareTo(this.ATTRAZIONI_TIPOLOGIE[1]) == 0 || tipo.compareTo(this.ATTRAZIONI_TIPOLOGIE[2]) == 0) {
                                            rand = random.nextInt(15);
                                            switch (rand) {
                                                case 0:
                                                    imageId = R.drawable.mussto1;
                                                    break;
                                                case 1:
                                                    imageId = R.drawable.mussto2;
                                                    break;
                                                case 2:
                                                    imageId = R.drawable.mussto3;
                                                    break;
                                                case 3:
                                                    imageId = R.drawable.mussto4;
                                                    break;
                                                case 4:
                                                    imageId = R.drawable.mussto5;
                                                    break;
                                                case 5:
                                                    imageId = R.drawable.mussto6;
                                                    break;
                                                case 6:
                                                    imageId = R.drawable.mussto7;
                                                    break;
                                                case 7:
                                                    imageId = R.drawable.mussto8;
                                                    break;
                                                case 8:
                                                    imageId = R.drawable.mussto9;
                                                    break;
                                                case 9:
                                                    imageId = R.drawable.mussto10;
                                                    break;
                                                case 10:
                                                    imageId = R.drawable.mussto11;
                                                    break;
                                                case 11:
                                                    imageId = R.drawable.mussto12;
                                                    break;
                                                case 12:
                                                    imageId = R.drawable.mussto13;
                                                    break;
                                                case 13:
                                                    imageId = R.drawable.mussto14;
                                                    break;
                                                case 14:
                                                    imageId = R.drawable.mussto15;
                                                    break;
                                            }
                                            drawable = ContextCompat.getDrawable(this.activity, imageId);
                                            bitmap = ((BitmapDrawable) drawable).getBitmap();
                                            bitmap = Bitmap.createScaledBitmap(bitmap, this.LARGHEZZA_IMG_STRUTTURA, this.ALTEZZA_IMG_STRUTTURA, false);
                                            objectsModel.addFirstCouple(this.ATTRIBUTO_IMG, bitmap);
                                        }
                                        else {
                                            if (tipo.compareTo(this.ATTRAZIONI_TIPOLOGIE[3]) == 0) {
                                                rand = random.nextInt(10);
                                                switch (rand) {
                                                    case 0:
                                                        imageId = R.drawable.mussc1;
                                                        break;
                                                    case 1:
                                                        imageId = R.drawable.mussc2;
                                                        break;
                                                    case 2:
                                                        imageId = R.drawable.mussc3;
                                                        break;
                                                    case 3:
                                                        imageId = R.drawable.mussc4;
                                                        break;
                                                    case 4:
                                                        imageId = R.drawable.mussc5;
                                                        break;
                                                    case 5:
                                                        imageId = R.drawable.mussc6;
                                                        break;
                                                    case 6:
                                                        imageId = R.drawable.mussc7;
                                                        break;
                                                    case 7:
                                                        imageId = R.drawable.mussc8;
                                                        break;
                                                    case 8:
                                                        imageId = R.drawable.mussc9;
                                                        break;
                                                    case 9:
                                                        imageId = R.drawable.mussc10;
                                                        break;
                                                }
                                                drawable = ContextCompat.getDrawable(this.activity, imageId);
                                                bitmap = ((BitmapDrawable) drawable).getBitmap();
                                                bitmap = Bitmap.createScaledBitmap(bitmap, this.LARGHEZZA_IMG_STRUTTURA, this.ALTEZZA_IMG_STRUTTURA, false);
                                                objectsModel.addFirstCouple(this.ATTRIBUTO_IMG, bitmap);
                                            }
                                            else {
                                                if (tipo.compareTo(this.ATTRAZIONI_TIPOLOGIE[4]) == 0) {
                                                    rand = random.nextInt(10);
                                                    switch (rand) {
                                                        case 0:
                                                            imageId = R.drawable.musstonat1;
                                                            break;
                                                        case 1:
                                                            imageId = R.drawable.musstonat2;
                                                            break;
                                                        case 2:
                                                            imageId = R.drawable.musstonat3;
                                                            break;
                                                        case 3:
                                                            imageId = R.drawable.musstonat4;
                                                            break;
                                                        case 4:
                                                            imageId = R.drawable.musstonat5;
                                                            break;
                                                        case 5:
                                                            imageId = R.drawable.musstonat6;
                                                            break;
                                                        case 6:
                                                            imageId = R.drawable.musstonat7;
                                                            break;
                                                        case 7:
                                                            imageId = R.drawable.musstonat8;
                                                            break;
                                                        case 8:
                                                            imageId = R.drawable.musstonat9;
                                                            break;
                                                        case 9:
                                                            imageId = R.drawable.musstonat10;
                                                            break;
                                                    }
                                                    drawable = ContextCompat.getDrawable(this.activity, imageId);
                                                    bitmap = ((BitmapDrawable) drawable).getBitmap();
                                                    bitmap = Bitmap.createScaledBitmap(bitmap, this.LARGHEZZA_IMG_STRUTTURA, this.ALTEZZA_IMG_STRUTTURA, false);
                                                    objectsModel.addFirstCouple(this.ATTRIBUTO_IMG, bitmap);
                                                }
                                            }
                                        }
                                    }
                                }
                                else {
                                    if (tipo.compareTo(this.ATTRAZIONI_TIPOLOGIE[5]) == 0) {
                                        rand = random.nextInt(10);
                                        switch (rand) {
                                            case 0:
                                                imageId = R.drawable.mon1;
                                                break;
                                            case 1:
                                                imageId = R.drawable.mon2;
                                                break;
                                            case 2:
                                                imageId = R.drawable.mon3;
                                                break;
                                            case 3:
                                                imageId = R.drawable.mon4;
                                                break;
                                            case 4:
                                                imageId = R.drawable.mon5;
                                                break;
                                            case 5:
                                                imageId = R.drawable.mon6;
                                                break;
                                            case 6:
                                                imageId = R.drawable.mon7;
                                                break;
                                            case 7:
                                                imageId = R.drawable.mon8;
                                                break;
                                            case 8:
                                                imageId = R.drawable.mon9;
                                                break;
                                            case 9:
                                                imageId = R.drawable.mon10;
                                                break;
                                        }
                                        drawable = ContextCompat.getDrawable(this.activity, imageId);
                                        bitmap = ((BitmapDrawable) drawable).getBitmap();
                                        bitmap = Bitmap.createScaledBitmap(bitmap, this.LARGHEZZA_IMG_STRUTTURA, this.ALTEZZA_IMG_STRUTTURA, false);
                                        objectsModel.addFirstCouple(this.ATTRIBUTO_IMG, bitmap);
                                        drawable = ContextCompat.getDrawable(this.activity, imageId);
                                        bitmap = ((BitmapDrawable) drawable).getBitmap();
                                        bitmap = Bitmap.createScaledBitmap(bitmap, this.LARGHEZZA_IMG_STRUTTURA, this.ALTEZZA_IMG_STRUTTURA, false);
                                        objectsModel.addFirstCouple(this.ATTRIBUTO_IMG, bitmap);
                                    } else {
                                        if (tipo.compareTo(this.ATTRAZIONI_TIPOLOGIE[7]) == 0) {
                                            rand = random.nextInt(6);
                                            switch (rand) {
                                                case 0:
                                                    imageId = R.drawable.zooe1;
                                                    break;
                                                case 1:
                                                    imageId = R.drawable.zooe2;
                                                    break;
                                                case 2:
                                                    imageId = R.drawable.zooe3;
                                                    break;
                                                case 3:
                                                    imageId = R.drawable.zooe4;
                                                    break;
                                                case 4:
                                                    imageId = R.drawable.zooe5;
                                                    break;
                                                case 5:
                                                    imageId = R.drawable.zooe6;
                                                    break;
                                            }
                                            drawable = ContextCompat.getDrawable(this.activity, imageId);
                                            bitmap = ((BitmapDrawable) drawable).getBitmap();
                                            bitmap = Bitmap.createScaledBitmap(bitmap, this.LARGHEZZA_IMG_STRUTTURA, this.ALTEZZA_IMG_STRUTTURA, false);
                                            objectsModel.addFirstCouple(this.ATTRIBUTO_IMG, bitmap);
                                            rand = random.nextInt(6);
                                            switch (rand) {
                                                case 0:
                                                    imageId = R.drawable.zoo1;
                                                    break;
                                                case 1:
                                                    imageId = R.drawable.zoo2;
                                                    break;
                                                case 2:
                                                    imageId = R.drawable.zoo3;
                                                    break;
                                                case 3:
                                                    imageId = R.drawable.zoo4;
                                                    break;
                                                case 4:
                                                    imageId = R.drawable.zoo5;
                                                    break;
                                                case 5:
                                                    imageId = R.drawable.zoo6;
                                                    break;
                                            }
                                            drawable = ContextCompat.getDrawable(this.activity, imageId);
                                            bitmap = ((BitmapDrawable) drawable).getBitmap();
                                            bitmap = Bitmap.createScaledBitmap(bitmap, this.LARGHEZZA_IMG_STRUTTURA, this.ALTEZZA_IMG_STRUTTURA, false);
                                            objectsModel.addFirstCouple(this.ATTRIBUTO_IMG, bitmap);
                                        } else {
                                            if (tipo.compareTo(this.ATTRAZIONI_TIPOLOGIE[8]) == 0) {
                                                rand = random.nextInt(6);
                                                switch (rand) {
                                                    case 0:
                                                        imageId = R.drawable.acqe1;
                                                        break;
                                                    case 1:
                                                        imageId = R.drawable.acqe2;
                                                        break;
                                                    case 2:
                                                        imageId = R.drawable.acqe3;
                                                        break;
                                                    case 3:
                                                        imageId = R.drawable.acqe4;
                                                        break;
                                                    case 4:
                                                        imageId = R.drawable.acqe5;
                                                        break;
                                                    case 5:
                                                        imageId = R.drawable.acqe6;
                                                        break;
                                                }
                                                drawable = ContextCompat.getDrawable(this.activity, imageId);
                                                bitmap = ((BitmapDrawable) drawable).getBitmap();
                                                bitmap = Bitmap.createScaledBitmap(bitmap, this.LARGHEZZA_IMG_STRUTTURA, this.ALTEZZA_IMG_STRUTTURA, false);
                                                objectsModel.addFirstCouple(this.ATTRIBUTO_IMG, bitmap);
                                                rand = random.nextInt(6);
                                                imageId = -1;
                                                switch (rand) {
                                                    case 0:
                                                        imageId = R.drawable.acq1;
                                                        break;
                                                    case 1:
                                                        imageId = R.drawable.acq2;
                                                        break;
                                                    case 2:
                                                        imageId = R.drawable.acq3;
                                                        break;
                                                    case 3:
                                                        imageId = R.drawable.acq4;
                                                        break;
                                                    case 4:
                                                        imageId = R.drawable.acq5;
                                                        break;
                                                    case 5:
                                                        imageId = R.drawable.acq6;
                                                        break;
                                                }
                                                drawable = ContextCompat.getDrawable(this.activity, imageId);
                                                bitmap = ((BitmapDrawable) drawable).getBitmap();
                                                bitmap = Bitmap.createScaledBitmap(bitmap, this.LARGHEZZA_IMG_STRUTTURA, this.ALTEZZA_IMG_STRUTTURA, false);
                                                objectsModel.addFirstCouple(this.ATTRIBUTO_IMG, bitmap);
                                            } else {
                                                if (tipo.compareTo(this.ATTRAZIONI_TIPOLOGIE[6]) == 0 || tipo.compareTo(this.ATTRAZIONI_TIPOLOGIE[9]) == 0) {
                                                    rand = random.nextInt(10);
                                                    switch (rand) {
                                                        case 0:
                                                            imageId = R.drawable.park1;
                                                            break;
                                                        case 1:
                                                            imageId = R.drawable.park2;
                                                            break;
                                                        case 2:
                                                            imageId = R.drawable.park3;
                                                            break;
                                                        case 3:
                                                            imageId = R.drawable.park4;
                                                            break;
                                                        case 4:
                                                            imageId = R.drawable.park5;
                                                            break;
                                                        case 5:
                                                            imageId = R.drawable.park6;
                                                            break;
                                                        case 6:
                                                            imageId = R.drawable.park7;
                                                            break;
                                                        case 7:
                                                            imageId = R.drawable.park8;
                                                            break;
                                                        case 8:
                                                            imageId = R.drawable.park9;
                                                            break;
                                                        case 9:
                                                            imageId = R.drawable.park10;
                                                            break;
                                                    }
                                                    drawable = ContextCompat.getDrawable(this.activity, imageId);
                                                    bitmap = ((BitmapDrawable) drawable).getBitmap();
                                                    bitmap = Bitmap.createScaledBitmap(bitmap, this.LARGHEZZA_IMG_STRUTTURA, this.ALTEZZA_IMG_STRUTTURA, false);
                                                    objectsModel.addFirstCouple(this.ATTRIBUTO_IMG, bitmap);
                                                    rand = random.nextInt(10);
                                                    switch (rand) {
                                                        case 0:
                                                            imageId = R.drawable.park1;
                                                            break;
                                                        case 1:
                                                            imageId = R.drawable.park2;
                                                            break;
                                                        case 2:
                                                            imageId = R.drawable.park3;
                                                            break;
                                                        case 3:
                                                            imageId = R.drawable.park4;
                                                            break;
                                                        case 4:
                                                            imageId = R.drawable.park5;
                                                            break;
                                                        case 5:
                                                            imageId = R.drawable.park6;
                                                            break;
                                                        case 6:
                                                            imageId = R.drawable.park7;
                                                            break;
                                                        case 7:
                                                            imageId = R.drawable.park8;
                                                            break;
                                                        case 8:
                                                            imageId = R.drawable.park9;
                                                            break;
                                                        case 9:
                                                            imageId = R.drawable.park10;
                                                            break;
                                                    }
                                                    drawable = ContextCompat.getDrawable(this.activity, imageId);
                                                    bitmap = ((BitmapDrawable) drawable).getBitmap();
                                                    bitmap = Bitmap.createScaledBitmap(bitmap, this.LARGHEZZA_IMG_STRUTTURA, this.ALTEZZA_IMG_STRUTTURA, false);
                                                    objectsModel.addFirstCouple(this.ATTRIBUTO_IMG, bitmap);
                                                } else {
                                                    rand = random.nextInt(10);
                                                    switch (rand) {
                                                        case 0:
                                                            imageId = R.drawable.nature1;
                                                            break;
                                                        case 1:
                                                            imageId = R.drawable.nature2;
                                                            break;
                                                        case 2:
                                                            imageId = R.drawable.nature3;
                                                            break;
                                                        case 3:
                                                            imageId = R.drawable.nature4;
                                                            break;
                                                        case 4:
                                                            imageId = R.drawable.nature5;
                                                            break;
                                                        case 5:
                                                            imageId = R.drawable.nature6;
                                                            break;
                                                        case 6:
                                                            imageId = R.drawable.nature7;
                                                            break;
                                                        case 7:
                                                            imageId = R.drawable.nature8;
                                                            break;
                                                        case 8:
                                                            imageId = R.drawable.nature9;
                                                            break;
                                                        case 9:
                                                            imageId = R.drawable.nature10;
                                                            break;
                                                    }
                                                    drawable = ContextCompat.getDrawable(this.activity, imageId);
                                                    bitmap = ((BitmapDrawable) drawable).getBitmap();
                                                    bitmap = Bitmap.createScaledBitmap(bitmap, this.LARGHEZZA_IMG_STRUTTURA, this.ALTEZZA_IMG_STRUTTURA, false);
                                                    objectsModel.addFirstCouple(this.ATTRIBUTO_IMG, bitmap);
                                                    rand = random.nextInt(10);
                                                    switch (rand) {
                                                        case 0:
                                                            imageId = R.drawable.nature1;
                                                            break;
                                                        case 1:
                                                            imageId = R.drawable.nature2;
                                                            break;
                                                        case 2:
                                                            imageId = R.drawable.nature3;
                                                            break;
                                                        case 3:
                                                            imageId = R.drawable.nature4;
                                                            break;
                                                        case 4:
                                                            imageId = R.drawable.nature5;
                                                            break;
                                                        case 5:
                                                            imageId = R.drawable.nature6;
                                                            break;
                                                        case 6:
                                                            imageId = R.drawable.nature7;
                                                            break;
                                                        case 7:
                                                            imageId = R.drawable.nature8;
                                                            break;
                                                        case 8:
                                                            imageId = R.drawable.nature9;
                                                            break;
                                                        case 9:
                                                            imageId = R.drawable.nature10;
                                                            break;
                                                    }
                                                    drawable = ContextCompat.getDrawable(this.activity, imageId);
                                                    bitmap = ((BitmapDrawable) drawable).getBitmap();
                                                    bitmap = Bitmap.createScaledBitmap(bitmap, this.LARGHEZZA_IMG_STRUTTURA, this.ALTEZZA_IMG_STRUTTURA, false);
                                                    objectsModel.addFirstCouple(this.ATTRIBUTO_IMG, bitmap);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        else {
                            if (query.contains(this.FND_QUERY)) {
                                while (iterator.hasNext()) {
                                    objectsModel = iterator.next();
                                    rand = random.nextInt(10);
                                    switch (rand) {
                                        case 0:
                                            imageId = R.drawable.fnd1;
                                            break;
                                        case 1:
                                            imageId = R.drawable.fnd2;
                                            break;
                                        case 2:
                                            imageId = R.drawable.fnd3;
                                            break;
                                        case 3:
                                            imageId = R.drawable.fnd4;
                                            break;
                                        case 4:
                                            imageId = R.drawable.fnd5;
                                            break;
                                        case 5:
                                            imageId = R.drawable.fnd6;
                                            break;
                                        case 6:
                                            imageId = R.drawable.fnd7;
                                            break;
                                        case 7:
                                            imageId = R.drawable.fnd8;
                                            break;
                                        case 8:
                                            imageId = R.drawable.fnd9;
                                            break;
                                        case 9:
                                            imageId = R.drawable.fnd10;
                                            break;
                                    }
                                    drawable = ContextCompat.getDrawable(this.activity, imageId);
                                    bitmap = ((BitmapDrawable) drawable).getBitmap();
                                    bitmap = Bitmap.createScaledBitmap(bitmap, this.LARGHEZZA_IMG_STRUTTURA, this.ALTEZZA_IMG_STRUTTURA, false);
                                    objectsModel.addFirstCouple(this.ATTRIBUTO_IMG, bitmap);
                                    rand = random.nextInt(10);
                                    switch (rand) {
                                        case 0:
                                            imageId = R.drawable.ptav1;
                                            break;
                                        case 1:
                                            imageId = R.drawable.ptav2;
                                            break;
                                        case 2:
                                            imageId = R.drawable.ptav3;
                                            break;
                                        case 3:
                                            imageId = R.drawable.ptav4;
                                            break;
                                        case 4:
                                            imageId = R.drawable.ptav5;
                                            break;
                                        case 5:
                                            imageId = R.drawable.ptav6;
                                            break;
                                        case 6:
                                            imageId = R.drawable.ptav7;
                                            break;
                                        case 7:
                                            imageId = R.drawable.ptav8;
                                            break;
                                        case 8:
                                            imageId = R.drawable.ptav9;
                                            break;
                                        case 9:
                                            imageId = R.drawable.ptav10;
                                            break;
                                    }
                                    drawable = ContextCompat.getDrawable(this.activity, imageId);
                                    bitmap = ((BitmapDrawable) drawable).getBitmap();
                                    bitmap = Bitmap.createScaledBitmap(bitmap, this.LARGHEZZA_IMG_STRUTTURA, this.ALTEZZA_IMG_STRUTTURA, false);
                                    objectsModel.addFirstCouple(this.ATTRIBUTO_IMG, bitmap);
                                }
                            }
                            else {
                                if (query.contains(this.RECENSIONI_QUERY)) {
                                    while (iterator.hasNext()) {
                                        objectsModel = iterator.next();
                                        objectsModel.addFirstCouple(this.ATTRIBUTO_IMG, null);
                                    }
                                    iterator = objectsModelLinkedList.iterator();
                                    while (iterator.hasNext()) {
                                        objectsModel = iterator.next();
                                        Bitmap bMap = (Bitmap) objectsModel.getValue(0);
                                        if (bMap == null) {
                                            rand = random.nextInt(20);
                                            switch (rand) {
                                                case 0:
                                                    imageId = R.drawable.user1;
                                                    break;
                                                case 1:
                                                    imageId = R.drawable.user2;
                                                    break;
                                                case 2:
                                                    imageId = R.drawable.user3;
                                                    break;
                                                case 3:
                                                    imageId = R.drawable.user4;
                                                    break;
                                                case 4:
                                                    imageId = R.drawable.user5;
                                                    break;
                                                case 5:
                                                    imageId = R.drawable.user6;
                                                    break;
                                                case 6:
                                                    imageId = R.drawable.user7;
                                                    break;
                                                case 7:
                                                    imageId = R.drawable.user8;
                                                    break;
                                                case 8:
                                                    imageId = R.drawable.user9;
                                                    break;
                                                case 9:
                                                    imageId = R.drawable.user10;
                                                    break;
                                                case 10:
                                                    imageId = R.drawable.user11;
                                                    break;
                                                case 11:
                                                    imageId = R.drawable.user12;
                                                    break;
                                                case 12:
                                                    imageId = R.drawable.user13;
                                                    break;
                                                case 13:
                                                    imageId = R.drawable.user14;
                                                    break;
                                                case 14:
                                                    imageId = R.drawable.user15;
                                                    break;
                                                case 15:
                                                    imageId = R.drawable.user16;
                                                    break;
                                                case 16:
                                                    imageId = R.drawable.user17;
                                                    break;
                                                case 17:
                                                    imageId = R.drawable.user18;
                                                    break;
                                                case 18:
                                                    imageId = R.drawable.user19;
                                                    break;
                                                case 19:
                                                    imageId = R.drawable.user20;
                                                    break;
                                            }
                                            String nickname = (String) objectsModel.getValue(1);
                                            Iterator<ObjectsModel> objectsModelIterator = objectsModelLinkedList.iterator();
                                            while (objectsModelIterator.hasNext()) {
                                                ObjectsModel objMod = objectsModelIterator.next();
                                                String nick = (String) objMod.getValue(1);
                                                bMap = (Bitmap) objMod.getValue(0);
                                                if (bMap == null && nick.compareTo(nickname) == 0) {
                                                    drawable = ContextCompat.getDrawable(this.activity, imageId);
                                                    bitmap = ((BitmapDrawable) drawable).getBitmap();
                                                    bitmap = Bitmap.createScaledBitmap(bitmap, RecensioneView.LARGHEZZA_IMG_PROFILO, RecensioneView.ALTEZZA_IMG_PROFILO, false);
                                                    objMod.updateValue(this.ATTRIBUTO_IMG, bitmap);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return objectsModelLinkedList;
    }

    public AbstractList<String> getSuggerimentiStrutturaSpecifica() {
        LinkedList<String> suggerimenti = new LinkedList<>();
        LinkedList<ObjectsModel> objectsModels = super.getObjectsModels("select nome,indirizzo,citta,paese from alberghi ");
        Iterator<ObjectsModel> iterator = objectsModels.iterator();
        String className = AbstractAlbergoModel.class.getName();
        while (iterator.hasNext()) {
            ObjectsModel objectsModel = iterator.next();
            String nome = (String) objectsModel.getValue(0);
            String indirizzo = (String) objectsModel.getValue(1);
            String citta = (String) objectsModel.getValue(2);
            String paese = (String) objectsModel.getValue(3);
            suggerimenti.add(className + "\n" + nome + "\n" + indirizzo + ", " + citta + ", " + paese);
        }
        objectsModels = super.getObjectsModels("select nome,indirizzo,citta,paese from ristoranti ");
        iterator = objectsModels.iterator();
        className = AbstractRistoranteModel.class.getName();
        while (iterator.hasNext()) {
            ObjectsModel objectsModel = iterator.next();
            String nome = (String) objectsModel.getValue(0);
            String indirizzo = (String) objectsModel.getValue(1);
            String citta = (String) objectsModel.getValue(2);
            String paese = (String) objectsModel.getValue(3);
            suggerimenti.add(className + "\n" + nome + "\n" + indirizzo + ", " + citta + ", " + paese);
        }
        objectsModels = super.getObjectsModels("select nome,indirizzo,citta,paese from attrazioni ");
        iterator = objectsModels.iterator();
        className = AbstractAttrazioneModel.class.getName();
        while (iterator.hasNext()) {
            ObjectsModel objectsModel = iterator.next();
            String nome = (String) objectsModel.getValue(0);
            String indirizzo = (String) objectsModel.getValue(1);
            String citta = (String) objectsModel.getValue(2);
            String paese = (String) objectsModel.getValue(3);
            suggerimenti.add(className + "\n" + nome + "\n" + indirizzo + ", " + citta + ", " + paese);
        }
        objectsModels = super.getObjectsModels("select nome,indirizzo,citta,paese from fnd ");
        iterator = objectsModels.iterator();
        className = AbstractFNDModel.class.getName();
        while (iterator.hasNext()) {
            ObjectsModel objectsModel = iterator.next();
            String nome = (String) objectsModel.getValue(0);
            String indirizzo = (String) objectsModel.getValue(1);
            String citta = (String) objectsModel.getValue(2);
            String paese = (String) objectsModel.getValue(3);
            suggerimenti.add(className + "\n" + nome + "\n" + indirizzo + ", " + citta + ", " + paese);
        }
        return suggerimenti;
    }

    @Override
    protected String getTablename(String query) {
        if (query.contains(this.ALBERGHI_QUERY) || query.contains(this.ALBERGHI_QUERY_2)) {
            return "alberghi";
        }
        else {
            if (query.contains(this.RISTORANTI_QUERY)) {
                return "ristoranti";
            }
            else {
                if (query.contains(this.ATTRAZIONI_QUERY)) {
                    return "attrazioni";
                }
                else {
                    if (query.contains(this.FND_QUERY)) {
                        return "fnd";
                    }
                    else {
                        if (query.contains(this.RECENSIONI_QUERY)) {
                            return "recensioni";
                        }
                        else {
                            if (query.contains(this.UTENTI_QUERY)) {
                                return "utenti";
                            }
                        }
                    }
                }
            }
        }
        return "";
    }

    @Override
    protected ObjectsModel buildObjectsModel(String tablename) {
        ObjectsModel objectsModel = null;
        switch(tablename) {
            case "alberghi":
                objectsModel = new LocaleAlbergoModel();
                break;
            case "ristoranti":
                objectsModel = new LocaleRistoranteModel();
                break;
            case "attrazioni":
                objectsModel = new LocaleAttrazioneModel();
                break;
            case "fnd":
                objectsModel = new LocaleFNDModel();
                break;
            case "recensioni":
                objectsModel = new LocaleRecensioneModel();
                break;
            case "utenti":
                objectsModel = new LocaleUtenteModel();
                break;
        }
        objectsModel.setTablename(tablename);
        return objectsModel;
    }

}