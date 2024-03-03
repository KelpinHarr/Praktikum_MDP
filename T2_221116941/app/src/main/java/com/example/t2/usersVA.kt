package com.example.t2

class usersVA(nama:String = "VA", kode:Int = 12345689) :VirtualAccount(nama = nama, kode = kode) {
    var nasabah:User = listUser[0]
    fun daftarVA(): String {
        return "${this.nama} (${this.kode})"
    }
    constructor(nama :String, kode :Int, nasabah:User) : this() {
        this.nama = nama
        this.kode = kode
        this.nasabah = nasabah
    }

    override fun toString(): String {
        return "${this.nama} - ${this.kode}"
    }
}