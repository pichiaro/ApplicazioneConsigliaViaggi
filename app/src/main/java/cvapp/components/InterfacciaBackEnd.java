package cvapp.components;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.PointF;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.text.SimpleDateFormat;
import java.util.AbstractList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Properties;
import java.util.Vector;

import asynctasks.DriverManagerConnector;
import components.AbstractJDBCAssetPropertiesConnector;
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
import cvapp.views.AbstractStrutturaView;
import cvapp.views.AlbergoView;
import cvapp.views.AttrazioneView;
import cvapp.views.FNDView;
import cvapp.views.RistoranteView;
import models.ObjectsModel;
import utilities.SQLUtility;

public class InterfacciaBackEnd extends AbstractJDBCAssetPropertiesConnector implements InterfacciaQuery {

    private static DriverManagerConnector SINGLETON_DRIVER_MANAGER_CONNECTOR;

    public static DriverManagerConnector getSingletonDriverManagerConnector() {
        return InterfacciaBackEnd.SINGLETON_DRIVER_MANAGER_CONNECTOR;
    }

    public InterfacciaBackEnd(Activity activity, String propertiesFilename) {
        super(activity, propertiesFilename);
        this.initialize();
    }

    public void initialize() {
        Activity activity = this.getActivity();
        if (activity != null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            builder.setTitle("Back-End Error");
            builder.setCancelable(true);
            builder.setPositiveButton("", null);
            AlertDialog alertDialog = builder.create();
            this.setAlertDialog(alertDialog);
            try {
                Resources resources = activity.getResources();
                AssetManager assetManager = resources.getAssets();
                InputStream inputStream = assetManager.open(this.getPropertiesFilename());
                Properties properties = new Properties();
                properties.load(inputStream);
                if (InterfacciaBackEnd.SINGLETON_DRIVER_MANAGER_CONNECTOR == null) {
                    DriverManagerConnector driverManagerConnector = new DriverManagerConnector();
                    this.setDriverManagerConnector(driverManagerConnector);
                    InterfacciaBackEnd.SINGLETON_DRIVER_MANAGER_CONNECTOR = driverManagerConnector;
                    String url = (String) properties.get("url");
                    driverManagerConnector.execute(url, properties);
                }
                else {
                    Connection connection = InterfacciaBackEnd.SINGLETON_DRIVER_MANAGER_CONNECTOR.getConnection();
                    if (connection == null) {
                        DriverManagerConnector driverManagerConnector = new DriverManagerConnector();
                        this.setDriverManagerConnector(driverManagerConnector);
                        InterfacciaBackEnd.SINGLETON_DRIVER_MANAGER_CONNECTOR = driverManagerConnector;
                        String url = (String) properties.get("url");
                        driverManagerConnector.execute(url, properties);
                    }
                }
            } catch (Exception exception) {
                if (alertDialog != null) {
                    alertDialog.setMessage(exception.getClass().getName() + "\n" + exception.getMessage());
                    alertDialog.show();
                }
            }
        }
    }

    @Override
    public boolean executeCommand(String update) {
        DriverManagerConnector driverManagerConnector = InterfacciaBackEnd.SINGLETON_DRIVER_MANAGER_CONNECTOR;
        Connection connection = driverManagerConnector.getConnection();
        if (connection != null) {
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(update);
                preparedStatement.executeUpdate();
                connection.commit();
                preparedStatement.close();
                connection.close();
                return true;
            }
            catch(Exception exception) {
                AlertDialog alertDialog = this.getAlertDialog();
                if (alertDialog != null) {
                    alertDialog.setMessage(exception.getClass().getName() + "\n" + exception.getMessage());
                    alertDialog.show();
                }
            }
        }
        return false;
    }

    @Override
    public boolean insert(String tableName, AbstractList<String> attributes, AbstractList<Object> values) {
        DriverManagerConnector driverManagerConnector = InterfacciaBackEnd.SINGLETON_DRIVER_MANAGER_CONNECTOR;
        Connection connection = driverManagerConnector.getConnection();
        if (connection != null) {
            try {
                if (attributes.size() == values.size()) {
                    String attributeList = "(";
                    String valuesList = "(";
                    Iterator<String> stringIterator = attributes.iterator();
                    while (stringIterator.hasNext()) {
                        String attribute = stringIterator.next();
                        attributeList = attributeList + attribute + ",";
                        valuesList = valuesList + "?,";
                    }
                    attributeList = attributeList + ")";
                    attributeList = attributeList.replaceAll(",\\)$", ")");
                    valuesList = valuesList + ")";
                    valuesList = valuesList.replaceAll(",\\)$", ")");
                    PreparedStatement insertStatement = connection.prepareStatement("insert into " + tableName + " " + attributeList + " values " + valuesList + ";");
                    Iterator<Object> iterator = values.iterator();
                    int parameterIndex = 1;
                    while (iterator.hasNext()) {
                        Object object = iterator.next();
                        insertStatement.setObject(parameterIndex, object);
                    }
                    insertStatement.executeUpdate();
                    connection.commit();
                    insertStatement.close();
                    connection.close();
                    return true;
                }
            } catch (Exception exception) {
                AlertDialog alertDialog = this.getAlertDialog();
                if (alertDialog != null) {
                    alertDialog.setMessage(exception.getClass().getName() + "\n" + exception.getMessage());
                    alertDialog.show();
                }
            }
        }
        return false;
    }

    public AbstractList<ObjectsModel> select(String query) {
        DriverManagerConnector driverManagerConnector = InterfacciaBackEnd.SINGLETON_DRIVER_MANAGER_CONNECTOR;
        Connection connection = driverManagerConnector.getConnection();
        LinkedList<ObjectsModel> objectsModels = new LinkedList<>();
        if (connection != null) {
            try {
                PreparedStatement queryStatement = connection.prepareStatement(query);
                ResultSet resultSet = queryStatement.executeQuery();
                String tableName = this.getTablename(query);
                ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
                int columnCount = resultSetMetaData.getColumnCount();
                while (resultSet.next()) {
                    ObjectsModel objectsModel = this.buildObjectsModel(tableName);
                    for (int index = 1; index <= columnCount; index++) {
                        Object object = resultSet.getObject(index);
                        String attribute = resultSetMetaData.getColumnName(index);
                        objectsModel.addCouple(attribute, object);
                    }
                    objectsModels.addFirst(objectsModel);
                }
            } catch (Exception exception) {
                AlertDialog alertDialog = this.getAlertDialog();
                if (alertDialog != null) {
                    alertDialog.setMessage(exception.getClass().getName() + "\n" + exception.getMessage());
                    alertDialog.show();
                }
            }
        }
        return objectsModels;
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

    private final String ALBERGHI_QUERY = " alberghi,";
    private final String ALBERGHI_QUERY_2 = " alberghi ";
    private final String RISTORANTI_QUERY = " ristoranti ";
    private final String ATTRAZIONI_QUERY = " attrazioni ";
    private final String FND_QUERY = " fnd ";
    private final String RECENSIONI_QUERY = " recensioni";
    private final String UTENTI_QUERY = " utenti ";

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
    public AbstractList<Object> prepareRecensioniValues(String strutturaId, String user, String data, String titolo , float voto, String testo) {
        Vector<Object> objects = new Vector<>();
        objects.add(Integer.parseInt(strutturaId));
        objects.add(user);
        objects.add(data);
        objects.add(titolo);
        objects.add(voto);
        objects.add(testo);
        return objects;
    }

    @Override
    public AbstractList<Object> prepareUtentiValues(String nome, String cognome, String nazionalita, String genere, String dataNascita, String dataRegistrazione, String userId, String nickname, String password , boolean etaPrivacy, boolean generePrivacy, boolean nazionalitaPrivacy) {
        Vector<Object> objects = new Vector<>();
        objects.add(nome);
        objects.add(cognome);
        objects.add(nazionalita);
        genere = genere.toLowerCase();
        if (genere.compareTo("uomo") == 0) {
            objects.add("m");
        }
        else {
            if (genere.compareTo("donna") == 0) {
                objects.add("f");
            }
            else {
                objects.add("x");
            }
        }
        objects.add(dataNascita);
        objects.add(dataRegistrazione);
        objects.add(userId);
        objects.add(nickname);
        objects.add(password);
        if (nazionalitaPrivacy) {
            objects.add(1);
        }
        else {
            objects.add(0);
        }
        if (generePrivacy) {
            objects.add(1);
        }
        else {
            objects.add(0);
        }
        if (etaPrivacy) {
            objects.add(1);
        }
        else {
            objects.add(0);
        }
        objects.add(0);
        return objects;
    }

    @Override
    public String getUpdateRecensioniCommand(String user) {
        return "update " + this.getUserTableName() + " set numero_recensioni = numero_recensioni + 1 where " + this.getUserNicknameAttribute() + " = '" + user + "'";
    }

    @Override
    public String getSelectAlberghi(Object...values) {
        String selectAlberghi = "select img,id,nome,indirizzo,citta,paese,tipologia,stelle,costo,servizi,voto_recensioni_medio,latitudine,longitudine from alberghi,(select id_albergo,min(costo) as costo from camere_alberghi group by id_albergo) where id_albergo = id and ";
        String votoClausola = this.costruisciVotoClausolaAlberghi(Float.parseFloat(values[0].toString()), Float.parseFloat(values[1].toString()));
        String personeClausola = this.costruisciPrenotazioneClausolaAlberghi((String) values[2], (String) values[3], (String) values[4], (String) values[5], Integer.parseInt(values[6].toString()), Integer.parseInt(values[7].toString()), Float.parseFloat(values[8].toString()), Float.parseFloat(values[9].toString()));
        String tipologiaClausola = this.costruisciTipologiaClausolaAlberghi((String[]) values[10]);
        String stelleClausola = this.costruisciStelleClausolaAlberghi((String[]) values[11]);
        String serviziClausola = this.costruisciServiziClausolaAlberghi((String[]) values[12]);
        String serviziRistorazioneClausola = this.costruisciServiziRistorazioneClausolaAlberghi((String[]) values[13]);
        String posizioneClausola = this.costruisciPosizioneClausolaAlberghi((PointF) values[14], (int) Float.parseFloat(values[15].toString()));
        String finaleClausola = SQLUtility.getAndProposition(posizioneClausola, tipologiaClausola, stelleClausola, serviziRistorazioneClausola, serviziClausola, votoClausola, personeClausola);
        selectAlberghi = this.getFinaleClausolaAlberghi(selectAlberghi, finaleClausola);
        selectAlberghi = this.costruisciSortClausolaAlberghi(selectAlberghi, (String) values[16], (Boolean) values[17], Integer.parseInt(values[18].toString()));
        return selectAlberghi;
    }

    @Override
    public String getSelectAttrazioni(Object...values) {
        String selectAttrazioni = "select img,id,nome,indirizzo,citta,paese,tipologia,costo_biglietto,giorno_chiusura,ora_apertura,ora_chiusura,servizi,voto_recensioni_medio,latitudine,longitudine from attrazioni";
        String giornoClausola = this.costruisciGiornoChiusuraClausolaAttrazioni((String) values[0], (String) values[1]);
        String costoClausola = this.costruisciCostoClausolaAttrazioni(Float.parseFloat(values[2].toString()), Float.parseFloat(values[3].toString()));
        String votoClausola = this.costruisciVotoClausolaAttrazioni(Float.parseFloat(values[4].toString()), Float.parseFloat(values[5].toString()));
        String tipologiaClausola = this.costruisciTipologiaClausolaAttrazioni((String[]) values[6]);
        String serviziClausola = this.costruisciServiziClausolaAttrazioni((String[]) values[7]);
        String guideClausola = this.costruisciGuidaClausolaAttrazioni((String[]) values[8]);
        String posizioneClausola = this.costruisciPosizioneClausolaAttrazioni((PointF) values[9], (int) Float.parseFloat(values[10].toString()));
        String finaleClausola = SQLUtility.getAndProposition(tipologiaClausola, costoClausola, votoClausola, posizioneClausola, serviziClausola, guideClausola, giornoClausola);
        selectAttrazioni = this.getFinaleClausolaAttrazioni(selectAttrazioni, finaleClausola);
        selectAttrazioni = this.costruisciSortClausolaAttrazioni(selectAttrazioni, (String) values[11], (Boolean) values[12], Integer.parseInt(values[13].toString()));
        return selectAttrazioni;
    }

    @Override
    public String getSelectFND(Object...values) {
        String selectFND = "select img,id,nome,indirizzo,citta,paese,tipologia,costo_medio,giorno_chiusura,ora_apertura,ora_chiusura,servizi,voto_recensioni_medio,latitudine,longitudine from fnd";
        String giornoClausola = this.costruisciGiornoChiusuraClausolaFND((String) values[0], (String) values[1]);
        String personeClausola = this.costruisciPrenotazioneClausolaFND((String) values[0], (String) values[1], Integer.parseInt(values[2].toString()), Integer.parseInt(values[3].toString()));
        String costoClausola = this.costruisciCostoClausolaFND(Float.parseFloat(values[4].toString()), Float.parseFloat(values[5].toString()));
        String votoClausola = this.costruisciVotoClausolaFND(Float.parseFloat(values[6].toString()), Float.parseFloat(values[7].toString()));
        String serviziClausola = this.costruisciServiziClausolaFND((String[]) values[8]);
        String tipologiaClausola = this.costruisciTipologiaClausolaFND((String[]) values[9]);
        String posizioneClausola = this.costruisciPosizioneClausolaFND((PointF) values[10], (int) Float.parseFloat(values[11].toString()));
        String finaleClausola = SQLUtility.getAndProposition(costoClausola, serviziClausola, tipologiaClausola, votoClausola, posizioneClausola, giornoClausola, personeClausola);
        selectFND = this.getFinaleClausolaFND(selectFND, finaleClausola);
        selectFND = this.costruisciSortClausolaFND(selectFND, (String) values[12], (Boolean) values[13], Integer.parseInt(values[14].toString()));
        return selectFND;
    }

    @Override
    public String getSelectRistoranti(Object...values) {
        String selectRistoranti = "select img,id,nome,indirizzo,citta,paese,costo_medio,giorno_chiusura,ora_apertura,ora_chiusura,servizi,voto_recensioni_medio,latitudine,longitudine from ristoranti";
        String giornoChiusuraClausola = this.costruisciGiornoChiusuraClausolaRistoranti((String) values[0], (String) values[1]);
        String personeClausola = this.costruisciPrenotazioneClausolaRistoranti((String) values[0], (String) values[1], Integer.parseInt(values[2].toString()), Integer.parseInt(values[3].toString()));
        String costoClausola = this.costruisciCostoClausolaRistoranti(Float.parseFloat(values[4].toString()), Float.parseFloat(values[5].toString()));
        String votoClausola = this.costruisciVotoClausolaRistoranti(Float.parseFloat(values[6].toString()), Float.parseFloat(values[7].toString()));
        String serviziClausola = this.costruisciServiziClausolaRistoranti((String[]) values[8]);
        String cucineClausola = this.costruisciCucinaClausolaRistoranti((String[]) values[9]);
        String posizioneClausola = this.costruisciPosizioneClausolaRistoranti((PointF) values[10], (int) Float.parseFloat(values[11].toString()));
        String finaleClausola = SQLUtility.getAndProposition(serviziClausola, cucineClausola, costoClausola, votoClausola, posizioneClausola, giornoChiusuraClausola, personeClausola);
        selectRistoranti = this.getFinaleClausolaRistoranti(selectRistoranti, finaleClausola);
        selectRistoranti = this.costruisciSortClausolaRistoranti(selectRistoranti, (String) values[12], (Boolean) values[13], Integer.parseInt(values[14].toString()));
        return selectRistoranti;
    }

    @Override
    public String getSelectRecensioni(AbstractStrutturaView strutturaView, Object...values) {
        String selectRecensioni = this.getSelectRecensioni(strutturaView);
        String votiClausola = this.costruisciVotiClausolaRecensioni((String[]) values[0]);
        String dataClausola = this.costruisciDataClausolaRecensioni((String) values[1], (String) values[2]);
        String finaleClausola = SQLUtility.getAndProposition(votiClausola, dataClausola);
        selectRecensioni = this.getFinaleClausolaRecensioni(selectRecensioni, finaleClausola);
        selectRecensioni = this.costruisciSortClausolaRecensioni(selectRecensioni, (String) values[3], (Boolean) values[4], Integer.parseInt(values[5].toString()));
        return selectRecensioni;
    }

    @Override
    public String getSelectSpecificaAlbergo(String id) {
        return "select img,id,nome,indirizzo,citta,paese,tipologia,stelle,costo,servizi,voto_recensioni_medio,latitudine,longitudine from alberghi,(select id_albergo,min(costo) as costo from camere_alberghi group by id_albergo) where id_albergo = id and nome || ' ' || indirizzo || ', ' || citta || ', ' || paese = '" + id + "'";
    }

    @Override
    public String getSelectSpecificaRistorante(String id) {
        return "select img,id,nome,indirizzo,citta,paese,costo_medio,giorno_chiusura,ora_apertura,ora_chiusura,servizi,voto_recensioni_medio,latitudine,longitudine from ristoranti where nome || ' ' || indirizzo || ', ' || citta || ', ' || paese = '" + id +"'";
    }

    @Override
    public String getSelectSpecificaAttrazione(String id) {
        return "select img,id,nome,indirizzo,citta,paese,tipologia,costo_biglietto,giorno_chiusura,ora_apertura,ora_chiusura,servizi,voto_recensioni_medio,latitudine,longitudine from attrazioni where nome || ' ' || indirizzo || ', ' || citta || ', ' || paese = '" + id +"'";
    }

    @Override
    public String getSelectSpecificaFND(String id) {
        return "select img,id,nome,indirizzo,citta,paese,tipologia,costo_medio,giorno_chiusura,ora_apertura,ora_chiusura,servizi,voto_recensioni_medio,latitudine,longitudine from fnd where nome || ' ' || indirizzo || ', ' || citta || ', ' || paese = '" + id +"'";
    }

    @Override
    public String getSelectVicineAlberghi(AbstractStrutturaView ultimaStrutturaVisitata, int distanza, int numMax) {
        String limitClausola = " limit " + numMax;
        String noSeStesso = " and id <> " + ultimaStrutturaVisitata.getPositionID();
        PointF posizione = new PointF(ultimaStrutturaVisitata.getLatitude(), ultimaStrutturaVisitata.getLongitude());
        String radiusClausola = this.costruisciPosizioneClausolaAlberghi(posizione, distanza);
        String select = "select img,id,nome,indirizzo,citta,paese,tipologia,stelle,costo,servizi,voto_recensioni_medio,latitudine,longitudine from alberghi,(select id_albergo,min(costo) as costo from camere_alberghi group by id_albergo) where id_albergo = id and " + radiusClausola + limitClausola;
        if (ultimaStrutturaVisitata instanceof AlbergoView) {
            select = select.replace(limitClausola, noSeStesso + limitClausola);
        }
        return select;
    }

    @Override
    public String getSelectVicineRistoranti(AbstractStrutturaView ultimaStrutturaVisitata, int distanza, int numMax) {
        String limitClausola = " limit " + numMax;
        String noSeStesso = " and id <> " + ultimaStrutturaVisitata.getPositionID();
        PointF posizione = new PointF(ultimaStrutturaVisitata.getLatitude(), ultimaStrutturaVisitata.getLongitude());
        String radiusClausola = this.costruisciPosizioneClausolaAlberghi(posizione, distanza);
        String select = "select img,id,nome,indirizzo,citta,paese,costo_medio,giorno_chiusura,ora_apertura,ora_chiusura,servizi,voto_recensioni_medio,latitudine,longitudine from ristoranti where " + radiusClausola + limitClausola;
        if (ultimaStrutturaVisitata instanceof RistoranteView) {
            select = select.replace(limitClausola, noSeStesso + limitClausola);
        }
        return select;
    }

    @Override
    public String getSelectVicineAttrazioni(AbstractStrutturaView ultimaStrutturaVisitata, int distanza, int numMax) {
        String limitClausola = " limit " + numMax;
        String noSeStesso = " and id <> " + ultimaStrutturaVisitata.getPositionID();
        PointF posizione = new PointF(ultimaStrutturaVisitata.getLatitude(), ultimaStrutturaVisitata.getLongitude());
        String radiusClausola = this.costruisciPosizioneClausolaAlberghi(posizione, distanza);
        String select = "select img,id,nome,indirizzo,citta,paese,tipologia,costo_biglietto,giorno_chiusura,ora_apertura,ora_chiusura,servizi,voto_recensioni_medio,latitudine,longitudine from attrazioni where " + radiusClausola + limitClausola;
        if (ultimaStrutturaVisitata instanceof AttrazioneView) {
            select = select.replace(limitClausola, noSeStesso + limitClausola);
        }
        return select;
    }

    @Override
    public String getSelectVicineFND(AbstractStrutturaView ultimaStrutturaVisitata, int distanza, int numMax) {
        String limitClausola = " limit " + numMax;
        String noSeStesso = " and id <> " + ultimaStrutturaVisitata.getPositionID();
        PointF posizione = new PointF(ultimaStrutturaVisitata.getLatitude(), ultimaStrutturaVisitata.getLongitude());
        String radiusClausola = this.costruisciPosizioneClausolaAlberghi(posizione, distanza);
        String select = "select img,id,nome,indirizzo,citta,paese,tipologia,costo_medio,giorno_chiusura,ora_apertura,ora_chiusura,servizi,voto_recensioni_medio,latitudine,longitudine from fnd where " + radiusClausola + limitClausola;
        if (ultimaStrutturaVisitata instanceof FNDView) {
            select = select.replace(limitClausola, noSeStesso + limitClausola);
        }
        return select;
    }

    @Override
    public String getSelectUtente(String user, String pass) {
        return "select nickname,id,password from utenti where id = '" + user + "' and password = '" + pass + "'";
    }

    @Override
    public String getSelectProfiloUtente(String user) {
        return "select nazionalita,sesso,data_nascita,data_registrazione,nickname,nazionalita_flag,sesso_flag,data_nascita_flag,numero_recensioni from utenti where nickname = '" + user + "'";
    }

    /* metodi di supporto */

    private String costruisciGiornoChiusuraClausolaRistoranti(String data, String ora) {
        String giornoClausola = "";
        String oraClausola = "";
        if (data.length() > 0) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
            Date date;
            String day;
            try {
                date = simpleDateFormat.parse(data);
                day = new SimpleDateFormat("EEEE", Locale.ITALIAN).format(date);
            } catch (Exception exception) {
                day = "x";
            }
            giornoClausola = SQLUtility.getNotEqualPredicate("giorno_chiusura", day, true);
            oraClausola = SQLUtility.getBetweenTwoAttributesPredicate("ora_apertura", ora, "ora_chiusura", true);
        }
        return SQLUtility.getAndProposition(giornoClausola, oraClausola);
    }

    private String costruisciPrenotazioneClausolaRistoranti(String data, String ora, int numAdulti, int numBambini) {
        String selectTavoli = "";
        int numeroTuristi = numAdulti + numBambini;
        if (data.length() > 0 || numeroTuristi > 0) {
            selectTavoli = "(select id_ristorante from tavoli_ristoranti";
            String dataClausola = "";
            String numeroTuristiClausola = "";
            if (data.length() > 0) {
                dataClausola = SQLUtility.getEqualPredicate("data_prenotato", data, true);
                String oraTavoloClausola = SQLUtility.getBetweenTwoAttributesPredicate("da_ora", ora, "a_ora", true);
                dataClausola = SQLUtility.getAndProposition(dataClausola, oraTavoloClausola);
                dataClausola = "((data_prenotato is null) or " + dataClausola + ")";
            } else {
                dataClausola = "(data_prenotato is null)";
            }
            if (numeroTuristi > 0) {
                numeroTuristiClausola = SQLUtility.getLowerOrEqualPredicate("persone", String.valueOf(numeroTuristi), false);
            }
            String whereTavoli = SQLUtility.getAndProposition(dataClausola, numeroTuristiClausola);
            selectTavoli = selectTavoli + " where " + whereTavoli + ")";
            selectTavoli = " id in " + selectTavoli;
        }
        return selectTavoli;
    }

    private String costruisciCostoClausolaRistoranti(float costoMin, float costoMax) {
        String costoMedioMinClausola = "";
        String costoMedioMaxClausola = "";
        String costoMedioEqClausola = "";
        if (costoMin == costoMax) {
            costoMedioEqClausola = SQLUtility.getEqualPredicate("costo_medio", String.valueOf(costoMin), false);
        } else {
            costoMedioMinClausola = SQLUtility.getLowerOrEqualPredicate("costo_medio", String.valueOf(costoMin), false);
            costoMedioMaxClausola = SQLUtility.getGreaterOrEqualPredicate("costo_medio", String.valueOf(costoMax), false);
        }
        return SQLUtility.getAndProposition(costoMedioEqClausola, costoMedioMinClausola, costoMedioMaxClausola);
    }

    private String costruisciVotoClausolaRistoranti(float votoMin, float votoMax) {
        String votoRecensioniMedioMinClausola = "";
        String votoRecensioniMedioMaxClausola = "";
        String votoRecensioniMedioEqClausola = "";
        if (votoMin == votoMax) {
            votoRecensioniMedioEqClausola = SQLUtility.getEqualPredicate("voto_recensioni_medio", String.valueOf(votoMin), false);
        } else {
            votoRecensioniMedioMinClausola = SQLUtility.getLowerOrEqualPredicate("voto_recensioni_medio", String.valueOf(votoMin), false);
            votoRecensioniMedioMaxClausola = SQLUtility.getGreaterOrEqualPredicate("voto_recensioni_medio", String.valueOf(votoMax), false);
        }
        return SQLUtility.getAndProposition(votoRecensioniMedioMinClausola, votoRecensioniMedioMaxClausola, votoRecensioniMedioEqClausola);
    }

    private String costruisciServiziClausolaRistoranti(String[] servizi) {
        return SQLUtility.getAndLikePredicate("servizi", servizi);
    }

    private String costruisciCucinaClausolaRistoranti(String[] cucina) {
        return SQLUtility.getAndLikePredicate("servizi", cucina);
    }

    private String costruisciPosizioneClausolaRistoranti(PointF point, int distanza) {
        return SQLUtility.getRadiusFromCenterPredicate(point, "latitudine", "longitudine", String.valueOf(distanza));
    }

    private String costruisciSortClausolaRistoranti(String select, String attributo, boolean isDecrescente, int numMax) {
        if (attributo.compareTo("Nessuno") != 0) {
            if (attributo.compareTo("Distanza") != 0) {
                attributo = attributo.toLowerCase();
                if (attributo.compareTo("città") == 0) {
                    attributo = "citta";
                } else {
                    attributo = attributo.replaceAll(" ", "_");
                }
                select = select + " order by " + attributo;
                if (attributo.compareTo("costo_medio") != 0) {
                    if (isDecrescente) {
                        select = select+ " asc";
                    } else {
                        select = select + " desc";
                    }
                }
                else {
                    if (isDecrescente) {
                        select = select + " desc";
                    } else {
                        select = select + " asc";
                    }
                }
            }
        }
        return select + " limit " + numMax;
    }

    private String costruisciPosizioneClausolaAlberghi(PointF point, int distanza) {
        return SQLUtility.getRadiusFromCenterPredicate(point, "latitudine", "longitudine", String.valueOf(distanza));
    }


    private String costruisciCostoClausolaAlberghi(float costoMin, float costoMax) {
        String costoMedioMinClausola = "";
        String costoMedioMaxClausola = "";
        String costoMedioEqClausola = "";
        if (costoMin == costoMax) {
            costoMedioEqClausola = SQLUtility.getEqualPredicate("costo", String.valueOf(costoMin), false);
        } else {
            costoMedioMinClausola = SQLUtility.getLowerOrEqualPredicate("costo", String.valueOf(costoMin), false);
            costoMedioMaxClausola = SQLUtility.getGreaterOrEqualPredicate("costo", String.valueOf(costoMax), false);
        }
        return SQLUtility.getAndProposition(costoMedioEqClausola, costoMedioMinClausola, costoMedioMaxClausola);
    }

    private String costruisciVotoClausolaAlberghi(float votoMin, float votoMax) {
        String votoRecensioniMedioMinClausola = "";
        String votoRecensioniMedioMaxClausola = "";
        String votoRecensioniMedioEqClausola = "";
        if (votoMin == votoMax) {
            votoRecensioniMedioEqClausola = SQLUtility.getEqualPredicate("voto_recensioni_medio", String.valueOf(votoMin), false);
        } else {
            votoRecensioniMedioMinClausola = SQLUtility.getLowerOrEqualPredicate("voto_recensioni_medio", String.valueOf(votoMin), false);
            votoRecensioniMedioMaxClausola = SQLUtility.getGreaterOrEqualPredicate("voto_recensioni_medio", String.valueOf(votoMax), false);
        }
        return SQLUtility.getAndProposition(votoRecensioniMedioMinClausola, votoRecensioniMedioMaxClausola, votoRecensioniMedioEqClausola);
    }

    private String costruisciPrenotazioneClausolaAlberghi(String dataIn, String oraIn, String dataOut, String oraOut, int numAdulti, int numBambini, float costoMin, float costoMax) {
        String costoClausola = this.costruisciCostoClausolaAlberghi(costoMin, costoMax);
        String numeroTuristiClausola = "";
        int numeroTuristi = numAdulti + numBambini;
        if (numeroTuristi > 0) {
            numeroTuristiClausola = SQLUtility.getLowerOrEqualPredicate("persone", String.valueOf(numeroTuristi), false);
        }
        String arrivoAndPartenzaClausola = "(data_arrivo is null)";
        if (dataIn.length() > 0 || dataOut.length() > 0) {
            String arrivoAndPartenzaClausolaLeft = "";
            String arrivoAndPartenzaClausolaRight = "";
            if (dataIn.length() > 0) {
                arrivoAndPartenzaClausolaLeft = "((data_partenza < '" + dataIn + "') or (data_partenza = '" + dataIn + "' and ora_partenza < '" + oraIn + "'))";
            }
            if (dataOut.length() > 0) {
                arrivoAndPartenzaClausolaRight = "((data_arrivo > '" + dataOut + "') or (data_arrivo = '" + dataOut + "' and ora_arrivo > '" + oraOut + "'))";
            }
            String supportClausola = SQLUtility.getAndProposition(arrivoAndPartenzaClausolaLeft, arrivoAndPartenzaClausolaRight);
            arrivoAndPartenzaClausola = SQLUtility.getOrProposition(arrivoAndPartenzaClausola, supportClausola);
        }
        String selectCamere = " id in (select id_albergo from camere_alberghi";
        String whereCamere = SQLUtility.getAndProposition(costoClausola, arrivoAndPartenzaClausola, numeroTuristiClausola);
        selectCamere = selectCamere + " where " + whereCamere + ")";
        return selectCamere;
    }

    private String costruisciSortClausolaAlberghi(String select, String attributo, boolean isDecrescente, int numMax) {
        if (attributo.compareTo("Nessuno") != 0) {
            if (attributo.compareTo("Distanza") != 0) {
                attributo = attributo.toLowerCase();
                if (attributo.compareTo("città") == 0) {
                    attributo = "citta";
                } else {
                    attributo = attributo.replaceAll(" ", "_");
                }
                select = select + " order by " + attributo;
                if (attributo.compareTo("costo") != 0) {
                    if (isDecrescente) {
                        select = select+ " asc";
                    } else {
                        select = select + " desc";
                    }
                }
                else {
                    if (isDecrescente) {
                        select = select + " desc";
                    } else {
                        select = select + " asc";
                    }
                }
            }
        }
        return select + " limit " + numMax;
    }

    private String costruisciTipologiaClausolaAlberghi(String[] tipologie) {
        return SQLUtility.getOrEqualPredicate("tipologia", true, tipologie);
    }

    private String costruisciStelleClausolaAlberghi(String[] stelle) {
        return SQLUtility.getOrEqualPredicate("stelle", false, stelle);
    }

    private String costruisciServiziClausolaAlberghi(String[] servizi) {
        return SQLUtility.getAndLikePredicate("servizi", servizi);
    }

    private String costruisciServiziRistorazioneClausolaAlberghi(String[] servizi) {
        return SQLUtility.getAndLikePredicate("servizi", servizi);
    }

    private String costruisciPosizioneClausolaAttrazioni(PointF point, int distanza) {
        return SQLUtility.getRadiusFromCenterPredicate(point, "latitudine", "longitudine", String.valueOf(distanza));
    }

    private String costruisciGiornoChiusuraClausolaAttrazioni(String data, String ora) {
        String giornoClausola = "";
        String oraClausola = "";
        if (data.length() > 0) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
            Date date;
            String day;
            try {
                date = simpleDateFormat.parse(data);
                day = new SimpleDateFormat("EEEE", Locale.ITALIAN).format(date);
            } catch (Exception exception) {
                day = "x";
            }
            giornoClausola = SQLUtility.getNotEqualPredicate("giorno_chiusura", day, true);
            oraClausola = SQLUtility.getBetweenTwoAttributesPredicate("ora_apertura", ora, "ora_chiusura", true);
        }
        return SQLUtility.getAndProposition(giornoClausola, oraClausola);
    }

    private String costruisciCostoClausolaAttrazioni(float costoMin, float costoMax) {
        String costoMedioMinClausola = "";
        String costoMedioMaxClausola = "";
        String costoMedioEqClausola = "";
        if (costoMin == costoMax) {
            costoMedioEqClausola = SQLUtility.getEqualPredicate("costo_biglietto", String.valueOf(costoMin), false);
        } else {
            costoMedioMinClausola = SQLUtility.getLowerOrEqualPredicate("costo_biglietto", String.valueOf(costoMin), false);
            costoMedioMaxClausola = SQLUtility.getGreaterOrEqualPredicate("costo_biglietto", String.valueOf(costoMax), false);
        }
        return SQLUtility.getAndProposition(costoMedioEqClausola, costoMedioMinClausola, costoMedioMaxClausola);
    }

    private String costruisciVotoClausolaAttrazioni(float votoMin, float votoMax) {
        String votoRecensioniMedioMinClausola = "";
        String votoRecensioniMedioMaxClausola = "";
        String votoRecensioniMedioEqClausola = "";
        if (votoMin == votoMax) {
            votoRecensioniMedioEqClausola = SQLUtility.getEqualPredicate("voto_recensioni_medio", String.valueOf(votoMin), false);
        } else {
            votoRecensioniMedioMinClausola = SQLUtility.getLowerOrEqualPredicate("voto_recensioni_medio", String.valueOf(votoMin), false);
            votoRecensioniMedioMaxClausola = SQLUtility.getGreaterOrEqualPredicate("voto_recensioni_medio", String.valueOf(votoMax), false);
        }
        return SQLUtility.getAndProposition(votoRecensioniMedioMinClausola, votoRecensioniMedioMaxClausola, votoRecensioniMedioEqClausola);
    }

    private String costruisciTipologiaClausolaAttrazioni(String[] tipologia) {
        return SQLUtility.getOrEqualPredicate("tipologia", true, tipologia);
    }

    private String costruisciServiziClausolaAttrazioni(String[] servizi) {
        return SQLUtility.getAndLikePredicate("servizi", servizi);
    }

    private String costruisciGuidaClausolaAttrazioni(String[] guida) {
        return SQLUtility.getAndLikePredicate("servizi", guida);
    }

    private String costruisciSortClausolaAttrazioni(String select, String attributo, boolean isDecrescente, int numMax) {
        if (attributo.compareTo("Nessuno") != 0) {
            if (attributo.compareTo("Distanza") != 0) {
                attributo = attributo.toLowerCase();
                if (attributo.compareTo("città") == 0) {
                    attributo = "citta";
                } else {
                    attributo = attributo.replaceAll(" ", "_");
                }
                select = select + " order by " + attributo;
                if (attributo.compareTo("costo_biglietto") != 0) {
                    if (isDecrescente) {
                        select = select+ " asc";
                    } else {
                        select = select + " desc";
                    }
                }
                else {
                    if (isDecrescente) {
                        select = select + " desc";
                    } else {
                        select = select + " asc";
                    }
                }
            }
        }
        return select + " limit " + numMax;
    }

    private String costruisciPosizioneClausolaFND(PointF point, int distanza) {
        return SQLUtility.getRadiusFromCenterPredicate(point, "latitudine", "longitudine", String.valueOf(distanza));
    }

    private String costruisciGiornoChiusuraClausolaFND(String data, String ora) {
        String giornoClausola = "";
        String oraClausola = "";
        if (data.length() > 0) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
            Date date;
            String day;
            try {
                date = simpleDateFormat.parse(data);
                day = new SimpleDateFormat("EEEE", Locale.ITALIAN).format(date);
            } catch (Exception exception) {
                day = "x";
            }
            giornoClausola = SQLUtility.getNotEqualPredicate("giorno_chiusura", day, true);
            oraClausola = SQLUtility.getBetweenTwoAttributesPredicate("ora_apertura", ora, "ora_chiusura", true);
        }
        return SQLUtility.getAndProposition(giornoClausola, oraClausola);
    }

    private String costruisciPrenotazioneClausolaFND(String data, String ora, int numAdulti, int numBambini) {
        String selectTavoli = "";
        int numeroTuristi = numAdulti + numBambini;
        if (data.length() > 0 || numeroTuristi > 0) {
            selectTavoli = "(select id_fnd from tavoli_fnd";
            String dataClausola = "";
            String numeroTuristiClausola = "";
            if (data.length() > 0) {
                dataClausola = SQLUtility.getEqualPredicate("data_prenotato", data, true);
                String oraTavoloClausola = SQLUtility.getBetweenTwoAttributesPredicate("da_ora", ora, "a_ora", true);
                dataClausola = SQLUtility.getAndProposition(dataClausola, oraTavoloClausola);
                dataClausola = "((data_prenotato is null) or " + dataClausola + ")";
            } else {
                dataClausola = "(data_prenotato is null)";
            }
            if (numeroTuristi > 0) {
                numeroTuristiClausola = SQLUtility.getLowerOrEqualPredicate("persone", String.valueOf(numeroTuristi), false);
            }
            String whereTavoli = SQLUtility.getAndProposition(dataClausola, numeroTuristiClausola);
            selectTavoli = selectTavoli + " where " + whereTavoli + ")";
            selectTavoli = " id in " + selectTavoli;
        }
        return selectTavoli;
    }

    private String costruisciCostoClausolaFND(float costoMin, float costoMax) {
        String costoMedioMinClausola = "";
        String costoMedioMaxClausola = "";
        String costoMedioEqClausola = "";
        if (costoMin == costoMax) {
            costoMedioEqClausola = SQLUtility.getEqualPredicate("costo_medio", String.valueOf(costoMin), false);
        } else {
            costoMedioMinClausola = SQLUtility.getLowerOrEqualPredicate("costo_medio", String.valueOf(costoMin), false);
            costoMedioMaxClausola = SQLUtility.getGreaterOrEqualPredicate("costo_medio", String.valueOf(costoMax), false);
        }
        return SQLUtility.getAndProposition(costoMedioEqClausola, costoMedioMinClausola, costoMedioMaxClausola);
    }

    private String costruisciVotoClausolaFND(float votoMin, float votoMax) {
        String votoRecensioniMedioMinClausola = "";
        String votoRecensioniMedioMaxClausola = "";
        String votoRecensioniMedioEqClausola = "";
        if (votoMin == votoMax) {
            votoRecensioniMedioEqClausola = SQLUtility.getEqualPredicate("voto_recensioni_medio", String.valueOf(votoMin), false);
        } else {
            votoRecensioniMedioMinClausola = SQLUtility.getLowerOrEqualPredicate("voto_recensioni_medio", String.valueOf(votoMin), false);
            votoRecensioniMedioMaxClausola = SQLUtility.getGreaterOrEqualPredicate("voto_recensioni_medio", String.valueOf(votoMax), false);
        }
        return SQLUtility.getAndProposition(votoRecensioniMedioMinClausola, votoRecensioniMedioMaxClausola, votoRecensioniMedioEqClausola);
    }

    private String costruisciServiziClausolaFND(String[] servizi) {
        return SQLUtility.getAndLikePredicate("servizi", servizi);
    }

    private String costruisciTipologiaClausolaFND(String[] tipologie) {
        return SQLUtility.getOrEqualPredicate("tipologia", true, tipologie);
    }

    private String costruisciSortClausolaFND(String select, String attributo, boolean isDecrescente, int numMax) {
        if (attributo.compareTo("Nessuno") != 0) {
            if (attributo.compareTo("Distanza") != 0) {
                attributo = attributo.toLowerCase();
                if (attributo.compareTo("città") == 0) {
                    attributo = "citta";
                } else {
                    attributo = attributo.replaceAll(" ", "_");
                }
                select = select + " order by " + attributo;
                if (attributo.compareTo("costo_medio") != 0) {
                    if (isDecrescente) {
                        select = select+ " asc";
                    } else {
                        select = select + " desc";
                    }
                }
                else {
                    if (isDecrescente) {
                        select = select + " desc";
                    } else {
                        select = select + " asc";
                    }
                }
            }
        }
        return select + " limit " + numMax;
    }

    private String getSelectRecensioni(AbstractStrutturaView strutturaView) {
        String id = strutturaView.getPositionID();
        String selectRecensioni = "select U.img,U.nickname,data,titolo,voto,testo from ";
        String userTable = this.getUserTableName();
        if (strutturaView instanceof AlbergoView) {
            selectRecensioni = selectRecensioni + "recensioni_alberghi as R," + userTable;
        } else {
            if (strutturaView instanceof RistoranteView) {
                selectRecensioni = selectRecensioni + "recensioni_ristoranti as R," + userTable;
            } else {
                if (strutturaView instanceof AttrazioneView) {
                    selectRecensioni = selectRecensioni + "recensioni_attrazioni as R," + userTable;
                } else {
                    if (strutturaView instanceof FNDView) {
                        selectRecensioni = selectRecensioni + "recensioni_fnd as R," + userTable;
                    }
                }
            }
        }
        selectRecensioni = selectRecensioni + " as U where id = " + id + " and R.nickname = U.nickname";
        return selectRecensioni;
    }

    private String costruisciVotiClausolaRecensioni(String[] voti) {
        return SQLUtility.getOrEqualPredicate("voto", false, voti);
    }

    private String costruisciDataClausolaRecensioni(String daData, String aData) {
        String daDataClausola = "";
        String aDataClausola = "";
        String dataClausola = "";
        if (daData.length() > 0 && aData.length() > 0) {
            if (daData.compareTo(aData) == 0) {
                dataClausola = SQLUtility.getEqualPredicate("data", daData, true);
            } else {
                daDataClausola = SQLUtility.getLowerOrEqualPredicate("data", daData, true);
                aDataClausola = SQLUtility.getGreaterOrEqualPredicate("data", aData, true);
            }
        }
        else {
            if (daData.length() > 0) {
                daDataClausola = SQLUtility.getLowerOrEqualPredicate("data", daData, true);
            }
        }
        return SQLUtility.getAndProposition(dataClausola, daDataClausola, aDataClausola);
    }

    private String costruisciSortClausolaRecensioni(String select, String attributo, boolean isDecrescente, int numMax) {
        if (attributo.compareTo("Nessuno") != 0) {
            attributo = attributo.toLowerCase();
            select = select + " order by " + attributo;
            if (attributo.compareTo("voto") != 0) {
                if (isDecrescente) {
                    select = select + " asc";
                } else {
                    select = select + " desc";
                }
            } else {
                if (isDecrescente) {
                    select = select + " desc";
                } else {
                    select = select + " asc";
                }
            }
        }
        return select + " limit " + numMax;
    }

    private String getFinaleClausolaAlberghi(String select, String finale) {
        if (finale.length() > 0) {
            select = select + finale;
        }
        return select;
    }

    private String getFinaleClausolaRistoranti(String select, String finale) {
        if (finale.length() > 0) {
            select = select + " where " + finale;
        }
        return select;
    }

    private String getFinaleClausolaAttrazioni(String select, String finale) {
        if (finale.length() > 0) {
            select = select + " where " + finale;
        }
        return select;
    }

    private String getFinaleClausolaFND(String select, String finale) {
        if (finale.length() > 0) {
            select = select + " where " + finale;
        }
        return select;
    }

    private String getFinaleClausolaRecensioni(String select, String finale) {
        if (finale.length() > 0) {
            select = select + " and " + finale;
        }
        return select;
    }

    @Override
    public AbstractList<String> getSuggestions() {
        LinkedList<String> suggerimenti = new LinkedList<>();
        LinkedList<ObjectsModel> objectsModels = (LinkedList) this.select ("select nome,indirizzo,citta,paese from alberghi ");
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
        objectsModels = (LinkedList) this.select("select nome,indirizzo,citta,paese from ristoranti ");
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
        objectsModels = (LinkedList) this.select ("select nome,indirizzo,citta,paese from attrazioni ");
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
        objectsModels = (LinkedList) this.select("select nome,indirizzo,citta,paese from fnd ");
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
    public String getUserTableName() {
        return "utenti";
    }

    @Override
    public String getUserIDAttribute() {
        return "id";
    }

    @Override
    public String getUserNicknameAttribute() {
        return "nickname";
    }

    @Override
    public String getUserPasswordAttribute() {
        return "password";
    }

    @Override
    public AbstractList<String> getAttributes(String tablename) {
        if (tablename.contains("recensioni")) {
            Vector<String> attributi = new Vector<>();
            attributi.add("id");
            attributi.add("nickname");
            attributi.add("data");
            attributi.add("titolo");
            attributi.add("voto");
            attributi.add("testo");
            return attributi;
        }
        else {
            if (tablename.compareTo("utenti") == 0) {
                Vector<String> attributi = new Vector<>();
                attributi.add("nome");
                attributi.add("cognome");
                attributi.add("nazionalita");
                attributi.add("sesso");
                attributi.add("data_nascita");
                attributi.add("data_registrazione");
                attributi.add("id");
                attributi.add("nickname");
                attributi.add("password");
                attributi.add("nazionalita_flag");
                attributi.add("sesso_flag");
                attributi.add("data_nascita_flag");
                attributi.add("numero_recensioni");
                return attributi;
            }
        }
        return null;
    }

    @Override
    public String getTableName(Object object) {
        String tableName = "";
        if (object instanceof AlbergoView) {
            tableName = "recensioni_alberghi";
        } else {
            if (object instanceof RistoranteView) {
                tableName = "recensioni_ristoranti";
            } else {
                if (object instanceof AttrazioneView) {
                    tableName = "recensioni_attrazioni";
                } else {
                    if (object instanceof FNDView) {
                        tableName = "recensioni_fnd";
                    }
                }
            }
        }
        return tableName;
    }

    @Override
    public boolean esisteConnessioneSingleton() {
        return InterfacciaBackEnd.SINGLETON_DRIVER_MANAGER_CONNECTOR != null;
    }

}
