package com.example.t2

open class VirtualAccount(var nama:String, var kode:Int){
    override fun toString(): String {
        return "${this.nama} (${this.kode})"
    }

    fun mutasiVA(nominal :Int){
        println("($nominal) Transfer ke VA ${this.nama} ${this.kode}")
    }

    companion object {
        fun listVA(list:ArrayList<VirtualAccount>){
            val litVA = list.sortedBy { it.nama.first() }
            for ((idx, i) in listVA.withIndex()){
                println("${idx+1}. ${i.toString()}")
            }
        }
    }
}