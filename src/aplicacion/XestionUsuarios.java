package aplicacion;

import baseDatos.FachadaBaseDatos;

class XestionUsuarios {
    private FachadaAplicacion fa;


    public XestionUsuarios(FachadaAplicacion fa) {
        this.fa = fa;
    }

    public Usuario comprobarAutentificacion(String dni, String pass) {
        return fa.fbd.comprobarAutentificacion(dni, pass);
    }

    public void logout() {
        fa.setUsuarioActual(null);
        fa.fgui.mostrarVentanaLogin();
    }

    public boolean logeado(String dni, String pass) {
        Usuario u = fa.comprobarAutentificacion(dni, pass);
        if (u != null) {
            fa.setUsuarioActual(u);
            if (u.getTipo().toString().equals("Contable")) {
                fa.fgui.mostrarVentanaContable(u);
            } else {
                fa.fgui.mostrarVentanaCoidador(u);
            }
            return true;
        } else return false;
    }
}
