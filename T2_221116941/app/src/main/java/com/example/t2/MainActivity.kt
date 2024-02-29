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

        if (input == 1){

        }
        else if (input == 2){
            register()
        }
        else {
            println("Invalid input!")
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

        if (password != confpass){
            println("Password tidak sama")
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

            print("Setoran Awal: ")
            var setoran = readln().toInt()
            var tabungan:Tabungan = listJenisTabungan[0]
            when(jenis){
                1 -> tabungan = listJenisTabungan[0]
                2 -> tabungan = listJenisTabungan[1]
            }

            if(setoran < tabungan.saldoMinim){
                println("Saldo kurang!")
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
            }
        }
    }while(donepass)
}

