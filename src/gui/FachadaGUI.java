package gui;

import aplicacion.Controller.CoidadorController;
import aplicacion.Controller.ContableController;
import aplicacion.Controller.ExcepcionController;
import aplicacion.Controller.LoginController;
import aplicacion.FachadaAplicacion;
import aplicacion.Usuario;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FachadaGUI {
    private Stage stage = new Stage();
    private FachadaAplicacion fa;

    public FachadaGUI(FachadaAplicacion fa) {
        this.fa = fa;
    }

    public void mostrarVentanaLogin() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXML/Login.fxml"));
        try {
            Scene scene = new Scene(loader.load(), 310, 188);
            LoginController controller = loader.getController();
            controller.initManager(fa);
            stage.setTitle("Login");
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.show();

        } catch (IOException ex) {
            Logger.getLogger(FachadaGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void mostrarVentanaContable(Usuario usuario) {

        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("FXML/Contable.fxml")
        );

        try {
            Scene scene = new Scene(loader.load(), 800, 600);
            ContableController controller = loader.getController();
            controller.initUser(fa, usuario);
            stage.setTitle("Ventana Contable: " + usuario.getNombre());
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void mostrarVentanaCoidador(Usuario usuario) {

        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("FXML/Coidador.fxml")
        );

        try {
            Scene scene = new Scene(loader.load(), 800, 600);
            CoidadorController controller = loader.getController();
            controller.initUser(fa, usuario);
            stage.setTitle("Ventana Coidador: " + usuario.getNombre());
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void logout() {
        fa.logout();
    }

    public void sair() {
        fa.sair();
    }

    public ObservableList buscarAnimal(String animal) {
        return fa.buscarAnimal(animal);
    }

    public void muestraExcepcion(String txtExcepcion) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXML/Excepcion.fxml"));
        try {
            Stage stage2 = new Stage();
            Scene scene = new Scene(loader.load());
            ExcepcionController controller = loader.getController();
            controller.initExcepcion(txtExcepcion, stage2);
            stage2.initOwner(stage);
            stage2.initModality(Modality.APPLICATION_MODAL);
            stage2.setTitle("Error");
            stage2.setScene(scene);
            stage2.centerOnScreen();
            stage2.show();

        } catch (IOException ex) {
            Logger.getLogger(FachadaGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}