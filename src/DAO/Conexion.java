/**
 Copyright [2019] [Andres Iturria Soler, Javier Linares Castrillon, Samuel Aragones Lozano]
 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at
 http://www.apache.org/licenses/LICENSE-2.0
 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.

 */

package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import domain.movimientos.Ataque;
import domain.movimientos.MO;
import domain.movimientos.MT;
import domain.movimientos.Maquina;
import domain.stats.Pokemon;


/**
 *
 * @author Javier Linares Castrillon
 * @author Andrés Iturria Soler
 *
 */

/**
 * Clase encargada de establecer conexión con la base de datos y realizar consultas.
 */
public class Conexion {
    private static Connection con = null;
    private static Statement stmt = null;


    /**
     * @return establece una conexión con la base de datos.
     */
    public static Connection conectar() {


        String url = "jdbc:sqlite:dataBase/Pokedex.db";

        try {
            Class.forName("org.sqlite.JDBC");

            con = DriverManager.getConnection(url);
            stmt = con.createStatement();


        } catch (Exception e) {
            System.out.println("No se pudo conectar a la base de datos");
            e.printStackTrace();
        }
        return con;
    }

    /**
     * @return Maquinas Técnicas o ocultas.
     */
    public static ArrayList<Maquina> maquinas() {


        ArrayList<Maquina> maquinas = new ArrayList<>();

        try {
            ResultSet resultado1 = stmt.executeQuery("select DISTINCT mo.mo, m.nombre from MO mo, formaAprendizaje fa, pokemonMovimientoForma pMF , movimiento m where " +
                    "mo.idFormaAprendizaje = fa.idFormaAprendizaje and fa.idFormaAprendizaje = pMF.idFormaAprendizaje and pMF.idMovimiento = m.idMovimiento order by mo.mo");

            while (resultado1.next()) {
                Maquina mo = new MO();
                mo.setNombre(resultado1.getString("MO"));
                mo.setNombreAtaque(resultado1.getString("Nombre"));
                maquinas.add(mo);

            }
            ResultSet resultado2 = stmt.executeQuery("select DISTINCT mt.mt, m.nombre from MT mt, formaAprendizaje fa, pokemonMovimientoForma pMF , movimiento m where " +
                    "mt.idFormaAprendizaje = fa.idFormaAprendizaje and fa.idFormaAprendizaje = pMF.idFormaAprendizaje and pMF.idMovimiento = m.idMovimiento ORDER BY mt.mt");

            while (resultado1.next()) {
                Maquina mt = new MT();
                mt.setNombre(resultado2.getString("MT"));
                mt.setNombreAtaque(resultado2.getString("Nombre"));
                maquinas.add(mt);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return maquinas;

    }

    /**
     * @param i id de la localizacion
     * @return nombre de todos los pokemons que se localizan en una localizacion de id i.
     */

    public static ArrayList<String> pokemonLocalizaciones(int i) {


        ArrayList<String> pokemons = new ArrayList<>();

        try {
            ResultSet resultado = stmt.executeQuery("select p.nombre from pokemon p, localizacion l, pokemonLocalizacion pl where " +
                    "p.idPokemon = pl.idPokemon and l.idLocalizacion = pl.idLocalizacion and l.idLocalizacion = " + i + " ORDER BY p.idPokemon");
            while (resultado.next()) {
                pokemons.add(resultado.getString("Nombre"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pokemons;
    }

    /**
     * @param n nombre del pokemon del  que se desean saber sus stats.
     * @return un objeto de la clase Pokemon con las stats en cuestion.
     */
    public static Pokemon pokemonStats(String n) {

        Pokemon p = new Pokemon();
        String path = "";

        try {
            ResultSet resultado = stmt.executeQuery("select * from pokemon p where p.nombre = \"" + n + "\"");
            while (resultado.next()) {

                path = "res/imagenes/fotos/" + resultado.getInt("idPokemon") + ".png";
                p.setName(n);
                p.setName(resultado.getString("Nombre"));
                p.setPath(path);
                p.setPs(resultado.getInt("Ps"));
                p.setAt(resultado.getInt("Ataque"));
                p.setDef(resultado.getInt("Defensa"));
                p.setAte(resultado.getInt("AtaqueEsp"));
                p.setDefe(resultado.getInt("DefensaEsp"));
                p.setVel(resultado.getInt("Velocidad"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return p;
    }

    /**
     * @param n nombre del pokemon.
     * @return el nombre y el path de la imagen de un pokemon de nombre n.
     */
    public static Pokemon pokemonNombrePath(String n) {

        Pokemon p = new Pokemon();
        String path = "";

        try {
            ResultSet resultado = stmt.executeQuery("select  p.nombre, p.idPokemon from pokemon p where p.nombre = \"" + n + "\"");
            while (resultado.next()) {

                path = "res/imagenes/fotos/" + resultado.getInt("idPokemon") + ".png";
                p.setName(resultado.getString("nombre"));
                p.setPath(path);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return p;
    }

    /**
     * @param n nombre del pokemon del que se desean saber sus ataques.
     * @return todos los movimientos que es capaz de aprender un pokemon de nombre n.
     */
    public static ArrayList<String> pokemonMovimientos(String n) {

        ArrayList<String> ataques = new ArrayList<>();

        try {
            ResultSet resultado = stmt.executeQuery("select DISTINCT m.nombre from pokemon p, movimiento m, pokemonMovimientoForma" +
                    " pm where p.idPokemon = pm.idPokemon and pm.idMovimiento = m.idMovimiento and p.nombre = \"" + n + "\"");
            while (resultado.next()) {
                ataques.add(resultado.getString("Nombre"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ataques;
    }


    /**
     * @return un arrayList de la clase Pokemon con todos los pokemons.
     */
    public static ArrayList<Pokemon> getPokemons() {


        ArrayList<Pokemon> pokemons = new ArrayList<>();
        try {
            ResultSet result1 = stmt.executeQuery("SELECT * FROM pokemon ORDER BY idpokemon");


            while (result1.next()) {
                Pokemon p = new Pokemon();
                p.setAltura(result1.getDouble("altura"));
                p.setPeso(result1.getInt("peso"));
                p.setCategoria(result1.getString("categoria"));
                p.setId(result1.getInt("idpokemon"));
                p.setName(result1.getString("nombre"));
                p.setPath("res/imagenes/fotos/" + result1.getString("idPokemon") + ".png");
                pokemons.add(p);
            }

            Pokemon po = new Pokemon();
            po.setPath("res/imagenes/fotos/0.png");
            pokemons.add(po);

            ResultSet result2 = stmt.executeQuery("SELECT * FROM pokemontipo ORDER BY idPokemon");
            int indice;
            while (result2.next()) {
                indice = result2.getInt("idpokemon") - 1;
                pokemons.get(indice).setTipo(result2.getInt("idTipo"));
            }
            ResultSet result3 = stmt.executeQuery("SELECT * FROM lineaEVo ORDER BY idPokemon");
            while (result3.next()) {
                indice = result3.getInt("idpokemon") - 1;
                pokemons.get(indice).setLineaEvo(result3.getInt("evo1"));
                pokemons.get(indice).setLineaEvo(result3.getInt("evo2"));
                pokemons.get(indice).setLineaEvo(result3.getInt("evo3"));


            }
        } catch (SQLException ex) {
            Logger.getLogger(DAO.Conexion.class.getName()).log(Level.SEVERE, null, ex);

        }
        return pokemons;
    }


    /**
     * @return un arrayList de la clase Ataque con todos los movimientos.
     */
    public static ArrayList<Ataque> getAtaques() {

        ArrayList<Ataque> ataques = new ArrayList<>();
        try {
            ResultSet result1 = stmt.executeQuery("SELECT * FROM movimiento ORDER BY idMovimiento");


            while (result1.next()) {

                Ataque a = new Ataque();
                a.setNombre(result1.getString("Nombre"));
                a.setPp(result1.getInt("PP"));
                a.setPotencia(result1.getInt("Potencia"));
                a.setPrecision(result1.getInt("Precision"));
                a.setDescripcion(result1.getString("Descripcion"));
                a.setTipo("res/imagenes/tipos/" + result1.getString("idTipo") + ".png");
                ataques.add(a);
            }

        } catch (Exception e) {

        }
        return ataques;
    }


    public static ArrayList<String> getEfis(int i){

        ArrayList<String> efis = new ArrayList<>();

        try{
            ResultSet result = stmt.executeQuery( "select t.idTipo from pokemon p, tipo t, efectividad e, pokemonEfectividad pe " +
                    "where p.idPokemon = pe.idPokemon and pe.idTipo = t.idTipo and pe.idEfectividad = e.id and e.id = 1 and p.idPokemon = \"" + i + "\"");

            while(result.next()){
                efis.add("res/imagenes/tipos/" + result.getInt("idTipo") + ".png");
            }
        }catch (Exception e){

        }
        return efis;
    }

    public static ArrayList<String> getInmus(int i){

        ArrayList<String> inmus = new ArrayList<>();

        try{
            ResultSet result = stmt.executeQuery( "select t.idTipo from pokemon p, tipo t, efectividad e, pokemonEfectividad pe " +
                    "where p.idPokemon = pe.idPokemon and pe.idTipo = t.idTipo and pe.idEfectividad = e.id and e.id = 3 and p.idPokemon = \"" + i + "\"");

            while(result.next()){
                inmus.add("res/imagenes/tipos/" + result.getInt("idTipo") + ".png");
            }
        }catch (Exception e){

        }
        return inmus;
    }

    public static ArrayList<String> getDebs(int i){

        ArrayList<String> debs = new ArrayList<>();

        try{
            ResultSet result = stmt.executeQuery( "select t.idTipo from pokemon p, tipo t, efectividad e, pokemonEfectividad pe " +
                    "where p.idPokemon = pe.idPokemon and pe.idTipo = t.idTipo and pe.idEfectividad = e.id and e.id = 2 and p.idPokemon = \"" + i + "\"");

            while(result.next()){
                debs.add("res/imagenes/tipos/" + result.getInt("idTipo") + ".png");
            }
        }catch (Exception e){

        }
        return debs;
    }





}









