package database;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BDDgeneral {
    String id;
    List<String> tables;
    public BDDgeneral() throws Exception {
        ConnectionPerso co = new ConnectionPerso();
        try {
            Connection c = co.getConnection();
            Statement stat = c.createStatement();
            setAllTables(stat);
            stat.close();
            c.close();
        } catch (Exception e) {
            throw e;
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) throws Exception {
        if (id != null && id.equalsIgnoreCase("")) {
            throw new Exception("id invalide");
        }
        this.id = id;
    }

    // PostgreSQL
    public void setAllTables(Statement stat) throws Exception {
        List<String> totalTables = new ArrayList<String>();
        try {
            String sql = "select * from pg_catalog.pg_tables where schemaname != 'pg_catalog' and \n"
                    + "schemaname != 'information_schema';";

            ResultSet rs = stat.executeQuery(sql);
            while(rs.next()) {
                String table = rs.getString(2);
                totalTables.add(table);
            }
            rs.close();
        }
        catch(Exception e){
            throw e;
        }
        setTables(totalTables);
    }

    public List<String> getTables() {
        return tables;
    }

    public void setTables(List<String> tables) {
        this.tables = tables;
    }

    public boolean isTableHere() {
        boolean state = false;

        if(tables.contains(this.getClass().getSimpleName().toLowerCase()) == true) {
            state = true;
        }
        return state;
    }

    /// Methods GET valides
    public List<String> getMethodGetOK() {
        List<String> getMethodOk = new ArrayList<String>();

        Method[] methods = this.getClass().getDeclaredMethods();
        List<String> getMethods = new ArrayList<String>();

        for(int i = 0; i < methods.length; i++) {
            if (methods[i].getName().equalsIgnoreCase("getStats"))
                continue;
            if (methods[i].getName().equalsIgnoreCase("getListJoueurs"))
                continue;
            if (methods[i].getName().equalsIgnoreCase("getPos"))
                continue;
            if (methods[i].getName().equalsIgnoreCase("getCanShoot"))
                continue;
            if (methods[i].getName().equalsIgnoreCase("getPoss"))
                continue;
            if (methods[i].getName().equalsIgnoreCase("getTempsEq"))
                continue;

            if(methods[i].getName().startsWith("get") == true) {
                getMethods.add(methods[i].getName());
            }
        }

        Field[] fields = this.getClass().getDeclaredFields();
        String[] getFieldS = new String[fields.length];

        String s1, s2;

        for(int i = 0; i<getFieldS.length; i++) {

            s1 = fields[i].getName().substring(0, 1).toUpperCase();
            s2 = s1 + fields[i].getName().substring(1);

            getFieldS[i] = "get".concat(s2);

            if(getMethods.contains(getFieldS[i]) == true) {
                getMethodOk.add(getFieldS[i]);
            }
        }
        return getMethodOk;
    }

    public String generateId(Connection connect) throws Exception {
        int length = 6;
        String id = this.getClass().getSimpleName().substring(0,3).toUpperCase();
        boolean isNewConnect=false;

        try {
            if (connect == null) {
                ConnectionPerso co = new ConnectionPerso();
                connect = co.getConnection();
                isNewConnect = true;
            }

            Statement stat = connect.createStatement();

            ResultSet rs = stat.executeQuery(" select nextval('seq"+id+"') ");

            Integer seq = null;
            if(rs.next()) {
                seq = rs.getInt(1);
            }

            String seqs = seq.toString();
            int seqslen = seqs.length();

            String zeros = "0";
            for (int i=0; i < (length-seqslen-1); i++) {
                zeros += "0";
            }

            String zeroSeq = zeros + seqs;
            id += zeroSeq;
            connect.commit();
        }
        catch (Exception e) {
            connect.rollback();
            throw e;
        }
        finally {
            if (isNewConnect==true) {
                try {
                    connect.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
        }
        return id;
    }

    // Insertion dans la base si la table associee existe
    public void save(Connection connect) throws Exception {
        String nomTable = this.getClass().getSimpleName().toLowerCase();
        boolean isNewConnect=false;

        if(isTableHere() == true ) {
            try {
                if (connect==null) {
                    ConnectionPerso co = new ConnectionPerso();
                    connect=co.getConnection();
                    isNewConnect=true;
                }
                Statement stat = connect.createStatement();

                List<String> getMethodOk = getMethodGetOK();

                Method m = null;
                Object o = null;
                String obj = null;

                String sql="insert into "+nomTable+" values(";

                for(int i = 0; i < getMethodOk.size(); i++) {

                    m = this.getClass().getDeclaredMethod(getMethodOk.get(i));
                    o = m.invoke(this);

                    if (getMethodOk.get(i).equalsIgnoreCase("getId")) {
                        if (o == null) {
                            o = generateId(connect);
                            this.setId(o.toString());
                        }
                    }

                    if (o != null)
                        obj = o.toString();

                    else
                        obj="null";

                    o = "'"+obj+"'";

                    sql += o;
                    if(i<getMethodOk.size()-1)
                        sql += ",";
                }
                sql += ")";

                System.out.println(sql);
                stat.executeUpdate(sql);
                //insertHisto(connect,"insertion");

                connect.commit();
            }
            catch(Exception e) {
                connect.rollback();
                throw e;
            }
            finally {
                if (isNewConnect==true) {
                    connect.close();
                }
            }
        }
        else {
            throw new Exception("Table "+nomTable+" inexistante");
        }
    }

    public String upperFirstL(String s) {
        String s1;
        s1 = s.substring(0, 1).toUpperCase();
        s = s1 + s.substring(1);
        return s;
    }

    /// Selection dans la base
    public List<Object> select(Connection connect) throws Exception {
        List<Object> lo = new ArrayList<>();

        String nomTable = this.getClass().getSimpleName().toLowerCase();
        boolean isNewConnect = false;

        try {
            if (connect == null) {
                ConnectionPerso co = new ConnectionPerso();
                connect = co.getConnection();
                isNewConnect = true;
            }

            Statement stat = connect.createStatement();
            List<String> getMethodOk = getMethodGetOK();

            Field[] fields = this.getClass().getDeclaredFields();
            String[] fieldS = new String[getMethodOk.size()];

            for (int i = 0; i < fieldS.length; i++) {
                fieldS[i] = getMethodOk.get(i).substring(3);
            }

            HashMap<String, Object> condition = new HashMap<String, Object>();
            Object o1 = null, o = null;
            Method m = null;

            for (int i = 0; i < fieldS.length; i++) {
                m = this.getClass().getDeclaredMethod(getMethodOk.get(i));
                o = m.invoke(this);

                if (o != null) {
                    condition.put(fieldS[i], o);
                }
            }

            String sql = "select * from " + nomTable + " ";

            if (condition.isEmpty() == false) {
                sql += " where ";
                int j = 0;
                for (String i : condition.keySet()) {
                    sql += i + " = '" + condition.get(i) + "' ";

                    if (j < condition.size() - 1) {
                        sql += " and ";
                    }
                    j++;
                }
            }

            ///////////////////
            ResultSet results = stat.executeQuery(sql);

            while (results.next()) {
                Object object = this.getClass().getDeclaredConstructor().newInstance();
                for (int i = 0; i < this.getClass().getDeclaredFields().length; i++) {
                    if (this.getClass().getDeclaredFields()[i].getName().equalsIgnoreCase("Stats"))
                        continue;
                    if (this.getClass().getDeclaredFields()[i].getName().equalsIgnoreCase("listJoueurs"))
                        continue;
                    if (this.getClass().getDeclaredFields()[i].getName().equalsIgnoreCase("Pos"))
                        continue;
                    if (this.getClass().getDeclaredFields()[i].getName().equalsIgnoreCase("canShoot"))
                        continue;
                    if (this.getClass().getDeclaredFields()[i].getName().equalsIgnoreCase("poss"))
                        continue;
                    if (this.getClass().getDeclaredFields()[i].getName().equalsIgnoreCase("tempsEq"))
                        continue;

                    if (this.getClass().getDeclaredFields()[i].getType().getSimpleName().compareToIgnoreCase("String") == 0)
                        object.getClass().getDeclaredMethod("set" + upperFirstL(object.getClass().getDeclaredFields()[i].getName()), String.class).invoke(object, results.getString(object.getClass().getDeclaredFields()[i].getName()));
                    else if (this.getClass().getDeclaredFields()[i].getType().getSimpleName().compareToIgnoreCase("Date") == 0)
                        object.getClass().getDeclaredMethod("set" + upperFirstL(object.getClass().getDeclaredFields()[i].getName()), Date.class).invoke(object, results.getDate(object.getClass().getDeclaredFields()[i].getName()));
                    else if (this.getClass().getDeclaredFields()[i].getType().getSimpleName().compareToIgnoreCase("Double") == 0)
                        object.getClass().getDeclaredMethod("set" + upperFirstL(object.getClass().getDeclaredFields()[i].getName()), Double.class).invoke(object, results.getDouble(object.getClass().getDeclaredFields()[i].getName()));
                    else if (this.getClass().getDeclaredFields()[i].getType().getSimpleName().compareToIgnoreCase("Timestamp") == 0)
                        object.getClass().getDeclaredMethod("set" + upperFirstL(object.getClass().getDeclaredFields()[i].getName()), Timestamp.class).invoke(object, results.getTimestamp(object.getClass().getDeclaredFields()[i].getName()));
                    else if (this.getClass().getDeclaredFields()[i].getType().getSimpleName().compareToIgnoreCase("Time") == 0)
                        object.getClass().getDeclaredMethod("set" + upperFirstL(object.getClass().getDeclaredFields()[i].getName()), Time.class).invoke(object, results.getTime(object.getClass().getDeclaredFields()[i].getName()));

                    else
                        object.getClass().getDeclaredMethod("set" + upperFirstL(object.getClass().getDeclaredFields()[i].getName()), Integer.class).invoke(object, results.getInt(object.getClass().getDeclaredFields()[i].getName()));

                }
                lo.add(object);
            }

            //////////////////
        } catch (Exception e) {
            connect.rollback();
            throw e;
        } finally {
            if (isNewConnect == true) {
                try {
                    connect.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
        }
        return lo;
    }

    public String getValeurHisto() throws Exception {
        String valeur = "";

        try {
            List<String> getMethodOk = getMethodGetOK();

            Method m = null;
            Object o = null;
            String obj = null;

            Field[] fields = this.getClass().getDeclaredFields();

            for(int i = 0; i < getMethodOk.size(); i++) {
                m = this.getClass().getDeclaredMethod(getMethodOk.get(i));
                o = m.invoke(this);
                if (o != null){
                    obj = o.toString();
                }
                else {
                    obj = "null";
                }
                valeur += fields[i].getName().concat(":" +obj+" | ");
            }
        }
        catch (Exception e){
            throw e;
        }
        return valeur;
    }

/*
    ///////// Fonction insertHisto //////////
    public void insertHisto(Connection connect, String action) throws Exception {
        boolean isNewConnect = false;

        try {
            if (connect == null) {
                ConnectionPerso co = new ConnectionPerso();
                connect = co.getConnection();
                isNewConnect = true;
            }
            Statement stat = connect.createStatement();

            Historique h = new Historique();
            String sql="insert into historique values('"+generateId(h,connect)+"','"
                    +this.getClass().getSimpleName()+"','"+getValeurHisto()+ "','"+action +
                    "','"+ getId()+"', (select localtimestamp))";


            //System.out.println(action);
            //System.out.println(sql);

            stat.executeUpdate(sql);
            stat.close();
        }
        catch (Exception e){
            connect.rollback();
            throw e;
        }
        finally {
            if (isNewConnect == true) {
                try {
                    connect.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
        }
    }
*/
/// Fonction Update


    /// Fonction Delete
    public int delete(Connection connect) throws Exception {
        List<Object> lo = new ArrayList<>();

        String nomTable = this.getClass().getSimpleName().toLowerCase();
        boolean isNewConnect = false;

        int result = 0;
        try {
            if (connect == null) {
                ConnectionPerso co = new ConnectionPerso();
                connect = co.getConnection();
                isNewConnect = true;
            }

            Statement stat = connect.createStatement();
            List<String> getMethodOk = getMethodGetOK();

            Field[] fields = this.getClass().getDeclaredFields();
            String[] fieldS = new String[getMethodOk.size()];

            for (int i = 0; i < fieldS.length; i++) {
                fieldS[i] = getMethodOk.get(i).substring(3);
            }

            HashMap<String, Object> condition = new HashMap<String, Object>();
            Object o1 = null, o = null;
            Method m = null;

            for (int i = 0; i < fieldS.length; i++) {
                m = this.getClass().getDeclaredMethod(getMethodOk.get(i));
                o = m.invoke(this);

                if (o != null) {
                    condition.put(fieldS[i], o);
                }
            }

            String sql = "delete from " + nomTable + " ";

            if (condition.isEmpty() == false) {
                sql += " where ";
                int j = 0;
                for (String i : condition.keySet()) {
                    sql += i + " = '" + condition.get(i) + "' ";

                    if (j < condition.size() - 1) {
                        sql += " and ";
                    }
                    j++;
                }
            }
            ///////////////////
            result = stat.executeUpdate(sql);
            //insertHisto(connect,"delete");
            connect.commit();
            //////////////////
        } catch (Exception e) {
            connect.rollback();
            throw e;
        } finally {
            if (isNewConnect == true) {
                try {
                    connect.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
        }
        return result;
    }
}
