package com.svalero.asociation.exception;


public class SocioNotFoundException extends Exception{//Consideración a futuro: reemplazar Eception por RuntimeException y eliminar la necesidad de hacer los métodos en service throwable
    public SocioNotFoundException(String message){
        super(message);
    }
}
