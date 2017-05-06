package baseDatos;

import aplicacion.Animal;
import aplicacion.Aviso;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DAOAvisos extends DAOAbstracto {

    public DAOAvisos(Connection conexion, aplicacion.FachadaAplicacion fa) {
        super.setConexion(conexion);
        super.setFachadaAplicacion(fa);
    }
    
    // Novo aviso
    public void novoAvisoAnimal(Aviso aviso) {
        Connection con;
        PreparedStatement stmAvisos = null;
        con = this.getConexion();
        try {
            stmAvisos = con.prepareStatement("insert into avisosanimais values (?,?,?,?,?,?,?,?);");
            /*avisosanimais (animal, nome, descripcion, dataInicio, dataFin, coidador, contable, tratamento)
            stmAvisos.setInt(1, animal.getId());
            stmAvisos.setString(2, animal.getNombre());
            stmAvisos.setString(3, animal.getEspecie());
            stmAvisos.setInt(4, animal.getEdad());
            stmAvisos.setInt(5, animal.getPeso());
            stmAvisos.setString(6, animal.getSexo());
            stmAvisos.setInt(7, animal.getXaula());
            stmAvisos.setInt(8, animal.getArea());*/

            stmAvisos.executeUpdate();
        } catch (SQLException e) {
            fa.muestraExcepcion(e.getMessage());
        } finally {
            try {
                stmAvisos.close();
            } catch (SQLException e) {
                fa.muestraExcepcion("Imposible cerrar cursores");
            }
        }
    }

    // Todos os avisos
    public ObservableList buscarAvisos() {
        ObservableList avisos = FXCollections.observableArrayList();
        Connection con;
        PreparedStatement stmAvisos = null;
        ResultSet rsAvisos;
        con = this.getConexion();
        try {
            stmAvisos = con.prepareStatement("select * from avisosxaulas;");
            rsAvisos = stmAvisos.executeQuery();
            while (rsAvisos.next()) {
                avisos.add(new Aviso(rsAvisos.getString("nome"), rsAvisos.getString("descripcion"), rsAvisos.getString("coidador") == null ? "" : rsAvisos.getString("coidador"), rsAvisos.getString("contable") == null ? "" : rsAvisos.getString("contable"), rsAvisos.getInt("area"), rsAvisos.getInt("xaula"), rsAvisos.getDate("datainicio").toString(), rsAvisos.getDate("datafin") == null ? "Non" : rsAvisos.getDate("datafin").toString(), rsAvisos.getDate("tratamento") == null ? "" : rsAvisos.getDate("tratamento").toString(), "xaula"));
            }
            stmAvisos = con.prepareStatement("select * from avisosareas;");
            rsAvisos = stmAvisos.executeQuery();
            while (rsAvisos.next()) {
                avisos.add(new Aviso(rsAvisos.getString("nome"), rsAvisos.getString("descripcion"), rsAvisos.getString("coidador") == null ? "" : rsAvisos.getString("coidador"), rsAvisos.getString("contable") == null ? "" : rsAvisos.getString("contable"), rsAvisos.getInt("area"), rsAvisos.getDate("datainicio").toString(), rsAvisos.getDate("datafin") == null ? "Non" : rsAvisos.getDate("datafin").toString(), rsAvisos.getString("tratamento") == null ? "" : rsAvisos.getString("tratamento").toString(), "area"));
            }
            stmAvisos = con.prepareStatement("select * from avisosanimais;");
            rsAvisos = stmAvisos.executeQuery();
            while (rsAvisos.next()) {
                Animal a = (Animal) fa.buscarAnimal(rsAvisos.getString("animal")).get(0);
                avisos.add(new Aviso(rsAvisos.getInt("animal"), a.getNombre(), rsAvisos.getString("nome"), rsAvisos.getString("descripcion"), rsAvisos.getString("coidador") == null ? "" : rsAvisos.getString("coidador"), rsAvisos.getString("contable") == null ? "" : rsAvisos.getString("contable"), rsAvisos.getDate("datainicio").toString(), rsAvisos.getDate("datafin") == null ? "Non" : rsAvisos.getDate("datafin").toString(), rsAvisos.getString("tratamento") == null ? "" : rsAvisos.getString("tratamento").toString(), "animal"));
            }
        } catch (SQLException e) {
            fa.muestraExcepcion(e.getMessage());
        } finally {
            try {
                stmAvisos.close();
            } catch (SQLException e) {
                fa.muestraExcepcion("Imposible cerrar cursores");
            }
        }
        return avisos;
    }
    
    // Todos os avisos que engadira o coidador actual
    public ObservableList buscarAvisosPropios() {
        ObservableList avisos = FXCollections.observableArrayList();
        Connection con;
        PreparedStatement stmAvisos = null;
        ResultSet rsAvisos;
        con = this.getConexion();
        try {
            stmAvisos = con.prepareStatement("select * from avisosxaulas where coidador = ?;");
            stmAvisos.setString(1, fa.getUsuarioActual().getDni());
            rsAvisos = stmAvisos.executeQuery();
            while (rsAvisos.next()) {
                avisos.add(new Aviso(rsAvisos.getString("nome"), rsAvisos.getString("descripcion"), rsAvisos.getString("coidador") == null ? "" : rsAvisos.getString("coidador"), rsAvisos.getString("contable") == null ? "" : rsAvisos.getString("contable"), rsAvisos.getInt("area"), rsAvisos.getInt("xaula"), rsAvisos.getDate("datainicio").toString(), rsAvisos.getDate("datafin") == null ? "Non" : rsAvisos.getDate("datafin").toString(), rsAvisos.getDate("tratamento") == null ? "" : rsAvisos.getDate("tratamento").toString(), "xaula"));
            }
            stmAvisos = con.prepareStatement("select * from avisosareas where coidador = ?;");
            stmAvisos.setString(1, fa.getUsuarioActual().getDni());
            rsAvisos = stmAvisos.executeQuery();
            while (rsAvisos.next()) {
                avisos.add(new Aviso(rsAvisos.getString("nome"), rsAvisos.getString("descripcion"), rsAvisos.getString("coidador") == null ? "" : rsAvisos.getString("coidador"), rsAvisos.getString("contable") == null ? "" : rsAvisos.getString("contable"), rsAvisos.getInt("area"), rsAvisos.getDate("datainicio").toString(), rsAvisos.getDate("datafin") == null ? "Non" : rsAvisos.getDate("datafin").toString(), rsAvisos.getString("tratamento") == null ? "" : rsAvisos.getString("tratamento").toString(), "area"));
            }
            stmAvisos = con.prepareStatement("select * from avisosanimais;");
            rsAvisos = stmAvisos.executeQuery();
            while (rsAvisos.next()) {
                Animal a = (Animal) fa.buscarAnimal(rsAvisos.getString("animal")).get(0);
                avisos.add(new Aviso(rsAvisos.getInt("animal"), a.getNombre(), rsAvisos.getString("nome"), rsAvisos.getString("descripcion"), rsAvisos.getString("coidador") == null ? "" : rsAvisos.getString("coidador"), rsAvisos.getString("contable") == null ? "" : rsAvisos.getString("contable"), rsAvisos.getDate("datainicio").toString(), rsAvisos.getDate("datafin") == null ? "Non" : rsAvisos.getDate("datafin").toString(), rsAvisos.getString("tratamento") == null ? "" : rsAvisos.getString("tratamento").toString(), "animal"));
            }
        } catch (SQLException e) {
            fa.muestraExcepcion(e.getMessage());
        } finally {
            try {
                stmAvisos.close();
            } catch (SQLException e) {
                fa.muestraExcepcion("Imposible cerrar cursores");
            }
        }
        return avisos;
    }
    
    // Avisos de animais que coida o coidador actual
    public ObservableList buscarAvisosAnimais() {
        ObservableList avisos = FXCollections.observableArrayList();
        Connection con;
        PreparedStatement stmAvisos = null;
        ResultSet rsAvisos;
        con = this.getConexion();
        try {
            stmAvisos = con.prepareStatement("select * from avisosanimais where coidador = ?;");
            stmAvisos.setString(1, fa.getUsuarioActual().getDni());
            rsAvisos = stmAvisos.executeQuery();
            while (rsAvisos.next()) {
                Animal a = (Animal) fa.buscarAnimal(rsAvisos.getString("animal")).get(0);
                avisos.add(new Aviso(rsAvisos.getInt("animal"), a.getNombre(), rsAvisos.getString("nome"), rsAvisos.getString("descripcion"), 
                        rsAvisos.getString("coidador") == null ? "" : rsAvisos.getString("coidador"), 
                        rsAvisos.getString("contable") == null ? "" : rsAvisos.getString("contable"), rsAvisos.getDate("datainicio").toString(), 
                        rsAvisos.getDate("datafin") == null ? "Non" : rsAvisos.getDate("datafin").toString(), 
                        rsAvisos.getString("tratamento") == null ? "" : rsAvisos.getString("tratamento").toString(), "animal"));
            }
        } catch (SQLException e) {
            fa.muestraExcepcion(e.getMessage());
        } finally {
            try {
                stmAvisos.close();
            } catch (SQLException e) {
                fa.muestraExcepcion("Imposible cerrar cursores");
            }
        }
        return avisos;
    }
    
    // Avisos de xaulas nas que estan animais que coida o coidador actual
    public ObservableList buscarAvisosXaulas() {
        ObservableList avisos = FXCollections.observableArrayList();
        Connection con;
        PreparedStatement stmAvisos = null;
        ResultSet rsAvisos;
        con = this.getConexion();
        try {
            stmAvisos = con.prepareStatement("select * from avisosxaulas where coidador = ?;");
            stmAvisos.setString(1, fa.getUsuarioActual().getDni());
            rsAvisos = stmAvisos.executeQuery();
            while (rsAvisos.next()) {
                avisos.add(new Aviso(rsAvisos.getString("nome"), rsAvisos.getString("descripcion"), rsAvisos.getString("coidador") == null ? "" : rsAvisos.getString("coidador"), 
                        rsAvisos.getString("contable") == null ? "" : rsAvisos.getString("contable"), rsAvisos.getInt("area"), rsAvisos.getInt("xaula"), 
                        rsAvisos.getDate("datainicio").toString(), rsAvisos.getDate("datafin") == null ? "Non" : rsAvisos.getDate("datafin").toString(), 
                        rsAvisos.getDate("tratamento") == null ? "" : rsAvisos.getDate("tratamento").toString(), "xaula"));
            }
        } catch (SQLException e) {
            fa.muestraExcepcion(e.getMessage());
        } finally {
            try {
                stmAvisos.close();
            } catch (SQLException e) {
                fa.muestraExcepcion("Imposible cerrar cursores");
            }
        }
        return avisos;
    }
    
    // Avisos de areas nas que estan as xaulas onde estan os animais que coida o coidador actual
    public ObservableList buscarAvisosAreas() {
        ObservableList avisos = FXCollections.observableArrayList();
        Connection con;
        PreparedStatement stmAvisos = null;
        ResultSet rsAvisos;
        con = this.getConexion();
        try {
            stmAvisos = con.prepareStatement("select * from avisosareas where coidador = ?;");
            stmAvisos.setString(1, fa.getUsuarioActual().getDni());
            rsAvisos = stmAvisos.executeQuery();
            while (rsAvisos.next()) {
                avisos.add(new Aviso(rsAvisos.getString("nome"), rsAvisos.getString("descripcion"), rsAvisos.getString("coidador") == null ? "" : rsAvisos.getString("coidador"), 
                        rsAvisos.getString("contable") == null ? "" : rsAvisos.getString("contable"), rsAvisos.getInt("area"), rsAvisos.getDate("datainicio").toString(), 
                        rsAvisos.getDate("datafin") == null ? "Non" : rsAvisos.getDate("datafin").toString(), 
                        rsAvisos.getString("tratamento") == null ? "" : rsAvisos.getString("tratamento").toString(), "area"));
            }
        } catch (SQLException e) {
            fa.muestraExcepcion(e.getMessage());
        } finally {
            try {
                stmAvisos.close();
            } catch (SQLException e) {
                fa.muestraExcepcion("Imposible cerrar cursores");
            }
        }
        return avisos;
    }

    public void resolverAviso(Aviso aviso) {
        Connection con;
        PreparedStatement stmAvisos = null;
        con = this.getConexion();
        try {
            if (aviso.getTipo().equals("area")) {
                stmAvisos = con.prepareStatement("update avisosareas set datafin = current_date , contable = ? , tratamento = ? where area = ? and nome = ? and datainicio = ?;");
                stmAvisos.setString(1, fa.getUsuarioActual().getDni());
                stmAvisos.setString(2, aviso.getTratamento());
                stmAvisos.setInt(3, Integer.valueOf(aviso.getArea()));
                stmAvisos.setString(4, aviso.getAsunto());
                stmAvisos.setDate(5, java.sql.Date.valueOf(aviso.getDataInicio()));
            }
            if (aviso.getTipo().equals("xaula")) {
                stmAvisos = con.prepareStatement("update avisosxaulas set datafin = current_date , contable = ? , tratamento = ? where idarea = ? and id = ? and nome = ? and datainicio = ?;");
                stmAvisos.setString(1, fa.getUsuarioActual().getDni());
                stmAvisos.setString(2, aviso.getTratamento());
                stmAvisos.setInt(3, Integer.valueOf(aviso.getArea()));
                stmAvisos.setInt(4, Integer.valueOf(aviso.getXaula()));
                stmAvisos.setString(5, aviso.getAsunto());
                stmAvisos.setDate(6, java.sql.Date.valueOf(aviso.getDataInicio()));
            }
            if (aviso.getTipo().equals("animal")) {
                stmAvisos = con.prepareStatement("update avisosanimais set datafin = current_date , contable = ? , tratamento = ? where animal = ? and nome = ? and datainicio = ?;");
                stmAvisos.setString(1, fa.getUsuarioActual().getDni());
                stmAvisos.setString(2, aviso.getTratamento());
                stmAvisos.setInt(3, Integer.valueOf(aviso.getAnimal()));
                stmAvisos.setString(4, aviso.getAsunto());
                stmAvisos.setDate(5, java.sql.Date.valueOf(aviso.getDataInicio()));
            }
            stmAvisos.executeUpdate();

        } catch (SQLException e) {
            fa.muestraExcepcion(e.getMessage());
        } finally {
            try {
                stmAvisos.close();
            } catch (SQLException e) {
                fa.muestraExcepcion("Imposible cerrar cursores");
            }
        }
    }

    public void reabrirAviso(Aviso aviso) {
        Connection con;
        PreparedStatement stmAvisos = null;
        con = this.getConexion();
        try {
            if (aviso.getTipo().equals("area")) {
                stmAvisos = con.prepareStatement("update avisosareas set datafin = null , contable = null , tratamento = null where area = ? and nome = ? and datainicio = ?;");
                stmAvisos.setInt(1, Integer.valueOf(aviso.getArea()));
                stmAvisos.setString(2, aviso.getAsunto());
                stmAvisos.setDate(3, java.sql.Date.valueOf(aviso.getDataInicio()));

            }
            if (aviso.getTipo().equals("xaula")) {
                stmAvisos = con.prepareStatement("update avisosxaulas set datafin = null , contable = null , tratamento = null where idarea = ? and nome = ? and datainicio = ? and id = ?;");
                stmAvisos.setInt(1, Integer.valueOf(aviso.getArea()));
                stmAvisos.setString(2, aviso.getAsunto());
                stmAvisos.setDate(3, java.sql.Date.valueOf(aviso.getDataInicio()));
                stmAvisos.setInt(4, Integer.valueOf(aviso.getXaula()));

            }
            if (aviso.getTipo().equals("animal")) {
                stmAvisos = con.prepareStatement("update avisosanimais set datafin = null , contable = null , tratamento = null where animal = ? and nome = ? and datainicio = ?;");
                stmAvisos.setInt(1, Integer.valueOf(aviso.getAnimal()));
                stmAvisos.setString(2, aviso.getAsunto());
                stmAvisos.setDate(3, java.sql.Date.valueOf(aviso.getDataInicio()));
            }
            stmAvisos.executeUpdate();

        } catch (SQLException e) {
            fa.muestraExcepcion(e.getMessage());
        } finally {
            try {
                stmAvisos.close();
            } catch (SQLException e) {
                fa.muestraExcepcion("Imposible cerrar cursores");
            }
        }
    }

    public void borrarAviso(Aviso aviso) {
        Connection con;
        PreparedStatement stmAvisos = null;
        con = this.getConexion();
        try {
            if (aviso.getTipo().equals("area")) {
                stmAvisos = con.prepareStatement("delete from avisosareas where area = ? and nome = ? and datainicio = ?;");
                stmAvisos.setInt(1, Integer.valueOf(aviso.getArea()));
                stmAvisos.setString(2, aviso.getAsunto());
                stmAvisos.setDate(3, java.sql.Date.valueOf(aviso.getDataInicio()));

            }
            if (aviso.getTipo().equals("xaula")) {
                stmAvisos = con.prepareStatement("delete from avisosxaulas where idarea = ? and nome = ? and datainicio = ? and id = ?;");
                stmAvisos.setInt(1, Integer.valueOf(aviso.getArea()));
                stmAvisos.setString(2, aviso.getAsunto());
                stmAvisos.setDate(3, java.sql.Date.valueOf(aviso.getDataInicio()));
                stmAvisos.setInt(4, Integer.valueOf(aviso.getXaula()));

            }
            if (aviso.getTipo().equals("animal")) {
                stmAvisos = con.prepareStatement("delete from avisosanimais where animal = ? and nome = ? and datainicio = ?;");
                System.out.println(aviso.getAnimal());
                stmAvisos.setInt(1, Integer.valueOf(aviso.getAnimal()));
                stmAvisos.setString(2, aviso.getAsunto());
                stmAvisos.setDate(3, java.sql.Date.valueOf(aviso.getDataInicio()));
            }
            stmAvisos.executeUpdate();
        } catch (SQLException e) {
            fa.muestraExcepcion(e.getMessage());
        } finally {
            try {
                stmAvisos.close();
            } catch (SQLException e) {
                fa.muestraExcepcion("Imposible cerrar cursores");
            }
        }
    }
}
