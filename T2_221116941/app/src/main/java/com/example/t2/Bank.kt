package com.example.t2

import kotlin.random.Random

open class Bank (var namaBank:String, var biayaTF:Int, var biayaVA:Int? = 0){
    var noRek:Int = 0

    companion object {
        fun getNasabah(listNasabah:List<User>, namaBank: String) :List<User>{
//            var list:MutableList<User> = mutableListOf()
            var bank:Bank = Bank("ABC", 0, 0)
            if (namaBank == "BCA"){
                listNasabah.filter { it.bank is BCA }
            }
            else if (namaBank == "BNI"){
                listNasabah.filter { it.bank is BNI }
            }
            else if (namaBank == "CIMB"){
                listNasabah.filter { it.bank is CIMB }
            }
            return listNasabah
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

                if (counter == 1){
                    println("Tidak ada nasabah")
                }
            }
        }
    }
}