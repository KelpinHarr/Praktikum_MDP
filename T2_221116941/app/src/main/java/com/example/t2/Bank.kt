package com.example.t2

import kotlin.random.Random

open class Bank {
    var noRek:Int
    var namaBank:String
    var biayaTF:Int
    var biayaVA:Int

    //default constructor
    constructor(namaBank: String = "", biayaTF:Int? = 0, biayaVA:Int? = 0){
        this.namaBank = namaBank
        this.biayaTF = biayaTF!!
        this.biayaVA = biayaVA!!
        this.noRek = 0
    }

    //companion object
    companion object {
        fun getNasabah(listNasabah:MutableList<User>, namaBank: String) :MutableList<User>{
            var list:MutableList<User> = mutableListOf()
            for (i in listNasabah){
                if (i.bank.namaBank == namaBank) list.add(i)
            }
            return list
        }
        fun listNasabah(idxBank :Int){
            var counter = 1
            val sortedUsers = listUser.sortedBy { it.nama }

            for (i in sortedUsers){
                if (idxBank == 1){
                    if (i.bank is BCA){
                        println("$counter. ${i.toString()}")
                        counter++
                    }
                }
                else if (idxBank == 2){
                    if (i.bank is BNI){
                        println("$counter. ${i.toString()}")
                        counter++
                    }
                }
                else if (idxBank == 3){
                    if (i.bank is CIMB){
                        println("$counter. ${i.toString()}")
                        counter++
                    }
                }

            }
            if (counter == 1){
                println("Tidak ada nasabah")
            }
        }
    }
}