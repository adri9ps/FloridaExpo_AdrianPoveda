package com.example.adri9ps.floridaexpo_adrianpoveda;

/**
 * Created by adri9ps on 25/3/18.
 */

public class Proyecto {

    private String nombreProyecto;
    private String descripcionProyecto;
    private String cicloProyecto;
    private String lugarProyecto;
    private int votosProyecto;

    public Proyecto(String nombreProyecto, String descripcionProyecto, String cicloProyecto, String lugarProyecto, int votosProyecto) {
        this.nombreProyecto = nombreProyecto;
        this.descripcionProyecto = descripcionProyecto;
        this.cicloProyecto = cicloProyecto;
        this.lugarProyecto = lugarProyecto;
        this.votosProyecto = votosProyecto;
    }

    @Override
    public String toString() {
        return "Proyecto{" +
                "nombre='" + nombreProyecto + '\'' +
                ", descripcion='" + descripcionProyecto + '\'' +
                ", cicloFormativo='" + cicloProyecto + '\'' +
                ", lugar='" + lugarProyecto + '\'' +
                ", votos='" + votosProyecto + '\'' +
                '}';
    }

    public String getNombreProyecto() {
        return nombreProyecto;
    }

    public void setNombreProyecto(String nombreProyecto) {
        this.nombreProyecto = nombreProyecto;
    }

    public String getDescripcionProyecto() {
        return descripcionProyecto;
    }

    public void setDescripcionProyecto(String descripcionProyecto) {
        this.descripcionProyecto = descripcionProyecto;
    }

    public String getCicloProyecto() {
        return cicloProyecto;
    }

    public void setCicloProyecto(String cicloProyecto) {
        this.cicloProyecto = cicloProyecto;
    }

    public int getVotosProyecto() {
        return votosProyecto;
    }

    public void setVotosProyecto(int votosProyecto) {
        this.votosProyecto = votosProyecto;
    }

    public String getLugarProyecto() {
        return lugarProyecto;

    }

    public void setLugarProyecto(String lugarProyecto) {
        this.lugarProyecto = lugarProyecto;
    }

    public Proyecto() {

    }
}

