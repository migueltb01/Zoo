/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package baseDatos;

import aplicacion.Animal;
import aplicacion.Comida;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Rosa
 */
public class DAOComidas extends DAOAbstracto {

    public DAOComidas(Connection conexion, aplicacion.FachadaAplicacion fa) {
        super.setConexion(conexion);
        super.setFachadaAplicacion(fa);
    }

    public ObservableList buscarComidas() {
        ObservableList comidas = FXCollections.observableArrayList();

        Connection con = this.getConexion();
        PreparedStatement stmComidas = null;
        ResultSet rsComidas = null;

        try {
            stmComidas = con.prepareStatement("select * from comidas;");
            rsComidas = stmComidas.executeQuery();

            while (rsComidas.next()) {
                comidas.add(new Comida(rsComidas.getInt("id"), rsComidas.getString("nome"), rsComidas.getString("unidades"), rsComidas.getInt("stock")));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            fa.muestraExcepcion(e.getMessage());
        } finally {
            try {
                stmComidas.close();
            } catch (SQLException e) {
                fa.muestraExcepcion("Imposible cerrar cursores");
            }
        }

        return comidas;
    }

    public ObservableList buscarAnimaisComida(Comida comida) {
        ObservableList animais = FXCollections.observableArrayList();

        Connection con = this.getConexion();
        PreparedStatement stmAnimais = null;
        ResultSet rsAnimais = null;

        try {
            stmAnimais = con.prepareStatement("select * from comer join animais on comer.animal = animais.id where comer.comida = ?;");
            stmAnimais.setInt(1, comida.getId());
            rsAnimais = stmAnimais.executeQuery();

            while (rsAnimais.next()) {
                animais.add(new Animal(rsAnimais.getInt("id"), rsAnimais.getString("nome")));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            fa.muestraExcepcion(e.getMessage());
        } finally {
            try {
                stmAnimais.close();
            } catch (SQLException e) {
                fa.muestraExcepcion("Imposible cerrar cursores");
            }
        }

        return animais;
    }

    public ObservableList buscarAnimaisNonComida(Comida comida) {
        ObservableList animais = FXCollections.observableArrayList();

        Connection con = this.getConexion();
        PreparedStatement stmAnimais = null;
        ResultSet rsAnimais = null;

        try {
            stmAnimais = con.prepareStatement("select * from animais where id not in (select animal from comer where comida = ?);");
            stmAnimais.setInt(1, comida.getId());
            rsAnimais = stmAnimais.executeQuery();

            while (rsAnimais.next()) {
                animais.add(new Animal(rsAnimais.getInt("id"), rsAnimais.getString("nome")));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            fa.muestraExcepcion(e.getMessage());

        } finally {
            try {
                stmAnimais.close();
            } catch (SQLException e) {
                fa.muestraExcepcion("Imposible cerrar cursores");
            }
        }

        return animais;
    }

    public void novaComida(Comida comida) {
        Connection con = this.getConexion();
        PreparedStatement stmComidas = null;

        try {
            stmComidas = con.prepareStatement("insert into comidas values(?, ?, ?, ?);");
            stmComidas.setInt(1, comida.getId());
            stmComidas.setString(2, comida.getNombre());
            stmComidas.setInt(3, comida.getStock());
            stmComidas.setString(4, comida.getUds());

            stmComidas.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            fa.muestraExcepcion(e.getMessage());

        } finally {
            try {
                stmComidas.close();
            } catch (SQLException e) {
                fa.muestraExcepcion("Imposible cerrar cursores");
            }
        }
    }

    public void updateComida(Comida comida) {
        Connection con = this.getConexion();
        PreparedStatement stmComidas = null;

        try {
            stmComidas = con.prepareStatement("update comidas set nome = ?, stock = ?, unidades = ? where id = ?");
            stmComidas.setInt(4, comida.getId());
            stmComidas.setString(1, comida.getNombre());
            stmComidas.setInt(2, comida.getStock());
            stmComidas.setString(3, comida.getUds());

            stmComidas.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            fa.muestraExcepcion(e.getMessage());

        } finally {
            try {
                stmComidas.close();
            } catch (SQLException e) {
                fa.muestraExcepcion("Imposible cerrar cursores");
            }
        }
    }

    public void gardarComida(Comida comida) {
        Connection con = this.getConexion();
        PreparedStatement stmComidas = null;
        ResultSet rsComidas = null;

        try {
            con.setAutoCommit(false);
            
            stmComidas = con.prepareStatement("select * from comidas where id = ?;"); 
            stmComidas.setInt(1, comida.getId());
            rsComidas = stmComidas.executeQuery();

            if (!rsComidas.next()) { // Non está, facemos insert
                novaComida(comida);
            } else { // Si está, facemos update
                updateComida(comida);
            }

            con.commit();
            con.setAutoCommit(true);

        } catch (SQLException e) {
            try {
                con.rollback();
            } catch (SQLException ex) {
                fa.muestraExcepcion(ex.getMessage());
            }

            System.out.println(e.getMessage());
            fa.muestraExcepcion(e.getMessage());

        } finally {
            try {
                stmComidas.close();
            } catch (SQLException e) {
                fa.muestraExcepcion("Imposible cerrar cursores");
            }
        }
    }

    public void borrarComida(Comida comida) {
        Connection con = this.getConexion();
        PreparedStatement stmComidas = null;

        try {
            stmComidas = con.prepareStatement("delete from comidas where id = ?;");
            stmComidas.setInt(1, comida.getId());

            stmComidas.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            fa.muestraExcepcion(e.getMessage());

        } finally {
            try {
                stmComidas.close();

            } catch (SQLException e) {
                fa.muestraExcepcion("Imposible cerrar cursores");
            }
        }
    }

    public void addAnimal(Comida c, Animal a, Integer cantidade) {
        Connection con = this.getConexion();
        PreparedStatement stmComer = null;

        try {
            stmComer = con.prepareStatement("insert into comer values(?, ?, ?);");
            stmComer.setInt(1, cantidade);
            stmComer.setInt(2, a.getId());
            stmComer.setInt(3, c.getId());

            stmComer.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            fa.muestraExcepcion(e.getMessage());

        } finally {
            try {
                stmComer.close();

            } catch (SQLException e) {
                fa.muestraExcepcion("Imposible cerrar cursores");
            }
        }
    }

    public void removeAnimal(Comida c, Animal a) {
        Connection con = this.getConexion();
        PreparedStatement stmComer = null;

        try {
            stmComer = con.prepareStatement("delete from comer where comida = ? and animal = ?;");
            stmComer.setInt(1, c.getId());
            stmComer.setInt(2, a.getId());

            stmComer.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            fa.muestraExcepcion(e.getMessage());

        } finally {
            try {
                stmComer.close();

            } catch (SQLException e) {
                fa.muestraExcepcion("Imposible cerrar cursores");
            }
        }
    }

    public void cambiarCantidade(Comida c, Animal a, Integer cantidade) {
        Connection con = this.getConexion();
        PreparedStatement stmComer = null;

        try {
            stmComer = con.prepareStatement("update comer set cantidaderacion = ? where comida = ? and animal = ?;");
            stmComer.setInt(2, c.getId());
            stmComer.setInt(3, a.getId());
            stmComer.setInt(1, cantidade);

            stmComer.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            fa.muestraExcepcion(e.getMessage());

        } finally {
            try {
                stmComer.close();

            } catch (SQLException e) {
                fa.muestraExcepcion("Imposible cerrar cursores");
            }
        }
    }

    public int recuperarCantidade(Comida c, Animal a) {
        Connection con = this.getConexion();
        PreparedStatement stmComer = null;
        ResultSet rsComer = null;
        int cantidade = 0;

        try {
            stmComer = con.prepareStatement("select cantidaderacion from comer where comida = ? and animal = ?;");
            stmComer.setInt(1, c.getId());
            stmComer.setInt(2, a.getId());

            rsComer = stmComer.executeQuery();

            if (rsComer.next()) {
                cantidade = rsComer.getInt("cantidaderacion");
            } 

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            fa.muestraExcepcion(e.getMessage());

        } finally {
            try {
                stmComer.close();

            } catch (SQLException e) {
                fa.muestraExcepcion("Imposible cerrar cursores");
            }
        }
        
        return cantidade; 
    }

}
