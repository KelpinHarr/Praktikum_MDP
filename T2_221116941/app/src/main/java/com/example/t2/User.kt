package com.example.t2

import kotlin.random.Random

open class User(var nama:String, var username:String, var password:String, var bank:Bank, var jenisTabungan:Tabungan, var saldo:Int=0) {
    var listTransfer:MutableList<User> = mutableListOf()

    override fun toString(): String {
        return "${this.nama} - ${this.jenisTabungan.jenis} - ${this.bank.noRek}"
    }
}