package com.example.t2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.t2.ui.theme.T2Theme
import kotlin.random.Random

var listUser:ArrayList<User> = arrayListOf()
var listJenisTabungan:ArrayList<Tabungan> = arrayListOf()


fun main(){
    listJenisTabungan.add(Tabungan("Silver", 10000, 0.01, 15000))
    listJenisTabungan.add(Tabungan("Gold", 15000, 0.025, 50000))

    do {
        print("""
            Bank MDP
            1. Login
            2. Register
            3. Exit
            >> 
        """.trimIndent())
        var input = readln()?.toIntOrNull()
        println()

        if (input == 1){
            login()
        }
        else if (input == 2){
            register()
        }
    }
    while(input != 3)
}

fun register(){
    var donepass = true
    do{
        println("REGISTER")
        print("Nama: ")
        var nama = readln()
        print("Username: ")
        var username = readln()
        print("Password: ")
        var password = readln()
        print("Confirm Password: ")
        var confpass = readln()

        val cekUser = listUser.find { it.username == username }
        if (cekUser != null){
            println("Username '$username' already exists.")
            println()
            donepass = true
        }
        else {
            if (password != confpass){
                println("Password tidak sama")
                println()
                donepass = true
            }
            else {
                print("""
                    Bank:
                    1. BCA
                    2. BNI
                    3. CIMB
                    >> 
                    """.trimIndent())
                donepass = false
                var bankName = readln().toInt()
                print("""
                    Jenis Tabungan: 
                    1. Tabungan Silver
                    2. Tabungan Gold
                    >> 
                """.trimIndent())
                var jenis = readln().toInt()

                var cekSetoran = true
                do {
                    print("Setoran Awal: ")
                    var setoran = readln().toInt()
                    var tabungan:Tabungan = listJenisTabungan[0]
                    when(jenis){
                        1 -> tabungan = listJenisTabungan[0]
                        2 -> tabungan = listJenisTabungan[1]
                    }

                    if(setoran < tabungan.saldoMinim){
                        println("Saldo kurang!")
                        cekSetoran = true
                    }
                    else {
                        var noRek = Random.nextInt(10000,99999)
                        var bank:Bank = Bank("abc",0,0)
                        when(bankName){
                            1 -> bank = BCA(noRek)
                            2 -> bank = BNI(noRek)
                            3 -> bank = CIMB(noRek)
                        }
                        listUser.add(User(nama, username, password, bank, tabungan, setoran))
                        println("Berhasil register nasabah baru!")
                        println("Nomor rekening: $noRek")
                        cekSetoran = false
                        println()
                    }
                } while(cekSetoran)
            }
        }
    }while(donepass)
}

fun menuAdmin(){
    do {
        print("""
            Welcome, Admin
            1. Daftar Nasabah
            2. Daftar VA
            3. Ganti Bulan
            4. Logout
            >> 
        """.trimIndent())
        var menuAdmin = readln().toInt()
        println()

        if (menuAdmin == 1){
            var cek = true
            do {
                print("""
                    Daftar Nasabah
                    1. Bank BCA
                    2. Bank BNI
                    3. Bank CIMB
                    99. Back
                    >> 
                """.trimIndent())
                var pilihBank = readln().toInt()
                println()

                if (pilihBank == 1){
                    var bool = true
                    do {
//                        printNasabah("BCA")
                        println("Nasabah Bank BCA")
                        var counter = 1
                        val sortedUsers = listUser.sortedBy { it.nama }

                        for (i in sortedUsers){
                            if (i.bank is BCA){
                                println("$counter. ${i.toString()}")
                                counter++
                            }

                            if (counter == 1){
                                println("Tidak ada nasabah")
                            }
                        }
                        println("99. Back")
                        print(">> ")
                        println()
                        var back = readln().toInt()
                        if (back == 99){
                            bool = false
                        }
                    } while (bool)
                }
                else if (pilihBank == 2){
                    var bool = true
                    do {
                        printNasabah("BNI")
                        println("99. Back")
                        print(">> ")
                        var back = readln().toInt()
                        println()

                        if (back == 99){
                            bool = false
                        }
                    } while (bool)
                }
                else if (pilihBank == 99){
                    cek = false
                }

            } while(cek)
        }
        else if (menuAdmin == 2){

        }
        else if (menuAdmin == 3){

        }
    } while(menuAdmin != 4)
}

fun menuNasabah(){

}

fun printNasabah(bank:String){
    println("Nasabah Bank $bank")
    var counter = 1
    val sortedUsers = listUser.sortedBy { it.nama }

    for (i in sortedUsers){
        if (i.bank.namaBank == bank){
            println("$counter. ${i.toString()}")
            counter++
        }

        if (counter == 1){
            println("Tidak ada nasabah")
        }
    }
}
//fun printNasabah(bank:Bank){
//    println("Nasabah Bank ${bank.namaBank}")
//    var counter = 1
//    val sortedUsers = listUser.sortedBy { it.nama }
//
//    for (i in sortedUsers){
//        if (i.bank == bank){
//            println("$counter. ${i.toString()}")
//            counter++
//        }
//
//        if (counter == 1){
//            println("Tidak ada nasabah")
//        }
//    }
//}

fun login(){
    println("LOGIN")
    print("Username: ")
    var username = readln()
    print("Password: ")
    var password = readln()
    println()

    val cekUser = listUser.find { it.username == username }

    if (cekUser != null){
        if (cekUser.password == password){
            menuNasabah()
        }
        else {
            println("Password salah!")
        }
    }
    else if (username.equals("admin") && password.equals("admin")){
        menuAdmin()
    }
    else {
        println("User tidak ditemukan")
    }
}

