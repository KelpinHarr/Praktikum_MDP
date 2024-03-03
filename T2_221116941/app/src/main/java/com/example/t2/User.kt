package com.example.t2

import kotlin.random.Random

open class User(var nama:String, var username:String, var password:String, var bank:Bank, var jenisTabungan:Tabungan, var saldo:Int=0) {
    var listRekening:MutableList<User> = mutableListOf()
    var listBank:MutableList<User> = mutableListOf()
    var listVA:MutableList<usersVA> = mutableListOf() // gtww
    var listMutasi:MutableList<String> = mutableListOf()

    companion object{
        fun cekKembar(list:MutableList<User>, input:Int):Boolean{
            return !list.filter { it.bank.noRek == input }.isEmpty()
        }
    }

    fun daftarTransfer(): String{
        return "${this.nama} (${this.bank.noRek})"
    }
    fun daftarBank(): String{
        return "${this.nama} (${this.bank.namaBank} - ${this.bank.noRek})"
    }

    override fun toString(): String {
        return "${this.nama} - ${this.jenisTabungan.jenis} - ${this.bank.noRek}"
    }
}