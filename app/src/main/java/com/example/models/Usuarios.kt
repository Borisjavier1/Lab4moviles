package com.example.models

class Usuarios {
    private var usuarios: ArrayList<Usuario> = ArrayList<Usuario>()
    init{
        addUsuario(Usuario("123","123",1)) //Est
        addUsuario(Usuario("900","900",1)) //Est
        addUsuario(Usuario("555","555",2)) //Prof
        addUsuario(Usuario("000","000",3)) //Adminin
        addUsuario(Usuario("444","444",4)) //Matri
    }

    private object HOLDER {
        val INSTANCE = Usuarios()
    }

    companion object {
        val instance: Usuarios by lazy {
            HOLDER.INSTANCE
        }
    }

    fun addUsuario(usuario: Usuario){
        usuarios?.add(usuario)
    }

    fun getUsuario(cedula: String): Usuario? {
        for (p: Usuario in usuarios!!){
            if(p.cedula.equals(cedula)){
                return p;
            }
        }
        return null;
    }

    fun getUsuario(): ArrayList<Usuario>{
        return this.usuarios!!
    }


    fun deleteUsuario(position: Int){
        usuarios!!.removeAt(position)
    }

    fun editProfesor(p: Usuario, position: Int){
        var aux = usuarios!!.get(position)
        aux.cedula = p.cedula
        aux.contrasena = p.contrasena
        aux.rol = p.rol
    }

    fun login(user: String?, password: String?): Boolean{
        for(p: Usuario in usuarios!!){
            if(p.cedula.equals(user) && p.contrasena.equals(password)){
                return true
            }
        }
        return false
    }
    fun loginP(user: String?, password: String?): Usuario?{
        for(p: Usuario in usuarios!!){
            if(p.cedula.equals(user) && p.contrasena.equals(password)){
                return p
            }
        }
        return null
    }

}